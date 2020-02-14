package com.example.jenya.studentachievements.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.jenya.studentachievements.utils.ImageActions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;
import com.example.jenya.studentachievements.activities.OtherProfileActivity;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;

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
    public Object getItem(int position) {
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
        final UserInfo s = getUserInfo(position);
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_student, parent, false);
        }

        CircleImageView avatar = view.findViewById(R.id.imageUser);

        if (s.getAvatar() != null) {
            int px = ImageActions.getAvatarSizeInPx(ctx);

            GlideUrl glideUrl = new GlideUrl(String.format("%s/student/pic/%s", Requests.getInstance().getURL(), s.getAvatar()), new LazyHeaders.Builder()
                    .addHeader("Authorization", SharedPreferencesActions.read("token", ctx))
                    .build());

            Glide.with(ctx)
                    .load(glideUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.profile)
                    .override(px, px)
                    .into(avatar);
        }

        String textProfile = s.getFullName().getLastName() + "\n" + s.getFullName().getFirstName() + "\n" + s.getFullName().getPatronymic() + "\n" + s.getGroup().getName();
        ((TextView) view.findViewById(R.id.textProfile)).setText(textProfile);
        final CheckBox checkBoxFavorite = view.findViewById(R.id.checkboxFavorite);

        for (UserInfo user : UserInfo.getCurrentUser().getFavouriteStudents()) {
            if (user.get_id().equals(s.get_id())) {
                checkBoxFavorite.setChecked(true);
                break;
            } else {
                checkBoxFavorite.setChecked(false);
            }
        }

        checkBoxFavorite.setOnClickListener((buttonView) -> {
            if (checkBoxFavorite.isChecked()) {
                Requests.getInstance().addFavourite(s, ctx);
            } else {
                Requests.getInstance().removeFavourite(s, ctx);
            }
        });

        RelativeLayout layout = view.findViewById(R.id.layout);
        layout.setOnClickListener(v -> {
            ActivityOptions options = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ((Activity) ctx).setExitSharedElementCallback(null);

                View statusBar = ((Activity) ctx).findViewById(android.R.id.statusBarBackground);
                View navigationBar = ((Activity) ctx).findViewById(android.R.id.navigationBarBackground);
                View bottomMenu = ((Activity) ctx).findViewById(R.id.bottomMenu);

                List<Pair<View, String>> pairs = new ArrayList<>();
                pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
                pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
                pairs.add(Pair.create(bottomMenu, bottomMenu.getTransitionName()));
                pairs.add(Pair.create(layout, layout.getTransitionName()));

                options = ActivityOptions.makeSceneTransitionAnimation((Activity) ctx, pairs.toArray(new Pair[0]));
            }

            Intent intent = new Intent(ctx, OtherProfileActivity.class);
            intent.putExtra("otherStudent", s);
            intent.putExtra("activity", ctx.getClass().getSimpleName());
            intent.putExtra("position", position);

            if (options == null) {
                ((Activity) ctx).startActivityForResult(intent, 1);
            } else {
                ((Activity) ctx).startActivityForResult(intent, 1, options.toBundle());
            }
        });

        return view;
    }

    // студент по позиции
    private UserInfo getUserInfo(int position) {
        return ((UserInfo) getItem(position));
    }
}
