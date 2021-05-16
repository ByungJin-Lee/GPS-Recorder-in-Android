package me.byungjin.gps;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GPSSender extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        Looper.prepare();
        while(Manager.running){
            try{
                GPSReader gpsReader = new GPSReader();
                Location location = gpsReader.getLocation();
                if(location != null){
                    String url = Manager.getDestinationURL() + "/" + Manager.identification + "?latitude="+location.getLatitude()+"&longitude="+location.getLongitude();
                    URL destination = new URL(url);
                    URLConnection connection = destination.openConnection();
                    connection.setUseCaches(false);
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    br.close();
                    Log.v("GPS", "Run at " + url);
                }
            }catch (Exception e){
                Log.e("ERRROR", e.getMessage());
            }
            try {
                Thread.sleep(Manager.interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        while(Manager.running){
//            try{
//                Log.e("Running", "Run");
//                GPSReader gpsReader = new GPSReader();
//                Looper.loop();
//                Location location = gpsReader.getLocation();
//                String url = Manager.getDestinationURL() + "/" + Manager.identification + "?latitude="+location.getLatitude()+"&longitude="+location.getLongitude();
//                URL destination = new URL(url);
//                URLConnection connection = destination.openConnection();
//                connection.setUseCaches(false);
//                InputStream is = connection.getInputStream();
//                BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                br.close();
//            }catch (Exception e){
//                Log.e("ERRROR", e.getMessage());
//            }
//            try {
//                Thread.sleep(Manager.interval);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        return null;
    }
}
