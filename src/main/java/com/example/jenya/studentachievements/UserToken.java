package com.example.jenya.studentachievements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserToken {
    @SerializedName("userToken")
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

    public void setStatus(String message)
    {
        this.status = status;
    }

}
