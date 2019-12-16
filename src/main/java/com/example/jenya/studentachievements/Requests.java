package com.example.jenya.studentachievements;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.jenya.studentachievements.activities.AuthActivity;
import com.example.jenya.studentachievements.activities.ProfileActivity;
import com.example.jenya.studentachievements.activities.SearchActivity;
import com.example.jenya.studentachievements.activities.SearchNoResultsActivity;
import com.example.jenya.studentachievements.activities.SearchResultsActivity;
import com.example.jenya.studentachievements.models.User;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.UserToken;
import com.example.jenya.studentachievements.models.Visibility;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Requests {
    private static final String URL = "http://192.168.1.65:8080";
    private final UserApi userApi;
    private static Requests instance;

    private Requests() {
        Retrofit retrofit = new Retrofit.Builder()
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
                    assert response.body() != null;
                    String token = response.body().getUserToken();
                    SharedPreferencesActions.save("token", token, ctx);
                    getUserInfo(ctx, btn);
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
    private void getUserInfo(Context ctx, Button btn) {
        userApi.info(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ctx, ProfileActivity.class);
                    UserInfo.setCurrentUser(response.body());
                    getFavourites(ctx);
                    ctx.startActivity(intent);
                    ((Activity) ctx).finish();
                } else {
                    initializeStudent(ctx, btn);
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
    public void getUserInfoFromSplashScreen(Context ctx) {
        userApi.info(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ctx, ProfileActivity.class);
                    UserInfo.setCurrentUser(response.body());
                    getFavourites(ctx);
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
    private void initializeStudent(Context ctx, Button btn) {
        userApi.initialize(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ctx, ProfileActivity.class);
                    UserInfo.setCurrentUser(response.body());
                    getFavourites(ctx);
                    ctx.startActivity(intent);
                    ((Activity) ctx).finish();
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
    public void studentSearch(String group, String search, Context ctx, Button btn) {
        userApi.search(SharedPreferencesActions.read("token", ctx), group, search).enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Response<ArrayList<UserInfo>> response) {
                if (response.isSuccessful()) {
                    SearchActivity.searchSuccessful();

                    assert response.body() != null;
                    if (response.body().isEmpty()) {
                        Intent intent = new Intent(ctx, SearchNoResultsActivity.class);
                        ctx.startActivity(intent);
                    } else {
                        Intent intent = new Intent(ctx, SearchResultsActivity.class);
                        intent.putParcelableArrayListExtra("students", response.body());
                        ctx.startActivity(intent);
                    }
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
    public void setVisibility(Visibility visibility, Context ctx) {
        userApi.visibility(SharedPreferencesActions.read("token", ctx), visibility).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
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
    private void getFavourites(Context ctx) {
        userApi.favourites(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<ArrayList<UserInfo>>() {
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
    public void addFavourite(UserInfo otherStudent, Context ctx) {
        UserInfo.getCurrentUser().getFavouriteStudents().add(otherStudent);

        userApi.addFavourite(SharedPreferencesActions.read("token", ctx), otherStudent.get_id()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                UserInfo.getCurrentUser().getFavouriteStudents().remove(otherStudent);
            }
        });
    }

    // /student/favourite?student=<idStudent>
    public void removeFavourite(UserInfo otherStudent, Context ctx) {
        UserInfo userForRemove = null;
        for (UserInfo user : UserInfo.getCurrentUser().getFavouriteStudents()) {
            if (user.get_id().equals(otherStudent.get_id())) {
                userForRemove = user;
                break;
            }
        }
        UserInfo.getCurrentUser().getFavouriteStudents().remove(userForRemove);
        UserInfo finalUserForRemove = userForRemove;

        userApi.removeFavourite(SharedPreferencesActions.read("token", ctx), otherStudent.get_id()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                if (finalUserForRemove != null) {
                    UserInfo.getCurrentUser().getFavouriteStudents().add(finalUserForRemove);
                }
            }
        });
    }

    // /student/pic
    public void uploadAvatar(MultipartBody.Part body, Context ctx, CircleImageView avatar, AlertDialog dialog)
    {
        userApi.uploadAvatar(SharedPreferencesActions.read("token", ctx), body).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response)
            {
                if(response.isSuccessful())
                {
                    UserInfo.getCurrentUser().setAvatar(response.body().getAvatar());
                    GlideUrl glideUrl = new GlideUrl(String.format("%s/student/pic/%s", Requests.getInstance().getURL(), UserInfo.getCurrentUser().getAvatar()), new LazyHeaders.Builder()
                            .addHeader("Authorization", SharedPreferencesActions.read("token", ctx))
                            .build());

                    Glide.with(ctx)
                            .load(glideUrl)
                            .placeholder(R.drawable.profile)
                            .into(avatar);
                    dialog.dismiss();
                }
                else
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t)
            {
                dialog.dismiss();
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
            }
        });
    }
}
