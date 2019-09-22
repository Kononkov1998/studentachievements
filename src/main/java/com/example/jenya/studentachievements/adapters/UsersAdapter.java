package com.example.jenya.studentachievements.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jenya.studentachievements.activities.OtherProfileActivity;
import com.example.jenya.studentachievements.models.User;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.R;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<User> objects;

    UsersAdapter(Context context, ArrayList<User> students) {
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
            view = lInflater.inflate(R.layout.item_favorites, parent, false);
        }

        // заполняем View в пункте списка данными из студентов
       // ((ImageView) view.findViewById(R.id.imageUser)).setImageResource(s.getImage());
        String textProfile = s.getFullName().getLastName() + "\n" + s.getFullName().getFirstName() + "\n" + s.getFullName().getPatronymic() + "\n" + s.getGroup().getName();
        ((TextView) view.findViewById(R.id.textProfile)).setText(textProfile);
        final CheckBox checkBox = view.findViewById(R.id.checkboxFavorite);
       /* if (DataBase.currentUser.getFavorites().contains(s)) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DataBase.currentUser.getFavorites().add(s);
                    notifyDataSetChanged();
                } else {
                    DataBase.currentUser.getFavorites().remove(s);
                    notifyDataSetChanged();
                }
            }
        });*/

        LinearLayout layout = view.findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = v.findViewById(R.id.textProfile);
                String name = textView.getText().toString().split("\n")[1];
                String surname = textView.getText().toString().split("\n")[0];
                String patronymic = textView.getText().toString().split("\n")[2];
                String group = textView.getText().toString().split("\n")[3];

                Intent intent = new Intent(ctx, OtherProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("surname", surname);
                intent.putExtra("patronymic", patronymic);
                intent.putExtra("group", group);
                ctx.startActivity(intent);
            }
        });

        return view;
    }

    // студент по позиции
    UserInfo getUserInfo(int position) {
        return ((UserInfo) getItem(position));
    }
}
