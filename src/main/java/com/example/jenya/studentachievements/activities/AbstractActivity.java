package com.example.jenya.studentachievements.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AbstractActivity extends AppCompatActivity {
    private BroadcastReceiver receiver;
    private boolean recreateIsNeeded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (receiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("recreateIsNeeded");// a string to identify your action
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // How can I finish the current activity here?
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
