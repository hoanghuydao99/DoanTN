package com.hoangdao.doantn.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hoangdao.doantn.Activity.Sodiachi;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.adapter.LoaiAdapter;
import com.hoangdao.doantn.adapter.PhanloaispAdapter;
import com.hoangdao.doantn.adapter.SPAdminAdapter;
import com.hoangdao.doantn.adapter.SodiachiAdapter;
import com.hoangdao.doantn.adapter.yeuthichadapter;
import com.hoangdao.doantn.model.Favorite;
import com.hoangdao.doantn.model.Giohangmua;
import com.hoangdao.doantn.model.LoaiModel;
import com.hoangdao.doantn.model.Sanpham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {

    yeuthichadapter phanloaispAdapter;
    ArrayList<Sanpham> sanphams;
    RecyclerView yeuthich;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String idsp = "";
    String idtk = "";
    ArrayList<Favorite> arrayList;

    yeuthichadapter adapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        init(view);
        sharedPreferences = getContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getyeuthich();
        return view;
    }

    private void getyeuthich() {

        idtk = sharedPreferences.getString("id", "");
        arrayList = new ArrayList<>();
        DataClient getData = APIUtils.getData();
        Call<List<Favorite>> callback = getData.yt(idtk);
        callback.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                arrayList = (ArrayList<Favorite>) response.body();

                for (int i = 0; i < arrayList.size(); i++) {
                    int idsp = Integer.parseInt((arrayList.get(i).getIdsp()));
                    Log.d("hoangchim", String.valueOf(idsp));
                    getdatasp(idsp);
                }
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
            }
        });
    }
    private void getdatasp(int idsp) {
        DataClient getData = APIUtils.getData();
        Call<List<Sanpham>> callback = getData.getspyt((idsp));
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                sanphams =  (ArrayList<Sanpham>) response.body();
                Log.d("hoangchim", sanphams.size()+"");
                if(sanphams.size() > 0 ){
//                    phanloaispAdapter = new yeuthichadapter(getContext(),sanphams);
//                    yeuthich.setHasFixedSize(true);
//                    yeuthich.setAdapter(phanloaispAdapter);
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//                    yeuthich.setLayoutManager(layoutManager);

                    phanloaispAdapter = new yeuthichadapter(getContext(),sanphams);
                    yeuthich.setHasFixedSize(true);
                    yeuthich.setAdapter(phanloaispAdapter);
                    yeuthich.setLayoutManager(new GridLayoutManager(getContext(), 1));
                }
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {
                Log.d("mes", t.getMessage());
                Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void init(View view){
        yeuthich = view.findViewById(R.id.yeuthich);
    }

}