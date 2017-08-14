package apptroid.e_krushimitra.com.adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apptroid.e_krushimitra.com.R;


import apptroid.e_krushimitra.com.activities.ApplicableScheme;
import apptroid.e_krushimitra.com.activities.ViewForm;
import apptroid.e_krushimitra.com.helper.Constants;
import apptroid.e_krushimitra.com.helper.CustomRequest;
import apptroid.e_krushimitra.com.helper.Utils;
import apptroid.e_krushimitra.com.models.FormDetails;
import apptroid.e_krushimitra.com.models.ListSearchItems;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter. MyViewHolder> {
    public static final String FORM_VIEW = "https://www.apptroid.in/api/demo/tcpdf/examples/pdf.php";
    public static final String FORM_DOWNLOAD = "https://www.apptroid.in/ekrushi/api/demo/tcpdf/examples/example_001.php";
    private List<FormDetails> formList;
    private Context context;
    private ProgressDialog progressDialog;
    String n,n1;
    int position;

    View.OnClickListener viewPdfClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MyViewHolder holder = (MyViewHolder) view.getTag();
            int pos = holder.getAdapterPosition();
            FormDetails listMovies = formList.get(pos);
            n = listMovies.getName();
            //n = "der";
            Log.i("View Details",n);
            viewPdfRequest();
        }
    };
    View.OnClickListener downloadPdfClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MyViewHolder hold= (MyViewHolder) view.getTag();
            position = hold.getAdapterPosition();
            FormDetails listMovies = formList.get(position);
            n1 = listMovies.getName();
            //n1 = "der";
            Log.i("Download Details",n1);
            downloadPdfRequest();
        }
    };


    public void viewPdfRequest()
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..!");
        progressDialog.setCancelable(false);
        progressDialog.show();


        StringRequest request = new StringRequest(Request.Method.GET,FORM_DOWNLOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.PrintErrorLog("Response", "" + response);
                try {
                    JSONObject jsonObject =  new JSONObject(response);
                    if(jsonObject.names().get(0).equals("response"))
                    {
                        Log.i("passed name",response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                        Intent i = new Intent(context,ViewForm.class);
                        i.putExtra("linktoform",FORM_DOWNLOAD+"?form="+n+"&&download=1");
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);


                        progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.PrintErrorLog("Volley Error : ", "" + error);
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("form", n);
              hashMap.put("download",Integer.toString(0));

                Log.w("hashmap",""+hashMap);
                return hashMap;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        requestQueue.add(request);

    }

    public void downloadPdfRequest() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..!");
        progressDialog.setCancelable(false);
        progressDialog.show();


        StringRequest request = new StringRequest(Request.Method.GET,FORM_DOWNLOAD, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                new DownloadFile().execute(FORM_DOWNLOAD+"?form="+n1+"&&download=1","PMFBY.pdf");
                Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.PrintErrorLog("Volley Error : ", "" + error);
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("form", n1);
                hashMap.put("download",Integer.toString(1));

                Log.w("hashmap",""+hashMap);
                return hashMap;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }



    public FormAdapter(List<FormDetails> formList, Context context) {
        this.formList = formList;
        this.context = context;
    }

    @Override
    public FormAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searched_form, parent, false);
        FormAdapter.MyViewHolder viewHolder = new FormAdapter.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FormAdapter.MyViewHolder holder, int position) {
        FormDetails fd = formList.get(position);

        holder.name.setText(fd.getName());
        holder.v.setOnClickListener(viewPdfClick);
        holder.v.setTag(holder);
        holder.download.setOnClickListener(downloadPdfClick);
        holder.download.setTag(holder);




    }
    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            //String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "PdfDemo");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            downloadFile(fileUrl, pdfFile);
            Log.i("Path", String.valueOf(folder));
            return null;
        }
    }
    public static void downloadFile(String fileURL, File directory) {
        try {

            FileOutputStream f = new FileOutputStream(directory);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            InputStream in = c.getInputStream();

            byte[] buffer = new byte[1024*1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return formList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView formname, name;
        Button v, download;


        public MyViewHolder(View view) {
            super(view);
            formname = (TextView) view.findViewById(R.id.formName);
            name = (TextView) view.findViewById(R.id.tvFarmerName);
            v =(Button)view.findViewById(R.id.buttView);
            download =(Button)view.findViewById(R.id.buttDown);


        }

    }

}
