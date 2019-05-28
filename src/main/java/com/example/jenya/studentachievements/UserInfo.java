package com.example.jenya.studentachievements;

import android.content.pm.InstrumentationInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo implements Parcelable
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
    public Achievement[] achievements;

    @SerializedName("__v")
    @Expose
    public int __v;

    public Achievement[] getAchievements() {
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

    public void setAchievements(Achievement[] achievements) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(idHumanFace);
        dest.writeString(institute);
        dest.writeString(email);
        dest.writeValue(group);
        dest.writeValue(fullName);
        dest.writeTypedArray(achievements, 0);
        dest.writeInt(__v);
    }

    protected UserInfo(Parcel in) {
        _id = in.readString();
        idHumanFace = in.readString();
        institute = in.readString();
        email = in.readString();
        group = (Group) in.readValue(getClass().getClassLoader());
        fullName = (FullName) in.readValue(FullName.class.getClassLoader());
        achievements = in.createTypedArray(Achievement.CREATOR);
        __v = in.readInt();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
