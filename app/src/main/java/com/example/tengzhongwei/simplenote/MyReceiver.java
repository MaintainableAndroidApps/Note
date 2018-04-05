package com.example.tengzhongwei.simplenote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        Intent notifyIntent = new Intent(context, MyNewIntentService.class);
        notifyIntent.putExtra("title",title);
        notifyIntent.putExtra("content", content);
        context.startService(notifyIntent);
    }
}
