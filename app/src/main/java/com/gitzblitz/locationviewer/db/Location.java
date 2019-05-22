package com.gitzblitz.locationviewer.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location")
public class Location {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "location_state")
    private Boolean locationState;

    @ColumnInfo(name = "location_type_name")
    private String locationTypeName;

    @ColumnInfo(name = "minor")
    private String minor;

    @ColumnInfo(name = "major")
    private String major;

    @ColumnInfo(name = "coordinates")
    private String coordinates;


    public Location(String name, String description, Boolean locationState, String locationTypeName, String minor, String major, String coordinates) {
        this.name = name;
        this.description = description;
        this.locationState = locationState;
        this.locationTypeName = locationTypeName;
        this.minor = minor;
        this.major = major;
        this.coordinates = coordinates;
    }

    public Boolean getLocationState() {
        return locationState;
    }

    public void setLocationState(Boolean locationState) {
        this.locationState = locationState;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationTypeName() {
        return locationTypeName;
    }

    public void setLocationTypeName(String locationTypeName) {
        this.locationTypeName = locationTypeName;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
