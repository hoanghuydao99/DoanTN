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

import com.hoangdao.doantn.Activity.ChitietsanphamActivity;
import com.hoangdao.doantn.CheckConnection;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TimKiemAdapter extends RecyclerView.Adapter<TimKiemAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> sanphamArrayList;

    public TimKiemAdapter(Context context, ArrayList<Sanpham> sanphamArrayList) {
        this.context = context;
        this.sanphamArrayList = sanphamArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phanloaisp,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
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

    public class ItemHolder extends RecyclerView.ViewHolder{
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
                    Intent intent = new Intent(context.getApplicationContext(), ChitietsanphamActivity.class);
                    intent.putExtra("chitietsanpham",sanphamArrayList.get(getPosition()) );
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context,sanphamArrayList.get(getPosition()).getTensp());
                    context.startActivity(intent);
                }
            });
        }
    }
}