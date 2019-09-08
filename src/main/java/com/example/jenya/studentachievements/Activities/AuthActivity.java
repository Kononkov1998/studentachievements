package com.example.jenya.studentachievements.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.jenya.studentachievements.Models.User;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.Requests;

public class AuthActivity extends AppCompatActivity {

    private EditText login, pass;

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
    }

    //обработка кнопки "вход"
    public void enter(View view) {
        if (login.getText().toString().trim().equals("") || pass.getText().toString().trim().equals("")) {
            return;
        }
        try {
            Requests.getUserToken(new User(login.getText().toString(), pass.getText().toString()), this);
        } catch (Exception e) {

        }
    }
}
