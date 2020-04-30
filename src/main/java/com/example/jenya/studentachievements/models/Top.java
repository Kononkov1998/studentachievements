package com.example.jenya.studentachievements.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Top
{
    @SerializedName("list")
    @Expose
    private ArrayList<UserInfo> list;

    @SerializedName("currentUser")
    @Expose
    private ArrayList<UserInfo> currentUser;

    @SerializedName("pageInfo")
    @Expose
    private ArrayList<PageInfo> pageInfo;

    public ArrayList<UserInfo> getList() {
        return list;
    }

    public ArrayList<PageInfo> getPageInfo() {
        return pageInfo;
    }

    public ArrayList<UserInfo> getCurrentUser() {
        return currentUser;
    }

    public void setPageInfo(ArrayList<PageInfo> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public void setList(ArrayList<UserInfo> list) {
        this.list = list;
    }

    public void setCurrentUser(ArrayList<UserInfo> currentUser) {
        this.currentUser = currentUser;
    }
}
