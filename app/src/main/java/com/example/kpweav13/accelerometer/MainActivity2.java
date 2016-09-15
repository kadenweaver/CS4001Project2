package com.example.kpweav13.accelerometer;

/**
 * Created by kpweav13 on 9/14/2016.
 */
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;
import android.Manifest;

public class MainActivity2
        extends AppCompatActivity
        implements Observer {

    // textviews
    private TextView accel_x_view = null;
    private TextView accel_y_view = null;
    private TextView accel_z_view = null;
    private TextView longitude = null;
    private TextView latitude = null;
    private Observable accel;
    private Observable gps;
    public final int TAG_CODE_PERMISSION_LOCATION = 1;



    @Override
    public void update(Observable observable, Object o) {

        if(o instanceof double[]) {
            double[] vals = (double[]) o;
            latitude.setText(Double.toString(vals[0]));
            longitude.setText(Double.toString(vals[1]));
        }
        else{
            float [] values = (float []) o;
            accel_x_view.setText(Float.toString(values[0]));
            accel_y_view.setText(Float.toString(values[1]));
            accel_z_view.setText(Float.toString(values[2]));
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION},TAG_CODE_PERMISSION_LOCATION);
        

        accel_x_view = (TextView) findViewById(R.id.accel_x);
        accel_y_view = (TextView) findViewById(R.id.accel_y);
        accel_z_view = (TextView) findViewById(R.id.accel_z);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        this.accel = new AccelerometerHandler(500, this);
        this.accel.addObserver(this);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            this.gps = new LocationHandler(this);
            this.gps.addObserver(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        accel_x_view.setText(getPreferences(MODE_PRIVATE).getString("X", "0"));
        accel_y_view.setText(getPreferences(MODE_PRIVATE).getString("Y", "0"));
        accel_z_view.setText(getPreferences(MODE_PRIVATE).getString("Z", "0"));
        latitude.setText(getPreferences(MODE_PRIVATE).getString("LAT", "0"));
        longitude.setText(getPreferences(MODE_PRIVATE).getString("LON", "0"));


    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferences(MODE_PRIVATE).edit().putString(
                "X", accel_x_view.getText().toString()).apply();
        getPreferences(MODE_PRIVATE).edit().putString(
                "Y", accel_y_view.getText().toString()).apply();
        getPreferences(MODE_PRIVATE).edit().putString(
                "Z", accel_z_view.getText().toString()).apply();
        getPreferences(MODE_PRIVATE).edit().putString(
                "LAT", latitude.getText().toString()).apply();
        getPreferences(MODE_PRIVATE).edit().putString(
                "LON", longitude.getText().toString()).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}