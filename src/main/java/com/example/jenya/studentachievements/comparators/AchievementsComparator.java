package com.example.jenya.studentachievements.comparators;

import com.example.jenya.studentachievements.models.Achievement;
import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

public class AchievementsComparator implements Comparator<Achievement> {
    @Override
    public int compare(Achievement a1, Achievement a2) {
        return ComparisonChain.start()
                .compare(a2.isReceived(), a1.isReceived())
                .compare(a2.getDate(), a2.getDate())
                .compare(a1.getAchievementInfo().getName(), a2.getAchievementInfo().getName())
                .result();
    }
}
