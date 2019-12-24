package com.example.jenya.studentachievements.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserToken
{
    @SerializedName("usertoken")
    @Expose
    private String userToken;

    public String getUserToken()
    {
        return userToken;
    }

    public void setUserToken(String token)
    {
        this.userToken = token;
    }
}
