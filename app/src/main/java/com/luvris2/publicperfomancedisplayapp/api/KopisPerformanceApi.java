package com.luvris2.publicperfomancedisplayapp.api;

import com.luvris2.publicperfomancedisplayapp.model.KopisApiDetail;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KopisPerformanceApi {
    // 공연 검색

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


//    @GET("/performancesearch")
//    Call<KopisApiPerformance> getPerformance(@Query("stdate") String stdate,
//                                             @Query("eddate") String eddate,
//                                             @Query("cpage") int cpage,
//                                             @Query("rows") int rows,
//                                             @Query("shprfnm") String shprfnm,
//                                             @Query("shprfnmfct") String shprfnmfct,
//                                             @Query("shcate") String shcate,
//                                             @Query("signgucode") String signgucode,
//                                             @Query("signgusubcod") String signgusubcode,
//                                             @Query("prfstate") int prfstate);

    // 공연 상세 검색
    @GET("/performancedetail/{prfId}")
    Call<KopisApiPerformance> getPerformanceDetail(@Path("prfId") String prfId);

    // 내 지역(구) 공연 검색
    @GET("/nearbyperformance/{sidoCodeSub}")
    Call<KopisApiPerformance> nearByPlaceSearch(@Path("sidoCodeSub") String sidoCodeSub,
                                                @Query("stdate") String stdate,
                                                @Query("eddate") String eddate,
                                                @Query("cpage") int cpage,
                                                @Query("rows") int rows,
                                                @Query("prfstate") int prfstate);
    // 공연 상세 검색
    @GET("/performancedetail/{prfId}")
    Call<KopisApiDetail> getDetailSearch(@Path("prfId")String prfId,
                                         @Query("service") String service);
}
