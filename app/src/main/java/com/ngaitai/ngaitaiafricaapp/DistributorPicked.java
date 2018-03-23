package com.ngaitai.ngaitaiafricaapp;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.ngaitai.ngaitaiafricaapp.R.id.listView;

public class DistributorPicked extends AppCompatActivity {
    ArrayList<DistributorModel> arrayList;
    ListView lv;
    public ProgressDialog progressDialog;

    private String distributorName,distributorCode,ditributorPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distributor_sold_to_main);

        progressDialog = new ProgressDialog(this);

        arrayList = new ArrayList<>();
        lv = (ListView) findViewById(listView);

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://www.amuri.heliohost.org/myfiles/distributor_sold_to.php");
              //  new ReadJSON().execute("http://192.168.1.232/ngaitai/distributor_sold_to.php");

            }

        });


    }

        class ReadJSON extends AsyncTask<String, Integer, String > {

            @Override
            protected String doInBackground(String... params) {
                return readURL(params[0]);
            }

            @Override
            protected void onPostExecute(String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    JSONArray jsonArray =  jsonObject.getJSONArray("distributors");

                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        arrayList.add(new DistributorModel(
                                productObject.getString("DistributorCD"),
                                productObject.getString("DistributorName"),
                                productObject.getString("MobileNo")

                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomDistributorAdapter adapter = new CustomDistributorAdapter(
                        getApplicationContext(), R.layout.custom_distributor_layout, arrayList
                );
                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3)
                    {


                        AlertDialog.Builder builder;
                        builder=new android.support.v7.app.AlertDialog.Builder(DistributorPicked.this);
                        //builder = new AlertDialog.Builder(getApplicationContext());

                        builder.setTitle("Distributor Picked")
                                .setMessage("Are you sure you want to Pick this Distributor?" )
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        DistributorModel distributorModel = arrayList.get(position);

                                        distributorCode=distributorModel.getDistributorCode();
                                        distributorName=distributorModel.getDistibutorName();
                                       ditributorPhone=distributorModel.getDistributorphone();

                                        Toast.makeText(getApplicationContext(),distributorName,Toast.LENGTH_LONG).show();

                                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        myIntent.putExtra("distName", distributorName);
                                        myIntent.putExtra("distCode", distributorCode);
                                        myIntent.putExtra("distPhone", ditributorPhone);
                                        startActivity(myIntent);
                                        finish();

                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .show();

                    }
                });
                progressDialog.dismiss();}


        }
    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    }

