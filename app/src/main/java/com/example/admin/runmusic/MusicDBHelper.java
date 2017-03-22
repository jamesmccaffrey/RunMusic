package com.example.admin.runmusic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.id;
import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 01/03/2017.
 */

public class MusicDBHelper extends SQLiteOpenHelper {
    //Variable declrations
    public static final String DB_NAME = "music.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE1 = "Music";
    //public static final String COLUMN_ID="ID";
    //public static final String COLUMN_NAME = "Track Name";
    //public static final String COLUMN_ARTIST = "Artist";
    //public static final String COLUMN_EMAIL = "Email Address";
    //public static final String COLUMN_ALBULM = "Albulm";
    public static final String COLUMN_LOCATION = "FileLocation";
    public static final String COLUMN_SPEED = "TrackSpeed";
   long id;

/*

    UNUSED CONSTRUCTOR
    public MusicDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    */
    public MusicDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /*
        @Override
        public void onCreate(SQLiteDatabase db) {
        String CREATE_MUSIC_TABLE = "CREATE TABLE "+TABLE1+"("+COLUMN_ID+" INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"+COLUMN_NAME+
                " TEXT,"+COLUMN_ARTIST+" TEXT,"+COLUMN_ALBULM+" TEXT,"+COLUMN_LOCATION+" TEXT,"+COLUMN_EMAIL+" TEXT,"+
                COLUMN_SPEED+" TEXT"+")";
            db.execSQL(CREATE_MUSIC_TABLE);
        }
    */
@Override
public void onCreate(SQLiteDatabase db) {
    String CREATE_MUSIC_TABLE = "CREATE TABLE " + TABLE1 + "(" + COLUMN_LOCATION + " TEXT PRIMARY KEY, "+ COLUMN_SPEED + " TEXT" +");";
    db.execSQL(CREATE_MUSIC_TABLE);
}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        // Create a new one.
        onCreate(db);

    }

    public void add(String path, String speed){
        SQLiteDatabase db = this.getWritableDatabase();
        String lpath=path;
        ContentValues tracks = new ContentValues(); //Content Values object populated through parameters
        tracks.put(COLUMN_LOCATION, path);
        tracks.put(COLUMN_SPEED, speed);
        id=db.insert(TABLE1,null, tracks);
        db.close();
        Log.d(TAG, "Record insered successfully in user table: " + id);
    }

    public String selectTrack(String speed){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+COLUMN_LOCATION+" FROM " + TABLE1+ " WHERE " + COLUMN_SPEED + "=" + "'" + speed + "'"+" ORDER BY RANDOM() LIMIT 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToPosition(0);
        String track=c.getString(0);
        c.close();
        return track;
    }
    public boolean doesFileExist(String url){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE1+" WHERE "+COLUMN_LOCATION+"='"+url+"'";
        Cursor c=db.rawQuery(query,null);
        if (c.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean doesAllExist(){
        SQLiteDatabase db=this.getWritableDatabase();
        Boolean slow=false, medium=false, fast=false;
        String query="SELECT * FROM "+TABLE1+" WHERE "+COLUMN_SPEED+"='Slow'";
        Cursor c=db.rawQuery(query,null);
        if (c.getCount()>0) {slow=true;}

        String query2="SELECT * FROM "+TABLE1+" WHERE "+COLUMN_SPEED+"='Medium'";
        Cursor c2=db.rawQuery(query2,null);
        if (c2.getCount()>0) {medium=true;}

        String query3="SELECT * FROM "+TABLE1+" WHERE "+COLUMN_SPEED+"='Fast'";
        Cursor c3=db.rawQuery(query3,null);
        if (c3.getCount()>0) {fast=true;}

        if (slow&&medium&&fast)
            return true;
        else
            return false;

    }
}
