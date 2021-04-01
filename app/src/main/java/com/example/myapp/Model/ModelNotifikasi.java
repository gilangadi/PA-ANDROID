package com.example.myapp.Model;

public class ModelNotifikasi {

    private int id;
    private int user_id;
    private String isi_notifikasi;
    private String tanggal;

    public ModelNotifikasi(int id, int user_id, String isi_notifikasi, String tanggal) {
        this.id = id;
        this.user_id = user_id;
        this.isi_notifikasi = isi_notifikasi;
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIsi_notifikasi() {
        return isi_notifikasi;
    }

    public void setIsi_notifikasi(String isi_notifikasi) {
        this.isi_notifikasi = isi_notifikasi;
    }
}
