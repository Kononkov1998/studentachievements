package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.Requests;
import com.example.jenya.studentachievements.SharedPreferencesActions;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.Visibility;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup rg;
    private RadioButton rb;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);

        rg = findViewById(R.id.radioGroup);

        userInfo = UserInfo.getCurrentUser();
        String visibilityStr = userInfo.getVisibility();

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
