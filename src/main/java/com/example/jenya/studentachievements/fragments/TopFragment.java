package com.example.jenya.studentachievements.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.activities.TopActivity;
import com.example.jenya.studentachievements.adapters.TopUsersAdapter;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;

import java.util.ArrayList;

public class TopFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private static final String ALL = "all";
    private static final String GROUP = "group";
    private static final String DIRECTION = "direction";
    private static final String YEAR = "year";
    private static final int PAGE_SIZE = 15;

    private TopUsersAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private int pageNumber = 1;
    private int maxPageNumber = -1;
    private boolean listIsLoading = false;
    private View footer;
    private String region;
    private int currentPlace = 1;
    private int currentStarCount = 0;
    private TopActivity activity;
    private boolean isLoadingFinished = false;
    private int itemHeight = -1;

    public static TopFragment newInstance(int page) {
        TopFragment fragment = new TopFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            region = getRegion(getArguments().getInt(ARGUMENT_PAGE_NUMBER));
        }
        adapter = new TopUsersAdapter(getContext(), new ArrayList<>());
        if (getActivity() != null) {
            activity = (TopActivity) getActivity();
        }
        activity.registerFragment(getArguments().getInt(ARGUMENT_PAGE_NUMBER), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        listView = view.findViewById(R.id.list);
        footer = inflater.inflate(R.layout.footer_top, listView, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        listView.setAdapter(adapter);
        listView.addFooterView(footer);
        footer.setVisibility(View.GONE);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) == null) {
                    return;
                }

                if (!isLoadingFinished) {
                    if (firstVisibleItem + visibleItemCount == totalItemCount) {
                        if (!listIsLoading) {
                            addItems();
                        }
                    }
                }
            }

        });

        return view;
    }

    private String getRegion(int page) {
        switch (page) {
            case TopActivity.ALL_PAGE_NUMBER:
                return ALL;
            case TopActivity.GROUP_PAGE_NUMBER:
                return GROUP;
            case TopActivity.DIRECTION_PAGE_NUMBER:
                return DIRECTION;
            case TopActivity.YEAR_PAGE_NUMBER:
                return YEAR;
        }
        return null;
    }

    private void addItems() {
        listIsLoading = true;
        footer.setVisibility(View.VISIBLE);
        Requests.getInstance().topStudents(this, pageNumber, PAGE_SIZE, region);
    }

    public ListView getListView() {
        return listView;
    }

    public void populateListView(ArrayList<UserInfo> students, int numberOfRecords) {
        if (students != null) {
            for (UserInfo student : students) {
                student.setPlace(calculatePlace(student.getStarCount()));
            }

            if (maxPageNumber == -1) {
                maxPageNumber = (int) Math.ceil((double) numberOfRecords / PAGE_SIZE);
            }

            adapter.addAll(students);

            if (itemHeight == -1) {
                View firstItem = adapter.getView(0, null, listView);
                firstItem.measure(0, 0);
                itemHeight = firstItem.getMeasuredHeight();
            }

            if (pageNumber == maxPageNumber) {
                listView.removeFooterView(footer);
                isLoadingFinished = true;
            }

            pageNumber++;
            footer.setVisibility(View.GONE);
        }


        listIsLoading = false;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        adapter.clear();
        listView.addFooterView(footer);
        currentPlace = 1;
        currentStarCount = 0;
        isLoadingFinished = false;
    }

    private int calculatePlace(int starCount) {
        if (starCount < currentStarCount) {
            currentPlace++;
        }
        currentStarCount = starCount;
        return currentPlace;
    }
}
