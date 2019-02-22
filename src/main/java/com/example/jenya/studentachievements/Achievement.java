package com.example.jenya.studentachievements;

import java.io.Serializable;

public class Achievement {
    private String name;
    private String description;
    private int studentsProgress;
    private int image;

    public Achievement(String name, String description, int studentsProgress, int image) {
        this.name = name;
        this.description = description;
        this.studentsProgress = studentsProgress;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStudentsProgress() {
        return studentsProgress;
    }

    public int getImage() {
        return image;
    }
}
