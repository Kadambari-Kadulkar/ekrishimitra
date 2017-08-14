package apptroid.e_krushimitra.com.activities;


import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.helper.CustomRequest;
import apptroid.e_krushimitra.com.helper.SessionManagement;
import apptroid.e_krushimitra.com.helper.Utils;


import static android.R.attr.id;
import static apptroid.e_krushimitra.com.R.id.view;

public class SchemeForm extends AppCompatActivity {
    public static final String SCHEME_FORM = "https://www.apptroid.in/ekrushi/api/demo/convert.php";
    Button save_pdf,down_pdf,history;
    SessionManagement sessionManagement;
    TextView PerDtail;
    EditText name,fathus,age;
    Spinner gen;
    String form_id,filled_id,email;
    private RequestQueue requestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManagement = new SessionManagement(SchemeForm.this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        PerDtail =(TextView)findViewById(R.id.PerDtail);
        name =(EditText)findViewById(R.id.name);
        fathus =(EditText)findViewById(R.id.HusFatName);
        age =(EditText)findViewById(R.id.age);
        gen =(Spinner)findViewById(R.id.SpinnerGen);
        history =(Button)findViewById(R.id.history);
        save_pdf =(Button)findViewById(R.id.savepdf);
        //down_pdf = (Button)findViewById(R.id.genpdf);
        save_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPdfTcpdf();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),FormsLists.class);
                startActivity(i);
            }
        });
        if(sessionManagement.isLoggedIn()){

            HashMap<String , String> user = sessionManagement.getUserDetails();

            email = user.get(SessionManagement.KEY_EMAIL);
            filled_id = user.get(SessionManagement.KEY_ID);

            Utils.PrintErrorLog("session",""+email+filled_id);

        }


    }


    public void createPdfTcpdf() {

        final HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("name", name.getText().toString());
        hashMap.put("fatname", fathus.getText().toString());
        hashMap.put("age", age.getText().toString());
        hashMap.put("gender", gen.getSelectedItem().toString());
        hashMap.put("user_id", filled_id);




        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, SCHEME_FORM, hashMap, this.createRequestSuccessListener(), this.createRequestErrorListener());
        Utils.PrintErrorLog("Parameters", ""+ hashMap);
        requestQueue.add(jsObjRequest);
    }

    private Response.ErrorListener createRequestErrorListener() {
        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Utils.ShowLongToast(getApplicationContext(), error.toString());
            }
        };
        return err;
    }


    private Response.Listener<JSONObject> createRequestSuccessListener() {
        Response.Listener listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JSON Response", response.toString());
                if(response.toString().contains("Success")){
                    try {
                        JSONArray result = response.getJSONArray("result");
                        if (result.length() > 0) {
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject json = result.getJSONObject(i);
                                form_id =json.getString("id");
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else{
                    Utils.ShowLongToast(getApplicationContext(), "Something went wrong!");
                }

            }
        };
        return listener;
    }

}



