package com.example.jenya.studentachievements.utils;

import android.app.Activity;

import com.example.jenya.studentachievements.R;

public class ThemeController {
    public final static int APP_THEME_DARK = 1;
    public final static int APP_THEME_LIGHT = 0;

    private static int currentTheme = APP_THEME_LIGHT;

    public static void setCurrentTheme(int currentTheme) {
        ThemeController.currentTheme = currentTheme;
    }

    public static int getCurrentTheme() {
        return currentTheme;
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (currentTheme) {
            case APP_THEME_DARK:
                activity.setTheme(R.style.AppTheme_Dark);
                break;
            case APP_THEME_LIGHT:
                activity.setTheme(R.style.AppTheme_Light);
                break;
        }
    }
}
