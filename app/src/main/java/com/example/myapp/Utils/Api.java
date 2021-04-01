package com.example.myapp.Utils;

import com.example.myapp.Model.MyResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    String BASE_URL = "http://ta.poliwangi.ac.id/~ti17189";
    String API_BASE_URL = BASE_URL + "/api";

    @Multipart
    @POST("rak")
    Call<MyResponse> upload(
            @Part("urlimages\"; filename=\"image.jpg\" ") RequestBody file,
            @Part("urlpdf\"; filename=\"pdf.pdf\" ") RequestBody filepdf,
            @Part("judul") RequestBody judul,
            @Part("ditulis_oleh") RequestBody ditulis_oleh,
            @Part("tahun_terbit") RequestBody tahun_terbit,
            @Part("status") RequestBody status,
            @Part("status_buku_jurnal") RequestBody status_buku_jurnal,
            @Part("stok") RequestBody stok,
            @Part("edisi") RequestBody edisi,
            @Part("no_panggil") RequestBody no_panggil,
            @Part("isbn_issn") RequestBody isbn_issn,
            @Part("subjek") RequestBody subjek,
            @Part("klasifikasi") RequestBody klasifikasi,
            @Part("judul_seri") RequestBody judul_seri,
            @Part("gmd") RequestBody gmd,
            @Part("bahasa") RequestBody bahasa,
            @Part("penerbit")RequestBody penerbit,
            @Part("tempat_terbit")RequestBody tempat_terbit,
            @Part("abstrak")RequestBody abstrak


//                    @Part("subjek") RequestBody subjek,
//                    @Part("klasifikasi") RequestBody klasifikasi,
//                    @Part("judul_seri") RequestBody judul_seri,
//                    @Part("gmd") RequestBody gmd,
//                    @Part("bahasa") RequestBody bahasa,
//                    @Part("tempat_terbit") RequestBody tempat_terbit,
//                    @Part("abstrak") RequestBody abstrak,
//                    @Part("penerbit") RequestBody penerbit

    );
}
