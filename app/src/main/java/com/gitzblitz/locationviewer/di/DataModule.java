package com.gitzblitz.locationviewer.di;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gitzblitz.locationviewer.api.Api;
import com.gitzblitz.locationviewer.db.AppDatabase;
import com.gitzblitz.locationviewer.db.LocationDao;
import com.gitzblitz.locationviewer.db.LocationRepository;
import com.gitzblitz.locationviewer.db.MockData;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class DataModule {
//    private static final String WEATHER_API_KEY = "aa7aa59bfd60ddf73816ad836743ab40";

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private AppDatabase appDatabase;

    public DataModule(Application application) {
        this.appDatabase = Room.databaseBuilder(application, AppDatabase.class, "location_database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        new PopulateDbAsync(appDatabase).execute();

                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final LocationDao locationDao;

        public PopulateDbAsync(AppDatabase db) {
            locationDao = db.locationDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            locationDao.deleteAll();

            MockData mockData = new MockData();

            locationDao.insert(mockData.generateLocations());


            return null;
        }
    }

    @Provides
    @Singleton
    public AppDatabase providesAppDataBase() {
        return appDatabase;

    }

    @Provides
    @Singleton
    public LocationDao providesLocationDao(AppDatabase appDatabase) {
        return appDatabase.locationDao();
    }


    @Provides
    @Singleton
    public OkHttpClient provideHttpClient() {
        return new OkHttpClient().newBuilder().build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    public Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    @Provides
    @Singleton
    public LocationRepository provideRepository(LocationDao locationDao, Api api) {
        return new LocationRepository(locationDao, api);
    }


}
