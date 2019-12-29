package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jenya.studentachievements.ImageActions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.ThemeController;

import java.util.Locale;

public class SemestersActivity extends AbstractActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_grade);
        initButtons(9); // число семестров из запроса
    }

    private void initButtons(int semesters) {
        if (semesters < 1) {
            return;
        }

        LinearLayout row = null;

        for (int i = 0; i < semesters; i++) {
            // если итерация делится на 4, то создаем горизонтальный список
            if (i % 4 == 0) {
                row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                row.setWeightSum((float) 4);
                LinearLayout list = findViewById(R.id.semesters_list);
                list.addView(row);
            }

            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, // width
                    0, // height
                    1 // weight
            );
            params.setMargins(
                    (int) ImageActions.pxFromDp(15, this),
                    (int) ImageActions.pxFromDp(15, this),
                    (int) ImageActions.pxFromDp(15, this),
                    (int) ImageActions.pxFromDp(15, this)
            );
            button.setLayoutParams(params);
            button.setText(String.format(Locale.getDefault(), "%d", (i + 1)));
            button.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            button.setTextSize(20);
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_semester_shape));

            // здесь открываем новую активити с дисциплинами, отпрвляя номер семестра
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(getApplicationContext(), DisciplinesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("semester", String.valueOf(button.getText()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            row.addView(button);

            ViewTreeObserver vto = button.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    button.getViewTreeObserver().removeOnPreDrawListener(this); // удаляем листенер, иначе уйдём в бесконечный цикл
                    params.height = button.getWidth();
                    button.setLayoutParams(params);
                    return true;
                }
            });

            // проверяем на последней итерации цикла количество оставшегося места под кнопки
            if ((i == (semesters - 1)) && (semesters % 4 != 0))
            {
                int freeButtons = 4 - (semesters % 4);
                for (int j = 0; j < freeButtons; j++) {
                    Button buttonInvisible = new Button(this);
                    buttonInvisible.setLayoutParams(params);
                    buttonInvisible.setVisibility(View.INVISIBLE);
                    buttonInvisible.setEnabled(false);

                    row.addView(buttonInvisible);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //recreate();
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
        startActivity(intent);
    }

    public void openFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
        startActivity(intent);
    }
}
