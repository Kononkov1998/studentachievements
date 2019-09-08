package com.example.jenya.studentachievements.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jenya.studentachievements.TokenAction;

public class SplashScreenActivity extends AppCompatActivity
{
    private static Context mContext;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;
        Intent intent = new Intent(this, AuthActivity.class);

        // вызываем checkToken()
        TokenAction.getInstance().checkToken();
    }

    public static Context getAppContext() {
        return mContext;
    }
}
