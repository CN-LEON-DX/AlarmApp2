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

import java.util.List;

public class AlarmDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="AlarmDataBase";
    private static final String TABLE_NAME="tblAlarm";
    private static final String COLUMN_ID_NAME ="id";
    private static final String COLUMN_TIME="Time";
    private static final String COLUMN_MESSAGE="Message";
    private static final String COLUMN_STATUS="Status";
    private static final String COLUMN_SOUND="sound";
    private static final String COLUMN_REPEAT="repeat";
    private static final String COLUMN_IS_VIBRATE="isVibrate";
    private static final String COLUMN_IS_REPEAT="isRepeat";
    public AlarmDataBase(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_NAME +
                "("+
                COLUMN_ID_NAME +" INT PRIMARY KEY, "+
                COLUMN_TIME +" STRING, "+
                COLUMN_MESSAGE + " STRING, " +
                COLUMN_STATUS + " INT, " +
                COLUMN_REPEAT + " STRING, "+
                COLUMN_SOUND + " STRING, " +
                COLUMN_IS_VIBRATE +" INT," +
                COLUMN_IS_REPEAT + " INT"
                + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    private boolean isTrue(int index){
       return index == 1;
    }
    private int isTrue(boolean index){
        return index?1:0;
    }
    @SuppressLint("Range")
    public void getData(List<Alarm> list){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String[] projection ={COLUMN_ID_NAME, COLUMN_TIME,COLUMN_MESSAGE, COLUMN_STATUS,COLUMN_SOUND,COLUMN_REPEAT,COLUMN_IS_VIBRATE,COLUMN_IS_REPEAT};
            Cursor cursor = db.query(TABLE_NAME,projection,null,null,null,null,null);
            if(cursor!=null){
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_NAME));
                    String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                    String message = cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE));
                    int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));
                    String repeat = cursor.getString(cursor.getColumnIndex(COLUMN_REPEAT));
                    String sound = cursor.getString(cursor.getColumnIndex(COLUMN_SOUND));
                    int vibrate = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_VIBRATE));
                    int isRepeat = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_REPEAT));
                    Alarm alarm = new Alarm(time, id, isTrue(status), message, sound, repeat, isTrue(vibrate), isTrue(isRepeat));
                    list.add(0, alarm);
                }
                cursor.close();


            }
            db.close();
        } catch (Exception e){
                Log.e("ERROR DATABASE1", "LOI DOC GHI DATABASE");
        }
    }
    public void putData(@NonNull Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_NAME, alarm.getId());
        contentValues.put(COLUMN_TIME,alarm.getTime_alarm());
        contentValues.put(COLUMN_MESSAGE,alarm.getMessage());
        contentValues.put(COLUMN_STATUS,isTrue(alarm.getTurnOn()));
        contentValues.put(COLUMN_REPEAT,alarm.getRepeat());
        contentValues.put(COLUMN_SOUND,alarm.getSound());
        contentValues.put(COLUMN_IS_VIBRATE,isTrue(alarm.isVibrate()));
        contentValues.put(COLUMN_IS_REPEAT,isTrue(alarm.isRepeat()));
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }
    public void deleteData(Alarm alarm){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("tag_id","id:"+alarm.getId());
        db.delete(TABLE_NAME,COLUMN_ID_NAME+"=?",new String[]{String.valueOf(alarm.getId())});
        db.close();
    }
    public void updateAlarm(Alarm oldAlarm, Alarm newalarm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_NAME, newalarm.getId());
        contentValues.put(COLUMN_TIME,newalarm.getTime_alarm());
        contentValues.put(COLUMN_MESSAGE,newalarm.getMessage());
        contentValues.put(COLUMN_STATUS,isTrue(newalarm.getTurnOn()));
        contentValues.put(COLUMN_REPEAT,newalarm.getRepeat());
        contentValues.put(COLUMN_SOUND,newalarm.getSound());
        contentValues.put(COLUMN_IS_VIBRATE,isTrue(newalarm.isVibrate()));
        contentValues.put(COLUMN_IS_REPEAT,isTrue(newalarm.isRepeat()));
        db.update(TABLE_NAME,contentValues,COLUMN_ID_NAME+"=?",new String[]{String.valueOf(oldAlarm.getId())});
        db.close();
    }
    public void updateStatusSwitch(Alarm alarm){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS,isTrue(alarm.getTurnOn()));
        db.update(TABLE_NAME,values,COLUMN_ID_NAME+"=?",new String[]{String.valueOf(alarm.getId())});
    }
}
