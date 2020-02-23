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
import com.example.jenya.studentachievements.adapters.UsersAdapter;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.utils.ThemeController;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AbstractActivityWithUsers {
    @SuppressWarnings("FieldCanBeLocal")
    private UsersAdapter adapter;
    private ListView listView;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_favorites);
        adapter = new UsersAdapter(this, UserInfo.getCurrentUser().getFavouriteStudents());
        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        postponeEnterTransition();
        int sharedElementPosition = data.getIntExtra("position", -1);
        if (sharedElementPosition != -1) {
            View listElement = listView.getChildAt(sharedElementPosition);
            if (listElement != null) {
                View sharedView = listElement.findViewById(R.id.layout);
                setExitSharedElementCallback(new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        sharedElements.clear();
                        sharedElements.put(sharedView.getTransitionName(), sharedView);
                    }
                });
            }
        }

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

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
        hud.show();
        Requests.getInstance().updateFavourites(this, hud, adapter);
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

    public void openTop(View view) {
        Intent intent = new Intent(this, TopActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
