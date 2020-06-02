package com.example.jenya.studentachievements.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractActivity extends AppCompatActivity {
    private BroadcastReceiver receiver;
    private boolean recreateIsNeeded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (receiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("recreateIsNeeded");
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if ("recreateIsNeeded".equals(intent.getAction())) {
                        recreateIsNeeded = true;
                    }
                }
            };
            registerReceiver(receiver, intentFilter);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (recreateIsNeeded) {
            recreateIsNeeded = false;
            recreate();
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
