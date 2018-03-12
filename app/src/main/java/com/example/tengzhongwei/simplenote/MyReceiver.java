package com.example.tengzhongwei.simplenote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notifyIntent = new Intent(context, MyNewIntentService.class);
        context.startService(notifyIntent);
    }
}
