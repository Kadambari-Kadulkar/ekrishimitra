package apptroid.e_krushimitra.com.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.activities.AppliedScheme;
import apptroid.e_krushimitra.com.models.ListSearchItems;

public class SearchedSchemeAdapter extends RecyclerView.Adapter<SearchedSchemeAdapter.ViewHolder> {

    private Context context;
    String schemesite;
    View v;
    int position;
    String disputeReason, transactionId;
    ProgressDialog progressDialog;
    //List of superHeroes
    List<ListSearchItems> listSearchItesms;

    public SearchedSchemeAdapter(List<ListSearchItems> superTran, Context context){
        super();
        //Getting all the superheroes
        this.listSearchItesms = superTran;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searched_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ListSearchItems superHero =  listSearchItesms.get(position);

        holder.tvSchemes.setText(String.valueOf(superHero.getAssDetails()));
        holder.tvDate.setText(String.valueOf(superHero.getProvision()));
        holder.tvSite.setText(String.valueOf(superHero.getCategory()));
        holder.tvState.setText(String.valueOf(superHero.getScheme()));
        holder.tvSchemeName.setText(String.valueOf(superHero.getSchemename()));
        holder.schemegoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListSearchItems listSearchItems = listSearchItesms.get(position);
                schemesite= listSearchItems.getCategory();
                Intent i = new Intent(context,AppliedScheme.class);
                Log.i("Selected Site",schemesite);
                i.putExtra("loadsite", schemesite);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listSearchItesms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

      public TextView tvSchemes,tvDate,tvSite,tvState,tvSchemeName;
        public Button schemegoto;


        public ViewHolder(View itemView) {
            super(itemView);

            tvSchemes = (TextView) itemView.findViewById(R.id.tvSchemeDesc);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvSite =(TextView) itemView.findViewById(R.id.tvSite);
            tvState =(TextView) itemView.findViewById(R.id.tvState);
            tvSchemeName=(TextView) itemView.findViewById(R.id.tvSchemeName);
            schemegoto = (Button)itemView.findViewById(R.id.schemegoto);

        }
    }
}
