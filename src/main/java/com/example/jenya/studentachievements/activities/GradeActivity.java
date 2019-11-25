package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.ThemeController;

public class GradeActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_grade);
        initButtons(29); // число семестров из запросов
    }

    private void initButtons(int semesters)
    {
        if(semesters < 1)
        {
            return;
        }

        LinearLayout row = null;

        for(int i = 0; i < semesters; i++)
        {
            // если итерация четная, то создаем горизонтальный список
            if(i % 2 == 0)
            {
                row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                row.setWeightSum((float)2);
                LinearLayout list = findViewById(R.id.semesters_list);
                list.addView(row);
            }

            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    0, // width
                    convertPixelsToDp(500), // height
                    1
            ));
            button.setText(String.format("Семестр %d", (i + 1)));
            button.setId(i + 1); // id кнопки === номер семестра

            row.addView(button);
        }
    }

    public int convertPixelsToDp(float px){
        return Math.round(px / ((float) this.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
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
}
