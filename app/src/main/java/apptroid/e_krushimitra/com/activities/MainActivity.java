package apptroid.e_krushimitra.com.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.fragments.CropAdvisorFragment;
import apptroid.e_krushimitra.com.fragments.GovSchemesFragment;
import apptroid.e_krushimitra.com.fragments.MandiFragment;
import apptroid.e_krushimitra.com.fragments.SettingsFragment;
import apptroid.e_krushimitra.com.fragments.SoilDetectFragment;
import apptroid.e_krushimitra.com.helper.SessionManagement;
import apptroid.e_krushimitra.com.helper.Utils;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar_actionbar;
    LinearLayout btnFour,btnOne,btnTwo,btnThree,btnFive,btnSix;
    Drawer result;
    private FragmentManager fragmentManager;
    private Stack<Fragment> fragmentStack;
    //ImageView btnOne,btnTwo,btnThree,btnFour;
    FrameLayout replaced_frame;
    ChangeLanguageActivity cla;
    SessionManagement sm;
    String lang,l,fullname,email,id;
    Locale myLocale;
    List<String> ListSchemeName = new ArrayList<String>();
    List<String> ListCategory = new ArrayList<String>();

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar_actionbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar_actionbar);



        sm = new SessionManagement(MainActivity.this);
        cla = new ChangeLanguageActivity();
        if(sm.isLoggedIn()){

            HashMap<String , String> user = sm.getUserDetails();
            fullname = user.get(SessionManagement.KEY_NAME);
            email = user.get(SessionManagement.KEY_EMAIL);
            id = user.get(SessionManagement.KEY_ID);
            lang = user.get(SessionManagement.KEY_LANG);

//            myLocale = new Locale(lang);
//            Log.i("Lang..",lang);
//            Resources res = getResources();
//            DisplayMetrics dm = res.getDisplayMetrics();
//            Configuration conf = res.getConfiguration();
//            conf.locale = myLocale;
//            res.updateConfiguration(conf, dm);

            setLocale(MainActivity.this,lang);
            Utils.PrintErrorLog("session",""+fullname+email+id+lang);
        }else
        {
            fullname="Guest";
            email="guest@gmail.com";

        }

        replaced_frame =(FrameLayout) findViewById(R.id.container);
        btnOne = (LinearLayout) findViewById(R.id.btnCrop);
        btnTwo = (LinearLayout) findViewById(R.id.btnTwo);
        btnThree = (LinearLayout)findViewById(R.id.btnThree);
        btnFour = (LinearLayout)findViewById(R.id.btnFour);
        btnFive = (LinearLayout)findViewById(R.id.btnFive);
        btnSix = (LinearLayout)findViewById(R.id.btnSix);

        btnOne.setVisibility(View.VISIBLE);
        btnTwo.setVisibility(View.VISIBLE);
        btnThree.setVisibility(View.VISIBLE);
        btnFour.setVisibility(View.VISIBLE);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advisor();
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mandi();
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gov();
            }
        });









        new DrawerBuilder().withActivity(this).build();

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTextColor(getResources().getColor(R.color.colorPrimary))
                .withHeaderBackground(R.color.md_white_1000)
                .addProfiles(
                        new ProfileDrawerItem().withName(fullname).withEmail(email).withIcon(R.drawable.user)
                )

                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        //if you want to update the items at a later time it is recommended to keep it in a variable
        // PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Hi Guest!!");
        PrimaryDrawerItem item11 = new PrimaryDrawerItem().withName(R.string.home).withSelectedIconColor(Color.WHITE).withTintSelectedIcon(true);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.cropadvisor).withSelectedIconColor(Color.WHITE).withTintSelectedIcon(true);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName(R.string.govscheme).withSelectedIconColor(Color.WHITE).withTintSelectedIcon(true);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName(R.string.soildetection).withSelectedIconColor(Color.WHITE).withTintSelectedIcon(true);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName(R.string.mandi).withSelectedIconColor(Color.WHITE).withTintSelectedIcon(true);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withName(R.string.settings).withSelectedIconColor(Color.WHITE).withTintSelectedIcon(true);


        //create the drawer and remember the `Drawer` result object
         result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar_actionbar)
                 .withActionBarDrawerToggle(true)


                .addDrawerItems(
                        item11,
                        item1,
                        item2,
                        item3,
                        item4,
                        item5

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    public static final String TAG = "Main Activity";

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position) {
//
                            case 1:

                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                                finish();
                                break;
                            case 2:

                                advisor();
                                break;
                            case 3:
                                gov();
                                break;
                            case 4:
                                soil();
                                break;

                            case 5:
                                mandi();
                                break;
                            case 6:
                                settings();

                                break;
                        }
                        return false;
                    }
                })
                .build();
}



    private void mandi() {
        MandiFragment man = new MandiFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(0,0,0,0);
        trans.replace(R.id.container,man);
        replaced_frame.setVisibility(View.VISIBLE);
        trans.addToBackStack(null);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null,0);
        trans.commit();

    }

    public void advisor()
    {
        CropAdvisorFragment frag = new CropAdvisorFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(0, 0, 0, 0);
        trans.replace(R.id.container,frag);
        replaced_frame.setVisibility(View.VISIBLE);
        trans.addToBackStack(null);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        trans.commit();

    }
    public void settings()
    {
        SettingsFragment frag = new SettingsFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(0, 0, 0, 0);
        trans.replace(R.id.container,frag);
        replaced_frame.setVisibility(View.VISIBLE);
        trans.addToBackStack(null);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        trans.commit();


    }
    public void gov()
    {
        GovSchemesFragment frag = new GovSchemesFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(0, 0, 0, 0);
        trans.add(R.id.container,frag);
        replaced_frame.setVisibility(View.VISIBLE);
        trans.addToBackStack(null);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        trans.commit();

    }
    public void soil() {
        SoilDetectFragment frag = new SoilDetectFragment ();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(0, 0, 0, 0);
        trans.replace(R.id.container, frag);
        replaced_frame.setVisibility(View.VISIBLE);
        trans.addToBackStack(null);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        trans.commit();
    }

    public void setLocale(Context con, String lang) {

        myLocale = new Locale(lang);
        Log.i("Lang",lang);
        Resources res = con.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}



