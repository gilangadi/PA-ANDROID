package com.example.myapp.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class DetailBukuActivity extends AppCompatActivity {

    public static final int PERMISSION_CODE = 42042;
    public static final String URLs = Api.API_BASE_URL + "/pinjam";
    public static final String URLsKoleksijurnal = Api.API_BASE_URL + "/koleksibuku";
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
            abstrak;


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
        setContentView(R.layout.activity_detail_buku);

        judul = (TextView) findViewById(R.id.judul_buku_detail);
        ditulis_oleh = (TextView) findViewById(R.id.ditulis_oleh_buku_detail);
        tahunterbit = (TextView) findViewById(R.id.tahun_terbit_buku_detail);
        jimg = (ImageView) findViewById(R.id.image_buku_detail);
        edisi = (TextView) findViewById(R.id.edisi_buku_detail);
        nopanggil = (TextView) findViewById(R.id. no_panggil_buku_detail);
        isbn_issn = (TextView) findViewById(R.id. isbn_buku_detail);
        subjek = (TextView) findViewById(R.id. subyek_buku_detail);
        klasifikasi = (TextView) findViewById(R.id. klasifikasi_buku_detail);
        judulseri = (TextView) findViewById(R.id. judul_seri_buku_detail);
        gmd = (TextView) findViewById(R.id. gmd_buku_detail);
        bahasa = (TextView) findViewById(R.id. bahasa_buku_detail);
        penerbit = (TextView) findViewById(R.id. penerbit_buku_detail);
        tempatterbit = (TextView) findViewById(R.id. tempat_terbit_buku_detail);
        abstrak = (TextView) findViewById(R.id. abstrak_buku_detail);

        //pasing detail jurnal
        final Intent intent = getIntent();
//        Intent intent = getIntent();
        String Judul = intent.getStringExtra("Judul");
        String Ditulis_oleh = intent.getStringExtra("Ditulis_oleh");
        String Tahun_terbit = intent.getStringExtra("Tahun_terbit");
        final String Urlimages = intent.getStringExtra("Urlimages");
        final String Urlpdf = intent.getStringExtra("Urlpdf");
        int stok_tersisa = intent.getIntExtra("stok_tersisa",0);
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


        Idrak = String.valueOf(intent.getIntExtra("Id", 0));
        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailBukuActivity.this).getId());
//        Toast.makeText(DetailBukuActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();
//
//        UrlGambar = (Api.API_BASE_URL + "/uploads/" + Urlimages);
//        UrlPdf = (Api.API_BASE_URL + "/pdf/" + Urlpdf);

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

        Glide.with(this)
//                .load(Api.API_BASE_URL + "/uploads/" + Urlimages)
                .load(Api.BASE_URL + "/uploads/" + Urlimages)
                .centerCrop()
                .fitCenter()
                .into(jimg);

        mContext = getApplicationContext();
        mActivity = DetailBukuActivity.this;

        mButton = (Button) findViewById(R.id.button_pinjam_buku);
        buttonKoleksi = (Button) findViewById(R.id.button_koleksi_buku);
        Buttonpdf = (Button) findViewById(R.id.Button_pdf);

//        mButton.setVisibility(View.INVISIBLE);
        if(stok_tersisa > 0){
            mButton.setText("PINJAM");
        }else{
            mButton.setText("STOK HABIS !");
        }

        //OPEN PDF
        Buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlPdf));
                startActivity(fileIntent);
            }
        });

        //BUTTON KOLEKSI
        buttonKoleksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                koleksibuku(idUser, Idrak);
            }
        });

        //BUTOON PINJAM
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(stok_tersisa > 0){
                    // Initializing a new alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setTitle("Konfimasi peminjaman!");
                    // Show a message on alert dialog
                    builder.setMessage("Buku pinjaman akan tersimpan pada rak!");
                    // Set the positive button
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(DetailBukuActivity.this, "Anda melanjudkan peminjaman!", Toast.LENGTH_SHORT).show();
                            transaksi(idUser, Idrak);

//                        scheduleNotification(getNotification("Anda telah melewati batas pengambilan buku"), 6000);
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DetailBukuActivity.this, "Anda membatalkan peminjaman!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Create the alert dialog
                    AlertDialog dialog = builder.create();
                    // Finally, display the alert dialog
                    dialog.show();
                    // Get the alert dialog buttons reference
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    // Change the alert dialog buttons text and background color
                    positiveButton.setTextColor(Color.parseColor("#2574A9"));
//                positiveButton.setBackgroundColor(Color.parseColor("#FFD9E9FF"));
                    negativeButton.setTextColor(Color.parseColor("#2574A9"));
//                negativeButton.setBackgroundColor(Color.parseColor("#FFD9E9FF"));
                }else{
                    Toast.makeText(DetailBukuActivity.this, "Stok buku ini telah habis!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    //CREATE KOLEKSI
    public void koleksibuku(final String id_user, final String id_rak) {
        RequestQueue queue = Volley.newRequestQueue(DetailBukuActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLsKoleksijurnal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        Toast.makeText(DetailBukuActivity.this, "Anda  menambahkan koleksi buku!", Toast.LENGTH_SHORT).show();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                        Toast.makeText(DetailBukuActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);
                params.put("id_rak", id_rak);
                return params;
            }
        };
        queue.add(postRequest);
    }


    //CREATE PINJAM
    public void transaksi(final String id_user, final String id_rak) {
        RequestQueue queue = Volley.newRequestQueue(DetailBukuActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        Toast.makeText(DetailBukuActivity.this, "Anda melanjudkan peminjaman!", Toast.LENGTH_SHORT).show();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                        Toast.makeText(DetailBukuActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);
                params.put("id_rak", id_rak);
                return params;
            }
        };
        queue.add(postRequest);
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