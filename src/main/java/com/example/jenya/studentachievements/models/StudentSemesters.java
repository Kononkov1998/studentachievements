package com.example.jenya.studentachievements.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudentSemesters
{
    @SerializedName("studentSemesters")
    @Expose
    private ArrayList<Semester> studentSemesters;

    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<Semester> getStudentSemesters() {
        return studentSemesters;
    }

    public void setStudentSemesters(ArrayList<Semester> studentSemesters) {
        this.studentSemesters = studentSemesters;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
