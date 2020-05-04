package com.example.jenya.studentachievements.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;
import com.example.jenya.studentachievements.utils.ImageActions;
import com.example.jenya.studentachievements.utils.SharedPreferencesActions;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

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


        ((TextView) view.findViewById(R.id.textProfile)).setText(String.format("%s\n%s\n%s", s.getFullName().getLastName(), s.getFullName().getFirstName(), s.getFullName().getPatronymic()));
        ((TextView) view.findViewById(R.id.groupProfile)).setText(s.getGroup().getName());
        ((TextView) view.findViewById(R.id.place)).setText("ХХХ");
        ((TextView) view.findViewById(R.id.starsSum)).setText(String.format(Locale.getDefault(), "%d", s.getStarCount()));

        return view;
    }
}
