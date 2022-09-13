package com.luvris2.publicperfomancedisplayapp.api;

import com.luvris2.publicperfomancedisplayapp.model.LikeList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PerformanceApi {

//    // 공연전시 좋아요 포함 상세조회 API
//    @GET("/performancedetaildb/{prfId}")
//    Call<LikeList> likeDetaildb(@Query(""))

    // 공연전시 좋아요 API

    // 공연전시 좋아요 해제 API

    // 공연전시 개인화 추천 API (작업중)


//    // 게시물 생성 API
//    @POST("/posting")
//    Call<PostingList> addPosting(@Header("Authorization") String token,
//                                 @Body Posting posting);
//
//    // 게시물 수정 API
//    @PUT("/posting/{postingId}")
//    Call<PostingList> updatePosting(@Header("Authorization") String token,
//                                    @Path("postingId") int postingId,
//                                    @Body Posting posting);
//
//    // 게시물 삭제 API
//    @DELETE("/posting/{postingId}")
//    Call<PostingList> deletePosting(@Header("Authorization") String token);
//
//    // 게시물 목록 조회
//    @GET("/posting")
//    Call<PostingList> getPostingList(@Query("offset") int offset,
//                                     @Query("limit") int limit);
//
//    // 게시물 정렬 커뮤프래그 리사이클러뷰
//    @GET("/posting/lists")
//    Call<PostingList> getPostingSort(@Query("order") String order,
//                                     @Query("offset") int offset,
//                                     @Query("limit") int limit);
//
//    // 내 게시물 보기
//    @GET("/posting/myposting")
//    Call<PostingList> getMyPosting(@Header("Authorization") String token,
//                                   @Query("offset") int offset,
//                                   @Query("limit") int limit);
}
