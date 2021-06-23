package com.hoangdao.doantn.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.hoangdao.doantn.Activity.DangBai;
import com.hoangdao.doantn.Activity.Dathang;
import com.hoangdao.doantn.Activity.GioHang;
import com.hoangdao.doantn.Activity.Homepage;
import com.hoangdao.doantn.Danhgia;
import com.hoangdao.doantn.MainActivity;
import com.hoangdao.doantn.QLdonhangKH;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Activity.ThongtincanhanActivity;
import com.hoangdao.doantn.qlbaidang;

public class settingsFragment extends Fragment {
    private RelativeLayout infoAcc, donhang, dangbai, dangxuat, dadangbai, danhgia;

    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    public settingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        luutaikhoan = getContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
        init(view);
        handler();
        return view;
    }

    private void init(View view) {
        infoAcc = view.findViewById(R.id.infoAcc);
        donhang = view.findViewById(R.id.donhang);
        dangbai = view.findViewById(R.id.dangbai);
        dangxuat = view.findViewById(R.id.dangxuat);
        dadangbai = view.findViewById(R.id.dadangbai);
        danhgia = view.findViewById(R.id.danhgia);
    }

    private void handler() {
        infoAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ThongtincanhanActivity.class);
                startActivity(intent);
            }
        });
        dangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DangBai.class);
                startActivity(intent);
            }
        });
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                Homepage.checkid();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QLdonhangKH.class);
                startActivity(intent);
            }
        });
        dadangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), qlbaidang.class);
                startActivity(intent);
            }
        });
        danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Danhgia.class);
                startActivity(intent);
            }
        });
    }

}