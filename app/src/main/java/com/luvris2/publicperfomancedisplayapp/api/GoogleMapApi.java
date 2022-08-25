package com.luvris2.publicperfomancedisplayapp.api;

import com.luvris2.publicperfomancedisplayapp.model.GoogleMapPlace;
import com.luvris2.publicperfomancedisplayapp.model.GoogleMapPlaceSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapApi {
    // 내 위치 검색
    @GET("/maps/api/geocode/json")
    Call<GoogleMapPlace> getMyLocation(@Query("latlng") String location,
                                       @Query("language") String language,
                                       @Query("key") String key);

    // 공연 시설 위치 검색
    @GET("/maps/api/place/textsearch/json")
    Call<GoogleMapPlaceSearch> nearByPerformanceLocation(@Query("query") String query,
                                                         @Query("language") String language,
                                                         @Query("key") String key);
}