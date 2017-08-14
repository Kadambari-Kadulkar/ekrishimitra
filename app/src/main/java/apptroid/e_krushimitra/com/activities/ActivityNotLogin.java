package apptroid.e_krushimitra.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import apptroid.e_krushimitra.com.R;

public class ActivityNotLogin extends AppCompatActivity {

    LinearLayout btnLogIn,btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_both);

        btnLogIn = (LinearLayout)findViewById(R.id.btnLogin);
        btnSignUp = (LinearLayout)findViewById(R.id.btnSignUp);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ActivityLogin.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ActivityRegister.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
            }
        });
    }
}
