package com.example.jenya.studentachievements.comparators;

import com.example.jenya.studentachievements.models.UserInfo;
import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

public class StudentsComparator implements Comparator<UserInfo> {
    @Override
    public int compare(UserInfo u1, UserInfo u2) {
        return ComparisonChain.start()
                .compareTrueFirst(u1.getIsAvailable(), u2.getIsAvailable())
                .result();
    }
}
