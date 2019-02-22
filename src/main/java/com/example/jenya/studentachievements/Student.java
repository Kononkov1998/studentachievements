package com.example.jenya.studentachievements;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Student {
    private String name;
    private String surname;
    private String patronymic;
    private String group;
    private int image;
    private int visibility = 0; //0-все, 1-одногруппники, 2-избранные, 3- 1+2 4-никто
    private ArrayList<StudentAchievement> achievements = new ArrayList<>();
    private ArrayList<Student> favorites = new ArrayList<>();

    public Student(String name, String surname, String patronymic, String group, int image) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.group = group;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getGroup() {
        return group;
    }

    public int getImage() {
        return image;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public ArrayList<Student> getFavorites() {
        return favorites;
    }

    public ArrayList<StudentAchievement> getAchievements() {
        return achievements;
    }

    public boolean checkVisibility(Student student) {
        if (student.getVisibility() == 0)
            return true;
        if (student.getVisibility() == 4)
            return false;
        if (student.getVisibility() == 1)
            return student.getGroup().equals(this.getGroup());
        if (student.getVisibility() == 2)
            return student.getFavorites().contains(this);
        return student.getVisibility() == 3 && student.getGroup().equals(this.getGroup()) || student.getVisibility() == 3 && student.favorites.contains(this);
    }
}
