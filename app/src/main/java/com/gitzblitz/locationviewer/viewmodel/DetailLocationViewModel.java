package com.gitzblitz.locationviewer.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gitzblitz.locationviewer.LocationApplication;
import com.gitzblitz.locationviewer.db.LocationRepository;
import com.gitzblitz.locationviewer.model.Location;
import com.gitzblitz.locationviewer.utils.Utilities;

import javax.inject.Inject;

import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DetailLocationViewModel extends ViewModel {

    private static final String TAG = DetailLocationViewModel.class.getName();

    public LocationRepository locationRepository;

    private MutableLiveData<String> detailLocationName  = new MutableLiveData<String>();
    private MutableLiveData<String> detailLocationDescription = new MutableLiveData<String>();
    private MutableLiveData<String> detailLocationLon = new MutableLiveData<String>();
    private MutableLiveData<String> detailLocationLat = new MutableLiveData<String>();
    private MutableLiveData<String> detailTemperature = new MutableLiveData<String>();
    private MutableLiveData<String> detailTimeFetched = new MutableLiveData<String>();

//
//    private LiveData<String> locationName;
//    private LiveData<String> locationDescription;
//    private LiveData<String> locationLon;
//    private LiveData<String> locationLat;
//    private LiveData<String> locationTemperature;
//    private LiveData<String> locationTimeFetched;



    private int mLocationID;

    public DetailLocationViewModel() {
    }

    @Inject
    public DetailLocationViewModel(LocationRepository repository) {
        this.locationRepository = repository;

    }


    @SuppressLint("CheckResult")
    public void start(int locationID){
        mLocationID = locationID;

        Log.d(TAG,"location id = " + locationID);

        if (locationID == 0) {

            throw new RuntimeException("locationID is null");
        }

        locationRepository
                .getLocationById(locationID)
                .flatMap(location -> locationRepository.getWeatherInformation(location.getLatitude(), location.getLongitude())
                        .map(w -> locationRepository.updateLocationWithWeather(w,location)))
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v-> {
                    detailLocationName.postValue(v.getName());
                    detailLocationDescription.postValue(v.getDescription());
                    detailTemperature.postValue(v.getTemperature());
                    detailLocationLat.postValue(v.getLatitude());
                    detailLocationLon.postValue(v.getLongitude());
                    detailTimeFetched.postValue(Utilities.dateToString(v.getWeatherDate()));
                }, e-> e.printStackTrace());

    }

    public MutableLiveData<String> getDetailLocationName() {
        return detailLocationName;
    }

    public MutableLiveData<String> getDetailLocationDescription() {
        return detailLocationDescription;
    }

    public MutableLiveData<String> getDetailLocationLon() {
        return detailLocationLon;
    }

    public MutableLiveData<String> getDetailLocationLat() {
        return detailLocationLat;
    }

    public MutableLiveData<String> getDetailTemperature() {
        return detailTemperature;
    }

    public MutableLiveData<String> getDetailTimeFetched() {
        return detailTimeFetched;
    }

}
