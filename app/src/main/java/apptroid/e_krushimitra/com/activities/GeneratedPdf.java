package apptroid.e_krushimitra.com.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import apptroid.e_krushimitra.com.helper.CustomRequest;
import apptroid.e_krushimitra.com.helper.Utils;

public class GeneratedPdf extends AppCompatActivity {
    public static final String FORM_DOWNLOAD = "https://www.apptroid.in/ekrushi/api/demo/tcpdf/examples/pdf.php";
    String form_entry,file_path;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        form_entry = bundle.getString("lastform_id");
        Log.i("Values",""+form_entry);
        pdfDownload();




    }
    public void pdfDownload() {

        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id",form_entry);



        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, FORM_DOWNLOAD, hashMap, this.createRequestSuccessListener(), this.createRequestErrorListener());
        Utils.PrintErrorLog("Parameters", ""+ hashMap);
        requestQueue.add(jsObjRequest);
    }

    private Response.ErrorListener createRequestErrorListener() {
        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Log.i("Errror",error.toString());
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
                if(response.toString().contains("success")) {

                    try {
                        file_path = response.getString("path");
                        viewPdf(file_path);
                        Log.i("File Path",file_path);
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

    private void viewPdf(String p){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(p), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
