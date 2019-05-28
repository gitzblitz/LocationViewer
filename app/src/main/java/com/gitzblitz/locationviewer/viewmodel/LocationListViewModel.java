package com.gitzblitz.locationviewer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.gitzblitz.locationviewer.db.LocationRepository;
import com.gitzblitz.locationviewer.model.Location;

import javax.inject.Inject;

public class LocationListViewModel extends ViewModel {


    protected LocationRepository locationRepository;

    private LiveData<PagedList<Location>> mAllLocations;


    @Inject
    public LocationListViewModel(LocationRepository repository) {
        this.locationRepository = repository;
        mAllLocations = new LivePagedListBuilder<>(
                locationRepository.getAllLocations(), 20).build();

    }

    public LiveData<PagedList<Location>> getAllLocations() {
        return mAllLocations;
    }

}
