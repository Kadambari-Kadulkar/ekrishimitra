package apptroid.e_krushimitra.com.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

import apptroid.e_krushimitra.com.activities.ActivityLogin;

public class SessionManagement {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared pref file name
    public static final String PREF_NAME = "ekrushimitra";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    // User name (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    // User name (make variable public to access from outside)
    public static final String KEY_ID = "id";

    public static final String KEY_LANG = "lang";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email,String id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);
        // Storing name in pref
        editor.putString(KEY_EMAIL, email);
        // Storing name in pref
        editor.putString(KEY_ID, id);



        // commit changes
        editor.commit();
    }
    public void selectLangSession(String lang)
    {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_LANG,lang);

        Log.i("selectedLangSession",lang);


        editor.commit();
    }

    /**
     * Check login method wil check edituser login status
     * If false it will redirect edituser to login page
     * Else won't do anything
     * */
//    public void checkLogin(){
//        // Check login status
//        if(!this.isLoggedIn()){
//            // edituser is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, ActivityLogin.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
//        }
//
//    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // edituser name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        user.put(KEY_LANG, pref.getString(KEY_LANG, null));


        // return edituser
        return user;
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
       editor.clear();
        editor.commit();

        // After logout redirect edituser to Loing Activity
        Intent i = new Intent(_context, ActivityLogin.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}