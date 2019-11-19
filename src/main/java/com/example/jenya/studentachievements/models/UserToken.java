package com.example.jenya.studentachievements.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserToken
{
    @SerializedName("usertoken")
    @Expose
    private String userToken;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUserToken()
    {
        return userToken;
    }

    public void setUserToken(String token)
    {
        this.userToken = token;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

}
