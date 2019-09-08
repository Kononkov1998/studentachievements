package com.example.jenya.studentachievements;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.jenya.studentachievements.Activities.AuthActivity;
import com.example.jenya.studentachievements.Activities.SplashScreenActivity;

import static android.content.Context.MODE_PRIVATE;

final public class TokenAction {
    private static SharedPreferences settings; // настройки

    private TokenAction() {
        settings = SplashScreenActivity.getAppContext().getSharedPreferences("User", MODE_PRIVATE);
    }

    // сохраняем токен
    public static void saveToken(String token) {
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("token", token);
        prefEditor.apply();
    }

    // удаляем токен
    public static void deleteToken() {
        if (settings.contains("token")) {
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.remove("token");
            prefEditor.apply();
        }
    }

    // проверяем, есть ли сохраненный токен
    public static void checkToken() {
        if (settings.contains("token")) {
            try {
                Requests.initializeStudent(settings.getString("token", ""));
            } catch (Exception e) {

            }
        } else {
            Intent intent = new Intent(SplashScreenActivity.getAppContext(), AuthActivity.class);
            SplashScreenActivity.getAppContext().startActivity(intent);
        }
    }
}
