package com.example.myapp.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.SharedPrefmanager;
import com.example.myapp.Utils.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailstatuspinjamActivity extends AppCompatActivity {

    public static final int PERMISSION_CODE = 42042;
    public static final String URLs = Api.API_BASE_URL + "/pengembalian";
    private final static int REQUEST_CODE = 42;
    boolean isNotificActive = false;
    int notifID = 33;
    String idUser = "";
    String Idrak = "";
    int id_user;
    private TextView
            judul,
            ditulis_oleh,
            tahunterbit,
            edisi,
            nopanggil,
            isbn_issn,
            subjek,
            klasifikasi,
            judulseri,
            gmd,
            bahasa,
            tempatterbit,
            penerbit,
            abstrak,
            textViewCreated_at;
    private ImageView jimg;
    private Context mContext;
    private Activity mActivity;
    private LinearLayout mCLayout;
    private Button Buttonpdf;
    private Button mButton;
    private Button buttonKoleksi;
    private Button alertButton;
    private String UrlGambar;
    private String UrlPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailstatuspinjam);

        judul = (TextView) findViewById(R.id.judul_detail_status_pinjam_pengguna);
        ditulis_oleh = (TextView) findViewById(R.id.ditulis_oleh_detail_status_pinjam_pengguna);
        tahunterbit = (TextView) findViewById(R.id.tahun_terbit_detail_status_pinjam_pengguna);
        edisi = (TextView) findViewById(R.id.edisi_detail_status_pinjam_pengguna);
        nopanggil = (TextView) findViewById(R.id.no_panggil_detail_status_pinjam_pengguna);
        isbn_issn = (TextView) findViewById(R.id.isbn_detail_status_pinjam_pengguna);
        subjek = (TextView) findViewById(R.id.subjek_detail_status_pinjam_pengguna);
        klasifikasi = (TextView) findViewById(R.id.klasifikasi_detail_status_pinjam_pengguna);
        judulseri = (TextView) findViewById(R.id.judul_seri_detail_status_pinjam_pengguna);
        gmd = (TextView) findViewById(R.id.gmd_detail_status_pinjam_pengguna);
        bahasa = (TextView) findViewById(R.id.bahasa_detail_status_pinjam_pengguna);
        penerbit = (TextView) findViewById(R.id.penerbit_detail_status_pinjam_pengguna);
        tempatterbit = (TextView) findViewById(R.id.tempat_terbit_detail_status_pinjam_pengguna);
        abstrak = (TextView) findViewById(R.id.abstrak_detail_status_pinjam_pengguna);
        textViewCreated_at = (TextView) findViewById(R.id.tanggal_pinjam_detail_pengguna);
        jimg = (ImageView) findViewById(R.id.image_detail_status_pinjam_pengguna);

        //pasing detail pinjam
        final Intent intent = getIntent();
        id_user = intent.getIntExtra("Id_user", 0);
        String Judul = intent.getStringExtra("Judul");
        String Ditulis_oleh = intent.getStringExtra("Ditulis_oleh");
        String Tahun_terbit = intent.getStringExtra("Tahun_terbit");
        final String Urlimages = intent.getStringExtra("Urlimages");
        final String Urlpdf = intent.getStringExtra("Urlpdf");
        String Edisi = intent.getStringExtra("Edisi");
        String No_panggil = intent.getStringExtra("No_panggil");
        String Isbn_issn = intent.getStringExtra("Isbn_issn");
        String Subjek = intent.getStringExtra("Subjek");
        String Klasifikasi = intent.getStringExtra("Klasifikasi");
        String Judul_seri = intent.getStringExtra("Judul_seri");
        String Gmd = intent.getStringExtra("Gmd");
        String Bahasa = intent.getStringExtra("Bahasa");
        String Penerbit = intent.getStringExtra("Penerbit");
        String Tempat_terbit = intent.getStringExtra("Tempat_terbit");
        String Abstrak = intent.getStringExtra("Abstrak");
        String Tanggal_pinjam = intent.getStringExtra("Created_at");

        Idrak = String.valueOf(intent.getIntExtra("Id", 0));
        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailstatuspinjamActivity.this).getId());

        Toast.makeText(DetailstatuspinjamActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();
        UrlGambar = (Api.BASE_URL + "/uploads/" + Urlimages);
        UrlPdf = (Api.BASE_URL + "/pdf/" + Urlpdf);

        judul.setText(Judul);
        ditulis_oleh.setText(Ditulis_oleh);
        edisi.setText(Edisi);
        tahunterbit.setText(Tahun_terbit);
        nopanggil.setText(No_panggil);
        isbn_issn.setText(Isbn_issn);
        subjek.setText(Subjek);
        klasifikasi.setText(Klasifikasi);
        judulseri.setText(Judul_seri);
        gmd.setText(Gmd);
        bahasa.setText(Bahasa);
        penerbit.setText(Penerbit);
        tempatterbit.setText(Tempat_terbit);
        abstrak.setText(Abstrak);
        textViewCreated_at.setText(Tanggal_pinjam);

        Glide.with(this)
                .load(Api.BASE_URL + "/uploads/" + Urlimages)
                .centerCrop()
                .fitCenter()
                .into(jimg);

        mContext = getApplicationContext();
        mActivity = DetailstatuspinjamActivity.this;

    }


    public void parseJson(String jsonStr) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);
            if (jsonObj.get("result").equals("result")) {
            } else {
            }
        } catch (JSONException e) {
        }
    }
}