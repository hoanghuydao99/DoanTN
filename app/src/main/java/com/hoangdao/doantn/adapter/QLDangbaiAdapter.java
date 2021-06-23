package com.hoangdao.doantn.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangdao.doantn.CheckConnection;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;
import com.hoangdao.doantn.chitietspadmin;
import com.hoangdao.doantn.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLDangbaiAdapter extends RecyclerView.Adapter<QLDangbaiAdapter.ItemHoler> {
    Context context;
    ArrayList<Sanpham> arrayList;

    public QLDangbaiAdapter(Context context, ArrayList<Sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public QLDangbaiAdapter.ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_qldangbai,parent,false);
        return new QLDangbaiAdapter.ItemHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLDangbaiAdapter.ItemHoler holder, final int position) {
        final Sanpham sanpham = arrayList.get(position);
        holder.txttensp.setText(sanpham.getTensp());
        int gia = Integer.parseInt(String.valueOf(sanpham.getGiasp()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText(decimalFormat.format(gia)  + " VNĐ");
        Picasso.get().load(sanpham.getHinhanh())
                .placeholder(R.drawable.image)
                .error(R.drawable.error)
                .into(holder.img);
        holder.btxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idsp = sanpham.getId();
                DataClient delete = APIUtils.getData();
                Call<String> callback = delete.deletesp(idsp);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null) {
                            String result = response.body();
                            if (result.equals("Success")) {
                                Toast.makeText(context, "Xoa sp thanh cong", Toast.LENGTH_SHORT).show();
                                arrayList.remove(position);
                                notifyDataSetChanged();
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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        public ImageView img, btxoa;
        public TextView txttensp, txtgiasp;

        public ItemHoler(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imagesanpham);
            txttensp = itemView.findViewById(R.id.txtensanpham);
            txtgiasp = itemView.findViewById(R.id.txtgia);
            btxoa = itemView.findViewById(R.id.btxoa);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, chitietspadmin.class);
                    intent.putExtra("chitietsanpham",arrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context, arrayList.get(getPosition()).getTensp());
                    context.startActivity(intent);
                }
            });


        }
    }
}
