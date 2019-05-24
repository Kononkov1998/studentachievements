package com.example.jenya.studentachievements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _AchievementInfo
{
    @SerializedName("_id")
    @Expose
    public String _id;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("generalProgress")
    @Expose
    public int generalProgress;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("__v")
    @Expose
    public int __v;

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
