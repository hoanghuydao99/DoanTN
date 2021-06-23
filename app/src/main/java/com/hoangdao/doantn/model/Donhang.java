package com.hoangdao.doantn.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Donhang implements Parcelable {
    int iddh;
    int idkh;
    String ngaydat;
    String tenkh;
    String sdt;
    String diachi;
    int tongtien;
    String trangthai;

    public Donhang(int iddh, int idkh, String ngaydat, String tenkh, String sdt, String diachi, int tongtien, String trangthai) {
        this.iddh = iddh;
        this.idkh = idkh;
        this.ngaydat = ngaydat;
        this.tenkh = tenkh;
        this.sdt = sdt;
        this.diachi = diachi;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public int getIddh() {
        return iddh;
    }

    public void setIddh(int iddh) {
        this.iddh = iddh;
    }

    public int getIdkh() {
        return idkh;
    }

    public void setIdkh(int idkh) {
        this.idkh = idkh;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
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

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    protected Donhang(Parcel in) {
        iddh = in.readInt();
        idkh = in.readInt();
        ngaydat = in.readString();
        tenkh = in.readString();
        sdt = in.readString();
        diachi = in.readString();
        tongtien = in.readInt();
        trangthai = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iddh);
        dest.writeInt(idkh);
        dest.writeString(ngaydat);
        dest.writeString(tenkh);
        dest.writeString(sdt);
        dest.writeString(diachi);
        dest.writeInt(tongtien);
        dest.writeString(trangthai);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Donhang> CREATOR = new Creator<Donhang>() {
        @Override
        public Donhang createFromParcel(Parcel in) {
            return new Donhang(in);
        }

        @Override
        public Donhang[] newArray(int size) {
            return new Donhang[size];
        }
    };
}
