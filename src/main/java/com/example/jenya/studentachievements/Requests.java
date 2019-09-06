package com.example.jenya.studentachievements;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Requests
{
    private Retrofit retrofit; // retrofit
    private UserApi userApi; // методы сервера
    private static Requests requests; // экземпляр класса
    private final String URL = "http://localhost:8080/";

    public String getURL()
    {
        return this.URL;
    }

    private Requests()
    {
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        userApi = retrofit.create(UserApi.class);
    }

    // метод, необходимый для шаблона 'синглтон'
    public static Requests getInstance()
    {
        if(requests == null)
        {
            return new Requests();
        }
        return  requests;
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
    public void getUserToken(User user) throws JSONException
    {
        userApi.signin(user).enqueue(new Callback<UserToken>()
        {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response)
            {
                if(response.isSuccessful())
                {
                    // сохраняем токен
                    String token = response.body().getUserToken();
                    TokenAction.getInstance().saveToken(token);
                    initializeStudent(token);
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t)
            {
                Toast.makeText(AuthActivity.getAppContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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
    public void initializeStudent(String token)
    {
        userApi.initialize(token).enqueue(new Callback<UserInfo>()
        {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response)
            {
                if(response.isSuccessful())
                {
                    Intent intent = new Intent(SplashScreenActivity.getAppContext(), ProfileActivity.class);
                    UserInfo.loadUserInfo(response.body());
                    SplashScreenActivity.getAppContext().startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(SplashScreenActivity.getAppContext(), AuthActivity.class);
                    SplashScreenActivity.getAppContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t)
            {
                Toast.makeText(SplashScreenActivity.getAppContext(), "Ошибка! Попробуйте зайти позже.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SplashScreenActivity.getAppContext(), AuthActivity.class);
                SplashScreenActivity.getAppContext().startActivity(intent);
            }
        });
    }
}
