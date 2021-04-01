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

public class DetailJurnalAdminActivity extends AppCompatActivity {

    public static final int PERMISSION_CODE = 42042;
    public static final String URLs = Api.API_BASE_URL + "/pinjam";
    public static final String URLsKoleksijurnal = Api.API_BASE_URL + "/koleksibuku";
    private final static int REQUEST_CODE = 42;
    boolean isNotificActive = false;
    int notifID = 33;
    String idUser = "";
    String Idrak = "";
    private TextView jjudul, jpengarang, jtahunterbit;
    private ImageView jimg;
    private Context mContext;
    private Activity mActivity;
    private LinearLayout mCLayout;
    private Button Buttonpdf;
    private Button mButton;
    private Button alertButton;
    private String UrlGambar;
    private String UrlPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jurnal_admin);

        jjudul = (TextView) findViewById(R.id.judul_jurnal_detail_admin);
        jpengarang = (TextView) findViewById(R.id.pengarang_jurnal_detail_admin);
        jtahunterbit = (TextView) findViewById(R.id.tahun_terbit_jurnal_detail_admin);
        jimg = (ImageView) findViewById(R.id.image_jurnal_detail_admin);

        //pasing detail jurnal
        Intent intent = getIntent();
        String Judul = intent.getStringExtra("Judul");
        String Pengarang = intent.getStringExtra("Pengarang");
        String Tahun_terbit = intent.getStringExtra("Tahun_terbit");
        final String Urlimages = intent.getStringExtra("Urlimages");
        final String Urlpdf = intent.getStringExtra("Urlpdf");
        Idrak = String.valueOf(intent.getIntExtra("Id", 0));

        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailJurnalAdminActivity.this).getId());

        Toast.makeText(DetailJurnalAdminActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();

        UrlGambar = (Api.BASE_URL + "/uploads/" + Urlimages);
        UrlPdf = (Api.BASE_URL + "/pdf/" + Urlpdf);

        jjudul.setText(Judul);
        jpengarang.setText(Pengarang);
        jtahunterbit.setText(Tahun_terbit);
        Glide.with(this)
                .load(Api.BASE_URL + "/uploads/" + Urlimages)
                .centerCrop()
                .fitCenter()
                .into(jimg);

        mContext = getApplicationContext();
        mActivity = DetailJurnalAdminActivity.this;

        mButton = (Button) findViewById(R.id.button_pinjam_jurnal_admin);
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
                builder.setTitle("Konfimasi hapus jurnal!");
                // Show a message on alert dialog
                builder.setMessage("Yakin untuk hapus jurnal!");
                // Set the positive button
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailJurnalAdminActivity.this, "Anda menghapus jurnal!", Toast.LENGTH_SHORT).show();
                        hapusbuku(Idrak);

                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailJurnalAdminActivity.this, "Anda membatalkan hapus jurnal!", Toast.LENGTH_SHORT).show();
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

    private void hapusbuku(final String idrak) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String URLsHapus = Api.API_BASE_URL + "/rakadmin/" + idrak;
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
