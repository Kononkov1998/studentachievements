package com.example.jenya.studentachievements.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatar
{
    private static Avatar currentAvatar;

    @SerializedName("_id")
    @Expose
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public static Avatar getCurrentAvatar() {
        return currentAvatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static void setCurrentAvatar(Avatar currentAvatar) {
        Avatar.currentAvatar = currentAvatar;
    }
}
