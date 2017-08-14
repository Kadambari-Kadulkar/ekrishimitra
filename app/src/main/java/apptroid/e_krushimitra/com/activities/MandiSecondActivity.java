package apptroid.e_krushimitra.com.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.adapters.ClickListener;
import apptroid.e_krushimitra.com.adapters.CropAdapter;
import apptroid.e_krushimitra.com.adapters.RecyclerTouchListener;
import apptroid.e_krushimitra.com.helper.CustomRequest;
import apptroid.e_krushimitra.com.helper.Utils;
import apptroid.e_krushimitra.com.models.Crop;

public class MandiSecondActivity extends AppCompatActivity {
    public static final String CROP_LIST = "https://www.apptroid.in/ekrushi/api/search_crop.php";
    TextView crop;
    String c1, c, s;
    String v_id;



    private List<Crop> cropList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CropAdapter mAdapter;
    RequestQueue requestQueue;
    ProgressDialog pDialog;
    Toolbar toolbar;
    Drawer result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_list);
        toolbar= (Toolbar) findViewById(R.id.toolbar_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        crop =(TextView) findViewById(R.id.CropName);

        requestQueue = Volley.newRequestQueue(this);
        Intent in=getIntent();
        final Bundle bundle = getIntent().getExtras();
        c = bundle.getString("state");
        s = bundle.getString("city");
        c1 = bundle.getString("crop");
        Log.i("Values", c);
        Log.i("Values", s);
        Log.i("Values", c1);

        crop.setText(c1);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Crop crop = cropList.get(position);
                Intent i = new Intent(getApplicationContext(),VendorsMandiActivity.class);
                i.putExtra("crop",c1);
                i.putExtra("city",s);
             i.putExtra("v_id",v_id);
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getCropList();
    }

    private void getCropList()
    {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("crop",c1);
        hashMap.put("city",s);
        hashMap.put("state",c);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, CROP_LIST, hashMap, this.createRequestSuccessListener(), this.createRequestErrorListener());
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

                            Crop c = new Crop();
                            JSONObject json = result.getJSONObject(i);
                            c.setCity(json.getString("city"));
                            c.setState(json.getString("state"));
                            c.setDate(json.getString("time"));
                            c.setQuantity(json.getString("amount"));
                            c.setUnit(json.getString("unit"));
                           // c.setLess(json.getString("less"));
                            v_id = json.getString("v_id");


                            cropList.add(c);


                            pDialog.hide();
                        }

                    }else{
                        pDialog.hide();

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.hide();
                }

                //Finally initializing our adapter
                RecyclerView.Adapter adapter = new CropAdapter(cropList,MandiSecondActivity.this);
                //Adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        return listener;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}



