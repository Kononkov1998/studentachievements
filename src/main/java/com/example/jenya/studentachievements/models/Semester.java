package com.example.jenya.studentachievements.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Semester
{
    @SerializedName("semesterName")
    @Expose
    private String semesterName;

    @SerializedName("idLGS")
    @Expose
    private int idLGS;

    @SerializedName("groupname")
    @Expose
    private int groupname;

    @SerializedName("semesterNumber")
    @Expose
    private int semesterNumber;

    @SerializedName("idSSS")
    @Expose
    private int idSSS;

    public int getGroupname() {
        return groupname;
    }

    public int getIdLGS() {
        return idLGS;
    }

    public int getIdSSS() {
        return idSSS;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setGroupname(int groupname) {
        this.groupname = groupname;
    }

    public void setIdLGS(int idLGS) {
        this.idLGS = idLGS;
    }

    public void setIdSSS(int idSSS) {
        this.idSSS = idSSS;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }
}
