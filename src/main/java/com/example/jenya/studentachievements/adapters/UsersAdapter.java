package com.example.jenya.studentachievements.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.Requests;
import com.example.jenya.studentachievements.activities.OtherProfileActivity;
import com.example.jenya.studentachievements.models.UserInfo;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {
    private final Context ctx;
    private final LayoutInflater lInflater;
    private final ArrayList<UserInfo> objects;
    private final String activity;

    public UsersAdapter(Context context, ArrayList<UserInfo> students, String activity) {
        ctx = context;
        objects = students;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
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

        // заполняем View в пункте списка данными из студентов
       // ((ImageView) view.findViewById(R.id.imageUser)).setImageResource(s.getImage());
        String textProfile = s.getFullName().getLastName() + "\n" + s.getFullName().getFirstName() + "\n" + s.getFullName().getPatronymic() + "\n" + s.getGroup().getName();
        ((TextView) view.findViewById(R.id.textProfile)).setText(textProfile);
        final CheckBox checkBox = view.findViewById(R.id.checkboxFavorite);

        for (UserInfo user : UserInfo.getCurrentUser().getFavouriteStudents()) {
            if (user.get_id().equals(s.get_id())){
                checkBox.setChecked(true);
                break;
            }
            else {
                checkBox.setChecked(false);
            }
        }

        checkBox.setOnClickListener((buttonView) -> {
            if (checkBox.isChecked()) {
                Requests.getInstance().addFavourite(s, ctx);
            } else {
                Requests.getInstance().removeFavourite(s, ctx);
            }
        });

        RelativeLayout layout = view.findViewById(R.id.layout);
        layout.setOnClickListener(v -> {
            Bundle bundle = null;

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                Activity activity = (Activity) ctx;
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, layout, "student_transition");
                    bundle = options.toBundle();
            }

            Intent intent = new Intent(ctx, OtherProfileActivity.class);
            intent.putExtra("otherStudent", s);
            intent.putExtra("activity", activity);

            if (bundle == null) {
                ctx.startActivity(intent);
            } else {
                ctx.startActivity(intent, bundle);
            }
        });

        return view;
    }

    // студент по позиции
    private UserInfo getUserInfo(int position) {
        return ((UserInfo) getItem(position));
    }
}
