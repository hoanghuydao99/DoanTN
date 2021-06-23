package com.hoangdao.doantn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangdao.doantn.CheckConnection;
import com.hoangdao.doantn.Activity.ChitietsanphamActivity;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHoler> {
    Context context;
    ArrayList<Sanpham> arrayList;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customhot, null);
        ItemHoler itemHoler = new ItemHoler(v);
        return itemHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHoler holder, int position) {
        Sanpham sanpham = arrayList.get(position);
        holder.txttensp.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText(decimalFormat.format(Integer.parseInt(String.valueOf(sanpham.getGiasp()))) + " VNĐ");
        Picasso.get().load(sanpham.getHinhanh())
                .placeholder(R.drawable.image)
                .error(R.drawable.error)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView txttensp, txtgiasp;

        public ItemHoler(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imagesanpham);
            txttensp = itemView.findViewById(R.id.txtensanpham);
            txtgiasp = itemView.findViewById(R.id.txtgia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChitietsanphamActivity.class);
                    intent.putExtra("chitietsanpham",arrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context, arrayList.get(getPosition()).getTensp());
                    context.startActivity(intent);
                }
            });
        }
    }
}
