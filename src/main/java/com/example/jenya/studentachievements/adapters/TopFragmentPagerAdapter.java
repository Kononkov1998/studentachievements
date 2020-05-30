package com.example.jenya.studentachievements.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.fragments.TopFragment;

public class TopFragmentPagerAdapter extends FragmentPagerAdapter {

    private final static int PAGE_COUNT = 4;
    private final static int ALL_TOP_ITEM = 0, YEAR_TOP_ITEM = 1, DIRECTION_TOP_ITEM = 2, GROUP_TOP_ITEM = 3;
    private Context context;

    public TopFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return TopFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case ALL_TOP_ITEM:
                return context.getString(R.string.topAll);
            case YEAR_TOP_ITEM:
                return context.getString(R.string.topYear);
            case DIRECTION_TOP_ITEM:
                return context.getString(R.string.topDirection);
            case GROUP_TOP_ITEM:
                return context.getString(R.string.topGroup);
        }
        return null;
    }

}
