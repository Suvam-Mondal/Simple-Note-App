package com.example.shuvo.simplenotetakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shuvo on 4/28/2017.
 */
public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE = "memos.db";
    public static final String TABLE = "memo";
    public static final int VERSION = 1;
    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE memo(dat VARCHAR,content VARCHAR);");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public List<String> getData() {

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM memo",null);
        List<String> results = new ArrayList<String>();

        int iCM = c.getColumnIndex("content");
        int date= c.getColumnIndex("dat");
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            results.add("\t\t\t\t\t\t\t\t\t"+c.getString(date)+"\n \n"+"  "+c.getString(iCM));

        }
        return results;
    }


    public void onDelete(String note_date,String str)
    {


        getWritableDatabase().execSQL("DELETE FROM "+ DBHelper.TABLE+ " WHERE dat='"+note_date+"' and content LIKE '"+str+".';");

    }
    public void onEdit(String note_date,String str,String con)
    {

        getWritableDatabase().execSQL("UPDATE "+ DBHelper.TABLE+ " SET content = '"+ str +".' WHERE dat='"+note_date+"' and content LIKE '"+con+".%';");

    }

}
