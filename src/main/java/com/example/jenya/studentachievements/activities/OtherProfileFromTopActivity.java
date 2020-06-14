package com.example.jenya.studentachievements.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.adapters.AchievementsAdapter;
import com.example.jenya.studentachievements.comparators.AchievementsComparator;
import com.example.jenya.studentachievements.models.Achievement;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.utils.ImageActions;
import com.example.jenya.studentachievements.utils.ImageViewActions;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;
import com.example.jenya.studentachievements.utils.ThemeController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfileFromTopActivity extends AbstractActivity {

    private CheckBox checkBoxFavorite;
    private UserInfo otherStudent;
    private View header;
    private int starsSum;
    private ListView listView;
    private View stats;
    private ProgressBar progressBar;
    private boolean isLoadingFinished = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_otherprofile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        ImageViewActions.setActiveColor(this, findViewById(R.id.imageTop));

        otherStudent = getIntent().getParcelableExtra("otherStudent");
        listView = findViewById(R.id.list);
        header = getLayoutInflater().inflate(R.layout.header_otherprofile, listView, false);
        stats = header.findViewById(R.id.stats);
        progressBar = findViewById(R.id.progressBar);
        CircleImageView avatar = header.findViewById(R.id.imageUser);
        starsSum = otherStudent.getStarCount();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            avatar.setTransitionName(otherStudent.get_id());
        }

        if (otherStudent.getAvatar() != null) {
            int px = ImageActions.getAvatarSizeInPx(this);

            GlideUrl glideUrl = new GlideUrl(String.format("%s/student/pic/%s", Requests.getInstance().getURL(), otherStudent.getAvatar()), new LazyHeaders.Builder()
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

        ((TextView) header.findViewById(R.id.textProfile))
                .setText(String.format(
                        "%s\n%s\n%s",
                        otherStudent.getFullName().getLastName(),
                        otherStudent.getFullName().getFirstName(),
                        otherStudent.getFullName().getPatronymic()
                ));

        ((TextView) header.findViewById(R.id.groupProfile))
                .setText(otherStudent.getGroup().getName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

        checkBoxFavorite = header.findViewById(R.id.checkboxFavorite);
        stats.setVisibility(View.GONE);
        updateCheckBoxFavorite();
        listView.addHeaderView(header);
        listView.setAdapter(new AchievementsAdapter(this, new ArrayList<>()));

        progressBar.setVisibility(View.VISIBLE);
        Requests.getInstance().student(this, otherStudent.get_id());
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
        updateCheckBoxFavorite();

        if (isLoadingFinished) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void loadInfo(UserInfo student) {
        otherStudent = student;

        final ArrayList<Achievement> completedAchievements = new ArrayList<>();
        final ArrayList<Achievement> userAchievements = otherStudent.getAchievements();

        for (Achievement achievement : userAchievements) {
            if (achievement.isReceived()) {
                completedAchievements.add(achievement);
            }
        }

        Collections.sort(userAchievements, new AchievementsComparator());

        final AchievementsAdapter adapter = new AchievementsAdapter(this, userAchievements);

        int completed = completedAchievements.size();
        int all = userAchievements.size();
        ((TextView) header.findViewById(R.id.achievementsTextView)).setText(String.format(Locale.getDefault(), "Получено достижений: %d из %d (%d%%)", completed, all, Math.round((double) completed / (double) all * 100.0)));
        ((ProgressBar) header.findViewById(R.id.achievementsProgressBar)).setProgress(completed);
        ((ProgressBar) header.findViewById(R.id.achievementsProgressBar)).setMax(all);

        int allStars = all * 3;
        ((TextView) header.findViewById(R.id.starsTextView)).setText(String.format(Locale.getDefault(), "Получено звезд: %d из %d (%d%%)", starsSum, allStars, Math.round((double) starsSum / (double) allStars * 100.0)));
        ((ProgressBar) header.findViewById(R.id.starsProgressBar)).setProgress(starsSum);
        ((ProgressBar) header.findViewById(R.id.starsProgressBar)).setMax(allStars);

        listView.setAdapter(adapter);

        CheckBox hideBox = findViewById(R.id.checkboxHide);
        hideBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (Achievement achievement : completedAchievements) {
                    userAchievements.remove(achievement);
                }
            } else {
                userAchievements.addAll(completedAchievements);
                Collections.sort(userAchievements, new AchievementsComparator());
            }
            adapter.notifyDataSetChanged();
        });

        checkBoxFavorite.setOnClickListener((buttonView) -> {
            if (checkBoxFavorite.isChecked()) {
                Requests.getInstance().addFavourite(otherStudent, this);
            } else {
                Requests.getInstance().removeFavourite(otherStudent, this);
            }
        });

        isLoadingFinished = true;
        stats.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void updateCheckBoxFavorite() {
        for (UserInfo user : UserInfo.getCurrentUser().getFavouriteStudents()) {
            if (user.get_id().equals(otherStudent.get_id())) {
                checkBoxFavorite.setChecked(true);
                break;
            } else {
                checkBoxFavorite.setChecked(false);
            }
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
        Intent intent = new Intent(this, TopActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
