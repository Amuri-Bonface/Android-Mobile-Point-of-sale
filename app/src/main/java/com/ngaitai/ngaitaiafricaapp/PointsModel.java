package com.ngaitai.ngaitaiafricaapp;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PointsModel extends AppCompatActivity {



     private static final String URL ="http://www.amuri.heliohost.org/myfiles/monthlypoints.php";

   // private static final String URL ="http://192.168.1.250/ngaitai/monthlypoints.php";
    private String jsonResponse = "";
    private ProgressDialog progressDialog;
    private EditText getCode;
    public String distCode;
    private TextView textPoints;
    private Button btncheckpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.points_main);

        progressDialog = new ProgressDialog(this);


        textPoints=(TextView) findViewById(R.id.textPoints);


        btncheckpoints=(Button)findViewById(R.id.btncheckpoints);



        btncheckpoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textPoints.setText(" ");
                getCode=(EditText)findViewById(R.id.editTextPoints);
                distCode=getCode.getText().toString();

               progressDialog.setMessage("Checking Details Please Wait...");
               progressDialog.show();
                new getDistributor().execute();

            }
        });



    }

    public class getDistributor extends AsyncTask<String, Void, String> {
        ProgressDialog pd = null;


        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(PointsModel.this, "please wait", "loading", true);
            pd.setCancelable(true);
        }
        @Override
        protected String doInBackground(String... params) {
           Map<String,String> para=new HashMap<>();
            para.put("distributorCode",distCode);

            Custom_Volly_Request msgReq = new Custom_Volly_Request(Request.Method.POST,URL,para,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject myMsg = (JSONObject) response.get(i);
                            String DistributorName = myMsg.getString("DistributorName");
                            String totalMPBV = myMsg.getString("totalMPBV");
                            String Month = myMsg.getString("Month");
                            String Year = myMsg.getString("Year");
                            String Accum = myMsg.getString("APBV");

                            jsonResponse += "Name: " + DistributorName + "\n\n";
                            jsonResponse += "Month: " + Month + "\n\n";
                            jsonResponse += "Points: " + totalMPBV + "\n\n";
                            jsonResponse += "Year: " + Year + "\n\n";
                            jsonResponse += "Total Points: " + Accum + "\n\n";
                            jsonResponse +="------------------"+ "\n\n";
                        }
                        textPoints.setText(jsonResponse);

                    } catch (JSONException e)
                    {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            }){

            }
                    ;
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(msgReq);
            return null;
        }
        @Override
        protected void onPostExecute(String result)
        {
            progressDialog.dismiss(); pd.dismiss();
        }
    }
}
