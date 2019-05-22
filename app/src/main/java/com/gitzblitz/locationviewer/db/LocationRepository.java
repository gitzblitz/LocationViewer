package com.gitzblitz.locationviewer.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LocationRepository {

    private LocationDao locationDao;
    private LiveData<List<Location>> mAllLocations;

    public LocationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        locationDao = db.locationDao();
        mAllLocations = locationDao.getAllLocations();
    }

  public  LiveData<List<Location>> getAllLocations(){
        return  mAllLocations;
    }

    public void insertLocation(Location location){
     new insertAsyncTask(locationDao).execute(location);
    }


    private static class insertAsyncTask extends AsyncTask<Location, Void, Void>{
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
