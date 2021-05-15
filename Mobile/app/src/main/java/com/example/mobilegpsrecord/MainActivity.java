package com.example.mobilegpsrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.byungjin.gps.GPSRecordThread;
import me.byungjin.gps.Manager;
import me.byungjin.gps.listeners.RunListener;
import me.byungjin.gps.listeners.SetDestinationListener;

public class MainActivity extends AppCompatActivity {
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(GPSRecordThread.serviceIntent == null){
            serviceIntent = new Intent(this, GPSRecordThread.class);
            startService(serviceIntent);
        }else{
            serviceIntent = GPSRecordThread.serviceIntent;
            Toast.makeText(getApplicationContext(), "Already", Toast.LENGTH_LONG).show();
        }

        this.setting();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceIntent!=null){
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }

    private void setting(){
        //Run
        Button run = (Button)findViewById(R.id.btn_running);
        if(Manager.isRunning()){
            run.setBackgroundColor(Color.GREEN);
        }else{
            run.setBackgroundColor(Color.RED);
        }
        run.setOnClickListener(new RunListener());
        //Set
        Button set = (Button)findViewById(R.id.btn_destination);
        set.setOnClickListener(new SetDestinationListener());
        //Destination
        EditText destination = (EditText)findViewById(R.id.txt_destination);
        Manager.setTxtEl(destination);
        destination.setText(Manager.getDestinationURL());
    }
}