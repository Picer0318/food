package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.icu.util.Output;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.service.media.CameraPrewarmService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.Bitmap.createBitmap;

public class AddActivity extends AppCompatActivity {

    ImageButton imgbtn_main, imgbtn_record, imgbtn_add, imgbtn_chat, imgbtn_account;
    Button btn_takepic,btn_openalbum,btn_submit;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private EditText exprtydate , pickuptime , edt_foodname,edt_address ,edt_detail;
    private ImageView Imgview_uploadfood;
    Spinner spn_foodlist , spn_arealist;
    RequestQueue requestQueue;
    String insertUrl = "https://roiliest-loaves.000webhostapp.com/connect/insertFood2.php";
    String phone;
    Bitmap bitmap;
    final int CODE_GALLERY_REQUEST = 999;
    final int CODE_CAMERA_REQUEST = 998;
    String imageData;
    final int PERMISSION_CODE = 997;
    Uri image_uri;
    String encodedImage;
    String bln = "1";
    String bln_collect = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imgbtn_main = findViewById(R.id.imgbtn_main);
        imgbtn_record = findViewById(R.id.imgbtn_record);
        imgbtn_add = findViewById(R.id.imgbtn_add);
        imgbtn_chat = findViewById(R.id.imgbtn_chat);
        imgbtn_account = findViewById(R.id.imgbtn_account);
        edt_foodname = findViewById(R.id.foodname);
        edt_address = findViewById(R.id.address);
        edt_detail = findViewById(R.id.edt_detail);
        spn_foodlist = findViewById(R.id.spn_kindOfFood);
        spn_arealist = findViewById(R.id.spn_arealist);
        btn_submit = findViewById(R.id.btn_check);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences setting = getSharedPreferences("uid",MODE_PRIVATE);
        phone = setting.getString("PREF_USERID","");
        btn_openalbum = findViewById(R.id.btn_openalbum);








        //StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //StrictMode.setVmPolicy(builder.build());
        //builder.detectFileUriExposure();
//---------------------------------------------------------------------------------------------------------------------------------------------------
        //跳至main
        imgbtn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至record
        imgbtn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,RecordActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至chat
        imgbtn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,ChatActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至account
        imgbtn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,AccountActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
//--------------------------------------------------------------------------------------------------------------------------------------------------
        //用日曆選擇日期
        exprtydate = findViewById(R.id.expirydate);
        exprtydate.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int  year = calendar.get(Calendar.YEAR);
            int  month = calendar.get(Calendar.MONTH);
            int  day = calendar.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month<9 && dayOfMonth<9){
                            //exprtydate.setText(yearToString + "-" + "0" + monthToString+ "-" + "0" + dayOfMonthToString);
                            exprtydate.setText(String.format("%d-0%d-0%d",year,month+1,dayOfMonth));
                        }else if(month<9){
                            //exprtydate.setText(year + R.string.minus + R.string.zero + (month+1)+ R.string.minus + dayOfMonth);
                            exprtydate.setText(String.format("%d-0%d-%d",year,month+1,dayOfMonth));
                        }else if(dayOfMonth<9){
                            //exprtydate.setText(year + R.string.minus + (month+1) + R.string.minus + R.string.zero + dayOfMonth);
                            exprtydate.setText(String.format("%d-%d-0%d",year,month+1,dayOfMonth));
                        }else{
                            //exprtydate.setText(year + R.string.minus + (month+1) + R.string.minus + dayOfMonth);
                            exprtydate.setText(String.format("%d-%d-%d",year,month+1,dayOfMonth));
                        }
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //用時鐘選擇時間
        pickuptime = findViewById(R.id.pickuptime);
        pickuptime.setOnClickListener(new View.OnClickListener() {
            Calendar calendar=Calendar.getInstance();
            int hour=calendar.get(Calendar.HOUR);
            int minute=calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute ) {
                        //pickuptime.setText(hourOfDay + R.string.colon + minute);
                        if(hourOfDay<10 && minute<10){
                            pickuptime.setText(String.format("0%d:0%d",hourOfDay,minute));
                        }else if(hourOfDay<10){
                            pickuptime.setText(String.format("0%d:%d",hourOfDay,minute));
                        }else if(minute<10){
                            pickuptime.setText(String.format("%d:0%d",hourOfDay,minute));
                        }else{
                            pickuptime.setText(String.format("%d:%d",hourOfDay,minute));
                        }
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------
        //拍照功能
        btn_takepic = findViewById(R.id.btn_takepic);
        Imgview_uploadfood = findViewById(R.id.Imgview_uploadfood);

        btn_takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    String[] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission,PERMISSION_CODE);
                }else{
                    openCamera();
                }

            }
        });

        //設定按鈕內文字
        btn_openalbum.setText("選擇圖片");
        //設定按鈕監聽式
        btn_openalbum.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );

            }

        });

        //送出
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_foodname.getText().toString().trim().equals("")
                        || edt_address.getText().toString().trim().equals("")
                        || edt_detail.getText().toString().trim().equals("")
                        ||exprtydate.getText().toString().trim().equals("")
                        ||pickuptime.getText().toString().equals("")
                        ||bitmap==null){
                    Toast.makeText(AddActivity.this,"所有欄位必須填寫",Toast.LENGTH_SHORT).show();
                }else{
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> parameters = new HashMap<>();
                           imageData  = imageToString(bitmap);

                            parameters.put("food_type", spn_foodlist.getSelectedItem().toString());
                            parameters.put("food_name", edt_foodname.getText().toString());
                            parameters.put("food_area", spn_arealist.getSelectedItem().toString());
                            parameters.put("food_address", edt_address.getText().toString());
                            parameters.put("expiry_date", exprtydate.getText().toString());
                            parameters.put("pickup_time", pickuptime.getText().toString());
                            parameters.put("detail", edt_detail.getText().toString());
                            parameters.put("phone", phone);
                            parameters.put("image",imageData);
                            parameters.put("boolean",bln);
                            parameters.put("bln_collect",bln_collect);


                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                    Toast.makeText(AddActivity.this,"新增成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        //送出

    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE,null);
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,CODE_CAMERA_REQUEST);

    }

    //取得相片後返回的監聽式
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case CODE_GALLERY_REQUEST:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent,"Select Image"),CODE_GALLERY_REQUEST);
                } else{
                    Toast.makeText(getApplicationContext(),"don't access gallery",Toast.LENGTH_LONG).show();
                }
                return;
            }
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else{
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_LONG).show();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath  = data.getData();
            assert filePath != null;
            try {

                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                Imgview_uploadfood.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CODE_CAMERA_REQUEST && resultCode == RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            assert extras != null;
            bitmap = (Bitmap)extras.get("data");
            Imgview_uploadfood.setImageBitmap(bitmap);
            /*
            try {
                InputStream inputStream = getContentResolver().openInputStream(image_uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                Imgview_uploadfood.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
*/
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }
    //
}
