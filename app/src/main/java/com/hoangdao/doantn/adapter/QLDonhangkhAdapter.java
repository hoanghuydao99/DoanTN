package com.hoangdao.doantn.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangdao.doantn.QLdonhangKH;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.model.Donhang;
import com.hoangdao.doantn.model.Sanpham;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLDonhangkhAdapter extends RecyclerView.Adapter<QLDonhangkhAdapter.ViewHolder> {

    private QLdonhangKH context;
    private ArrayList<Donhang> listdhkh;

    public QLDonhangkhAdapter(QLdonhangKH context, ArrayList<Donhang> listdhkh) {
        this.context = context;
        this.listdhkh = listdhkh;
    }

    @NonNull
    @Override
    public QLDonhangkhAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dong_donhangkh, parent, false
                );
        return new QLDonhangkhAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLDonhangkhAdapter.ViewHolder holder, final int position) {
        final Donhang donhang = listdhkh.get(position);
        holder.hoten.setText(donhang.getTenkh());
        holder.diachi.setText(donhang.getDiachi());
        holder.tongtien.setText(String.valueOf(donhang.getTongtien()));
        holder.ngaydat.setText(donhang.getNgaydat());
        holder.trangthai.setText(donhang.getTrangthai());
        final String trangthai = donhang.getTrangthai();
        holder.btnhuydon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trangthai.equals("Chờ xác nhận")) {
                    XacnhanHuy(donhang.getIddh(), position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listdhkh.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView hoten, diachi, tongtien, ngaydat, trangthai;
        private ImageButton btnhuydon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trangthai = itemView.findViewById(R.id.tvtrangthai);
            hoten = itemView.findViewById(R.id.tvhotendh);
            ngaydat = itemView.findViewById(R.id.tvngaydat);
            tongtien = itemView.findViewById(R.id.tvtongtiendh);
            diachi = itemView.findViewById(R.id.tvdiachidh);
            btnhuydon = itemView.findViewById(R.id.btnhuydon);
        }
    }
    private void XacnhanHuy(final int madh, final int position){
        final AlertDialog.Builder dialoghuy = new AlertDialog.Builder(context);
        dialoghuy.setMessage("Bạn có muốn hủy đơn hàng này không?");
        dialoghuy.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                UpdateTrangthai(madh, "đã hủy", position);
            }
        });
        dialoghuy.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        dialoghuy.show();
    }
    private void UpdateTrangthai(final int madh, String huydon, final int position) {
        String query = "UPDATE donhang SET trangthai = '" + huydon + "' WHERE iddh ='" + madh + "'";
        DataClient insertdata = APIUtils.getData();
        Call<String> callback = insertdata.huydh(query);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message = response.body();
                if(message.equals("Success")){
                    Toast.makeText(context, "Huy thanh cong!", Toast.LENGTH_SHORT).show();
                    listdhkh.remove(position);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}