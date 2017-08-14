package apptroid.e_krushimitra.com.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import apptroid.e_krushimitra.com.R;

public class AppliedSolScheme extends AppCompatActivity {
    Toolbar toolbar;
    WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_sol_scheme);
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wv1 = (WebView)findViewById(R.id.webView1);
        wv1.setWebViewClient(new MyBrowse());
        final Bundle b = getIntent().getExtras();
        String b1 = b.getString("loadsite");
        Log.i("BundleValues",b1);
        wv1.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(b1);
    }

    private class MyBrowse extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String s = view.getUrl();
            view.loadUrl(s);
            Log.i("Site",s);
            return true;
        }
    }

}
