package apptroid.e_krushimitra.com.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.mikepenz.materialdrawer.Drawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.helper.CustomRequest;
import apptroid.e_krushimitra.com.helper.Utils;
import apptroid.e_krushimitra.com.models.Vendor;


public class VendorsMandiActivity extends AppCompatActivity{
    public static final String VENDOR_DETAIL ="https://www.apptroid.in/ekrushi/api/get_vendor.php";
    TextView crop, location,phone,add,name;
    String crop1,city1;
    List<Vendor> vendorList;
    String i;
    Drawer result;
    RequestQueue requestQueue;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mandi);
        //result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        crop= (TextView) findViewById(R.id.location);
        name= (TextView) findViewById(R.id.name);
        phone= (TextView) findViewById(R.id.phone);
        add= (TextView) findViewById(R.id.add);
        Bundle bundle = getIntent().getExtras();
        crop1 = bundle.getString("crop");
        city1 = bundle.getString("city");
        i = bundle.getString("v_id");
        Log.i("Values",""+city1+""+crop1);
        crop.setText(""+crop1+"-"+""+city1);
        requestQueue = Volley.newRequestQueue(this);

        getVendorDetail();

    }

    private void getVendorDetail() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        final HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("v_id",i);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, VENDOR_DETAIL, hashMap, this.createRequestSuccessListener(), this.createRequestErrorListener());
        Utils.PrintErrorLog("Parameters", ""+ hashMap);
        requestQueue.add(jsObjRequest);
    }

    private Response.ErrorListener createRequestErrorListener() {
        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                pDialog.hide();
            }
        };
        return err;
    }

    private Response.Listener<JSONObject> createRequestSuccessListener() {

        Response.Listener listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JSON Response", response.toString());
                try {

                    JSONArray result = response.getJSONArray("result");
                    if (result.length() > 0) {
                        for (int i = 0; i < result.length(); i++) {

                            JSONObject json = result.getJSONObject(i);
                            name.setText(json.getString("name"));
                            add.setText(json.getString("add"));
                            phone.setText(json.getString("phone"));
                            pDialog.hide();
                        }

                    }else{
                        pDialog.hide();

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.hide();
                }


            }
        };
        return listener;
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(getApplicationContext(),MainActivity.class);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startMain);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
