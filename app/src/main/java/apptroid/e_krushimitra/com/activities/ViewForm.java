package apptroid.e_krushimitra.com.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import apptroid.e_krushimitra.com.R;

public class ViewForm extends AppCompatActivity {
    String site;
    WebView wv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wv1 = (WebView)findViewById(R.id.formWebView);
        wv1.setWebViewClient(new ViewForm.MyFormBrowser());
        final Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
           site = bundle.getString("linktoform");
            Log.w("bundlevalue",""+bundle.toString());
        }
        wv1.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + site);
    }

    private class MyFormBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
