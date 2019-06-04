package com.example.jenya.studentachievements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {
    private static UserInfo currentUser;

    public static UserInfo getCurrentUser() {
            return currentUser;
    }

    static void loadUserInfo(UserInfo user) {
            currentUser = user;
    }

    @SerializedName("_id")
    @Expose
    String _id;

    @SerializedName("idHumanFace")
    @Expose
    String idHumanFace;

    @SerializedName("institute")
    @Expose
    String institute;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("group")
    @Expose
    Group group;

    @SerializedName("fullName")
    @Expose
    FullName fullName;

    @SerializedName("achievements")
    @Expose
    Achievement[] achievements;

    @SerializedName("__v")
    @Expose
    int __v;

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
