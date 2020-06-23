package com.example.jenya.studentachievements.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

final public class SharedPreferencesActions {

    private SharedPreferencesActions() {
    }

    public static String read(String name, Context ctx){
            return ctx.getSharedPreferences("User", MODE_PRIVATE).getString(name, "");
    }

    public static void save(String name, String value, Context ctx) {
        SharedPreferences.Editor prefEditor = ctx.getSharedPreferences("User", MODE_PRIVATE).edit();
        prefEditor.putString(name, value);
        prefEditor.apply();
    }

    public static void delete(String name, Context ctx) {
        if (check(name, ctx)) {
            SharedPreferences.Editor prefEditor = ctx.getSharedPreferences("User", MODE_PRIVATE).edit();
            prefEditor.remove(name);
            prefEditor.apply();
        }
    }

    public static boolean check(String name, Context ctx) {
        return ctx.getSharedPreferences("User", MODE_PRIVATE).contains(name);
    }

    public static void deleteAll(Context ctx)
    {
        SharedPreferences.Editor prefEditor = ctx.getSharedPreferences("User", MODE_PRIVATE).edit();
        prefEditor.clear();
        prefEditor.apply();
    }
}
