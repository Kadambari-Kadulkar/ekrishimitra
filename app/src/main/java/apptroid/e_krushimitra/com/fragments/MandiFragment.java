package apptroid.e_krushimitra.com.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.activities.MainActivity;
import apptroid.e_krushimitra.com.activities.MandiSecondActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MandiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MandiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MandiFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Button buttonSearch;
   Spinner city,state,crop;
    String s, s1, s2, item;
    Bundle b = new Bundle();
    Bundle b1 = new Bundle();
    Bundle b2 = new Bundle();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MandiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MandiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MandiFragment newInstance(String param1, String param2) {
        MandiFragment fragment = new MandiFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.mandi_first, container, false);
       city =(Spinner) v.findViewById(R.id.city_spinner);

        List<String> city1 = new ArrayList<String>();
        city1.add("SELECT CITY");
        city1.add("Pune");
        city1.add("Mumbai");
        city1.add("Nashik");

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,city1);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityAdapter);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();
                s= item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        state =(Spinner) v.findViewById(R.id.location_spinner);
        List<String> stat = new ArrayList<String>();
        stat.add("SELECT STATE");
        stat.add("Maharashtra");
        stat.add("Punjab");
        stat.add("Kerala");

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,stat);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();
                s1= item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        crop =(Spinner) v.findViewById(R.id.crop_spinner);
        List<String> crop1 = new ArrayList<String>();
        crop1.add("SELECT CROP");
        crop1.add("Wheat");
        crop1.add("Corn");
        crop1.add("Rice");
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,crop1);
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crop.setAdapter(cropAdapter);



        crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();
                s2= item;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        buttonSearch= (Button) v.findViewById(R.id.search_button);




        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!valid())
//                {
//
//                }
//                else
//                    {
                    Intent i1 = new Intent(getActivity(), MandiSecondActivity.class);
                    b.putString("city",s);
                    b1.putString("state",s1);
                    b2.putString("crop",s2);
                    i1.putExtras(b);
                    i1.putExtras(b1);
                    i1.putExtras(b2);
                    startActivity(i1);
//              /  }
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public boolean valid()
    {
        if(state.getSelectedItem().toString().trim().equals("SELECT STATE"))
        {

            Toast.makeText(getActivity(), "Please Select State", Toast.LENGTH_SHORT).show();
        }

        if(city.getSelectedItem().toString().trim().equals("SELECT CITY"))
        {
            Toast.makeText(getActivity(), "Please Select City", Toast.LENGTH_SHORT).show();
        }

        if(crop.getSelectedItem().toString().trim().equals("SELECT CROP"))
        {
            Toast.makeText(getActivity(), "Please Select Crop", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId())
//        {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }
//
//    public void onBackPressed()
//    {
//        Intent i = new Intent(getActivity(), MainActivity.class);
//        startActivity(i);
//        getActivity().overridePendingTransition(0,0);
//    }
}
