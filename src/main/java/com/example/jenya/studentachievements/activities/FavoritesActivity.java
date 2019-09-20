package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.jenya.studentachievements.adapters.UsersAdapter;
import com.example.jenya.studentachievements.R;

public class FavoritesActivity extends AppCompatActivity {

    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_favorites);
        //adapter = new UsersAdapter(this, DataBase.currentUser.getFavorites());
        final ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openGrade(View view)
    {
        Intent intent = new Intent(this, GradeActivity.class);
        startActivity(intent);
    }
}
