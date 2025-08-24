package com.romjan.drawing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }
}