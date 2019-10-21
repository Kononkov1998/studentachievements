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

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.adapters.AchievementsAdapter;
import com.example.jenya.studentachievements.comparators.AchievementsComparator;
import com.example.jenya.studentachievements.models.Achievement;
import com.example.jenya.studentachievements.models.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class OtherProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_otherprofile);

        UserInfo userInfo = getIntent().getParcelableExtra("otherStudent");
        final ArrayList<Achievement> completedAchievements = new ArrayList<>();
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
        View header = getLayoutInflater().inflate(R.layout.header_otherprofile, listView, false);
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

        supportStartPostponedEnterTransition();
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

       /* final CheckBox checkBox = header.findViewById(R.id.checkboxFavorite);
        if (userInfo.getFavorites().contains(student)) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DataBase.currentUser.getFavorites().add(student);
                } else {
                    DataBase.currentUser.getFavorites().remove(student);
                }
            }
        });*/
    }

    @Override
    protected void onStart(){
        super.onStart();
        overridePendingTransition(0, 0);
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
