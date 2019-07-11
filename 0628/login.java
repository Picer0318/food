//在AndroidManifest <manifest></manifest> 新增   <uses-permission android:name="android.permission.INTERNET"></uses-permission>
//在build.grade(Module:app) dependencies{}新增     implementation 'com.android.volley:volley:1.1.1'

package com.example.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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


public class HomeActivity extends AppCompatActivity {

    private Button mBnFood;
    private ImageButton mBtnRecord, mBtnAdd, mBtnChat, mBtnAccount;
    EditText  phone, password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
		
        phone = (EditText)findViewById(R.id.phone);//輸入帳號
        password = (EditText)findViewById(R.id.password);//輸入密碼
        btn_login = findViewById(R.id.btn_login);//登入按鈕

        //login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                login();//執行login()
            }
        });
        //login
	}
	
	//login
    public void login(){
        StringRequest request = new StringRequest(Request.Method.POST, "https://roiliest-loaves.000webhostapp.com/connect/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("1")){
                    Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_SHORT).show();/
					//跳到其他activity寫在這一行
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