package com.example.myapp.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapp.Activity.DetailStatusKembaliActivity;
import com.example.myapp.Fragment.StatusKembaliFragment;
import com.example.myapp.Model.ModelAdminStatusKembali;
import com.example.myapp.Model.ModelPengembalian;
import com.example.myapp.R;
import com.example.myapp.Utils.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterStatusPengembalian extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> recyclerViewItems;
    private final Context mContext;
    StatusKembaliFragment statusKembaliFragment;

    String id_user = "";
    String Idrak = "";

    public AdapterStatusPengembalian(Context context, List<Object> recyclerViewItems, StatusKembaliFragment statusKembaliFragment) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.statusKembaliFragment = statusKembaliFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_status_pengembalian, null);
        return new AdapterStatusPengembalian.MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final AdapterStatusPengembalian.MenuItemViewHolder menuItemHolder = (AdapterStatusPengembalian.MenuItemViewHolder) holder;
        holder.itemView.getContext();
        final ModelPengembalian fp = (ModelPengembalian) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudul());
        menuItemHolder.ditulis_oleh.setText(fp.getDitulis_oleh());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_Terbit());
        Glide.with(mContext)
                .load(Api.BASE_URL + "/uploads/" + fp.getUrlimages())
                .override(900, 250)
                .into(menuItemHolder.urlimages);

        menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());

                CharSequence[] dialogItem = {"Detail", "Delete"};
                builder.setTitle(((ModelPengembalian) recyclerViewItems.get(position)).getJudul());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(mContext, DetailStatusKembaliActivity.class);
                                intent.putExtra("Id_user", ((ModelPengembalian) recyclerViewItems.get(position)).getId_user());
                                intent.putExtra("Id_rak", ((ModelPengembalian) recyclerViewItems.get(position)).getId_rak());
                                intent.putExtra("Id", ((ModelPengembalian) recyclerViewItems.get(position)).getId());
                                intent.putExtra("Judul", ((ModelPengembalian) recyclerViewItems.get(position)).getJudul());
                                intent.putExtra("Ditulis_oleh", ((ModelPengembalian) recyclerViewItems.get(position)).getDitulis_oleh());
                                intent.putExtra("Tahun_terbit", ((ModelPengembalian) recyclerViewItems.get(position)).getTahun_Terbit());
                                intent.putExtra("Urlimages", ((ModelPengembalian) recyclerViewItems.get(position)).getUrlimages());
                                intent.putExtra("Urlpdf", ((ModelPengembalian) recyclerViewItems.get(position)).getUrlpdf());
                                intent.putExtra("Status_buku_jurnal", ((ModelPengembalian) recyclerViewItems.get(position)).getStatus_buku_jurnal());
                                intent.putExtra("Status", ((ModelPengembalian) recyclerViewItems.get(position)).getStatus());
                                intent.putExtra("Edisi", ((ModelPengembalian) recyclerViewItems.get(position)).getEdisi());
                                intent.putExtra("No_panggil", ((ModelPengembalian) recyclerViewItems.get(position)).getNo_panggil());
                                intent.putExtra("Isbn_issn", ((ModelPengembalian) recyclerViewItems.get(position)).getIsbn_issn());
                                intent.putExtra("Subjek", ((ModelPengembalian) recyclerViewItems.get(position)).getSubjek());
                                intent.putExtra("Klasifikasi", ((ModelPengembalian) recyclerViewItems.get(position)).getKlasifikasi());
                                intent.putExtra("Judul_seri", ((ModelPengembalian) recyclerViewItems.get(position)).getJudul_seri());
                                intent.putExtra("Gmd", ((ModelPengembalian) recyclerViewItems.get(position)).getGmd());
                                intent.putExtra("Bahasa", ((ModelPengembalian) recyclerViewItems.get(position)).getBahasa());
                                intent.putExtra("Penerbit", ((ModelPengembalian) recyclerViewItems.get(position)).getPenerbit());
                                intent.putExtra("Tempat_terbit", ((ModelPengembalian) recyclerViewItems.get(position)).getTempat_terbit());
                                intent.putExtra("Abstrak", ((ModelPengembalian) recyclerViewItems.get(position)).getAbstrak());
                                intent.putExtra("Created_at", ((ModelPengembalian) recyclerViewItems.get(position)).getCreated_at());
                                mContext.startActivity(intent);
                                break;

                            case 1:
                                deletepengembalian(((ModelPengembalian) recyclerViewItems.get(position)).getId());
                                break;
                        }
                    }

                    //DELETE KOLEKSI BUKU
                    private void deletepengembalian(int id) {
                        final String URLs = Api.BASE_URL + "/api/deletepengembalian/";
                        RequestQueue queue = Volley.newRequestQueue(menuItemHolder.itemView.getContext());
                        StringRequest postRequest = new StringRequest(Request.Method.DELETE, URLs + id,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("APPLOG", response);
                                        Toast.makeText(menuItemHolder.itemView.getContext(), response, Toast.LENGTH_SHORT).show();
                                        parseJson(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("APPLOG", error.toString());
                                        Toast.makeText(menuItemHolder.itemView.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        public TextView judul;
        public TextView ditulis_oleh;
        public TextView tahun_terbit;
        public ImageView urlimages;
        public LinearLayout lineLayout;

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            judul = (TextView) itemLayoutView.findViewById(R.id.judul_pengguna_status_pengembalian);
            ditulis_oleh = (TextView) itemLayoutView.findViewById(R.id.pengarang_pengguna_status_pengembalian);
            tahun_terbit = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_pengguna_status_pengembalian);
            urlimages = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_pengguna_status_pengembalian);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_pengguna_pengembalian);

        }
    }

}
