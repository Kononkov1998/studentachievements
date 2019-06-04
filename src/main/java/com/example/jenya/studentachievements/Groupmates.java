package com.example.jenya.studentachievements;

import com.google.gson.annotations.Expose;

public class Groupmates
{
    @Expose
    public UserInfo[] userInfo;

    public Groupmates()
    {

    }

    public UserInfo[] getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo[] userInfo) {
        this.userInfo = userInfo;
    }
}
