import javax.swing.Timer;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Mengatur seluruh logika permainan.
 * Class ini menangani timer, gerakan player dan pipa, skor,
 * deteksi tabrakan, serta input keyboard.
 */
public class Logic implements ActionListener, KeyListener {
    // Ukuran area permainan dan posisi batas tanah bagian bawah.
    private static final int WIDTH = 360, GROUND = 570;

    private Player player;
    private final ArrayList<Pipe> pipes = new ArrayList<>();
    private final Timer gameLoop;
    private final Random random = new Random();

    // Konstanta gerakan: gravitasi, kekuatan lompatan, dan kecepatan pipa.
    private final int gravity = 1, jumpStrength = -10, pipeVelocityX = -4;

    private int score, pipeCooldown;
    private boolean gameOver;
    private View view;
    private Runnable returnToMenuAction;

    public Logic() {
        resetGame(); // Menyiapkan kondisi awal sebelum permainan dimulai.

        // Timer berjalan setiap 16 ms (sekitar 60 kali per detik).
        // Setiap tick akan memanggil actionPerformed().
        gameLoop = new Timer(16, this);
    }

    // Setter dan getter untuk menghubungkan Logic dengan class lain.
    public void setView(View view) { this.view = view; }
    public void setReturnToMenuAction(Runnable action) { returnToMenuAction = action; }
    public Player getPlayer() { return player; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }

    // Memulai permainan dari kondisi awal dan menjalankan timer.
    public void startGame() {
        resetGame();
        gameLoop.start();
        SoundManager.playBgm();
    }

    // Menghentikan timer dan BGM, misalnya ketika kembali ke menu.
    public void pauseGame() {
        gameLoop.stop();
        SoundManager.stopBgm();
    }

    // Kembali ke Main Menu hanya jika permainan sudah Game Over.
    public void returnToMenu() {
        if (!gameOver) return;
        pauseGame();
        if (returnToMenuAction != null) returnToMenuAction.run();
    }

    // Mengembalikan player, pipa, skor, dan status game ke kondisi awal.
    public void resetGame() {
        player = new Player(70, 250, 44, 31);
        pipes.clear();
        score = 0;
        gameOver = false;
        pipeCooldown = 0;

        // Memperbarui tampilan skor dan menggambar ulang panel jika sudah tersedia.
        if (view != null) {
            view.updateScore(score);
            view.repaint();
        }
    }

    // Membuat sepasang pipa dengan celah yang posisi vertikalnya acak.
    private void placePipe() {
        int gap = 170;
        int top = random.nextInt(191) + 70;
        int width = 52;

        // Pipa atas dimulai dari y = 0 hingga posisi top.
        Pipe upper = new Pipe(WIDTH, 0, width, top, true);

        // Pipa bawah dimulai setelah celah dan berakhir di batas tanah.
        Pipe lower = new Pipe(WIDTH, top + gap, width, GROUND - (top + gap), false);

        upper.setVelocityX(pipeVelocityX);
        lower.setVelocityX(pipeVelocityX);

        // Kedua pipa dimasukkan ke daftar obstacle.
        pipes.add(upper);
        pipes.add(lower);
    }

    // Memperbarui posisi player dan semua pipa pada setiap tick timer.
    private void move() {
        // Gravitasi menambah kecepatan jatuh player setiap frame.
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());

        // Menghitung waktu kemunculan pipa berikutnya.
        if (++pipeCooldown >= 90) {
            placePipe();
            pipeCooldown = 0;
        }

        // Menggeser semua pipa ke kiri.
        for (Pipe pipe : pipes) {
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());
        }

        // Skor bertambah saat sisi kanan pipa atas sudah melewati player.
        // Hanya pipa atas yang dihitung agar satu pasang pipa memberi satu poin.
        for (Pipe pipe : pipes) {
            if (pipe.isUpper() && !pipe.isPassed()
                    && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                pipe.setPassed(true);
                score++;
                SoundManager.playEffect("score.wav");
                if (view != null) view.updateScore(score);
            }
        }

        // Menghapus pipa yang sudah sepenuhnya keluar dari sisi kiri layar.
        pipes.removeIf(pipe -> pipe.getPosX() + pipe.getWidth() < 0);

        // Memeriksa apakah player menabrak pipa atau batas area permainan.
        checkCollision();
    }

    // Memeriksa tabrakan player dengan batas atas/bawah dan setiap pipa.
    private void checkCollision() {
        // Game Over jika player menyentuh tanah atau melewati batas atas.
        if (player.getPosY() + player.getHeight() >= GROUND || player.getPosY() <= 0) {
            endGame();
            return;
        }

        // Axis-Aligned Bounding Box (AABB) untuk mendeteksi irisan dua persegi.
        for (Pipe pipe : pipes) {
            boolean hit = player.getPosX() < pipe.getPosX() + pipe.getWidth()
                    && player.getPosX() + player.getWidth() > pipe.getPosX()
                    && player.getPosY() < pipe.getPosY() + pipe.getHeight()
                    && player.getPosY() + player.getHeight() > pipe.getPosY();

            if (hit) {
                endGame();
                return;
            }
        }
    }

    // Menghentikan permainan dan menampilkan status Game Over.
    private void endGame() {
        if (gameOver) return; // Mencegah proses Game Over dipanggil berulang kali.

        gameOver = true;
        gameLoop.stop();
        SoundManager.stopBgm();
        SoundManager.playEffect("gameover.wav");

        if (view != null) view.repaint();
    }

    // Dipanggil otomatis oleh Timer setiap 16 ms.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) move();
        if (view != null) view.repaint();
    }

    // Mengolah tombol keyboard yang ditekan pemain.
    @Override
    public void keyPressed(KeyEvent e) {
        // SPACE memberi dorongan ke atas dengan kecepatan negatif.
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            player.setVelocityY(jumpStrength);
            SoundManager.playEffect("jump.wav");

        // R memulai ulang permainan setelah Game Over.
        } else if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            startGame();

        // M mengembalikan pemain ke Main Menu setelah Game Over.
        } else if (e.getKeyCode() == KeyEvent.VK_M && gameOver) {
            returnToMenu();
        }
    }

    // Method wajib dari KeyListener; tidak digunakan dalam game ini.
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
