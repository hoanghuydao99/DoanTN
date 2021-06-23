package com.hoangdao.doantn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.hoangdao.doantn.adapter.DanhgiaAdapter;
import com.hoangdao.doantn.adapter.yeuthichadapter;
import com.hoangdao.doantn.model.Favorite;
import com.hoangdao.doantn.model.Sanpham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Danhgia extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    DanhgiaAdapter phanloaispAdapter;
    String idsp = "";
    String idtk = "";

    ArrayList<com.hoangdao.doantn.model.Danhgia> arrayList;
    ArrayList<Sanpham> sanphams;

    private Toolbar toolbar;
    private RecyclerView lvdonhang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_danhgia);

        sharedPreferences = getApplicationContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        toolbar = findViewById(R.id.toolbarqldh);
        lvdonhang = findViewById(R.id.lvdonhang);
        ActionToobar();
        getdanhgia();
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

    private void getdanhgia() {

        idtk = sharedPreferences.getString("id", "");
        arrayList = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<com.hoangdao.doantn.model.Danhgia>> callback = getData.dg(idtk);
        callback.enqueue(new Callback<List<com.hoangdao.doantn.model.Danhgia>>() {
            @Override
            public void onResponse(Call<List<com.hoangdao.doantn.model.Danhgia>> call, Response<List<com.hoangdao.doantn.model.Danhgia>> response) {
                arrayList = (ArrayList<com.hoangdao.doantn.model.Danhgia>) response.body();
                for (int i = 0; i < arrayList.size(); i++) {
                    int idsp = Integer.parseInt((arrayList.get(i).getIdsp()));
                    Log.d("hoangchim", String.valueOf(idsp));
                    getdatasp(idsp);
                }
            }

            @Override
            public void onFailure(Call<List<com.hoangdao.doantn.model.Danhgia>> call, Throwable t) {
            }
        });
    }
    private void getdatasp(int idsp) {
        DataClient getData = APIUtils.getData();
        Call<List<Sanpham>> callback = getData.getspdg((idsp));
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                sanphams =  (ArrayList<Sanpham>) response.body();
                Log.d("hoangchim", sanphams.size()+"");
                if(sanphams.size() > 0 ){
                    phanloaispAdapter = new DanhgiaAdapter(getApplicationContext(),sanphams);
                    lvdonhang.setHasFixedSize(true);
                    lvdonhang.setAdapter(phanloaispAdapter);
                    lvdonhang.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
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