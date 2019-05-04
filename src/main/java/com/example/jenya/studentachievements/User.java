package com.example.jenya.studentachievements;

public class User {
    private String email; // логин
    private String password; // пароль

    public User(String email, String password)
    {
        if(email.isEmpty() || password.isEmpty())
            throw new IllegalArgumentException("Пустые параметры");

        this.email = email;
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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
