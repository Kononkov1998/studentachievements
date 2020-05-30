package com.example.jenya.studentachievements.activities;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.adapters.TopFragmentPagerAdapter;
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
    private final static int OFFSCREEN_PAGE_LIMIT = 3;
    private final static int CARD_VIEW_SHADOW_HEIGHT = 2;

    private ArrayList<TopFragment> registeredFragments = new ArrayList<>();
    private TopFragment currentFragment;
    private CardView cardProfile;
    private LinearLayout container;
    private RelativeLayout.LayoutParams containerParams;
    private ViewGroup.MarginLayoutParams cardProfileParams;

    private int targetTopMargin = -1;
    private int startTopMargin = -1;
    public boolean isAnimating = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_top);

        UserInfo userInfo = UserInfo.getCurrentUser();
        //noinspection unchecked
        final ArrayList<Achievement> userAchievements = (ArrayList<Achievement>) userInfo.getAchievements().clone();
        int starsSum = 0;

        for (Achievement achievement : userAchievements) {
            int stars = achievement.getStars();
            if (stars != 0) {
                starsSum += stars;
            }
        }

        ((TextView) findViewById(R.id.starsSum)).setText(String.valueOf(starsSum));

        CircleImageView avatar = findViewById(R.id.imageUser);
        ((TextView) findViewById(R.id.textProfile))
                .setText(String.format(
                        "%s\n%s\n%s",
                        userInfo.getFullName().getLastName(),
                        userInfo.getFullName().getFirstName(),
                        userInfo.getFullName().getPatronymic()
                ));

        ((TextView) findViewById(R.id.groupProfile)).setText(userInfo.getGroup().getName());

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

        cardProfile = findViewById(R.id.cardProfile);
        container = findViewById(R.id.container);

        containerParams = (RelativeLayout.LayoutParams) container.getLayoutParams();
        cardProfileParams = (ViewGroup.MarginLayoutParams) cardProfile.getLayoutParams();
        startTopMargin = containerParams.topMargin;

        ViewPager pager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new TopFragmentPagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(OFFSCREEN_PAGE_LIMIT);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                currentFragment = registeredFragments.get(position);
            }
        });
    }

    public void animateHeader(boolean headerVisible) {
        if (targetTopMargin == -1) {
            targetTopMargin = -cardProfile.getHeight() - cardProfileParams.bottomMargin - CARD_VIEW_SHADOW_HEIGHT;
        }

        ValueAnimator animExit = createTopMarginAnimation(startTopMargin, targetTopMargin);
        ValueAnimator animEnter = createTopMarginAnimation(targetTopMargin, startTopMargin);

        if (headerVisible) {
            animExit.start();
        } else {
            animEnter.start();

        }
    }

    private ValueAnimator createTopMarginAnimation(int from, int to) {
        ValueAnimator anim = ValueAnimator.ofInt(from, to);

        anim.addUpdateListener(valueAnimator -> {
            containerParams.topMargin = (int) valueAnimator.getAnimatedValue();
            container.setLayoutParams(containerParams);
        });

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        return anim;
    }

    public void registerFragment(int position, TopFragment fragment) {
        registeredFragments.add(position, fragment);
        if (currentFragment == null) {
            currentFragment = fragment;
        }
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
        currentFragment.getListView().smoothScrollToPosition(0);
    }
}
