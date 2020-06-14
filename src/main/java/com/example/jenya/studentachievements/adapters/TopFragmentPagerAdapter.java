package com.example.jenya.studentachievements.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.activities.TopActivity;
import com.example.jenya.studentachievements.fragments.TopFragment;

public class TopFragmentPagerAdapter extends FragmentPagerAdapter {

    private final static int PAGE_COUNT = 4;
    private final static int ALL_TOP_ITEM = 0, YEAR_TOP_ITEM = 1, DIRECTION_TOP_ITEM = 2, GROUP_TOP_ITEM = 3;
    private Context context;

    public TopFragmentPagerAdapter(FragmentManager supportFragmentManager, TopActivity context) {
        super(supportFragmentManager);
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
            case TopActivity.ALL_PAGE_NUMBER:
                return context.getString(R.string.top_all);
            case TopActivity.YEAR_PAGE_NUMBER:
                return context.getString(R.string.top_year);
            case TopActivity.DIRECTION_PAGE_NUMBER:
                return context.getString(R.string.top_direction);
            case TopActivity.GROUP_PAGE_NUMBER:
                return context.getString(R.string.top_group);
        }
        return null;
    }

}
