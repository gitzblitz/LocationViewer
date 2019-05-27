package com.gitzblitz.locationviewer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.gitzblitz.locationviewer.LocationApplication;
import com.gitzblitz.locationviewer.model.Location;
import com.gitzblitz.locationviewer.db.LocationRepository;

import javax.inject.Inject;

public class LocationListViewModel extends ViewModel {


    protected LocationRepository locationRepository;

    private LiveData<PagedList<Location>> mAllLocations;


    @Inject
    public LocationListViewModel(LocationRepository repository) {
        this.locationRepository = repository;
//        locationRepository = new LocationRepository(application);
//        LocationApplication.getApp().getAppComponent().inject(this);
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
