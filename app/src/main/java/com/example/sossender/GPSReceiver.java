package com.example.sossender;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class GPSReceiver implements LocationListener {
    Context context;
    double latitude = 0;
    double longitude = 0;


    public GPSReceiver(Context context) {
        this.context = context;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Toast.makeText(context,
                    "READY TO SEND!!!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "NOT READY YET...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onProviderEnabled(String s){
        Toast.makeText(context, "GPS Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(context, "Please enable GPS!", Toast.LENGTH_LONG).show();
    }
}