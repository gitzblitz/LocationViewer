package com.gitzblitz.locationviewer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.gitzblitz.locationviewer.model.Location;
import com.gitzblitz.locationviewer.db.LocationRepository;

public class LocationListViewModel extends AndroidViewModel {

    private LocationRepository locationRepository;
    private LiveData<PagedList<Location>> mAllLocations;


    public LocationListViewModel(@NonNull Application application) {
        super(application);
        locationRepository = new LocationRepository(application);
        mAllLocations = new LivePagedListBuilder<>(
                locationRepository.getAllLocations(), 20).build();

    }

    public LiveData<PagedList<Location>> getAllLocations() {
        return mAllLocations;
    }

    public void insertLocation(Location location) {
        locationRepository.insertLocation(location);
    }
}
