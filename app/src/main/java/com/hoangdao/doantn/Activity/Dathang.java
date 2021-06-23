package com.hoangdao.doantn.Activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangdao.doantn.Fragment.StoreFragment;
import com.hoangdao.doantn.MainActivity;
import com.hoangdao.doantn.R;

import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.DathangAdapter;


import org.simple.eventbus.Subscriber;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hoangdao.doantn.Fragment.StoreFragment.listGiohang;

public class Dathang extends AppCompatActivity {

    private ListView lvdathang;
    private TextView tvtenkh, tvsdtkh, tvdiachi, tvthaydoi, tvphiship;
    private TextView tvtamtinh, tvthanhtien;
    private Button btnxacnhan;
    ImageView imgback;

    String hoten = "";
    String sdt = "";
    String diachikh = "";
    String idkh;
    long tongtien = 0;
    long thanhtien = 0;
    long phiship = 0;

    DathangAdapter dathangAdapter;

    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dathang);
        Anhxa();
        initShare();
        EventData();
        GetDiachi();
        EventClick();

    }

    public void initShare() {
        luutaikhoan = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
    }


    private void EventClick() {


        idkh = luutaikhoan.getString("id", "");
        hoten = luutaikhoan.getString("hoten", "");
        diachikh = luutaikhoan.getString("diachi", "");
        sdt = luutaikhoan.getString("sdt", "");


        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvtenkh.length() < 1 || tvsdtkh.length() < 1 || tvdiachi.length() < 1) {
                    Toast.makeText(Dathang.this, "Vùi lòng chọn địa chỉ nhận hàng!", Toast.LENGTH_SHORT).show();
                } else if (thanhtien < 1) {
                    Toast.makeText(Dathang.this, "Không có sản phẩm để đặt hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    DataClient insertdata = APIUtils.getData();
                    Call<String> callback = insertdata.donhang(idkh, hoten, sdt, diachikh, String.valueOf(thanhtien));
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String iddonhang = response.body();
                            Log.d("aaaa", response.body().toString());
                            if (iddonhang != null) {
                                DataClient insertdata = APIUtils.getData();
                                for (int i = 0; i < listGiohang.size(); i++) {
                                    int idsp = listGiohang.get(i).getIdgh();
                                    String tensp = listGiohang.get(i).getTensp();
                                    int slsp = listGiohang.get(i).getSoluongsp();
                                    int giasp = listGiohang.get(i).getGiasp();
                                    Call<String> callback = insertdata.chitietdonhang(iddonhang, idsp, slsp, tensp, giasp);
                                    callback.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String message = response.body();
                                            Log.d("aaaa", message);
                                            if (message.equals("Success")) {
                                                Toast.makeText(Dathang.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                listGiohang = new ArrayList<>();
                                                startActivity(new Intent(getApplicationContext(), Homepage.class));
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("hoangaa", t.getMessage());
                            Toast.makeText(Dathang.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        tvthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sodiachi.class));
            }
        });
    }

    private void Anhxa() {
        imgback = findViewById(R.id.imgback);
        tvtenkh = findViewById(R.id.tvtenkh);
        tvsdtkh = findViewById(R.id.tvsdtkh);
        tvdiachi = findViewById(R.id.tvdiachikh);
        tvthaydoi = findViewById(R.id.tvthaydoi);
        tvtamtinh = findViewById(R.id.tamtinh);
        tvphiship = findViewById(R.id.phiship);
        tvthanhtien = findViewById(R.id.thanhtien);
        btnxacnhan = findViewById(R.id.xacnhan);
        lvdathang = findViewById(R.id.listdathang);

        dathangAdapter = new DathangAdapter(Dathang.this, listGiohang);
        lvdathang.setAdapter(dathangAdapter);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void GetDiachi() {
        Intent intent = getIntent();
        hoten = intent.getStringExtra("hoten");
        sdt = intent.getStringExtra("sdt");
        diachikh = intent.getStringExtra("diachi");

        tvtenkh.setText(hoten);
        tvsdtkh.setText(sdt);
        tvdiachi.setText(diachikh);
    }


    public void EventData() {
        for (int i = 0; i < listGiohang.size(); i++) {
            tongtien += listGiohang.get(i).getGiasp() * listGiohang.get(i).getSoluongsp();
            Log.d("binh", tongtien + "");
            if (listGiohang.size() > 1 || listGiohang.get(i).getSoluongsp() > 1) {
                phiship = 0;
                thanhtien = tongtien + phiship;
            } else {
                phiship = 15000;
                thanhtien = tongtien + phiship;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvtamtinh.setText(decimalFormat.format(tongtien) + " VNĐ");
        tvphiship.setText(decimalFormat.format(phiship) + " VNĐ");
        tvthanhtien.setText(decimalFormat.format(thanhtien) + " VNĐ");
    }
}