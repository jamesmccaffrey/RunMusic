package com.example.admin.runmusic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Console;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.admin.runmusic.R.id.btnStart;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;
   // Listner gps=new Listner(this);

    TextView label;
    Button btnStart, btnStop;
    GPSStore store;
    Listner gps;
    double lat;
    double longit;
    double speed;
    PlayMusic play;
    MusicDBHelper db;
    private String username;

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater expand=getMenuInflater();
        expand.inflate(R.menu.settings, menu);
        return super .onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ChangeMusic: // Selection to change password starts ChnagePassword activity
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item); //Default Case
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        label=(TextView)findViewById(R.id.textView);
        gps=new Listner(this);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop=(Button) findViewById(R.id.btnStop);
        store=new GPSStore(this);
        play=new PlayMusic(this);
        Intent get=getIntent();
        Bundle user=getIntent().getExtras();
        username=user.getString("user");
        db=new MusicDBHelper(this);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

/*

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.doesAllExist()==true)
                {
                    Log.d("on Click", "Start button selected");
                    try {
                        play.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //set timer

                    lat = gps.getLat();
                    longit = gps.getLong();
                    speed = gps.getSpeed();
                    long time = gps.getTime();
                    Date date = new Date(time);
                    label.setText("Latitude: " + lat + "\n " + "Longitude: " + longit + "\n " + "Speed: " + speed);
                    store.saveStartValues(lat, longit, date);
                }
                else {
                    Toast.makeText(MainActivity.this, "Add music files for each speed!", Toast.LENGTH_SHORT).show();
                }
                //Set timer to keep getting speed
                //do something with speed to decide music selection


            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Stop button selected");
                lat=gps.getLat();
                longit=gps.getLong();
                long time=gps.getTime();
                play.Stop();
                //STOP GPS FUNCTIONALITY
                //RECORD DISTANCE FROM START POSITION etc etc
            }
        });
        */
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v){
        int num=v.getId();
        if(R.id.btnStart==num){
            if (db.doesAllExist()==false)
            {
                Toast.makeText(MainActivity.this, "Add music files for each speed!", Toast.LENGTH_SHORT).show();
                return;
            }
                Log.d("on Click", "Start button selected");
                try {
                    play.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //set timer

                lat = gps.getLat();
                longit = gps.getLong();
                speed = gps.getSpeed();
                long time = gps.getTime();
                Date date = new Date(time);
                label.setText("Latitude: " + lat + "\n " + "Longitude: " + longit + "\n " + "Speed: " + speed);
                store.saveStartValues(lat, longit, date);


        }
        else if (R.id.btnStop==num){
            Log.d("onClick", "Stop button selected");
            lat=gps.getLat();
            longit=gps.getLong();
            long time=gps.getTime();
            play.Stop();
        }
    }


}
