package com.example.jenya.studentachievements;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;

public class DataBase {
    public static ArrayList<Achievement> achievements = new ArrayList<>();
    public static ArrayList<Student> students = new ArrayList<>();
    public static Student currentUser;

    public static void fill() {
        achievements.add(new Achievement("Покоряя вершины", "Сдать сессию на отлично", 20, R.drawable.mountains));
        achievements.add(new Achievement("Выше небес", "Сдать экзамен на отлично", 13, R.drawable.rocket));
        achievements.add(new Achievement("На хорошем счету", "Сдать все зачёты", 37, R.drawable.prize));
        achievements.add(new Achievement("Полный комплект", "Выполнить все задания в ЭК", 38, R.drawable.target));
        achievements.add(new Achievement("Новая ступень", "Перейти на 2-ой курс", 51, R.drawable.stairs));

        Student student1 = new Student("Евгений", "Кононков", "Вячеславович", "КИ16-16Б", R.drawable.profile);
        Student student2 = new Student("Амгалан", "Бадмаев", "Бэликтуевич", "КИ16-16Б", R.drawable.profile2);
        Student student3 = new Student("Василий", "Чанчиков", "Сергеевич", "КИ16-17Б", R.drawable.profile3);

        student1.getAchievements().add(new StudentAchievement(achievements.get(0), 50, ""));
        student1.getAchievements().add(new StudentAchievement(achievements.get(1), 100, "01.12.18"));
        student1.getAchievements().add(new StudentAchievement(achievements.get(2), 100, "30.11.18"));
        student1.getAchievements().add(new StudentAchievement(achievements.get(3), 50, ""));
        student1.getAchievements().add(new StudentAchievement(achievements.get(4), 100, "01.07.18"));

        student2.getAchievements().add(new StudentAchievement(achievements.get(0), 60, ""));
        student2.getAchievements().add(new StudentAchievement(achievements.get(1), 20, ""));
        student2.getAchievements().add(new StudentAchievement(achievements.get(2), 100, "30.11.18"));
        student2.getAchievements().add(new StudentAchievement(achievements.get(3), 100, "30.11.18"));
        student2.getAchievements().add(new StudentAchievement(achievements.get(4), 100, "01.12.18"));

        student3.getAchievements().add(new StudentAchievement(achievements.get(0), 100, "01.12.18"));
        student3.getAchievements().add(new StudentAchievement(achievements.get(1), 15, ""));
        student3.getAchievements().add(new StudentAchievement(achievements.get(2), 90, ""));
        student3.getAchievements().add(new StudentAchievement(achievements.get(3), 15, ""));
        student3.getAchievements().add(new StudentAchievement(achievements.get(4), 0, ""));

        student1.getFavorites().add(student2);
        students.add(student1);
        students.add(student2);
        students.add(student3);
    }

    public static ArrayList<Student> search(String name, String surname, String patronymic, String group) {
        ArrayList<Student> result = new ArrayList<>(DataBase.students);

        if (!name.isEmpty() && !result.isEmpty())
            for (Student student : DataBase.students)
                if (!name.equals(student.getName()))
                    result.remove(student);
        if (!surname.isEmpty() && !result.isEmpty())
            for (Student student : DataBase.students)
                if (!surname.equals(student.getSurname()))
                    result.remove(student);
        if (!patronymic.isEmpty() && !result.isEmpty())
            for (Student student : DataBase.students)
                if (!patronymic.equals(student.getPatronymic()))
                    result.remove(student);
        if (!group.isEmpty() && !result.isEmpty())
            for (Student student : DataBase.students)
                if (!group.equals(student.getGroup()))
                    result.remove(student);
        for (Student student : DataBase.students)
            if (result.contains(student) && !DataBase.currentUser.checkVisibility(student))
                result.remove(student);
        return result;
    }

    public static Student searchStudent(String name, String surname, String patronymic, String group) {
        for (Student student : DataBase.students) {
            if (student.getName().equals(name) && student.getSurname().equals(surname) && student.getPatronymic().equals(patronymic) && student.getGroup().equals(group))
                return student;
        }
        return null;
    }
}
