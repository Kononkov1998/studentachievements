package com.example.jenya.studentachievements.activities;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.ThemeController;
import com.example.jenya.studentachievements.adapters.UsersAdapter;
import com.example.jenya.studentachievements.models.UserInfo;

import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AbstractActivity {

    private UsersAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_favorites);
        adapter = new UsersAdapter(this, UserInfo.getCurrentUser().getFavouriteStudents());
        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

    }

    private void setSharedElementCallback(final View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setExitSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names,
                                                Map<String, View> sharedElements) {
                    names.clear();
                    sharedElements.clear();
                    names.add(view.getTransitionName());
                    sharedElements.put(view.getTransitionName(), view);
                    //Toast.makeText(getApplicationContext(), "callback1", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int position = data.getIntExtra("position", -1);

            if (position != -1) {
                View view = listView.getChildAt(position).findViewById(R.id.layout);
                //Toast.makeText(getApplicationContext(), "onactivityresult", Toast.LENGTH_SHORT).show();
                //setSharedElementCallback(view);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(), "BOOM", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, GradeActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
        startActivity(intent);
    }

    public void openFavorites(View view) {
        listView.smoothScrollToPosition(0);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);;
        startActivity(intent);
    }
}
