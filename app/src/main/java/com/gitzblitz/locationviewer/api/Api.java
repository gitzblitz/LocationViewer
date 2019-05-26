package com.gitzblitz.locationviewer.api;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("/weather")
    void getLocationWeather(@Query("lat") String latitude,
                            @Query("lon") String longitude,
                            @Query("appid") String appid);
}
