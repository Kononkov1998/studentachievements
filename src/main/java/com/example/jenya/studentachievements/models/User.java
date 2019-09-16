package com.example.jenya.studentachievements.models;

public class User
{
    private String username; // логин
    private String password; // пароль

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getusername()
    {
        return username;
    }

    public void setusername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}