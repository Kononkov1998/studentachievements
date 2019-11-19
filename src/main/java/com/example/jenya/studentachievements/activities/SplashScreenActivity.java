package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jenya.studentachievements.Requests;
import com.example.jenya.studentachievements.SharedPreferencesActions;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferencesActions.delete("name", this);
        SharedPreferencesActions.delete("surname", this);
        SharedPreferencesActions.delete("patronymic", this);
        SharedPreferencesActions.delete("group", this);

        if (SharedPreferencesActions.check("token", this)) {
            Requests.getInstance().getUserInfoFromSplashScreen(this);
        } else {
            Intent intent = new Intent(this, AuthActivity.class);
            this.startActivity(intent);
        }
    }
}
