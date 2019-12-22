package com.example.jenya.studentachievements.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AchievementInfo implements Parcelable
{
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("code")
    @Expose
    private String code;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private AchievementInfo(Parcel in) {
        description = in.readString();
        name = in.readString();
        code = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(name);
        dest.writeString(code);
    }

    public static final Parcelable.Creator<AchievementInfo> CREATOR = new Parcelable.Creator<AchievementInfo>() {
        @Override
        public AchievementInfo createFromParcel(Parcel in) {
            return new AchievementInfo(in);
        }

        @Override
        public AchievementInfo[] newArray(int size) {
            return new AchievementInfo[size];
        }
    };
}
