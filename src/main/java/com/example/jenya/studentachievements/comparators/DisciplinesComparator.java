package com.example.jenya.studentachievements.comparators;

import com.example.jenya.studentachievements.models.Achievement;
import com.example.jenya.studentachievements.models.Mark;
import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

public class DisciplinesComparator implements Comparator<Mark>
{
    @Override
    public int compare(Mark m1, Mark m2)
    {
        if(m1.getDateOfPass() == null || m2.getDateOfPass() == null)
        {
            return 1;
        }

        return ComparisonChain.start()
                .compare(m1.getDateOfPass(), m2.getDateOfPass())
                .result();
    }
}
