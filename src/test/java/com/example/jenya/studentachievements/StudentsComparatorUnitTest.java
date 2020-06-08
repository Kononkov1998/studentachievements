package com.example.jenya.studentachievements;

import com.example.jenya.studentachievements.comparators.StudentsComparator;
import com.example.jenya.studentachievements.models.UserInfo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StudentsComparatorUnitTest {
    private StudentsComparator comparator;
    private UserInfo student1;
    private UserInfo student2;

    @Before
    public void setUp() {
        comparator = new StudentsComparator();
        student1 = new UserInfo();
        student2 = new UserInfo();
    }

    @Test
    public void comparisonByAvailability() {
        student1.setIsAvailable(false);
        student2.setIsAvailable(false);
        assertEquals(0, comparator.compare(student1, student2));

        student1.setIsAvailable(true);
        student2.setIsAvailable(false);
        assertEquals(-1, comparator.compare(student1, student2));

        student1.setIsAvailable(false);
        student2.setIsAvailable(true);
        assertEquals(1, comparator.compare(student1, student2));
    }

}
