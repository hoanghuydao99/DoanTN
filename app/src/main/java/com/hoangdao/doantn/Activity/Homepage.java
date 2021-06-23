package com.hoangdao.doantn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hoangdao.doantn.Fragment.AdminFragment;
import com.hoangdao.doantn.Fragment.FavoriteFragment;
import com.hoangdao.doantn.Fragment.StoreFragment;
import com.hoangdao.doantn.Fragment.settingsFragment;
import com.hoangdao.doantn.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import static com.hoangdao.doantn.Fragment.StoreFragment.listGiohang;

public class Homepage extends AppCompatActivity {

    private static final String TAG = Homepage.class.getSimpleName();
    static BottomNavigationView bottomNavigation;
    FragmentManager fragmentManager;

    static SharedPreferences luutaikhoan;
    SharedPreferences.Editor editor;
    int loaitk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_homepage);
        EventBus.getDefault().register(this);
        luutaikhoan = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();

        bottomNavigation = findViewById(R.id.btn_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        int loaitk = luutaikhoan.getInt("loaitk",0);
        if(loaitk==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new AdminFragment()).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new StoreFragment()).commit();
        }

        if (listGiohang != null) {//nếu mạng có dữ liệu k tạo mảng mới
        } else {//nếu chưa có dữ liệu
            listGiohang = new ArrayList<>();// tạo mảng cấp phát dữ liệu
        }
        checkid();


    }
    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b){
        checkid();
    }

    public static void checkid() {
        int loaitk = luutaikhoan.getInt("loaitk",0);
        Log.d("anhdz", loaitk+"");
        if(loaitk == 1){
            bottomNavigation.getMenu().findItem(R.id.store).setVisible(false);
            bottomNavigation.getMenu().findItem(R.id.favorite).setVisible(false);
            bottomNavigation.getMenu().findItem(R.id.settings).setVisible(false);
            bottomNavigation.getMenu().findItem(R.id.admin).setVisible(true);
        }else {
            bottomNavigation.getMenu().findItem(R.id.store).setVisible(true);
            bottomNavigation.getMenu().findItem(R.id.favorite).setVisible(true);
            bottomNavigation.getMenu().findItem(R.id.settings).setVisible(true);
            bottomNavigation.getMenu().findItem(R.id.admin).setVisible(false);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.store:
                            fragment = new StoreFragment();
                            break;
                        case R.id.favorite:
                            fragment = new FavoriteFragment();
                            break;
                        case R.id.settings:
                            fragment = new settingsFragment();
                            break;
                        case R.id.admin:
                            fragment = new AdminFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();

                    return true;
                }
            };

}