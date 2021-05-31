package me.byungjin.gps.listeners;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;

import me.byungjin.gps.Manager;
import me.byungjin.gps.GPSService;

public class RunListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Manager.running = !Manager.running;
        if(Manager.running){
            Manager.setDelay();
            v.setBackgroundColor(Color.GREEN);
            //Foreground
            if(Manager.context != null){
                Intent serviceIntent = new Intent(Manager.context, GPSService.class);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    Manager.context.startForegroundService(serviceIntent);
                }else{
                    Manager.context.startService(serviceIntent);
                }
            }
        }else{
            v.setBackgroundColor(Color.RED);
        }
    }
}
