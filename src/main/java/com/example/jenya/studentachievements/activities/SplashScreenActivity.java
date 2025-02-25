package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;
import com.example.jenya.studentachievements.utils.ThemeController;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SharedPreferencesActions.read("theme", this).equals("light")) {
            ThemeController.setCurrentTheme(ThemeController.APP_THEME_LIGHT);
        } else if (SharedPreferencesActions.read("theme", this).equals("dark")) {
            ThemeController.setCurrentTheme(ThemeController.APP_THEME_DARK);
        }
        ThemeController.onActivityCreateSetTheme(this);

        super.onCreate(savedInstanceState);

        if (SharedPreferencesActions.check("token", this)) {
            Requests.getInstance().getUserInfoFromSplashScreen(this);
        } else {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }
    }
}
