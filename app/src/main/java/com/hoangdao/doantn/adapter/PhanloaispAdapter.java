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

public class PhanloaispAdapter extends RecyclerView.Adapter<PhanloaispAdapter.ViewHolder> {

    ArrayList<Sanpham> listsanphams;
    Context context;

    public PhanloaispAdapter(Context context, ArrayList<Sanpham> sanphams){
        this.context = context;
        this.listsanphams = sanphams;
    }
    @NonNull
    @Override
    public PhanloaispAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phanloaisp,parent,false
                );
        return new PhanloaispAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhanloaispAdapter.ViewHolder holder, int position) {
        Sanpham sanpham = listsanphams.get(position);
        holder.product_name.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.product_price.setText(decimalFormat.format(Integer.parseInt(String.valueOf(sanpham.getGiasp()))) + " VNĐ");
        String url = listsanphams.get(position).getHinhanh();
        Picasso.get().load(url)
                .placeholder(R.drawable.image)
                .error(R.drawable.error)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return listsanphams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView product_name;
        TextView product_price;

        public ViewHolder(View iteamView){
            super(iteamView);
            image = iteamView.findViewById(R.id.imagesanpham);
            product_name = iteamView.findViewById(R.id.txtensanpham);
            product_price = iteamView.findViewById(R.id.txtgia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChitietsanphamActivity.class);
                    intent.putExtra("chitietsanpham",listsanphams.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context, listsanphams.get(getPosition()).getTensp());
                    context.startActivity(intent);
                }
            });
        }
    }
}
