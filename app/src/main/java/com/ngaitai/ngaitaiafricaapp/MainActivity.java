package com.ngaitai.ngaitaiafricaapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.intentfilter.androidpermissions.PermissionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.ngaitai.ngaitaiafricaapp.R.id.listView;
import static java.util.Collections.singleton;

public class MainActivity extends AppCompatActivity {

    ArrayList<Product> arrayList;
    ListView lv;
    private String test,price;
    private String image;
    private String Description;
    private String SellingPrice;
    private String Remarks;
    private String BV;
    private Button cart,buttonCust;
    private String distributorName,distributorCode,distributorPhone,qty;
    private TextView textViewPhone,textViewName,textViewCode;
    private int totBV;
    private float totPrice;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseItems, moreDatabaseItems;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_center);

        textViewCode=(TextView)findViewById(R.id.textViewCode);
        textViewName=(TextView)findViewById(R.id.textViewName);
        textViewPhone=(TextView)findViewById(R.id.textViewPhone);

        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        databaseItems= FirebaseDatabase.getInstance().getReference("Items").child(user.getUid());
        moreDatabaseItems= FirebaseDatabase.getInstance().getReference("Sold Items").child(user.getUid());
        PermissionManager permissionManager = PermissionManager.getInstance(getApplicationContext());
        permissionManager.checkPermissions(singleton(Manifest.permission.INTERNET), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplicationContext(), "Permissions Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
               Toast.makeText(getApplicationContext(), "Permissions Denied", Toast.LENGTH_SHORT).show();
            }
        });

        arrayList = new ArrayList<>();
        lv = (ListView) findViewById(listView);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
              new ReadJSON().execute("http://www.amuri.heliohost.org/myfiles/list_test.php");
             // new ReadJSON().execute("http://192.168.1.232/ngaitai/list_test.php");

            }

        });

        Intent intent = getIntent();
        String Name= intent.getStringExtra("distName");
        String Code= intent.getStringExtra("distCode");
        String Phone= intent.getStringExtra("distPhone");

        textViewName.setText(Name);
        textViewCode.setText(Code);
        textViewPhone.setText(Phone);



        buttonCust=(Button)findViewById(R.id.buttonCust);

        buttonCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DistributorPicked.class);
                startActivityForResult(intent,100);
            }
        });
        cart=(Button)findViewById(R.id.shopCart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShoppingItems.class);
                startActivity(intent);
            }
        });
        }



    class ReadJSON extends AsyncTask<String, Integer, String > {
        ProgressDialog pd = null;


        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(MainActivity.this, "please wait", "loading", true);
            pd.setCancelable(true);
        }
        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("products");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new Product(
                            productObject.getString("image"),
                            productObject.getString("Description"),
                            productObject.getString("SellingPrice"),
                            productObject.getString("Remarks"),
                            productObject.getString("BV")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapter adapter = new CustomListAdapter(
                    getApplicationContext(), R.layout.custom_list_layout, arrayList

            );

            lv.setAdapter(adapter);
            pd.dismiss();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3)
                {


                    AlertDialog.Builder builder;
                    builder=new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                    //builder = new AlertDialog.Builder(getApplicationContext());

                    builder.setTitle("Item Picked")
                            .setMessage("Are you sure you want to Sale this Item?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Product product = arrayList.get(position);
                                    SellingPrice=product.getSellingPrice();
                                    image=product.getImage();
                                    Description=product.getDescription();
                                    Remarks=product.getRemarks();
                                    BV=product.getBV();

                                    pick_item();


                                   // Toast.makeText(MainActivity.this, "" + position + product.getSellingPrice(), Toast.LENGTH_SHORT).show();

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
        }
    }


    public void pick_item()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.custom_qty, null);

        Button btok = (Button) view.findViewById(R.id.buttonOk);
        final EditText quantity=(EditText) view.findViewById(R.id.editTextQty);

        mBuilder.setView(view);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(500,200);

        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    qty=quantity.getText().toString().trim();

                AlertDialog.Builder builder;

                builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Confirm Quantity ")
                        .setMessage("Is this your Quantity}")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                totBV=(Integer.parseInt(BV)*Integer.parseInt(qty));
                                totPrice=Float.parseFloat(SellingPrice)*Integer.parseInt(qty);

                                postToFirebase();
                                postToFirebase2();


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

                dialog.dismiss();

            }
        });

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
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            }


            return false;
        } else

        {
            return true;
        }
    }

public void postToFirebase()

{

    distributorName=textViewName.getText().toString();
    distributorCode=textViewCode.getText().toString();
    distributorPhone=textViewPhone.getText().toString();

    String uniQue= databaseItems.push().getKey();



    ProductFirebase createItem= new ProductFirebase(image,Description,SellingPrice,Remarks,BV,distributorCode,distributorName,distributorPhone,qty,totBV,totPrice);

    databaseItems.child(uniQue).setValue(createItem);
    Toast.makeText(getApplicationContext(),"Item Added",Toast.LENGTH_LONG).show();
}
    public void postToFirebase2()

    {

        distributorName=textViewName.getText().toString();
        distributorCode=textViewCode.getText().toString();
        distributorPhone=textViewPhone.getText().toString();

      String uniQue2= moreDatabaseItems.push().getKey();

        ProductFirebase createItem= new ProductFirebase(image,Description,SellingPrice,Remarks,BV,distributorCode,distributorName,distributorPhone,qty,totBV,totPrice);

        moreDatabaseItems.child(uniQue2).setValue(createItem);
        Toast.makeText(getApplicationContext(),"Item Added",Toast.LENGTH_LONG).show();
    }
}
