package com.example.myapp.Model;

public class ModelAdminStatusKembali {

    private int id;
    private int id_user;
    private int id_rak;
    private String judul;
    private String ditulis_oleh;
    private String tahun_terbit;
    private String urlimages;
    private String urlpdf;
    private String status_buku_jurnal;
    private String status;
    private int stok_tersisa;
    private String edisi;
    private String no_panggil;
    private String isbn_issn;
    private String subjek;
    private String klasifikasi;
    private String judul_seri;
    private String gmd;
    private String bahasa;
    private String tempat_terbit;
    private String penerbit;
    private String abstrak;
    private String nama;
    private String nim;
    private String prodi;
    private String created_at;

    public ModelAdminStatusKembali(int id,
                                   int id_user,
                                   int id_rak,
                                   String judul,
                                   String ditulis_oleh,
                                   String tahun_terbit,
                                   String urlimages,
                                   String urlpdf,
                                   String status_buku_jurnal,
                                   String status,
//                                   int stok_tersisa,
                                   String edisi,
                                   String no_panggil,
                                   String isbn_issn,
                                   String subjek,
                                   String klasifikasi,
                                   String judul_seri,
                                   String gmd,
                                   String bahasa,
                                   String tempat_terbit,
                                   String penerbit,
                                   String abstrak,
                                   String nama,
                                   String nim,
                                   String prodi,
                                   String created_at)
    {

        this.id = id;
        this.id_user = id_user;
        this.id_rak = id_rak;
        this.judul = judul;
        this.ditulis_oleh = ditulis_oleh;
        this.tahun_terbit = tahun_terbit;
        this.urlimages = urlimages;
        this.urlpdf = urlpdf;
        this.status_buku_jurnal = status_buku_jurnal;
        this.status = status;
//        this.stok_tersisa = stok_tersisa;
        this.edisi = edisi;
        this.no_panggil = no_panggil;
        this.isbn_issn = isbn_issn;
        this.subjek = subjek;
        this.klasifikasi = klasifikasi;
        this.judul_seri = judul_seri;
        this.gmd = gmd;
        this.bahasa = bahasa;
        this.tempat_terbit = tempat_terbit;
        this.penerbit = penerbit;
        this.abstrak = abstrak;
        this.status_buku_jurnal = status_buku_jurnal;
        this.nama = nama;
        this.nim = nim;
        this.prodi = prodi;
        this.created_at = created_at; }

    public int getId() {
        return id;
    }
    public int getId_user() {
        return id_user;
    }
    public int getId_rak() {
        return id_rak;
    }
    public String getJudul() {
        return judul;
    }
    public String getDitulis_oleh() {
        return ditulis_oleh;
    }
    public String getTahun_Terbit() {
        return tahun_terbit;
    }
    public String getUrlimages() {
        return urlimages;
    }
    public String getUrlpdf() {
        return urlpdf;
    }
    public String getStatus_buku_jurnal() {
        return status_buku_jurnal;
    }
    public String getStatus() {
        return status;
    }
    public String getEdisi() {
        return edisi;
    }
    public String getNo_panggil() {
        return no_panggil;
    }
    public String getIsbn_issn() {
        return isbn_issn;
    }
    public String getSubjek() {
        return subjek;
    }
    public String getKlasifikasi() {
        return klasifikasi;
    }
    public String getJudul_seri() {
        return judul_seri;
    }
    public String getGmd() {
        return gmd;
    }
    public String getBahasa() {
        return bahasa;
    }
    public String getTempat_terbit() {
        return tempat_terbit;
    }
    public String getPenerbit() {
        return penerbit;
    }
    public String getAbstrak() {
        return abstrak;
    }
//    public int getStok_tersisa() {
//        return stok_tersisa;
//    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public String getProdi() {
        return prodi;
    }

    public String getCreated_at() {
        return created_at;
    }


}
