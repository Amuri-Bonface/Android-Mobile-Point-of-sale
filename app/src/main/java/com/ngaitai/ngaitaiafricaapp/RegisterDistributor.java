package com.ngaitai.ngaitaiafricaapp;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.intentfilter.androidpermissions.PermissionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singleton;


public class RegisterDistributor extends AppCompatActivity{
    private EditText distributorName,mobileNo,registrationDate,Town,distibutorNo,sponsorNo;

    private Button btnSave;

   //private static final String URL1 ="http://192.168.8.100/ngaitai/last_record.php";
  //  private static final String URL ="http://192.168.8.100/ngaitai/register_distributor.php";
   private static final String URL1 ="http://www.amuri.heliohost.org/myfiles/last_record.php";
    private static final String URL ="http://www.amuri.heliohost.org/myfiles/register_distributor.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_distributor);

        PermissionManager permissionManager = PermissionManager.getInstance(getApplicationContext());
        permissionManager.checkPermissions(singleton(Manifest.permission.SEND_SMS), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
              //  Toast.makeText(getApplicationContext(), "Permissions Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
               // Toast.makeText(getApplicationContext(), "Permissions Denied", Toast.LENGTH_SHORT).show();
            }
        });
////////////////////

        distributorName=(EditText)findViewById(R.id.distributorName);
        mobileNo=(EditText)findViewById(R.id.mobileNo);
        registrationDate=(EditText)findViewById(R.id.regdate);
        Town=(EditText)findViewById(R.id.Town);
        distibutorNo=(EditText)findViewById(R.id.distributorNo);
        sponsorNo=(EditText)findViewById(R.id.sponsorNo);
        btnSave=(Button)findViewById(R.id.btnsave);
        final EditText distributorNo=(EditText)findViewById(R.id.distributorNo);
////////////////////
        StringRequest stringRequest1 =new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String found_user = jsonObject.getString("reply");
                    distributorNo.setText(found_user);
                     }catch (Exception e) {        }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest1);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest1 =new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String found_user = jsonObject.getString("reply");
                            Toast.makeText(getApplicationContext(),found_user+" SMS sent to Distributor ",Toast.LENGTH_LONG).show();
                                finish();
                        }catch (Exception e)
                        {

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("distributorNo", distributorNo.getText().toString());
                        hashMap.put("distributorName", distributorName.getText().toString());
                        hashMap.put("mobileNo", mobileNo.getText().toString());
                        hashMap.put("regDate", registrationDate.getText().toString());
                        hashMap.put("town", Town.getText().toString());
                        hashMap.put("sponsorNo", sponsorNo.getText().toString());

                        return hashMap;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(stringRequest1);

                SmsManager sms = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                    sms = SmsManager.getDefault();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                    sms.sendTextMessage(mobileNo.getText().toString(), null, "Dear "+distributorName.getText().toString()+
                            " Welcome to Ngaitai Africa Your Distributor No. is "+distributorNo.getText().toString() + " Date Registered :"
                            +registrationDate.getText().toString()+" Customer Care No. is 0792089941", null, null);
                }

                String lastmsg="Dear "+distributorName.getText().toString()+
        " Welcome to Ngaitai Africa Your Distributor No. is "+distributorNo.getText().toString() + " Date Registered :"
        +registrationDate.getText().toString()+" Customer Care No. is 0792089941";



                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mobileNo.getText().toString()));
                smsIntent.putExtra("sms_body", lastmsg);
                startActivity(smsIntent);
            }
        });
    }

    public static final int MY_PERMISSIONS_REQUEST_SENDSMS = 99;
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SENDSMS);


            }


            return false;
        } else

        {
            return true;
        }
    }


}