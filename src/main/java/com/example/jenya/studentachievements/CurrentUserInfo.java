package com.example.jenya.studentachievements;

public class CurrentUserInfo extends UserInfoParent
{
    private static CurrentUserInfo currentUserInfo; // экземпляр класса

    // метод, необходимый для шаблона 'синглтон'
    public static CurrentUserInfo getInstance()
    {
        if(currentUserInfo == null)
        {
            return new CurrentUserInfo();
        }
        return  currentUserInfo;
    }
}
