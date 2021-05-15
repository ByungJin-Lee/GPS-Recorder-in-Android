package me.byungjin.gps;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Manager {
    //MAC
    public static String identification = null;
    //Context
    public static Context context = null;
    //on
    public static boolean running = false;
    //URL String
    private static String destinationURL;
    //Elements
    private static EditText txtEl = null;
    //Delay
    public static long interval = 3000;

    //Setter and Getter
    public static void setDestinationURL(String url){
        destinationURL = url;
    }
    public static String getDestinationURL(){
        return destinationURL;
    }
    public static boolean isRunning(){
        return running;
    }
    public static void setTxtEl(EditText e){
        txtEl = e;
    }
    public static void setURL(){
        if(txtEl != null){
            destinationURL = txtEl.getText().toString();
            Log.e("설정", destinationURL);
        }
    }

    //Methods
    public static void sendGPSJSON(double latitude, double longitude){
        if(!running) return;

        try{
            URL destination = new URL(destinationURL + "/" + identification);
            HttpURLConnection connection = (HttpURLConnection)destination.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);
            String jsonInputString = "{latitude: "+latitude+", longitude: " + longitude+"}";
            try{
                OutputStream os = connection.getOutputStream();
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
            }catch (Exception e){
                Log.e("Error", jsonInputString);
            }
            connection.disconnect();
            Log.e("Send", "Success");
        }catch (Exception e){
            Log.e("Send", e.getMessage());
        }
    }

    public static void setIdentification(String identificationString){
        Log.e("SE", identificationString);
        identification = identificationString;
    }
}
