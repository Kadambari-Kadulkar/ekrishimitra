package apptroid.e_krushimitra.com.adapters;

import android.view.View;


public interface ClickListener {
    void onClick(View v, int position);


    void onLongClick(View child, int childPosition);
}
