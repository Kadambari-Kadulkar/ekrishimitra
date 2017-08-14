package apptroid.e_krushimitra.com.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import apptroid.e_krushimitra.com.helper.SessionManagement;
import apptroid.e_krushimitra.com.helper.Utils;
import apptroid.e_krushimitra.com.models.ListSearchItems;


public class SearchedScheme extends AppCompatActivity {

    //Creating a List of superheroes
    List<ListSearchItems> listscheme;
    public static final String SCHEME_LIST = "https://www.apptroid.in/ekrushi/api/new.php";

    //Creating Views
    private RecyclerView recyclerView;
    SessionManagement sessionManagement;
    Utils utils;
    String name,scheme,schemeid,schemesite;
    ProgressDialog pDialog;
    ImageView no_internet;
    TextView errorText;
    int id;
    Toolbar toolbar;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searched_scheme);
        toolbar= (Toolbar) findViewById(R.id.toolbar_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        no_internet= (ImageView)findViewById(R.id.no_internet);
        errorText =(TextView)findViewById(R.id.errorText);


        sessionManagement = new SessionManagement(this);
        requestQueue = Volley.newRequestQueue(this);
        utils = new Utils();

        no_internet.setVisibility(View.INVISIBLE);

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
//                ListSearchItems listSearchItems = listscheme.get(position);
//                schemeid = listSearchItems.getId();
//                schemesite= listSearchItems.getCategory();
//                Intent i = new Intent(getApplicationContext(),AppliedScheme.class);
//
//                Log.i("Selected Site",schemesite);
//                i.putExtra("loadsite", schemesite);
//                startActivity(i);
            }

            @Override
            public void onLongClick(View child, int childPosition) {

            }
        }));



        //Initializing our superheroes list

        listscheme = new ArrayList<>();
        final Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            id = bundle.getInt("cat_id");
            scheme = bundle.getString("s");
            Log.w("bundlevalue",""+bundle.toString());
        }
        GetSchemesSearched();
    }

       /** public void GetSchemeSearched() {
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

            final HashMap<String, String> hashMap = new HashMap<String, String>();
            //hashMap.put("s",state);
            hashMap.put("sname",name);
            hashMap.put("s",state);


            Log.i("Params category no", String.valueOf(id));



            CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, SCHEME_LIST, hashMap, this.createRequestSuccessListener(), this.createRequestErrorListener());
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
                                superTransaction.setProvision(json.getString("schemedate"));
                                superTransaction.setCategory(json.getString("schemelink"));
                                superTransaction.setScheme(json.getString("schemearea"));
                                superTransaction.setSchemename(json.getString("schemename"));

                                listscheme.add(superTransaction);

                                errorText.setVisibility(View.INVISIBLE);
                                pDialog.hide();
                            }

                        }else{
                            pDialog.hide();
                            errorText.setVisibility(View.VISIBLE);
                        }

                    }catch (JSONException e) {
                        e.printStackTrace();
                        pDialog.hide();
                    }

                    //Finally initializing our adapter
                    RecyclerView.Adapter adapter = new SearchedSchemeAdapter(listscheme,SearchedScheme.this);
                    //Adding adapter to recyclerview
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            };
            return listener;
        }**/

//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menu) {
//        switch (menu.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(menu);
//        }
//    }

    public void GetSchemesSearched() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        final HashMap<String, String> hashMap = new HashMap<String, String>();
        //hashMap.put("s",state);
        //hashMap.put("sname",name);
        hashMap.put("cat_id", String.valueOf(id));
        hashMap.put("s",scheme);


        Log.i("Params..",String.valueOf(hashMap));



        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, SCHEME_LIST, hashMap, this.createRequestSuccessListener(), this.createRequestErrorListener());
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

                            errorText.setVisibility(View.INVISIBLE);
                            pDialog.hide();
                        }

                    }else{
                        pDialog.hide();
                        errorText.setVisibility(View.VISIBLE);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.hide();
                }

                //Finally initializing our adapter
                RecyclerView.Adapter adapter = new SearchedSchemeAdapter(listscheme,SearchedScheme.this);
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
        Intent startMain = new Intent(getApplicationContext(),MainActivity.class);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startMain);

        //finish();
    }
}
