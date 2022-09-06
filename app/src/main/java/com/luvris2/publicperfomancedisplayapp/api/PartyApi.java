package com.luvris2.publicperfomancedisplayapp.api;

import com.luvris2.publicperfomancedisplayapp.model.PartyData;
import com.luvris2.publicperfomancedisplayapp.model.PartyRoom;
import com.luvris2.publicperfomancedisplayapp.model.PartyRoomRes;
import com.luvris2.publicperfomancedisplayapp.model.PostingList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PartyApi {

    // 파티생성
    @POST("/party")
    Call<PartyRoom> addPartyRoom(@Header("Authorization") String token,
                                 @Body PartyRoom partyRoom);

    // 파티찾기(조회)
    @GET("/party")
    Call<PartyRoomRes> getPartyRoom(@Query("limit") int limit,
                                    @Query("offset") int offset,
                                    @Query("keyword") String keyword,
                                    @Query("searchType") String searchType);

    // 파티수정
    @PUT("/party/{partyId}")
    Call<PartyRoom> updatePartyRoom(@Header("Authorization") String token,
                                    @Path("partyId") int partyId,
                                    @Body PartyRoom partyRoom);

    // 파티삭제
    @DELETE("/party/{partyId}")
    Call<PartyRoom> deletePartyRoom(@Header("Authorization") String token,
                                    @Path("partyId") int partyId);
}