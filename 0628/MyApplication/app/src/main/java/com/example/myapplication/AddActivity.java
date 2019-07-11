package com.example.myapplication;
//http://blog.kenyang.net/2010/10/09/android-camera 相機功能待試用
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private ImageButton imgbtn_main, imgbtn_record, imgbtn_add, imgbtn_chat, imgbtn_account;
    private Button btn_takepic,btn_openalbum;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private EditText exprtydate , pickuptime;
    public static final int CROP_PHOTO = 2;
    private ImageView Imgview_uploadfood;
    //圖片路徑
    private Uri imageUri;
    //圖片名稱
    private String filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imgbtn_main = findViewById(R.id.imgbtn_main);
        imgbtn_record = findViewById(R.id.imgbtn_record);
        imgbtn_add = findViewById(R.id.imgbtn_add);
        imgbtn_chat = findViewById(R.id.imgbtn_chat);
        imgbtn_account = findViewById(R.id.imgbtn_account);


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
                        exprtydate.setText(year+"-"+(month+1)+"-"+dayOfMonth);
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
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        pickuptime.setText(hourOfDay + "：" + minute);
                    }
                },hour,minute,false);
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
    }




}
