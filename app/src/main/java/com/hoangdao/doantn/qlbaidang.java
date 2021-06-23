package com.hoangdao.doantn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.QLDangbaiAdapter;
import com.hoangdao.doantn.adapter.SanphamAdapter;
import com.hoangdao.doantn.model.Sanpham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class qlbaidang extends AppCompatActivity {



    private Toolbar toolbar;
    private RecyclerView recyclerdb;

    QLDangbaiAdapter sanphamAdapter;
    ArrayList<Sanpham> sanphams;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_qlbaidang);

        toolbar = findViewById(R.id.toolbarqldh);
        recyclerdb = findViewById(R.id.recyclerdb);
        initShare();
        getbaidang();
        ActionToobar();
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

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void getbaidang(){
        String idkh = sharedPreferences.getString("id", "");
        DataClient getData = APIUtils.getData();
        Call<List<Sanpham>> callback = getData.getbaidang(idkh);
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                sanphams =  (ArrayList<Sanpham>) response.body();
                if(sanphams.size() > 0 ){
                    sanphamAdapter = new QLDangbaiAdapter(getApplicationContext(),sanphams);
                    recyclerdb.setHasFixedSize(true);
                    recyclerdb.setAdapter(sanphamAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                    recyclerdb.setLayoutManager(layoutManager);
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