package apptroid.e_krushimitra.com.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.fragments.SettingsFragment;
import apptroid.e_krushimitra.com.helper.SessionManagement;
import apptroid.e_krushimitra.com.helper.Utils;


public class ChangePasswordActivity extends AppCompatActivity {
    private static final String CHANGE_PASS_URL ="https://www.apptroid.in/ekrushi/api/change_password.php";
    SessionManagement sessionManagement;
    String id;
    TextView tv_email;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    EditText et_new_pass,et_conf_pass;
    Button ChangePass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass_layout);
        toolbar= (Toolbar) findViewById(R.id.toolbar_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(ChangePasswordActivity.this);
        sessionManagement = new SessionManagement(ChangePasswordActivity.this);
        if(sessionManagement.isLoggedIn()){

            HashMap<String,String> users = sessionManagement.getUserDetails();
            id = users.get(SessionManagement.KEY_EMAIL);
            Log.i("Email",id);
        }
        tv_email =(TextView)findViewById(R.id.EmailField);
        tv_email.setText(id);
        et_new_pass=(EditText)findViewById(R.id.NewPassField);
        et_conf_pass=(EditText)findViewById(R.id.ConfPassField);
        ChangePass=(Button)findViewById(R.id.ChangPassword);
        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((et_new_pass.getText().toString()).equals(et_conf_pass.getText().toString()))
                {
                    settingsChangePass();
                }
            }
        });

    }

    private void settingsChangePass() {
        progressDialog.setMessage("Please wait..!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest sr = new StringRequest(Request.Method.POST, CHANGE_PASS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Utils.PrintErrorLog("Response", "" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success")) {
                        Toast.makeText(ChangePasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        Log.i("Password","Updated");
                        Intent i = new Intent(getApplicationContext(), SettingsFragment.class);
                        startActivity(i);
                        progressDialog.dismiss();
                    } else if (jsonObject.names().get(0).equals("error")) {
                        Utils.ShowShortToast(getApplicationContext(), "Something Went Wrong!");
                        Log.i("Password","Not Updated");
                        progressDialog.dismiss();
                    }
                }
                catch (Exception e)
                {
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
                params.put("email",id);
                params.put("password",et_conf_pass.getText().toString());
                Log.i("Paramaeters", String.valueOf(params));
                //returning parameter
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(sr);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
