package com.hoangdao.doantn.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hoangdao.doantn.Activity.Homepage;
import com.hoangdao.doantn.MainActivity;
import com.hoangdao.doantn.QLsanpham;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.qldonhangAdmin;


public class AdminFragment extends Fragment {

    RelativeLayout donhang, thongke, qlsanpham, dangxuat;

    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        luutaikhoan = getContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
        anhxa(view);
        chuyenman();
        return view;
    }

    private void chuyenman() {
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
        qlsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), QLsanpham.class);
                startActivity(intent);
            }
        });
        donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), qldonhangAdmin.class);
                startActivity(intent);
            }
        });
    }

    private void anhxa(View view) {
        donhang = view.findViewById(R.id.donhang);
        thongke = view.findViewById(R.id.thongke);
        qlsanpham = view.findViewById(R.id.quanlysp);
        dangxuat = view.findViewById(R.id.dangxuat);

    }
}