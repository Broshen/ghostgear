package com.example.boshen.ghost_gear;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    LocationManager locationManager;
    Context mContext;
    Boolean gpsIsEnabled;
    Button logBtn;
    Location location;
    Double latitute, longitute;

    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this.getApplicationContext();

        alertDialog = new AlertDialog.Builder(this);

        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        logBtn = (Button)findViewById(R.id.log_equip);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocation();

                if(location != null && gpsIsEnabled) {//if location is not null, and the gps is enabled
                    alertDialog.setMessage("latitute: " + latitute + " longitute:" + longitute);
                    alertDialog.show();
                    Log.d("gps", "latitute: " + latitute + " longitute:" + longitute);
                }
            }
        });


    }

    public void getLocation(){

        checkGPSenabled();

        if(gpsIsEnabled) {
            try {
                //update the location
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                //get the location
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitute = location.getLatitude();
                    longitute = location.getLongitude();
                } else {
                    alertDialog.setMessage("location is null?");
                    alertDialog.show();
                }
            } catch (SecurityException e) {
                alertDialog.setMessage(e.getMessage());
                alertDialog.show();
                Log.d("gps", e.getMessage());
            }
        }
    }

    public void checkGPSenabled(){
        //check to see if gps is enabled
        gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!gpsIsEnabled){
            alertDialog.setMessage("GPS is not enabled. Enable it first!");
            alertDialog.show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
