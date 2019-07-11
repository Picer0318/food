package com.example.myapplication;
//http://blog.kenyang.net/2010/10/09/android-camera 相機功能待試用
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private ImageButton imgbtn_main, imgbtn_record, imgbtn_add, imgbtn_chat, imgbtn_account;
    private Button btn_takepic,btn_openalbum,btn_submit;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private EditText exprtydate , pickuptime , edt_foodname,edt_address ,edt_detail;
    public static final int CROP_PHOTO = 2;
    private ImageView Imgview_uploadfood;
    //圖片路徑
    private Uri imageUri;
    //圖片名稱
    private String filename;
    Spinner spn_foodlist , spn_arealist;
    RequestQueue requestQueue;
    String insertUrl = "https://roiliest-loaves.000webhostapp.com/connect/insertFood.php";
    String phone;

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
        spn_foodlist = findViewById(R.id.spn_foodlist);
        spn_arealist = findViewById(R.id.spn_arealist);
        btn_submit = findViewById(R.id.btn_submit);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences setting = getSharedPreferences("uid",MODE_PRIVATE);
        phone = setting.getString("PREF_USERID","");



        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
//---------------------------------------------------------------------------------------------------------------------------------------------------
        //跳至main
        imgbtn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至record
        imgbtn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,RecordActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至chat
        imgbtn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,ChatActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至account
        imgbtn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,AccountActivity.class);
                startActivity(intent);
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
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month<9 && dayOfMonth<9){
                            exprtydate.setText(year+"-" +"0"+ (month+1)+"-"+"0"+dayOfMonth);
                        }else if(month<9){
                            exprtydate.setText(year+"-" +"0"+ (month+1)+"-"+dayOfMonth);
                        }else if(dayOfMonth<9){
                            exprtydate.setText(year+"-" + (month+1)+"-"+"0"+dayOfMonth);
                        }else{
                            exprtydate.setText(year+"-" + (month+1)+"-"+dayOfMonth);
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
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute ) {
                        pickuptime.setText(hourOfDay + ":" + minute);
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
                //圖片名稱格式
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                //建立File 儲存照片SD卡根目錄 存至DCIM
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                filename="Food n Go";
                File outputImage = new File(path,filename+".jpg");

                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e){
                    e.printStackTrace();
                }
                //File轉為Uril並照相
                imageUri = Uri.fromFile(outputImage);
                //開啟相機
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //照片輸出
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent,CROP_PHOTO);
            }
            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
                AddActivity.super.onActivityResult(requestCode, resultCode, data);
                if(resultCode != RESULT_OK){
                    Toast.makeText(AddActivity.this, "ActivityResult resultCode error", Toast.LENGTH_SHORT).show();
                        return;
                }
                switch (requestCode){
                    case CROP_PHOTO:
                    try{

                        //圖片解析為Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        //刷新DCIM 儲存檔案至DCIM
                        Intent intentBcc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intentBcc.setData(imageUri);
                        Imgview_uploadfood.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    break;
                    default:
                        break;
                }
                }


        });

        //找尋Button按鈕
        Button button = (Button)findViewById(R.id.btn_openalbum);
        //設定按鈕內文字
        button.setText("選擇圖片");
        //設定按鈕監聽式
        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent();
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action                                            //會開啟選取圖檔視窗讓您選取手機內圖檔
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //取得相片後返回本畫面
                startActivityForResult(intent, 1);
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
                        ||pickuptime.getText().toString().equals("")) {
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
                            parameters.put("food_type", spn_foodlist.getSelectedItem().toString());
                            parameters.put("food_name", edt_foodname.getText().toString());
                            parameters.put("food_area", spn_arealist.getSelectedItem().toString());
                            parameters.put("food_address", edt_address.getText().toString());
                            parameters.put("expiry_date", exprtydate.getText().toString());
                            parameters.put("pickup_time", pickuptime.getText().toString());
                            parameters.put("detail", edt_detail.getText().toString());
                            parameters.put("phone", phone);


                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                    Toast.makeText(AddActivity.this,"新增成功",Toast.LENGTH_SHORT).show();

                }

            }
        });

        //送出

    }

    //取得相片後返回的監聽式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //當使用者按下確定後
        if (resultCode == RESULT_OK) {
            //取得圖檔的路徑位置
            Uri uri = data.getData();
            //寫log
            Log.e("uri", uri.toString());
            //抽象資料的接口
            ContentResolver cr = this.getContentResolver();
            try {
                //由抽象資料接口轉換圖檔路徑為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //取得圖片控制項ImageView
                ImageView imageView = (ImageView) findViewById(R.id.Imgview_uploadfood);
                // 將Bitmap設定到ImageView
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
