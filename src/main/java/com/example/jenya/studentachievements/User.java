package com.example.jenya.studentachievements;

public class User
{
    public String username; // логин
    public String password; // пароль

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
