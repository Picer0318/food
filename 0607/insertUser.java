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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

    Button show, insert;
    RequestQueue requestQueue;
    String insertUrl = "https://roiliest-loaves.000webhostapp.com/connect/insertUser.php";
    EditText  phone, password;

    @Override
protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		insert = (Button)findViewById(R.id.b3);//新增按鈕
		requestQueue = Volley.newRequestQueue(getApplicationContext());
		phone = (EditText)findViewById(R.id.phone);//輸入帳號
		password = (EditText)findViewById(R.id.password);//輸入密碼
        //insert
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(phone.getText().toString().trim())  || phone.length() != 10 || "".equals(password.getText().toString().trim()) || password.length() < 8 || password.length() > 16 ) {//判斷輸入的帳號密碼
                    Toast.makeText(HomeActivity.this,"帳號、密碼不可為空，帳號必須十個數字",Toast.LENGTH_SHORT).show();
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
                                parameters.put("phone", phone.getText().toString());
                                parameters.put("password", password.getText().toString());

                                return parameters;
                            }
                        };
                        requestQueue.add(request);
                        Toast.makeText(HomeActivity.this, "註冊成功", Toast.LENGTH_SHORT).show();
                    }

        }
        });
        //insert
    }






}
