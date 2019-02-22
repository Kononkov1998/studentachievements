package com.example.jenya.studentachievements;

import java.io.Serializable;

public class StudentAchievement {
    private Achievement achievement;
    private int progress;
    private String date;

    public StudentAchievement(Achievement achievement, int progress, String date) {
        this.achievement = achievement;
        this.progress = progress;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public int getProgress() {
        return progress;
    }

    public Achievement getAchievement() {
        return achievement;
    }
}
