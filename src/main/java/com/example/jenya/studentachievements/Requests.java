package com.example.jenya.studentachievements;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.example.jenya.studentachievements.Activities.AuthActivity;
import com.example.jenya.studentachievements.Activities.ProfileActivity;
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
    private static String URL = "http://9a2f6a9e.ngrok.io";
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build(); // retrofit
    private static UserApi userApi = retrofit.create(UserApi.class); // методы сервера

    private Requests() {
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
    public static void getUserToken(User user, Context ctx) throws JSONException
    {
        userApi.signin(user).enqueue(new Callback<UserToken>()
        {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response)
            {
                if (response.isSuccessful())
                {
                    // сохраняем токен
                    String token = response.body().getUserToken();
                    TokenAction.saveToken(token, ctx);
                    initializeStudent(token, ctx);
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t)
            {
                AuthActivity.toggleButton();
                Toast.makeText(ctx, "Нет ответа от сервера", Toast.LENGTH_LONG).show();
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
    public static void initializeStudent(String token, Context ctx)
    {
        userApi.initialize(token).enqueue(new Callback<UserInfo>()
        {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response)
            {
                if (response.isSuccessful())
                {
                    Intent intent = new Intent(ctx, ProfileActivity.class);
                    UserInfo.setCurrentUser(response.body());
                    ctx.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ctx, AuthActivity.class);
                    ctx.startActivity(intent);
                }
                AuthActivity.toggleButton();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t)
            {
                AuthActivity.toggleButton();
                Toast.makeText(ctx, "Нет ответа от сервера", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ctx, AuthActivity.class);
                ctx.startActivity(intent);
            }
        });
    }
}
