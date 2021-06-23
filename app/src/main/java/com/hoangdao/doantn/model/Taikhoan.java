package com.hoangdao.doantn.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Taikhoan implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tentaikhoan")
    @Expose
    private String tentaikhoan;
    @SerializedName("matkhau")
    @Expose
    private String matkhau;
    @SerializedName("hinhanh")
    @Expose
    private String hinhanh;
    @SerializedName("hoten")
    @Expose
    private String hoten;
    @SerializedName("gioitinh")
    @Expose
    private String gioitinh;
    @SerializedName("ngaysinh")
    @Expose
    private String ngaysinh;
    @SerializedName("sdt")
    @Expose
    private String sdt;
    @SerializedName("diachi")
    @Expose
    private String diachi;
    @SerializedName("loaitk")
    @Expose
    private int loaitk;

    public Taikhoan(String id, String tentaikhoan, String matkhau, String hinhanh, String hoten, String gioitinh, String ngaysinh, String sdt, String diachi, int loaitk) {
        this.id = id;
        this.tentaikhoan = tentaikhoan;
        this.matkhau = matkhau;
        this.hinhanh = hinhanh;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.diachi = diachi;
        this.loaitk = loaitk;
    }

    protected Taikhoan(Parcel in) {
        id = in.readString();
        tentaikhoan = in.readString();
        matkhau = in.readString();
        hinhanh = in.readString();
        hoten = in.readString();
        gioitinh = in.readString();
        ngaysinh = in.readString();
        sdt = in.readString();
        diachi = in.readString();
        loaitk = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(tentaikhoan);
        dest.writeString(matkhau);
        dest.writeString(hinhanh);
        dest.writeString(hoten);
        dest.writeString(gioitinh);
        dest.writeString(ngaysinh);
        dest.writeString(sdt);
        dest.writeString(diachi);
        dest.writeInt(loaitk);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Taikhoan> CREATOR = new Creator<Taikhoan>() {
        @Override
        public Taikhoan createFromParcel(Parcel in) {
            return new Taikhoan(in);
        }

        @Override
        public Taikhoan[] newArray(int size) {
            return new Taikhoan[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
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

    public int getLoaitk() {
        return loaitk;
    }

    public void setLoaitk(int loaitk) {
        this.loaitk = loaitk;
    }
}