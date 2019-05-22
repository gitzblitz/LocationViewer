package com.gitzblitz.locationviewer.ui;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gitzblitz.locationviewer.R;
import com.gitzblitz.locationviewer.db.Location;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationViewHolder> {

    private final LayoutInflater mInflater;
    private List<Location> mLocations; // Cached copy of locations
    private Context mContext;

    public LocationListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.location_list_item, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.LocationViewHolder holder, int position) {
        if (mLocations != null) {
            Location current = mLocations.get(position);
            holder.locationNameTextView.setText(current.getName());
            holder.locationDescriptionTextView.setText(current.getDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Adapter", "postion = "+ position);
                }
            });
        } else {
            holder.locationNameTextView.setText("No location");
        }
    }

    public void setLocations(List<Location> locations){
        mLocations = locations;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if (mLocations != null) {
            return mLocations.size();
        } else
        return 0;
    }

  public class LocationViewHolder extends RecyclerView.ViewHolder{

        private final TextView locationNameTextView;
        private final TextView locationDescriptionTextView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            locationNameTextView = itemView.findViewById(R.id.location_name);
            locationDescriptionTextView = itemView.findViewById(R.id.location_description);
        }
    }
}
