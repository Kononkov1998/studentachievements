package com.example.jenya.studentachievements.comparators;

import com.example.jenya.studentachievements.models.Achievement;

import java.util.Comparator;

public class AchievementsComparator implements Comparator<Achievement> {
    public int compare(Achievement a1, Achievement a2)
    {
        return Integer.compare(a2.isReceived(), a1.isReceived());
    }
}
