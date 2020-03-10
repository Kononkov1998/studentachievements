package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.models.Mark;
import com.example.jenya.studentachievements.models.Semester;
import com.example.jenya.studentachievements.utils.ThemeController;

import java.util.ArrayList;
import java.util.Locale;

public class DisciplinesActivity extends AbstractActivity {
    private ArrayList<Mark> marks;
    private View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_disciplines);

        mainView = findViewById(R.id.discipline_main);
        // узнаем в каком мы семестре
        int semesterNumber = getIntent().getIntExtra("semesterNumber", 0);
        marks = Semester.getSemesters().get(semesterNumber - 1).getMarks();
        // создаем карточку для каждого предмета
        initCards();
    }

    private void initCards() {
        if (marks.size() < 1) {
            mainView.setVisibility(View.INVISIBLE);
            return;
        }

        LinearLayout row = null;

        for (int i = 0; i < marks.size(); i++) {
            Mark currentMark = marks.get(i);
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
            TextView name = discipline.findViewById(R.id.discipline_name);
            TextView rating = discipline.findViewById(R.id.discipline_rating);
            TextView date = discipline.findViewById(R.id.discipline_date);

            name.setText(currentMark.getSubjectName());
            rating.setText(currentMark.getStrRating());
            date.setText(currentMark.getStrDate());

            //////////////////////////////////////////////////
            row.addView(discipline);

            // проверяем на последней итерации цикла количество оставшегося места под карточки
            if ((i == (marks.size() - 1)) && (marks.size() % 2 != 0)) {
                View disciplineInvisible = getLayoutInflater().inflate(R.layout.item_discipline, row, false);
                disciplineInvisible.setVisibility(View.INVISIBLE);
                disciplineInvisible.setEnabled(false);
                row.addView(disciplineInvisible);
            }

            if (i == 0) {
                setAsMain(discipline);
            }
        }
    }

    public void setAsMain(View view) {
        ////////////////////////////////////////////////// Пример изменения даты Пример выставления карточки как главной
        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 0);
        TextView textView = view.findViewById(R.id.discipline_name);
        String name = textView.getText().toString();
        Mark currentMark = findMark(name);
        TextView textViewName = mainView.findViewById(R.id.discipline_name);
        TextView textViewType = mainView.findViewById(R.id.discipline_type);
        TextView textViewTutor = mainView.findViewById(R.id.discipline_tutor);
        TextView textViewRating = mainView.findViewById(R.id.discipline_rating);
        TextView textViewHours = mainView.findViewById(R.id.discipline_hours);
        TextView textViewDate = mainView.findViewById(R.id.discipline_date);

        textViewName.setText(currentMark.getSubjectName());
        textViewType.setText(currentMark.getDisciplineType());
        textViewTutor.setText(currentMark.getStrTutor());
        textViewRating.setText(currentMark.getStrRating());
        String hours = String.format(Locale.getDefault(), "%d (%d з.е.)", currentMark.getHoursCount(), currentMark.getCreditUnits());
        textViewHours.setText(hours);
        textViewDate.setText(currentMark.getStrDate());
        //////////////////////////////////////////////////
    }


    private Mark findMark(String name) {
        for (Mark mark : marks) {
            if (mark.getSubjectName().equals(name)) {
                return mark;
            }
        }
        return null;
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
