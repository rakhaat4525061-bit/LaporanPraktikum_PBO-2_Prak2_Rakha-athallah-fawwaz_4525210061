# Sesi 2 — Enkapsulasi yang Menjaga Invariant (Mahasiswa)

Latihan kedua PBO: merancang kelas `Mahasiswa` yang **menjaga aturan datanya sendiri** (invariant) lewat enkapsulasi, validasi di constructor, dan atribut yang tidak dapat diubah. Diimplementasikan dalam **Java** dan **PHP**.

## Identitas

| Nama                  | NIM        |
|-----------------------|------------|
| Rakha Athallah Fawwaz | 4525210061 |

## Deskripsi Masalah

> Sistem akademik mencatat mahasiswa dengan NIM, nama, dan tiga komponen nilai: tugas, UTS, dan UAS. NIM tidak pernah berubah setelah mahasiswa terdaftar. Setiap komponen nilai berada dalam rentang 0 sampai 100. Nilai akhir dihitung 30% tugas, 30% UTS, 40% UAS.

## Invariant

| No | Invariant                                    | Cara Menjaganya                                              |
|----|----------------------------------------------|--------------------------------------------------------------|
| 1  | NIM tidak boleh null atau kosong             | Validasi di constructor, lempar exception jika dilanggar     |
| 2  | NIM tidak pernah berubah setelah terdaftar   | Atribut `final` (Java) / `readonly` (PHP), tanpa `setNim()`  |
| 3  | Setiap komponen nilai berada di rentang 0–100 | Method privat `pastikanNilaiSah()` dipanggil di constructor  |
| 4  | Bobot nilai akhir: 30% / 30% / 40%           | Konstanta `BOBOT_TUGAS`, `BOBOT_UTS`, `BOBOT_UAS`            |
| 5  | Nilai `NaN` tidak dianggap nilai yang sah    | Pemeriksaan `isNaN` / `is_nan` terpisah                      |

## Struktur Berkas

```
.
├── java/
│   └── Mahasiswa.java
├── php/
│   └── Mahasiswa.php
├── analisis.md
└── README.md
```

## Aturan Huruf Mutu

| Nilai Akhir | Huruf Mutu |
|-------------|------------|
| >= 80       | A          |
| >= 70       | B          |
| >= 60       | C          |
| >= 50       | D          |
| < 50        | E          |

## Cara Menjalankan

### Java

Prasyarat: JDK 8 atau lebih baru.

```bash
cd java
javac -d out Mahasiswa.java
java -cp out Mahasiswa
```

### PHP

Prasyarat: PHP 8.3 atau lebih baru (konstanta bertipe `const float`).
Untuk PHP 8.0–8.2, hapus kata `float` pada deklarasi konstanta.

```bash
cd php
php Mahasiswa.php
```

## Contoh Keluaran

**Java**

```
4525210061 Rakha Athallah     akhir= 84.90  mutu=A
Ditolak: NIM tidak boleh null atau kosong
Ditolak: nilaiUts harus berada dalam rentang 0 sampai 100, tetapi diterima: 105.0
```

**PHP**

```
4525210061 Rakha Athallah     akhir= 84.90  mutu=A
Ditolak: NIM tidak boleh kosong
Ditolak: nilaiUts harus berada dalam rentang 0 sampai 100, tetapi diterima: 105
```

Perhitungan: `0,30 × 85 + 0,30 × 78 + 0,40 × 90 = 25,5 + 23,4 + 36 = 84,9` → mutu **A**.

## Perbandingan Java vs PHP

| Konsep                    | Java                                   | PHP                                            |
|---------------------------|----------------------------------------|------------------------------------------------|
| Atribut tak berubah       | `private final String nim;`            | `private readonly string $nim`                 |
| Deklarasi atribut         | Dideklarasikan lalu diisi di constructor | Constructor property promotion (PHP 8)       |
| Exception validasi        | `IllegalArgumentException`             | `InvalidArgumentException`                     |
| Konstanta                 | `public static final double`           | `public const float`                           |
| Akses konstanta           | `BOBOT_TUGAS`                          | `self::BOBOT_TUGAS`                            |
| Method pembantu statis    | `pastikanNilaiSah(...)`                | `self::pastikanNilaiSah(...)`                  |
| Percabangan huruf mutu    | Rangkaian `if`                         | `match (true) { ... }`                         |
| Cek NaN                   | `Double.isNaN(x)`                      | `is_nan($x)`                                   |
| Representasi string       | `toString()`                           | `__toString()`                                 |

## Catatan Desain

- **Validasi di constructor**: objek yang tidak sah tidak pernah berhasil dibuat, sehingga setiap objek `Mahasiswa` yang ada pasti memenuhi invariant.
- **Tidak ada `setNim()`**: invariant "NIM tidak berubah" dijaga dengan tidak menyediakan jalan untuk mengubahnya.
- **Satu method pembantu**: `pastikanNilaiSah()` mencegah pemeriksaan rentang yang sama ditulis tiga kali.
- **Pesan error spesifik**: menyebut komponen mana yang salah dan nilai yang diterima, bukan sekadar "Error".
- **`NaN` dicek terpisah**: perbandingan apa pun dengan `NaN` bernilai `false`, sehingga tanpa pemeriksaan khusus ia lolos dari pengecekan rentang.