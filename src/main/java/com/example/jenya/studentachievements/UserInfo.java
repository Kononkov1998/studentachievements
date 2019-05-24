package com.example.jenya.studentachievements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo
{
    @SerializedName("_id")
    @Expose
    public String _id;

    @SerializedName("idHumanFace")
    @Expose
    public String idHumanFace;

    @SerializedName("institute")
    @Expose
    public String institute;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("group")
    @Expose
    public Group group;

    @SerializedName("fullName")
    @Expose
    public FullName fullName;

    @SerializedName("achievements")
    @Expose
    public _Achievement[] achievements;

    @SerializedName("__v")
    @Expose
    public int __v;

    public _Achievement[] getAchievements() {
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

    public void setAchievements(_Achievement[] achievements) {
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
