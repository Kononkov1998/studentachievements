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
    private Requests requests; // запросы
    private static Context mContext; // контекст

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_auth);

        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passText);

        requests = Requests.getInstance();
        mContext = this;
    }

    //обработка кнопки "вход"
    public void enter(View view) {
        if (login.getText().toString().trim().equals("") || pass.getText().toString().trim().equals("")) {
            return;
        }
        try {
            requests.getUserToken(new User(login.getText().toString(), pass.getText().toString()));
        } catch (Exception e) {
            
        }
    }

    // получить контекст
    public static Context getAppContext() {
        return mContext;
    }
}
