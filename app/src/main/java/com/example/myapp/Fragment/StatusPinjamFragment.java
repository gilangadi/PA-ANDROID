package com.example.myapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp.Adapter.AdapterKoleksiBuku;
import com.example.myapp.Adapter.AdapterStatusPinjam;
import com.example.myapp.Model.ModelKoleksiBuku;
import com.example.myapp.Model.ModelStatusPinjam;
import com.example.myapp.R;
import com.example.myapp.SharedPrefmanager;
import com.example.myapp.User;
import com.example.myapp.Utils.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KoleksiBukuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusPinjamFragment  extends Fragment {


    public static final String TAG = "MYTAG";
    RequestQueue QUEUE;
    String URLHTTP;

    String Idrak = "";
    RecyclerView rv;
    private List<Object> mRecyclerViewItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView myRecyclerview;


    public static StatusPinjamFragment  newInstance() {
        StatusPinjamFragment  statusPinjamFragment  = new StatusPinjamFragment();
        return statusPinjamFragment ;
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_status_pinjam, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.recyclerView_statuspinjam);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new AdapterStatusPinjam(getContext(), mRecyclerViewItems, this);
        QUEUE = Volley.newRequestQueue(getContext());

        User user = SharedPrefmanager.getInstance(getContext()).getUser();
        URLHTTP = Api.API_BASE_URL + "/listpinjam/" + user.getId(); //getResources().getString(R.string.urlkoleksibuku)+'/'+user.getId();
        httpGET(URLHTTP);

        return rootView;

    }

    public void httpGET(String url) {
        //2 KOLOM
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(gridLayoutManager);

        mRecyclerViewItems.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parsingData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    Log.d(TAG, "ERROR " + responseBody);
                } catch (UnsupportedEncodingException errorr) {
                    Log.d(TAG, errorr.toString());
                }
            }
        }
        );
        QUEUE.add(stringRequest);
    }

    public void parsingData(String jsonData) {
        try {
            JSONArray obj = new JSONArray(jsonData);
            for (int i = 0; i < obj.length(); i++) {
                JSONObject jo_inside = obj.getJSONObject(i);
                int id = jo_inside.getInt("id");
                String judul = jo_inside.getString("judul");
                String ditulis_oleh = jo_inside.getString("ditulis_oleh");
                String tahun_terbit = jo_inside.getString("tahun_terbit");
                String urlimages = jo_inside.getString("urlimages");
                String urlpdf = jo_inside.getString("urlpdf");
                String status_buku_jurnal = jo_inside.getString("status_buku_jurnal");
                String status = jo_inside.getString("status");
                int stok_tersisa = jo_inside.getInt("stok");
                String edisi = jo_inside.getString("edisi");
                String no_panggil = jo_inside.getString("no_panggil");
                String isbn_issn = jo_inside.getString("isbn_issn");
                String subjek = jo_inside.getString("subjek");
                String klasifikasi = jo_inside.getString("klasifikasi");
                String judul_seri = jo_inside.getString("judul_seri");
                String gmd = jo_inside.getString("gmd");
                String bahasa = jo_inside.getString("bahasa");
                String penerbit = jo_inside.getString("penerbit");
                String tempat_terbit = jo_inside.getString("tempat_terbit");
                String abstrak = jo_inside.getString("abstrak");
                String created_at = jo_inside.getString("created_at");


                ModelStatusPinjam modelStatusPinjam = new ModelStatusPinjam(id,
                        judul,
                        ditulis_oleh,
                        tahun_terbit,
                        urlimages,
                        urlpdf,
                        status_buku_jurnal,
                        status,
                        stok_tersisa,
                        edisi,
                        no_panggil,
                        isbn_issn,
                        subjek,
                        klasifikasi,
                        judul_seri,
                        gmd,
                        bahasa,
                        penerbit,
                        tempat_terbit,
                        abstrak,
                        created_at);
                mRecyclerViewItems.add(modelStatusPinjam);
            }

            rv.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}