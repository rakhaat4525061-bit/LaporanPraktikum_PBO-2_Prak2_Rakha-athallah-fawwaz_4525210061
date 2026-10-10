<?php
declare(strict_types=1);

/**
 * Sesi 2 — enkapsulasi yang menjaga invariant (PHP).
 * Bandingkan baris demi baris dengan java/Mahasiswa.java.
 */
class Mahasiswa
{
    public const float BOBOT_TUGAS = 0.30;
    public const float BOBOT_UTS   = 0.30;
    public const float BOBOT_UAS   = 0.40;

    private const float NILAI_MIN = 0.0;
    private const float NILAI_MAX = 100.0;

    /**
     * Constructor property promotion (PHP 8):
     * readonly adalah padanan `final` pada atribut Java.
     *
     * TODO 1: nim dan nama tidak berubah -> readonly.
     *         Komponen nilai boleh diperbarui -> tidak readonly.
     */
    public function __construct(
        private readonly string $nim,
        private readonly string $nama,
        private float $nilaiTugas,
        private float $nilaiUts,
        private float $nilaiUas,
    ) {
        // TODO 2: tolak NIM kosong (setelah di-trim)
        if (trim($nim) === '') {
            throw new InvalidArgumentException('NIM tidak boleh kosong');
        }

        // TODO 3: tolak komponen nilai di luar rentang 0-100
        self::pastikanNilaiSah('nilaiTugas', $nilaiTugas);
        self::pastikanNilaiSah('nilaiUts', $nilaiUts);
        self::pastikanNilaiSah('nilaiUas', $nilaiUas);
    }

    /**
     * TODO 4: validasi satu komponen nilai.
     */
    private static function pastikanNilaiSah(string $namaKomponen, float $nilai): void
    {
        // is_nan dicek terpisah karena perbandingan apa pun dengan NAN selalu false
        if (is_nan($nilai) || $nilai < self::NILAI_MIN || $nilai > self::NILAI_MAX) {
            throw new InvalidArgumentException(sprintf(
                '%s harus berada dalam rentang %d sampai %d, tetapi diterima: %s',
                $namaKomponen,
                (int) self::NILAI_MIN,
                (int) self::NILAI_MAX,
                (string) $nilai
            ));
        }
    }

    /** TODO 5: hitung nilai akhir memakai konstanta bobot. */
    public function nilaiAkhir(): float
    {
        return self::BOBOT_TUGAS * $this->nilaiTugas
             + self::BOBOT_UTS   * $this->nilaiUts
             + self::BOBOT_UAS   * $this->nilaiUas;
    }

    /** TODO 6: kembalikan huruf mutu. */
    public function hurufMutu(): string
    {
        $akhir = $this->nilaiAkhir();

        return match (true) {
            $akhir >= 80 => 'A',
            $akhir >= 70 => 'B',
            $akhir >= 60 => 'C',
            $akhir >= 50 => 'D',
            default      => 'E',
        };
    }

    // TODO 7: getter seperlunya. Tidak ada setNim() karena NIM tidak boleh berubah.
    public function getNim(): string  { return $this->nim; }
    public function getNama(): string { return $this->nama; }
    public function getNilaiAkhir(): float { return $this->nilaiAkhir(); }

    public function __toString(): string
    {
        return sprintf('%-10s %-18s akhir=%6.2f  mutu=%s',
            $this->nim, $this->nama, $this->nilaiAkhir(), $this->hurufMutu());
    }
}

// Demo singkat (opsional, boleh dihapus)
$m = new Mahasiswa('4525210061', 'Rakha Athallah', 85, 78, 90);
echo $m, PHP_EOL;

try {
    new Mahasiswa('', 'Tanpa NIM', 80, 80, 80);
} catch (InvalidArgumentException $e) {
    echo 'Ditolak: ', $e->getMessage(), PHP_EOL;
}

try {
    new Mahasiswa('123', 'Nilai Salah', 80, 105, 80);
} catch (InvalidArgumentException $e) {
    echo 'Ditolak: ', $e->getMessage(), PHP_EOL;
}