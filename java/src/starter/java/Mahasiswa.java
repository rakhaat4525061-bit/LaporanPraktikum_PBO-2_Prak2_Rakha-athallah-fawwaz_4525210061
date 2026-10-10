/**
 * Sesi 2 — enkapsulasi yang menjaga invariant.
 *
 * Deskripsi masalah:
 *   "Sistem akademik mencatat mahasiswa dengan NIM, nama, dan tiga komponen
 *    nilai: tugas, UTS, dan UAS. NIM tidak pernah berubah setelah mahasiswa
 *    terdaftar. Setiap komponen nilai berada dalam rentang 0 sampai 100.
 *    Nilai akhir dihitung 30% tugas, 30% UTS, 40% UAS."
 */
public class Mahasiswa {

    // Konstanta bobot — jangan menulis angka 0.30 dan 0.40 di dalam method.
    public static final double BOBOT_TUGAS = 0.30;
    public static final double BOBOT_UTS   = 0.30;
    public static final double BOBOT_UAS   = 0.40;

    private static final double NILAI_MIN = 0;
    private static final double NILAI_MAX = 100;

    // TODO 1: nim dan nama tidak berubah -> final. Nilai boleh berubah.
    private final String nim;
    private final String nama;
    private double nilaiTugas;
    private double nilaiUts;
    private double nilaiUas;

    public Mahasiswa(String nim, String nama, double nilaiTugas, double nilaiUts, double nilaiUas) {
        // TODO 2: tolak NIM null atau kosong
        if (nim == null || nim.trim().isEmpty()) {
            throw new IllegalArgumentException("NIM tidak boleh null atau kosong");
        }

        // TODO 3: tolak nilai di luar rentang 0-100
        pastikanNilaiSah("nilaiTugas", nilaiTugas);
        pastikanNilaiSah("nilaiUts", nilaiUts);
        pastikanNilaiSah("nilaiUas", nilaiUas);

        this.nim = nim;
        this.nama = nama;
        this.nilaiTugas = nilaiTugas;
        this.nilaiUts = nilaiUts;
        this.nilaiUas = nilaiUas;
    }

    // TODO 4: method privat pembantu validasi satu komponen nilai
    private static void pastikanNilaiSah(String namaKomponen, double nilai) {
        // isNaN dicek terpisah karena perbandingan apa pun dengan NaN selalu false
        if (Double.isNaN(nilai) || nilai < NILAI_MIN || nilai > NILAI_MAX) {
            throw new IllegalArgumentException(
                    namaKomponen + " harus berada dalam rentang " + (int) NILAI_MIN
                            + " sampai " + (int) NILAI_MAX + ", tetapi diterima: " + nilai);
        }
    }

    /** TODO 5: hitung nilai akhir memakai konstanta bobot. */
    public double nilaiAkhir() {
        return BOBOT_TUGAS * nilaiTugas
                + BOBOT_UTS * nilaiUts
                + BOBOT_UAS * nilaiUas;
    }

    /** TODO 6: huruf mutu berdasarkan nilai akhir. */
    public String hurufMutu() {
        double akhir = nilaiAkhir();
        if (akhir >= 80) return "A";
        if (akhir >= 70) return "B";
        if (akhir >= 60) return "C";
        if (akhir >= 50) return "D";
        return "E";
    }

    // TODO 7: getter (tanpa setNim() karena NIM tidak boleh berubah)
    public String getNim()  { return nim; }
    public String getNama() { return nama; }
    public double getNilaiAkhir() { return nilaiAkhir(); }

    @Override
    public String toString() {
        return String.format("%-10s %-18s akhir=%6.2f  mutu=%s",
                nim, nama, nilaiAkhir(), hurufMutu());
    }

    // Demo singkat (opsional, boleh dihapus)
    public static void main(String[] args) {
        Mahasiswa m = new Mahasiswa("4525210061", "Rakha Athallah", 85, 78, 90);
        System.out.println(m);

        try {
            new Mahasiswa("", "Tanpa NIM", 80, 80, 80);
        } catch (IllegalArgumentException e) {
            System.out.println("Ditolak: " + e.getMessage());
        }

        try {
            new Mahasiswa("123", "Nilai Salah", 80, 105, 80);
        } catch (IllegalArgumentException e) {
            System.out.println("Ditolak: " + e.getMessage());
        }
    }
}