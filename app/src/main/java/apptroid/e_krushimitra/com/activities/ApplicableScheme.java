package apptroid.e_krushimitra.com.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.adapters.ClickListener;
import apptroid.e_krushimitra.com.adapters.RecyclerTouchListener;
import apptroid.e_krushimitra.com.adapters.SearchedSchemeAdapter;
import apptroid.e_krushimitra.com.helper.CustomRequest;
import apptroid.e_krushimitra.com.models.ListSearchItems;

public class ApplicableScheme extends AppCompatActivity {
    public static final String SOL_SCHEME_LIST = "https://www.apptroid.in/ekrushi/api/SchemeSearch.php";
    List<ListSearchItems> listscheme;
    TextView schemeName, link;
    String sn,schemesite,schemeid,question;
    int position;
    Button apply;
    RequestQueue requestQueue;
    ProgressDialog pDialog;
    Toolbar toolbar;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.sol_scheme);
        super.onCreate(savedInstanceState);
        toolbar= (Toolbar) findViewById(R.id.toolbar_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        schemeName =(TextView) findViewById(R.id.tvSchemeName);

        pDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        listscheme = new ArrayList<>();
        final Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            question = bundle.getString("question");
            Log.w("bundlevalue",""+bundle);
        }
        recyclerView = (RecyclerView) findViewById(R.id.search_scheme_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                ListSearchItems listSearchItems = listscheme.get(position);
                schemeid = listSearchItems.getId();
                schemesite= listSearchItems.getCategory();
                Intent i = new Intent(getApplicationContext(),SchemeForm.class);

                Log.i("Selected Site",schemesite);
                i.putExtra("loadsite", schemesite);
                startActivity(i);
            }

            @Override
            public void onLongClick(View child, int childPosition) {}
        }));
            applicableSchemeList();
    }

    private void applicableSchemeList() {

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

            final HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("position", question);



            CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, SOL_SCHEME_LIST, hashMap, this.createRequestSuccessListener(), this.createRequestErrorListener());
            //Utils.PrintErrorLog("Parameters", "" + Constants.BASE_URL + getResources().getString(R.string.SchemeSearch) + hashMap);
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

                            ListSearchItems superTransaction = new ListSearchItems();

                            JSONObject json = result.getJSONObject(i);
                            superTransaction.setAssDetails(json.getString("schemedesc"));
                            superTransaction.setProvision(json.getString("schemelaunch"));
                            superTransaction.setCategory(json.getString("schemesite"));
                            superTransaction.setScheme(json.getString("schemearea"));
                            superTransaction.setSchemename(json.getString("schemename"));



                            listscheme.add(superTransaction);


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
                RecyclerView.Adapter adapter = new SearchedSchemeAdapter(listscheme,ApplicableScheme.this);
                //Adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        return listener;
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
//        Intent startMain = new Intent(getApplicationContext(),MainActivity.class);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(startMain);
        super.onBackPressed();
    }
}

