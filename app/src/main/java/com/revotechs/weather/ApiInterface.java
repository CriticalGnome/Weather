package com.revotechs.weather;

import com.revotechs.weather.entity.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Project Wearher
 * Created on 27.08.2017
 *
 * @author CriticalGnome
 */

interface ApiInterface {
    @GET("weather")
    Call<Forecast> loadCurrentWeather(@Query("id") String id, @Query("units") String units, @Query("appid") String appId);
}
