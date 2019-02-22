package com.example.jenya.studentachievements;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile);

        final ArrayList<StudentAchievement> completedAchievements = new ArrayList<>();
        for (StudentAchievement achievement : DataBase.currentUser.getAchievements()) {
            if (achievement.getProgress() == 100)
                completedAchievements.add(achievement);
        }
        Collections.sort(DataBase.currentUser.getAchievements(), new StudentAchievementsComparator());

        final StudentAchievementsAdapter adapter = new StudentAchievementsAdapter(this, DataBase.currentUser.getAchievements());
        final ListView listView = findViewById(R.id.list);
        View header = getLayoutInflater().inflate(R.layout.header_profile, listView, false);
        ((ImageView) header.findViewById(R.id.imageUser)).setImageResource(DataBase.currentUser.getImage());
        ((TextView) header.findViewById(R.id.textProfile)).setText(DataBase.currentUser.getSurname() + "\n" + DataBase.currentUser.getName() + "\n" + DataBase.currentUser.getPatronymic() + "\n" + DataBase.currentUser.getGroup());
        listView.addHeaderView(header);
        listView.setAdapter(adapter);

        CheckBox hideBox = findViewById(R.id.checkboxHide);
        hideBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (StudentAchievement achievement : completedAchievements) {
                        DataBase.currentUser.getAchievements().remove(achievement);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    DataBase.currentUser.getAchievements().addAll(completedAchievements);
                    Collections.sort(DataBase.currentUser.getAchievements(), new StudentAchievementsComparator());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
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
