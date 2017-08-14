package apptroid.e_krushimitra.com.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import apptroid.e_krushimitra.com.R;
import apptroid.e_krushimitra.com.models.Crop;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter. MyViewHolder> {
    private List<Crop> cropList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView state, city, date, less,unit,quantity;

        public MyViewHolder(View view) {
            super(view);
            state = (TextView) view.findViewById(R.id.state);
            city = (TextView) view.findViewById(R.id.city);

            date = (TextView) view.findViewById(R.id.date);
            less = (TextView) view.findViewById(R.id.less);
            unit = (TextView) view.findViewById(R.id.unit);
            quantity = (TextView) view.findViewById(R.id.quantity);
        }
    }

    public CropAdapter(List<Crop> cropList, Context context) {
        this.cropList = cropList;
        this.context = context;
    }

    @Override
    public CropAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mandi_second, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CropAdapter.MyViewHolder holder, int position) {
        Crop crop = cropList.get(position);
        holder.state.setText("State: "+crop.getState());
        holder.city.setText("City: "+crop.getCity());

        holder.date.setText("Date: "+crop.getDate());
        //holder.less.setText(crop.getLess());
        holder.unit.setText("/"+crop.getUnit());
        holder.quantity.setText("Rs "+crop.getQuantity());


    }

    @Override
    public int getItemCount() {
     return cropList.size();
    }
}
