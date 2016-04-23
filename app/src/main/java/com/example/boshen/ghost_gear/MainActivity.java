package com.example.boshen.ghost_gear;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements LocationSource, LocationListener, OnMapReadyCallback{

    LocationManager locationManager;
    Context mContext;
    Boolean gpsIsEnabled, ispublic;
    Button logBtn, manageBtn;
    EditText trapId;
    CheckBox makepublic;
    Double latitute, longitute;
    server myserver;
    private GoogleMap mMap;

    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myserver = new server();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Build Alert Dialog
        mContext = this.getApplicationContext();
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mMap = mapFragment.getMap();
        mMap.setMyLocationEnabled(true);

        // Initialize buttons
        logBtn = (Button)findViewById(R.id.log_equip);
        manageBtn = (Button)findViewById(R.id.manage_equip);
        trapId = (EditText)findViewById(R.id.trapId);
        makepublic = (CheckBox)findViewById(R.id.ispublic);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id = trapId.getText().toString();
                ispublic = makepublic.isChecked();


                getLocation();
                myserver.logTrap(Id, latitute, longitute, ispublic);
            }
        });

        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myserver.getAllTraps();

                getLocation();

                double[] lats = myserver.lats;
                double[] longs = myserver.longs;
                String[] times = myserver.times;
                int len= myserver.arrlen;
                for(int i=0; i<len; i++){
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lats[i], longs[i]))
                            .title(times[i]));
                }
                //Intent intent = new Intent (v.getContext(), MapsActivity.class);
                //startActivity(intent);
            }
        });


    }

    public void getLocation(){

        checkGPSenabled();

        if(gpsIsEnabled) {
            try {
                Location myLocation = mMap.getMyLocation();  //Nullpointer exception.........

                if (myLocation != null) {
                    LatLng myLatLng = new LatLng(myLocation.getLatitude(),
                            myLocation.getLongitude());

                    CameraPosition myPosition = new CameraPosition.Builder()
                            .target(myLatLng).zoom(15).build();

                    mMap.animateCamera(
                            CameraUpdateFactory.newCameraPosition(myPosition));

                    latitute = myLocation.getLatitude();
                    longitute = myLocation.getLongitude();
                    alertDialog.setMessage(latitute + " " + longitute);
                    alertDialog.show();
                } else {
                    alertDialog.setMessage("location is null");
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
        latitute = location.getLatitude();
        longitute = location.getLongitude();
        alertDialog.setMessage("onlocationchanged worked!");
        alertDialog.show();
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

    public void activate(OnLocationChangedListener locationChangedListener){

    }

    public void deactivate(){

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getMyLocation();
        getLocation();
    }

}
