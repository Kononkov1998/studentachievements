package com.example.jenya.studentachievements.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.annotation.ColorInt;

import com.example.jenya.studentachievements.R;

public class ImageViewActions {

    public static void setActiveColor(Context ctx, ImageView view) {
        TypedValue typedValue = new TypedValue();
        ctx.getTheme().resolveAttribute(R.attr.ic_active_color, typedValue, true);
        @ColorInt int color = typedValue.data;
        view.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

}
