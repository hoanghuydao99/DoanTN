package com.hoangdao.doantn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.hoangdao.doantn.Activity.DangBai;
import com.hoangdao.doantn.Activity.TimKiem;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.PhanloaispAdapter;
import com.hoangdao.doantn.adapter.SPAdminAdapter;
import com.hoangdao.doantn.adapter.SanphamAdapter;
import com.hoangdao.doantn.adapter.TimKiemAdapter;
import com.hoangdao.doantn.model.Sanpham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLsanpham extends AppCompatActivity {

    Toolbar toolbarqlsp;
    RecyclerView recyclerqlsp;
    ImageButton imgtimkiem;
    Button idthemsp;
    SPAdminAdapter sanphamAdapter;
    ArrayList<Sanpham> sanphams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_q_lsanpham);
        Anhxa();
        ActionToobar();
        getdatasp();
    }

    private void Anhxa() {
        imgtimkiem = findViewById(R.id.imgtimkiem);
        toolbarqlsp = findViewById(R.id.toolbarqlsp);
        recyclerqlsp = findViewById(R.id.recyclerqlsp);
        idthemsp = findViewById(R.id.idthemsp);
        idthemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Themsp.class);
                startActivity(intent);
            }
        });
        imgtimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimkiemAd.class);
                startActivity(intent);
            }
        });
    }


    private void ActionToobar() {
        setSupportActionBar(toolbarqlsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarqlsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getdatasp() {
        String idForm = "1";
        DataClient getData = APIUtils.getData();
        Call<List<Sanpham>> callback = getData.getspadmin(idForm);
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                sanphams = (ArrayList<Sanpham>) response.body();
                if (sanphams.size() > 0) {
//                    sanphamAdapter = new SPAdminAdapter(getApplicationContext(), sanphams);
//                    recyclerqlsp.setHasFixedSize(true);
//                    recyclerqlsp.setAdapter(sanphamAdapter);
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    sanphamAdapter = new SPAdminAdapter(getApplicationContext(),sanphams);
                    recyclerqlsp.setHasFixedSize(true);
                    recyclerqlsp.setAdapter(sanphamAdapter);
                    recyclerqlsp.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//                    recyclerqlsp.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {
                Log.d("mes", t.getMessage());
                Toast.makeText(getApplicationContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
        });
    }
}