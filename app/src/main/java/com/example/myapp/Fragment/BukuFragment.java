package com.example.myapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp.Adapter.AdapterBuku;
import com.example.myapp.Model.ModelBuku;
import com.example.myapp.R;
import com.example.myapp.Utils.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BukuFragment extends Fragment {

  public static final String TAG = "MYTAG";
  RequestQueue QUEUE;
  String URLHTTP;
  RecyclerView rv;
  SwipeRefreshLayout swipeRefreshLayout;
  private List<Object> mRecyclerViewItems;
  private AdapterBuku mAdapter;
  private SearchView searchView;
  private GridLayoutManager gridLayoutManager;
  private RecyclerView myRecyclerview;

  public static BukuFragment newInstance() {
    BukuFragment bukuFragment = new BukuFragment();
    return bukuFragment;
  }

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  //SEARCHVIEW
  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main, menu);

    //2 KOLOM
    gridLayoutManager = new GridLayoutManager(getContext(), 2);
    rv.setLayoutManager(gridLayoutManager);

    MenuItem item = menu.findItem(R.id.search);
    final SearchView searchView = new SearchView(getContext());
    searchView.setQueryHint("Cari buku");

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        mRecyclerViewItems.clear();
        searchbuku(query);
        mAdapter = new AdapterBuku (getContext(),mRecyclerViewItems,BukuFragment.this);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    item.setActionView(searchView);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_buku, container, false);
    setHasOptionsMenu(true);

    swipeRefreshLayout=(SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout_pengguna);
    swipeRefreshLayout.setOnRefreshListener(getContext());

    mRecyclerViewItems = new ArrayList<>();
    rv = (RecyclerView) rootView.findViewById(R.id.recyclerView_buku);
    rv.setHasFixedSize(true);
    rv.setLayoutManager(new LinearLayoutManager(getContext()));
//    mAdapter = new AdapterBuku();
    QUEUE = Volley.newRequestQueue(getContext());
    URLHTTP = Api.API_BASE_URL + "/rakbuku";//getResources().getString(R.string.urlserverbuku);
    httpGET(URLHTTP);
    mAdapter = new AdapterBuku (getContext(),mRecyclerViewItems,this);
    return rootView;
  }

  public void httpGET(String url) {
    Log.e( BukuFragment.class.getSimpleName() +  "URL",url);
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
    QUEUE.getCache().clear();
    QUEUE.add(stringRequest);
  }

  public void parsingData(String jsonData) {

    swipeRefreshLayout.setRefreshing(true);
    
    try {
      JSONObject obj = new JSONObject(jsonData);
      JSONArray m_jArry = obj.getJSONArray("data");
      for (int i = 0; i < m_jArry.length(); i++) {
        JSONObject jo_inside = m_jArry.getJSONObject(i);
        int id = jo_inside.getInt("id");
        String judul = jo_inside.getString("judul");
        String ditulis_oleh = jo_inside.getString("ditulis_oleh");
        String tahun_terbit = jo_inside.getString("tahun_terbit");
        String urlimages = jo_inside.getString("urlimages");
        String urlpdf = jo_inside.getString("urlpdf");
        String status_buku_jurnal = jo_inside.getString("status_buku_jurnal");
        String status = jo_inside.getString("status");
        int stok_tersisa = jo_inside.getInt("stok_tersisa");
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

        ModelBuku modelBuku = new ModelBuku(id,
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
                abstrak);
        mRecyclerViewItems.add(modelBuku);

      }
      //mAdapter.send(mRecyclerViewItems);
      rv.setAdapter(mAdapter);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void searchbuku(String query) {
    String UrlSearch = Api.API_BASE_URL + "/searchbuku/" + query;
    QUEUE = Volley.newRequestQueue(getContext());
    mRecyclerViewItems.clear();
    StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlSearch,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                JSONArray m_jArry = null;
                try {
                  m_jArry = new JSONArray(response);
                  for (int i = 0; i < m_jArry.length(); i++) {
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    int id = jo_inside.getInt("id");
                    String judul = jo_inside.getString("judul");
                    String ditulis_oleh = jo_inside.getString("ditulis_oleh");
                    String tahun_terbit = jo_inside.getString("tahun_terbit");
                    String urlimages = jo_inside.getString("urlimages");
                    String urlpdf = jo_inside.getString("urlpdf");
                    String status_buku_jurnal = jo_inside.getString("status_buku_jurnal");
                    String status = jo_inside.getString("status");
                    int stok_tersisa = jo_inside.getInt("stok_tersisa");
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

                    ModelBuku modelBuku = new ModelBuku(
                            id,
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
                            abstrak);
                    mRecyclerViewItems.add(modelBuku);
                  }
                  rv.setAdapter(mAdapter);
                } catch (JSONException e) {
                  e.printStackTrace();
                }
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
    QUEUE.getCache().clear();
    QUEUE.add(stringRequest);
  }

  @Override
  public void onResume() {
    super.onResume();
    //httpGET(URLHTTP);
  }

  @Override
  public void onPause() {
    super.onPause();
    // httpGET(URLHTTP);
  }
}