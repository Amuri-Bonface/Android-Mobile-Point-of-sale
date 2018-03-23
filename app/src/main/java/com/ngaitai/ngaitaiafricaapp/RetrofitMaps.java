package com.ngaitai.ngaitaiafricaapp;

import com.ngaitai.ngaitaiafricaapp.POJO.Example;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;


public interface RetrofitMaps {

    /*
     * Retrofit get annotation with our URL

     */
    @GET("api/directions/json?key=AIzaSyAl2IdRmGn-kz7xkl9kOOcuqAib4ERrLFk")
    Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);

}
