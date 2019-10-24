package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.view.Gravity;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    private ImageButton imgbtn_main, imgbtn_record, imgbtn_add, imgbtn_chat, imgbtn_account;
    Button btn_check;
    RequestQueue requestQueue ,requestQueue2 ,requestQueue3;
    String mainUrl = "https://roiliest-loaves.000webhostapp.com/connect/showfood.php";
    String insertReportUrl = "https://roiliest-loaves.000webhostapp.com/connect/insertReport.php";
    String mainUrlDESC = "https://roiliest-loaves.000webhostapp.com/connect/showfoodDESC.php";
    String mainUrlASC = "https://roiliest-loaves.000webhostapp.com/connect/showfoodASC.php";

    TableLayout user_list;
    String report_phone;
    Spinner spn_kindOfFood,spn_arealist,spn_sort;
    String sort;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgbtn_main = findViewById(R.id.imgbtn_main);
        imgbtn_record = findViewById(R.id.imgbtn_record);
        imgbtn_add = findViewById(R.id.imgbtn_add);
        imgbtn_chat = findViewById(R.id.imgbtn_chat);
        imgbtn_account = findViewById(R.id.imgbtn_account);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        requestQueue3 = Volley.newRequestQueue(getApplicationContext());
        btn_check = (Button)findViewById(R.id.btn_check);
        spn_kindOfFood=(Spinner) findViewById(R.id.spn_kindOfFood);
        spn_arealist=(Spinner) findViewById(R.id.spn_arealist);
        spn_sort=(Spinner) findViewById(R.id.spn_sort);
        SharedPreferences setting = getSharedPreferences("uid",MODE_PRIVATE);
        report_phone = setting.getString("PREF_USERID","");





        //跳至record
        imgbtn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecordActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至add
        imgbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至chat
        imgbtn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至account
        imgbtn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //show
     //   btn_check.setOnClickListener(new View.OnClickListener() {
    //        @Override
     //       public void onClick(View v) {
/*
                user_list = (TableLayout) findViewById(R.id.user_list);
                user_list.setStretchAllColumns(true);
*/
                /*
                final TableLayout.LayoutParams row_layout = new
                        TableLayout.LayoutParams(ViewGroup.LayoutParams.
                        WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                final TableRow.LayoutParams view_layout = new
                        TableRow.LayoutParams(600,300);
                final TableRow.LayoutParams img_layout = new
                        TableRow.LayoutParams(200,200);
                final TableRow.LayoutParams report_layout = new
                        TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 90);
*/

        showfood();
