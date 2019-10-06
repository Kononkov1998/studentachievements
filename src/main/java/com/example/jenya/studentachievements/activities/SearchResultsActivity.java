package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.models.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_searchresults);

        ArrayList<UserInfo> students = getIntent().getParcelableArrayListExtra("students");
        Log.i("Опачки2", Arrays.toString(students.toArray()));
    }

    @Override
    protected void onStart() {
        super.onStart();

/*
        ArrayList<UserInfo> students = DataBase.search(name, surname, patronymic, group);

        final UsersAdapter adapter = new UsersAdapter(this, students);
        final ListView listView = findViewById(R.id.list);
        if (students.isEmpty())
            listView.addHeaderView(getLayoutInflater().inflate(R.layout.header_searchresults, listView, false));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
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
