package com.trunghieu.todolistapp.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

import com.trunghieu.todolistapp.R;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String path = intent.getStringExtra("path");
        File f = new File(path);
        String fileName = f.getName();
        fileName = fileName.substring(0, fileName.length() - 4);
        if (path != null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(fileName, "raw", getPackageName()));
            mediaPlayer.start();// Thay your_audio_file bằng tên tệp âm thanh trong res/raw
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    stopSelf();
                }
            }, 10000);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
