package me.byungjin.gps;

import android.content.Context;
import android.widget.EditText;

import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Manager {
    //on
    public static boolean running = false;
    //URL String
    private static String destinationURL;
    //Elements
    private static EditText txtEl = null;
    //MainContent
    public static Context mContent = null;

    //Setter and Getter
    public static void setDestinationURL(String url){
        destinationURL = url;
    }
    public static String getDestinationURL(){
        return destinationURL;
    }
    public static void setRunning(Boolean j){
        running = j;
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
        }
    }

    //Methods
    public static void sendGPSJSON(String latitude, String longitude){
        if(!running) return;

        try{
            URL destination = new URL(destinationURL);
            HttpURLConnection connection = (HttpURLConnection)destination.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);
            String jsonInputString = "{latitude: "+latitude+", longitude: " + longitude+"}";
            try(OutputStream os = connection.getOutputStream()){
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
            }
            connection.disconnect();
        }catch (IOException e){

        }
    }

}
