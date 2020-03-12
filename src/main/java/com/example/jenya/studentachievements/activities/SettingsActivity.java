package com.example.jenya.studentachievements.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;
import com.example.jenya.studentachievements.utils.ThemeController;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.Visibility;
import com.example.jenya.studentachievements.requests.Requests;

public class SettingsActivity extends AbstractActivity {

    private Switch themeSwitcher;
    private RadioGroup rg;
    private RadioGroup.OnCheckedChangeListener rgListener;
    @SuppressWarnings("FieldCanBeLocal")
    private RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);

        rgListener = (radioGroup, i) -> {
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
        rg.setOnCheckedChangeListener(rgListener);

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
        Intent i = new Intent("recreateIsNeeded");
        sendBroadcast(i);
        i = new Intent("recreate");
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
                rb = findViewById(R.id.radioButton0);
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
        rg.setOnCheckedChangeListener(rgListener);

        if (ThemeController.getCurrentTheme() == ThemeController.APP_THEME_DARK) {
            themeSwitcher.setChecked(true);
        } else {
            themeSwitcher.setChecked(false);
        }
    }

    public void exit(View view) {
        SharedPreferencesActions.delete("token", this);
        SharedPreferencesActions.delete("showCompleted", this);
        SharedPreferencesActions.delete("theme", this);
        ThemeController.setCurrentTheme(ThemeController.APP_THEME_LIGHT);
        Intent intent = new Intent(this, SplashScreenActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void deleteAccount(View view)
    {
        new AlertDialog.Builder(this, R.style.AlertDialogDanger)
                .setMessage("Вы уверены, что хотите удалить свой аккаунт?")
                .setCancelable(false)
                .setPositiveButton("Да", (dialog, id) -> Requests.getInstance().deleteAccount(SettingsActivity.this))
                .setNegativeButton("Нет", null)
                .show();
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

    public void openTop(View view) {
        Intent intent = new Intent(this, TopActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
