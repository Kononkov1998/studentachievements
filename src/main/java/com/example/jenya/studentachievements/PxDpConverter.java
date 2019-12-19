package com.example.jenya.studentachievements;

import android.content.Context;

public class PxDpConverter
{
    public static float dpFromPx(float px, Context ctx)
    {
        return px / ctx.getApplicationContext().getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(float dp, Context ctx)
    {
        return dp * ctx.getApplicationContext().getResources().getDisplayMetrics().density;
    }
}
