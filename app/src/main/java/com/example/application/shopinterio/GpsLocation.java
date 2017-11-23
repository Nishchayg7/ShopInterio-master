package com.example.application.shopinterio;

/**
 * Created by nishc on 11/13/2017.
 */

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;
import static java.security.AccessController.getContext;


public class GpsLocation implements LocationListener {
    Context context;

    public GpsLocation(Context context) {
        super();
        this.context = context;
    }

    public Location getLocation(){
        if (ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            Log.e("fist","error");
            return null;
        }
        try {
            LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

            Location loc;


                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000,10,this);
                 loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                 if(loc!=null) {
                     Toast.makeText(context, "GPS", Toast.LENGTH_LONG).show();
                     return loc;
                 }


                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 0, this);
                loc=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(loc!=null)
                {
                    Toast.makeText(context,"Network",Toast.LENGTH_LONG).show();
                    return loc;
                }

              lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,0,0,this);
                loc=lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if(loc!=null)
                {
                    Toast.makeText(context,"Passive",Toast.LENGTH_LONG).show();
                    return loc;
                }
                if(!isGPSEnabled) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    this.context.startActivity(i);
                    Log.e("sec", "errpr");
                }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        this.context.startActivity(i);
    }
}