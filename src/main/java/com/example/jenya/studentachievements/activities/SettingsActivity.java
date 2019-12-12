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
import com.example.jenya.studentachievements.Requests;
import com.example.jenya.studentachievements.SharedPreferencesActions;
import com.example.jenya.studentachievements.ThemeController;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.Visibility;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);

        RadioGroup rg = findViewById(R.id.radioGroup);

        UserInfo userInfo = UserInfo.getCurrentUser();
        String visibilityStr = userInfo.getVisibility();

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

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            int selectedParam = rg.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedParam);
            String selectedRadioButtonText = selectedRadioButton.getText().toString();
            Visibility visibility = new Visibility();
            switch (selectedRadioButtonText) {
                case "Все":
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
        });

        Switch themeSwitcher = findViewById(R.id.themeSwitcher);
        if (ThemeController.getCurrentTheme() == ThemeController.APP_THEME_DARK) {
            themeSwitcher.setChecked(true);
        }

        themeSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
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
        finish();
        startActivity(new Intent(this, getClass()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    public void exit(View view) {
        SharedPreferencesActions.delete("token", this);
        SharedPreferencesActions.delete("name", this);
        SharedPreferencesActions.delete("surname", this);
        SharedPreferencesActions.delete("patronymic", this);
        SharedPreferencesActions.delete("group", this);
        SharedPreferencesActions.delete("showCompleted", this);
        SharedPreferencesActions.delete("theme", this);
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void openFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, GradeActivity.class);
        startActivity(intent);
    }
}
