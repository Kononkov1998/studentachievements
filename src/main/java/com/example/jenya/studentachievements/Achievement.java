package com.example.jenya.studentachievements;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Achievement
{
    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("stars")
    @Expose
    public int stars;

    @SerializedName("achievement")
    @Expose
    public AchievementInfo achievementInfo;

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
