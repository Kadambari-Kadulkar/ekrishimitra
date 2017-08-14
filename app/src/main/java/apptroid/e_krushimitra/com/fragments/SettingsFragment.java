package apptroid.e_krushimitra.com.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.activities.ActivityLogin;
import apptroid.e_krushimitra.com.activities.ChangeLanguageActivity;
import apptroid.e_krushimitra.com.activities.ChangePasswordActivity;
import apptroid.e_krushimitra.com.helper.SessionManagement;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    private static final String CHANGE_PASS_URL ="https://www.apptroid.in/ekrushi/api/change_password.php";
    Button logout;
    SessionManagement sessionManagement;
    MenuInflater inf;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_menu_drawer,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.ChangePass:
                Intent i = new Intent(getActivity(),ChangePasswordActivity.class);
                startActivity(i);
                return true;
            case R.id.ChangeLang:
                Intent i1 = new Intent(getActivity(),ChangeLanguageActivity.class);
                startActivity(i1);
                return true;
            case R.id.LogOut:
                settingsLogOut();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);
        sessionManagement = new SessionManagement(getActivity());

        logout =(Button) v.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsLogOut();

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



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void settingsLogOut()
    {
        if(sessionManagement.isLoggedIn()){

            sessionManagement.logoutUser();
            Intent myLogin = new Intent(getActivity(), ActivityLogin.class);
            // Closing all the Activities
            myLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            myLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myLogin);
            getActivity().finish();
        }


    }


}
