package com.example.jenya.studentachievements.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.fragments.TopFragment;
import com.example.jenya.studentachievements.models.Achievement;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.utils.ImageActions;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;
import com.example.jenya.studentachievements.utils.ThemeController;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopActivity extends AbstractActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private UserInfo userInfo;
    @SuppressWarnings("FieldCanBeLocal")
    private CircleImageView avatar;

    static final int PAGE_COUNT = 4;

    ViewPager pager;
    PagerAdapter pagerAdapter;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_top);

        userInfo = UserInfo.getCurrentUser();
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") ArrayList<Achievement> completedAchievements = new ArrayList<>();
        @SuppressWarnings("unchecked") final ArrayList<Achievement> userAchievements = (ArrayList<Achievement>) userInfo.getAchievements().clone();
        int starsSum = 0;

        for (Achievement achievement : userAchievements) {
            int stars = achievement.getStars();
            if (stars != 0) {
                starsSum += stars;
                completedAchievements.add(achievement);
            }
        }

        ((TextView) findViewById(R.id.starsSum)).setText(String.valueOf(starsSum));

        avatar = findViewById(R.id.imageUser);
        ((TextView) findViewById(R.id.textProfile))
                .setText(String.format(
                        "%s\n%s\n%s",
                        userInfo.getFullName().getLastName(),
                        userInfo.getFullName().getFirstName(),
                        userInfo.getFullName().getPatronymic()
                ));

        ((TextView) findViewById(R.id.groupProfile))
                .setText(userInfo.getGroup().getName());

        if (userInfo.getAvatar() != null) {
            int px = ImageActions.getAvatarSizeInPx(this);

            GlideUrl glideUrl = new GlideUrl(String.format("%s/student/pic/%s", Requests.getInstance().getURL(), userInfo.getAvatar()), new LazyHeaders.Builder()
                    .addHeader("Authorization", SharedPreferencesActions.read("token", this))
                    .build());

            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().timeout(30000))
                    .load(glideUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.profile)
                    .override(px, px)
                    .into(avatar);
        }


        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        Context ctx = this;
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ctx, "onPageSelected, position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TopFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    public void openOverallTop(View view) {
        pager.setCurrentItem(0, true);
    }

    public void openCourseTop(View view) {
        pager.setCurrentItem(1, true);
    }

    public void openStreamTop(View view) {
        pager.setCurrentItem(2, true);
    }

    public void openGroupTop(View view) {
        pager.setCurrentItem(3, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, SemestersActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openTop(View view) {
        ((ListView) pager.findViewById(R.id.list)).smoothScrollToPosition(0);
    }
}
