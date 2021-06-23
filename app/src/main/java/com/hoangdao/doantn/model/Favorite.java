package com.hoangdao.doantn.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite implements Parcelable {
    @SerializedName("idyt")
    @Expose
    private String idyt;
    @SerializedName("idtk")
    @Expose
    private String idtk;
    @SerializedName("idsp")
    @Expose
    private String idsp;

    protected Favorite(Parcel in) {
        idyt = in.readString();
        idtk = in.readString();
        idsp = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

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