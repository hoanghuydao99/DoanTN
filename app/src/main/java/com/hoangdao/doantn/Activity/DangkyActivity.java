package com.hoangdao.doantn.Activity;

import android.Manifest;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

public class DangkyActivity extends AppCompatActivity {

    ImageView imgdangky;
    EditText edttk, edtmk, edtngaysinh, edthoten, edtsdt, edtdiachi;
    Button btxacnhan, bthuy;
    int request_code_image = 123;
    String realpath = "";
    private RadioButton radiobtnnam, radiobtnnu;
    String tentaikhoan, matkhau, hoten, gioitinh, ngaysinh, sdt, diachi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_dangky);

        anhxa();
        imgdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePhot();
            }
        });
        btxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radiobtnnam.isChecked()) {
                    gioitinh = "Nam";
                } if(radiobtnnu.isChecked()) {
                    gioitinh = "Nữ";
                }
                Register();
            }
        });
    }

    public void Register() {
        tentaikhoan = edttk.getText().toString();
        matkhau = edtmk.getText().toString();
        hoten = edthoten.getText().toString();
//        gioitinh = edtgioitinh.getText().toString();
        ngaysinh = edtngaysinh.getText().toString();
        sdt = edtsdt.getText().toString();
        diachi = edtdiachi.getText().toString();

        if (tentaikhoan.length() > 0 && matkhau.length() > 0 && realpath.length() > 0 && hoten.length() > 0 && gioitinh.length() > 0 && ngaysinh.length() > 0 && sdt.length() > 0 && diachi.length() > 0) {
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
                        Log.d("msg", message);
                        if (message.length() > 0) {
                            DataClient insertdata = APIUtils.getData();
                            Call<String> callback = insertdata.InsertData(tentaikhoan, matkhau, APIUtils.Base_Ur + "image/" + message, hoten, gioitinh, ngaysinh, sdt, diachi);
                            callback.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String resulit = response.body();
                                    Log.d("hinhanh", resulit);
                                    if (resulit.equals("Success")) {
                                        Toast.makeText(DangkyActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(DangkyActivity.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == request_code_image && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgdangky.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhxa() {
        imgdangky = (ImageView) findViewById(R.id.imgdangky);
        edttk = (EditText) findViewById(R.id.edittexttaikhoan);
        edtmk = (EditText) findViewById(R.id.edittextmatkhau);
        edthoten = (EditText) findViewById(R.id.edittexthoten);
//        edtgioitinh = (EditText) findViewById(R.id.edittextgioitinh);
        edtngaysinh = (EditText) findViewById(R.id.edittextngaysinh);
        edtsdt = (EditText) findViewById(R.id.edtsdt);
        edtdiachi = (EditText) findViewById(R.id.edtdiachi);
        radiobtnnam = findViewById(R.id.radiobtnnam);
        radiobtnnu = findViewById(R.id.radiobtnnu);
        btxacnhan = (Button) findViewById(R.id.buttonxacnhan);
        bthuy = (Button) findViewById(R.id.buttonhuy);
        bthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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