package com.example.myapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.SharedPrefmanager;
import com.example.myapp.Utils.Api;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailStatusKembaliAdminActivity extends AppCompatActivity {

    public static final int PERMISSION_CODE = 42042;
    public static final String URLs = Api.API_BASE_URL + "/pengembalian";
    private final static int REQUEST_CODE = 42;
    boolean isNotificActive = false;
    int notifID = 33;
    String idUser = "";
    String Idrak = "";
    private TextView judul,
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
            textViewNama,
            textViewNim,
            textViewProdi,
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
        setContentView(R.layout.activity_detail_status_kembali_admin);

        jimg = (ImageView) findViewById(R.id.image_detail_status_admin_kembali);
        judul = (TextView) findViewById(R.id.judul_detail_status_admin_kembali);
        ditulis_oleh = (TextView) findViewById(R.id.ditulis_detail_status_admin_kembali);
        tahunterbit = (TextView) findViewById(R.id.tahun_terbit_detail_status_admin_kembali);
        edisi = (TextView) findViewById(R.id.edisi_detail_status_admin_kembali);
        nopanggil = (TextView) findViewById(R.id.no_panggil_detail_status_admin_kembali);
        isbn_issn = (TextView) findViewById(R.id.isbn_detail_status_admin_kembali);
        subjek = (TextView) findViewById(R.id.subjek_detail_status_admin_kembali);
        klasifikasi = (TextView) findViewById(R.id.klasifikasi_detail_status_admin_kembali);
        judulseri = (TextView) findViewById(R.id.judul_seri_detail_status_admin_kembali);
        gmd = (TextView) findViewById(R.id.gmd_detail_status_admin_kembali);
        bahasa = (TextView) findViewById(R.id.bahasa_detail_status_admin_kembali);
        penerbit = (TextView) findViewById(R.id.penerbit_detail_status_admin_kembali);
        tempatterbit = (TextView) findViewById(R.id.tempat_terbit_detail_status_admin_kembali);
        abstrak = (TextView) findViewById(R.id.abstrak_detail_status_admin_kembali);
        textViewNama = (TextView) findViewById(R.id.nama_peminjam_detail_status_admin_kembali);
        textViewNim = (TextView) findViewById(R.id.nim_peminjam_detail_status_admin_kembali);
        textViewProdi = (TextView) findViewById(R.id.prodi_peminjam_detail_status_admin_kembali);
        textViewCreated_at = (TextView) findViewById(R.id.tanggal_peminjam_detail_status_admin_kembali);

        //pasing detail jurnal
        Intent intent = getIntent();
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

        String Nama = intent.getStringExtra("Nama");
        String Nim = intent.getStringExtra("Nim");
        String Prodi = intent.getStringExtra("Prodi");
        String Tanggal_pinjam = intent.getStringExtra("Created_at");

        Idrak = String.valueOf(intent.getIntExtra("Id", 0));
        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailStatusKembaliAdminActivity.this).getId());

        Toast.makeText(DetailStatusKembaliAdminActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();
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
        textViewNama.setText(Nama);
        textViewNim.setText(Nim);
        textViewProdi.setText(Prodi);
        textViewCreated_at.setText(Tanggal_pinjam);


        Glide.with(this)
                .load(Api.BASE_URL + "/uploads/" + Urlimages)
                .centerCrop()
                .fitCenter()
                .into(jimg);

        mContext = getApplicationContext();
        mActivity = DetailStatusKembaliAdminActivity.this;
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