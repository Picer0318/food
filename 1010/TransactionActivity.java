package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransactionActivity extends AppCompatActivity {

    private ImageButton imgbtn_main, imgbtn_record, imgbtn_add, imgbtn_chat, imgbtn_account;
    TableLayout user_list;
    RequestQueue requestQueue ,requestQueue2;
    String mainUrl = "https://roiliest-loaves.000webhostapp.com/connect/showfood.php";
    String updateTradeUrl = "https://roiliest-loaves.000webhostapp.com/connect/updateTrade.php";
    int rating;
    String report_phone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        imgbtn_main = findViewById(R.id.imgbtn_main);
        imgbtn_record = findViewById(R.id.imgbtn_record);
        imgbtn_add = findViewById(R.id.imgbtn_add);
        imgbtn_chat = findViewById(R.id.imgbtn_chat);
        imgbtn_account = findViewById(R.id.imgbtn_account);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences setting = getSharedPreferences("uid",MODE_PRIVATE);
        report_phone = setting.getString("PREF_USERID","");


        //跳至main
        imgbtn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至record
        imgbtn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this,RecordActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至add
        imgbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this,AddActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至chat
        imgbtn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this,ChatActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //跳至account
        imgbtn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this,AccountActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


        showfood();



    }

    private void showfood(){

        user_list = (TableLayout) findViewById(R.id.user_list);
        user_list.setStretchAllColumns(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, mainUrl ,null ,new Response.Listener<JSONObject>(){

            final TableLayout.LayoutParams row_layout = new
                    TableLayout.LayoutParams(TableLayout.LayoutParams.
                    MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            final TableRow.LayoutParams view_layout = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,300);

            final TableRow.LayoutParams img_layout = new TableRow.LayoutParams(200,200);

            final TableRow.LayoutParams rating_layout = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

            final TableRow.LayoutParams report_layout = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);


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
            @Override
            public void onResponse(JSONObject response){
                try {
                    JSONArray foods = response.getJSONArray("food");
                    for (int i = 0; i < foods.length(); i++){
                        final JSONObject user = foods.getJSONObject(i);

                        if(!user.getString("phone").trim().equals(report_phone)){continue;}

                        TableRow tr = new TableRow(TransactionActivity.this);
                        tr.setLayoutParams(row_layout);
                        tr.setGravity(Gravity.CENTER_VERTICAL);

                        final NetworkImageView img = new NetworkImageView(TransactionActivity.this);
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

                        final TextView foodview = new TextView(TransactionActivity.this);
                        foodview.setGravity(Gravity.CENTER_VERTICAL);
                        foodview.setText("\t"+"提供者：" + user.getString("name") + "\n" +
                                "\t"+"食品名稱：" + user.getString("food_name") + "\n" +
                                "\t"+"地區："+ user.getString("food_area"));
                        view_layout.gravity = Gravity.CENTER;
                        foodview.setLayoutParams(view_layout);



                        final RatingBar ratingBar = new RatingBar(TransactionActivity.this,null, android.R.attr.ratingBarStyleSmall);
                        rating = user.getInt("rating");
                        ratingBar.setStepSize((float) 0.1);
                        ratingBar.setMax(5);
                        ratingBar.setRating((float) rating);
                        rating_layout.gravity=Gravity.CENTER;
                        ratingBar.setLayoutParams(rating_layout);




                        final ImageButton btnReport = new ImageButton(TransactionActivity.this);
                        btnReport.setBackgroundResource(R.drawable.ic_done_black_24dp);
                        report_layout.gravity = Gravity.CENTER;
                        btnReport.setLayoutParams(report_layout);

                        tr.addView(img);
                        tr.addView(foodview);
                        tr.addView(ratingBar);
                        tr.addView(btnReport);


                        user_list.addView(tr);

                        final String id = user.getString("id");
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
                                Intent intent = new Intent(TransactionActivity.this,foodpage.class);
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


                                StringRequest request = new StringRequest(Request.Method.POST, updateTradeUrl, new Response.Listener<String>() {
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
                                        parameters.put("id", id);

                                        return parameters;
                                    }
                                };
                                requestQueue2.add(request);
                                Toast.makeText(TransactionActivity.this,"交易完成",Toast.LENGTH_SHORT).show();




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
