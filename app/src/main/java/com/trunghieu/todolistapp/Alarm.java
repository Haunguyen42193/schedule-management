package com.trunghieu.todolistapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.trunghieu.todolistapp.broadcast.AlarmReceiver;

public class Alarm {
    private Context context;
    private long triggerAtMillis;
    private String path;

    public Alarm(Context context, long triggerAtMillis, String path) {
        this.context = context;
        this.triggerAtMillis = triggerAtMillis;
        this.path = path;
    }

    public void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("path", path);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + triggerAtMillis, pendingIntent);
    }

    public Context getContext() {
        return context;
    }

    public long getTriggerAtMillis() {
        return triggerAtMillis;
    }

    public String getPath() {
        return path;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTriggerAtMillis(long triggerAtMillis) {
        this.triggerAtMillis = triggerAtMillis;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
