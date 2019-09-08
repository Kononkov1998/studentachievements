package com.example.jenya.studentachievements.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Group
{
    @SerializedName("_id")
    @Expose
    public String _id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("id")
    @Expose
    public String id;

    public String get_id() {
        return _id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
