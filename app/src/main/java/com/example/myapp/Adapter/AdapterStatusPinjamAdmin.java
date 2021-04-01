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
import com.example.myapp.Fragment.StatusPinjamAdminFragment;
import com.example.myapp.Model.ModelAdminStatusPinjam;
import com.example.myapp.Model.ModelBuku;
import com.example.myapp.R;
import com.example.myapp.Utils.Api;

import java.util.List;

public class AdapterStatusPinjamAdmin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> recyclerViewItems;
    private final Context mContext;
    StatusPinjamAdminFragment statusPinjamAdminFragment;

    String id_user = "";
    String Idrak = "";

    public AdapterStatusPinjamAdmin(Context context, List<Object> recyclerViewItems, StatusPinjamAdminFragment statusPinjamAdminFragment) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.statusPinjamAdminFragment = statusPinjamAdminFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_status_pinjam_admin, null);
        return new AdapterStatusPinjamAdmin.MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final AdapterStatusPinjamAdmin.MenuItemViewHolder menuItemHolder = (AdapterStatusPinjamAdmin.MenuItemViewHolder) holder;
        holder.itemView.getContext();
        final ModelAdminStatusPinjam fp = (ModelAdminStatusPinjam) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudul());
        menuItemHolder.ditulis_oleh.setText(fp.getDitulis_oleh());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_Terbit());
        Glide.with(mContext)
                .load(Api.BASE_URL + "/uploads/" + fp.getUrlimages())
                .override(900, 250)
                .into(menuItemHolder.urlimages);

        menuItemHolder.nama.setText(fp.getNama());
        menuItemHolder.nim.setText(fp.getNim());
        menuItemHolder.prodi.setText(fp.getProdi());
//        menuItemHolder.status.setText(fp.getStatus());

        menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());

                CharSequence[] dialogItem = {"Detail"};
                builder.setTitle(((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getJudul());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {

                            case 0:
                                Intent intent = new Intent(mContext, DetailStatusPinjamAdminMainActivity.class);
                                intent.putExtra("Id", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getId());
                                intent.putExtra("Id_rak", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getId_rak());
                                intent.putExtra("Id_user", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getId_user());
                                intent.putExtra("Judul", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getJudul());
                                intent.putExtra("Ditulis_oleh", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getDitulis_oleh());
                                intent.putExtra("Tahun_terbit", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getTahun_Terbit());
                                intent.putExtra("Urlimages", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getUrlimages());
                                intent.putExtra("Urlpdf", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getUrlpdf());
                                intent.putExtra("Status_buku_jurnal", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getStatus_buku_jurnal());
                                intent.putExtra("Status", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getStatus());
                                intent.putExtra("stok_tersisa",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getStok_tersisa());
                                intent.putExtra("Edisi", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getEdisi());
                                intent.putExtra("No_panggil", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getNo_panggil());
                                intent.putExtra("Isbn_issn", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getIsbn_issn());
                                intent.putExtra("Subjek", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getSubjek());
                                intent.putExtra("Klasifikasi", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getKlasifikasi());
                                intent.putExtra("Judul_seri", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getJudul_seri());
                                intent.putExtra("Gmd", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getGmd());
                                intent.putExtra("Bahasa", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getBahasa());
                                intent.putExtra("Penerbit", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getPenerbit());
                                intent.putExtra("Tempat_terbit", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getTempat_terbit());
                                intent.putExtra("Abstrak", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getAbstrak());
                                intent.putExtra("status_buku_jurnal", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getStatus_buku_jurnal());

                                intent.putExtra("nama", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getNama());
                                intent.putExtra("nim", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getNim());
                                intent.putExtra("prodi", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getProdi());
                                intent.putExtra("created_at", ((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getCreated_at());

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
        public TextView nama;
        public TextView nim;
        public TextView prodi;

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            judul = (TextView) itemLayoutView.findViewById(R.id.judul_admin_status);
            ditulis_oleh = (TextView) itemLayoutView.findViewById(R.id.pengarang_admin_status);
            tahun_terbit = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_admin_status);
            urlimages = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_admin_status);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_admin);
            nama = (TextView) itemLayoutView.findViewById(R.id.namapeminjam);
            nim = (TextView) itemLayoutView.findViewById(R.id.nimpeminjam);
            prodi = (TextView) itemLayoutView.findViewById(R.id.prodipeminjam);
        }
    }
}
