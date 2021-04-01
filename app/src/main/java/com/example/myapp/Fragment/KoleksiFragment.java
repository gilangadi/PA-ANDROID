package com.example.myapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapp.R;
import com.example.myapp.SectionPagerAdapterRak;
import com.google.android.material.tabs.TabLayout;

public class KoleksiFragment extends Fragment {

    View myFragment;

    private ViewPager viewPagerKoleksi;
    private TabLayout tabLayoutKoleksi;

    public KoleksiFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_koleksi, container, false);


        viewPagerKoleksi = myFragment.findViewById(R.id.view_pager_koleksi);
        tabLayoutKoleksi = myFragment.findViewById(R.id.tab_layout_koleksi);

        return myFragment;
    }

    //Call onactivity create method
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPagerKoleksi);
        tabLayoutKoleksi.setupWithViewPager(viewPagerKoleksi);

        tabLayoutKoleksi.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setUpViewPager(ViewPager viewPagerKoleksi) {
        SectionPagerAdapterRak adabter = new SectionPagerAdapterRak(getChildFragmentManager());

        adabter.addFragment(new KoleksiBukuFragment(), "Koleksi Buku");
        adabter.addFragment(new KoleksiJurnalFragment(), "Koleksi Jurnal");

        viewPagerKoleksi.setAdapter(adabter);
    }
}