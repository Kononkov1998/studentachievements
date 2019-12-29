package com.example.jenya.studentachievements.activities;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.ThemeController;
import com.example.jenya.studentachievements.adapters.UsersAdapter;
import com.example.jenya.studentachievements.models.UserInfo;

import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AbstractActivity {
    @SuppressWarnings("FieldCanBeLocal")
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        int position = data.getIntExtra("position", -1);

        if (position != -1) {
            postponeEnterTransition();

            View view = listView.getChildAt(position).findViewById(R.id.layout);
            setExitSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    sharedElements.put(view.getTransitionName(), view);
                }
            });

            final View decor = getWindow().getDecorView();
            decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    decor.getViewTreeObserver().removeOnPreDrawListener(this);
                    startPostponedEnterTransition();
                    return true;
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        //recreate();
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, SemestersActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openFavorites(View view) {
        listView.smoothScrollToPosition(0);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
