package com.hoangdao.doantn.adapter;

import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangdao.doantn.QLdonhangKH;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.model.Donhang;
import com.hoangdao.doantn.qldonhangAdmin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLDonhangAdapter extends RecyclerView.Adapter<QLDonhangAdapter.ViewHolder> {

    String trangthaidh = "";
    qldonhangAdmin context;
    private ArrayList<Donhang> listdhad;

    public QLDonhangAdapter(qldonhangAdmin context, ArrayList<Donhang> listdhad) {
        this.context = context;
        this.listdhad = listdhad;
    }
    @NonNull
    @Override
    public QLDonhangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dong_donhang, parent, false
                );
        return new QLDonhangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLDonhangAdapter.ViewHolder holder, int position) {
        final Donhang donhang = listdhad.get(position);
        holder.tvmadh.setText(String.valueOf(donhang.getIddh()));
        holder.tvhotendh.setText(donhang.getTenkh());
        holder.tvsdtdh.setText(donhang.getSdt());
        holder.tvdiachidh.setText(donhang.getDiachi());
        holder.tvtongtiendh.setText(String.valueOf(donhang.getTongtien()));
        holder.tvngaydat.setText(donhang.getNgaydat());
        holder.tvtrangthai.setText(donhang.getTrangthai());
        final String trangthai = donhang.getTrangthai();
        Log.d("trangthai", trangthai);
        holder.btntrangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trangthai.equals("hoàn thành")){
                    Toast.makeText(context, "Đơn hàng này đã hoàn thành!", Toast.LENGTH_SHORT).show();
                }else if(trangthai.equals("đã hủy")){
                    Toast.makeText(context, "Đơn hàng này đã được hủy!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DialogTrangthai( donhang.getIddh());
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return listdhad.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvmadh, tvtrangthai, tvhotendh, tvsdtdh, tvdiachidh, tvtongtiendh, tvngaydat;
        private ImageButton btntrangthai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmadh = itemView.findViewById(R.id.tvmadh);
            tvtrangthai = itemView.findViewById(R.id.tvtrangthai);
            tvhotendh = itemView.findViewById(R.id.tvhotendh);
            tvsdtdh = itemView.findViewById(R.id.tvsdtdh);
            tvdiachidh = itemView.findViewById(R.id.tvdiachidh);
            tvtongtiendh = itemView.findViewById(R.id.tvtongtiendh);
            tvngaydat = itemView.findViewById(R.id.tvngaydat);
            btntrangthai = itemView.findViewById(R.id.btntrangthai);
        }
    }
    private void DialogTrangthai(final int madh){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.customdialog_trangthai);

        final RadioButton radchovanchuyen = dialog.findViewById(R.id.radchovc);
        final RadioButton raddanggiao = dialog.findViewById(R.id.raddanggiao);
        final RadioButton radhoanthanh = dialog.findViewById(R.id.radhoanthanh);
        final RadioButton radhuydon = dialog.findViewById(R.id.radhuydon);
        Button btnxacnhan = dialog.findViewById(R.id.btnxacnhan);
        Button btnhuy = dialog.findViewById(R.id.btnhuy);

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radchovanchuyen.isChecked()){
                    trangthaidh = "chờ vận chuyển";
                }
                if(raddanggiao.isChecked()){
                    trangthaidh = "đang giao";
                }
                if(radhoanthanh.isChecked()){
                    trangthaidh = "hoàn thành";
                }
                if (radhuydon.isChecked()){
                    trangthaidh = "đã hủy";
                }
                UpdateTrangthai(madh);
                dialog.dismiss();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void UpdateTrangthai(int madh) {

        final String query = "UPDATE donhang SET trangthai = '" + trangthaidh + "' WHERE iddh ='" + madh + "'";

        DataClient insertdata = APIUtils.getData();
        Call<String> callback = insertdata.qldonhangkh(query);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message = response.body();
                if(message.equals("Success")){
                    Toast.makeText(context, "cap nhat thanh cong!", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
