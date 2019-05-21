package com.example.food;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserid;
    private EditText edtPasswd;
    private String uid;
    private String pw;
    private Button mBtnRegiste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserid = findViewById(R.id.mEdtUserid);
        edtPasswd = findViewById(R.id.mEdtUserpw);
        mBtnRegiste = findViewById(R.id.mBtnRegiste);
        //進入註冊頁面
        mBtnRegiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisteActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        //------------------------------------------



    }

    //判斷登入是否成功
    public void login(View view) {
        uid = edtUserid.getText().toString();
        pw = edtPasswd.getText().toString();
        if (uid.equals("123") && pw.equals("123")) {
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("登入失敗")
                    .setPositiveButton("OK", null)
                    .show();
        }


    }
    //----------------------------------------------------
}


