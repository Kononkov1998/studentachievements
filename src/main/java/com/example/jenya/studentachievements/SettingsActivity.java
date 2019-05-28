package com.example.jenya.studentachievements;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

       /* radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton0:
                        DataBase.currentUser.setVisibility(0);
                        break;
                    case R.id.radioButton1:
                        DataBase.currentUser.setVisibility(1);
                        break;
                    case R.id.radioButton2:
                        DataBase.currentUser.setVisibility(2);
                        break;
                    case R.id.radioButton3:
                        DataBase.currentUser.setVisibility(3);
                        break;
                    case R.id.radioButton4:
                        DataBase.currentUser.setVisibility(4);
                        break;
                    default:
                        break;
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
}
