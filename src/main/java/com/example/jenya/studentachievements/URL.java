package com.example.jenya.studentachievements;

public class URL
{
    @SuppressWarnings("FieldCanBeLocal")
    private static String currentURL = "http://192.168.1.70:8080";

    private URL()
    {

    }

    public static String getCurrentURL() {
        return currentURL;
    }
}
