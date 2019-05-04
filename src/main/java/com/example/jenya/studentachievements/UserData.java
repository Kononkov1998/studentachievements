package com.example.jenya.studentachievements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("visibilityLevel")
    @Expose
    private Integer visibilityLevel;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("__v")
    @Expose
    private Integer __v;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public Integer getVisibilityLevel()
    {
        return visibilityLevel;
    }

    public void setVisibilityLevel(Integer visibilityLevel)
    {
        this.visibilityLevel = visibilityLevel;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Integer get__V()
    {
        return __v;
    }

    public void set__V(Integer __v)
    {
        this.__v = __v;
    }
}
