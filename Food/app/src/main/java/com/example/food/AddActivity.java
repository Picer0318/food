package com.example.food;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity{

    private static final String DB_FILE = "food.db",DB_TABLE =  "additem";
    private SQLiteDatabase mFoodDb;

    private RelativeLayout layout;
    private EditText date, time;
    private EditText mEdtDetail;
    private Button mBtnAdditem;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private Spinner mSpnProduct , mSpnArea;
    private String msProduct , msArea;
    private Button mBnFood;
    private ImageButton mBtnRecord, mBtnAdd, mBtnChat, mBtnAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //資料庫
        FoodDbOpenHelper foodDbOpenHelper = new FoodDbOpenHelper(getApplicationContext(),DB_FILE, null,1);
        mFoodDb = foodDbOpenHelper.getWritableDatabase();
        //資料庫
        mBnFood = findViewById(R.id.food);
        mBtnRecord =findViewById(R.id.record);
        mBtnAdd =findViewById(R.id.add);
        mBtnChat =findViewById(R.id.chat);
        mBtnAccount =findViewById(R.id.account);
        mEdtDetail = findViewById(R.id.Detail) ;
        mBtnAdditem = findViewById(R.id.submit);
        mBtnAdditem.setOnClickListener(submitOnclick);
        mSpnProduct = findViewById(R.id.spnProduct);
        mSpnArea = findViewById(R.id.spnArea);

        //跳到food頁面
        mBnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //跳到record頁面
        mBtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,RecordActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //跳到add頁面
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,AddActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //跳到chat頁面
        mBtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,ChatActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //跳到account頁面
        mBtnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,AccountActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //日期選擇
        date = findViewById(R.id.Date);
        date.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int  year = calendar.get(Calendar.YEAR);
            int  month = calendar.get(Calendar.MONTH);
            int  day = calendar.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //日期選擇

        //時間選擇
        time = findViewById(R.id.Time);
        time.setOnClickListener(new View.OnClickListener() {
            Calendar calendar=Calendar.getInstance();
            int hour=calendar.get(Calendar.HOUR);
            int minute=calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            time.setText(hourOfDay + "：" + minute);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        //時間選擇

        //hide keyboard
        layout = findViewById(R.id.addlayout);
        date = findViewById(R.id.Date);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
        //hide keyboard

        //
        Cursor cursor = mFoodDb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        mFoodDb = foodDbOpenHelper.getWritableDatabase();

        if(cursor != null){
            if(cursor.getCount()==0)
                mFoodDb.execSQL("CREATE TABLE " + DB_TABLE + " (" +"_id INTEGER PRIMARY KEY,"+ "product TEXT ," + "area TEXT,"  + "date TEXT NOT NULL," + "time TEXT,"+"detail TEXT);");

            cursor.close();
        }
        //
        //
        mSpnProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                msProduct = mSpnProduct.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                msArea = mSpnArea.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFoodDb.close();
    }

    private View.OnClickListener submitOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues newRow = new ContentValues();
            newRow.put("product",msProduct);
            newRow.put("area",msArea);
            newRow.put("date", date.getText().toString());
            newRow.put("time", time.getText().toString());
            newRow.put("detail",mEdtDetail.getText().toString());
            mFoodDb.insert(DB_TABLE, null , newRow);
            Toast.makeText(AddActivity.this,"新增成功",Toast.LENGTH_SHORT).show();
        }
    };

}
