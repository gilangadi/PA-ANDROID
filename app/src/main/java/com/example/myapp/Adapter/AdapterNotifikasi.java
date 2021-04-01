package com.example.myapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp.Activity.NotifikasiActivity;
import com.example.myapp.Model.ModelNotifikasi;
import com.example.myapp.R;
import com.example.myapp.Utils.Api;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class AdapterNotifikasi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> recyclerViewItems;
    private final Context mContext;

    public AdapterNotifikasi(List<Object> recyclerViewItems, Context mContext) {
        this.recyclerViewItems = recyclerViewItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi, null);

        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final ModelNotifikasi fp = (ModelNotifikasi) recyclerViewItems.get(position);

        menuItemHolder.tanggal.setText(fp.getTanggal());
        menuItemHolder.isi.setText(fp.getIsi_notifikasi());

        menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        menuItemHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setTitle("Hapus Item");
//                builder.setMessage("Apakah anda ingin menghapus item ini?");
//                builder.setPositiveButton("Hapus", (dialog, which) -> {
//                    dialog.cancel();
//
//                    RequestQueue  QUEUE = Volley.newRequestQueue(mContext);
//
//                    String url = Api.API_BASE_URL + "/hapus-notifikasi/" + fp.getId();
//                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    Toast.makeText(mContext, "Item terhapus", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            try {
//                                String responseBody = new String(error.networkResponse.data, "utf-8");
//                                Log.d("TAG", "ERROR " + responseBody);
//                            } catch (UnsupportedEncodingException errorr) {
//                                Log.d("TAG", errorr.toString());
//                            }
//                        }
//                    });
//                    QUEUE.add(stringRequest);
//
//                });
//
//                builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
//                AlertDialog alert = builder.create();
//                alert.show();

//                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.MyDialogTheme);
//                builder.setCancelable(false);
//                builder.setMessage("Are you sure?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(mContext, "Item terhapus", Toast.LENGTH_SHORT).show();
//                        dialogInterface.dismiss();
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(mContext, "Item terhapus", Toast.LENGTH_SHORT).show();
//                        dialogInterface.dismiss();
//                    }
//                });
//                final AlertDialog dialog = builder.create();
//                dialog.show();


                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Konfimasi hapus!");
                // Show a message on alert dialog
                builder.setMessage("Apakah anda ingin menghapus item ini?");
                // Set the positive button
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                        RequestQueue QUEUE = Volley.newRequestQueue(mContext);

                        String url = Api.API_BASE_URL + "/hapus-notifikasi/" + fp.getId();

                        Log.i("URL",url);
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(mContext, "Item terhapus", Toast.LENGTH_SHORT).show();
                                        recyclerViewItems.clear();
                                        Intent mapIntent = new Intent(mContext, NotifikasiActivity.class);
                                        view.getContext().startActivity(mapIntent);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                try {
                                    String responseBody = new String(error.networkResponse.data, "utf-8");
                                    Log.d("TAG", "ERROR " + responseBody);
                                } catch (UnsupportedEncodingException errorr) {
                                    Log.d("TAG", errorr.toString());
                                }
                            }
                        });
                        QUEUE.add(stringRequest);

                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(DetailKoleksiJurnalActivity.this, "Anda membatalkan peminjaman!", Toast.LENGTH_SHORT).show();
                    }
                });
                // Create the alert dialog
                AlertDialog dialog = builder.create();

                dialog.show();

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positiveButton.setTextColor(Color.parseColor("#2574A9"));
                negativeButton.setTextColor(Color.parseColor("#2574A9"));

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

    private class MenuItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tanggal;
        public TextView isi;

        public MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tanggal = (TextView) itemLayoutView.findViewById(R.id.notifikasi_tgl);
            isi = (TextView) itemLayoutView.findViewById(R.id.notifikasi_isi);
        }
    }
}
