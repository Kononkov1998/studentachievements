package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.SharedPreferencesActions;
import com.example.jenya.studentachievements.ThemeController;
import com.example.jenya.studentachievements.adapters.UsersAdapter;
import com.example.jenya.studentachievements.models.UserInfo;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_searchresults);

        ArrayList<UserInfo> students = getIntent().getParcelableArrayListExtra("students");

        adapter = new UsersAdapter(this, students);
        final ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        SharedPreferencesActions.delete("name", this);
        SharedPreferencesActions.delete("surname", this);
        SharedPreferencesActions.delete("patronymic", this);
        SharedPreferencesActions.delete("group", this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, GradeActivity.class);
        startActivity(intent);
    }

    public void openFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
