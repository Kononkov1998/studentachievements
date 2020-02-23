package com.example.jenya.studentachievements.requests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.activities.AuthActivity;
import com.example.jenya.studentachievements.activities.ProfileActivity;
import com.example.jenya.studentachievements.activities.SearchActivity;
import com.example.jenya.studentachievements.activities.SearchNoResultsActivity;
import com.example.jenya.studentachievements.activities.SearchResultsActivity;
import com.example.jenya.studentachievements.adapters.UsersAdapter;
import com.example.jenya.studentachievements.models.User;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.UserToken;
import com.example.jenya.studentachievements.models.Visibility;
import com.example.jenya.studentachievements.utils.ButtonActions;
import com.example.jenya.studentachievements.utils.ImageActions;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Requests {
    private static final String URL = com.example.jenya.studentachievements.requests.URL.getCurrentURL();
    private final UserApi userApi;
    private static Requests instance;

    private Requests() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
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

    // /student/signin
    public void getUserToken(User user, Context ctx, Button btn) {
        userApi.signin(user).enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(@NonNull Call<UserToken> call, @NonNull Response<UserToken> response) {
                if (response.isSuccessful()) {
                    // сохраняем токен
                    String token = response.body().getUserToken();
                    SharedPreferencesActions.save("token", token, ctx);
                    getUserInfo(ctx, btn);
                } else {
                    Toast.makeText(ctx, "Неверный логин и/или пароль!", Toast.LENGTH_LONG).show();
                    ButtonActions.enableButton(btn);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserToken> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                ButtonActions.enableButton(btn);
            }
        });
    }

    // /student/info
    private void getUserInfo(Context ctx, Button btn) {
        userApi.info(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    String id = response.body().get_id();

                    // если такой токен есть
                    if (SharedPreferencesActions.check(id, ctx)) {
                        String pattern = "dd.MM.yyyy";
                        DateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);

                        String dateAsString = SharedPreferencesActions.read(id, ctx);
                        Date today = Calendar.getInstance().getTime();

                        try {
                            // вычисляем разницу в днях
                            Date date = format.parse(dateAsString);
                            long milliseconds = Math.abs(today.getTime() - date.getTime());
                            int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
                            // если пользователь не обновлялся 30 дней
                            if (days > 30) {
                                update(ctx, btn);
                                return;
                            }
                        } catch (Exception ignored) {
                        }

                    }
                    // если такого токена нет
                    else {
                        update(ctx, btn);
                        return;
                    }
                    UserInfo.setCurrentUser(response.body());
                    getFavourites(ctx, true);
                } else if(response.code() == 403) {
                    initializeStudent(ctx, btn);
                }
                else
                {
                    Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                    ButtonActions.enableButton(btn);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                ButtonActions.enableButton(btn);
            }
        });
    }

    // /student/info
    public void getUserInfoFromSplashScreen(Context ctx) {
        userApi.info(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    String id = response.body().get_id();

                    // если такой токен есть
                    if (SharedPreferencesActions.check(id, ctx)) {
                        String pattern = "dd.MM.yyyy";
                        DateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);

                        String dateAsString = SharedPreferencesActions.read(id, ctx);
                        Date today = Calendar.getInstance().getTime();

                        try {
                            // вычисляем разницу в днях
                            Date date = format.parse(dateAsString);
                            long milliseconds = Math.abs(today.getTime() - date.getTime());
                            int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
                            // если пользователь не обновлялся 30 дней
                            if (days > 30) {
                                updateFromSplashScreen(ctx);
                                return;
                            }
                        } catch (Exception ignored) {
                        }

                    }
                    // если такого токена нет
                    else {
                        updateFromSplashScreen(ctx);
                        return;
                    }
                    UserInfo.setCurrentUser(response.body());
                    getFavourites(ctx, false);
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
                    String id = response.body().get_id();
                    String pattern = "dd.MM.yyyy";
                    DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
                    Date today = Calendar.getInstance().getTime();
                    String todayAsString = dateFormat.format(today);
                    SharedPreferencesActions.save(id, todayAsString, ctx);

                    UserInfo.setCurrentUser(response.body());
                    getFavourites(ctx, true);
                } else {
                    ButtonActions.enableButton(btn);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                ButtonActions.enableButton(btn);
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

                    if (response.body().isEmpty()) {
                        Intent intent = new Intent(ctx, SearchNoResultsActivity.class);
                        ctx.startActivity(intent);
                    } else {
                        Intent intent = new Intent(ctx, SearchResultsActivity.class);
                        intent.putParcelableArrayListExtra("students", response.body());
                        ctx.startActivity(intent);
                    }
                } else {
                    ButtonActions.enableButton(btn);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                ButtonActions.enableButton(btn);
            }
        });
    }

    // /student/visibility
    public void setVisibility(Visibility visibility, Context ctx) {
        String visibilityStr = UserInfo.getCurrentUser().getVisibility();
        UserInfo.getCurrentUser().setVisibility(visibility.getVisibility());

        userApi.visibility(SharedPreferencesActions.read("token", ctx), visibility).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                UserInfo.getCurrentUser().setVisibility(visibilityStr);
            }
        });
    }

    // /student/favourite/list
    private void getFavourites(Context ctx, boolean needToFinishActivity) {
        userApi.favourites(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Response<ArrayList<UserInfo>> response) {
                if (response.isSuccessful()) {
                    UserInfo.getCurrentUser().setFavouriteStudents(response.body());
                    Intent intent = new Intent(ctx, ProfileActivity.class);
                    ctx.startActivity(intent);
                    if (needToFinishActivity) {
                        ((Activity) ctx).finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ctx, AuthActivity.class);
                ctx.startActivity(intent);
            }
        });
    }

    // /student/favourite/list
    public void updateFavourites(Context ctx, KProgressHUD hud, UsersAdapter adapter) {
        userApi.favourites(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Response<ArrayList<UserInfo>> response) {
                if (response.isSuccessful()) {
                    adapter.updateObjects(response.body());
                    adapter.notifyDataSetChanged();
                    hud.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Throwable t) {
                hud.dismiss();
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
    public void uploadAvatar(MultipartBody.Part body, Context ctx, CircleImageView avatar, KProgressHUD hud) {
        userApi.uploadAvatar(SharedPreferencesActions.read("token", ctx), body).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        UserInfo.getCurrentUser().setAvatar(response.body().getAvatar());
                        int px = ImageActions.getAvatarSizeInPx(ctx);

                        GlideUrl glideUrl = new GlideUrl(String.format("%s/student/pic/%s", Requests.getInstance().getURL(), response.body().getAvatar()), new LazyHeaders.Builder()
                                .addHeader("Authorization", SharedPreferencesActions.read("token", ctx))
                                .build());

                        Glide.with(ctx)
                                .load(glideUrl)
                                .placeholder(R.drawable.profile)
                                .override(px, px)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(avatar);
                    }
                }
                hud.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                hud.dismiss();
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    // /achievements/update
    private void update(Context ctx, Button btn) {
        userApi.update(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    String id = response.body().get_id();
                    String pattern = "dd.MM.yyyy";
                    DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
                    Date today = Calendar.getInstance().getTime();
                    String todayAsString = dateFormat.format(today);
                    SharedPreferencesActions.save(id, todayAsString, ctx);

                    UserInfo.setCurrentUser(response.body());
                    getFavourites(ctx, true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                ButtonActions.enableButton(btn);
            }
        });
    }

    private void updateFromSplashScreen(Context ctx) {
        userApi.update(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                String id = response.body().get_id();
                String pattern = "dd.MM.yyyy";
                DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
                Date today = Calendar.getInstance().getTime();
                String todayAsString = dateFormat.format(today);
                SharedPreferencesActions.save(id, todayAsString, ctx);

                UserInfo.setCurrentUser(response.body());
                getFavourites(ctx, false);
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ctx, AuthActivity.class);
                ctx.startActivity(intent);
            }
        });
    }
}
