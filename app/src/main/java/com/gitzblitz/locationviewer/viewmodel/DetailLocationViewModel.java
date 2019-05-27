package com.gitzblitz.locationviewer.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitzblitz.locationviewer.db.LocationRepository;
import com.gitzblitz.locationviewer.utils.Utilities;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailLocationViewModel extends AndroidViewModel {

    private static final String TAG = DetailLocationViewModel.class.getName();

    private LocationRepository locationRepository;

    private MutableLiveData<String> detailLocationName  = new MutableLiveData<String>();
    private MutableLiveData<String> detailLocationDescription = new MutableLiveData<String>();
    private MutableLiveData<String> detailLocationLon = new MutableLiveData<String>();
    private MutableLiveData<String> detailLocationLat = new MutableLiveData<String>();
    private MutableLiveData<String> detailTemperature = new MutableLiveData<String>();
    private MutableLiveData<String> detailTimeFetched = new MutableLiveData<String>();


    private LiveData<String> locationName;
    private LiveData<String> locationDescription;
    private LiveData<String> locationLon;
    private LiveData<String> locationLat;
    private LiveData<String> locationTemperature;
    private LiveData<String> locationTimeFetched;



    private int mLocationID;


    public DetailLocationViewModel(@NonNull Application application) {
        super(application);
        locationRepository = new LocationRepository(application);
    }


    @SuppressLint("CheckResult")
    public void start(int locationID){
        mLocationID = locationID;

        if (locationID == 0) {

            throw new RuntimeException("locationID is null");
        }

        locationRepository.getLocationById(locationID)
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
