package com.hoangdao.doantn.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LoaiModel implements Parcelable {
    public int id;
    public String tenloaisp;
    public String hinhanh;

    protected LoaiModel(Parcel in) {
        id = in.readInt();
        tenloaisp = in.readString();
        hinhanh = in.readString();
    }

    public static final Creator<LoaiModel> CREATOR = new Creator<LoaiModel>() {
        @Override
        public LoaiModel createFromParcel(Parcel in) {
            return new LoaiModel(in);
        }

        @Override
        public LoaiModel[] newArray(int size) {
            return new LoaiModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public String getHinhanhloaisp() {
        return hinhanh;
    }

    public void setHinhanhloaisp(String hinhanhloaisp) {
        this.hinhanh = hinhanhloaisp;
    }

    public LoaiModel(int id, String tenloaisp, String hinhanhloaisp) {
        this.id = id;
        this.tenloaisp = tenloaisp;
        this.hinhanh = hinhanhloaisp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(tenloaisp);
        parcel.writeString(hinhanh);
    }
}
