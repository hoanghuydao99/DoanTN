package com.hoangdao.doantn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.QLDonhangAdapter;
import com.hoangdao.doantn.adapter.QLDonhangkhAdapter;
import com.hoangdao.doantn.model.Donhang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class qldonhangAdmin extends AppCompatActivity {


    private Toolbar toolbar;
    private RecyclerView lvdonhang;
    static ArrayList<Donhang> listdonhang;
    QLDonhangAdapter donhangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_qldonhangadmin);

        toolbar = findViewById(R.id.toolbarqldh);
        lvdonhang = findViewById(R.id.lvdonhang);
        donhangadmin();
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


    public void donhangadmin(){
        String hoang = "1";
        DataClient insertdata = APIUtils.getData();
        Call<List<Donhang>> callback = insertdata.getalldonhang(hoang);
        callback.enqueue(new Callback<List<Donhang>>() {
            @Override
            public void onResponse(Call<List<Donhang>> call, Response<List<Donhang>> response) {
                listdonhang = (ArrayList<Donhang>) response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                lvdonhang.setLayoutManager(layoutManager);
                donhangAdapter = new QLDonhangAdapter(qldonhangAdmin.this,listdonhang);
                lvdonhang.setAdapter(donhangAdapter);

                Log.d("hoang", listdonhang.size()+"");
            }

            @Override
            public void onFailure(Call<List<Donhang>> call, Throwable t) {

            }
        });
    }
}