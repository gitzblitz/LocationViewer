package com.gitzblitz.locationviewer.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.paging.DataSource;

import com.gitzblitz.locationviewer.api.Api;
import com.gitzblitz.locationviewer.model.Location;
import com.gitzblitz.locationviewer.model.WeatherData;
import com.gitzblitz.locationviewer.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Single;

public class LocationRepository {

    private LocationDao locationDao;
    private Api api;
    private DataSource.Factory<Integer, Location> mAllLocations;
    private static final String WEATHER_API_KEY = "aa7aa59bfd60ddf73816ad836743ab40";

    @Inject
    public LocationRepository(LocationDao locationDao, Api api) {
        this.api = api;
        this.locationDao = locationDao;
    }

    public DataSource.Factory<Integer, Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    public void insertLocation(Location location) {
        new insertAsyncTask(locationDao).execute(location);
    }

    public Single<Location> getLocationById(int locationID){
        return locationDao.getLocationById(locationID);
    }

    public void updateLocation(Location location) {
        new updateAsyncTask(locationDao).execute(location);
    }

    public Single<WeatherData> getWeatherInformation(String lat, String lon){
        return api.getLocationWeather(lat, lon, Constants.WEATHER_API_KEY);
    }


    private static class updateAsyncTask extends  AsyncTask<Location, Void, Void> {
        private LocationDao mAsynclocationDao;

        public updateAsyncTask(LocationDao mAsynclocationDao) {
            this.mAsynclocationDao = mAsynclocationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            mAsynclocationDao.updateLocation(locations[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao mAsynclocationDao;

        public insertAsyncTask(LocationDao locationDao) {
            mAsynclocationDao = locationDao;
        }

        @Override
        protected Void doInBackground(final Location... locations) {
            mAsynclocationDao.insert(locations[0]);
            return null;
        }
    }
}
