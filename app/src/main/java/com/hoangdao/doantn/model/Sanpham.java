package com.hoangdao.doantn.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sanpham implements Parcelable{
    @SerializedName("id")
    @Expose
    private int id;

    public Sanpham(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getIdloaisp() {
        return idloaisp;
    }

    public void setIdloaisp(int idloaisp) {
        this.idloaisp = idloaisp;
    }

    @SerializedName("tensp")
    @Expose
    private String tensp;
    @SerializedName("giasp")
    @Expose
    private int giasp;

    public Sanpham(int id, String tensp, int giasp, int soluong, String hinhanh, String mota, int idloaisp) {
        this.id = id;
        this.tensp = tensp;
        this.giasp = giasp;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
        this.mota = mota;
        this.idloaisp = idloaisp;
    }

    @SerializedName("soluong")
    @Expose
    private int soluong;
    @SerializedName("hinhanh")
    @Expose
    private String hinhanh;
    @SerializedName("mota")
    @Expose
    private String mota;
    @SerializedName("idloaisp")
    @Expose
    private int idloaisp;

    protected Sanpham(Parcel in) {
        id = in.readInt();
        tensp = in.readString();
        giasp = in.readInt();
        soluong = in.readInt();
        hinhanh = in.readString();
        mota = in.readString();
        idloaisp = in.readInt();
    }

    public static final Creator<Sanpham> CREATOR = new Creator<Sanpham>() {
        @Override
        public Sanpham createFromParcel(Parcel in) {
            return new Sanpham(in);
        }

        @Override
        public Sanpham[] newArray(int size) {
            return new Sanpham[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(tensp);
        parcel.writeInt(giasp);
        parcel.writeInt(soluong);
        parcel.writeString(hinhanh);
        parcel.writeString(mota);
        parcel.writeInt(idloaisp);
    }
}
