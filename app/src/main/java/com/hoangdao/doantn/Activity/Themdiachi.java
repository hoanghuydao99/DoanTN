package com.hoangdao.doantn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Themdiachi extends AppCompatActivity {

    private EditText edthoten, edtsdt, edtdiachi;
    private Button btnthem;
    private Toolbar toolbar;
    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_themdiachi);
        Anhxa();
        EventToobar();
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Themdiachi();
            }
        });
    }

    private void Anhxa() {
        edthoten = findViewById(R.id.edthoten);
        edtsdt = findViewById(R.id.edtsdt);
        edtdiachi = findViewById(R.id.edtdiachi);
        btnthem = findViewById(R.id.btnthem);
        toolbar = findViewById(R.id.toolbarthemdiachi);
    }


    private void Themdiachi() {
        luutaikhoan = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
        String makh = luutaikhoan.getString("id", "");

        if (edthoten.length() < 6) {
            Toast.makeText(this, "Bạn cần nhập đầy đủ họ tên", Toast.LENGTH_SHORT).show();
        } else if (edtsdt.length() > 10 || edtsdt.length() <= 9) {
            Toast.makeText(this, "Bạn chưa nhập hoặc nhập sai sdt", Toast.LENGTH_SHORT).show();
        } else if (edtdiachi.length() < 20) {
            Toast.makeText(this, "Địa chỉ cần ghi đầy đủ", Toast.LENGTH_SHORT).show();
        } else {
            String hoten = edthoten.getText().toString();
            String sdt = edtsdt.getText().toString().trim();
            String diachi = edtdiachi.getText().toString();

            DataClient insertdata = APIUtils.getData();
            Call<String> callback = insertdata.postdiachi(makh, hoten, sdt, diachi);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String resulit = response.body();
                    Log.d("hoangdz", resulit);
                    if (resulit.equals("Success")) {
                        Toast.makeText(Themdiachi.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Themdiachi.this, Sodiachi.class));
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("error", t.getMessage());
                }
            });
        }
    }


    private void EventToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}