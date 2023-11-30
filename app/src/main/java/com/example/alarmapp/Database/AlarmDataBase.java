package com.example.alarmapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.alarmapp.Base.Alarm;
import com.example.alarmapp.Base.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class AlarmDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="AlarmDataBase";
    private static final String TABLE_NAME="tblAlarm";
    private static final String COLUMN_ID_NAME ="id";
    private static final String COLUMN_TIME="Time";
    private static final String COLUMN_MESSAGE="Message";
    private static final String COLUMN_STATUS="Status";
    public AlarmDataBase(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_NAME +
                "("+
                COLUMN_ID_NAME +" INTEGER PRIMARY KEY, "+
                COLUMN_TIME +" STRING, "+
                COLUMN_MESSAGE + " STRING, " +
                COLUMN_STATUS + " BOOLEAN "
                + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    public void getData(List<Alarm> list){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String[] projection ={COLUMN_ID_NAME, COLUMN_TIME,COLUMN_MESSAGE, COLUMN_STATUS};
            Cursor cursor = db.query(TABLE_NAME,projection,null,null,null,null,null);
            if(cursor!=null){
                if(cursor.moveToNext()){
                    do{
                        String index = String.valueOf(list.size()+1);
                        @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                        @SuppressLint("Range") String message =cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE));
                        @SuppressLint("Range") Boolean isTurnOn = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                        list.add(0,new Alarm(index,time,isTurnOn, message));
                        Log.i("TAG", String.valueOf(isTurnOn));
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
            db.close();
        } catch (Exception e){
                Log.i("ERROR DATABASE1", "LOI DOC GHI DATABASE");
        }
    }
    public void putData(@NonNull Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_NAME, alarm.getId());
        contentValues.put(COLUMN_TIME,alarm.getTime_alarm());
        contentValues.put(COLUMN_MESSAGE,alarm.getMessage());
        contentValues.put(COLUMN_STATUS, alarm.getTurnOn()+"");
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }
    public void clearData(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
    public void deleteData(Alarm alarm){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME,COLUMN_ID_NAME+"=?",new String[]{String.valueOf(alarm.getId())});
        db.close();
    }
    public void updateDatabase(Alarm alarm){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_NAME,alarm.getId());
        values.put(COLUMN_TIME,alarm.getTime_alarm());
        values.put(COLUMN_MESSAGE,alarm.getMessage());
        values.put(COLUMN_STATUS, alarm.getTurnOn());
        db.update(TABLE_NAME,values,COLUMN_ID_NAME+"=?",new String[]{alarm.getId()});
    }
}
