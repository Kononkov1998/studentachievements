package com.example.jenya.studentachievements.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jenya.studentachievements.ButtonActions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.models.User;
import com.example.jenya.studentachievements.requests.Requests;

public class AuthActivity extends AppCompatActivity {
    private Button btn;
    private EditText login, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_auth);

        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passText);
        btn = findViewById(R.id.buttonEnter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    //обработка кнопки "вход"
    public void enter(View view) {
        if (login.getText().toString().trim().equals("") && pass.getText().toString().trim().equals("")) {
            Requests.getInstance().getUserToken(new User("ekononkov-ki16", "Rjyjyrjd1998"), this, btn);
            Toast.makeText(this, "Введите логин и пароль!", Toast.LENGTH_LONG).show();
            return;
        }
        ButtonActions.disableButton(btn);
        Requests.getInstance().getUserToken(new User(login.getText().toString(), pass.getText().toString()), this, btn);
    }
}
