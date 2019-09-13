package com.example.jenya.studentachievements.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.TokenAction;

public class SplashScreenActivity extends AppCompatActivity
{
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // вызываем checkToken()
        TokenAction.checkToken(this);
    }
}
