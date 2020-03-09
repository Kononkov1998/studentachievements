package com.example.jenya.studentachievements.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudentMarks
{
    @SerializedName("ratings")
    @Expose
    private ArrayList<Mark> ratings;

    @SerializedName("status")
    @Expose
    private String status;
}
