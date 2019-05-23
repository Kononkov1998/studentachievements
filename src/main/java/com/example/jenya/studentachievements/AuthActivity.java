package com.example.jenya.studentachievements;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity {

    private EditText login, pass;
    private static SharedPreferences settings; // настройки
    static boolean auth = false;
    private Requests requests; // запросы
    private static Context mContext; // контекст

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_auth);

        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passText);

        requests = Requests.getInstance();
        mContext = this;
        settings = getSharedPreferences("User", MODE_PRIVATE);
        DataBase.fill();
    }

    //обработка кнопки "вход"
    public void enter(View view)
    {
        try
        {
            requests.getUserToken(new User(login.getText().toString(), pass.getText().toString()));
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // сохраняем токен
    public static void saveToken(String token)
    {
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("token", token);
        prefEditor.apply();
    }

    // получить контекст
    public static Context getAppContext()
    {
        return mContext;
    }

    /*public void enter(View view) {
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
    }*/
}
