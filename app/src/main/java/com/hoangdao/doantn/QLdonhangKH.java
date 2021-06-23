package com.hoangdao.doantn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.hoangdao.doantn.Activity.DangBai;
import com.hoangdao.doantn.Activity.Sodiachi;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.QLDonhangkhAdapter;
import com.hoangdao.doantn.adapter.SodiachiAdapter;
import com.hoangdao.doantn.model.Diachi;
import com.hoangdao.doantn.model.Donhang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLdonhangKH extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView lvqldh;
    private ArrayList<Donhang> listdonhangkh;
    private QLDonhangkhAdapter qlDonhangkhAdapter;
    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_qldonhangkh);

        toolbar = findViewById(R.id.toolbarqldh);
        lvqldh = findViewById(R.id.lvdonhang);


        ActionToobar();
        initPreferences();
        getDonhang();
    }
    private void ActionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initPreferences() {
        luutaikhoan = getApplicationContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
    }

    public void getDonhang() {

        String makh = luutaikhoan.getString("id", "");
        Log.d("lll", makh);
        DataClient insertdata = APIUtils.getData();
        Call<List<Donhang>> callback = insertdata.postdonhangkh(makh);
        callback.enqueue(new Callback<List<Donhang>>() {
            @Override
            public void onResponse(Call<List<Donhang>> call, Response<List<Donhang>> response) {
                listdonhangkh = (ArrayList<Donhang>) response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                lvqldh.setLayoutManager(layoutManager);
                qlDonhangkhAdapter = new QLDonhangkhAdapter(QLdonhangKH.this,listdonhangkh);
                lvqldh.setAdapter(qlDonhangkhAdapter);

                Log.d("hoang", listdonhangkh.size()+"");
            }

            @Override
            public void onFailure(Call<List<Donhang>> call, Throwable t) {

            }
        });

    }
}