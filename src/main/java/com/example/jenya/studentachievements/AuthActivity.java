package com.example.jenya.studentachievements;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AuthActivity extends AppCompatActivity {

    private EditText login, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_auth);

        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passText);
        DataBase.fill();
    }

    public void enter(View view) {
        boolean auth = false;
        for (Student student : DataBase.students) {
            if (login.getText().toString().equals(student.getName()) && pass.getText().toString().equals("")) {
                Intent intent = new Intent(this, ProfileActivity.class);
                DataBase.currentUser = student;
                startActivity(intent);
                auth = true;
            }
        }
        if (!auth) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ошибка!")
                    .setMessage("Неверный логин или пароль!")
                    .setCancelable(false)
                    .setNegativeButton("Ок",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        /*if (login.getText().toString().equals("") && pass.getText().toString().equals("")) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ошибка!")
                    .setMessage("Неверный логин или пароль!")
                    .setCancelable(false)
                    .setNegativeButton("Ок",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }*/
    }
}
