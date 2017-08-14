package apptroid.e_krushimitra.com.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.helper.Constants;
import apptroid.e_krushimitra.com.helper.Utils;

public class ActivityRegister extends AppCompatActivity  {
    public static final String FILTERED_CITY_LIST = "https://www.apptroid.in/ekrushi/api/filter_city.php";

    TextView textViewLogin;
    EditText editTextName,editTextEmail,editTextPass,editTextPhone;
    LinearLayout LinLaySignup;
    ProgressDialog progressDialog;
    Spinner filter_city_spinner, state_spinner;
    String s,s1,item;
    private ArrayList<String> city;
    private JSONArray res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        city = new ArrayList<String>();
        city.add("SELECT CITY");
        //type casting of edit text
        progressDialog = new ProgressDialog(this);
        textViewLogin = (TextView)findViewById(R.id.textViewLogin);
        editTextName = (EditText)findViewById(R.id.editTextName);
        LinLaySignup = (LinearLayout) findViewById(R.id.LinLaySignup);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPass = (EditText)findViewById(R.id.editTextPass);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        state_spinner = (Spinner) findViewById(R.id.state_spinner);
        filter_city_spinner = (Spinner) findViewById(R.id.filter_city_spinner);

        List<String> sta = new ArrayList<String>();
        sta.add("SELECT STATE");
        sta.add("Maharashtra");
        sta.add("Andaman and Nicobar Islands");
        sta.add("Andhra Pradesh");
        sta.add("Arunachal Pradesh");
        sta.add("Assam");
        sta.add("Bihar");
        sta.add("Chhattisgarh");
        sta.add("Dadra and Nagar Haveli");
        sta.add("Daman and Diu");
        sta.add("Delhi");
        sta.add("Goa");
        sta.add("Gujarat");
        sta.add("Haryana");
        sta.add("Himachal Pradesh");
        sta.add("Jammu and Kashmir");
        sta.add("Jharkhand");
        sta.add("Karnataka");
        sta.add("Kerala");
        sta.add("Lakshadweep");
        sta.add("Madhya Pradesh");
        sta.add("Manipur");
        sta.add("Meghalaya");
        sta.add("Mizoram");
        sta.add("Nagaland");
        sta.add("Orissa");
        sta.add("Pondicherry");
        sta.add("Punjab");
        sta.add("Rajasthan");
        sta.add("Sikkim");
        sta.add("West Bengal");
        sta.add("Tamil Nadu");
        sta.add("Tripura");
        sta.add("Uttar Pradesh");
        sta.add("Ghazipur");
        sta.add("Hardoi");
        sta.add("Rampur");
        sta.add("Agra");
        sta.add("Farrukhabad");
        sta.add("Bulandshahr");
        sta.add("Uttarakhand");
        sta.add("Karnataka");
        sta.add("Purulia");
        sta.add("Tamil Nadu");



        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sta);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_spinner.setAdapter(stateAdapter);
        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getSelectedItem().toString();
                city.clear();
                filteredCityList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        // set onclick event

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ActivityLogin.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
            }
        });

        LinLaySignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validFields()== true){

                    if (Utils.getConnectivityStatus(getApplicationContext()) != 0) {
                        DoRegisterNew();
                    } else {
                        Utils.ShowLongToast(getApplicationContext(), getResources().getString(R.string.nointernet));
                        finish();
                    }
                }
            }
        });

    }

   public  boolean validFields(){

       if(editTextName.getText().toString().length() == 0){

           Utils.ShowShortToast(getApplicationContext(),"Enter Full Name");

           return false;
       }
       if(editTextEmail.getText().toString().length() == 0){

           Utils.ShowShortToast(getApplicationContext(),"Enter Email");

           return false;
       }
       if (!Utils.isValidEmail(editTextEmail.getText().toString())){

           Utils.ShowShortToast(getApplicationContext(),"Enter Valid Email");

           return false;
       }
       if(editTextPass.getText().toString().length() == 0){

           Utils.ShowShortToast(getApplicationContext(),"Enter Password");

           return false;
       }
       if(editTextPhone.getText().toString().length() == 0){

           Utils.ShowShortToast(getApplicationContext(),"Enter Phone No.");

           return false;
       }
       return true;
   }


    public void DoRegisterNew() {
        progressDialog.setMessage("Please wait..!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = Constants.BASE_URL + getResources().getString(R.string.signupUrl);
        Log.w("url",""+url);
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Utils.PrintErrorLog("Response", "" + response);
                    JSONObject jsonObject =  new JSONObject(response);
                    if(jsonObject.names().get(0).equals("success")){
                        Utils.ShowShortToast(getApplicationContext(), "Successfully registered! Please verify your account!");
                        Intent i = new Intent(getApplicationContext(),ActivityPreLogin.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        progressDialog.dismiss();
                    }else if(jsonObject.names().get(0).equals("fail")){
                        Utils.ShowShortToast(getApplicationContext(), "Please try again!");
                        progressDialog.dismiss();
                    } else  if(jsonObject.names().get(0).equals("error")){
                        Utils.ShowShortToast(getApplicationContext(), "Email already in use...!");
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {

                    Log.w("Error tag",""+e);
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.PrintErrorLog("Volley Error : ", "" + error);
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("name", editTextName.getText().toString());
                hashMap.put("email", editTextEmail.getText().toString());
                hashMap.put("pass", editTextPass.getText().toString());
                hashMap.put("phone", editTextPhone.getText().toString());

                Log.w("hashmap",""+hashMap);
                return hashMap;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }



    public void filteredCityList(){

        city.add("SELECT CITY");
        StringRequest sr = new StringRequest(Request.Method.POST, FILTERED_CITY_LIST , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    res = j.getJSONArray("result");
                    Log.i("Response",response);
                    getStudents(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", String.valueOf(error));
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("state",item);
                Log.i("Paramaeters", String.valueOf(params));
                //returning parameter
                return params;
            }
            };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);
    }

    private void getStudents(JSONArray res) {
        for(int i=0;i<res.length();i++){
            try {
                JSONObject json = res.getJSONObject(i);

                city.add(json.getString("city_name"));
                Log.i("JSONArray Result", String.valueOf(res));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,city);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter_city_spinner.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();

    }
    }

