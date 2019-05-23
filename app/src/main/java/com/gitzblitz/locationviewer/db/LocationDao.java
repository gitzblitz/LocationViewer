package com.gitzblitz.locationviewer.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location location);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Location> locations);

    @Query("DELETE FROM location")
    void deleteAll();

    @Query("SELECT * FROM location ORDER BY name ASC")
    DataSource.Factory<Integer, Location> getAllLocations();
}
