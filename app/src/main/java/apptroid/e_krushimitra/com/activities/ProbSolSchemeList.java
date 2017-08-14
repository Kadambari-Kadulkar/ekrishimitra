package apptroid.e_krushimitra.com.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.adapters.SearchedSchemeAdapter;
import apptroid.e_krushimitra.com.helper.CustomRequest;
import apptroid.e_krushimitra.com.models.ListSearchItems;

public class ProbSolSchemeList extends AppCompatActivity {
    public static final String SOL_SCHEME_LIST = "https://www.apptroid.in/ekrushi/api/levishtein_search_scheme.php";
    int pos,exactMatch;
    Button done;
    Spinner QueSpinner;
    Toolbar toolbar;
    EditText questionEditText;
    TextView didumeanTextView;
    String quest,didumean;
    ProgressDialog pDialog;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.layout_toinfliate);
        super.onCreate(savedInstanceState);
        toolbar= (Toolbar) findViewById(R.id.toolbar_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

        done = (Button) findViewById(R.id.done);
        questionEditText = (EditText) findViewById(R.id.questionTextView);
        didumeanTextView = (TextView) findViewById(R.id.didumean);
//        QueSpinner=(Spinner)findViewById(R.id.que_spinner);
//        String[] myQueArray = getResources().getStringArray(R.array.question);
//        List<String> s3 = Arrays.asList(myQueArray);
//
//        ArrayAdapter<String> qAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,s3);
//        qAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        QueSpinner.setAdapter(qAdapter);
//        QueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                pos = adapterView.getSelectedItemPosition();
//                Log.i("Selected Item Position", String.valueOf(pos));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bundle b = new Bundle();
                if(questionEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(ProbSolSchemeList.this, "Please Enter Question", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    correctQuestion();
                }

            }
        });



    }
    private void correctQuestion() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("sname", questionEditText.getText().toString());



        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, SOL_SCHEME_LIST, hashMap, this.createRequestSuccessListener(), this.createRequestErrorListener());
        //Utils.PrintErrorLog("Parameters", "" + Constants.BASE_URL + getResources().getString(R.string.SchemeSearch) + hashMap);
        Log.i("hashMap", String.valueOf(hashMap));
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

                            exactMatch = json.getInt("exact match");
                            didumean = json.getString("didumean");
                            quest = json.getString("schemename");
                            if(exactMatch == 0)
                            {
                                didumeanTextView.setVisibility(View.VISIBLE);
                                didumeanTextView.setText("Did You Mean:"+didumean);
                                didumeanTextView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        questionEditText.setText(didumean);
                                        Intent i4 = new Intent(getApplicationContext(),ApplicableScheme.class);
                                        i4.putExtra("question",quest);
                                        i4.putExtra("didumean",didumean);

                                        startActivity(i4);
                                    }
                                });
                            }
                            else
                            {
                                didumeanTextView.setVisibility(View.GONE);
                                Intent i4 = new Intent(getApplicationContext(),ApplicableScheme.class);
                                i4.putExtra("question",quest);
                                startActivity(i4);
                            }

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
//                RecyclerView.Adapter adapter = new SearchedSchemeAdapter(listscheme,ApplicableScheme.this);
//                //Adding adapter to recyclerview
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
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
        super.onBackPressed();
    }
}
