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
    private final String URL = "http://localhost:8080/";

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

    // post-запрос
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
                    AuthActivity.saveToken(response.body());
                    Intent intent = new Intent(AuthActivity.getAppContext(), ProfileActivity.class);
                    AuthActivity.getAppContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.getAppContext());
                builder.setTitle("Ошибка!")
                        .setMessage("Неверный логин или пароль!")
                        .setCancelable(false)
                        .setNegativeButton("Ок",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    /* ПОКА ЧТО ЗАКОММЕНТИЛ ПОТОМУ ЧТО НЕ ИСПОЛЬЗУЕТСЯ
    // get-запрос
    public void getUserByToken(String token)
    {
        userApi.getUserByToken(token).enqueue(new Callback<UserGetRequest>()
        {
            @Override
            public void onResponse(Call<UserGetRequest> call, Response<UserGetRequest> response)
            {
                if(response.isSuccessful())
                {
                    MainActivity.startNewActivity(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserGetRequest> call, Throwable t)
            {

            }
        });
    }
    */
}
