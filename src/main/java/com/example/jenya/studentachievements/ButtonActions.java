package com.example.jenya.studentachievements;

import android.widget.Button;

public class ButtonActions {
    public static void disableButton(Button btn) {
        btn.getBackground().setAlpha(100);
        btn.setTextColor(btn.getTextColors().withAlpha(150));
        btn.setEnabled(false);
    }

    public static void enableButton(Button btn) {
        btn.getBackground().setAlpha(255);
        btn.setTextColor(btn.getTextColors().withAlpha(255));
        btn.setEnabled(true);
    }
}
