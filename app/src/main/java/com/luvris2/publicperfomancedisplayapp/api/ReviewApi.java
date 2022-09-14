package com.luvris2.publicperfomancedisplayapp.api;

import com.luvris2.publicperfomancedisplayapp.model.Review;
import com.luvris2.publicperfomancedisplayapp.model.ReviewList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewApi {

    // 리뷰 작성 API
    @POST("/review/{prfId}")
    Call<Review> addReview(@Header("Authorization") String token,
                           @Path("prfId") String prfId,
                           @Body Review review);

    // 리뷰 추천 취소 API
    @DELETE("/review/recommend/{reviewId}")
    Call<ReviewList> deleteReviewRecommend(@Header("Authorization") String token);

    // 리뷰 작성 API
    @POST("/review")
    Call<ReviewList> ReviewWrite(@Header("Authorization") String token);

    // 리뷰 수정 API
    @PUT("/review/{reviewId}")
    Call<ReviewList> updateReview(@Header("Authorization") String token);

    // 리뷰 자세히보기 API
    @GET("/review/detail/{reviewId}")
    Call<ReviewList> getReviewDetail(@Header("Authorization") String token);

    // 리뷰 삭제 API
    @DELETE("/review/{reviewId}")
    Call<ReviewList> deleteReview(@Header("Ahthorization") String token);

    // 해당 작품 리뷰 보기 API
    @GET("/review/{prfId}")
    Call<ReviewList> getShowReview(@Path("prfId") String prfId,
                                   @Query("offset") int offset,
                                   @Query("limit") int limit);

    // 내 리뷰 보기 API
    @GET("/review/myreview/{userId}")
    Call<ReviewList> getReview(@Header("Authorization") String token,
                                       @Query("offset") int offset,
                                       @Query("limit") int limit);

}
