package com.example.jenya.studentachievements;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
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

    public void search(View view) {
        String name = ((EditText) findViewById(R.id.textViewName)).getText().toString();
        String surname = ((EditText) findViewById(R.id.textViewSurname)).getText().toString();
        String patronymic = ((EditText) findViewById(R.id.textViewPatronymic)).getText().toString();
        String group = ((EditText) findViewById(R.id.textViewGroup)).getText().toString();

        if (name.isEmpty() && surname.isEmpty() && patronymic.isEmpty() && group.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ошибка!")
                    .setMessage("Заполните хотя бы одно поле!")
                    .setCancelable(false)
                    .setNegativeButton("Ок",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Intent intent = new Intent(this, SearchResultsActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("surname", surname);
            intent.putExtra("patronymic", patronymic);
            intent.putExtra("group", group);
            startActivity(intent);
        }
    }
}
