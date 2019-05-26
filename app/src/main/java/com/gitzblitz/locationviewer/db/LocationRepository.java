package com.gitzblitz.locationviewer.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.paging.DataSource;

import com.gitzblitz.locationviewer.model.Location;

import io.reactivex.Single;

public class LocationRepository {

    private LocationDao locationDao;
    private DataSource.Factory<Integer, Location> mAllLocations;

    public LocationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        locationDao = db.locationDao();
        mAllLocations = locationDao.getAllLocations();
    }

    public DataSource.Factory<Integer, Location> getAllLocations() {
        return mAllLocations;
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
