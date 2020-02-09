package com.example.jenya.studentachievements.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class AbstractActivityWithUsers extends AppCompatActivity {
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (receiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("recreate");
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if ("recreate".equals(intent.getAction())) {
                        recreate();
                    }
                }
            };
            registerReceiver(receiver, intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
