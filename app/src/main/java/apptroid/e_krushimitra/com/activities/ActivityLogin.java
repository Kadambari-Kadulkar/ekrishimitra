package apptroid.e_krushimitra.com.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.helper.Constants;
import apptroid.e_krushimitra.com.helper.SessionManagement;
import apptroid.e_krushimitra.com.helper.Utils;

public class ActivityLogin extends AppCompatActivity {

    TextView textViewSignup;

    EditText etLoginEmail,etLoginPass;
    Locale myLocale;
    LinearLayout LinLayLoginBtn;
    ProgressDialog progressDialog;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        sessionManagement = new SessionManagement(this);

        textViewSignup = (TextView)findViewById(R.id.textViewSignup);
        etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        etLoginPass = (EditText)findViewById(R.id.etLoginPass);
        LinLayLoginBtn = (LinearLayout) findViewById(R.id.LinLayLoginBtn);

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ActivityRegister.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
            }
        });

        LinLayLoginBtn.setOnClickListener(new View.OnClickListener() {
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

    public boolean validFields(){

        if(etLoginEmail.getText().toString().length() == 0){

            Utils.ShowShortToast(getApplicationContext(),"Enter Email");

            return false;
        }
        if (!Utils.isValidEmail(etLoginEmail.getText().toString())){

            Utils.ShowShortToast(getApplicationContext(),"Enter Valid Email");

            return false;
        }
        if(etLoginPass.getText().toString().length() == 0){


            Utils.ShowShortToast(getApplicationContext(),"Enter Password");

            return false;
        }


     return true;
    }
    public void DoRegisterNew() {
        progressDialog.setMessage("Please wait..!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = Constants.BASE_URL + getResources().getString(R.string.loginUrl);
        Log.w("url",""+url);
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Utils.PrintErrorLog("Response", "" + response);
                    JSONObject jsonObject =  new JSONObject(response);
                    if(jsonObject.names().get(0).equals("pass")){
                        String Name = jsonObject.getString("name");
                        String id = jsonObject.getString("id");
                        Utils.ShowShortToast(getApplicationContext(), "Successfully Login! Welcome to E-Krushi");
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        sessionManagement.createLoginSession(Name,etLoginEmail.getText().toString(),id);

                        setLocale(getApplicationContext(),"en");
                        progressDialog.dismiss();
                    }else if(jsonObject.names().get(0).equals("fail")){
                        Utils.ShowShortToast(getApplicationContext(), "Account is not activated!");
                        progressDialog.dismiss();
                    } else  if(jsonObject.names().get(0).equals("error")){
                        Utils.ShowShortToast(getApplicationContext(), "No such edituser exist...!");
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
                hashMap.put("email", etLoginEmail.getText().toString());
                hashMap.put("pass", etLoginPass.getText().toString());

                Log.w("hashmap",""+hashMap);
                return hashMap;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void setLocale(Context con, String lang) {

        myLocale = new Locale(lang);
        Resources res = con.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        sessionManagement.selectLangSession(lang);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }


}
