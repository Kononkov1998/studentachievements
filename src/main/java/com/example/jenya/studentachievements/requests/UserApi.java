package com.example.jenya.studentachievements.requests;

import com.example.jenya.studentachievements.models.Mark;
import com.example.jenya.studentachievements.models.Semester;
import com.example.jenya.studentachievements.models.StudentMarks;
import com.example.jenya.studentachievements.models.StudentSemesters;
import com.example.jenya.studentachievements.models.User;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.UserToken;
import com.example.jenya.studentachievements.models.Visibility;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
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
    @POST("/student/signin")
    Call<UserToken> signin(@Body User user);

    @POST("/student/initialize")
    Call<UserInfo> initialize(@Header("Authorization") String token);

    @GET("/student/info")
    Call<UserInfo> info(@Header("Authorization") String token);

    @GET("/student/anotherStudent")
    Call<ArrayList<UserInfo>> search(@Header("Authorization") String token, @Query("group") String group, @Query("search") String search);

    @PUT("/student/visibility")
    Call<UserInfo> visibility(@Header("Authorization") String token, @Body Visibility visibility);

    @GET("/student/favourite/list")
    Call<ArrayList<UserInfo>> favourites(@Header("Authorization") String token);

    @POST("/student/favourite")
    Call<UserInfo> addFavourite(@Header("Authorization") String token, @Query("student") String studentID);

    @DELETE("/student/favourite")
    Call<UserInfo> removeFavourite(@Header("Authorization") String token, @Query("student") String studentID);

    @Multipart
    @POST("/student/pic")
    Call<UserInfo> uploadAvatar(@Header("Authorization") String token, @Part MultipartBody.Part avatar);

    @POST("/student/achievements/update")
    Call<UserInfo> update(@Header("Authorization") String token);

    @GET("/student/semester/list")
    Call<StudentSemesters> semesters(@Header("Authorization") String token);

    @GET("/student/semester/marks/{idLGS}")
    Call<StudentMarks> marks(@Header("Authorization") String token, @Path("idLGS") int idLGS);

    @DELETE("/student/account")
    Call<Void> deleteAccount(@Header("Authorization") String token);
}
