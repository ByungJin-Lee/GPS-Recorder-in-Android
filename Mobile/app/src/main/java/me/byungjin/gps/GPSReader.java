package me.byungjin.gps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.content.Context.LOCATION_SERVICE;



public class GPSReader implements LocationListener {
    private LocationManager locationManager;

    public Location getLocation() {
        try{
            //Check Permission
            if (ActivityCompat.checkSelfPermission(Manager.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Manager.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            if(ContextCompat.checkSelfPermission(Manager.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(Manager.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return null;
            }
            //After checking
            locationManager = (LocationManager) Manager.context.getSystemService(LOCATION_SERVICE);
            if(locationManager == null) {
                return null;
            }
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Manager.interval, 0f, this);
                if(locationManager != null){
                    return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Manager.interval, 0f, this);
                if(locationManager != null){
                    return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
            return null;
        }catch (Exception e){
            Log.e("Error", "Can't not find Location..." + e.getMessage());
            return null;
        }
    }

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSReader.this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}