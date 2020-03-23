package com.example.jenya.studentachievements.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.models.Mark;

import java.util.ArrayList;
import java.util.Locale;

public class DisciplinesAdapter extends BaseAdapter {
    private final Context ctx;
    private final LayoutInflater lInflater;
    private final ArrayList<Mark> objects;

    public DisciplinesAdapter(Context context, ArrayList<Mark> marks) {
        ctx = context;
        objects = marks;
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
        final Mark mark = (Mark) getItem(position);

        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_discipline, parent, false);
        }

        final CardView card = view.findViewById(R.id.card);
        card.setCardBackgroundColor(mark.getColor(ctx));

        TextView textViewName = view.findViewById(R.id.discipline_name);
        TextView textViewType = view.findViewById(R.id.discipline_type);
        TextView textViewTutor = view.findViewById(R.id.discipline_tutor);
        TextView textViewRating = view.findViewById(R.id.discipline_rating);
        TextView textViewHours = view.findViewById(R.id.discipline_hours);
        TextView textViewDate = view.findViewById(R.id.discipline_date);

        textViewName.setText(mark.getSubjectName());
        textViewType.setText(mark.getDisciplineType());
        textViewTutor.setText(mark.getStrTutor());
        textViewRating.setText(mark.getStrRating());
        String hours = String.format(Locale.getDefault(), "%d (%d з.е.)", mark.getHoursCount(), mark.getCreditUnits());
        textViewHours.setText(hours);
        textViewDate.setText(mark.getStrDate());

        return view;
    }
}
