package com.hoangdao.doantn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoangdao.doantn.Activity.GioHang;
import com.hoangdao.doantn.R;
import com.hoangdao.doantn.model.Giohangmua;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.hoangdao.doantn.Activity.GioHang.tongtien;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohangmua> listGioHangAdapter;

    public GiohangAdapter(Context context, ArrayList<Giohangmua> ListCart) {
        this.context = context;
        this.listGioHangAdapter = ListCart;
    }

    @Override
    public int getCount() {
        return listGioHangAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;

    }

    private class ViewHolder {
        public TextView tvtengiohang, tvgiagiohang, tvvalues;
        public ImageView imggiohang, btnminus, btnplus, btxoa;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang, null);
            holder = new ViewHolder();
            holder.tvtengiohang = convertView.findViewById(R.id.tengiohang);
            holder.tvgiagiohang = convertView.findViewById(R.id.giagiohang);
            holder.imggiohang = convertView.findViewById(R.id.imggiohang);
            holder.btnminus = convertView.findViewById(R.id.btnminus);
            holder.tvvalues = convertView.findViewById(R.id.tvvalues);
            holder.btnplus = convertView.findViewById(R.id.btnplus);
            holder.btxoa = convertView.findViewById(R.id.btxoa);
            holder.btxoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listGioHangAdapter.remove(position);
                    notifyDataSetChanged();
                    tongtien();
                }
            });
            convertView.setTag(holder);
        } else { //nếu có dữ liệu chỉ cần gán lại
            holder = (ViewHolder) convertView.getTag();
        }
        final Giohangmua gioHang = listGioHangAdapter.get(position);
        Picasso.get().load(gioHang.getHinhsp()).into(holder.imggiohang);
//        Log.d(gioHang.getHinhsp(), "gethinh: ");
        holder.tvtengiohang.setText(gioHang.getTensp());
//        Log.d(gioHang.getTensp(), "getten: ");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvgiagiohang.setText(decimalFormat.format(gioHang.getGiasp()) + " VNĐ");
        holder.tvvalues.setText(gioHang.getSoluongsp() + "");

        int soLuong = Integer.parseInt(holder.tvvalues.getText().toString());
        checkSoLuong(soLuong, holder.btnplus, holder.btnminus);
        holder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHang.setSoluongsp(gioHang.getSoluongsp() + 1);
                checkSoLuong(gioHang.getSoluongsp(), holder.btnplus, holder.btnminus);
                holder.tvvalues.setText(gioHang.getSoluongsp() + "");
                tongtien();
            }
        });
        holder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHang.setSoluongsp(gioHang.getSoluongsp() - 1);
                checkSoLuong(gioHang.getSoluongsp(), holder.btnplus, holder.btnminus);
                holder.tvvalues.setText(gioHang.getSoluongsp() + "");
                tongtien();
            }
        });
        return convertView;

    }

    public void checkSoLuong(int sl, ImageView btnCong, ImageView btnTru) {
        if (sl <= 1) {
            btnTru.setEnabled(false);
            btnCong.setEnabled(true);
        } else if (sl >= 1000) {
            btnCong.setEnabled(false);
            btnTru.setEnabled(true);
        } else {
            btnTru.setEnabled(true);
            btnCong.setEnabled(true);
        }
    }
}