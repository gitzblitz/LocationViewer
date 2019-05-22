package com.gitzblitz.locationviewer.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gitzblitz.locationviewer.db.Location;
import com.gitzblitz.locationviewer.db.LocationRepository;

import java.util.List;

public class LocationListViewModel extends AndroidViewModel {

    private LocationRepository locationRepository;
    private LiveData<List<Location>> mAllLocations;


    public LocationListViewModel(@NonNull Application application) {
        super(application);
        locationRepository = new LocationRepository(application);
        mAllLocations = locationRepository.getAllLocations();

    }

    public LiveData<List<Location>> getAllLocations(){
        return mAllLocations;
    }
    public void insertLocation(Location location){
        locationRepository.insertLocation(location);
    }
}
