package com.hoangdao.doantn.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangdao.doantn.Activity.Dathang;
import com.hoangdao.doantn.Activity.Sodiachi;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.model.Diachi;
import com.hoangdao.doantn.model.LoaiModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SodiachiAdapter extends RecyclerView.Adapter<SodiachiAdapter.ViewHolder> {

    private Sodiachi context;
    ArrayList<Diachi> listdiachi;

    public SodiachiAdapter(Sodiachi context, ArrayList<Diachi> listdiachi) {
        this.context = context;
        this.listdiachi = listdiachi;
    }

    @NonNull
    @Override
    public SodiachiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_diachi,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Diachi diachi = listdiachi.get(position);
        holder.tvhotenkh.setText(diachi.getHoten());
        holder.tvsdt.setText(diachi.getSdt());
        holder.tvdiachi.setText(diachi.getDiachi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Dathang.class);
                intent.putExtra("hoten", listdiachi.get(position).getHoten());
                intent.putExtra("sdt", listdiachi.get(position).getSdt());
                intent.putExtra("diachi", listdiachi.get(position).getDiachi());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdiachi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvhotenkh, tvsdt,tvcaidat, tvdiachi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvhotenkh = itemView.findViewById(R.id.tvhotenkh);
            tvsdt = itemView.findViewById(R.id.tvsdtkh);
            tvdiachi = itemView.findViewById(R.id.tvdiachikh);
            tvcaidat = itemView.findViewById(R.id.caidat);
        }
    }


    private void XacnhanXoa(final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa địa chỉ này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                context.DeleteDiachi(id);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        dialogXoa.show();
    }
}