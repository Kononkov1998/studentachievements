package com.example.jenya.studentachievements.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.activities.OtherProfileActivity;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.utils.ImageActions;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends BaseAdapter {
    private final Context ctx;
    private final LayoutInflater lInflater;
    private final ArrayList<UserInfo> objects;

    public UsersAdapter(Context context, ArrayList<UserInfo> students) {
        ctx = context;
        objects = students;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public UserInfo getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UserInfo s = getItem(position);
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_student, parent, false);
        }

        CircleImageView avatar = view.findViewById(R.id.imageUser);
        int px = ImageActions.getAvatarSizeInPx(ctx);
        GlideUrl glideUrl = null;
        if (s.getAvatar() != null) {
            glideUrl = new GlideUrl(String.format("%s/student/pic/%s", Requests.getInstance().getURL(), s.getAvatar()), new LazyHeaders.Builder()
                    .addHeader("Authorization", SharedPreferencesActions.read("token", ctx))
                    .build());
        }

        Glide.with(ctx)
                .setDefaultRequestOptions(new RequestOptions().timeout(30000))
                .load(glideUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.profile)
                .override(px, px)
                .fallback(R.drawable.profile)
                .into(avatar);


        ((TextView) view.findViewById(R.id.textProfile)).setText(String.format("%s\n%s\n%s", s.getFullName().getLastName(), s.getFullName().getFirstName(), s.getFullName().getPatronymic()));
        ((TextView) view.findViewById(R.id.groupProfile)).setText(s.getGroup().getName());
        final CheckBox checkBoxFavorite = view.findViewById(R.id.checkboxFavorite);
        checkBoxFavorite.setChecked(false);

        for (UserInfo user : UserInfo.getCurrentUser().getFavouriteStudents()) {
            if (user.get_id().equals(s.get_id())) {
                checkBoxFavorite.setChecked(true);
                break;
            }
        }

        UsersAdapter adapter = this;
        View finalView = view;
        checkBoxFavorite.setOnClickListener((buttonView) -> {
            if (checkBoxFavorite.isChecked()) {
                Requests.getInstance().addFavourite(s, ctx);
            } else {
                if (ctx.getClass().getSimpleName().equals("FavoritesActivity")) {
                    Animator.AnimatorListener listener = new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            Requests.getInstance().removeFavouriteFromFavoritesActivity(s, ctx, adapter);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    };
                    ObjectAnimator animation = ObjectAnimator.ofFloat(finalView, "alpha", 1f, 0f);
                    animation.addListener(listener);
                    animation.start();
                } else {
                    Requests.getInstance().removeFavourite(s, ctx);
                }
            }
        });

        if (ctx.getClass().getSimpleName().equals("FavoritesActivity") && !s.getIsAvailable()) {
            view.setAlpha(0.5f);
        } else {
            view.setAlpha(1f);
        }

        RelativeLayout layout = view.findViewById(R.id.layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.setTransitionName(s.get_id());
        }
        layout.setOnClickListener(v -> {
            if (ctx.getClass().getSimpleName().equals("FavoritesActivity") && !s.getIsAvailable()) {
                Toast.makeText(ctx, "Профиль недоступен из-за настроек приватности", Toast.LENGTH_SHORT).show();
                return;
            }
            ActivityOptions options = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((Activity) ctx).setExitSharedElementCallback(null);

                View statusBar = ((Activity) ctx).findViewById(android.R.id.statusBarBackground);
                View navigationBar = ((Activity) ctx).findViewById(android.R.id.navigationBarBackground);
                View bottomMenu = ((Activity) ctx).findViewById(R.id.bottomMenu);

                List<Pair<View, String>> pairs = new ArrayList<>();
                pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
                if (navigationBar != null) {
                    pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
                }
                pairs.add(Pair.create(bottomMenu, bottomMenu.getTransitionName()));
                pairs.add(Pair.create(layout, layout.getTransitionName()));
                options = ActivityOptions.makeSceneTransitionAnimation((Activity) ctx, pairs.toArray(new Pair[0]));
            }

            Intent intent = new Intent(ctx, OtherProfileActivity.class);
            intent.putExtra("otherStudent", s);
            intent.putExtra("activity", ctx.getClass().getSimpleName());

            if (options == null) {
                ((Activity) ctx).startActivityForResult(intent, 1);
            } else {
                ((Activity) ctx).startActivityForResult(intent, 1, options.toBundle());
            }
        });

        return view;
    }
}
