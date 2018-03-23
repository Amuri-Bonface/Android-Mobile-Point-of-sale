package com.ngaitai.ngaitaiafricaapp;


import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.intentfilter.androidpermissions.PermissionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import static java.util.Collections.singleton;

public class SponsorS extends AppCompatActivity{

   private static final String URL = "http://www.amuri.heliohost.org/myfiles/retrieve_sponsors.php";
 // private static final String URL = "http://192.168.1.250/ngaitai/retrieve_sponsors.php";
    private String owner_email;
    private String mail;
    private String jsonResponse = "";
    private TextView textview_call;
    private Button btn_call;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.distributor_list);

        textview_call = (TextView) findViewById(R.id.textview_call);
        progressDialog = new ProgressDialog(this);

        PermissionManager permissionManager = PermissionManager.getInstance(getApplicationContext());
        permissionManager.checkPermissions(singleton(Manifest.permission.GET_ACCOUNTS), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplicationContext(), "Permissions Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(getApplicationContext(), "Permissions Denied", Toast.LENGTH_SHORT).show();
            }
        });


        Pattern gmailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(SponsorS.this).getAccounts();
        for (Account account : accounts) {
            if (gmailPattern.matcher(account.name).matches()) {
                owner_email = account.name;
            }
        }
        mail = owner_email;
        textview_call.setText("");

        progressDialog.setMessage("Checking Details Please Wait...");
        progressDialog.show();
        new getSponsors().execute();


        btn_call=(Button)findViewById(R.id.register_distributor);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PointsModel.class);
                startActivity(intent);
            }
        });

        }


public class getSponsors extends AsyncTask<String, Void, String> {
        ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(SponsorS.this, "please wait", "loading", true);
            pd.setCancelable(true);
        }
        @Override
        protected String doInBackground(String... params) {

            Custom_Volly_Request msgReq = new Custom_Volly_Request(Request.Method.POST,URL,null,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject myMsg = (JSONObject) response.get(i);
                            String DistributorCD = myMsg.getString("DistributorCD");
                            String DistributorName = myMsg.getString("DistributorName");
                            String MobileNo = myMsg.getString("MobileNo");


                            jsonResponse += "SponsorNo:: " + DistributorCD + " ";
                            jsonResponse += "Name:: " + DistributorName + "\n ";
                            jsonResponse += "Phone:: " + MobileNo + "\n";
                            jsonResponse +="------------------"+ "\n\n";
                        }
                        textview_call.setText(jsonResponse);

                    } catch (JSONException e)
                    {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(msgReq);
            return null;
        }
        @Override
        protected void onPostExecute(String result)
        {
            progressDialog.dismiss();
            pd.dismiss();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.GET_ACCOUNTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.GET_ACCOUNTS},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            }


            return false;
        } else

        {
            return true;
        }
    }

}
