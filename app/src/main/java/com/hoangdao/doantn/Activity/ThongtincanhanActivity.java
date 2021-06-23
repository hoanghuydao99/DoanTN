package com.hoangdao.doantn.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hoangdao.doantn.R;
import com.hoangdao.doantn.model.Taikhoan;
import com.squareup.picasso.Picasso;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

public class ThongtincanhanActivity extends AppCompatActivity {

    TextView txttk, txtmk, txthoten, txtgt, txtns, txtsdt, txtdiachi;
    ImageView username, imgttcn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_thongtincanhan);
        EventBus.getDefault().register(this);
        anhxa();
        initShare();
        init();
    }

    private void anhxa() {
        txttk = findViewById(R.id.txttentk);
        txtmk = findViewById(R.id.txtmk);
        txthoten = findViewById(R.id.txthoten);
        txtgt = findViewById(R.id.txtgt);
        txtns = findViewById(R.id.txtns);
        txtsdt = findViewById(R.id.txtsdt);
        txtdiachi = findViewById(R.id.txtdiachi);
        username = findViewById(R.id.imguser);
        imgttcn = findViewById(R.id.imgttcn);
        imgttcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initShare() {
        sharedPreferences = getApplicationContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Subscriber(tag = "loginSuccess")
    public void loginSuccess(boolean b) {
        init();
    }

    private void init() {
        String url = sharedPreferences.getString("hinhanh", "");
        Log.d("url", url);
        txttk.setText("Ten tai khoan: " + sharedPreferences.getString("tentaikhoan", ""));
        txtmk.setText("Mat khau: " + sharedPreferences.getString("matkhau", ""));
        Picasso.get().load(url).into(username);
        txthoten.setText("Ho ten: " + sharedPreferences.getString("hoten", ""));
        txtgt.setText("Gioi tinh: " + sharedPreferences.getString("gioitinh", ""));
        txtns.setText("Ngay sinh: " + sharedPreferences.getString("ngaysinh", ""));
        txtsdt.setText("SDT: " + sharedPreferences.getString("sdt", ""));
        txtdiachi.setText("Dia chi: " + sharedPreferences.getString("diachi", ""));
    }


}