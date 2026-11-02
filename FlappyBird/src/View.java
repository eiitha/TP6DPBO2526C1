import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * Panel tampilan permainan.
 * Class ini menggambar background, player, pipa, skor, dan overlay Game Over.
 */
public class View extends JPanel {
    private final Logic logic;

    // Komponen Swing untuk menampilkan skor dan tombol kembali ke menu.
    private final JLabel scoreLabel;
    private final JButton menuButton;

    // Gambar yang digunakan dalam permainan.
    private Image background, bird, upperPipe, lowerPipe;

    public View(Logic logic) {
        this.logic = logic;

        // Ukuran panel mengikuti ukuran aset background.
        setPreferredSize(new Dimension(360, 640));
        setLayout(null);
        setFocusable(true);

        // Memuat semua gambar dari folder src/assets.
        try {
            background = ImageIO.read(new File("src/assets/background.png"));
            bird = ImageIO.read(new File("src/assets/bird.png"));
            upperPipe = ImageIO.read(new File("src/assets/upperPipe.png"));
            lowerPipe = ImageIO.read(new File("src/assets/lowerPipe.png"));
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println("Gagal memuat gambar: " + ex.getMessage());
        }

        // JLabel untuk menampilkan skor yang diperbarui oleh Logic.
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(12, 10, 180, 40);
        add(scoreLabel);

        // Tombol ini hanya ditampilkan ketika status permainan Game Over.
        menuButton = new JButton("MAIN MENU");
        menuButton.setBounds(100, 430, 160, 42);
        menuButton.setVisible(false);
        menuButton.addActionListener(e -> logic.returnToMenu());
        add(menuButton);
    }

    // Memperbarui teks skor pada JLabel.
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    // Dipanggil Swing ketika panel perlu digambar ulang.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Graphics2D dibuat salinan agar pengaturan gambar tidak memengaruhi objek Graphics asli.
        Graphics2D g2 = (Graphics2D) g.create();

        // Nearest-neighbor menjaga tampilan pixel art tetap tajam saat digambar.
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // Menggambar background; jika gagal dimuat, gunakan warna pengganti.
        if (background != null) {
            g2.drawImage(background, 0, 0, 360, 640, null);
        } else {
            g2.setColor(new Color(110, 200, 220));
            g2.fillRect(0, 0, 360, 640);
        }

        // Menggambar seluruh pipa berdasarkan daftar yang dikelola Logic.
        for (Pipe pipe : logic.getPipes()) {
            Image img = pipe.isUpper() ? upperPipe : lowerPipe;

            if (img != null) {
                g2.drawImage(img, pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            } else {
                // Bentuk persegi hijau menjadi pengganti jika gambar pipa tidak tersedia.
                g2.setColor(Color.GREEN);
                g2.fillRect(pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight());
            }
        }

        // Menggambar player sesuai posisi dan ukuran dari class Player.
        Player p = logic.getPlayer();
        if (bird != null) {
            g2.drawImage(bird, p.getPosX(), p.getPosY(),
                    p.getWidth(), p.getHeight(), null);
        } else {
            // Lingkaran kuning menjadi pengganti jika gambar burung tidak tersedia.
            g2.setColor(Color.YELLOW);
            g2.fillOval(p.getPosX(), p.getPosY(), p.getWidth(), p.getHeight());
        }

        // Ketika Game Over, gambar overlay gelap dan informasi hasil permainan.
        boolean over = logic.isGameOver();
        menuButton.setVisible(over);

        if (over) {
            g2.setColor(new Color(0, 0, 0, 145));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 38));
            g2.drawString("GAME OVER", 48, 270);

            g2.setFont(new Font("Arial", Font.BOLD, 23));
            g2.drawString("Score: " + logic.getScore(), 135, 320);

            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            g2.drawString("R = Restart    M = Menu", 91, 365);
        }

        // Melepaskan salinan Graphics2D setelah proses menggambar selesai.
        g2.dispose();
    }
}
