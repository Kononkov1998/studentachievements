package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.SharedPreferencesActions;
import com.example.jenya.studentachievements.adapters.AchievementsAdapter;
import com.example.jenya.studentachievements.comparators.AchievementsComparator;
import com.example.jenya.studentachievements.models.Achievement;
import com.example.jenya.studentachievements.models.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile);

        final ArrayList<Achievement> completedAchievements = new ArrayList<>();
        UserInfo userInfo = UserInfo.getCurrentUser();
        final ArrayList<Achievement> userAchievements = new ArrayList<>(Arrays.asList(userInfo.getAchievements()));
        int starsSum = 0;

        for (Achievement achievement : userAchievements)
        {
            int stars = achievement.getStars();
            if (stars != 0)
            {
                starsSum += stars;
                completedAchievements.add(achievement);
            }
        }

        Collections.sort(userAchievements, new AchievementsComparator());

        final AchievementsAdapter adapter = new AchievementsAdapter(this, userAchievements);
        final ListView listView = findViewById(R.id.list);
        View header = getLayoutInflater().inflate(R.layout.header_profile, listView, false);
        //((ImageView) header.findViewById(R.id.imageUser)).setImageResource(DataBase.currentUser.getImage());
        String headerText = userInfo.getFullName().getLastName() + "\n" + userInfo.getFullName().getFirstName() + "\n" + userInfo.getFullName().getPatronymic() + "\n" + userInfo.getGroup().getName();
        ((TextView) header.findViewById(R.id.textProfile)).setText(headerText);

        int completed = completedAchievements.size();
        int all = userAchievements.size();
        ((TextView) header.findViewById(R.id.achievementsTextView)).setText(String.format("Получено достижений: %d из %d (%d%%)", completed, all, Math.round((double)completed / (double)all * 100.0)));
        ((ProgressBar) header.findViewById(R.id.achievementsProgressBar)).setProgress(completed);
        ((ProgressBar) header.findViewById(R.id.achievementsProgressBar)).setMax(all);

        int allStars = all * 3;
        ((TextView) header.findViewById(R.id.starsTextView)).setText(String.format("Получено звезд: %d из %d (%d%%)", starsSum, allStars, Math.round((double) starsSum / (double) allStars * 100.0)));
        ((ProgressBar) header.findViewById(R.id.starsProgressBar)).setProgress(starsSum);
        ((ProgressBar) header.findViewById(R.id.starsProgressBar)).setMax(allStars);

        listView.addHeaderView(header);
        listView.setAdapter(adapter);

        CheckBox hideBox = findViewById(R.id.checkboxHide);
        hideBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
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
        });
        if (SharedPreferencesActions.check("showCompleted", this)) {
            hideBox.setChecked(true);
        }
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

    @Override
    protected void onStart(){
        super.onStart();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (((CheckBox) findViewById(R.id.checkboxHide)).isChecked()) {
            SharedPreferencesActions.save("showCompleted", "false", this);
        } else {
            SharedPreferencesActions.delete("showCompleted", this);
        }
        super.onSaveInstanceState(savedInstanceState);
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

    public void openGrade(View view) {
        Intent intent = new Intent(this, GradeActivity.class);
        startActivity(intent);
    }
}
