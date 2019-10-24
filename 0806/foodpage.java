package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class foodpage extends AppCompatActivity {
    private TextView tV_supplier , tV_foodname , tV_expirydate , tV_pickuptime ,tV_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodpage);

        tV_supplier = (TextView)findViewById(R.id.tV_supplier);
        tV_foodname = (TextView)findViewById(R.id.tV_foodname);
        tV_expirydate = (TextView)findViewById(R.id.tV_expirydate);
        tV_pickuptime = (TextView)findViewById(R.id.tV_pickuptime);
        tV_address = (TextView)findViewById(R.id.tV_address);


        Bundle bundle = getIntent().getExtras();
        String supplier = bundle.getString("tV_supplier");
        String foodname = bundle.getString("tV_foodname");
        String expirydate = bundle.getString("tV_expirydate");
        String pickuptime = bundle.getString("tV_pickuptime");
        String address = bundle.getString("tV_address");
        String area = bundle.getString("area");

        tV_supplier.setText(supplier);
        tV_foodname.setText(foodname);
        tV_expirydate.setText(expirydate);
        tV_pickuptime.setText(pickuptime);
        tV_address.setText(area + address);



    }
}
