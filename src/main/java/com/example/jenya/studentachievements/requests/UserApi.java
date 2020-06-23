package com.example.jenya.studentachievements.requests;

import com.example.jenya.studentachievements.models.StudentMarks;
import com.example.jenya.studentachievements.models.StudentSemesters;
import com.example.jenya.studentachievements.models.Top;
import com.example.jenya.studentachievements.models.User;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.UserToken;
import com.example.jenya.studentachievements.models.Visibility;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface UserApi
{
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("/students/signin")
    Call<UserToken> signin(@Body User user);

    @POST("/students/initialize")
    Call<UserInfo> initialize(@Header("Authorization") String token);

    @GET("/students/info")
    Call<UserInfo> info(@Header("Authorization") String token);

    @GET("/students/anotherStudents")
    Call<ArrayList<UserInfo>> search(@Header("Authorization") String token, @Query("group") String group, @Query("search") String search);

    @PUT("/students/visibility")
    Call<UserInfo> visibility(@Header("Authorization") String token, @Body Visibility visibility);

    @GET("/students/favourite/list")
    Call<ArrayList<UserInfo>> favourites(@Header("Authorization") String token);

    @POST("/students/favourite")
    Call<UserInfo> addFavourite(@Header("Authorization") String token, @Query("student") String studentID);

    @DELETE("/students/favourite")
    Call<UserInfo> removeFavourite(@Header("Authorization") String token, @Query("student") String studentID);

    @Multipart
    @POST("/student/pic")
    Call<UserInfo> uploadAvatar(@Header("Authorization") String token, @Part MultipartBody.Part avatar);

    @GET("/deans/semesters")
    Call<StudentSemesters> semesters(@Header("Authorization") String token);

    @GET("/deans/semester/{id}")
    Call<StudentMarks> marks(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("/students/account")
    Call<Void> deleteAccount(@Header("Authorization") String token);

    @GET("/statistics/top")
    Call<Top> topStudents(@Header("Authorization") String token, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("region") String region);

    @GET("/students/student/{id}")
    Call<UserInfo> student(@Header("Authorization") String token, @Path("id") String id);
}
