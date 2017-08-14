package apptroid.e_krushimitra.com.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.helper.SessionManagement;


public class ChangeLanguageActivity extends AppCompatActivity{
    Locale myLocale;
    Toolbar toolbar;
    Spinner lang_spinner;
    String lang,l,langSessionVar;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_lang_layout);
        toolbar= (Toolbar) findViewById(R.id.toolbar_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManagement = new SessionManagement(this);
        lang_spinner = (Spinner) findViewById(R.id.langspinner);
        lang_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {

                    Toast.makeText(adapterView.getContext(),
                            "You have selected Hindi", Toast.LENGTH_SHORT)
                            .show();
                    setLocale(getApplicationContext(),"hi");


                } else if (i == 2) {

                    Toast.makeText(adapterView.getContext(),
                            "You have selected Marathi", Toast.LENGTH_SHORT)
                            .show();
                    setLocale(getApplicationContext(),"mr");
                    loadLocale();


                } else if (i == 3) {

                    Toast.makeText(adapterView.getContext(),
                            "You have selected English", Toast.LENGTH_SHORT)
                            .show();
                    setLocale(getApplicationContext(),"en");
                    loadLocale();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        //loadLocale();

        /**if(sessionManagement.isLoggedIn()){

            HashMap<String,String> users = sessionManagement.getUserDetails();
            lang= users.get(SessionManagement.KEY_LANG);
            loadLocale();
            Log.i("Lanaguage",lang);
        }**/
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setLocale(Context con, String lang) {

                myLocale = new Locale(lang);
                Log.i("Lang",lang);
                Resources res = con.getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                sessionManagement.selectLangSession(lang);
                Intent refresh = new Intent(this, MainActivity.class);
                startActivity(refresh);
            }
    public void loadLocale() {

        SharedPreferences prefs = getSharedPreferences(SessionManagement.PREF_NAME,
                Activity.MODE_PRIVATE);
        String language = prefs.getString(SessionManagement.KEY_LANG, "");
        Log.i("KEY_LANG",language);
        setLocale(this,language);
        }


        /**if(sm.isLoggedIn()){

         HashMap<String,String> users = sm.getUserDetails();
         l = users.get(SessionManagement.KEY_LANG);
         Log.i("Email",l);

         }**/

    }



