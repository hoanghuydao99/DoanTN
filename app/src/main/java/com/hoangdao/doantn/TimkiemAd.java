package com.hoangdao.doantn;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.TimKiemAdapter;
import com.hoangdao.doantn.adapter.TimkiemAdmin;
import com.hoangdao.doantn.model.Sanpham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimkiemAd extends AppCompatActivity {
    private SearchView searchView;
    private ImageButton btnback;
    private RecyclerView recyclerView;
    private ArrayList<Sanpham> listtimkiem;
    private TimkiemAdmin timkiemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_timkiem_admin);
        recyclerView = findViewById(R.id.recytimkiem);
        searchView = findViewById(R.id.seachview);
        btnback = findViewById(R.id.btnback);
        getdatasp();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timkiem();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getdatasp() {
        String idForm = "1";
        DataClient getData = APIUtils.getData();
        Call<List<Sanpham>> callback = getData.getsanpham(idForm);
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                listtimkiem =  (ArrayList<Sanpham>) response.body();
                if(listtimkiem.size() > 0 ){
                    timkiemAdapter = new TimkiemAdmin(getApplicationContext(),listtimkiem);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(timkiemAdapter);
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
    private void Timkiem() {
        String tentk = searchView.getQuery().toString().trim();
        final String query = "SELECT * FROM sanpham WHERE tensp LIKE '%"+tentk+"%' OR mota LIKE '%"+tentk+"%'";
        DataClient getData = APIUtils.getData();
        Call<List<Sanpham>> callback = getData.gettimkiem(query);
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                listtimkiem =  (ArrayList<Sanpham>) response.body();
                if(listtimkiem.size() > 0 ){
                    timkiemAdapter = new TimkiemAdmin(getApplicationContext(),listtimkiem);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(timkiemAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                }
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {

            }
        });
    }
}