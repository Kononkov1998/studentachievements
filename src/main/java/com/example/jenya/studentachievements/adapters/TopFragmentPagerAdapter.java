package com.example.jenya.studentachievements.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jenya.studentachievements.fragments.TopFragment;

public class TopFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 4;

    public TopFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TopFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
