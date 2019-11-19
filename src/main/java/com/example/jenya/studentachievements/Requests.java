package com.example.jenya.studentachievements;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.jenya.studentachievements.activities.AuthActivity;
import com.example.jenya.studentachievements.activities.ProfileActivity;
import com.example.jenya.studentachievements.activities.SearchActivity;
import com.example.jenya.studentachievements.activities.SearchResultsActivity;
import com.example.jenya.studentachievements.models.User;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.UserToken;
import com.example.jenya.studentachievements.models.Visibility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Requests {
    private static final String URL = "https://b0fbdc0b.ngrok.io";
    private Retrofit retrofit;
    private UserApi userApi;
    private static Requests instance;

    private Requests() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApi = retrofit.create(UserApi.class); // методы сервера
    }

    public static Requests getInstance() {
        if (instance == null) {
            instance = new Requests();
        }
        return instance;
    }

    public String getURL() {
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
    public void getUserToken(User user, Context ctx, Button btn) {
        userApi.signin(user).enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(@NonNull Call<UserToken> call, @NonNull Response<UserToken> response) {
                if (response.isSuccessful()) {
                    // сохраняем токен
                    String token = response.body().getUserToken();
                    SharedPreferencesActions.save("token", token, ctx);
                    getUserInfo(token, ctx, btn);
                } else {
                    Toast.makeText(ctx, "Неверный логин и/или пароль!", Toast.LENGTH_LONG).show();
                    btn.getBackground().setAlpha(255);
                    btn.setEnabled(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserToken> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                btn.getBackground().setAlpha(255);
                btn.setEnabled(true);
            }
        });
    }

    // /student/info
    public void getUserInfo(String token, Context ctx, Button btn) {
        userApi.info(token).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ctx, ProfileActivity.class);
                    UserInfo.setCurrentUser(response.body());
                    getFavourites(token, ctx);
                    ctx.startActivity(intent);
                } else {
                    initializeStudent(token, ctx, btn);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                btn.getBackground().setAlpha(255);
                btn.setEnabled(true);
            }
        });
    }

    // /student/info
    public void getUserInfoFromSplashScreen(String token, Context ctx) {
        userApi.info(token).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ctx, ProfileActivity.class);
                    UserInfo.setCurrentUser(response.body());
                    getFavourites(token, ctx);
                    ctx.startActivity(intent);
                } else {
                    Intent intent = new Intent(ctx, AuthActivity.class);
                    ctx.startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Intent intent = new Intent(ctx, AuthActivity.class);
                ctx.startActivity(intent);
            }
        });
    }

    // /student/initialize
    public void initializeStudent(String token, Context ctx, Button btn) {
        userApi.initialize(token).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ctx, ProfileActivity.class);
                    UserInfo.setCurrentUser(response.body());
                    getFavourites(token, ctx);
                    ctx.startActivity(intent);
                } else {
                    btn.getBackground().setAlpha(255);
                    btn.setEnabled(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {

                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                btn.getBackground().setAlpha(255);
                btn.setEnabled(true);
            }
        });
    }

    // /student/anotherStudent
    public void studentSearch(String token, String group, String search, Context ctx, Button btn) {
        userApi.search(token, group, search).enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Response<ArrayList<UserInfo>> response) {
                if (response.isSuccessful()) {
                    SearchActivity.searchSuccessful();

                    Intent intent = new Intent(ctx, SearchResultsActivity.class);
                    intent.putParcelableArrayListExtra("students", response.body());
                    ctx.startActivity(intent);
                } else {
                    btn.getBackground().setAlpha(255);
                    btn.setEnabled(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                btn.getBackground().setAlpha(255);
                btn.setEnabled(true);
            }
        });
    }

    // /student/visibility
    public void setVisibility(String token, Visibility visibility, Context ctx) {
        userApi.visibility(token, visibility).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    UserInfo.getCurrentUser().setVisibility(response.body().getVisibility());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    // /student/favourite/list
    public void getFavourites(String token, Context ctx) {
        userApi.favourites(token).enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Response<ArrayList<UserInfo>> response) {
                if (response.isSuccessful()) {
                    UserInfo.getCurrentUser().setFavouriteStudents(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    // /student/favourite?student=<idStudent>
    public void addFavourite(String token, UserInfo otherStudent, Context ctx) {
        userApi.addFavourite(token, otherStudent.get_id()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    UserInfo.getCurrentUser().getFavouriteStudents().add(otherStudent);
                    Log.e("CHECK", "added " + otherStudent.get_id() + ", size now: " + UserInfo.getCurrentUser().getFavouriteStudents().size());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    // /student/favourite?student=<idStudent>
    public void removeFavourite(String token, UserInfo otherStudent, Context ctx) {
        userApi.removeFavourite(token, otherStudent.get_id()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    for (UserInfo user : UserInfo.getCurrentUser().getFavouriteStudents()) {
                        if (user.get_id().equals(otherStudent.get_id())){
                            UserInfo.getCurrentUser().getFavouriteStudents().remove(user);
                            break;
                        }
                    }
                    Log.e("CHECK", "deleted " + otherStudent.get_id() + ", size now: " + UserInfo.getCurrentUser().getFavouriteStudents().size());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
            }
        });
    }
}
