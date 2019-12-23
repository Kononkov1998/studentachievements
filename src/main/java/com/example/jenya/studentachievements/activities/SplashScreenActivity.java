package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.SharedPreferencesActions;
import com.example.jenya.studentachievements.ThemeController;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferencesActions.delete("name", this);
        SharedPreferencesActions.delete("surname", this);
        SharedPreferencesActions.delete("patronymic", this);
        SharedPreferencesActions.delete("group", this);

        if (SharedPreferencesActions.check("token", this)) {
            Requests.getInstance().getUserInfoFromSplashScreen(this);
        } else {
            Intent intent = new Intent(this, AuthActivity.class);
            this.startActivity(intent);
        }

        if (SharedPreferencesActions.read("theme", this).equals("light")) {
            ThemeController.setCurrentTheme(ThemeController.APP_THEME_LIGHT);
        } else if (SharedPreferencesActions.read("theme", this).equals("dark")) {
            ThemeController.setCurrentTheme(ThemeController.APP_THEME_DARK);
        }
    }
}
