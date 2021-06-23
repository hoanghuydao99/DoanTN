package com.hoangdao.doantn.Retrofit;
//gửi và nhận dữ liệu về để chứa trong class interface
public class APIUtils {
    public static final String Base_Ur = "http://192.168.1.2/doantn/";

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Ur).create(DataClient.class);
    }
}
