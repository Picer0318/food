package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    private EditText edt_userid;
    private EditText edt_userpw;
    private Button btn_goRegiste;
    private Button btn_login;
    private String uid;
    private String pw;
    private Button mBnFood;
    private Button mBtnRecord, mBtnAdd, mBtnChat, mBtnAccount;
    private EditText  phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText)findViewById(R.id.edt_userid);//輸入帳號
        password = (EditText)findViewById(R.id.edt_userpw);//輸入密碼

        btn_goRegiste = findViewById(R.id.btn_goRegiste);


        btn_goRegiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisteActivity.class);
                startActivity(intent);
            }
        });



        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();//執行login()
            }
        });





    }
    //login
    public void login(){
        StringRequest request = new StringRequest(Request.Method.POST, "https://roiliest-loaves.000webhostapp.com/connect/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("1")){
                    SharedPreferences setting = getSharedPreferences("uid",MODE_PRIVATE);
                    setting.edit().putString("PREF_USERID",phone.getText().toString()).apply();
                    Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);


                }else{
                    Toast.makeText(getApplicationContext(), "登入失敗", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("phone",phone.getText().toString().trim());
                params.put("password",password.getText().toString().trim());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
    //login



}
