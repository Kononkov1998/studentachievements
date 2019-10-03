package com.example.jenya.studentachievements;

import com.example.jenya.studentachievements.models.User;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.UserToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi
{
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("/student/signin")
    Call<UserToken> signin(@Body User user);

    @POST("/student/initialize")
    Call<UserInfo> initialize(@Header("Authorization") String token);

    /*@GET("/student/info")
    Call<UserInfo> info(@Header("Authorization") String token);*/

    /*@GET("/student/groupmates")
    Call<UserInfo[]> groupmates(@Header("Authorization") String token);*/

    @GET("/student/anotherStudent")
    Call<UserInfo[]> search(@Header("Authorization") String token, @Query("group") String group, @Query("search") String search);
}
