package com.example.alarmapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.alarmapp.Base.Clock;

import java.util.ArrayList;
import java.util.List;

public class WatchTimeCityDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="ClockDataBase";
    private static final String TABLE_NAME="tblTimeCity";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_CITY="nameCity";
    private static final String COLUMN_TIME_DIFFERENCES="timeDifferences";
    private static final String COLUMN_TIMEZONE="timeZone";

    public WatchTimeCityDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +
                "("+
                COLUMN_ID + " INTEGER PRIMARY KEY , "+
                COLUMN_CITY +" STRING, "+
                COLUMN_TIME_DIFFERENCES+" STRING,"+
                COLUMN_TIMEZONE + " STRING "
                +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    public void getData(List<Clock> list){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection={COLUMN_CITY,COLUMN_TIME_DIFFERENCES,COLUMN_TIMEZONE};
        Cursor cursor = db.query(TABLE_NAME,projection,null,null,null,null,null);
        if (cursor!=null){
            if (cursor.moveToNext()){
                do{
                    @SuppressLint("Range") String city=cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                    @SuppressLint("Range") String timeDifferences=cursor.getString(cursor.getColumnIndex(COLUMN_TIME_DIFFERENCES));
                    @SuppressLint("Range") String timeZone = cursor.getString(cursor.getColumnIndex(COLUMN_TIMEZONE));
                    Clock clock = new Clock();
                    clock.setCity(city);
                    clock.setTimeDifferences(timeDifferences);
                    clock.setTimeZone(timeZone);
                    list.add(0,clock);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
    }
    public void putData(Clock clock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY,clock.getCity());
        values.put(COLUMN_TIME_DIFFERENCES,clock.getTimeDifferences());
        values.put(COLUMN_TIMEZONE,clock.getTimeZone());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public void deleteData(Clock clock){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME,COLUMN_CITY+"=?",new String[]{clock.getCity()});
        db.close();
    }
    public void deleteData(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
    @SuppressLint("Range")
    public Clock selectData(String nameCity){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection={COLUMN_ID,COLUMN_CITY,COLUMN_TIME_DIFFERENCES,COLUMN_TIMEZONE};
        Cursor cursor = db.query(TABLE_NAME,projection,COLUMN_CITY+"=?",new String[]{nameCity},null,null,null);
        Clock clock=null;
        if(cursor!=null&&cursor.moveToFirst()){
            clock = new Clock();
            clock.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
            clock.setTimeDifferences(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_DIFFERENCES)));
            clock.setTimeZone(cursor.getString(cursor.getColumnIndex(COLUMN_TIMEZONE)));
            db.close();
        }
        return clock;
    }
    public void updateDataTimeZone(@NonNull Clock clock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMEZONE,clock.getTimeZone());
        db.update(TABLE_NAME,values,COLUMN_CITY+"=?",new String[]{clock.getCity()});
        db.close();
    }
    public void updateDataTimeDifferences(Clock clock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_DIFFERENCES,clock.getTimeDifferences());
        db.update(TABLE_NAME,values,COLUMN_CITY+"=?",new String[]{clock.getCity()});
        db.close();
    }
}
