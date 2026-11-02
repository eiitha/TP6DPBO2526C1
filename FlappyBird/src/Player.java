/**
 * Merepresentasikan karakter burung yang dikendalikan pemain.
 * Class ini menyimpan data posisi, ukuran, dan kecepatan vertikal player.
 */
public class Player {
    private int posX, posY, width, height, velocityY;

    // Membuat player pada koordinat (x, y) dengan ukuran tertentu.
    public Player(int x, int y, int w, int h) {
        posX = x;
        posY = y;
        width = w;
        height = h;
        velocityY = 0; // Awalnya player tidak bergerak secara vertikal.
    }

    // Getter dan setter agar posisi serta kecepatan dapat diakses Logic dan View.
    public int getPosX() { return posX; }
    public void setPosX(int v) { posX = v; }

    public int getPosY() { return posY; }
    public void setPosY(int v) { posY = v; }

    public int getWidth() { return width; }
    public void setWidth(int v) { width = v; }

    public int getHeight() { return height; }
    public void setHeight(int v) { height = v; }

    public int getVelocityY() { return velocityY; }
    public void setVelocityY(int v) { velocityY = v; }
}
