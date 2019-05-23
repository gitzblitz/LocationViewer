package com.gitzblitz.locationviewer.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.gitzblitz.locationviewer.db.Location;
import com.gitzblitz.locationviewer.db.LocationRepository;

import java.util.List;

public class LocationListViewModel extends AndroidViewModel {

    private LocationRepository locationRepository;
    private LiveData<PagedList<Location>> mAllLocations;


    public LocationListViewModel(@NonNull Application application) {
        super(application);
        locationRepository = new LocationRepository(application);
        mAllLocations = new LivePagedListBuilder<>(
                locationRepository.getAllLocations(), 20).build();

    }

    public LiveData<PagedList<Location>> getAllLocations(){
        return mAllLocations;
    }

    public void insertLocation(Location location){
        locationRepository.insertLocation(location);
    }
}
