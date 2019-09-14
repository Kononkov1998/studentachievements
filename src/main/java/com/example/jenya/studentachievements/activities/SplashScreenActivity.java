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
        if (SharedPreferencesActions.check("token", this)) {
            Requests.getInstance().initializeStudentFromSplashScreen(SharedPreferencesActions.read("token", this), this);
        } else {
            Intent intent = new Intent(this, AuthActivity.class);
            this.startActivity(intent);
        }
    }
}
