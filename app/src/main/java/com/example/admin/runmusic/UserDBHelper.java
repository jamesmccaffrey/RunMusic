package com.example.admin.runmusic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 21/02/2017.
 */

import android.util.Log;

public class UserDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "loginactivity.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE1 = "Registration";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    long id;

    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REGISTRATION_TABLE = "CREATE TABLE " + TABLE1 + "(" + COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + COLUMN_EMAIL + " TEXT," + COLUMN_PASSWORD + " TEXT" +")";
        db.execSQL(CREATE_REGISTRATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        onCreate(db);
    }

    public void AddUser(String email, String username, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues user=new ContentValues();
        user.put(COLUMN_USERNAME, username);
        user.put(COLUMN_EMAIL,email);
        user.put(COLUMN_PASSWORD, password);
        id=db.insert(TABLE1,null,user);
        db.close(); //Close sqlite database reference
        Log.d(TAG, "Record insered successfully in user table: " + id);
    }

    public boolean CheckUser(String username, String Password){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE1+" WHERE "+COLUMN_USERNAME+"='"+username+"' AND "+COLUMN_PASSWORD+"='"+Password+"'";
        Cursor c=db.rawQuery(query,null);
        if (c.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean usernameExists(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE1+" WHERE "+COLUMN_USERNAME+"='"+username+"'";
        Cursor c=db.rawQuery(query,null);
        if (c.getCount()>0)
            return true;
        else
            return false;
    }
}
