package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class RegisteActivity extends Activity {

    private EditText edt_userid;
    private EditText edt_userpw;
    private CheckBox chkBox_agree;
    private Button btn_cancel;
    private Button btn_registe;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        //取消註冊回登入頁面
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisteActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        //----------------------------------------------------------------------------------------------------------------------------------------
        //判斷勾選checkbox及註冊按鈕可按
        chkBox_agree = findViewById(R.id.chkBox_agree);
        btn_registe = findViewById(R.id.btn_registe);
        btn_registe.setEnabled(false);
        chkBox_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btn_registe.setEnabled(true);
                }
                else{
                    btn_registe.setEnabled(false);
                }
            }

        });
        //--------------------------------------------------------------------------------------------------------------------------------------
        //按註冊鈕後進入主頁
        btn_registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisteActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------
    }
}
