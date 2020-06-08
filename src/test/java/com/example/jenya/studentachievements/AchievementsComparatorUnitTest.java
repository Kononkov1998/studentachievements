package com.example.jenya.studentachievements;

import com.example.jenya.studentachievements.comparators.AchievementsComparator;
import com.example.jenya.studentachievements.models.Achievement;
import com.example.jenya.studentachievements.models.AchievementInfo;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AchievementsComparatorUnitTest {
    private AchievementsComparator comparator;
    private Achievement a1;
    private Achievement a2;

    @Before
    public void setUp() {
        comparator = new AchievementsComparator();
        a1 = createDefaultAchievement();
        a2 = createDefaultAchievement();
    }

    private Achievement createDefaultAchievement() {
        Achievement a = new Achievement();
        a.setStars(3);
        a.setDate(new Date());
        a.setAchievementInfo(new AchievementInfo());
        a.getAchievementInfo().setName("А");
        return a;
    }

    @Test
    public void comparisonByStars() {
        a1.setStars(3);
        a2.setStars(2);
        assertEquals(0, comparator.compare(a1, a2));

        a1.setStars(0);
        a2.setStars(1);
        assertEquals(1, comparator.compare(a1, a2));

        a1.setStars(2);
        a2.setStars(0);
        assertEquals(-1, comparator.compare(a1, a2));
    }

    @Test
    public void comparisonByDate() {
        a1.setDate(new Date(1998));
        a2.setDate(new Date(1998));
        assertEquals(0, comparator.compare(a1, a2));

        a1.setDate(new Date(500000));
        a2.setDate(new Date(499999));
        assertEquals(-1, comparator.compare(a1, a2));

        a1.setDate(new Date(-500000));
        a2.setDate(new Date(500000));
        assertEquals(1, comparator.compare(a1, a2));
    }

    @Test
    public void comparisonByName() {
        a1.getAchievementInfo().setName("А");
        a2.getAchievementInfo().setName("Б");
        assertEquals(-1, comparator.compare(a1, a2));

        a1.getAchievementInfo().setName("б");
        a2.getAchievementInfo().setName("b");
        assertEquals(1, comparator.compare(a1, a2));

        a1.getAchievementInfo().setName("я");
        a2.getAchievementInfo().setName("я");
        assertEquals(0, comparator.compare(a1, a2));
    }
}