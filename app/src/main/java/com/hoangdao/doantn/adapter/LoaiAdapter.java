package com.hoangdao.doantn.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangdao.doantn.Activity.LoaispActivity;
import com.hoangdao.doantn.model.LoaiModel;
import com.hoangdao.doantn.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.ViewHolder> {

    ArrayList<LoaiModel> loaiModels;
    Context context;

    public LoaiAdapter(Context context, ArrayList<LoaiModel> loaiModels){
        this.context = context;
        this.loaiModels = loaiModels;
    }
    @NonNull
    @Override
    public LoaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiAdapter.ViewHolder holder, int position) {
        LoaiModel loaiModel = loaiModels.get(position);
        holder.textView.setText(loaiModel.getTenloaisp());
        String url = loaiModels.get(position).getHinhanhloaisp();
        Picasso.get().load(url)
                .placeholder(R.drawable.image)
                .error(R.drawable.error)
                .into(holder.image);
        Log.e("daipv",loaiModels.get(position).getHinhanhloaisp()+"");

        holder.onclick(loaiModel.getId());
    }

    @Override
    public int getItemCount() {
        return loaiModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView textView;
        public ViewHolder(View iteamView){
            super(iteamView);
            image = iteamView.findViewById(R.id.image);
            textView = iteamView.findViewById(R.id.title);
        }
        private void onclick(final int id){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LoaispActivity.class);
                    intent.putExtra("name", id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
