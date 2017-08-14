package apptroid.e_krushimitra.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import apptroid.e_krushimitra.com.R;

public class ActivityPreLogin extends AppCompatActivity {

    LinearLayout LinLaySignupPre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        LinLaySignupPre = (LinearLayout) findViewById(R.id.LinLaySignupPre);

        LinLaySignupPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ActivityLogin.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
            }
        });
    }
}
