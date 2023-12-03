package com.example.sossender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LocationManager manager;
    private GPSReceiver receiver;
    double latitude = 0;
    double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendSOS = findViewById(R.id.sos_button);

        sendSOS.setOnClickListener(this);
        receiver = new GPSReceiver(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = (LocationManager)
                    this.getSystemService(Context.LOCATION_SERVICE);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1.0F, receiver);
    }

    @Override
    public void onClick(View view) {
        // Get the current location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        // Send the SMS
        SmsManager sms = SmsManager.getDefault();
        String phoneNumber = "09505549536";
        String messageBody = "Please take me from CHAPTER 10. ANDROID APP #6: S.O.S. MESSAGE SENDER longitude: " + Double.toString(longitude) + " and latitude: " + Double.toString(latitude);
        try {
            sms.sendTextMessage(phoneNumber, null, messageBody ,null, null);
            Toast.makeText(getApplicationContext(),
                    messageBody, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Message sending failed!!! :" +e , Toast.LENGTH_LONG).show();
        }
    }

}