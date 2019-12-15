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

    @SerializedName("visibility")
    @Expose
    private String visibility;

    @SerializedName("idHumanFace")
    @Expose
    private String idHumanFace;

    @SerializedName("institute")
    @Expose
    private String institute;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("group")
    @Expose
    private Group group;

    @SerializedName("fullName")
    @Expose
    private FullName fullName;

    @SerializedName("achievements")
    @Expose
    private ArrayList<Achievement> achievements;

    @SerializedName("__v")
    @Expose
    private int __v;

    @SerializedName("favouriteStudents")
    @Expose
    private ArrayList<UserInfo> favouriteStudents;

    public  ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public FullName getFullName() {
        return fullName;
    }

    public Group getGroup() {
        return group;
    }

    public int get__v() {
        return __v;
    }

    public String get_id() {
        return _id;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdHumanFace() {
        return idHumanFace;
    }

    public String getInstitute() {
        return institute;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setAchievements(ArrayList<Achievement> achievements) {
        this.achievements = achievements;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setIdHumanFace(String idHumanFace) {
        this.idHumanFace = idHumanFace;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
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
        UserInfo.getCurrentUser().favouriteStudents = favouriteStudents;
    }

    private UserInfo(Parcel in) {
        _id = in.readString();
        visibility = in.readString();
        idHumanFace = in.readString();
        institute = in.readString();
        email = in.readString();
        group = (Group) in.readValue(Group.class.getClassLoader());
        fullName = (FullName) in.readValue(FullName.class.getClassLoader());
        achievements = in.createTypedArrayList(Achievement.CREATOR);
        favouriteStudents = in.createTypedArrayList(UserInfo.CREATOR);
        __v = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(visibility);
        dest.writeString(idHumanFace);
        dest.writeString(institute);
        dest.writeString(email);
        dest.writeValue(group);
        dest.writeValue(fullName);
        dest.writeTypedList(achievements);
        dest.writeTypedList(favouriteStudents);
        dest.writeInt(__v);
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
}