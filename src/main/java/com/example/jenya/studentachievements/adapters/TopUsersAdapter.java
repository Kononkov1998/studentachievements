package com.example.jenya.studentachievements.adapters;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.activities.OtherProfileFromTopActivity;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.utils.ImageActions;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.common.collect.ComparisonChain.start;

public class TopUsersAdapter extends BaseAdapter {
    private final Context ctx;
    private final LayoutInflater lInflater;
    private final ArrayList<UserInfo> objects;

    public TopUsersAdapter(Context context, ArrayList<UserInfo> students) {
        ctx = context;
        objects = students;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addAll(ArrayList<UserInfo> students) {
        objects.addAll(students);
        notifyDataSetChanged();
    }

    public void clear() {
        objects.clear();
        notifyDataSetChanged();
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
            view = lInflater.inflate(R.layout.item_top_student, parent, false);
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

        ((TextView) view.findViewById(R.id.textProfile)).setText(String.format("%s %s. %s.", s.getFullName().getLastName(), s.getFullName().getFirstName().charAt(0), s.getFullName().getPatronymic().charAt(0)));
        ((TextView) view.findViewById(R.id.groupProfile)).setText(s.getGroup().getName());
        ((TextView) view.findViewById(R.id.place)).setText(String.format(Locale.getDefault(), "%d", s.getPlace()));
        ((TextView) view.findViewById(R.id.starsSum)).setText(String.format(Locale.getDefault(), "%d", s.getStarCount()));

        View layout = view.findViewById(R.id.layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            avatar.setTransitionName(s.get_id());
        }

        if (!s.get_id().equals(UserInfo.getCurrentUser().get_id())) {
            layout.setOnClickListener(v -> {
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
                    pairs.add(Pair.create(avatar, avatar.getTransitionName()));
                    options = ActivityOptions.makeSceneTransitionAnimation((Activity) ctx, pairs.toArray(new Pair[0]));
                }

                Intent intent = new Intent(ctx, OtherProfileFromTopActivity.class);
                intent.putExtra("otherStudent", s);

                if (options == null) {
                    ((Activity) ctx).startActivity(intent);
                } else {
                    ((Activity) ctx).startActivity(intent, options.toBundle());
                }
            });
        }
        
        return view;
    }
}
