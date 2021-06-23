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
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.chitietspadmin;
import com.hoangdao.doantn.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TimkiemAdmin extends RecyclerView.Adapter<TimkiemAdmin.ItemHolder> {
    Context context;
    ArrayList<Sanpham> sanphamArrayList;

    public TimkiemAdmin(Context context, ArrayList<Sanpham> sanphamArrayList) {
        this.context = context;
        this.sanphamArrayList = sanphamArrayList;
    }

    @NonNull
    @Override
    public TimkiemAdmin.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phanloaisp, null);
        TimkiemAdmin.ItemHolder itemHolder = new TimkiemAdmin.ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimkiemAdmin.ItemHolder holder, int position) {
        Sanpham sanpham = sanphamArrayList.get(position);
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
        return sanphamArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView txttensp, txtgiasp;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imagesanpham);
            txttensp = itemView.findViewById(R.id.txtensanpham);
            txtgiasp = itemView.findViewById(R.id.txtgia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, chitietspadmin.class);
                    CheckConnection.ShowToast(context, sanphamArrayList.get(getPosition()).getTensp());
                    intent.putExtra("chitietsanpham",sanphamArrayList.get(getPosition()) );
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}