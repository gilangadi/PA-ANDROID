package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapp.Activity.AdminActivity;
import com.example.myapp.Activity.NotifikasiActivity;
import com.example.myapp.Activity.PenggunaActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Notification extends Activity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        if (getIntent().hasExtra("msg")) {

            String isi_pesan = getIntent().getExtras().getString("msg");

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Notifikasi")
                    .setContentText(isi_pesan)
                    .setConfirmText("OK")
                    .setConfirmClickListener(sDialog -> {
                        if (SharedPrefmanager.getInstance(this).isLoggedIn() && SharedPrefmanager.getInstance(this).getKeyStatus().equals("admin")) {
                            Intent intent = new Intent(com.example.myapp.Notification.this, NotifikasiActivity.class);
                            intent.putExtra("redirect", "goto-notifikasi");
                            startActivity(intent);

                        }else{
                            Intent intent = new Intent(com.example.myapp.Notification.this, NotifikasiActivity.class);
                            intent.putExtra("redirect", "goto-notifikasi");
                            startActivity(intent);

                        }

                        sDialog.dismissWithAnimation();
                        finish();


                    })
                    .show();


        }

    }
}
