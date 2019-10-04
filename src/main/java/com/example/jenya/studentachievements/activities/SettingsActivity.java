package com.example.jenya.studentachievements.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.Requests;
import com.example.jenya.studentachievements.SharedPreferencesActions;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.Visibility;

public class SettingsActivity extends AppCompatActivity {

    private Button btn;
    private RadioGroup rg;
    private RadioButton rb;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);

        btn = findViewById(R.id.applyButton);
        rg = findViewById(R.id.radioGroup);

        userInfo = UserInfo.getCurrentUser();
        String visibility = userInfo.getVisibility();

        switch (visibility)
        {
            case "all":
                rb = findViewById(R.id.radioButton0);
                rb.setChecked(true);
                break;
            case "me":
                rb = findViewById(R.id.radioButton4);
                rb.setChecked(true);
                break;
            case "groupmates":
                rb = findViewById(R.id.radioButton1);
                rb.setChecked(true);
                break;
        }

       /* radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton0:
                        DataBase.currentUser.setVisibility(0);
                        break;
                    case R.id.radioButton1:
                        DataBase.currentUser.setVisibility(1);
                        break;
                    case R.id.radioButton2:
                        DataBase.currentUser.setVisibility(2);
                        break;
                    case R.id.radioButton3:
                        DataBase.currentUser.setVisibility(3);
                        break;
                    case R.id.radioButton4:
                        DataBase.currentUser.setVisibility(4);
                        break;
                    default:
                        break;
                }
            }
        });*/
    }

    public void exit(View view){
        SharedPreferencesActions.delete("token", this);
        SharedPreferencesActions.delete("name", this);
        SharedPreferencesActions.delete("surname", this);
        SharedPreferencesActions.delete("patronymic", this);
        SharedPreferencesActions.delete("group", this);
        SharedPreferencesActions.delete("showCompleted", this);
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void apply(View view)
    {
        int selectedParam = rg.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = (RadioButton) findViewById(selectedParam);
        String selectedRadioButtonText = selectedRadioButton.getText().toString();
        Visibility visibility = new Visibility();
        switch (selectedRadioButtonText)
        {
            case "Все":
                visibility.setAll();
                break;
            case "Одногруппники":
                visibility.setGroupmates();
                break;
            case "Только я":
                visibility.setMe();
                break;
        }

        btn.getBackground().setAlpha(100);
        btn.setEnabled(false);
        Requests.getInstance().setVisibility(SharedPreferencesActions.read("token", this), visibility, this, btn);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void openFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    public void openGrade(View view)
    {
        Intent intent = new Intent(this, GradeActivity.class);
        startActivity(intent);
    }
}
