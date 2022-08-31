package com.luvris2.publicperfomancedisplayapp.api;

import android.content.Context;

import com.luvris2.publicperfomancedisplayapp.config.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient_KOPIS {

    public static Retrofit retrofit_KOPIS;

    public static Retrofit getRetrofitClient(Context context){
        if(retrofit_KOPIS == null){
            // 네트워크 통신 로그
            HttpLoggingInterceptor loggingInterceptor =
                    new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            // 네트워크 연결
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit_KOPIS = new Retrofit.Builder().baseUrl(Config.KOPIS_OPEN_API_BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return retrofit_KOPIS;
    }
}


