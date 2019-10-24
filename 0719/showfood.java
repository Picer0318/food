package com.example.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String JSON_STRING;
    Button show, insert;
    RequestQueue requestQueue;
    String showUrl = "https://roiliest-loaves.000webhostapp.com/connect/showfood.php";
    String insertUrl = "https://roiliest-loaves.000webhostapp.com/connect/insertUser.php";
    TextView textView;
    EditText  phone, password;
    Button btn_login;
    TableLayout user_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        show = (Button)findViewById(R.id.b2);
        textView = (TextView) findViewById(R.id.textview);
        insert = (Button)findViewById(R.id.b3);//新增按鈕
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        phone = (EditText)findViewById(R.id.phone);//輸入帳號
        password = (EditText)findViewById(R.id.password);//輸入密碼
        btn_login = findViewById(R.id.btn_login);


        //login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                /*if ("".equals(phone.getText().toString().trim())  || phone.length() != 10 || "".equals(password.getText().toString().trim()) || password.length() < 6 || password.length() > 12 ) {//判斷輸入的帳號密碼
                    Toast.makeText(HomeActivity.this,"帳號、密碼不可為空，帳號必須十個數字，密碼6-12字元",Toast.LENGTH_SHORT).show();
                }else {*/
                    login();
               // }
            }
        });
        //login
        //show
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_list = (TableLayout)findViewById(R.id.user_list);
                user_list.setStretchAllColumns(true);
                final TableLayout.LayoutParams row_layout = new
                        TableLayout.LayoutParams(ViewGroup.LayoutParams.
                        WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                final TableRow.LayoutParams view_layout = new
                        TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl,null ,new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            JSONArray foods = response.getJSONArray("food");
                            for (int i = 0; i < foods.length(); i++){
                                JSONObject user = foods.getJSONObject(i);
                                TableRow tr = new TableRow(HomeActivity.this);
                                tr.setLayoutParams(row_layout);
                                tr.setGravity(Gravity.LEFT);
								
                                TableRow tr1 = new TableRow(HomeActivity.this);
                                tr1.setLayoutParams(row_layout);
                                tr1.setGravity(Gravity.CENTER_HORIZONTAL);
								
                                TableRow tr2 = new TableRow(HomeActivity.this);
                                tr2.setLayoutParams(row_layout);
                                tr2.setGravity(Gravity.CENTER_HORIZONTAL);
								
                                TableRow tr3 = new TableRow(HomeActivity.this);
                                tr3.setLayoutParams(row_layout);
                                tr3.setGravity(Gravity.CENTER_HORIZONTAL);
                                TextView user_acc = new TextView(HomeActivity.this);
                                user_acc.setText("提供者：" + user.getString("phone"));
                                user_acc.setLayoutParams(view_layout);
                                TextView food_name = new TextView(HomeActivity.this);
                                food_name.setText("食品名稱：" + user.getString("food_name"));
                                food_name.setLayoutParams(view_layout);
                                TextView food_area = new TextView(HomeActivity.this);
                                food_area.setText("地區："+ user.getString("food_area"));
                                food_area.setLayoutParams(view_layout);
                                TextView dashed = new TextView(HomeActivity.this);
                                dashed.setText("-------------------------------------");
                                dashed.setLayoutParams(view_layout);

                                tr.addView(user_acc);
                                tr1.addView(food_name);
                                tr2.addView(food_area);
                                tr3.addView(dashed);
                                user_list.addView(tr);
                                user_list.addView(tr1);
                                user_list.addView(tr2);
                                user_list.addView(tr3);

                                //String phone = user.getString("phone");
                                //textView.append( "phone： " + phone + "\n");
                            }
                            //textView.append("===\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
        //show
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



        mBnFood = findViewById(R.id.food);
        mBtnRecord =findViewById(R.id.record);
        mBtnAdd =findViewById(R.id.add);
        mBtnChat =findViewById(R.id.chat);
        mBtnAccount =findViewById(R.id.account);

        //跳到food頁面
        mBnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //跳到record頁面
        mBtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RecordActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //跳到add頁面
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AddActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        //跳到chat頁面
        mBtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ChatActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //跳到account頁面
        mBtnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AccountActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }

    //login
    public void login(){
        StringRequest request = new StringRequest(Request.Method.POST, "https://roiliest-loaves.000webhostapp.com/connect/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("1")){
                    Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_SHORT).show();
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


    //getjson
    public void getJSON(View v){

        new BackgroundTask().execute();

    }
    //getjson
    class BackgroundTask extends AsyncTask<Void,Void,String> {

        String json_url;
        @Override
        protected void onPreExecute(){
            json_url = "https://roiliest-loaves.000webhostapp.com/connect/test.php";
        }
        @Override
        protected String doInBackground(Void... voids){

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine()) !=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }





}
