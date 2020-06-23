package com.example.jenya.studentachievements.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Achievement implements Parcelable {
    @SerializedName("stars")
    @Expose
    private int stars;

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("achievement")
    @Expose
    private AchievementInfo achievementInfo;

    public Achievement() {
    }

    public boolean isReceived() {
        return this.stars != 0;
    }

    public AchievementInfo getAchievementInfo() {
        return achievementInfo;
    }

    public int getStars() {
        return stars;
    }

    public void setAchievementInfo(AchievementInfo achievementInfo) {
        this.achievementInfo = achievementInfo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    private Achievement(Parcel in) {
        stars = in.readInt();
        achievementInfo = (AchievementInfo) in.readValue(AchievementInfo.class.getClassLoader());
        date = (Date) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stars);
        dest.writeValue(achievementInfo);
        dest.writeSerializable(date);
    }

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
