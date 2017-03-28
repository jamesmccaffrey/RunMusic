package com.example.admin.runmusic;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.IllegalFormatException;
import java.util.Locale;

public class AfterRunActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView viewSpeed, viewTime, viewDistance, message;
    private String username;
    private double speed;
    private long time;
    private float distance;
    Button btnMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_run);
        Intent get = getIntent();
        Bundle user = getIntent().getExtras();
        username=user.getString("user");
        speed=user.getDouble("speed");
        time=user.getLong("time");
        distance=user.getFloat("distance");
        viewDistance=(TextView)findViewById(R.id.textEndDist);
        viewSpeed=(TextView)findViewById(R.id.textEndSpeed);
        viewTime=(TextView)findViewById(R.id.textEndTime);
        message=(TextView)findViewById(R.id.textMessage);
        btnMain=(Button)findViewById(R.id.buttonHome);
        btnMain.setOnClickListener(this);
        printValues();
        /*
        message.setText("Good run "+username+"! Well Done.");
        viewDistance.setText(String.valueOf(distance));
        viewSpeed.setText(String.valueOf(speed));
        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        viewTime.setText(String.format("%d:%02d", minutes, seconds));
        */

    }

    private void printValues() {
        message.setText("Good run "+username+"! Well Done.");
        try {
            viewDistance.setText(String.format("%.02f", distance + " m"));
            viewSpeed.setText(String.format("%.02f", speed) + " m/s");
        }
        catch(IllegalFormatException ex){
            viewDistance.setText(String.valueOf(distance));
            viewSpeed.setText(String.valueOf(speed));
        }
        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        viewTime.setText(String.format("%d:%02d", minutes, seconds));
    }


    @Override
    public void onClick(View v) {
        //STOP ALL AND CLEAR ALL
        Intent intent=new Intent(AfterRunActivity.this, MainActivity.class);
        intent.putExtra("user",username);
        startActivity(intent);
    }
}
