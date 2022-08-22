package com.luvris2.publicperfomancedisplayapp.api;


import com.luvris2.publicperfomancedisplayapp.model.UserRes;
import com.luvris2.publicperfomancedisplayapp.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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


}



