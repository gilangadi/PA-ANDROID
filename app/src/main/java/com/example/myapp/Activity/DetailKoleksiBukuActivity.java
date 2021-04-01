package com.example.myapp.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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

public class DetailKoleksiBukuActivity extends AppCompatActivity {

    public static final String URLs = Api.API_BASE_URL + "/pinjam";
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
    private Button mButton;
    private Button Buttonpdf;
    private String UrlGambar;
    private String UrlPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_koleksi_buku);

        createNotivicationChanel();

        judul = (TextView) findViewById(R.id.judul_koleksi);
        ditulis_oleh = (TextView) findViewById(R.id.ditulis_oleh_koleksi);
        tahun_terbit = (TextView) findViewById(R.id.tahun_terbit_koleksi);
        edisi = (TextView) findViewById(R.id.edisi_koleksi);
        no_panggil = (TextView) findViewById(R.id.no_panggil_koleksi);
        isbn_issn = (TextView) findViewById(R.id.isbn_koleksi);
        subjek = (TextView) findViewById(R.id.subyek_koleksi);
        klasifikasi = (TextView) findViewById(R.id.klasifikasi_koleksi);
        judul_seri = (TextView) findViewById(R.id.judul_seri_koleksi);
        gmd = (TextView) findViewById(R.id.gmd_koleksi);
        bahasa = (TextView) findViewById(R.id.bahasa_koleksi);
        penerbit = (TextView) findViewById(R.id.penerbit_koleksi);
        tempat_terbit = (TextView) findViewById(R.id.tempat_terbit_koleksi);
        abstrak = (TextView) findViewById(R.id.abstrak_koleksi);
        jimg = (ImageView) findViewById(R.id.image_koleksi);

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

        Idrak = String.valueOf(intent.getIntExtra("Id_rak", 0));
        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailKoleksiBukuActivity.this).getId());
        Toast.makeText(DetailKoleksiBukuActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();

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
        mActivity = DetailKoleksiBukuActivity.this;

        mButton = (Button) findViewById(R.id.button_pinjam_bukunya);
        Buttonpdf = (Button) findViewById(R.id.Button_pdf);

        //OPEN PDF
        Buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlPdf));
                startActivity(fileIntent);
            }
        });

//        BUTOON PINJAM
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing a new alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Konfimasi peminjaman!");
                // Show a message on alert dialog
                builder.setMessage("Buku pinjaman akan tersimpan pada rak!");
                // Set the positive button
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailKoleksiBukuActivity.this, "Anda melanjudkan peminjaman!", Toast.LENGTH_SHORT).show();
                        transaksi(idUser, Idrak);

//                        //NOTIVIKASI PEMINJAMAN
//                        Intent intent = new Intent(Detail_Koleksi_Buku_Activity.this,ReminderBroadcastReceiver.class);
//                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Detail_Koleksi_Buku_Activity.this, 0,intent,0);
//                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                        long timeButtononClick = System.currentTimeMillis();
//                        long tenSeconInmillis = 10 * 5;
//                        Log.d("waktunya",""+timeButtononClick + tenSeconInmillis);
//                        alarmManager.set(AlarmManager.RTC_WAKEUP,
//                                15000,
//                                pendingIntent);
//
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailKoleksiBukuActivity.this, "Anda membatalkan peminjaman!", Toast.LENGTH_SHORT).show();
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

    //CREATE NOTIVIKASI
    private void createNotivicationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Kemdsjhfkjh";
            String description = "Chanell notiv";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //CREATE PINJAM
    public void transaksi(final String id_user, final String id_rak) {
        RequestQueue queue = Volley.newRequestQueue(DetailKoleksiBukuActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        Toast.makeText(DetailKoleksiBukuActivity.this, "Anda melanjudkan peminjaman!", Toast.LENGTH_SHORT).show();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                        Toast.makeText(DetailKoleksiBukuActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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