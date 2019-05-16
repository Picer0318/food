package com.example.food;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText date;
    DatePickerDialog datePickerDialog;
    private Button mBnFood;
    private ImageButton mBtnRecord, mBtnAdd, mBtnChat, mBtnAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mBnFood = findViewById(R.id.food);
        mBtnRecord =findViewById(R.id.record);
        mBtnAdd =findViewById(R.id.add);
        mBtnChat =findViewById(R.id.chat);
        mBtnAccount =findViewById(R.id.account);


        //跳到food頁面
        mBnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
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

        //時間選擇
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

        //時間選擇
    }
    //到期日選擇
    /*private Button.OnClickListener btnExpiry_dateOnclick = new Button.OnClickListener(){
        public void onClick(View v){
            mTxtExpiry_date.setText("");

            Calendar now = Calendar.getInstance();

            DatePickerDialog datePickDlg = new DatePickerDialog(AddActivity.this,
                    datePickerDlgOnDateSet,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH));
            datePickDlg.setTitle("選擇日期");
            datePickDlg.setMessage("請選擇適合您的日期");
            datePickDlg.setCancelable(false);

            datePickDlg.show();
        }
    };
    private DatePickerDialog.OnDateSetListener  datePickerDlgOnDateSet =
            new DatePickerDialog.OnDateSetListener() {
        public void onDateSet (DatePicker view, int year, int monthOfYear,int dayOfMonth){
            mTxtExpiry_date.setText(Integer.toString(year)+"年"+
                    Integer.toString(monthOfYear+1)+"月"+
                    Integer.toString(dayOfMonth)+"日");
        }

    };*/
}
