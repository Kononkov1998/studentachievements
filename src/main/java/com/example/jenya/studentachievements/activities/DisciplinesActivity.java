package com.example.jenya.studentachievements.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.adapters.DisciplinesAdapter;
import com.example.jenya.studentachievements.models.Mark;
import com.example.jenya.studentachievements.models.Semester;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.utils.ThemeController;

import java.util.ArrayList;

public class DisciplinesActivity extends AbstractActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_disciplines);

        // узнаем в каком мы семестре
        int semesterNumber = getIntent().getIntExtra("semesterNumber", 0);
        Semester currentSemester = Semester.getSemesters().get(semesterNumber - 1);
        ArrayList<Mark> marks = currentSemester.getMarks();
        ListView listView = findViewById(R.id.list);
        DisciplinesAdapter adapter = new DisciplinesAdapter(this, marks);
        listView.setAdapter(adapter);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setEnabled(false);
        if (marks.isEmpty()) {
            swipeRefreshLayout.setRefreshing(true);
            Requests.getInstance().getMarks(this, currentSemester, adapter, swipeRefreshLayout);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    public void openFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, SemestersActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openTop(View view) {
        Intent intent = new Intent(this, TopActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
