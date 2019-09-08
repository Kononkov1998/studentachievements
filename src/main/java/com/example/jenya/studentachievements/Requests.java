package com.example.jenya.studentachievements;

import android.content.Intent;
import android.widget.Toast;

import com.example.jenya.studentachievements.Activities.AuthActivity;
import com.example.jenya.studentachievements.Activities.ProfileActivity;
import com.example.jenya.studentachievements.Activities.SplashScreenActivity;
import com.example.jenya.studentachievements.Models.User;
import com.example.jenya.studentachievements.Models.UserInfo;
import com.example.jenya.studentachievements.Models.UserToken;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

final public class Requests {
    private static Retrofit retrofit; // retrofit
    private static UserApi userApi; // методы сервера
    private static String URL = "http://76466c11.ngrok.io";

    private Requests() {
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        userApi = retrofit.create(UserApi.class);
    }

    public static String getURL() {
        return URL;
    }

    // /student/groupmates
    /*public void getGroupmates(String token)
    {
        userApi.groupmates(token).enqueue(new Callback<UserInfo[]>()
        {
            @Override
            public void onResponse(Call<UserInfo[]> call, Response<UserInfo[]> response)
            {
                UserInfo[] groupmates = response.body();
            }

            @Override
            public void onFailure(Call<UserInfo[]> call, Throwable t)
            {

            }
        });
    }*/

    // /student/signin
    public static void getUserToken(User user) throws JSONException {
        userApi.signin(user).enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                if (response.isSuccessful()) {
                    // сохраняем токен
                    String token = response.body().getUserToken();
                    TokenAction.saveToken(token);
                    initializeStudent(token);
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {

            }
        });
    }

    // /student/info
    /*public void getUserInfo(final String token)
    {
        userApi.info(token).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response)
            {
                if(response.isSuccessful())
                {
                    Intent intent = new Intent(AuthActivity.getAppContext(), ProfileActivity.class);
                    UserInfo.loadUserInfo(response.body());
                    AuthActivity.getAppContext().startActivity(intent);
                }
                else
                {
                    initializeStudent(token);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t)
            {
                Toast.makeText(AuthActivity.getAppContext(), "Ошибка! Попробуйте зайти позже.", Toast.LENGTH_LONG).show();
            }
        });
    }*/

    // /student/initialize
    public static void initializeStudent(String token) {
        userApi.initialize(token).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Toast.makeText(SplashScreenActivity.getAppContext(), "work!.", Toast.LENGTH_LONG).show();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(SplashScreenActivity.getAppContext(), ProfileActivity.class);
                    UserInfo.setCurrentUser(response.body());
                    SplashScreenActivity.getAppContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreenActivity.getAppContext(), AuthActivity.class);
                    SplashScreenActivity.getAppContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(SplashScreenActivity.getAppContext(), "Ошибка! Попробуйте зайти позже.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SplashScreenActivity.getAppContext(), AuthActivity.class);
                SplashScreenActivity.getAppContext().startActivity(intent);
            }
        });
    }
}
