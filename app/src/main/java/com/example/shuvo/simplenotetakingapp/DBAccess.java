package com.example.shuvo.simplenotetakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shuvo on 4/28/2017.
 */
public class DBAccess {
    private SQLiteDatabase database;
    private DBHelper openHelper;
    private static volatile DBAccess instance;
    public static int id=1;
    public static final String PREFS_NAME = "MyPrefsFile";

    public void restoreID(Context ctx)
    {
        SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        id = pref.getInt("id",1);
    }
    public DBAccess(Context context) {
        openHelper = new DBHelper(context);
    }

    public static synchronized DBAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DBAccess(context);
        }
        return instance;
    }

    public void open() {
        database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public void save(String str,Context ct) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");
        Date date =  new Date();
        String dd= dateFormat.format(date);

        database.execSQL("INSERT INTO " + DBHelper.TABLE+ " VALUES('"+dd+"','"+str+"');");
        id=id+1;
        SharedPreferences pref = ct.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("id",id).commit();

    }



}
