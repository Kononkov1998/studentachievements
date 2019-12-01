package com.example.jenya.studentachievements.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.Requests;
import com.example.jenya.studentachievements.models.Achievement;

import java.util.ArrayList;

public class AchievementsAdapter extends BaseAdapter {
    private final Context ctx;
    private final LayoutInflater lInflater;
    private final ArrayList<Achievement> objects;

    public AchievementsAdapter(Context context, ArrayList<Achievement> achievements) {
        ctx = context;
        objects = achievements;
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
        Achievement a = getAchievement(position);
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_achievement, parent, false);
        }

        // заполняем View в пункте списка данными из ачивок
        ((TextView) view.findViewById(R.id.name)).setText(a.getAchievementInfo().getName());
        ((TextView) view.findViewById(R.id.description)).setText(a.getAchievementInfo().getDescription());
        //((TextView) view.findViewById(R.id.date)).setText(a.getDate());
        //((TextView) view.findViewById(R.id.studentsProgress)).setText(a.getAchievement().getStudentsProgress() + "% студентов получили");
        switch (a.getStars()){ // меняем иконки звёзд в зависимости от их количества
            default:
                ((ImageView) view.findViewById(R.id.star1)).setImageResource(R.drawable.ic_star_border_grey_24dp);
                ((ImageView) view.findViewById(R.id.star2)).setImageResource(R.drawable.ic_star_border_grey_24dp);
                ((ImageView) view.findViewById(R.id.star3)).setImageResource(R.drawable.ic_star_border_grey_24dp);
                break;
            case 1:
                ((ImageView) view.findViewById(R.id.star1)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) view.findViewById(R.id.star2)).setImageResource(R.drawable.ic_star_border_grey_24dp);
                ((ImageView) view.findViewById(R.id.star3)).setImageResource(R.drawable.ic_star_border_grey_24dp);
                break;
            case 2:
                ((ImageView) view.findViewById(R.id.star1)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) view.findViewById(R.id.star2)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) view.findViewById(R.id.star3)).setImageResource(R.drawable.ic_star_border_grey_24dp);
                break;
            case 3:
                ((ImageView) view.findViewById(R.id.star1)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) view.findViewById(R.id.star2)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) view.findViewById(R.id.star3)).setImageResource(R.drawable.ic_star_yellow_24dp);
                break;
        }

        Glide.with(ctx)
                .load(Requests.getInstance().getURL() + "/icons/" + a.getCode() + ".png")
                .placeholder(R.drawable.no_photo)
                .into((ImageView) view.findViewById(R.id.image));
        return view;
    }

    // ачивка по позиции
    private Achievement getAchievement(int position) {
        return ((Achievement) getItem(position));
    }
}
