package com.luvris2.publicperfomancedisplayapp.api;

import com.luvris2.publicperfomancedisplayapp.model.PostRes;
import com.luvris2.publicperfomancedisplayapp.model.Posting;
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

// 최지훈
public interface PostingApi {

    // 게시물 생성 API
//    @Multipart
//    @POST("/posting")
//    Call<PostingList> addPosting(@Header("Authorization") String token,
//                                 @Part("title")RequestBody title,
//                                 @Part("content")RequestBody content);

    // 게시물 생성 API
    @POST("/posting")
    Call<PostingList> addPosting(@Header("Authorization") String token,
                                 @Body Posting posting);

    // 게시물 수정 API
    @PUT("/posting/{postingId}")
    Call<PostingList> updatePosting(@Header("Authorization") String token,
                                    @Path("postingId") int postingId,
                                    @Body Posting posting);

    // 게시물 삭제 API
    @DELETE("/posting/{postingId}")
    Call<PostingList> deletePosting(@Header("Authorization") String token);

    // 게시물 목록 조회
    @GET("/posting")
    Call<PostingList> getPostingList(@Query("offset") int offset,
                                     @Query("limit") int limit);

    // 게시물 정렬 커뮤프래그 리사이클러뷰
    @GET("/posting/lists")
    Call<PostingList> getPostingSort(@Query("order") String order,
                                     @Query("offset") int offset,
                                     @Query("limit") int limit);

    // 내 게시물 보기
    @GET("/posting/myposting")
    Call<PostingList> getMyPosting(@Header("Authorization") String token,
                                   @Query("offset") int offset,
                                   @Query("limit") int limit);
    // 게시물 추천 확인
    @GET("/posting/recommend/{postingId}")
    Call<PostRes> checkRecommendPosting(@Header("Authorization") String token,
                                        @Path("postingId") int postingId);

    // 게시물 추천
    @POST("/posting/recommend/{postingId}")
    Call<PostRes> recommendThisPosting(@Header("Authorization") String token,
                                           @Path("postingId") int postingId);

    // 게시물 추천 취소
    @DELETE("/posting/recommend/{postingId}")
    Call<PostRes> cancelRecommendPosting(@Header("Authorization") String token,
                                             @Path("postingId") int postingId);


}
