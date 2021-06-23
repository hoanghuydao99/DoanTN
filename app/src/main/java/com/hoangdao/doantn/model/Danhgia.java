package com.hoangdao.doantn.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Danhgia implements Parcelable {
    @SerializedName("iddg")
    @Expose
    private String idyt;
    @SerializedName("idtk")
    @Expose
    private String idtk;
    @SerializedName("idsp")
    @Expose
    private String idsp;

    public Danhgia(String idyt, String idtk, String idsp) {
        this.idyt = idyt;
        this.idtk = idtk;
        this.idsp = idsp;
    }

    public String getIdyt() {
        return idyt;
    }

    public void setIdyt(String idyt) {
        this.idyt = idyt;
    }

    public String getIdtk() {
        return idtk;
    }

    public void setIdtk(String idtk) {
        this.idtk = idtk;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }

    protected Danhgia(Parcel in) {
        idyt = in.readString();
        idtk = in.readString();
        idsp = in.readString();
    }

    public static final Creator<Danhgia> CREATOR = new Creator<Danhgia>() {
        @Override
        public Danhgia createFromParcel(Parcel in) {
            return new Danhgia(in);
        }

        @Override
        public Danhgia[] newArray(int size) {
            return new Danhgia[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idyt);
        parcel.writeString(idtk);
        parcel.writeString(idsp);
    }
}
