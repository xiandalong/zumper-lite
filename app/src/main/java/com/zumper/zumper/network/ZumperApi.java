package com.zumper.zumper.network;

import com.zumper.zumper.model.RestaurantResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZumperApi {
    @GET("place/nearbysearch/json")
    Single<RestaurantResponse> getRestaurantsByLocation(@Query("location") String location,
                                                              @Query("radius") String radius,
                                                              @Query("type") String type,
                                                              @Query("key") String key);
}
