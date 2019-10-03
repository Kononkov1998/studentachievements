package com.example.jenya.studentachievements.models;

public class Visibility
{
    private String visibility; // all, me, groupmates

    public Visibility(){}

    public void setAll()
    {
        visibility = "all";
    }

    public void setMe()
    {
        visibility = "me";
    }

    public void setGroupmates()
    {
        visibility = "groupmates";
    }

    public String getVisibility()
    {
        return visibility;
    }
}
