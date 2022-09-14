package com.luvris2.publicperfomancedisplayapp.api;

import com.luvris2.publicperfomancedisplayapp.model.Review;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NaverClovaApi {
    // 티켓 이미지 판독 후 인증 여부 확인 API
    @Multipart
    @POST("/imgocr")
    Call<Review> imgVerified(@Part("prfName") RequestBody prfName,
                            @Part MultipartBody.Part photo);
}