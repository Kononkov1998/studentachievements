package com.example.jenya.studentachievements.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullName implements Parcelable
{

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("patronymic")
    @Expose
    private String patronymic;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    private FullName(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        patronymic = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(patronymic);
    }

    public static final Parcelable.Creator<FullName> CREATOR = new Parcelable.Creator<FullName>() {
        @Override
        public FullName createFromParcel(Parcel in) {
            return new FullName(in);
        }

        @Override
        public FullName[] newArray(int size) {
            return new FullName[size];
        }
    };
}
