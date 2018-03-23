package com.ngaitai.ngaitaiafricaapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {
  private static final String URL ="http://www.amuri.heliohost.org/myfiles/login.php";

 // private static final String URL ="http://192.168.1.232/ngaitai/login.php";

    private EditText user_name;
    private EditText password;
    private EditText shop;
    private StringRequest request;
    private RequestQueue requestQueue;
    private Button sign_in_register;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        user_name=(EditText)findViewById(R.id.user_name);
        password=(EditText)findViewById(R.id.distributorNo);
        shop=(EditText)findViewById(R.id.shopNo);
        sign_in_register=(Button) findViewById(R.id.sign_in_register);
        progressDialog = new ProgressDialog(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user=user_name.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String shopz=shop.getText().toString().trim();
                //checking if email and passwords are empty
                if(TextUtils.isEmpty(user)){
                    Toast.makeText(Login.this,"Please enter uname",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(Login.this,"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(shopz)){
                    Toast.makeText(Login.this,"Please enter Shop No",Toast.LENGTH_LONG).show();
                    return;
                }
                //if the email and password are not empty
                //displaying a progress dialog

                progressDialog.setMessage("Checking Login Details Please Wait...");
                progressDialog.show();

                StringRequest stringRequest1 =new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String found_user = jsonObject.getString("reply");
                            String success = jsonObject.getString("reply1");
                            Toast.makeText(getApplicationContext(),found_user+"  ",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                if (Objects.equals(success, "success"))
                                {
                                   Intent myIntent = new Intent(Login.this, MainActivity_2.class);
                                   startActivity(myIntent);
                                    finish();
                                }
                            }
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
                        hashMap.put("login_name", user_name.getText().toString());
                        hashMap.put("login_pass", password.getText().toString());
                        hashMap.put("shop_no", shop.getText().toString());

                        return hashMap;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(stringRequest1);
            }
        });

    }
}
