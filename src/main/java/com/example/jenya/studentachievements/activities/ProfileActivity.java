package com.example.jenya.studentachievements.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.jenya.studentachievements.utils.ImageActions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;
import com.example.jenya.studentachievements.utils.ThemeController;
import com.example.jenya.studentachievements.adapters.AchievementsAdapter;
import com.example.jenya.studentachievements.comparators.AchievementsComparator;
import com.example.jenya.studentachievements.models.Achievement;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AbstractActivity {
    private static final int PICK_FROM_GALLERY = 1;
    private CircleImageView avatar;
    private ListView listView;
    private UserInfo userInfo;
    @SuppressWarnings("FieldCanBeLocal")
    private View header;
    @SuppressWarnings("FieldCanBeLocal")
    private CheckBox hideBox;
    private KProgressHUD hud;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThemeController.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_profile);

        final ArrayList<Achievement> completedAchievements = new ArrayList<>();
        userInfo = UserInfo.getCurrentUser();
        @SuppressWarnings("unchecked") final ArrayList<Achievement> userAchievements = (ArrayList<Achievement>) userInfo.getAchievements().clone();
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
        listView = findViewById(R.id.list);
        header = getLayoutInflater().inflate(R.layout.header_profile, listView, false);
        avatar = header.findViewById(R.id.imageUser);

        ((TextView) header.findViewById(R.id.textProfile))
                .setText(String.format(
                        "%s\n%s\n%s",
                        userInfo.getFullName().getLastName(),
                        userInfo.getFullName().getFirstName(),
                        userInfo.getFullName().getPatronymic())
                );

        ((TextView) header.findViewById(R.id.groupProfile))
                .setText(userInfo.getGroup().getName());

        int completed = completedAchievements.size();
        int all = userAchievements.size();
        ((TextView) header.findViewById(R.id.achievementsTextView)).setText(String.format(Locale.getDefault(), "Получено достижений: %d из %d (%d%%)", completed, all, Math.round((double) completed / (double) all * 100.0)));
        ((ProgressBar) header.findViewById(R.id.achievementsProgressBar)).setProgress(completed);
        ((ProgressBar) header.findViewById(R.id.achievementsProgressBar)).setMax(all);

        int allStars = all * 3;
        ((TextView) header.findViewById(R.id.starsTextView)).setText(String.format(Locale.getDefault(), "Получено звезд: %d из %d (%d%%)", starsSum, allStars, Math.round((double) starsSum / (double) allStars * 100.0)));
        ((ProgressBar) header.findViewById(R.id.starsProgressBar)).setProgress(starsSum);
        ((ProgressBar) header.findViewById(R.id.starsProgressBar)).setMax(allStars);

        listView.addHeaderView(header);
        listView.setAdapter(adapter);

        hideBox = findViewById(R.id.checkboxHide);
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

        if (SharedPreferencesActions.check("showCompleted", this)) {
            hideBox.setChecked(true);
        }

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    public void uploadAvatar(View view) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Crop.pickImage(this);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Произошла ошибка. Попробуйте еще раз", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PICK_FROM_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Crop.pickImage(this);
            } else {
                Toast.makeText(this, "Для загрузки аватара необходимо предоставить доступ к фотографиям", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            try {
                hud.show();
                int px = ImageActions.getAvatarSizeInPx(this);
                File file = new File(Crop.getOutput(result).getPath());
                Bitmap bitmap = ImageActions.decodeSampledBitmapFromResource(file.getAbsolutePath(), px, px);
                file = ImageActions.convertBitmapToFile(bitmap, this);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);
                Requests.getInstance().uploadAvatar(body, this, avatar, hud);
            } catch (Exception e) {
                hud.dismiss();
                Toast.makeText(this, "Произошла ошибка. Попробуйте еще раз", Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
        if (userInfo.getAvatar() != null) {
            int px = ImageActions.getAvatarSizeInPx(this);

            GlideUrl glideUrl = new GlideUrl(String.format("%s/student/pic/%s", Requests.getInstance().getURL(), userInfo.getAvatar()), new LazyHeaders.Builder()
                    .addHeader("Authorization", SharedPreferencesActions.read("token", this))
                    .build());

            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().timeout(30000))
                    .load(glideUrl)
                    .placeholder(R.drawable.profile)
                    .override(px, px)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(avatar);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (((CheckBox) findViewById(R.id.checkboxHide)).isChecked()) {
            SharedPreferencesActions.save("showCompleted", "false", this);
        } else {
            SharedPreferencesActions.delete("showCompleted", this);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void openProfile(View view) {
        listView.smoothScrollToPosition(0);
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
        Intent intent = new Intent(this, FavoritesActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openTop(View view) {
        Intent intent = new Intent(this, TopActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
