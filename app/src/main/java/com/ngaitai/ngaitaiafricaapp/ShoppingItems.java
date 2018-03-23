package com.ngaitai.ngaitaiafricaapp;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShoppingItems extends AppCompatActivity{
    ArrayList<Product> arrayList;
    private ListView listViewItems;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseItems;
    private List<ProductDescription> productList;
    private Button sale;
    private FirebaseDatabase itemsSold;
    FirebaseUser user;
    ProgressDialog pDialog;
    private  int leng;
    private String id,stringdata;

    private static final String URL ="http://www.amuri.heliohost.org/myfiles/http_est.php";
    private static final String URL1 ="http://www.amuri.heliohost.org/myfiles/receipt.php";
    float sumAll=0;
    int sumBv=0;
    Object obj2;
    private String phone,receiptNo;
    private String DistributorCD, DistributorName,DistributorCD2, DistributorName2, ProductCD, Description, UnitPrice, Quantity, BV, SubTotBV, SubTotal, CashTend;
    private String currMonth,currYear,currDate;
    private TextView Total;
    private EditText amountPaid;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        listViewItems =(ListView)findViewById(R.id.listItems);
        arrayList = new ArrayList<>();
        productList=new ArrayList<>();

        firebaseAuth=FirebaseAuth.getInstance();

        user= firebaseAuth.getInstance().getCurrentUser();
        databaseItems= FirebaseDatabase.getInstance().getReference("Items").child(user.getUid());
        sale=(Button)findViewById(R.id.buttonSale);
        cancel=(Button)findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference letsDelete=FirebaseDatabase.getInstance().getReference("Items").child(user.getUid());
                letsDelete.removeValue();
finish();
                Toast.makeText(getApplicationContext(),"Transaction cancelled",Toast.LENGTH_SHORT);
            }
        });


        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy");

        currYear = format.format(date);
        currDate= DateFormat.getDateTimeInstance().format(new Date());

        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        Calendar cal = Calendar.getInstance();
        currMonth = monthName[cal.get(Calendar.MONTH)];

//lets get the receipt No.
                //////////////
                StringRequest stringRequest1 =new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    receiptNo = jsonObject.getString("reply");
                }catch (Exception e) {        }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest1);

