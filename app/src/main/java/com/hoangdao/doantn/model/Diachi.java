package com.hoangdao.doantn.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Diachi implements Parcelable {
    int id;
    String makh;
    String hoten;
    String sdt;
    String diachi;

    public Diachi(int id, String makh, String hoten, String sdt, String diachi) {
        this.id = id;
        this.makh = makh;
        this.hoten = hoten;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    protected Diachi(Parcel in) {
        id = in.readInt();
        makh = in.readString();
        hoten = in.readString();
        sdt = in.readString();
        diachi = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(makh);
        dest.writeString(hoten);
        dest.writeString(sdt);
        dest.writeString(diachi);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Diachi> CREATOR = new Creator<Diachi>() {
        @Override
        public Diachi createFromParcel(Parcel in) {
            return new Diachi(in);
        }

        @Override
        public Diachi[] newArray(int size) {
            return new Diachi[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}