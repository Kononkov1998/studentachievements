package com.example.jenya.studentachievements.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jenya.studentachievements.Models.User;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.Requests;

public class AuthActivity extends AppCompatActivity
{
    private static Button btn;
    private EditText login, pass;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_auth);

        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passText);
        btn = findViewById(R.id.buttonEnter);
    }

    //обработка кнопки "вход"
    public void enter(View view)
    {
        if (login.getText().toString().trim().equals("") || pass.getText().toString().trim().equals(""))
        {
            Toast.makeText(this, "Введите логин и пароль!", Toast.LENGTH_LONG).show();
            return;
        }
        try
        {
            toggleButton();
            Requests.getUserToken(new User(login.getText().toString(), pass.getText().toString()), this);
        }
        catch (Exception e)
        {

        }
    }

    public static void toggleButton()
    {
        if(btn == null) return;
        if(btn.isEnabled())
        {
            btn.getBackground().setAlpha(100);
            btn.setEnabled(false);
            return;
        }
        btn.getBackground().setAlpha(255);
        btn.setEnabled(true);
    }
}
