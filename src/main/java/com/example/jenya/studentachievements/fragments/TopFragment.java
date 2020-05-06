package com.example.jenya.studentachievements.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.jenya.studentachievements.R;
import com.example.jenya.studentachievements.adapters.TopUsersAdapter;
import com.example.jenya.studentachievements.models.UserInfo;
import com.example.jenya.studentachievements.requests.Requests;

import java.util.ArrayList;

public class TopFragment extends Fragment {
    private static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private static final String ALL = "all";
    private static final String GROUP = "group";
    private static final String DIRECTION = "direction";
    private static final String YEAR = "year";
    private static final int ALL_PAGE_NUMBER = 0;
    private static final int YEAR_PAGE_NUMBER = 1;
    private static final int DIRECTION_PAGE_NUMBER = 2;
    private static final int GROUP_PAGE_NUMBER = 3;
    private static final int PAGE_SIZE = 1;


    private TopUsersAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private int pageNumber = 1;
    private boolean listIsLoading = false;
    private View footer;
    private String region;
    private int maxPageNumber = -1;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        footer = inflater.inflate(R.layout.footer_top, container, false);

        swipeRefreshLayout = view.findViewById(R.id.refresh);

        listView = view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.addFooterView(footer);
        footer.setVisibility(View.GONE);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (!listIsLoading) {
                        listIsLoading = true;
                        addItems();
                    }
                }
            }
        });
        return view;
    }

    private String getRegion(int page) {
        switch (page) {
            case ALL_PAGE_NUMBER:
                return ALL;
            case GROUP_PAGE_NUMBER:
                return GROUP;
            case DIRECTION_PAGE_NUMBER:
                return DIRECTION;
            case YEAR_PAGE_NUMBER:
                return YEAR;
        }
        return null;
    }

    private void addItems() {
        footer.setVisibility(View.VISIBLE);
        Requests.getInstance().topStudents(this, pageNumber, PAGE_SIZE, region);
    }

    public void populateListView(ArrayList<UserInfo> students, int numberOfRecords) {
        if (students != null) {
            if (maxPageNumber == -1) {
                maxPageNumber = (int) Math.ceil((double) numberOfRecords / PAGE_SIZE);
            }
            adapter.addAll(students);
            if (pageNumber == maxPageNumber) {
                listView.setOnScrollListener(null);
                listView.removeFooterView(footer);
                return;
            }
            pageNumber++;
        }

        footer.setVisibility(View.GONE);
        listIsLoading = false;
    }
}
