package com.example.jenya.studentachievements.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AchievementInfo
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
}
