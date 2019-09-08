package com.example.jenya.studentachievements.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {
    private static UserInfo currentUser;

    public static UserInfo getCurrentUser() {
            return currentUser;
    }

    public static void loadUserInfo(UserInfo user) {
            currentUser = user;
    }

    @SerializedName("_id")
    @Expose
    private String _id;

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
    private Achievement[] achievements;

    @SerializedName("__v")
    @Expose
    private int __v;

    public Achievement[] getAchievements() {
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

    public void setAchievements(Achievement[] achievements) {
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
}
