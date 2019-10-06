package com.example.jenya.studentachievements.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Achievement implements Parcelable
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

    protected Achievement(Parcel in) {
        code = in.readString();
        stars = in.readInt();
        achievementInfo = (AchievementInfo) in.readValue(AchievementInfo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeInt(stars);
        dest.writeValue(achievementInfo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Achievement> CREATOR = new Parcelable.Creator<Achievement>() {
        @Override
        public Achievement createFromParcel(Parcel in) {
            return new Achievement(in);
        }

        @Override
        public Achievement[] newArray(int size) {
            return new Achievement[size];
        }
    };
}
