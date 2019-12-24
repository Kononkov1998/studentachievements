package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.SharedPreferencesActions;
import com.example.jenya.studentachievements.ThemeController;

public class SearchActivity extends AppCompatActivity {

    private Button btn;
    private static boolean isSearchSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_search);

        isSearchSuccessful = false;
        ((EditText) findViewById(R.id.textViewName)).setText(SharedPreferencesActions.read("name", this));
        ((EditText) findViewById(R.id.textViewSurname)).setText(SharedPreferencesActions.read("surname", this));
        ((EditText) findViewById(R.id.textViewPatronymic)).setText(SharedPreferencesActions.read("patronymic", this));
        ((EditText) findViewById(R.id.textViewGroup)).setText(SharedPreferencesActions.read("group", this));

        btn = findViewById(R.id.searchBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
        if (!btn.isEnabled()) {
            btn.getBackground().setAlpha(255);
            btn.setEnabled(true);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (isSearchSuccessful) {
            SharedPreferencesActions.delete("name", this);
            SharedPreferencesActions.delete("surname", this);
            SharedPreferencesActions.delete("patronymic", this);
            SharedPreferencesActions.delete("group", this);

        } else {
            SharedPreferencesActions.save("name", ((EditText) findViewById(R.id.textViewName)).getText().toString(), this);
            SharedPreferencesActions.save("surname", ((EditText) findViewById(R.id.textViewSurname)).getText().toString(), this);
            SharedPreferencesActions.save("patronymic", ((EditText) findViewById(R.id.textViewPatronymic)).getText().toString(), this);
            SharedPreferencesActions.save("group", ((EditText) findViewById(R.id.textViewGroup)).getText().toString(), this);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void search(View view) {
        String name = ((EditText) findViewById(R.id.textViewName)).getText().toString().trim();
        String surname = ((EditText) findViewById(R.id.textViewSurname)).getText().toString().trim();
        String patronymic = ((EditText) findViewById(R.id.textViewPatronymic)).getText().toString().trim();
        String group = ((EditText) findViewById(R.id.textViewGroup)).getText().toString().trim();

        if (name.isEmpty() && surname.isEmpty() && patronymic.isEmpty() && group.isEmpty()) {
            Toast.makeText(this, "Заполните хотя бы одно поле!", Toast.LENGTH_LONG).show();
            return;
        }

        String fio = String.format("%s %s %s", surname, name, patronymic);
        btn.getBackground().setAlpha(100);
        btn.setEnabled(false);
        Requests.getInstance().studentSearch(group, fio.trim(), this, btn);
    }

    public static void searchSuccessful() {
        isSearchSuccessful = true;
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, SemestersActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
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
