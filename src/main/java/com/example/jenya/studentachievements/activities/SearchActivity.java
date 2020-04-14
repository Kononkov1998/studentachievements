package com.example.jenya.studentachievements.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jenya.studentachievements.utils.ButtonActions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.utils.ThemeController;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;

public class SearchActivity extends AbstractActivity {

    private Button btn;
    private static boolean isSearchSuccessful;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_search);

        btn = findViewById(R.id.searchBtn);
        String myGroup = UserInfo.getCurrentUser().getGroup().getName();
        String myFlow = myGroup.split("-")[0];

        final String[] groups = {
                myGroup,
                myFlow
        };

        ((AutoCompleteTextView) findViewById(R.id.textViewGroup)).setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, groups));
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
        isSearchSuccessful = false;
        if (!btn.isEnabled()) {
            ButtonActions.enableButton(btn);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (isSearchSuccessful) {
            clearFields();
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void search(View view) {
        String name = ((EditText) findViewById(R.id.textViewName)).getText().toString().trim();
        String surname = ((EditText) findViewById(R.id.textViewSurname)).getText().toString().trim();
        String patronymic = ((EditText) findViewById(R.id.textViewPatronymic)).getText().toString().trim();
        String group = ((AutoCompleteTextView) findViewById(R.id.textViewGroup)).getText().toString().trim();

        if (name.isEmpty() && surname.isEmpty() && patronymic.isEmpty() && group.isEmpty()) {
            Toast.makeText(this, "Заполните хотя бы одно поле!", Toast.LENGTH_LONG).show();
            return;
        }

        String fio = String.format("%s %s %s", surname, name, patronymic).trim();
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("fio", fio);
        intent.putExtra("group", group);
        startActivity(intent);
    }

    private void clearFields() {
        ((EditText) findViewById(R.id.textViewName)).setText("");
        ((EditText) findViewById(R.id.textViewSurname)).setText("");
        ((EditText) findViewById(R.id.textViewPatronymic)).setText("");
        ((AutoCompleteTextView) findViewById(R.id.textViewGroup)).setText("");
    }

    public static void searchSuccessful() {
        isSearchSuccessful = true;
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, SemestersActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openTop(View view) {
        Intent intent = new Intent(this, TopActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
