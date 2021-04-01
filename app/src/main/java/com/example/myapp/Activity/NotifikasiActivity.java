package com.example.myapp.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp.Adapter.AdapterNotifikasi;
import com.example.myapp.Model.ModelNotifikasi;
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

public class NotifikasiActivity extends AppCompatActivity {

    public static final String TAG = "MYTAG";
    RequestQueue QUEUE;
    String URLHTTP;
    RecyclerView rv;
    private List<Object> mRecyclerViewItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView myRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        rv = (RecyclerView) findViewById(R.id.recyclerView_notifikasi);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AdapterNotifikasi(mRecyclerViewItems, this);

        User user = SharedPrefmanager.getInstance(this).getUser();
        QUEUE = Volley.newRequestQueue(this);
        URLHTTP = Api.API_BASE_URL + "/notifikasi/" + user.getId(); //getResources().getString(R.string.urlserverjurnal);
        httpGET(URLHTTP);
        Log.e("URLHTTP",URLHTTP);
    }

    private void httpGET(String url) {
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
        });
        QUEUE.add(stringRequest);
    }

    private void parsingData(String jsonData) {
        try {
            JSONObject obj = new JSONObject(jsonData);
            JSONArray m_jArry = obj.getJSONArray("data");
            for (int i = 0; i < m_jArry.length(); i++) {

                JSONObject jo_inside = m_jArry.getJSONObject(i);

                int id = jo_inside.getInt("id");
                int user_id = jo_inside.getInt("user_id");
                String isi = jo_inside.getString("isi_notifikasi");
                String tanggal = jo_inside.getString("tanggal");

                ModelNotifikasi model = new ModelNotifikasi(id, user_id, isi, tanggal);
                mRecyclerViewItems.add(model);
            }
            rv.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}