package com.example.jenya.studentachievements;

import android.app.Activity;

public class ThemeController {
    private static int currentTheme = 0;

    public static void setCurrentTheme(int currentTheme) {
        ThemeController.currentTheme = currentTheme;
    }

    public static int getCurrentTheme(){
        return currentTheme;
    }

    private final static int APP_THEME_DARK = 0;
    private final static int APP_THEME_LIGHT = 1;

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (currentTheme) {
            case APP_THEME_DARK:
                activity.setTheme(R.style.AppThemeDark);
                break;
            case APP_THEME_LIGHT:
                activity.setTheme(R.style.AppThemeLight);
                break;
        }
    }
}
