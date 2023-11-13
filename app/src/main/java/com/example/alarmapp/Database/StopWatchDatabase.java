package com.example.alarmapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alarmapp.Base.StopWatch;
import java.util.ArrayList;
import java.util.List;

public class StopWatchDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="clockApp";
    private static final String TABLE_NAME="tblStopWatchTimeMark";
    private static final String COLUMN_ID_NAME ="id";
    private static final String COLUMN_TIME_ADD_NAME="timeAdd";
    private static final String COLUMN_TIME_RECORD_NAME="timeRecord";
    public StopWatchDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_NAME +
                        "("+
                            COLUMN_ID_NAME +" INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT(0), "+
                            COLUMN_TIME_RECORD_NAME +" STRING, "+
                            COLUMN_TIME_ADD_NAME + " STRING "
                        + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    public void getData(List<StopWatch> list){
        Log.i("TAG_DATA","ok");
        SQLiteDatabase db=this.getReadableDatabase();
        String[] projection ={COLUMN_TIME_RECORD_NAME,COLUMN_TIME_ADD_NAME};
        Cursor cursor = db.query(TABLE_NAME,projection,null,null,null,null,null);
        if(cursor!=null){
            if(cursor.moveToNext()){
                do{
                    String index = String.valueOf(list.size()+1);
                    @SuppressLint("Range") String timeAdd = cursor.getString(cursor.getColumnIndex(COLUMN_TIME_ADD_NAME));
                    @SuppressLint("Range") String timeRecord=cursor.getString(cursor.getColumnIndex(COLUMN_TIME_RECORD_NAME));
                    list.add(0,new StopWatch(index,timeRecord,timeAdd));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
    }
    public void putData(StopWatch stopWatch){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TIME_RECORD_NAME,stopWatch.getTimeRecord());
        contentValues.put(COLUMN_TIME_ADD_NAME,stopWatch.getTimeAdd());
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }
    public void clearData(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}
