package com.gitzblitz.locationviewer.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gitzblitz.locationviewer.LocationApplication;
import com.gitzblitz.locationviewer.SingleLiveEvent;
import com.gitzblitz.locationviewer.db.LocationRepository;
import com.gitzblitz.locationviewer.model.Location;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddEditLocationViewModel extends ViewModel {
    private static final String TAG = AddEditLocationViewModel.class.getName();


    protected LocationRepository locationRepository;


    public MutableLiveData<String> locationNameEditText = new MutableLiveData<String>();


    public MutableLiveData<String> locationDescEditText = new MutableLiveData<String>();


    public MutableLiveData<String> longitudeEditText = new MutableLiveData<String>();


    public MutableLiveData<String> latitudeEditText = new MutableLiveData<String>();

    public MutableLiveData<Integer> selectedItemPosition = new MutableLiveData<Integer>();


    public MutableLiveData<Boolean> checkedSwitch = new MutableLiveData<Boolean>();

    private final SingleLiveEvent<Void> mLocationUpdated = new SingleLiveEvent<>();

    private final SingleLiveEvent<Void> mLocationCancelled = new SingleLiveEvent<>();

    private int mLocationID;


    private boolean isNewLocation;


    private boolean isDataLoaded = false;

    private String[] entries = {"GPS", "Bluetooth", "RFID"};

    public AddEditLocationViewModel() {
    }

    @Inject
    public AddEditLocationViewModel(LocationRepository repository) {
        this.locationRepository = repository;

    }

    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {


        Log.d(TAG, "item selected = " + parent.getSelectedItem());


    }

    private int getSpinnerPosition(String entry, String[] entries) {

        for (int i = 0; i < entries.length; i++) {
            if (entries[i].equals(entry)) {

                return i;
            }
        }
        return -1;
    }

    @SuppressLint("CheckResult")
    public void start(int locationID) {

        mLocationID = locationID;

        if (locationID == 0) {
            isNewLocation = true;
            return;
        }

        isNewLocation = false;

        locationRepository.getLocationById(locationID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> {


//                    selectedItemPosition.postValue();
                    locationNameEditText.postValue(v.getName());
                    locationDescEditText.postValue(v.getDescription());
                    longitudeEditText.postValue(v.getLongitude());
                    latitudeEditText.postValue(v.getLatitude());
                    checkedSwitch.postValue(v.getLocationState());

                    String item = v.getLocationTypeName();

                    //get index of type

                    for (int i = 0; i < entries.length; i++) {
                        if (entries[i].equals(item)) {
                            selectedItemPosition.postValue(i);
                            break;
                        }
                    }


                }, e -> e.printStackTrace());

    }

    public boolean isNewLocation() {
        return isNewLocation;
    }

    @BindingAdapter(value = {"android:checked", "nullValue"}, requireAll = false)
    public static void setChecked(CompoundButton checkableView, Boolean isChecked, boolean nullValue) {
        checkableView.setChecked(isChecked != null ? isChecked : nullValue);
    }


    public void cancelUpdate() {
        mLocationCancelled.call();
    }

    public void saveLocation() {

        Location location = new Location(locationNameEditText.getValue(), locationDescEditText.getValue(), checkedSwitch.getValue(), entries[selectedItemPosition.getValue()], null, null, longitudeEditText.getValue(), latitudeEditText.getValue(), null, null);

        if (location.isEmpty()) {
            return;
        }
        if (isNewLocation() || mLocationID == 0) {
            createLocation(location);

        } else {
            location = new Location(mLocationID, locationNameEditText.getValue(), locationDescEditText.getValue(), checkedSwitch.getValue(), entries[selectedItemPosition.getValue()], longitudeEditText.getValue(), latitudeEditText.getValue());
            updateLocation(location);

        }


    }

    public SingleLiveEvent<Void> getmLocationUpdated() {
        return mLocationUpdated;
    }


    public SingleLiveEvent<Void> getmLocationCancelled() {
        return mLocationCancelled;
    }

    private void createLocation(Location newLocation) {
        locationRepository.insertLocation(newLocation);
        mLocationUpdated.call();
    }

    private void updateLocation(Location location) {

        if (isNewLocation()) {
            throw new RuntimeException("updateLocation was called but location is new");

        }

        locationRepository.updateLocation(location);
        mLocationUpdated.call();

    }


    public MutableLiveData<String> getLocationNameEditText() {
        return locationNameEditText;
    }


    public MutableLiveData<String> getLocationDescEditText() {
        return locationDescEditText;
    }


    public MutableLiveData<String> getLongitudeEditText() {
        return longitudeEditText;
    }


    public MutableLiveData<String> getLatitudeEditText() {
        return latitudeEditText;
    }

}
