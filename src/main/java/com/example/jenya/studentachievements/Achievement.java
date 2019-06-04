package com.example.jenya.studentachievements;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Achievement
{
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("stars")
    @Expose
    private int stars;

    @SerializedName("achievement")
    @Expose
    private AchievementInfo achievementInfo;

    public AchievementInfo getAchievementInfo() {
        return achievementInfo;
    }

    public int getStars() {
        return stars;
    }

    public String getCode() {
        return code;
    }

    public void setAchievementInfo(AchievementInfo achievementInfo) {
        this.achievementInfo = achievementInfo;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
