package com.hoangdao.doantn.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangdao.doantn.Fragment.StoreFragment;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.adapter.GiohangAdapter;

import java.text.DecimalFormat;

import static com.hoangdao.doantn.Fragment.StoreFragment.listGiohang;

public class GioHang extends AppCompatActivity {

    ListView lvgiohang;
    TextView tvthongbao;
    public static TextView tvtongtien;
    private Button btndathang, btnmuatiep;
    GiohangAdapter giohangAdapter;
    ImageView back, btxoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        onclick();
        tongtien();
    }

    public static void tongtien() {
        int tongTien = 0;
        for (int i = 0; i < listGiohang.size(); i++) {
            tongTien += listGiohang.get(i).tongTien();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvtongtien.setText(decimalFormat.format(tongTien) + " VNĐ");
    }

    private void onclick() {
        btnmuatiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GioHang.this, Homepage.class));
            }
        });
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listGiohang.size() > 0) {
                    startActivity(new Intent(getApplicationContext(), Dathang.class));
                } else {
                    Toast.makeText(GioHang.this, "Gio hang khong co san pham", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void Anhxa() {
        btxoa = findViewById(R.id.btxoa);
        lvgiohang = findViewById(R.id.lvgiohang);
        tvtongtien = findViewById(R.id.tvtongtien);
        btndathang = findViewById(R.id.btn_danghang);
        btnmuatiep = findViewById(R.id.btn_muatiep);
        giohangAdapter = new GiohangAdapter(GioHang.this, StoreFragment.listGiohang);
        lvgiohang.setAdapter(giohangAdapter);
        back = findViewById(R.id.backgiohang);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}