package com.hoangdao.doantn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.PhanloaispAdapter;
import com.hoangdao.doantn.adapter.SanphamAdapter;
import com.hoangdao.doantn.model.LoaiModel;
import com.hoangdao.doantn.model.Sanpham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoaispActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView img;

    PhanloaispAdapter phanloaispAdapter;
    ArrayList<Sanpham> sanphams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loaisp);
        init();
        getdatasp();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




    private void getdatasp() {
        Intent intent = getIntent();
        int category_id = intent.getIntExtra("name", -1);
        Log.d("category", category_id+"");

        DataClient getData = APIUtils.getData();
        Call<List<Sanpham>> callback = getData.getProductbyKey(category_id);
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                sanphams =  (ArrayList<Sanpham>) response.body();
                if(sanphams.size() > 0 ){
                    phanloaispAdapter = new PhanloaispAdapter(getApplicationContext(),sanphams);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(phanloaispAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

                }
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {
                Log.d("mes", t.getMessage());
                Toast.makeText(getApplicationContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        img = findViewById(R.id.imghh);
        recyclerView = findViewById(R.id.product_recyclerView);
    }
}