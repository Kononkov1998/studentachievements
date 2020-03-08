package com.example.jenya.studentachievements.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Mark
{
    @SerializedName("examrating")
    @Expose
    private int examrating;

    @SerializedName("practicerating")
    @Expose
    private int practicerating;

    @SerializedName("isCP")
    @Expose
    private boolean isCP;

    @SerializedName("passTutor")
    @Expose
    private String passTutor;

    @SerializedName("cprating")
    @Expose
    private int cprating;

    @SerializedName("isExam")
    @Expose
    private boolean isExam;

    @SerializedName("idLGS")
    @Expose
    private int idLGS;

    @SerializedName("isCW")
    @Expose
    private boolean isCW;

    @SerializedName("cwTutor")
    @Expose
    private String cwTutor;

    @SerializedName("hoursCount")
    @Expose
    private int hoursCount;

    @SerializedName("isPractice")
    @Expose
    private boolean isPractice;

    @SerializedName("cpTutor")
    @Expose
    private String cpTutor;

    @SerializedName("passrating")
    @Expose
    private int passrating;

    @SerializedName("examTutor")
    @Expose
    private String examTutor;

    @SerializedName("semester")
    @Expose
    private int semester;

    @SerializedName("isPass")
    @Expose
    private boolean isPass;

    @SerializedName("cwrating")
    @Expose
    private int cwrating;

    @SerializedName("subjectName")
    @Expose
    private String subjectName;

    @SerializedName("consultationDate")
    @Expose
    private Date consultationDate;

    @SerializedName("dateOfPass")
    @Expose
    private Date dateOfPass;

    public int getIdLGS() {
        return idLGS;
    }

    public Date getConsultationDate() {
        return consultationDate;
    }

    public Date getDateOfPass() {
        return dateOfPass;
    }

    public int getCprating() {
        return cprating;
    }

    public int getCwrating() {
        return cwrating;
    }

    public int getExamrating() {
        return examrating;
    }

    public int getHoursCount() {
        return hoursCount;
    }

    public int getPassrating() {
        return passrating;
    }

    public int getPracticerating() {
        return practicerating;
    }

    public int getSemester() {
        return semester;
    }

    public String getCpTutor() {
        return cpTutor;
    }

    public String getCwTutor() {
        return cwTutor;
    }

    public String getExamTutor() {
        return examTutor;
    }

    public String getPassTutor() {
        return passTutor;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setIdLGS(int idLGS) {
        this.idLGS = idLGS;
    }

    public void setConsultationDate(Date consultationDate) {
        this.consultationDate = consultationDate;
    }

    public void setCP(boolean CP) {
        isCP = CP;
    }

    public void setCprating(int cprating) {
        this.cprating = cprating;
    }

    public void setCpTutor(String cpTutor) {
        this.cpTutor = cpTutor;
    }

    public void setCW(boolean CW) {
        isCW = CW;
    }

    public void setCwrating(int cwrating) {
        this.cwrating = cwrating;
    }

    public void setCwTutor(String cwTutor) {
        this.cwTutor = cwTutor;
    }

    public void setDateOfPass(Date dateOfPass) {
        this.dateOfPass = dateOfPass;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }

    public void setExamrating(int examrating) {
        this.examrating = examrating;
    }

    public void setExamTutor(String examTutor) {
        this.examTutor = examTutor;
    }

    public void setHoursCount(int hoursCount) {
        this.hoursCount = hoursCount;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public void setPassrating(int passrating) {
        this.passrating = passrating;
    }

    public void setPassTutor(String passTutor) {
        this.passTutor = passTutor;
    }

    public void setPractice(boolean practice) {
        isPractice = practice;
    }

    public void setPracticerating(int practicerating) {
        this.practicerating = practicerating;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
