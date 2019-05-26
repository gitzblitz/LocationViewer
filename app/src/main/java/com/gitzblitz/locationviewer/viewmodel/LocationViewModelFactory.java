package com.gitzblitz.locationviewer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LocationViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application mApplication;
    private String mParam;

    public LocationViewModelFactory(Application mApplication, String mParam) {
        this.mApplication = mApplication;
        this.mParam = mParam;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return modelClass.cast(new LocationViewModelFactory(mApplication,mParam));
    }
}
