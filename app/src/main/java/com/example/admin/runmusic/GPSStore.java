package com.example.admin.runmusic;

import android.content.Context;

import java.util.Date;

/**
 * Created by Admin on 17/02/2017.
 */

public class GPSStore {
    double startLat;
    double startLong;
    Date startTime;
    double endLat;
    double endLong;
    Date endTime;


    Context mcontext;

    public GPSStore(Context context){
        this.mcontext=context;
    }

    public void getAverageSpeed(){}
    public void saveStartValues(double Lat, double longit, Date time ){
        startLat=Lat;
        startLong=longit;
        startTime=time;
        //Need to make sure these are only collected once.

    }

    public void saveEndValues(double Lat, double longit, Date time){
        endLat=Lat;
        endLong=longit;
        endTime=time;
    }


}
