import javax.sound.sampled.*;

/**
 * Mengelola pemutaran BGM dan sound effect menggunakan Java Sound API.
 * Semua file audio dibaca dari folder src/assets.
 */
public final class SoundManager {
    // Clip khusus untuk musik latar agar dapat diputar berulang kali.
    private static Clip bgmClip;

    // Constructor dibuat private karena class ini hanya berisi method static.
    private SoundManager() {}

    // Membaca file WAV dan menyiapkannya sebagai Clip.
    private static Clip loadClip(String fileName) {
        try (AudioInputStream audio = AudioSystem.getAudioInputStream(
                new java.io.File("src/assets/" + fileName))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        } catch (Exception ex) {
            System.err.println("Gagal memuat audio " + fileName + ": " + ex.getMessage());
            return null;
        }
    }

    // Memulai BGM secara looping. Clip hanya dimuat sekali dan digunakan kembali.
    public static void playBgm() {
        if (bgmClip == null) bgmClip = loadClip("bgm.wav");
        if (bgmClip != null && !bgmClip.isRunning()) {
            bgmClip.setFramePosition(0);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Menghentikan BGM tanpa menghapus Clip dari memori.
    public static void stopBgm() {
        if (bgmClip != null && bgmClip.isRunning()) bgmClip.stop();
    }

    // Memuat dan memutar efek suara satu kali, misalnya jump, score, atau game over.
    public static void playEffect(String fileName) {
        Clip effect = loadClip(fileName);
        if (effect != null) {
            // Menutup Clip setelah selesai agar resource audio tidak terus terbuka.
            effect.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    effect.close();
                }
            });
            effect.start();
        }
    }

    // Menghentikan dan menutup seluruh resource audio saat aplikasi ditutup.
    public static void closeAll() {
        stopBgm();
        if (bgmClip != null) {
            bgmClip.close();
            bgmClip = null;
        }
    }
}
