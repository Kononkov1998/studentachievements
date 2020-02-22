package com.example.jenya.studentachievements.comparators;

import com.example.jenya.studentachievements.models.UserInfo;
import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

public class StudentsComparator implements Comparator<UserInfo>
{
    @Override
    public int compare(UserInfo u1, UserInfo u2)
    {
        return ComparisonChain.start()
                .compare(u2.getIsAvailable(), u1.getIsAvailable())
                .result();
    }
}
