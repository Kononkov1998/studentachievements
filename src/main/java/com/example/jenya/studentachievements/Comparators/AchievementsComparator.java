package com.example.jenya.studentachievements.Comparators;

import com.example.jenya.studentachievements.Models.Achievement;

import java.util.Comparator;

public class AchievementsComparator implements Comparator<Achievement> {
    public int compare(Achievement a1, Achievement a2) {
        if (a1.getAchievementInfo().getName().compareTo(a2.getAchievementInfo().getName()) < 0) {
            return -1;
        }
        if (a1.getAchievementInfo().getName().compareTo(a2.getAchievementInfo().getName()) > 0) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
