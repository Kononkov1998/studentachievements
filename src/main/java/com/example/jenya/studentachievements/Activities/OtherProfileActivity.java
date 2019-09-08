package com.example.jenya.studentachievements.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jenya.studentachievements.R;

public class OtherProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_otherprofile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        ListView listView = findViewById(R.id.list);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");
        String patronymic = intent.getStringExtra("patronymic");
        String group = intent.getStringExtra("group");
        final Student student = DataBase.searchStudent(name, surname, patronymic, group);

        View header = getLayoutInflater().inflate(R.layout.header_otherprofile, listView, false);
        ((ImageView) header.findViewById(R.id.imageUser)).setImageResource(student.getImage());
        ((TextView) header.findViewById(R.id.textProfile)).setText(student.getSurname() + "\n" + student.getName() + "\n" + student.getPatronymic() + "\n" + student.getGroup());
        listView.addHeaderView(header);

        final ArrayList<StudentAchievement> completedAchievements = new ArrayList<>();
        for (StudentAchievement achievement : student.getAchievements()) {
            if (achievement.getProgress() == 100)
                completedAchievements.add(achievement);
        }
        Collections.sort(student.getAchievements(), new AchievementsComparator());
        final AchievementsAdapter adapter = new AchievementsAdapter(this, student.getAchievements());
        listView.setAdapter(adapter);

        CheckBox hideBox = findViewById(R.id.checkboxHide);
        hideBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (StudentAchievement achievement : completedAchievements) {
                        student.getAchievements().remove(achievement);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    student.getAchievements().addAll(completedAchievements);
                    Collections.sort(student.getAchievements(), new AchievementsComparator());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        final CheckBox checkBox = header.findViewById(R.id.checkboxFavorite);
        if (DataBase.currentUser.getFavorites().contains(student)) {
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
}
