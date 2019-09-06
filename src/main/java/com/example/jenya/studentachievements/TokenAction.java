package com.example.jenya.studentachievements;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class TokenAction
{
    private static TokenAction tokenAction; // экземпляр класса
    private SharedPreferences settings; // настройки
    private Requests requests; // запросы

    private TokenAction()
    {
        settings = SplashScreenActivity.getAppContext().getSharedPreferences("User", MODE_PRIVATE);
        requests = Requests.getInstance();
    }

    public static TokenAction getInstance()
    {
        if(tokenAction == null)
        {
            return new TokenAction();
        }
        return tokenAction;
    }

    // сохраняем токен
    public void saveToken(String token) {
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("token", token);
        prefEditor.apply();
    }

    // удаляем токен
    public void deleteToken() {
        if (settings.contains("token")) {
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.remove("token");
            prefEditor.apply();
        }
    }

    // проверяем, есть ли сохраненный токен
    public void checkToken() {
        if (settings.contains("token")) {
            try {
                Toast.makeText(SplashScreenActivity.getAppContext(), "Ошауцкуцкзже.", Toast.LENGTH_LONG).show();
                requests.initializeStudent(settings.getString("token", ""));
            } catch (Exception e) {
                Toast.makeText(AuthActivity.getAppContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Intent intent = new Intent(SplashScreenActivity.getAppContext(), AuthActivity.class);
            SplashScreenActivity.getAppContext().startActivity(intent);
        }
    }
}
