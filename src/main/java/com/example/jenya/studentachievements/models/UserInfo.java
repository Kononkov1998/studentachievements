package com.example.jenya.studentachievements.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserInfo implements Parcelable {
    private static UserInfo currentUser;

    public static UserInfo getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserInfo user) {
        currentUser = user;
    }

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("isAvailable")
    @Expose
    private boolean isAvailable = true;

    @SerializedName("visibility")
    @Expose
    private String visibility;

    @SerializedName("group")
    @Expose
    private Group group;

    @SerializedName("fullName")
    @Expose
    private FullName fullName;

    @SerializedName("achievements")
    @Expose
    private ArrayList<Achievement> achievements;

    @SerializedName("favouriteStudents")
    @Expose
    private ArrayList<UserInfo> favouriteStudents;

    @SerializedName("starCount")
    @Expose
    private int starCount;

    public  ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public FullName getFullName() {
        return fullName;
    }

    public Group getGroup() {
        return group;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAchievements(ArrayList<Achievement> achievements) {
        this.achievements = achievements;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean getIsAvailable()
    {
        return this.isAvailable;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public ArrayList<UserInfo> getFavouriteStudents() {
        return favouriteStudents;
    }

    public void setFavouriteStudents(ArrayList<UserInfo> favouriteStudents) {
        this.favouriteStudents = favouriteStudents;
    }

    private UserInfo(Parcel in) {
        _id = in.readString();
        visibility = in.readString();
        group = (Group) in.readValue(Group.class.getClassLoader());
        fullName = (FullName) in.readValue(FullName.class.getClassLoader());
        achievements = in.createTypedArrayList(Achievement.CREATOR);
        favouriteStudents = in.createTypedArrayList(UserInfo.CREATOR);
        avatar = in.readString();
        starCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(visibility);
        dest.writeValue(group);
        dest.writeValue(fullName);
        dest.writeTypedList(achievements);
        dest.writeTypedList(favouriteStudents);
        dest.writeString(avatar);
        dest.writeInt(starCount);
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }
}