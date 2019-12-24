package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.SharedPreferencesActions;
import com.example.jenya.studentachievements.ThemeController;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.Visibility;

public class SettingsActivity extends AppCompatActivity {

    private Switch themeSwitcher;
    private RadioGroup rg;
    private RadioGroup.OnCheckedChangeListener listener;
    @SuppressWarnings("FieldCanBeLocal")
    private RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);

        listener = (radioGroup, i) -> {
            RadioButton selectedRadioButton = findViewById(i);
            String selectedRadioButtonText = selectedRadioButton.getText().toString();
            Visibility visibility = new Visibility();
            switch (selectedRadioButtonText) {
                case "Все пользователи":
                    visibility.setAll();
                    break;
                case "Одногруппники":
                    visibility.setGroupmates();
                    break;
                case "Только я":
                    visibility.setMe();
                    break;
            }
            Requests.getInstance().setVisibility(visibility, this);
        };

        rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(listener);

        themeSwitcher = findViewById(R.id.themeSwitcher);
        themeSwitcher.setOnClickListener((buttonView) -> {
            if (themeSwitcher.isChecked()) {
                changeToTheme(ThemeController.APP_THEME_DARK);
                SharedPreferencesActions.save("theme", "dark", this);
            } else {
                changeToTheme(ThemeController.APP_THEME_LIGHT);
                SharedPreferencesActions.save("theme", "light", this);
            }
        });
    }

    private void changeToTheme(int theme) {
        ThemeController.setCurrentTheme(theme);
        Intent i = new Intent("recreate"); // the two action strings MUST be same
        sendBroadcast(i);
        finish();
        startActivity(new Intent(this, getClass()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);

        rg.setOnCheckedChangeListener(null);
        String visibilityStr = UserInfo.getCurrentUser().getVisibility();
        switch (visibilityStr) {
            case "all":
                RadioButton rb = findViewById(R.id.radioButton0);
                rb.setChecked(true);
                break;
            case "me":
                rb = findViewById(R.id.radioButton4);
                rb.setChecked(true);
                break;
            case "groupmates":
                rb = findViewById(R.id.radioButton1);
                rb.setChecked(true);
                break;
        }
        rg.setOnCheckedChangeListener(listener);

        if (ThemeController.getCurrentTheme() == ThemeController.APP_THEME_DARK) {
            themeSwitcher.setChecked(true);
        } else {
            themeSwitcher.setChecked(false);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void exit(View view) {
        SharedPreferencesActions.delete("token", this);
        SharedPreferencesActions.delete("name", this);
        SharedPreferencesActions.delete("surname", this);
        SharedPreferencesActions.delete("patronymic", this);
        SharedPreferencesActions.delete("group", this);
        SharedPreferencesActions.delete("showCompleted", this);
        SharedPreferencesActions.delete("theme", this);
        ThemeController.setCurrentTheme(ThemeController.APP_THEME_LIGHT);
        Intent intent = new Intent(this, SplashScreenActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, SemestersActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
