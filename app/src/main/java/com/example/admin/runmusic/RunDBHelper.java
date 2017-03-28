package com.example.admin.runmusic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 21/02/2017.
 */

public class RunDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "runactivity.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE1 = "PreviousRuns";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_RUNTIME = "Time";
    public static final String COLUMN_AVGSPEED = "AverageSpeed";
    public static final String COLUMN_DISTANCE="Distance";
    long id;

    public RunDBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RUN_TABLE = "CREATE TABLE " + TABLE1 + "(" + COLUMN_DATE + " BIGINT PRIMARY KEY,"
                + COLUMN_RUNTIME + " BIGINT," + COLUMN_AVGSPEED + " FLOAT," + COLUMN_DISTANCE + " REAL" +")";
        db.execSQL(CREATE_RUN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        onCreate(db);
    }

    public void AddRun(long date, long runtime, double avgSpeed, float distance){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues run=new ContentValues();
        run.put(COLUMN_AVGSPEED, avgSpeed);
        run.put(COLUMN_DATE, date);
        run.put(COLUMN_DISTANCE, distance);
        run.put(COLUMN_RUNTIME, runtime);
        id=db.insert(TABLE1,null,run);
        db.close(); //Close sqlite database reference
        Log.d(TAG, "Record insered successfully in user table: " + id);
    }
}
