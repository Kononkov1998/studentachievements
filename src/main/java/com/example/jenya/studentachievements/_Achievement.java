package com.example.jenya.studentachievements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Achievement
{
    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("stars")
    @Expose
    public int stars;

    @SerializedName("achievement")
    @Expose
    public _AchievementInfo achievementInfo;

    public _AchievementInfo getAchievementInfo() {
        return achievementInfo;
    }

    public int getStars() {
        return stars;
    }

    public String getCode() {
        return code;
    }

    public void setAchievementInfo(_AchievementInfo achievementInfo) {
        this.achievementInfo = achievementInfo;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
