package com.example.jenya.studentachievements;

import java.util.Comparator;

public class StudentAchievementsComparator implements Comparator<StudentAchievement> {
    public int compare(StudentAchievement a1, StudentAchievement a2) {
        if (a1.getProgress() > a2.getProgress()) {
            return -1;
        }
        if (a1.getProgress() == a2.getProgress()) {
            return 0;
        }
        else {
            return 1;
        }
    }
}
