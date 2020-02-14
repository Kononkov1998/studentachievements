package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.utils.ThemeController;

import java.util.Locale;

public class DisciplinesActivity extends AbstractActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_disciplines);

        Bundle bundle = getIntent().getExtras();
        // узнаем в каком мы семестре
        String semester = bundle.getString("semester");
        // создаем карточку для каждого предмета
        initCards(4);
    }

    private void initCards(int disciplines) {
        if (disciplines < 1) {
            return;
        }

        LinearLayout row = null;

        for (int i = 0; i < disciplines; i++) {
            // если итерация делится на 2, то создаем горизонтальный список
            if (i % 2 == 0) {
                row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                row.setWeightSum((float) 2);
                LinearLayout list = findViewById(R.id.disciplines_list);
                list.addView(row);
            }

            View discipline = getLayoutInflater().inflate(R.layout.item_discipline, row, false);
            ////////////////////////////////////////////////// Пример изменения даты
            TextView date = discipline.findViewById(R.id.discipline_date);
            date.setText(String.format(Locale.getDefault(), "%d.%d.%d", i, i, i));
            //////////////////////////////////////////////////
            row.addView(discipline);

            // проверяем на последней итерации цикла количество оставшегося места под карточки
            if ((i == (disciplines - 1)) && (disciplines % 2 != 0)) {
                View disciplineInvisible = getLayoutInflater().inflate(R.layout.item_discipline, row, false);
                disciplineInvisible.setVisibility(View.INVISIBLE);
                disciplineInvisible.setEnabled(false);
                row.addView(disciplineInvisible);
            }
        }
    }

    public void setAsMain(View view) {
        ////////////////////////////////////////////////// Пример изменения даты Пример выставления карточки как главной
        TextView textView = view.findViewById(R.id.discipline_date);
        String date = textView.getText().toString();
        TextView textViewMain = findViewById(R.id.discipline_date);
        textViewMain.setText(date);
        //////////////////////////////////////////////////
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
