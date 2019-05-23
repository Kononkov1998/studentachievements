package com.example.jenya.studentachievements;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApi
{
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("/student/signin")
    Call<UserToken> signin(@Body String user);

    @POST("/student/initialize")
    Call<UserInfo> initialize(@Header("Authorization") String token);

    @GET("/student/info")
    Call<UserInfo> info(@Header("Authorization") String token);
}
