package com.example.jenya.studentachievements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAchievementsAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<StudentAchievement> objects;

    StudentAchievementsAdapter(Context context, ArrayList<StudentAchievement> achievements) {
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
        StudentAchievement a = getStudentAchievement(position);
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_profile, parent, false);
        }

        // заполняем View в пункте списка данными из ачивок
        ((TextView) view.findViewById(R.id.name)).setText(a.getAchievement().getName());
        ((TextView) view.findViewById(R.id.description)).setText(a.getAchievement().getDescription());
        ((TextView) view.findViewById(R.id.date)).setText(a.getDate());
        ((TextView) view.findViewById(R.id.progress)).setText(a.getProgress() + "%");
        ((ProgressBar) view.findViewById(R.id.progressBar)).setProgress(a.getProgress());
        ((TextView) view.findViewById(R.id.studentsProgress)).setText(a.getAchievement().getStudentsProgress() + "% студентов получили");
        ((ImageView) view.findViewById(R.id.image)).setImageResource(a.getAchievement().getImage());
        return view;
    }

    // ачивка по позиции
    StudentAchievement getStudentAchievement(int position) {
        return ((StudentAchievement) getItem(position));
    }
}
