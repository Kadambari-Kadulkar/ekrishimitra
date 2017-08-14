package apptroid.e_krushimitra.com.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Locale;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.helper.SessionManagement;


public class ActivitySplash extends AppCompatActivity {



    private boolean mIsBackButtonPressed = false;
    private static final int SPLASH_DURATION = 4000; //3 seconds
    private Handler myhandler;
    SessionManagement sessionManagement;
    Locale myLocale;
    String lang;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManagement = new SessionManagement(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        myhandler = new Handler();

        // run a thread to start the home screen
        myhandler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {

                                      finish();

                                      if (!mIsBackButtonPressed) {

                                          if(!sessionManagement.isLoggedIn()){

                                              // start the home activity
                                              Intent i = new Intent(getApplicationContext(),ActivityNotLogin.class);
                                              overridePendingTransition(0,1);
                                              startActivity(i);
                                              finish();


                                          } else{

//                                              // start the home activity
//                                              Intent i = new Intent(getApplicationContext(),MainActivity.class);
//                                              overridePendingTransition(0,1);
//                                              startActivity(i);
                                              HashMap<String , String> user = sessionManagement.getUserDetails();

                                              lang = user.get(SessionManagement.KEY_LANG);
                                              setLocale(getApplicationContext(),lang);
                                          }


                                      }

                                  }

                              }

                , SPLASH_DURATION);
    }

    @Override
    public void onBackPressed() {
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }

    public void setLocale(Context con, String lang) {

        myLocale = new Locale(lang);
        Log.i("ssLang",lang);
        Resources res = con.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        sessionManagement.selectLangSession(lang);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

}
