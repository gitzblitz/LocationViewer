package com.gitzblitz.locationviewer.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gitzblitz.locationviewer.utils.Converters;


@Database(entities = {Location.class}, version = 2,exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LocationDao locationDao();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "location_database")
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){


        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void > {

        private final LocationDao locationDao;
        public PopulateDbAsync(AppDatabase db) {
            locationDao = db.locationDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            locationDao.deleteAll();

            MockData mockData = new MockData();
//            Location location = new Location("Cape Town", "Some description", true, "GPS",
//                    null, null, null, null);

            locationDao.insert(mockData.generateLocations());

//            Location location2 = new Location("Johannesburg", "Some description", true, "Bluetooth",
//                    null, null, null, null);
//
//            locationDao.insert(location2);




            return null;
        }
    }
}
