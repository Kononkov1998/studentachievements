package com.example.jenya.studentachievements.requests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.jenya.studentachievements.activities.AuthActivity;
import com.example.jenya.studentachievements.activities.DisciplinesActivity;
import com.example.jenya.studentachievements.activities.FavoritesActivity;
import com.example.jenya.studentachievements.activities.OtherProfileFromTopActivity;
import com.example.jenya.studentachievements.activities.ProfileActivity;
import com.example.jenya.studentachievements.activities.SearchActivity;
import com.example.jenya.studentachievements.activities.SearchResultsActivity;
import com.example.jenya.studentachievements.activities.SemestersActivity;
import com.example.jenya.studentachievements.activities.SettingsActivity;
import com.example.jenya.studentachievements.adapters.UsersAdapter;
import com.example.jenya.studentachievements.comparators.StudentsComparator;
import com.example.jenya.studentachievements.fragments.TopFragment;
import com.example.jenya.studentachievements.models.StudentMarks;
import com.example.jenya.studentachievements.models.StudentSemesters;
import com.example.jenya.studentachievements.models.Top;
import com.example.jenya.studentachievements.models.User;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.UserToken;
import com.example.jenya.studentachievements.models.Visibility;
import com.example.jenya.studentachievements.utils.ButtonActions;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
                    Toast.makeText(ctx, "Неверный логин и/или пароль!", Toast.LENGTH_SHORT).show();
                    ButtonActions.enableButton(btn);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserToken> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
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
                } else if (response.code() == 403) {
                    initializeStudent(ctx, btn);
                } else {
                    Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                    ButtonActions.enableButton(btn);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
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

    // /student/semester/list
    public void getSemesters(SemestersActivity activity) {
        userApi.semesters(SharedPreferencesActions.read("token", activity)).enqueue(new Callback<StudentSemesters>() {
            @Override
            public void onResponse(@NonNull Call<StudentSemesters> call, @NonNull Response<StudentSemesters> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        activity.setSemesters(response.body().getStudentSemesters());
                    }
                } else {
                    activity.setSemesters(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StudentSemesters> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                activity.setSemesters(null);
            }
        });
    }

    // /student/semester/marks/{idLGS}
    public void getMarks(DisciplinesActivity activity, int idLGS) {
        userApi.marks(SharedPreferencesActions.read("token", activity), idLGS).enqueue(new Callback<StudentMarks>() {
            @Override
            public void onResponse(@NonNull Call<StudentMarks> call, @NonNull Response<StudentMarks> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        activity.setMarks(response.body().getRatings());
                    }
                } else {
                    activity.setMarks(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StudentMarks> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                activity.setMarks(null);
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
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                ButtonActions.enableButton(btn);
            }
        });
    }

    // /student/anotherStudent
    public void studentSearch(SearchResultsActivity activity, String group, String search) {
        userApi.search(SharedPreferencesActions.read("token", activity), group, search).enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Response<ArrayList<UserInfo>> response) {
                if (response.isSuccessful()) {
                    SearchActivity.searchSuccessful();
                    activity.populateListView(response.body());
                } else {
                    activity.populateListView(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                activity.populateListView(null);
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
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
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
                    Collections.sort(UserInfo.getCurrentUser().getFavouriteStudents(), new StudentsComparator());

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
    public void updateFavourites(FavoritesActivity activity) {
        userApi.favourites(SharedPreferencesActions.read("token", activity)).enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Response<ArrayList<UserInfo>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        activity.updateFavourites(response.body());
                    }
                } else {
                    activity.updateFavourites(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserInfo>> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                activity.updateFavourites(null);
            }
        });
    }

    // /student/favourite?student=<idStudent>
    public void addFavourite(UserInfo otherStudent, Context ctx) {
        otherStudent.setIsAvailable(true);
        UserInfo.getCurrentUser().getFavouriteStudents().add(otherStudent);
        userApi.addFavourite(SharedPreferencesActions.read("token", ctx), otherStudent.get_id()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                UserInfo.getCurrentUser().getFavouriteStudents().remove(otherStudent);
            }
        });
    }

    // /student/favourite?student=<idStudent>
    public void removeFavourite(UserInfo otherStudent, Context ctx) {
        UserInfo userForRemove = findStudentInFavourites(otherStudent);
        UserInfo.getCurrentUser().getFavouriteStudents().remove(userForRemove);

        userApi.removeFavourite(SharedPreferencesActions.read("token", ctx), otherStudent.get_id()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                if (userForRemove != null) {
                    UserInfo.getCurrentUser().getFavouriteStudents().add(userForRemove);
                }
            }
        });
    }

    // /student/favourite?student=<idStudent>
    public void removeFavourite(UserInfo otherStudent, Context ctx, UsersAdapter adapter) {
        UserInfo userForRemove = findStudentInFavourites(otherStudent);
        UserInfo.getCurrentUser().getFavouriteStudents().remove(userForRemove);
        adapter.notifyDataSetChanged();

        userApi.removeFavourite(SharedPreferencesActions.read("token", ctx), otherStudent.get_id()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                if (userForRemove != null) {
                    UserInfo.getCurrentUser().getFavouriteStudents().add(userForRemove);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    // /student/pic
    public void uploadAvatar(MultipartBody.Part body, Context ctx) {
        userApi.uploadAvatar(SharedPreferencesActions.read("token", ctx), body).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    ((ProfileActivity) ctx).setNewAvatar(response.body());
                } else if (response.code() == 400) {
                    ((ProfileActivity) ctx).setNewAvatar(null);
                    Toast.makeText(ctx, "Аватар не загружен. Проверьте соединение с интернетом", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                ((ProfileActivity) ctx).setNewAvatar(null);
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                ButtonActions.enableButton(btn);
            }
        });
    }

    private void updateFromSplashScreen(Context ctx) {
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
                    getFavourites(ctx, false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ctx, AuthActivity.class);
                ctx.startActivity(intent);
            }
        });
    }

    // /student/account
    public void deleteAccount(Context ctx) {
        userApi.deleteAccount(SharedPreferencesActions.read("token", ctx)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    SharedPreferencesActions.deleteAll(ctx);
                    ((SettingsActivity) ctx).exit(null);
                    Toast.makeText(ctx, "Аккаунт успешно удалён из базы данных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(ctx, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // /statistics/top
    public void topStudents(TopFragment fragment, int pageNumber, int pageSize, String region) {
        if (fragment.getContext() != null) {
            userApi.topStudents(SharedPreferencesActions.read("token", fragment.getContext()), pageNumber, pageSize, region).enqueue(new Callback<Top>() {
                @Override
                public void onResponse(@NonNull Call<Top> call, @NonNull Response<Top> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            fragment.populateListView(response.body().getList(), response.body().getPageInfo().get(0).getTotal());
                        }
                    } else {
                        fragment.populateListView(null, -1);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Top> call, @NonNull Throwable t) {
                    Toast.makeText(fragment.getContext(), "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                    fragment.populateListView(null, -1);
                }
            });
        }
    }

    // /student/students/{student_id}
    public void student(OtherProfileFromTopActivity activity, String id) {
        userApi.student(SharedPreferencesActions.read("token", activity), id).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        activity.loadInfo(response.body());
                    }
                } else {
                    Toast.makeText(activity, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Сервер не отвечает. Попробуйте позже", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private UserInfo findStudentInFavourites(UserInfo student) {
        for (UserInfo user : UserInfo.getCurrentUser().getFavouriteStudents()) {
            if (user.get_id().equals(student.get_id())) {
                return user;
            }
        }
        return null;
    }
}
