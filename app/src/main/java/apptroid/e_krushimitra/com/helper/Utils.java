package apptroid.e_krushimitra.com.helper;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import apptroid.e_krushimitra.com.R;


public class Utils {
    public static final Boolean DEBUG_STATUS = true;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        } else {
            Utils.ShowShortToast(context, context.getResources().getString(R.string.nointernet));
        }
        return TYPE_NOT_CONNECTED;
    }

    public static void ShowShortToast(Context view, String message) {
        Toast.makeText(view, message, Toast.LENGTH_SHORT).show();
    }

    public static void ShowShortHelpToast(Context view, String message) {
        Toast.makeText(view, "Please fill " + message, Toast.LENGTH_SHORT).show();
    }

    public static void ShowLongToast(Context view, String message) {
        Toast.makeText(view, message, Toast.LENGTH_LONG).show();
    }

    public static void PrintErrorLog(String TAG, String message) {
        if (DEBUG_STATUS) {
            Log.w(TAG, message);
        }
    }
}
