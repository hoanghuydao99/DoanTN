package com.hoangdao.doantn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.hoangdao.doantn.Activity.DangkyActivity;
import com.hoangdao.doantn.Activity.Homepage;
import com.hoangdao.doantn.Activity.IntroActivity;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.model.Taikhoan;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button signin, btdangky;
    EditText edttentk, edtpass;
    String taikhoan, matkhau;
    SharedPreferences luutaikhoan;
    SharedPreferences.Editor editor;
    static String tentk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        luutaikhoan = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
        tentk = luutaikhoan.getString("tentaikhoan", "");

        signin = findViewById(R.id.signin);
        btdangky = findViewById(R.id.btdangky);
        edttentk = findViewById(R.id.edttentk);
        edtpass = findViewById(R.id.edtpass);

        checkLogin();

    }

    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b) {
        checkLogin();
    }

    private void checkLogin() {
        if (!TextUtils.isEmpty(tentk)) {
            startActivity(new Intent(MainActivity.this, Homepage.class));
        } else {
            EventButton();
        }
    }

    private void EventButton() {
        btdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DangkyActivity.class);
                startActivity(intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taikhoan = edttentk.getText().toString();
                matkhau = edtpass.getText().toString();
                if (taikhoan.length() > 0 && matkhau.length() > 0) {
                    DataClient dataClient = APIUtils.getData();
                    Call<List<Taikhoan>> callback = dataClient.Logindata(taikhoan, matkhau);
                    callback.enqueue(new Callback<List<Taikhoan>>() {
                        @Override
                        public void onResponse(Call<List<Taikhoan>> call, Response<List<Taikhoan>> response) {
                            ArrayList<Taikhoan> mangtaikhoan = (ArrayList<Taikhoan>) response.body();
                            if (mangtaikhoan.size() > 0) {

                                String id = mangtaikhoan.get(0).getId();
                                String tentaikhoan = mangtaikhoan.get(0).getTentaikhoan();
                                String matkhau = mangtaikhoan.get(0).getMatkhau();
                                String hinhanh = mangtaikhoan.get(0).getHinhanh();
                                String hoten = mangtaikhoan.get(0).getHoten();
                                String gioitinh = mangtaikhoan.get(0).getGioitinh();
                                String ngaysinh = mangtaikhoan.get(0).getNgaysinh();
                                String sdt = mangtaikhoan.get(0).getSdt();
                                String diachi = mangtaikhoan.get(0).getDiachi();
                                int loaitk = mangtaikhoan.get(0).getLoaitk();
                                updateCaced(getApplicationContext(), id, tentaikhoan, matkhau, hinhanh, hoten, gioitinh, ngaysinh, sdt, diachi, loaitk);
                                EventBus.getDefault().post(true, "loginSuccess");

                                if (loaitk == 1) {
                                    startActivity(new Intent(MainActivity.this, Homepage.class));
                                } else {
                                    Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                                    intent.putExtra("thongtintaikhoan", mangtaikhoan);
                                    startActivity(intent);
                                }

                            }
//                            showLoginDialog();
                        }

                        @Override
                        public void onFailure(Call<List<Taikhoan>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "khong ton tai tk nay!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void showLoginDialog() {
        final FlatDialog flatDialog = new FlatDialog(MainActivity.this);
        flatDialog.setTitle("Welcome to Farmer")
                .setSubtitle("Task successfully submitted")
                .setFirstButtonText("Đăng nhập")
                .setSecondButtonText("Hủy")
                .withFirstButtonListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                        startActivity(intent);
                    }
                })
                .withSecondButtonListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flatDialog.dismiss();
                    }
                })
                .show();
    }

    public void updateCaced(Context context, String id, String tentaikhoan, String matkhau, String hinhanh, String hoten,
                            String gioitinh, String ngaysinh, String sdt, String diachi, int loaitk) {
        SharedPreferences cachedangnhap = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = cachedangnhap.edit();
        editor.putString("id", id);
        editor.putString("tentaikhoan", tentaikhoan);
        editor.putString("matkhau", matkhau);
        editor.putString("hinhanh", hinhanh);
        editor.putString("hoten", hoten);
        editor.putString("gioitinh", gioitinh);
        editor.putString("ngaysinh", ngaysinh);
        editor.putString("sdt", sdt);
        editor.putString("diachi", diachi);
        editor.putInt("loaitk", loaitk);
        editor.commit();
    }

}

