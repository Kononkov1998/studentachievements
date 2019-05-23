package com.example.jenya.studentachievements;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    private final String URL = "http://46cf3e68.ngrok.io/";

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

    // /student/signin
    public void getUserToken(User user)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        userApi.signin(gson.toJson(user)).enqueue(new Callback<UserToken>()
        {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response)
            {
                if(response.isSuccessful())
                {
                    // сохраняем токен
                    String token = response.body().getUserToken();
                    AuthActivity.saveToken(token);
                    // вызываем метод /student/info, если его ответ не 200 то /student/initialize
                    getUserInfo(token);
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
    public void getUserInfo(final String token)
    {
        userApi.info(token).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response)
            {
                if(response.isSuccessful())
                {
                    Intent intent = new Intent(AuthActivity.getAppContext(), ProfileActivity.class);
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

            }
        });
    }

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
                    Intent intent = new Intent(AuthActivity.getAppContext(), ProfileActivity.class);
                    AuthActivity.getAppContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t)
            {

            }
        });
    }
}
