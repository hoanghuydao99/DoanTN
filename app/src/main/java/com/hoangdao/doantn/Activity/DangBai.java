package com.hoangdao.doantn.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hoangdao.doantn.R;
import com.hoangdao.doantn.Retrofit.APIUtils;
import com.hoangdao.doantn.Retrofit.DataClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangBai extends AppCompatActivity {

    ImageView imgdangbai, imgback;
    EditText edttensp, edtgiasp, edtsoluong, edtmota, edtidloaisp;
    Button btnthem;
    int request_code_image = 123;
    String realpath = "";
    String tensp, giasp, soluong, mota, idloaisp;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dangbai);
        Anhxa();
        initShare();
        imgdangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePhot();
            }
        });
        ButtonThem();
    }

    public void initShare() {
        sharedPreferences = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    private void ButtonThem() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tensp = edttensp.getText().toString();
                giasp = edtgiasp.getText().toString();
                soluong = edtsoluong.getText().toString();
                mota = edtmota.getText().toString();
                idloaisp = edtidloaisp.getText().toString();

                final String idkh = sharedPreferences.getString("id", "");
                if (tensp.length() > 0 && giasp.length() > 0 && realpath.length() > 0 && soluong.length() > 0 && mota.length() > 0 && idloaisp.length() > 0) {
                    File file = new File(realpath);
                    String file_path = file.getAbsolutePath();
                    String[] mangtenfile = file_path.split("\\.");

                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                    final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);
                    DataClient dataClient = APIUtils.getData();
                    Call<String> callback = dataClient.UploadPhoto(body);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response != null) {
                                String message = response.body();

                                if (message.length() > 0) {
                                    DataClient insertdata = APIUtils.getData();
                                    Call<String> callback = insertdata.InsertSanPham(tensp, giasp, soluong, APIUtils.Base_Ur + "image/" + message, mota, idloaisp, idkh);
                                    callback.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String resulit = response.body();
                                            Log.d("dhh", resulit);
                                            if (resulit.equals("Success")) {
                                                Toast.makeText(DangBai.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Log.e("error", t.getMessage());
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("dhh", t.getMessage());

                        }
                    });
                } else {
                    Toast.makeText(DangBai.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Anhxa() {
        imgdangbai = findViewById(R.id.imgdangbai);
        imgback = findViewById(R.id.back);
        edttensp = findViewById(R.id.edttensp);
        edtgiasp = findViewById(R.id.edtgiasp);
        edtsoluong = findViewById(R.id.edtsoluong);
        edtmota = findViewById(R.id.edtmota);
        edtidloaisp = findViewById(R.id.edtidloaisp);
        btnthem = findViewById(R.id.btthem);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == request_code_image && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgdangbai.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    public void ChoosePhot() {
        Log.d("log", handlerPermission() + "");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, request_code_image);
    }

    private boolean handlerPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("log", "Permission is granted");
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("log", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("log", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }
}