package com.luvris2.publicperfomancedisplayapp.api;


import com.luvris2.publicperfomancedisplayapp.model.User;
import com.luvris2.publicperfomancedisplayapp.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserApi {

    // 회원가입 API
    @POST("/user/register")
    Call<UserRes> register(@Body User user);

    // 로그인 API
    @POST("/user/login")
    Call<UserRes> login(@Body User user);

    // 로그아웃 API
    @POST("/user/logout")
    Call<UserRes> logout(@Header("Authorization") String token);

    // 회원탈퇴 API
    @DELETE("/user/withdrawal")
    Call<UserRes> withdrawal(@Header("Authorization") String token);

    // 회원정보 가져오는 API
    @GET("/user")
    Call<UserRes> getUserInfo(
            @Header("Authorization") String token);

    // 비밀번호 수정 API
    @PUT("/user/editpassword")
    Call<UserRes> editpassword(@Header("Authorization") String token,
                               @Body User user);
    // 닉네임 수정 API
    @PUT("/user/editnickname")
    Call<UserRes> editnickname(@Header("Authorization") String token,
                               @Body User user);
    // 나이 수정 API
    @PUT("/user/editage")
    Call<UserRes> editage(@Header("Authorization") String token,
                               @Body User user);
    // 성별 수정 API
    @PUT("/user/editgender")
    Call<UserRes> editgender(@Header("Authorization") String token,
                               @Body User user);


}



