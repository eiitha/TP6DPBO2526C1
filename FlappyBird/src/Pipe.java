/**
 * Merepresentasikan satu objek pipa (obstacle) di dalam permainan.
 * Sepasang pipa atas dan bawah membentuk satu celah yang harus dilewati player.
 */
public class Pipe {
    // Posisi kiri-atas, ukuran, dan kecepatan horizontal pipa.
    private int posX, posY, width, height, velocityX;

    // Menandai apakah pipa sudah dilewati untuk mencegah skor dihitung berulang.
    private boolean passed;

    // true berarti pipa atas, false berarti pipa bawah.
    private final boolean upper;

    // Membuat pipa dengan posisi, ukuran, dan jenis yang ditentukan.
    public Pipe(int x, int y, int w, int h, boolean upper) {
        posX = x;
        posY = y;
        width = w;
        height = h;
        this.upper = upper;
        velocityX = -4; // Nilai negatif membuat pipa bergerak ke kiri.
    }

    // Getter dan setter digunakan Logic dan View untuk membaca/mengubah data pipa.
    public int getPosX() { return posX; }
    public void setPosX(int v) { posX = v; }

    public int getPosY() { return posY; }
    public void setPosY(int v) { posY = v; }

    public int getWidth() { return width; }
    public void setWidth(int v) { width = v; }

    public int getHeight() { return height; }
    public void setHeight(int v) { height = v; }

    public int getVelocityX() { return velocityX; }
    public void setVelocityX(int v) { velocityX = v; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean v) { passed = v; }

    public boolean isUpper() { return upper; }
}
