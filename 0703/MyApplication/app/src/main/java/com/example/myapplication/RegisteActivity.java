package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

public class RegisteActivity extends Activity {

    private TextView tVidError;
    private TextView tVpwError;
    private TextView tVConfirmError;
    private EditText edt_userid;
    private EditText edt_userpw;
    private EditText edt_confirmuserpw;
    private CheckBox chkBox_agree;
    private Button btn_cancel;
    private Button btn_registe;
    String phoneNum = "[0][9][0-9]{8}";//設定手機號碼格式
    String pwFormat = "[a-zA-Z0-9]{6,12}";//設定密碼格式
    RequestQueue requestQueue;
    String insertUrl = "https://roiliest-loaves.000webhostapp.com/connect/insertUser.php";


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
        chkBox_agree = findViewById(R.id.chkBox_agree);
        btn_registe = findViewById(R.id.btn_registe);
        edt_userid = findViewById(R.id.edt_userid);
        edt_userpw = findViewById(R.id.edt_userpw);
        edt_confirmuserpw = findViewById(R.id.edt_confirmuserpw);
        tVidError = findViewById(R.id.tVidError);
        tVpwError = findViewById(R.id.tVpwError);
        tVConfirmError = findViewById(R.id.tVConfirmError);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //設定userid輸入數字
        edt_userid.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        //設定userid最大長度為10
        edt_userid.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        //判斷勾選checkbox及註冊按鈕可按
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

        //--------------------------------------------------------------------------------------------------------------------------------------
        //按註冊鈕後
        btn_registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((edt_userid.getText().toString()).matches(phoneNum) //手機號碼格式正確
                        && edt_userpw.getText().toString().equals(edt_confirmuserpw.getText().toString()) //兩次密碼相同
                        && edt_userpw.getText().toString().matches(pwFormat) //密碼格式正確
                        && !edt_userpw.getText().toString().trim().equals("")) {//密碼不為空

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
                            parameters.put("phone", edt_userid.getText().toString());
                            parameters.put("password", edt_userpw.getText().toString());

                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                    Toast.makeText(RegisteActivity.this, "註冊成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisteActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisteActivity.this,"帳號、密碼不可為空，帳號必須十個數字",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------
    }
}
