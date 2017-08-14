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
import apptroid.e_krushimitra.com.adapters.FormAdapter;
import apptroid.e_krushimitra.com.adapters.RecyclerTouchListener;
import apptroid.e_krushimitra.com.adapters.SearchedSchemeAdapter;
import apptroid.e_krushimitra.com.helper.CustomRequest;
import apptroid.e_krushimitra.com.helper.SessionManagement;
import apptroid.e_krushimitra.com.helper.Utils;
import apptroid.e_krushimitra.com.models.FormDetails;
import apptroid.e_krushimitra.com.models.ListSearchItems;

public class FormsLists extends AppCompatActivity {
    public static final String FORM_LIST = "https://www.apptroid.in/ekrushi/api/demo/FormList.php";
    List<FormDetails> formdet;
    RequestQueue requestQueue;
    ProgressDialog pDialog;
    private RecyclerView recyclerView;
    Toolbar toolbar;
    int position;
    String formid, name,email,id1,id;
    SessionManagement sm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_list);
        toolbar= (Toolbar) findViewById(R.id.toolbar_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        formdet = new ArrayList<>();
        sm = new SessionManagement(FormsLists.this);
        int i= 1;
        id = Integer.toString(i);
//        if(sm.isLoggedIn()){
//
//            HashMap<String , String> user = sm.getUserDetails();
//
//            email = user.get(SessionManagement.KEY_EMAIL);
//            //id = user.get(SessionManagement.KEY_ID);
//
//            Utils.PrintErrorLog("session",""+email+id);
//
//        }


        recyclerView = (RecyclerView) findViewById(R.id.search_form_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onLongClick(View child, int childPosition) {

            }
        }));
        userFormLists();

    }

    private void userFormLists() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("position", id);



        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, FORM_LIST, hashMap, this.createRequestSuccessListener(), this.createRequestErrorListener());
        //Utils.PrintErrorLog("Parameters", "" + Constants.BASE_URL + getResources().getString(R.string.SchemeSearch) + hashMap);
        requestQueue.add(jsObjRequest);
    }

    private Response.ErrorListener createRequestErrorListener() {
        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();
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

                            FormDetails superTransaction = new FormDetails();

                            JSONObject json = result.getJSONObject(i);
                            superTransaction.setName(json.getString("name"));




                            formdet.add(superTransaction);


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
                RecyclerView.Adapter adapter = new FormAdapter(formdet,FormsLists.this);
                //Adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        return listener;
    }
}
