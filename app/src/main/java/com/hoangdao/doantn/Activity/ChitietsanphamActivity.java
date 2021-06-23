package com.hoangdao.doantn.Activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.model.Danhgia;
import com.hoangdao.doantn.model.Favorite;
import com.hoangdao.doantn.model.Giohangmua;
import com.hoangdao.doantn.model.Sanpham;
import com.squareup.picasso.Picasso;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hoangdao.doantn.Fragment.StoreFragment.listGiohang;


public class ChitietsanphamActivity extends AppCompatActivity {
    ImageView back, imgsanpham, imageViewFavorite;
    TextView tvtensp, tvgiasp, tvmota, tvsoluongsp, tvsltrongkho;
    Button btthemgh, giam, tang;

    LinearLayout hhh;

    ImageView sao0, sao1, sao2, sao3, sao4;

    Sanpham sp;
    int slhientai;

    ArrayList<Danhgia> arrayList1;
    ArrayList<Favorite> arrayList;
    String idsp = "";
    String idtk = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chitietsanpham);

        init();
        initShare();
        GetInfor();
        getFavortie();
        getDanhgia();
        soluong();
        ButtonThemGH();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    private void soluong() {
        String sl = tvsoluongsp.getText().toString();
        if(sl.equals("1")){
            giam.setVisibility(View.GONE);
        }

        tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(tvsoluongsp.getText().toString()) + 1;
                tvsoluongsp.setText(slmoi + "");
                Log.d("slmoi", slmoi + "");
                if (slmoi > 100 || slmoi >= slhientai) {
                    tang.setVisibility(View.GONE);
                    giam.setVisibility(View.VISIBLE);
                    tvsoluongsp.setText(slmoi + "");
                } else {
                    tang.setVisibility(View.VISIBLE);
                    giam.setVisibility(View.VISIBLE);
                    tvsoluongsp.setText(slmoi + "");
                }
            }
        });
        giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(tvsoluongsp.getText().toString()) - 1;
                tvsoluongsp.setText(slmoi + "");
                if (slmoi <= 1) {
                    tang.setVisibility(View.VISIBLE);
                    giam.setVisibility(View.GONE);
                    tvsoluongsp.setText(slmoi + "");
                } else {
                    tang.setVisibility(View.VISIBLE);
                    giam.setVisibility(View.VISIBLE);
                    tvsoluongsp.setText(slmoi + "");
                }
            }
        });
    }

    private void ButtonThemGH() {
        btthemgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listGiohang.size() > 0) {
                    boolean exist = false;
                    for (int i = 0; i < listGiohang.size(); i++) {
                        if (listGiohang.get(i).getIdgh() == sp.getId()) {
                            int soLuong = Integer.parseInt(tvsoluongsp.getText().toString().trim());
                            int sl = soLuong + listGiohang.get(i).getSoluongsp();
                            if (sl > 100) {
                                listGiohang.get(i).setSoluongsp(100);
                            } else {
                                listGiohang.get(i).setSoluongsp(sl);
                            }
                            exist = true;
                        }
                    }
                    if (exist == false) {
                        int soLuong = Integer.parseInt(tvsoluongsp.getText().toString().trim());
                        Giohangmua gioHang = new Giohangmua(sp.getId(), sp.getTensp(), sp.getGiasp(), sp.getHinhanh(), soLuong);
                        listGiohang.add(gioHang);

                    }
                } else {
                    int soLuong = Integer.parseInt(tvsoluongsp.getText().toString().trim());
                    Giohangmua gioHang = new Giohangmua(sp.getId(), sp.getTensp(), sp.getGiasp(), sp.getHinhanh(), soLuong);
                    listGiohang.add(gioHang);
                }
                startActivity(new Intent(ChitietsanphamActivity.this, GioHang.class));
                //  }
                // Toast.makeText(Chitietsanpham.this,"Đã thêm"+listgh.size()+"vào giỏ hàng",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        back = findViewById(R.id.back);
        imgsanpham = findViewById(R.id.imgsanpham);
        imageViewFavorite = findViewById(R.id.imageViewFavorite);
        tvtensp = findViewById(R.id.tvtensp);
        tvgiasp = findViewById(R.id.tvgiasp);
        tvmota = findViewById(R.id.tvmota);
        btthemgh = findViewById(R.id.btthemgh);
        giam = findViewById(R.id.giam);
        tang = findViewById(R.id.tang);
        tvsoluongsp = findViewById(R.id.soluongsp);
        tvsltrongkho = findViewById(R.id.tvsoluongsp);
        hhh = findViewById(R.id.hhh);
        sao0 = findViewById(R.id.sao0);
        sao1 = findViewById(R.id.sao1);
        sao2 = findViewById(R.id.sao2);
        sao3 = findViewById(R.id.sao3);
        sao4 = findViewById(R.id.sao4);
    }

    private void GetInfor() {
        Intent intent = (Intent) getIntent();
        sp = (Sanpham) intent.getParcelableExtra("chitietsanpham");
        idsp = String.valueOf(sp.getId());
        slhientai = sp.getSoluong();
        tvtensp.setText(sp.getTensp());
        tvgiasp.setText(String.valueOf(sp.getGiasp()) + " VNĐ");
        tvmota.setText(sp.getMota());
        tvsltrongkho.setText(slhientai + " sp còn lại");
//        Toast.makeText(this, ""+sanpham.getHinhanh(), Toast.LENGTH_SHORT).show();
        Picasso.get().load(sp.getHinhanh())
                .placeholder(R.drawable.image)
                .error(R.drawable.error)
                .into(imgsanpham);
    }

    public void getFavortie() {
        idtk = sharedPreferences.getString("id", "");
        arrayList = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<Favorite>> callback = getData.getyeuthich(idtk, idsp);
        callback.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                arrayList = (ArrayList<Favorite>) response.body();
                if (arrayList.size() > 0) {
                    imageViewFavorite.setImageResource(R.drawable.ic_yeuthich);
                    delete();
                }

            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
                Fravorit();
            }
        });
    }

    public void Fravorit() {
        idtk = sharedPreferences.getString("id", "");
        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataClient dataClient = APIUtils.getData();
                Call<String> call = dataClient.postyeuthich(idtk, idsp);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String result = response.body();
                        if (result.equals("1")) {
                            Toast.makeText(ChitietsanphamActivity.this, "Đã yêu thích", Toast.LENGTH_SHORT).show();
                            getFavortie();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ChitietsanphamActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void delete() {
        idtk = sharedPreferences.getString("id", "");
        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataClient delete = APIUtils.getData();
                Call<String> callback = delete.deleteFavorite(idsp, idtk);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null) {
                            int result = Integer.parseInt(response.body());
                            if (result == 1) {
                                imageViewFavorite.setImageResource(R.drawable.traitim);
                                Toast.makeText(getApplicationContext(), "Bỏ yêu thích", Toast.LENGTH_SHORT).show();
                                getFavortie();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Loi", t.getMessage());
                    }
                });
            }
        });
    }


    public void getDanhgia() {
        idtk = sharedPreferences.getString("id", "");
        arrayList1 = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<Danhgia>> callback = getData.getdanhgia(idtk, idsp);
        callback.enqueue(new Callback<List<Danhgia>>() {
            @Override
            public void onResponse(Call<List<Danhgia>> call, Response<List<Danhgia>> response) {
                arrayList1 = (ArrayList<Danhgia>) response.body();
                if (arrayList1.size() > 0) {
                    sao0.setBackgroundResource(R.drawable.saovang);
                    sao1.setBackgroundResource(R.drawable.saovang);
                    sao2.setBackgroundResource(R.drawable.saovang);
                    sao3.setBackgroundResource(R.drawable.saovang);
                    sao4.setBackgroundResource(R.drawable.saovang);
                    deletedg();
                }

            }

            @Override
            public void onFailure(Call<List<Danhgia>> call, Throwable t) {
                Danhgia();
            }
        });
    }

    public void Danhgia() {
        idtk = sharedPreferences.getString("id", "");
        hhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataClient dataClient = APIUtils.getData();
                Call<String> call = dataClient.postdanhgia(idtk, idsp);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String result = response.body();
                        if (result.equals("1")) {
                            Toast.makeText(ChitietsanphamActivity.this, "Đã đánh giá", Toast.LENGTH_SHORT).show();
                            getDanhgia();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ChitietsanphamActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void deletedg() {
        idtk = sharedPreferences.getString("id", "");
        hhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataClient delete = APIUtils.getData();
                Call<String> callback = delete.deletedg(idsp, idtk);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null) {
                            int result = Integer.parseInt(response.body());
                            if (result == 1) {
                                sao0.setBackgroundResource(R.drawable.sao);
                                sao1.setBackgroundResource(R.drawable.sao);
                                sao2.setBackgroundResource(R.drawable.sao);
                                sao3.setBackgroundResource(R.drawable.sao);
                                sao4.setBackgroundResource(R.drawable.sao);
                                Toast.makeText(getApplicationContext(), "Bỏ đánh giá", Toast.LENGTH_SHORT).show();
                                getDanhgia();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Loi", t.getMessage());
                    }
                });
            }
        });

    }
}