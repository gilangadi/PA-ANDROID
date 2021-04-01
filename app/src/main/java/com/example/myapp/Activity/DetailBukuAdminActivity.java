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

public class DetailBukuAdminActivity extends AppCompatActivity {

    public static final int PERMISSION_CODE = 42042;
    private final static int REQUEST_CODE = 42;

//    public static final String URLs = Api.BASE_URL + "/api/pinjam";
    boolean isNotificActive = false;
    int notifID = 33;
    String idUser = "";
    String Idrak = "";
    private TextView judul,
            edisi,
            no_panggil,
            isbn_issn,
            ditulis_oleh,
            subjek,
            klasifikasi,
            judul_seri,
            gmd,
            bahasa,
            penerbit,
            tahun_terbit,
            tempat_terbit,
            abstrak;

    private ImageView jimg;
    private Context mContext;
    private Activity mActivity;
    private LinearLayout mCLayout;
    private Button Buttonpdf;
    private Button alertButton;
    private String UrlGambar;
    private String UrlPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku_admin);

        judul = (TextView) findViewById(R.id.judul_buku_detail_admin);
        ditulis_oleh = (TextView) findViewById(R.id.ditulis_oleh_buku_detail_admin);
        tahun_terbit = (TextView) findViewById(R.id.tahun_terbit_buku_detail_admin);
        edisi = (TextView) findViewById(R.id.edisi_buku_detail_admin);
        no_panggil = (TextView) findViewById(R.id.nopanggil_buku_detail_admin);
        isbn_issn = (TextView) findViewById(R.id.isbn_buku_detail_admin);
        subjek = (TextView) findViewById(R.id.subjek_buku_detail_admin);
        klasifikasi = (TextView) findViewById(R.id.klasifikasi_buku_detail_admin);
        judul_seri = (TextView) findViewById(R.id.judul_seri_buku_detail_admin);
        gmd = (TextView) findViewById(R.id.gmd_buku_detail_admin);
        bahasa = (TextView) findViewById(R.id.bahasa_buku_detail_admin);
        penerbit = (TextView) findViewById(R.id.penerbit_buku_detail_admin);
        tempat_terbit = (TextView) findViewById(R.id.tempat_ternit_buku_detail_admin);
        abstrak = (TextView) findViewById(R.id.abstrak_buku_detail_admin);
        jimg = (ImageView) findViewById(R.id.image_buku_detail_admin);

        //pasing detail jurnal
        final Intent intent = getIntent();
        String Judul = intent.getStringExtra("Judul");
        String Ditulis_oleh = intent.getStringExtra("Ditulis_oleh");
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
        String Tahun_terbit = intent.getStringExtra("Tahun_terbit");
        final String Urlimages = intent.getStringExtra("Urlimages");
        final String Urlpdf = intent.getStringExtra("Urlpdf");
        Idrak = String.valueOf(intent.getIntExtra("Id", 0));
        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailBukuAdminActivity.this).getId());
        Toast.makeText(DetailBukuAdminActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();

        UrlGambar = (Api.BASE_URL + "/uploads/" + Urlimages);
        UrlPdf = (Api.BASE_URL + "/pdf/" + Urlpdf);

        judul.setText(Judul);
        ditulis_oleh.setText(Ditulis_oleh);
        edisi.setText(Edisi);
        no_panggil.setText(No_panggil);
        isbn_issn.setText(Isbn_issn);
        subjek.setText(Subjek);
        klasifikasi.setText(Klasifikasi);
        judul_seri.setText(Judul_seri);
        gmd.setText(Gmd);
        bahasa.setText(Bahasa);
        penerbit.setText(Penerbit);
        tempat_terbit.setText(Tempat_terbit);
        abstrak.setText(Abstrak);
        tahun_terbit.setText(Tahun_terbit);
        Glide.with(this)
                .load(Api.BASE_URL + "/uploads/" + Urlimages)
                .centerCrop()
                .fitCenter()
                .into(jimg);

        mContext = getApplicationContext();
        mActivity = DetailBukuAdminActivity.this;

        Button mButton = (Button) findViewById(R.id.button_pinjam_buku_admin_admin);
        Buttonpdf = (Button) findViewById(R.id.Button_pdf_admin);

        //OPEN PDF
        Buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlPdf));
                startActivity(fileIntent);
            }
        });

        //BUTOON HAPUS
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing a new alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Konfimasi hapus buku!");
                // Show a message on alert dialog
                builder.setMessage("Yakin untuk hapus buku!");
                // Set the positive button
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailBukuAdminActivity.this, "Anda menghapus buku!", Toast.LENGTH_SHORT).show();
                        hapusbuku(Idrak);

                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailBukuAdminActivity.this, "Anda membatalkan hapus buku!", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    //HAPUS
    private void hapusbuku(final String idrak) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String URLsHapus = Api.BASE_URL + "/api/rakadmin/" + idrak;
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, URLsHapus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
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