/*
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, mainUrl,null ,new Response.Listener<JSONObject>(){

                    final TableLayout.LayoutParams row_layout = new
                            TableLayout.LayoutParams(ViewGroup.LayoutParams.
                            WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    final TableRow.LayoutParams view_layout = new
                            TableRow.LayoutParams(600,300);
                    final TableRow.LayoutParams img_layout = new
                            TableRow.LayoutParams(200,200);
                    final TableRow.LayoutParams report_layout = new
                            TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 90);



                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            JSONArray foods = response.getJSONArray("food");
                            for (int i = 0; i < foods.length(); i++){
                                final JSONObject user = foods.getJSONObject(i);

                                if(user.getString("boolean").trim().equals("0")){break;}

                                TableRow tr = new TableRow(MainActivity.this);
                                tr.setLayoutParams(row_layout);
                                tr.setGravity(Gravity.CENTER_VERTICAL);

                                final NetworkImageView img = new NetworkImageView(MainActivity.this);
                                ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                                    @Override
                                    public void putBitmap(String url, Bitmap bitmap) {
                                    }

                                    @Override
                                    public Bitmap getBitmap(String url) {
                                        return null;
                                    }
                                });

                                final String imgUrl = "https://roiliest-loaves.000webhostapp.com/connect/images";

                                img.setLayoutParams(img_layout);
                                img.setImageUrl(imgUrl + user.getString("image"),imageLoader);

                                final TextView foodview = new TextView(MainActivity.this);
                                foodview.setGravity(Gravity.CENTER_VERTICAL);
                                foodview.setText("\t"+"提供者：" + user.getString("phone") + "\n" +
                                                 "\t"+"食品名稱：" + user.getString("food_name") + "\n" +
                                                 "\t"+"地區："+ user.getString("food_area"));
                                foodview.setLayoutParams(view_layout);

                                final ImageButton btnReport = new ImageButton(MainActivity.this);
                                btnReport.setBackgroundResource(R.drawable.ic_report_black_24dp);
                                btnReport.setLayoutParams(report_layout);

                                tr.addView(img);
                                tr.addView(foodview);
                                tr.addView(btnReport);

                                user_list.addView(tr);

                                final String supplier = user.getString("phone");
                                final String foodname = user.getString("food_name");
                                final String expirydate = user.getString("expiry_date");
                                final String pickuptime = user.getString("pickup_time");
                                final String address = user.getString("food_address");
                                final String area = user.getString("food_area");
                                final String imgview_food = user.getString("image");
                                final String detail = user.getString("detail");

                                foodview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MainActivity.this,foodpage.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("tV_supplier", supplier);
                                        bundle.putString("tV_foodname", foodname);
                                        bundle.putString("tV_expirydate", expirydate);
                                        bundle.putString("tV_pickuptime", pickuptime);
                                        bundle.putString("tV_address", address);
                                        bundle.putString("tV_area", area);
                                        bundle.putString("imgUrl",imgUrl);
                                        bundle.putString("imgview_food", imgview_food);
                                        bundle.putString("detail", detail);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                                btnReport.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder altDlgBuilder = new AlertDialog.Builder(MainActivity.this);
                                        altDlgBuilder.setTitle("檢舉內容");
                                        final EditText input = new EditText(MainActivity.this);
                                        altDlgBuilder.setView(input);
                                        altDlgBuilder.setPositiveButton("送出", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                if(input.getText().toString().trim().equals("")){
                                                    Toast.makeText(MainActivity.this,"請勿留白",Toast.LENGTH_SHORT).show();
                                                }else{

                                                    StringRequest request = new StringRequest(Request.Method.POST, insertReportUrl, new Response.Listener<String>() {
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
                                                            parameters.put("report_phone", report_phone);
                                                            parameters.put("reported_phone_food", supplier + "_" + foodname);
                                                            parameters.put("report_content", input.getText().toString());

                                                            return parameters;
                                                        }
                                                    };
                                                    requestQueue2.add(request);
                                                    Toast.makeText(MainActivity.this,"檢舉成功",Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        });
                                        altDlgBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        altDlgBuilder.show();

                                    }

                                });




                            }
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
*/


       //     }
     //   });
        //show

        //
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(spn_sort.getSelectedItemId()==1){
                    mainUrl = "https://roiliest-loaves.000webhostapp.com/connect/showfoodDESC.php" ;
                    if(spn_kindOfFood.getSelectedItemId()==0 && spn_arealist.getSelectedItemId()==0){
                        user_list.removeAllViews();
                        showfood();
                    }else if(spn_kindOfFood.getSelectedItemId()==0 || spn_arealist.getSelectedItemId()==0){
                        user_list.removeAllViews();
                        selectFood();
                    }else{
                        user_list.removeAllViews();
                        selectFood2();
                    }

                }else if (spn_sort.getSelectedItemId()==2){
                    mainUrl = "https://roiliest-loaves.000webhostapp.com/connect/showfoodASC.php";
                    if(spn_kindOfFood.getSelectedItemId()==0 && spn_arealist.getSelectedItemId()==0){
                        user_list.removeAllViews();
                        showfood();
                    }else if(spn_kindOfFood.getSelectedItemId()==0 || spn_arealist.getSelectedItemId()==0){
                        user_list.removeAllViews();
                        selectFood();
                    }else{
                        user_list.removeAllViews();
                        selectFood2();
                    }
                }else{
                    mainUrl = "https://roiliest-loaves.000webhostapp.com/connect/showfood.php";
                    if(spn_kindOfFood.getSelectedItemId()==0 && spn_arealist.getSelectedItemId()==0){
                        user_list.removeAllViews();
                        showfood();
                    }else if(spn_kindOfFood.getSelectedItemId()==0 || spn_arealist.getSelectedItemId()==0){
                        user_list.removeAllViews();
                        selectFood();
                    }else{
                        user_list.removeAllViews();
                        selectFood2();
                    }
                }





                //showfood();



            }
        });

        //









    }


    private void showfood(){

        user_list = (TableLayout) findViewById(R.id.user_list);
        user_list.setStretchAllColumns(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, mainUrl,null ,new Response.Listener<JSONObject>(){

            final TableLayout.LayoutParams row_layout = new
                    TableLayout.LayoutParams(ViewGroup.LayoutParams.
                    WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final TableRow.LayoutParams view_layout = new
                    TableRow.LayoutParams(600,300);
            final TableRow.LayoutParams img_layout = new
                    TableRow.LayoutParams(200,200);
            final TableRow.LayoutParams report_layout = new
                    TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 90);

            @Override
            public void onResponse(JSONObject response){
                try {
                    JSONArray foods = response.getJSONArray("food");
                    for (int i = 0; i < foods.length(); i++){
                        final JSONObject user = foods.getJSONObject(i);

                        if(user.getString("boolean").trim().equals("0")){continue;}

                        TableRow tr = new TableRow(MainActivity.this);
                        tr.setLayoutParams(row_layout);
                        tr.setGravity(Gravity.CENTER_VERTICAL);

                        final NetworkImageView img = new NetworkImageView(MainActivity.this);
                        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                            @Override
                            public void putBitmap(String url, Bitmap bitmap) {
                            }

                            @Override
                            public Bitmap getBitmap(String url) {
                                return null;
                            }
                        });

                        final String imgUrl = "https://roiliest-loaves.000webhostapp.com/connect/images";

                        img.setLayoutParams(img_layout);
                        img.setImageUrl(imgUrl + user.getString("image"),imageLoader);

                        final TextView foodview = new TextView(MainActivity.this);
                        foodview.setGravity(Gravity.CENTER_VERTICAL);
                        foodview.setText("\t"+"提供者：" + user.getString("phone") + "\n" +
                                "\t"+"食品名稱：" + user.getString("food_name") + "\n" +
                                "\t"+"地區："+ user.getString("food_area"));
                        foodview.setLayoutParams(view_layout);

                        final ImageButton btnReport = new ImageButton(MainActivity.this);
                        btnReport.setBackgroundResource(R.drawable.ic_report_black_24dp);
                        btnReport.setLayoutParams(report_layout);

                        tr.addView(img);
                        tr.addView(foodview);
                        tr.addView(btnReport);


                        user_list.addView(tr);

                        final String supplier = user.getString("phone");
                        final String foodname = user.getString("food_name");
                        final String expirydate = user.getString("expiry_date");
                        final String pickuptime = user.getString("pickup_time");
                        final String address = user.getString("food_address");
                        final String area = user.getString("food_area");
                        final String imgview_food = user.getString("image");
                        final String detail = user.getString("detail");

                        foodview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this,foodpage.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tV_supplier", supplier);
                                bundle.putString("tV_foodname", foodname);
                                bundle.putString("tV_expirydate", expirydate);
                                bundle.putString("tV_pickuptime", pickuptime);
                                bundle.putString("tV_address", address);
                                bundle.putString("tV_area", area);
                                bundle.putString("imgUrl",imgUrl);
                                bundle.putString("imgview_food", imgview_food);
                                bundle.putString("detail", detail);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        btnReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder altDlgBuilder = new AlertDialog.Builder(MainActivity.this);
                                altDlgBuilder.setTitle("檢舉內容");
                                final EditText input = new EditText(MainActivity.this);
                                altDlgBuilder.setView(input);
                                altDlgBuilder.setPositiveButton("送出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(input.getText().toString().trim().equals("")){
                                            Toast.makeText(MainActivity.this,"請勿留白",Toast.LENGTH_SHORT).show();
                                        }else{

                                            StringRequest request = new StringRequest(Request.Method.POST, insertReportUrl, new Response.Listener<String>() {
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
                                                    parameters.put("report_phone", report_phone);
                                                    parameters.put("reported_phone_food", supplier + "_" + foodname);
                                                    parameters.put("report_content", input.getText().toString());

                                                    return parameters;
                                                }
                                            };
                                            requestQueue2.add(request);
                                            Toast.makeText(MainActivity.this,"檢舉成功",Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                                altDlgBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                altDlgBuilder.show();

                            }

                        });




                    }
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
    private void selectFood(){

        user_list = (TableLayout) findViewById(R.id.user_list);
        user_list.setStretchAllColumns(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, mainUrl,null ,new Response.Listener<JSONObject>(){

            final TableLayout.LayoutParams row_layout = new
                    TableLayout.LayoutParams(ViewGroup.LayoutParams.
                    WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final TableRow.LayoutParams view_layout = new
                    TableRow.LayoutParams(600,300);
            final TableRow.LayoutParams img_layout = new
                    TableRow.LayoutParams(200,200);
            final TableRow.LayoutParams report_layout = new
                    TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 90);



            @Override
            public void onResponse(JSONObject response){
                try {
                    JSONArray foods = response.getJSONArray("food");
                    for (int i = 0; i < foods.length(); i++){
                        final JSONObject user = foods.getJSONObject(i);

                        if(user.getString("boolean").trim().equals("0")){continue;}
                        if((!spn_kindOfFood.getSelectedItem().toString().trim().equals(user.getString("food_type"))&& spn_arealist.getSelectedItemId()==0 && spn_kindOfFood.getSelectedItemId()!=0)||
                                (!spn_arealist.getSelectedItem().toString().trim().equals(user.getString("food_area"))&&spn_kindOfFood.getSelectedItemId()==0 && spn_arealist.getSelectedItemId()!=0)) {
                            continue;
                        }



                       TableRow tr = new TableRow(MainActivity.this);
                        tr.setLayoutParams(row_layout);
                        tr.setGravity(Gravity.CENTER_VERTICAL);

                        final NetworkImageView img = new NetworkImageView(MainActivity.this);
                        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                            @Override
                            public void putBitmap(String url, Bitmap bitmap) {
                            }

                            @Override
                            public Bitmap getBitmap(String url) {
                                return null;
                            }
                        });

                        final String imgUrl = "https://roiliest-loaves.000webhostapp.com/connect/images";

                        img.setLayoutParams(img_layout);
                        img.setImageUrl(imgUrl + user.getString("image"),imageLoader);

                        final TextView foodview = new TextView(MainActivity.this);
                        foodview.setGravity(Gravity.CENTER_VERTICAL);
                        foodview.setText("\t"+"提供者：" + user.getString("phone") + "\n" +
                                "\t"+"食品名稱：" + user.getString("food_name") + "\n" +
                                "\t"+"地區："+ user.getString("food_area"));
                        foodview.setLayoutParams(view_layout);

                        final ImageButton btnReport = new ImageButton(MainActivity.this);
                        btnReport.setBackgroundResource(R.drawable.ic_report_black_24dp);
                        btnReport.setLayoutParams(report_layout);

                        tr.addView(img);
                        tr.addView(foodview);
                        tr.addView(btnReport);

                        user_list.addView(tr);

                        final String supplier = user.getString("phone");
                        final String foodname = user.getString("food_name");
                        final String expirydate = user.getString("expiry_date");
                        final String pickuptime = user.getString("pickup_time");
                        final String address = user.getString("food_address");
                        final String area = user.getString("food_area");
                        final String imgview_food = user.getString("image");
                        final String detail = user.getString("detail");

                        foodview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this,foodpage.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tV_supplier", supplier);
                                bundle.putString("tV_foodname", foodname);
                                bundle.putString("tV_expirydate", expirydate);
                                bundle.putString("tV_pickuptime", pickuptime);
                                bundle.putString("tV_address", address);
                                bundle.putString("tV_area", area);
                                bundle.putString("imgUrl",imgUrl);
                                bundle.putString("imgview_food", imgview_food);
                                bundle.putString("detail", detail);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        btnReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder altDlgBuilder = new AlertDialog.Builder(MainActivity.this);
                                altDlgBuilder.setTitle("檢舉內容");
                                final EditText input = new EditText(MainActivity.this);
                                altDlgBuilder.setView(input);
                                altDlgBuilder.setPositiveButton("送出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(input.getText().toString().trim().equals("")){
                                            Toast.makeText(MainActivity.this,"請勿留白",Toast.LENGTH_SHORT).show();
                                        }else{

                                            StringRequest request = new StringRequest(Request.Method.POST, insertReportUrl, new Response.Listener<String>() {
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
                                                    parameters.put("report_phone", report_phone);
                                                    parameters.put("reported_phone_food", supplier + "_" + foodname);
                                                    parameters.put("report_content", input.getText().toString());

                                                    return parameters;
                                                }
                                            };
                                            requestQueue2.add(request);
                                            Toast.makeText(MainActivity.this,"檢舉成功",Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                                altDlgBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                altDlgBuilder.show();

                            }

                        });




                    }
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
    private void selectFood2(){

        user_list = (TableLayout) findViewById(R.id.user_list);
        user_list.setStretchAllColumns(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, mainUrl,null ,new Response.Listener<JSONObject>(){

            final TableLayout.LayoutParams row_layout = new
                    TableLayout.LayoutParams(ViewGroup.LayoutParams.
                    WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final TableRow.LayoutParams view_layout = new
                    TableRow.LayoutParams(600,300);
            final TableRow.LayoutParams img_layout = new
                    TableRow.LayoutParams(200,200);
            final TableRow.LayoutParams report_layout = new
                    TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 90);



            @Override
            public void onResponse(JSONObject response){
                try {
                    JSONArray foods = response.getJSONArray("food");
                    for (int i = 0; i < foods.length(); i++){
                        final JSONObject user = foods.getJSONObject(i);

                        if(user.getString("boolean").trim().equals("0")){continue;}

                        if(!spn_kindOfFood.getSelectedItem().toString().trim().equals(user.getString("food_type"))){
                            continue;
                        }
                        if(!spn_arealist.getSelectedItem().toString().trim().equals(user.getString("food_area"))){
                            continue;
                        }






                        TableRow tr = new TableRow(MainActivity.this);
                        tr.setLayoutParams(row_layout);
                        tr.setGravity(Gravity.CENTER_VERTICAL);

                        final NetworkImageView img = new NetworkImageView(MainActivity.this);
                        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                            @Override
                            public void putBitmap(String url, Bitmap bitmap) {
                            }

                            @Override
                            public Bitmap getBitmap(String url) {
                                return null;
                            }
                        });

                        final String imgUrl = "https://roiliest-loaves.000webhostapp.com/connect/images";

                        img.setLayoutParams(img_layout);
                        img.setImageUrl(imgUrl + user.getString("image"),imageLoader);

                        final TextView foodview = new TextView(MainActivity.this);
                        foodview.setGravity(Gravity.CENTER_VERTICAL);
                        foodview.setText("\t"+"提供者：" + user.getString("phone") + "\n" +
                                "\t"+"食品名稱：" + user.getString("food_name") + "\n" +
                                "\t"+"地區："+ user.getString("food_area"));
                        foodview.setLayoutParams(view_layout);

                        final ImageButton btnReport = new ImageButton(MainActivity.this);
                        btnReport.setBackgroundResource(R.drawable.ic_report_black_24dp);
                        btnReport.setLayoutParams(report_layout);

                        tr.addView(img);
                        tr.addView(foodview);
                        tr.addView(btnReport);

                        user_list.addView(tr);

                        final String supplier = user.getString("phone");
                        final String foodname = user.getString("food_name");
                        final String expirydate = user.getString("expiry_date");
                        final String pickuptime = user.getString("pickup_time");
                        final String address = user.getString("food_address");
                        final String area = user.getString("food_area");
                        final String imgview_food = user.getString("image");
                        final String detail = user.getString("detail");

                        foodview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this,foodpage.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tV_supplier", supplier);
                                bundle.putString("tV_foodname", foodname);
                                bundle.putString("tV_expirydate", expirydate);
                                bundle.putString("tV_pickuptime", pickuptime);
                                bundle.putString("tV_address", address);
                                bundle.putString("tV_area", area);
                                bundle.putString("imgUrl",imgUrl);
                                bundle.putString("imgview_food", imgview_food);
                                bundle.putString("detail", detail);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        btnReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder altDlgBuilder = new AlertDialog.Builder(MainActivity.this);
                                altDlgBuilder.setTitle("檢舉內容");
                                final EditText input = new EditText(MainActivity.this);
                                altDlgBuilder.setView(input);
                                altDlgBuilder.setPositiveButton("送出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(input.getText().toString().trim().equals("")){
                                            Toast.makeText(MainActivity.this,"請勿留白",Toast.LENGTH_SHORT).show();
                                        }else{

                                            StringRequest request = new StringRequest(Request.Method.POST, insertReportUrl, new Response.Listener<String>() {
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
                                                    parameters.put("report_phone", report_phone);
                                                    parameters.put("reported_phone_food", supplier + "_" + foodname);
                                                    parameters.put("report_content", input.getText().toString());

                                                    return parameters;
                                                }
                                            };
                                            requestQueue2.add(request);
                                            Toast.makeText(MainActivity.this,"檢舉成功",Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                                altDlgBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                altDlgBuilder.show();

                            }

                        });




                    }
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
}
