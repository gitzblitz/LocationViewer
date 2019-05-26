package com.gitzblitz.locationviewer.db;

import com.gitzblitz.locationviewer.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MockData {

    private List<Location> mockLocations = new ArrayList<>();


    public List<Location> generateLocations() {

        String[] cities =   {"Cape Town", "Johannesburg", "Durban", "Port Elizabeth", "Nairobi", "Nakuru", "Copenhagen", "Seattle", "Los Angeles",
        "Boston", "Amsterdam"};

        Random rand = new Random();


        int len = cities.length;
        for (int i = 0; i <= 2000; i++) {
            int randomInt = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                randomInt = ThreadLocalRandom.current().nextInt(0,len);
            } else {
                randomInt = rand.nextInt(len );
            }
            String randCity = cities[randomInt];
            Location location = new Location(randCity,
                    "Lorem ipsum dhgeghdfh  fhdfgdfgh", false,
                    "GPS", "", "", Double.toString(rand.nextDouble()) , Double.toString(rand.nextDouble()), null, "");

            mockLocations.add(location);
        }
        return mockLocations;
    }

}
