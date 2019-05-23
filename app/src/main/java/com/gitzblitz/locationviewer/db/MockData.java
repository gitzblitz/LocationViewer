package com.gitzblitz.locationviewer.db;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    private List<Location> mockLocations = new ArrayList<>();


    public List<Location> generateLocations() {

        for (int i = 0; i <= 2000; i++) {

            mockLocations.add(new Location("Cape Town",
                    "Lorem ipsum dhgeghdfh  fhdfgdfgh", true, "GPS", "", "", "", ""));
        }
        return mockLocations;
    }

}
