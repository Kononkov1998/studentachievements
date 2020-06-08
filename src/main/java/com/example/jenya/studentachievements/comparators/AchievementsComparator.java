package com.example.jenya.studentachievements.comparators;

import com.example.jenya.studentachievements.models.Achievement;
import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

public class AchievementsComparator implements Comparator<Achievement> {
    @Override
    public int compare(Achievement a1, Achievement a2) {
        return ComparisonChain.start()
                .compareTrueFirst(a1.isReceived(), a2.isReceived())
                .compare(a2.getDate(), a1.getDate())
                .compare(a1.getAchievementInfo().getName(), a2.getAchievementInfo().getName())
                .result();
    }
}
