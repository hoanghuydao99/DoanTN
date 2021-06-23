package com.hoangdao.doantn.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.hoangdao.doantn.Activity.GioHang;
import com.hoangdao.doantn.Activity.TimKiem;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.LoaiAdapter;
import com.hoangdao.doantn.adapter.SanphamAdapter;
import com.hoangdao.doantn.adapter.SanphamSaleAdapter;
import com.hoangdao.doantn.model.Giohangmua;
import com.hoangdao.doantn.model.LoaiModel;
import com.hoangdao.doantn.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreFragment extends Fragment {

    ViewFlipper viewFlipper;
    private ImageButton imggiohang, imgtimkiem;
    RecyclerView recyclerView, recycler1, recycler2;

    ArrayList<LoaiModel> loaiModels;

    LoaiAdapter loaiAdapter;

    SanphamAdapter sanphamAdapter;
    ArrayList<Sanpham> sanphams;

    SanphamSaleAdapter sanphamSaleAdapter;

    public static ArrayList<Giohangmua> listGiohang;

    private ScrollView scrollView;

    public StoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        if (listGiohang != null) {//nếu mạng có dữ liệu k tạo mảng mới
        } else {//nếu chưa có dữ liệu
            listGiohang = new ArrayList<Giohangmua>();// tạo mảng cấp phát dữ liệu
        }
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewlipper);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recycler1 = (RecyclerView) view.findViewById(R.id.recycler1);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        imggiohang = view.findViewById(R.id.imggiohang);
        imggiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GioHang.class);
                startActivity(intent);
            }
        });
        imgtimkiem = view.findViewById(R.id.imgtimkiem);
        imgtimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimKiem.class);
                startActivity(intent);
            }
        });
        getdatasp();
        getdataloaisp();
//        getspsale();
        ActionViewFlipper();
        return view;
    }

//    private void getspsale() {
//        String idForm2 = "1";
//        DataClient getData = APIUtils.getData();
//        Call<List<Sanpham>> callback = getData.getsanphamsale(idForm2);
//        callback.enqueue(new Callback<List<Sanpham>>() {
//            @Override
//            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
//                sanphams =  (ArrayList<Sanpham>) response.body();
//                if(sanphams.size() > 0 ){
//                    sanphamSaleAdapter = new SanphamSaleAdapter(getContext(),sanphams);
//                    recycler2.setHasFixedSize(true);
//                    recycler2.setAdapter(sanphamSaleAdapter);
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//
//                    recycler2.setLayoutManager(layoutManager);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Sanpham>> call, Throwable t) {
//                Log.d("mes", t.getMessage());
//                Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void getdataloaisp() {
        String idForm1 = "1";
        DataClient getData = APIUtils.getData();
        Call<List<LoaiModel>> callback = getData.getloaisp(idForm1);
        callback.enqueue(new Callback<List<LoaiModel>>() {
            @Override
            public void onResponse(Call<List<LoaiModel>> call, Response<List<LoaiModel>> response) {
                loaiModels =  (ArrayList<LoaiModel>) response.body();
                Log.e("hhd",loaiModels.get(0).getHinhanhloaisp()+"");
                if(loaiModels.size() > 0 ){
                    loaiAdapter = new LoaiAdapter(getContext(),loaiModels);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(loaiAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                    recyclerView.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<List<LoaiModel>> call, Throwable t) {
                Log.d("mes", t.getMessage());
                Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
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
                sanphams =  (ArrayList<Sanpham>) response.body();
                if(sanphams.size() > 0 ){
                    sanphamAdapter = new SanphamAdapter(getContext(),sanphams);
                    recycler1.setHasFixedSize(true);
                    recycler1.setAdapter(sanphamAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                    recycler1.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {
                Log.d("mes", t.getMessage());
                Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ActionViewFlipper() {
        ArrayList<String> manquangcao = new ArrayList<>();
        manquangcao.add("https://nef.vn/wp-content/uploads/2019/11/trade-marketing-1024x427.jpg");
        manquangcao.add("https://cdn.123job.vn/123job/uploads/2020/06/27/2020_06_27______1570489203ecaef5d93a69c93bc7942c.jpg");
        manquangcao.add("https://finviet.com.vn/wp-content/uploads/2020/06/distribution_banner-1024x632.jpg");
        for (int i = 0; i < manquangcao.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Picasso.get().load(manquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim1);
        Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.anim2);
        viewFlipper.setInAnimation(animation);
        viewFlipper.setOutAnimation(animation1);
    }
}