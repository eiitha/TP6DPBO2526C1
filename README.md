# Flappy Bird — Java Swing

## Janji
Saya Jillena Surbakti dengan NIM 2501485 mengerjakan Tugas Praktikum 6 pada Mata Kuliah Desain dan Pemrograman Berorientasi Objek (DPBO) untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin

## 1. Deskripsi Program

Flappy Bird merupakan permainan sederhana yang dikembangkan menggunakan bahasa pemrograman Java dengan library Java Swing. Pemain mengendalikan seekor burung untuk melewati rintangan berupa pipa yang bergerak dari kanan ke kiri.

Program ini dikembangkan dengan melengkapi fitur permainan Flappy Bird, meliputi deteksi tabrakan, Game Over, restart permainan, sistem skor, Main Menu, serta audio berupa Background Music (BGM) dan Sound Effect.

## 2. Desain Program

Program menggunakan pendekatan **Object-Oriented Programming (OOP)** dengan membagi fungsi permainan ke dalam beberapa class.

### Class dan Fungsinya

| Class | Deskripsi |
|---|---|
| `App.java` | Menginisialisasi JFrame, menampilkan Main Menu, serta mengatur perpindahan antara menu dan permainan. |
| `Player.java` | Mengatur objek burung, termasuk posisi, ukuran, dan pergerakannya. |
| `Pipe.java` | Mengatur objek pipa sebagai obstacle dalam permainan. |
| `View.java` | Menampilkan background, burung, pipa, skor, dan tampilan Game Over. |
| `Logic.java` | Mengatur logika permainan, timer, gravitasi, pergerakan pipa, deteksi tabrakan, dan perhitungan skor. |
| `SoundManager.java` | Mengatur pemutaran BGM dan Sound Effect menggunakan Java Sound API. |

### Struktur Folder

```text
FlappyBird
├── src
│   ├── assets
│   │   ├── background.png
│   │   ├── bird.png
│   │   ├── upperPipe.png
│   │   ├── lowerPipe.png
│   │   ├── bgm.wav
│   │   ├── jump.wav
│   │   ├── score.wav
│   │   └── gameover.wav
│   │
│   ├── App.java
│   ├── Logic.java
│   ├── Pipe.java
│   ├── Player.java
│   ├── SoundManager.java
│   └── View.java
│
├── Dokumentasi
│   └── Gameplay.mp4
│
└── README.md
```

## 3. Fitur Permainan

1. **Main Menu**  
   Tampilan awal permainan menyediakan tombol START GAME untuk memulai permainan dan EXIT untuk keluar dari aplikasi.

2. **Player Movement**  
   Pemain dapat mengendalikan burung menggunakan tombol SPACE pada keyboard. Burung bergerak mengikuti efek gravitasi.

3. **Pipe Obstacle**  
   Pipa muncul sebagai rintangan dan bergerak dari kanan ke kiri dengan posisi celah yang bervariasi.

4. **Collision Detection**  
   Permainan akan berakhir apabila burung menabrak pipa atau jatuh melewati batas bawah area permainan.

5. **Game Over**  
   Ketika terjadi tabrakan atau burung jatuh, permainan dihentikan dan tampilan Game Over ditampilkan.

6. **Restart Game**  
   Pemain dapat memulai ulang permainan dengan menekan tombol R pada keyboard setelah Game Over.

7. **Score System**  
   Skor ditampilkan menggunakan JLabel dan bertambah satu poin setiap kali burung berhasil melewati pipa.

8. **Audio**  
   Permainan dilengkapi Background Music dan Sound Effect untuk menambah pengalaman bermain.

## 4. Penjelasan Alur Program

1. Program dijalankan melalui class `App.java`.
2. JFrame dibuat dan Main Menu ditampilkan.
3. Pemain memilih tombol START GAME untuk memulai permainan.
4. Class `Logic.java` menginisialisasi player, obstacle pipa, timer, dan skor.
5. Timer menjalankan proses permainan secara berkala.
6. Burung bergerak mengikuti gravitasi dan input keyboard dari pemain.
7. Pipa bergerak dari kanan ke kiri dan ditampilkan melalui class `View.java`.
8. Program memeriksa tabrakan antara burung dengan pipa serta kondisi burung terhadap batas area permainan.
9. Jika burung berhasil melewati pipa, skor bertambah satu poin.
10. Jika burung menabrak pipa atau jatuh, permainan berhenti dan Game Over ditampilkan.
11. Pemain dapat menekan R untuk restart atau memilih kembali ke Main Menu.
12. Pemain dapat keluar dari permainan melalui tombol EXIT.

## 5. Kontrol Permainan

| Tombol | Fungsi |
|---|---|
| `SPACE` | Membuat burung terbang |
| `R` | Restart permainan setelah Game Over |
| `M` | Kembali ke Main Menu setelah Game Over |

## 6. Dokumentasi Program

### Gameplay


https://github.com/user-attachments/assets/ddd0dfe0-7049-4f73-9fb7-62985f96ced7
