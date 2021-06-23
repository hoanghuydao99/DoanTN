package com.hoangdao.doantn.Retrofit;

import com.hoangdao.doantn.model.Danhgia;
import com.hoangdao.doantn.model.Diachi;
import com.hoangdao.doantn.model.Donhang;
import com.hoangdao.doantn.model.Favorite;
import com.hoangdao.doantn.model.Taikhoan;
import com.hoangdao.doantn.model.LoaiModel;
import com.hoangdao.doantn.model.Sanpham;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

//Quản lý các phương thức đẩy lên server hoặc chạy dữ liệu về thì sẽ chứa trong class này
public interface DataClient {

    @Multipart
    @POST("uploadhinhanh.php")
    Call<String> UploadPhoto(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("insert.php")
    Call<String> InsertData(@Field("tentaikhoan") String tentaikhoan,
                            @Field("matkhau") String matkhau,
                            @Field("hinhanh") String hinhanh,
                            @Field("hoten") String hoten,
                            @Field("gioitinh") String gioitinh,
                            @Field("ngaysinh") String ngaysinh,
                            @Field("sdt") String sdt,
                            @Field("diachi") String diachi);
    @FormUrlEncoded
    @POST("insertsanpham.php")
    Call<String> InsertSanPham(@Field("tensp") String tensp,
                               @Field("giasp") String giasp,
                               @Field("soluong") String soluong,
                               @Field("hinhanh") String hinhanh,
                               @Field("mota") String mota,
                               @Field("idloaisp") String idloaisp,
                               @Field("idkh") String idkh);

    @FormUrlEncoded
    @POST("login.php")
    Call<List<Taikhoan>> Logindata(@Field("tentaikhoan") String tentaikhoan,
                                   @Field("matkhau") String matkhau);

    @FormUrlEncoded
    @POST("getsanpham.php")
    Call<List<Sanpham>> getsanpham(@Field("idForm") String idForm);

    @FormUrlEncoded
    @POST("getProductbyKey.php")
    Call<List<Sanpham>> getProductbyKey(@Field("category_id") int id);

    @FormUrlEncoded
    @POST("getloaisp.php")
    Call<List<LoaiModel>> getloaisp(@Field("idForm1") String idForm1);

    @FormUrlEncoded
    @POST("getspsale.php")
    Call<List<Sanpham>> getsanphamsale(@Field("idForm2") String idForm2);

    @FormUrlEncoded
    @POST("donhang.php")
    Call<String> donhang(@Field("idkh") String idkh,
                         @Field("tenkh") String tenkh,
                         @Field("sdt") String sdt,
                         @Field("diachi") String diachi,
                         @Field("tongtien") String tongtien);

    @FormUrlEncoded
    @POST("chitietdonhang.php")
    Call<String> chitietdonhang(@Field("iddh") String iddh,
                                @Field("idsp") int idsp,
                                @Field("soluong") int soluong,
                                @Field("tensp") String tensp,
                                @Field("giasp") int giasp);
    @FormUrlEncoded
    @POST("yeuthich.php")
    Call<String> postyeuthich(@Field("idtk") String idtk,
                              @Field("idsp") String idsp);

    @FormUrlEncoded
    @POST("getyeuthich.php")
    Call<List<Favorite>> getyeuthich(@Field("idtk") String idtk,
                                     @Field("idsp") String idsp);

    @GET("deleteyt.php")
    Call<String> deleteFavorite(@Query("idsp") String idsp,
                                @Query("idtk") String idtk);

    @FormUrlEncoded
    @POST("getdiachi.php")
    Call<List<Diachi>> getdiachi(@Field("makh") String makh);

    @FormUrlEncoded
    @POST("themdiachi.php")
    Call<String> postdiachi(@Field("makh") String makh,
                            @Field("hoten") String hoten,
                            @Field("sdt") String sdt,
                            @Field("diachi") String diachi);
    @FormUrlEncoded
    @POST("qldonhangkh.php")
    Call<List<Donhang>> postdonhangkh(@Field("idkh") String idkh);

    @FormUrlEncoded
    @POST("huydhkh.php")
    Call<String> huydh(@Field("query") String query);

    @FormUrlEncoded
    @POST("getdatayt.php")
    Call<List<Favorite>> yt(@Field("idtk") String idtk);

    @FormUrlEncoded
    @POST("getspyt.php")
    Call<List<Sanpham>> getspyt(@Field("id") int id);

    @FormUrlEncoded
    @POST("qlspadmin.php")
    Call<List<Sanpham>> getspadmin(@Field("idForm") String idForm);

    @FormUrlEncoded
    @POST("timkiem.php")
    Call<List<Sanpham>> gettimkiem(@Field("query") String query);

    @FormUrlEncoded
    @POST("xoaspadmin.php")
    Call<String> deletesp(@Field("id") int id);

    @FormUrlEncoded
    @POST("qlbaidang.php")
    Call<List<Sanpham>> getbaidang(@Field("idtk") String idtk);

    @FormUrlEncoded
    @POST("qldonhangadmin.php")
    Call<String> qldonhangkh(@Field("query") String query);

    @FormUrlEncoded
    @POST("getdhadmin.php")
    Call<List<Donhang>> getalldonhang(@Field("hoang") String hoang);

    @FormUrlEncoded
    @POST("danhgia.php")
    Call<String> postdanhgia(@Field("idtk") String idtk,
                              @Field("idsp") String idsp);

    @FormUrlEncoded
    @POST("getdanhgia.php")
    Call<List<Danhgia>> getdanhgia(@Field("idtk") String idtk,
                                   @Field("idsp") String idsp);

    @GET("deletedg.php")
    Call<String> deletedg(@Query("idsp") String idsp,
                                @Query("idtk") String idtk);


    @FormUrlEncoded
    @POST("getdatadg.php")
    Call<List<Danhgia>> dg(@Field("idtk") String idtk);

    @FormUrlEncoded
    @POST("getspdg.php")
    Call<List<Sanpham>> getspdg(@Field("id") int id);
}
