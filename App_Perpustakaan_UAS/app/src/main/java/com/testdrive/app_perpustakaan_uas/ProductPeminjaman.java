package com.testdrive.app_perpustakaan_uas;

public class ProductPeminjaman {
    private String kodepeminjam;
    private String nama;
    private String notelp;
    private String alamat;
    private String tglpinjam;
    private String kodebuku;
    private String tglkembali;
    private String status;

    public String getKodepeminjam() {
        return kodepeminjam;
    }

    public void setKodepeminjam(String kodepeminjam) {
        this.kodepeminjam = kodepeminjam;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTglpinjam() {
        return tglpinjam;
    }

    public void setTglpinjam(String tglpinjam) {
        this.tglpinjam = tglpinjam;
    }

    public String getKodebuku() {
        return kodebuku;
    }

    public void setKodebuku(String kodebuku) {
        this.kodebuku = kodebuku;
    }

    public String getTglkembali() {
        return tglkembali;
    }

    public void setTglkembali(String tglkembali) {
        this.tglkembali = tglkembali;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
