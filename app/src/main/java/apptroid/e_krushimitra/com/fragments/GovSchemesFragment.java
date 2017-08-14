package apptroid.e_krushimitra.com.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.activities.ProbSolSchemeList;
import apptroid.e_krushimitra.com.activities.SearchedScheme;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GovSchemesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GovSchemesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GovSchemesFragment extends Fragment {
    public static final String FILTERED_SCHEME_LIST = "https://www.apptroid.in/ekrushi/api/filter_scheme.php";
    public static final String CATWISE_SCHEME = "https://www.apptroid.in/ekrushi/api/scheme_cat.php";
    Button help,MayIhelp,done;
    LinearLayout InflatedLayout;
    View v,view;
    Bundle b,b1;
    int pos;
    String n,item,schemename,item1;
    Spinner spinner2,spinner,QueSpinner;
    private JSONArray res;
    private ArrayList<String> category;
    private ArrayList<String> scheme;
    private JSONArray res1;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GovSchemesFragment() {
        // Required empty public constructor
    }

        public static GovSchemesFragment newInstance(String param1, String param2) {
        GovSchemesFragment fragment = new GovSchemesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View getView() {


        return super.getView();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gov_schemes, container, false);
        help = (Button)v.findViewById(R.id.proceed);
        MayIhelp = (Button)v.findViewById(R.id.help);

        spinner = (Spinner) v.findViewById(R.id.SpinnerState);
        spinner2 = (Spinner)v.findViewById(R.id.SpinnerCat);
       // QueSpinner = (Spinner)v.findViewById(R.id.que_spinner);
        scheme = new ArrayList<String>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item1 = adapterView.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] myCatArray = getResources().getStringArray(R.array.cat);
        List<String> s2 = Arrays.asList(myCatArray);

        ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,s2);
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(cAdapter);


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getSelectedItem().toString();
                schemename = item;
                pos = i;
                scheme.clear();
                filteredSchemeList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        MayIhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent i3 = new Intent(getActivity(),ProbSolSchemeList.class);
                startActivity(i3);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchedScheme.class);

                i.putExtra("cat_id", pos);
                i.putExtra("s", item1);

                Log.i("catid", String.valueOf(pos));
                Log.i("s", item1);
                startActivity(i);
            }});
        return v;
    }

    public void filteredSchemeList(){

        scheme.add("SELECT SCHEME");
        StringRequest sr = new StringRequest(Request.Method.POST, FILTERED_SCHEME_LIST , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    res1 = j.getJSONArray("result");

                    Log.i("Response",response);
                    getStudents(res1);
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
                params.put("cat_id", String.valueOf(pos));
                Log.i("Paramaeters", String.valueOf(params));
                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(sr);
    }

    private void getStudents(JSONArray res) {
        for(int i=0;i<res.length();i++){
            try {
                JSONObject json = res.getJSONObject(i);

                scheme.add(json.getString("schemename"));
                String ii = json.getString("id");
                Log.i("ids",ii);
                Log.i("JSONArray Result", String.valueOf(res));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,scheme);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**public void categoryWiseScheme(){

        category.add("SELECT SCHEME CATEGORY");
        StringRequest sr = new StringRequest(Request.Method.POST,CATWISE_SCHEME  , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                   JSONObject j = new JSONObject(response);
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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(sr);
    }

    private void getStudents(JSONArray res) {
        for(int i=0;i<res.length();i++){
            try {
                JSONObject json = res.getJSONObject(i);

                category.add(json.getString("scheme_name"));
                Log.i("JSONArray Result", String.valueOf(res));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,category);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();

    }**/

}
