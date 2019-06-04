package com.example.jenya.studentachievements;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile);

        final ArrayList<Achievement> completedAchievements = new ArrayList<>();
        UserInfo userInfo = UserInfo.getCurrentUser();
        final ArrayList<Achievement> userAchievements = new ArrayList<>(Arrays.asList(userInfo.getAchievements()));

        for (Achievement achievement : userAchievements) {
            if (achievement.getAchievementInfo().getGeneralProgress() == 100)
                completedAchievements.add(achievement);
        }

        Collections.sort(userAchievements, new AchievementsComparator());

        final AchievementsAdapter adapter = new AchievementsAdapter(this, userAchievements);
        final ListView listView = findViewById(R.id.list);
        View header = getLayoutInflater().inflate(R.layout.header_profile, listView, false);
        //((ImageView) header.findViewById(R.id.imageUser)).setImageResource(DataBase.currentUser.getImage());
        ((TextView) header.findViewById(R.id.textProfile)).setText(userInfo.getFullName().getLastName() + "\n" + userInfo.getFullName().getFirstName() + "\n" + userInfo.getFullName().getPatronymic() + "\n" + userInfo.getGroup().getName());
        listView.addHeaderView(header);
        listView.setAdapter(adapter);

        CheckBox hideBox = findViewById(R.id.checkboxHide);
        hideBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (Achievement achievement : completedAchievements) {
                       userAchievements.remove(achievement);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    userAchievements.addAll(completedAchievements);
                    Collections.sort(userAchievements, new AchievementsComparator());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        /*
        final ArrayList<StudentAchievement> completedAchievements = new ArrayList<>();
        for (StudentAchievement achievement : DataBase.currentUser.getAchievements()) {
            if (achievement.getProgress() == 100)
                completedAchievements.add(achievement);
        }
        Collections.sort(DataBase.currentUser.getAchievements(), new AchievementsComparator());

        final AchievementsAdapter adapter = new AchievementsAdapter(this, DataBase.currentUser.getAchievements());
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
                    Collections.sort(DataBase.currentUser.getAchievements(), new AchievementsComparator());
                    adapter.notifyDataSetChanged();
                }
            }
        });*/
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
