package com.example.jenya.studentachievements.activities;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.Requests;
import com.example.jenya.studentachievements.SharedPreferencesActions;
import com.example.jenya.studentachievements.ThemeController;
import com.example.jenya.studentachievements.adapters.AchievementsAdapter;
import com.example.jenya.studentachievements.comparators.AchievementsComparator;
import com.example.jenya.studentachievements.models.Achievement;
import com.example.jenya.studentachievements.models.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfileActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private UserInfo otherStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_otherprofile);
        Intent intent = getIntent();
        ImageView selectedElement = null;

        switch (intent.getStringExtra("activity")) {
            case "SearchResultsActivity":
                selectedElement = findViewById(R.id.imageSearch);
                break;
            case "FavoritesActivity":
                selectedElement = findViewById(R.id.imageFavorites);
                break;
        }
        if (selectedElement != null) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.ic_active_color, typedValue, true);
            @ColorInt int color = typedValue.data;
            selectedElement.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }

        otherStudent = intent.getParcelableExtra("otherStudent");
        final ArrayList<Achievement> completedAchievements = new ArrayList<>();
        final ArrayList<Achievement> userAchievements = otherStudent.getAchievements();
        int starsSum = 0;

        for (Achievement achievement : userAchievements) {
            int stars = achievement.getStars();
            if (stars != 0) {
                starsSum += stars;
                completedAchievements.add(achievement);
            }
        }

        Collections.sort(userAchievements, new AchievementsComparator());

        final AchievementsAdapter adapter = new AchievementsAdapter(this, userAchievements);
        final ListView listView = findViewById(R.id.list);
        View header = getLayoutInflater().inflate(R.layout.header_otherprofile, listView, false);
        CircleImageView avatar = header.findViewById(R.id.imageUser);

        if(otherStudent.getAvatar() != null)
        {
            int px = getResources().getDimensionPixelSize(R.dimen.image_size);

            GlideUrl glideUrl = new GlideUrl(String.format("%s/student/pic/%s", Requests.getInstance().getURL(), otherStudent.getAvatar()), new LazyHeaders.Builder()
                    .addHeader("Authorization", SharedPreferencesActions.read("token", this))
                    .build());

            Glide.with(this)
                    .load(glideUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.profile)
                    .override(px, px)
                    .into(avatar);
        }

        String headerText = otherStudent.getFullName().getLastName() + "\n" + otherStudent.getFullName().getFirstName() + "\n" + otherStudent.getFullName().getPatronymic() + "\n" + otherStudent.getGroup().getName();
        ((TextView) header.findViewById(R.id.textProfile)).setText(headerText);

        int completed = completedAchievements.size();
        int all = userAchievements.size();
        ((TextView) header.findViewById(R.id.achievementsTextView)).setText(String.format("Получено достижений: %d из %d (%d%%)", completed, all, Math.round((double) completed / (double) all * 100.0)));
        ((ProgressBar) header.findViewById(R.id.achievementsProgressBar)).setProgress(completed);
        ((ProgressBar) header.findViewById(R.id.achievementsProgressBar)).setMax(all);

        int allStars = all * 3;
        ((TextView) header.findViewById(R.id.starsTextView)).setText(String.format("Получено звезд: %d из %d (%d%%)", starsSum, allStars, Math.round((double) starsSum / (double) allStars * 100.0)));
        ((ProgressBar) header.findViewById(R.id.starsProgressBar)).setProgress(starsSum);
        ((ProgressBar) header.findViewById(R.id.starsProgressBar)).setMax(allStars);

        listView.addHeaderView(header);
        listView.setAdapter(adapter);

        CheckBox hideBox = findViewById(R.id.checkboxHide);
        hideBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (Achievement achievement : completedAchievements) {
                    userAchievements.remove(achievement);
                }
                adapter.notifyDataSetChanged();
            } else {
                userAchievements.addAll(completedAchievements);
                Collections.sort(userAchievements, new AchievementsComparator());
                adapter.notifyDataSetChanged();
            }
        });

        checkBox = header.findViewById(R.id.checkboxFavorite);

        for (UserInfo user : UserInfo.getCurrentUser().getFavouriteStudents()) {
            if (user.get_id().equals(otherStudent.get_id())) {
                checkBox.setChecked(true);
                break;
            } else {
                checkBox.setChecked(false);
            }
        }

        checkBox.setOnClickListener((buttonView) -> {
            if (checkBox.isChecked()) {
                Requests.getInstance().addFavourite(otherStudent, this);
            } else {
                Requests.getInstance().removeFavourite(otherStudent, this);
            }
        });

        setSharedElementCallback(header.findViewById(R.id.layout));
    }

    private void setSharedElementCallback(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                   // names.clear();
                    //sharedElements.clear();
                    //names.add(view.getTransitionName());
                    sharedElements.put(view.getTransitionName(), view);
                    Toast.makeText(getApplicationContext(), "enterCallback", Toast.LENGTH_SHORT).show();
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
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEnterSharedElementCallback((SharedElementCallback) null);
        }
        Intent intent = new Intent();
        intent.putExtra("position", getIntent().getIntExtra("position", -1));
        setResult(RESULT_OK, intent);
        super.onBackPressed();
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

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openGrade(View view) {
        Intent intent = new Intent(this, GradeActivity.class);
        startActivity(intent);
    }
}