//////////////
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*  Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(),"This feature is currently unavailable",Toast.LENGTH_LONG).show();
                */

               // Create a new HttpClient and Post Header
                //new inputResep().execute();

                payHere();

                        //getFireToMysql();
                //Toast.makeText(getApplicationContext(),String.valueOf(sumAll),Toast.LENGTH_LONG).show();


                DatabaseReference letsDelete=FirebaseDatabase.getInstance().getReference("Items").child(user.getUid());
                letsDelete.removeValue();

                Toast.makeText(getApplicationContext(),"Items Sold",Toast.LENGTH_SHORT);




               // new inputResep().execute();
            }});

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ProductDescription product=ds.getValue(ProductDescription.class);


                    product.setDistributorPhone(ds.getValue(ProductDescription.class).getDistributorPhone());
                    product.setTotPrice(ds.getValue(ProductDescription.class).getTotPrice());
                    product.setTotBv(ds.getValue(ProductDescription.class).getTotBv());
                    product.setDistributorName(ds.getValue(ProductDescription.class).getDistributorName());
                    product.setDistributorCode(ds.getValue(ProductDescription.class).getDistributorCode());

                    sumBv+=product.getTotBv();
                    sumAll+=product.getTotPrice();

                    phone=product.getDistributorPhone();
                    productList.add(product);

                    DistributorCD2=product.getDistributorCode();
                    DistributorName2=product.getDistributorName();
                }
                Shopping_list adapter= new Shopping_list(ShoppingItems.this,productList);
                listViewItems.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


        public void  getFireToMysql()
        {
            databaseItems.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    productList.clear();
                   long x= dataSnapshot.getChildrenCount();

                  for (DataSnapshot ds : dataSnapshot.getChildren()) {

                      for (long i=0; i<=x;i++) {

                      ProductDescription product = ds.getValue(ProductDescription.class);

                          product.setDescription(ds.getValue(ProductDescription.class).getDescription());
                          product.setSellingPrice(ds.getValue(ProductDescription.class).getSellingPrice());
                          product.setBV(ds.getValue(ProductDescription.class).getBV());
                          product.setDistributorCode(ds.getValue(ProductDescription.class).getDistributorCode());
                          product.setDistributorName(ds.getValue(ProductDescription.class).getDistributorName());
                          product.setQuantity(ds.getValue(ProductDescription.class).getQuantity());
                          product.setBV(ds.getValue(ProductDescription.class).getBV());
                          product.setRemarks(ds.getValue(ProductDescription.class).getRemarks());


                          obj2 = ds.getKey();

                          DistributorCD=product.getDistributorCode();
                          DistributorName=product.getDistributorName();
                          ProductCD=product.getDistributorCode();
                          Description=product.getDescription();
                          UnitPrice=product.getSellingPrice();
                          Quantity=product.getQuantity();
                          BV=product.getBV();

                      StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                          @Override
                          public void onResponse(String response) {


                          }
                      }, new Response.ErrorListener() {
                          @Override
                          public void onErrorResponse(VolleyError error) {

                          }
                      }) {

                          @Override
                          protected Map<String, String> getParams() throws AuthFailureError {

                              HashMap<String, String> hashMap = new HashMap<>();
                              hashMap.put("InvoiceNo", receiptNo);
                              hashMap.put("Month", currMonth);
                              hashMap.put("Year", currYear);
                              hashMap.put("DistributorCD", DistributorCD);
                              hashMap.put("DistributorName", DistributorName);
                              hashMap.put("ProductCD", ProductCD);
                              hashMap.put("Description", Description);
                              hashMap.put("UnitPrice", UnitPrice);
                              hashMap.put("Quantity", Quantity);
                              hashMap.put("BV", BV);
                              hashMap.put("SubTotal", String.valueOf(sumAll));
                              hashMap.put("CashTend", CashTend);
                              hashMap.put("DateEncoded", currDate);
                              hashMap.put("unique", obj2.toString());

                              return hashMap;
                          }
                      };

                      RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                      queue.add(stringRequest1);

                  }

              }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        public void payHere()
        {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ShoppingItems.this);
            View view = getLayoutInflater().inflate(R.layout.custom_payment, null);

            Button lipa = (Button) view.findViewById(R.id.PayHere);
            Total=(TextView) view.findViewById(R.id.textViewTotal);
            amountPaid=(EditText) view.findViewById(R.id.amountPaid);

            Total.setText(" ");
            amountPaid.setText(" ");

            Total.setText(String.valueOf(sumAll));

            mBuilder.setView(view);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(500,300);

            lipa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SubTotal=Total.getText().toString().trim();
                    CashTend=amountPaid.getText().toString().trim();

                    if(Integer.parseInt(CashTend)<Float.parseFloat(SubTotal))

                    {
                            AlertDialog.Builder builder;

                            builder = new android.support.v7.app.AlertDialog.Builder(ShoppingItems.this);

                            builder.setTitle("Less Amount ")
                                    .setMessage("Please Enter the exact value")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();

                    }
                    else if(Integer.parseInt(CashTend)>Float.parseFloat(SubTotal))
                    {
                        AlertDialog.Builder builder;

                        builder = new android.support.v7.app.AlertDialog.Builder(ShoppingItems.this);

                        builder.setTitle("Excess Amount ")
                                .setMessage("Amount Entered is more than required amount")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();

                    }
                    else
                    {
                      // getFireToMysql();;

                        databaseItems.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                productList.clear();
                                long x= dataSnapshot.getChildrenCount();

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    for (long i=0; i<=x;i++) {

                                        ProductDescription product = ds.getValue(ProductDescription.class);

                                        product.setDescription(ds.getValue(ProductDescription.class).getDescription());
                                        product.setSellingPrice(ds.getValue(ProductDescription.class).getSellingPrice());
                                        product.setBV(ds.getValue(ProductDescription.class).getBV());
                                        product.setDistributorCode(ds.getValue(ProductDescription.class).getDistributorCode());
                                        product.setDistributorName(ds.getValue(ProductDescription.class).getDistributorName());
                                        product.setQuantity(ds.getValue(ProductDescription.class).getQuantity());
                                        product.setBV(ds.getValue(ProductDescription.class).getBV());
                                        product.setRemarks(ds.getValue(ProductDescription.class).getRemarks());


                                        obj2 = ds.getKey();

                                        DistributorCD=product.getDistributorCode();
                                        DistributorName=product.getDistributorName();
                                        ProductCD=product.getDistributorCode();
                                        Description=product.getDescription();
                                        UnitPrice=product.getSellingPrice();
                                        Quantity=product.getQuantity();
                                        BV=product.getBV();

                                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {


                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }) {

                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {

                                                HashMap<String, String> hashMap = new HashMap<>();
                                               //hashMap.put("InvoiceNo", receiptNo);
                                                hashMap.put("Month", currMonth);
                                                hashMap.put("Year", currYear);
                                                hashMap.put("DistributorCD", DistributorCD);
                                                hashMap.put("DistributorName", DistributorName);
                                                hashMap.put("ProductCD", ProductCD);
                                                hashMap.put("Description", Description);
                                                hashMap.put("UnitPrice", UnitPrice);
                                                hashMap.put("Quantity", Quantity);
                                                hashMap.put("BV", BV);
                                                hashMap.put("SubTotal", SubTotal);
                                                hashMap.put("CashTend", CashTend);
                                                hashMap.put("DateEncoded", currDate);
                                              // hashMap.put("unique", obj2.toString());

                                                return hashMap;
                                            }
                                        };

                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        queue.add(stringRequest1);

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                       dialog.dismiss();

                        AlertDialog.Builder builder;

                        builder = new android.support.v7.app.AlertDialog.Builder(ShoppingItems.this);

                        builder.setTitle("Thanks ")
                                .setMessage("Thank You For Shopping! Press Ok to send Receipt")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ///////////////////////////
                                        finish();
                                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
                                        String sms_data="Dear "+DistributorName2+" "+"Distributor No"+DistributorCD2+" "+" Items bought worth"+
                                                "Ksh "+SubTotal+" BV:"+sumBv+" Thank you. Ngaitai Africa(K) Customer Care No. is 0792089941";
                                        smsIntent.putExtra("sms_body", sms_data);
                                        startActivity(smsIntent);

                                        //new getProfileimage().execute();

                                    }
                                })
                                .show();


                                                      //  new MyAsyncTask().execute();






                    }


                }
            });


        }

    public class getProfileimage extends AsyncTask<String, Void, String> {

        StringBuffer stringBuffer = new StringBuffer();
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(ShoppingItems.this, "Please wait", "loading",true);
           // pd.setCancelable(true);

        }

        @Override
        protected String doInBackground(String... params) {

            databaseItems.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    productList.clear();
                    long x= dataSnapshot.getChildrenCount();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        for (long i=0; i<=x;i++) {

                            ProductDescription product = ds.getValue(ProductDescription.class);

                            product.setDescription(ds.getValue(ProductDescription.class).getDescription());
                            product.setSellingPrice(ds.getValue(ProductDescription.class).getSellingPrice());
                            product.setBV(ds.getValue(ProductDescription.class).getBV());
                            product.setDistributorCode(ds.getValue(ProductDescription.class).getDistributorCode());
                            product.setDistributorName(ds.getValue(ProductDescription.class).getDistributorName());
                            product.setQuantity(ds.getValue(ProductDescription.class).getQuantity());
                            product.setBV(ds.getValue(ProductDescription.class).getBV());
                            product.setRemarks(ds.getValue(ProductDescription.class).getRemarks());


                            obj2 = ds.getKey();

                            DistributorCD=product.getDistributorCode();
                            DistributorName=product.getDistributorName();
                            ProductCD=product.getDistributorCode();
                            Description=product.getDescription();
                            UnitPrice=product.getSellingPrice();
                            Quantity=product.getQuantity();
                            BV=product.getBV();

                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("InvoiceNo", receiptNo);
                                    hashMap.put("Month", currMonth);
                                    hashMap.put("Year", currYear);
                                    hashMap.put("DistributorCD", DistributorCD);
                                    hashMap.put("DistributorName", DistributorName);
                                    hashMap.put("ProductCD", ProductCD);
                                    hashMap.put("Description", Description);
                                    hashMap.put("UnitPrice", UnitPrice);
                                    hashMap.put("Quantity", Quantity);
                                    hashMap.put("BV", BV);
                                    hashMap.put("SubTotal", String.valueOf(sumAll));
                                    hashMap.put("CashTend", CashTend);
                                    hashMap.put("DateEncoded", currDate);
                                    hashMap.put("unique", obj2.toString());

                                    return hashMap;
                                }
                            };

                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            queue.add(stringRequest1);

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




            Toast.makeText(getApplicationContext(),"Transaction Recorded Succesfully",Toast.LENGTH_LONG).show();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
           pd.dismiss();
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
            String sms_data="Dear "+DistributorName2+" "+"Distributor No"+DistributorCD2+" "+" Items bought worth"+
                    "Ksh "+SubTotal+" BV:"+sumBv+" Thank you. Ngaitai Africa(K) Customer Care No. is 0792089941";
            smsIntent.putExtra("sms_body", sms_data);
            startActivity(smsIntent);

        }


    }

  /*  class MyAsyncTask extends android.os.AsyncTask {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(ShoppingItems.this, "Please wait", "submiting details",true);
            pd.setCancelable(true);


        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
            String sms_data="Dear "+DistributorName+" "+"Distributor No"+DistributorCD+" "+" Items bought worth"+
                    "Ksh "+SubTotal+" BV:"+sumBv+" Thank you. Ngaitai Africa(K) Customer Care No. is 0792089941";
            smsIntent.putExtra("sms_body", sms_data);
            startActivity(smsIntent);

            Toast.makeText(getApplicationContext(),"Transaction Recorded Succesfully",Toast.LENGTH_LONG).show();
            pd.dismiss();
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);



        }

        @Override
        protected Object doInBackground(Object[] objects) {
            //do something asynchronously


            databaseItems.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    productList.clear();
                    long x= dataSnapshot.getChildrenCount();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        for (long i=0; i<=x;i++) {

                            ProductDescription product = ds.getValue(ProductDescription.class);

                            product.setDescription(ds.getValue(ProductDescription.class).getDescription());
                            product.setSellingPrice(ds.getValue(ProductDescription.class).getSellingPrice());
                            product.setBV(ds.getValue(ProductDescription.class).getBV());
                            product.setDistributorCode(ds.getValue(ProductDescription.class).getDistributorCode());
                            product.setDistributorName(ds.getValue(ProductDescription.class).getDistributorName());
                            product.setQuantity(ds.getValue(ProductDescription.class).getQuantity());
                            product.setBV(ds.getValue(ProductDescription.class).getBV());
                            product.setRemarks(ds.getValue(ProductDescription.class).getRemarks());


                            obj2 = ds.getKey();

                            DistributorCD=product.getDistributorCode();
                            DistributorName=product.getDistributorName();
                            ProductCD=product.getDistributorCode();
                            Description=product.getDescription();
                            UnitPrice=product.getSellingPrice();
                            Quantity=product.getQuantity();
                            BV=product.getBV();

                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("InvoiceNo", receiptNo);
                                    hashMap.put("Month", currMonth);
                                    hashMap.put("Year", currYear);
                                    hashMap.put("DistributorCD", DistributorCD);
                                    hashMap.put("DistributorName", DistributorName);
                                    hashMap.put("ProductCD", ProductCD);
                                    hashMap.put("Description", Description);
                                    hashMap.put("UnitPrice", UnitPrice);
                                    hashMap.put("Quantity", Quantity);
                                    hashMap.put("BV", BV);
                                    hashMap.put("SubTotal", SubTotal);
                                    hashMap.put("CashTend", CashTend);
                                    hashMap.put("DateEncoded", currDate);
                                    hashMap.put("unique", obj2.toString());

                                    return hashMap;
                                }
                            };

                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            queue.add(stringRequest1);

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }*/
    }
