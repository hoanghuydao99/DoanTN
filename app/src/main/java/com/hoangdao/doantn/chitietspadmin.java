package com.hoangdao.doantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoangdao.doantn.model.Sanpham;
import com.squareup.picasso.Picasso;

public class chitietspadmin extends AppCompatActivity {

    Button capnhap;
    ImageView back, imgsanpham;
    EditText tvtensp, tvgiasp, tvmota, tvsoluong;
    String idsp = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Sanpham sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chitietspadmin);
        init();
        initShare();
        GetInfor();
    }
    private void init() {
        back = findViewById(R.id.back);
        imgsanpham = findViewById(R.id.imgsanpham);
        tvtensp = findViewById(R.id.tvtensp);
        tvgiasp = findViewById(R.id.tvgiasp);
        tvmota = findViewById(R.id.tvmota);
        capnhap = findViewById(R.id.capnhap);
        tvsoluong = findViewById(R.id.tvsoluong);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    private void GetInfor() {
        Intent intent = (Intent) getIntent();
        sp = (Sanpham) intent.getParcelableExtra("chitietsanpham");
        idsp = String.valueOf(sp.getId());
        tvtensp.setText(sp.getTensp());
        tvgiasp.setText(String.valueOf(sp.getGiasp()) + " VNĐ");
        tvsoluong.setText(String.valueOf(sp.getSoluong()));
        tvmota.setText(sp.getMota());
//        Toast.makeText(this, ""+sanpham.getHinhanh(), Toast.LENGTH_SHORT).show();
        Picasso.get().load(sp.getHinhanh())
                .placeholder(R.drawable.image)
                .error(R.drawable.error)
                .into(imgsanpham);
    }
}