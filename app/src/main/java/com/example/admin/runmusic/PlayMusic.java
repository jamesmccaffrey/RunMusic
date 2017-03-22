package com.example.admin.runmusic;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Admin on 19/03/2017.
 */

//Play at start
//GET a current average speed
    //Update a variable for next track
public class PlayMusic implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    MediaPlayer mediaPlayer=new MediaPlayer();
    MusicDBHelper db;
   Listner gps;
String currentTrack;
    String nextTrack;


    Context mcontext;

    public PlayMusic(Context context){
        this.mcontext=context;

    }

    public void start() throws IOException {
        db = new MusicDBHelper(mcontext);
        gps = new Listner(mcontext);

       // mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
       mediaPlayer.setOnCompletionListener(this);

        currentTrack = db.selectTrack("Slow");
        String filePath = Environment.getExternalStorageDirectory().getPath() + currentTrack;
        File filPath = new File(currentTrack);
        //mediaPlayer.reset();
        /*
        if (!filPath.exists())
        {
            filPath.createNewFile();
        }

        FileInputStream is = new FileInputStream(filPath);
        */

        try {
            mediaPlayer.setDataSource(currentTrack); //THIS IS WHERE PROBLEM IS
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(IllegalStateException e){
            e.printStackTrace();
        }
            mediaPlayer.prepareAsync();



        //mediaPlayer.start();
    }

    public String speedDecision(){
        double speed=gps.getAverageSpeed();
        speed=1.5;
        if(speed<=1){
            return "Slow";
        }
        else if (speed>1&&speed<2){
            return "Medium";
        }
        else{
            return "Fast";
        }
    }



    public void Stop(){

        mediaPlayer.stop();
        //new view then for run activity
        //get calculated distance and overall average speed
        //maybe show on map

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();

    }
    @Override
    public void onCompletion(MediaPlayer mp) {
         if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        String trackSpeed=speedDecision();
        String nextTrack=db.selectTrack(trackSpeed);
        while (nextTrack.equals(currentTrack)){
            nextTrack=db.selectTrack(trackSpeed);
        }
        String path=Environment.getExternalStorageDirectory()+nextTrack;

        try {

            mediaPlayer.setDataSource(nextTrack);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(IllegalStateException e){
            e.printStackTrace();
        }

        currentTrack=nextTrack;
        mediaPlayer.prepareAsync();

    }

}
