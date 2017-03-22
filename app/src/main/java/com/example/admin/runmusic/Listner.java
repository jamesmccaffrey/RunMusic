package com.example.admin.runmusic;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.icu.text.DateTimePatternGenerator;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Created by Admin on 20/02/2017.
 */
public class Listner implements LocationListener {

    private static final long MIN_DISTANCE = 5;
    private static final long MIN_TIME = 6000;
    private final Context mcontext;
    //Instantiate variables
    double lat;
    double longit;
    double speed;
    Location location;
    LocationManager mgr;
    boolean GPSEnabled = false;
    boolean NetworkEnabled=false;
    ArrayList<Double> averageSpeed=new ArrayList<Double>();
    ArrayList<Location> locationList=new ArrayList<>();

    public Listner(Context context) {
        this.mcontext = context;
        getLocation();
    }

    private Location getLocation() {
        boolean permission = false;


     /*   if (ActivityCompat.checkSelfPermission(mcontext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)mcontext, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 10); */
        try {

                mgr = (LocationManager) mcontext
                        .getSystemService(Context.LOCATION_SERVICE);
            NetworkEnabled=mgr.isProviderEnabled(NETWORK_PROVIDER);
            GPSEnabled=mgr.isProviderEnabled(GPS_PROVIDER);

            if(GPSEnabled){
                if (location == null) {

                        mgr.requestLocationUpdates(GPS_PROVIDER,
                                MIN_TIME,
                                MIN_DISTANCE,
                                this);

                }
                if (mgr != null)
                    location = mgr.getLastKnownLocation(GPS_PROVIDER);
                if (location != null)
                    lat = location.getLatitude();
                longit = location.getLongitude();

        }

        else{}

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            //Location get values and
            locationList.add(location);
            lat = location.getLatitude();
            speed = location.getSpeed();
            longit = location.getLongitude();
            location.getTime();
            averageSpeed.add(speed);
            Log.d("lat", Double.toString(lat));
            Log.d("long", Double.toString(longit));
            Log.d("speed", Double.toString(speed));
        }
    }

    public void GPSStop() {
        if (mgr != null)
            if (ActivityCompat.checkSelfPermission((Activity)mcontext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity)mcontext, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                }, 10);
                mgr.removeUpdates(Listner.this);
    }}

    public double getLat(){
       // getLocation();
       // while(location!=null)
            lat=location.getLatitude();
        return lat;
    }

    public double getLong(){
       // getLocation();
        //while(location!=null)
            longit=location.getLongitude();

        return longit;
    }

    public long getTime(){
        return location.getTime();
    }

    public double getSpeed(){
        //getLocation();
        //while(location!=null)
            speed=location.getSpeed();
        return speed;
    }
    public double getAverageSpeed(){
        //Add a speed to the list every time location changes
        //Take an average of last 10 in list
        double sum=0;
        if(!averageSpeed.isEmpty()){
            for(double s: averageSpeed){
                sum+=s;
            }
            double ans= sum/averageSpeed.size();
            return ans;
        }
        return sum;
    }

    public double getAverageSpeed2(){
        //Add a speed to the list every time location changes
        //Take an average of last 10 in list
        double sum=0;

        if(locationList.size()>5){
                for (Location s : locationList) {
               while(s.getTime()>=location.getTime()-120){
                   sum+=s.getSpeed();
               }
            }
            double ans= sum/averageSpeed.size();
            return ans;
        }
        return sum;
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
