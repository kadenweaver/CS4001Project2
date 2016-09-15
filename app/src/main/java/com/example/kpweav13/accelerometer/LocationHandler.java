package com.example.kpweav13.accelerometer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Observable;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.Observable;

public class LocationHandler
        extends Observable
        implements LocationListener {

    private int THRESHOLD;

    private Sensor gps = null;
    private LocationManager locationManager = null;
    private Location locale = null;



    public LocationHandler(Activity act) {
        locationManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }


    }



    @Override
    public void onLocationChanged(Location location) {
        double[] n = new double[2];
        n[0]=location.getLatitude();
        n[1]=location.getLongitude();
        setChanged();
        notifyObservers(n);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
