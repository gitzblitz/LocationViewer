package com.gitzblitz.locationviewer.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gitzblitz.locationviewer.R;
import com.gitzblitz.locationviewer.db.Location;


public class LocationListAdapter extends PagedListAdapter<Location, LocationListAdapter.LocationViewHolder> {


    private Context context;

    protected LocationListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<Location> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Location>() {
                @Override
                public boolean areItemsTheSame(@NonNull Location oldLocation, @NonNull Location newLocation) {
                    return oldLocation.getUid() == newLocation.getUid();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Location oldLocation, @NonNull Location newLocation) {
                    return oldLocation.equals(newLocation);
                }
            };

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.LocationViewHolder holder, int position) {
        Location location = getItem(position);
        if (location != null) {
            holder.bindTo(location);
            holder.itemView.setOnClickListener(v ->
                    Log.d("Adapter", "postion = " + position));
        } else {
            holder.clear();
        }
    }


    class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView locationNameTextView;
        TextView locationDescriptionTextView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            locationNameTextView = itemView.findViewById(R.id.location_name);
            locationDescriptionTextView = itemView.findViewById(R.id.location_description);
        }


        public void bindTo(Location location) {
            locationNameTextView.setText(location.getName());
            locationDescriptionTextView.setText(location.getDescription());
        }


        public void clear() {
            locationNameTextView.setText("");
            locationDescriptionTextView.setText("");
        }
    }
}
