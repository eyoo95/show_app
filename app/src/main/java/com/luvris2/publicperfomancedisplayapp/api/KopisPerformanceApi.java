package com.luvris2.publicperfomancedisplayapp.api;

import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KopisPerformanceApi {
    // 공연 검색
    @GET("/performancesearch")
    Call<KopisApiPerformance> getPlaceSearch(@Query("stdate") String stdate,
                                             @Query("eddate") String eddate,
                                             @Query("cpage") int cpage,
                                             @Query("rows") int rows,
                                             @Query("shprfnm") String shprfnm,
                                             @Query("shprfnmfct") String shprfnmfct,
                                             @Query("shcate") String shcate,
                                             @Query("signgucode") String signgucode,
                                             @Query("prfstate") int prfstate);
}
