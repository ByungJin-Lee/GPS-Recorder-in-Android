package me.byungjin.gps;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Manager extends Thread {
    //Identification
    public static String identification = null;
    //Context
    public static Context context = null;
    //on
    public static boolean running = false;
    //URL String
    private static String destinationURL = "";
    //Elements
    private static EditText txtEl = null;
    private static EditText intervalEl = null;
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
    public static void setIntervalEl(EditText e){
        intervalEl = e;
    }
    public static void setDelay(){
        if(intervalEl!=null){
            interval = Long.parseLong(intervalEl.getText().toString())*1000;
        }
    }
    public static void setURL(){
        if(txtEl != null){
            destinationURL = txtEl.getText().toString();
        }
    }

    public static void setIdentification(String identificationString){
        identification = identificationString;
    }
}
