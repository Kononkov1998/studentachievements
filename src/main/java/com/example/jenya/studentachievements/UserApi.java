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

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("/student/signin")
    Call<UserToken> initialize(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET("/student/info")
    Call<UserGetRequest> info(@Header("Authorization") String token);
}
