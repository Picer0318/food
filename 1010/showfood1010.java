    private void showfood(){

        user_list = (TableLayout) findViewById(R.id.user_list);
        user_list.setStretchAllColumns(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, mainUrl,null ,new Response.Listener<JSONObject>(){

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
                        view_layout.gravity = Gravity.CENTER;
                        foodview.setLayoutParams(view_layout);

                        final RatingBar ratingBar = new RatingBar(MainActivity.this,null, android.R.attr.ratingBarStyleSmall);
                        ratingBar.setStepSize((float) 0.1);
                        ratingBar.setMax(5);
                        ratingBar.setRating((float) 5);
                        rating_layout.gravity=Gravity.CENTER;
                        ratingBar.setLayoutParams(rating_layout);



                        final ImageButton btnReport = new ImageButton(MainActivity.this);
                        btnReport.setBackgroundResource(R.drawable.ic_report_black_24dp);
                        report_layout.gravity = Gravity.CENTER;
                        btnReport.setLayoutParams(report_layout);

                        tr.addView(img);
                        tr.addView(foodview);
                        tr.addView(ratingBar);
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
	
	                if(editable) {
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
                            parameters.put("phone", phoneNum);
                            parameters.put("name", edt_name.getText().toString());
                            parameters.put("sex", userGender);
                            parameters.put("rating","5");


                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                    //跳至Login-------------------------------------------------------------------
                    Intent toLogin = new Intent(UserdataActivity.this, MainActivity.class);
                    startActivity(toLogin);

                }else{
                    //跳至account-----------------------------------------------------------------
                    Intent toAccount = new Intent(UserdataActivity.this, AccountActivity.class);
                    startActivity(toAccount);
                }