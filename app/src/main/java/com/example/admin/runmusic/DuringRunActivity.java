package com.example.admin.runmusic;


import android.content.Intent;
import android.support.test.espresso.core.deps.guava.base.Stopwatch;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


import static com.example.admin.runmusic.R.id.textTime;
import static com.example.admin.runmusic.R.id.textTrack;
import static com.example.admin.runmusic.R.id.username;


public class DuringRunActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView speed, track, runTime, distance;
    private Button endRun;
    private String username;
    private PlayMusic play;
    private MusicDBHelper db;
    private RunDBHelper runDB;
    long startTime = 0;
    private Listner gps;
    private Long millis;

    //runs without a timer by reposting this handler at the end of the runnable

    Handler timerHandler=new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            runTime.setText(String.format("%d:%02d", minutes, seconds));
            distance.setText(String.format("%.02f", gps.getDistance()) + " m");
            speed.setText(play.speedDecision() );
            if(play.isPlaying()) {
                try {
                    String path = play.currentTrack();
                    String trac = db.getTrack(path);
                    track.setText(trac);
                } catch (NullPointerException ex) {

                }
            }
            timerHandler.postDelayed(this, 500);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_during_run);
        speed=(TextView)findViewById(R.id.textSpeed);
        track = (TextView) findViewById(R.id.textTrack);
        runTime = (TextView) findViewById(R.id.textTime);
        distance = (TextView) findViewById(R.id.textDistance);
        endRun = (Button) findViewById(R.id.buttonEndRun);
        Intent get = getIntent();
        Bundle user = getIntent().getExtras();
        username = user.getString("user");
        endRun.setOnClickListener(this);
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
        play=new PlayMusic(this);
        db=new MusicDBHelper(this);
        runDB=new RunDBHelper(this);
        gps=new Listner(this);
        try {
            play.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        play.Stop();
        long totalTime=millis;
        timerHandler.removeCallbacks(timerRunnable);
        //Save Current Date/Time, Run Time, Run Distance, Average Speed to database
        //Revert back to home screen
        long Now=System.currentTimeMillis();
        double finalAverage=gps.getFinalAverageSpeed();
        float dist=gps.getDistance();
        runDB.AddRun(Now,totalTime, finalAverage,dist);

        Intent intent=new Intent(DuringRunActivity.this, AfterRunActivity.class);
        intent.putExtra("user",username);
        intent.putExtra("time", totalTime);
        intent.putExtra("speed",finalAverage);
        intent.putExtra("distance", dist);
        startActivity(intent);


    }

    //Function to get current track and keep updating
    //Function to get distance ran
    //Function to print time


}
