package com.luvris2.publicperfomancedisplayapp.api;

import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KopisPerformanceApi {
    // 공연 검색
    @GET("/performancesearch")
    Call<KopisApiPerformance> getPlaceSearch(@Query("stdate") String stdate,          // 시작일
                                             @Query("eddate") String eddate,          // 종료일
                                             @Query("cpage") int cpage,               // 전체페이지
                                             @Query("rows") int rows,                 // 페이지 당 목록 수
                                             @Query("shprfnm") String shprfnm,        // 공연 명
                                             @Query("shprfnmfct") String shprfnmfct,  // 공연 시설 명
                                             @Query("shcate") String shcate,          // 장르 코드
                                             @Query("signgucode") String signgucode,  // 시도 코드
                                             @Query("prfstate") int prfstate);        // 공연 상태
}
