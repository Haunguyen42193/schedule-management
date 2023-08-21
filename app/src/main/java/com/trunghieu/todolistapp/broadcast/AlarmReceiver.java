package com.trunghieu.todolistapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.trunghieu.todolistapp.services.MusicService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String path = intent.getStringExtra("path");
        Intent intent1 = new Intent(context, MusicService.class);
        intent1.putExtra("path", path);
        context.startService(intent1);
    }
}
