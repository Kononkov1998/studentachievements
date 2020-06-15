package com.example.jenya.studentachievements.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.adapters.TopFragmentPagerAdapter;
import com.example.jenya.studentachievements.fragments.TopFragment;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.models.Visibility;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.utils.ImageActions;
import com.example.jenya.studentachievements.utils.ImageViewActions;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;
import com.example.jenya.studentachievements.utils.ThemeController;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopActivity extends AbstractActivity {
    public static final int ALL_PAGE_NUMBER = 0;
    public static final int YEAR_PAGE_NUMBER = 1;
    public static final int DIRECTION_PAGE_NUMBER = 2;
    public static final int GROUP_PAGE_NUMBER = 3;
    private final static int OFFSCREEN_PAGE_LIMIT = 1;

    private SparseArray<TopFragment> registeredFragments = new SparseArray<>();
    private TopFragment currentFragment;
    private UserInfo currentUser;
    private CircleImageView avatar;
    private int px;
    private ImageView helpIcon;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_top);

        ImageViewActions.setActiveColor(this, findViewById(R.id.imageTop));
        currentUser = UserInfo.getCurrentUser();
        avatar = findViewById(R.id.imageUser);
        px = ImageActions.getAvatarSizeInPx(this);
        helpIcon = findViewById(R.id.helpIcon);

        ((TextView) findViewById(R.id.starsSum)).setText(String.valueOf(currentUser.getStarCount()));
        ((TextView) findViewById(R.id.textProfile))
                .setText(String.format(
                        "%s %s. %s.",
                        currentUser.getFullName().getLastName(),
                        currentUser.getFullName().getFirstName().charAt(0),
                        currentUser.getFullName().getPatronymic().charAt(0)
                ));

        ((TextView) findViewById(R.id.groupProfile)).setText(currentUser.getGroup().getName());

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager pager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new TopFragmentPagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(OFFSCREEN_PAGE_LIMIT);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                currentFragment = registeredFragments.get(position);
            }
        });

        tabLayout.setupWithViewPager(pager);
    }

    public void registerFragment(int position, TopFragment fragment) {
        registeredFragments.put(position, fragment);
        if (currentFragment == null) {
            currentFragment = fragment;
        }
    }

    private void setAvatarWithGlide() {
        GlideUrl glideUrl = new GlideUrl(String.format("%s/student/pic/%s", Requests.getInstance().getURL(), currentUser.getAvatar()), new LazyHeaders.Builder()
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

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);

        if (currentUser.getAvatar() != null) {
            setAvatarWithGlide();
        }
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
        currentFragment.getListView().smoothScrollToPosition(0);
    }

    public void openHelp(View view) {
        new AlertDialog.Builder(this, R.style.AlertDialogDanger)
                .setMessage(R.string.rating_info)
                .setCancelable(true)
                .setNeutralButton(R.string.ok, null)
                .show();
    }
}
