package com.example.myapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.Activity.DetailBukuAdminActivity;
import com.example.myapp.Fragment.BukuAdminFragment;
import com.example.myapp.Model.ModelBuku;
import com.example.myapp.Model.ModelBukuAdmin;
import com.example.myapp.R;
import com.example.myapp.Utils.Api;

import java.util.List;

public class AdapterBukuAdmin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> recyclerViewItems;
    private final Context mContext;
    BukuAdminFragment bukuAdminFragment;

    public AdapterBukuAdmin(Context context, List<Object> recyclerViewItems, BukuAdminFragment bukuAdminFragment) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.bukuAdminFragment = bukuAdminFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_buku_admin, null);
        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final AdapterBukuAdmin.MenuItemViewHolder menuItemHolder = (AdapterBukuAdmin.MenuItemViewHolder) holder;
        final ModelBukuAdmin fp = (ModelBukuAdmin) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudul());
        menuItemHolder.ditulis_oleh.setText(fp.getDitulis_oleh());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_Terbit());
        menuItemHolder.status_buku_jurnal.setText(fp.getStatus_buku_jurnal());


        Glide.with(mContext)
                .load(Api.BASE_URL + "/uploads/" + fp.getUrlimages())
                .override(300, 300)
                .centerCrop()
                .fitCenter()
                .into(menuItemHolder.urlimages);

        menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailBukuAdminActivity.class);
                intent.putExtra("Id", ((ModelBukuAdmin) recyclerViewItems.get(position)).getId());
                intent.putExtra("Judul", ((ModelBukuAdmin) recyclerViewItems.get(position)).getJudul());
                intent.putExtra("Ditulis_oleh", ((ModelBukuAdmin) recyclerViewItems.get(position)).getDitulis_oleh());
                intent.putExtra("Tahun_terbit", ((ModelBukuAdmin) recyclerViewItems.get(position)).getTahun_Terbit());
                intent.putExtra("Urlimages", ((ModelBukuAdmin) recyclerViewItems.get(position)).getUrlimages());
                intent.putExtra("Urlpdf", ((ModelBukuAdmin) recyclerViewItems.get(position)).getUrlpdf());
                intent.putExtra("Status_buku_jurnal", ((ModelBukuAdmin) recyclerViewItems.get(position)).getStatus_buku_jurnal());
                intent.putExtra("Status", ((ModelBukuAdmin) recyclerViewItems.get(position)).getStatus());
                intent.putExtra("stok_tersisa",((ModelBukuAdmin) recyclerViewItems.get(position)).getStok_tersisa());
                intent.putExtra("Edisi", ((ModelBukuAdmin) recyclerViewItems.get(position)).getEdisi());
                intent.putExtra("No_panggil", ((ModelBukuAdmin) recyclerViewItems.get(position)).getNo_panggil());
                intent.putExtra("Isbn_issn", ((ModelBukuAdmin) recyclerViewItems.get(position)).getIsbn_issn());
                intent.putExtra("Subjek", ((ModelBukuAdmin) recyclerViewItems.get(position)).getSubjek());
                intent.putExtra("Klasifikasi", ((ModelBukuAdmin) recyclerViewItems.get(position)).getKlasifikasi());
                intent.putExtra("Judul_seri", ((ModelBukuAdmin) recyclerViewItems.get(position)).getJudul_seri());
                intent.putExtra("Gmd", ((ModelBukuAdmin) recyclerViewItems.get(position)).getGmd());
                intent.putExtra("Bahasa", ((ModelBukuAdmin) recyclerViewItems.get(position)).getBahasa());
                intent.putExtra("Penerbit", ((ModelBukuAdmin) recyclerViewItems.get(position)).getPenerbit());
                intent.putExtra("Tempat_terbit", ((ModelBukuAdmin) recyclerViewItems.get(position)).getTempat_terbit());
                intent.putExtra("Abstrak", ((ModelBukuAdmin) recyclerViewItems.get(position)).getAbstrak());
                mContext.startActivity(intent);
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
        public TextView status_buku_jurnal;
        public LinearLayout lineLayout;

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            judul = (TextView) itemLayoutView.findViewById(R.id.judul_buku_admin);
            ditulis_oleh = (TextView) itemLayoutView.findViewById(R.id.ditulis_oleh_buku_admin);
            tahun_terbit = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_buku_admin);
            urlimages = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_buku_admin);
            status_buku_jurnal = (TextView) itemLayoutView.findViewById(R.id.status_buku_jurnal_admin);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_buku_admin);

        }
    }
}
