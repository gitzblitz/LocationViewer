package com.gitzblitz.locationviewer;

import android.app.Activity;
import android.app.Application;

import com.gitzblitz.locationviewer.di.AppComponent;
import com.gitzblitz.locationviewer.di.AppModule;
import com.gitzblitz.locationviewer.di.DaggerAppComponent;
import com.gitzblitz.locationviewer.di.DataModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class LocationApplication extends Application implements HasActivityInjector {

    private static LocationApplication app;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        initDi();

        appComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    public static LocationApplication getApp(){
        return app;
    }
    private void initDi() {

        appComponent = DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule(this))
                .dataModule(new DataModule(this))
                .build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
