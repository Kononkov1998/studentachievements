package com.example.jenya.studentachievements;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.jenya.studentachievements.Activities.AuthActivity;

import static android.content.Context.MODE_PRIVATE;

final public class TokenAction {

    private TokenAction() {
    }

    // сохраняем токен
    public static void saveToken(String token, Context ctx) {
        SharedPreferences.Editor prefEditor = ctx.getSharedPreferences("User", MODE_PRIVATE).edit();
        prefEditor.putString("token", token);
        prefEditor.apply();
    }

    // удаляем токен
    public static void deleteToken(Context ctx) {
        if (ctx.getSharedPreferences("User", MODE_PRIVATE).contains("token")) {
            SharedPreferences.Editor prefEditor = ctx.getSharedPreferences("User", MODE_PRIVATE).edit();
            prefEditor.remove("token");
            prefEditor.apply();
        }
    }

    // проверяем, есть ли сохраненный токен
    public static void checkToken(Context ctx) {
        if (ctx.getSharedPreferences("User", MODE_PRIVATE).contains("token")) {
            try {
                Requests.initializeStudent(ctx.getSharedPreferences("User", MODE_PRIVATE).getString("token", ""), ctx);
            } catch (Exception e) {

            }
        } else {
            Intent intent = new Intent(ctx, AuthActivity.class);
            ctx.startActivity(intent);
        }
    }
}
