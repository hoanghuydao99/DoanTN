package com.hoangdao.doantn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hoangdao.doantn.Fragment.StoreFragment;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.GiohangAdapter;
import com.hoangdao.doantn.adapter.PhanloaispAdapter;
import com.hoangdao.doantn.adapter.SodiachiAdapter;
import com.hoangdao.doantn.model.Diachi;
import com.hoangdao.doantn.model.Sanpham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sodiachi extends AppCompatActivity {

    private RecyclerView recyclerView_diachi;
    private Button btnthemdiachi;
    private Toolbar toolbar;
    ArrayList<Diachi> listdiachi;
    SodiachiAdapter sodiachiAdapter;
    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sodiachi);

        recyclerView_diachi = findViewById(R.id.recyclerView_diachi);
        btnthemdiachi = findViewById(R.id.themdiachi);
        toolbar = findViewById(R.id.toolbarsodiachi);
        listdiachi = new ArrayList<>();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EventClick();
        GetDiachi();
    }
    public void DeleteDiachi(int iddc){
        final String query = "DELETE FROM diachigiaohang WHERE id ='"+iddc+"'";
    }

    private void EventClick() {
        btnthemdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Themdiachi.class));
            }
        });
    }

    private void GetDiachi(){
        listdiachi.clear();

        luutaikhoan = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
        String makh = luutaikhoan.getString("id", "");
        Log.d("anh", makh);

        DataClient getData = APIUtils.getData();
        Call<List<Diachi>> callback = getData.getdiachi(makh);
        callback.enqueue(new Callback<List<Diachi>>() {
            @Override
            public void onResponse(Call<List<Diachi>> call, Response<List<Diachi>> response) {
                listdiachi = (ArrayList<Diachi>) response.body();
                Log.d("anh", listdiachi.toString());
                if(listdiachi.size() > 0 ){
                    sodiachiAdapter = new SodiachiAdapter(Sodiachi.this,listdiachi);
                    recyclerView_diachi.setHasFixedSize(true);
                    recyclerView_diachi.setAdapter(sodiachiAdapter);
                    recyclerView_diachi.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));


                }
            }

            @Override
            public void onFailure(Call<List<Diachi>> call, Throwable t) {
                Log.d("mes", t.getMessage());
                Toast.makeText(getApplicationContext(), "Không có dia chi nào", Toast.LENGTH_SHORT).show();
            }
        });
    }
}