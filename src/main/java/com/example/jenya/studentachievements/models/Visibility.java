package com.example.jenya.studentachievements.models;

public class Visibility {
    private final static String VISIBILITY_ALL = "all", VISIBILITY_ME = "me", VISIBILITY_GROUP = "groupmates";

    private String visibility; // all, me, groupmates

    public Visibility() {
    }

    public void setAll() {
        visibility = VISIBILITY_ALL;
    }

    public void setMe() {
        visibility = VISIBILITY_ME;
    }

    public void setGroupmates() {
        visibility = VISIBILITY_GROUP;
    }

    public String getVisibility() {
        return visibility;
    }
}
