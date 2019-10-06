package com.example.jenya.studentachievements.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AchievementInfo implements Parcelable
{
    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("generalProgress")
    @Expose
    private int generalProgress;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("__v")
    @Expose
    private int __v;

    public String getCode() {
        return code;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public int get__v() {
        return __v;
    }

    public int getGeneralProgress() {
        return generalProgress;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGeneralProgress(int generalProgress) {
        this.generalProgress = generalProgress;
    }

    protected AchievementInfo(Parcel in) {
        _id = in.readString();
        description = in.readString();
        generalProgress = in.readInt();
        name = in.readString();
        code = in.readString();
        __v = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(description);
        dest.writeInt(generalProgress);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeInt(__v);
    }

    @SuppressWarnings("unused")
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
