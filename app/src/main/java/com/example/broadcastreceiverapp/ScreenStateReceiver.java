package com.example.broadcastreceiverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ScreenStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            Log.i("It is on","It is on");
            Toast.makeText(context,"It is on",Toast.LENGTH_LONG).show();
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Log.i("It is off","It is off");
            Toast.makeText(context,"It is off",Toast.LENGTH_LONG).show();
        }
    }
}