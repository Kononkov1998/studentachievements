package com.example.jenya.studentachievements;

import java.util.Comparator;

public class AchievementsComparator implements Comparator<Achievement> {
    public int compare(Achievement a1, Achievement a2) {
        if (a1.getAchievementInfo().getGeneralProgress() > a2.getAchievementInfo().getGeneralProgress()) {
            return -1;
        }
        if (a1.getAchievementInfo().getGeneralProgress() == a2.getAchievementInfo().getGeneralProgress()) {
            return 0;
        }
        else {
            return 1;
        }
    }
}
