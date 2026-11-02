import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * Class utama aplikasi.
 * Bertugas membuat window, menampilkan Main Menu, dan menghubungkan
 * tampilan menu dengan tampilan permainan.
 */
public class App {
    public static void main(String[] args) {
        // Menjalankan pembuatan komponen Swing pada Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flappy Bird");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            // CardLayout memungkinkan menu dan game berada dalam satu window,
            // lalu ditampilkan bergantian tanpa membuka JFrame baru.
            CardLayout cards = new CardLayout();
            JPanel root = new JPanel(cards);

            // Membuat logika game dan panel untuk menggambar permainan.
            Logic logic = new Logic();
            View game = new View(logic);
            logic.setView(game);
            game.addKeyListener(logic); // Logic menerima input keyboard dari panel game.

            // Membuat panel Main Menu dengan background gambar.
            JPanel menu = new JPanel(new GridBagLayout()) {
                private Image bg;

                {
                    try {
                        bg = ImageIO.read(new File("src/assets/background.png"));
                    } catch (Exception ex) {
                        System.err.println("Background menu gagal dimuat: " + ex.getMessage());
                    }
                }

                // Menggambar background memenuhi ukuran panel menu.
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (bg != null) {
                        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                    }
                }
            };
            menu.setPreferredSize(new Dimension(360, 640));

            // Panel content menyusun judul, tombol, dan petunjuk secara vertikal.
            JPanel content = new JPanel();
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            content.setOpaque(false);

            JLabel title = new JLabel("FLAPPY BIRD");
            title.setFont(new Font("Arial", Font.BOLD, 38));
            title.setForeground(Color.WHITE);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel subtitle = new JLabel("Hindari pipa dan raih skor tertinggi!");
            subtitle.setFont(new Font("Arial", Font.BOLD, 13));
            subtitle.setForeground(Color.WHITE);
            subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Tombol untuk memulai permainan dan menutup aplikasi.
            JButton start = new JButton("START GAME");
            JButton exit = new JButton("EXIT");
            for (JButton b : new JButton[]{start, exit}) {
                b.setFont(new Font("Arial", Font.BOLD, 18));
                b.setAlignmentX(Component.CENTER_ALIGNMENT);
                b.setMaximumSize(new Dimension(210, 45));
                b.setFocusable(false);
            }

            JLabel controls = new JLabel("SPACE = Terbang");
            controls.setForeground(Color.WHITE);
            controls.setFont(new Font("Arial", Font.BOLD, 14));
            controls.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Menambahkan komponen menu beserta jarak antar komponen.
            content.add(title);
            content.add(Box.createVerticalStrut(10));
            content.add(subtitle);
            content.add(Box.createVerticalStrut(42));
            content.add(start);
            content.add(Box.createVerticalStrut(14));
            content.add(exit);
            content.add(Box.createVerticalStrut(25));
            content.add(controls);
            menu.add(content);

            // Mendaftarkan kedua tampilan ke CardLayout.
            root.add(menu, "MENU");
            root.add(game, "GAME");
            frame.setContentPane(root);

            // Aksi ketika game meminta kembali ke Main Menu.
            logic.setReturnToMenuAction(() -> {
                cards.show(root, "MENU");
                SoundManager.playBgm();
            });

            // Tombol Start menampilkan game, menginisialisasi ulang permainan,
            // lalu mengembalikan fokus keyboard ke panel game.
            start.addActionListener(e -> {
                cards.show(root, "GAME");
                logic.startGame();
                SwingUtilities.invokeLater(game::requestFocusInWindow);
            });

            // Tombol Exit menghentikan audio sebelum aplikasi ditutup.
            exit.addActionListener(e -> {
                SoundManager.closeAll();
                System.exit(0);
            });

            // Membersihkan audio jika window ditutup melalui tombol X.
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    SoundManager.closeAll();
                }
            });

            // Mengatur ukuran dan posisi window, lalu menampilkannya.
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // BGM mulai diputar saat Main Menu pertama kali muncul.
            SoundManager.playBgm();
        });
    }
}
