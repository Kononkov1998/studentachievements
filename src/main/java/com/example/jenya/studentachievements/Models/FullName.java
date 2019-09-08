package com.example.jenya.studentachievements.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullName implements Parcelable
{
    @SerializedName("_id")
    @Expose
    public String _id;

    @SerializedName("firstName")
    @Expose
    public String firstName;

    @SerializedName("lastName")
    @Expose
    public String lastName;

    @SerializedName("patronymic")
    @Expose
    public String patronymic;

    public String get_id() {
        return _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(patronymic);
    }

    protected FullName(Parcel in) {
        _id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        patronymic = in.readString();
    }

    public static final Creator<FullName> CREATOR = new Creator<FullName>() {
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
