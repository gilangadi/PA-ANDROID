package com.example.myapp.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.Activity.DetailStatusPinjamAdminMainActivity;
import com.example.myapp.Activity.DetailstatuspinjamActivity;
import com.example.myapp.Fragment.StatusPinjamFragment;
import com.example.myapp.Model.ModelAdminStatusPinjam;
import com.example.myapp.Model.ModelStatusPinjam;
import com.example.myapp.R;
import com.example.myapp.Utils.Api;

import java.util.List;

public class AdapterStatusPinjam extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Object> recyclerViewItems;
    private final Context mContext;
    StatusPinjamFragment statusPinjamFragment;

    public AdapterStatusPinjam(Context context, List<Object> recyclerViewItems, StatusPinjamFragment statusPinjamFragment) {
        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.statusPinjamFragment = statusPinjamFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_status_pinjam, null);
        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final ModelStatusPinjam fp = (ModelStatusPinjam) recyclerViewItems.get(position);

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

                CharSequence[] dialogItem = {"Detail"};
                builder.setTitle(((ModelStatusPinjam) recyclerViewItems.get(position)).getJudul());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {

                            case 0:
                                Intent intent = new Intent(mContext, DetailstatuspinjamActivity.class);
                                intent.putExtra("Id", ((ModelStatusPinjam) recyclerViewItems.get(position)).getId());
                                intent.putExtra("Judul", ((ModelStatusPinjam) recyclerViewItems.get(position)).getJudul());
                                intent.putExtra("Ditulis_oleh", ((ModelStatusPinjam) recyclerViewItems.get(position)).getDitulis_oleh());
                                intent.putExtra("Tahun_terbit", ((ModelStatusPinjam) recyclerViewItems.get(position)).getTahun_Terbit());
                                intent.putExtra("Urlimages", ((ModelStatusPinjam) recyclerViewItems.get(position)).getUrlimages());
                                intent.putExtra("Urlpdf", ((ModelStatusPinjam) recyclerViewItems.get(position)).getUrlpdf());
                                intent.putExtra("Status_buku_jurnal", ((ModelStatusPinjam) recyclerViewItems.get(position)).getStatus_buku_jurnal());
                                intent.putExtra("Status", ((ModelStatusPinjam) recyclerViewItems.get(position)).getStatus());
                                intent.putExtra("stok_tersisa",((ModelStatusPinjam) recyclerViewItems.get(position)).getStok_tersisa());
                                intent.putExtra("Edisi", ((ModelStatusPinjam) recyclerViewItems.get(position)).getEdisi());
                                intent.putExtra("No_panggil", ((ModelStatusPinjam) recyclerViewItems.get(position)).getNo_panggil());
                                intent.putExtra("Isbn_issn", ((ModelStatusPinjam) recyclerViewItems.get(position)).getIsbn_issn());
                                intent.putExtra("Subjek", ((ModelStatusPinjam) recyclerViewItems.get(position)).getSubjek());
                                intent.putExtra("Klasifikasi", ((ModelStatusPinjam) recyclerViewItems.get(position)).getKlasifikasi());
                                intent.putExtra("Judul_seri", ((ModelStatusPinjam) recyclerViewItems.get(position)).getJudul_seri());
                                intent.putExtra("Gmd", ((ModelStatusPinjam) recyclerViewItems.get(position)).getGmd());
                                intent.putExtra("Bahasa", ((ModelStatusPinjam) recyclerViewItems.get(position)).getBahasa());
                                intent.putExtra("Penerbit", ((ModelStatusPinjam) recyclerViewItems.get(position)).getPenerbit());
                                intent.putExtra("Tempat_terbit", ((ModelStatusPinjam) recyclerViewItems.get(position)).getTempat_terbit());
                                intent.putExtra("Abstrak", ((ModelStatusPinjam) recyclerViewItems.get(position)).getAbstrak());
                                intent.putExtra("status_buku_jurnal", ((ModelStatusPinjam) recyclerViewItems.get(position)).getStatus_buku_jurnal());
                                intent.putExtra("Created_at", ((ModelStatusPinjam) recyclerViewItems.get(position)).getCreated_at());

                                mContext.startActivity(intent);
                                break;
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
            judul = (TextView) itemLayoutView.findViewById(R.id.judul_jurnal);
            ditulis_oleh = (TextView) itemLayoutView.findViewById(R.id.pengarang_jurnal);
            tahun_terbit = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_jurnal);
            urlimages = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_jurnal);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_pinjam);


        }
    }
}
