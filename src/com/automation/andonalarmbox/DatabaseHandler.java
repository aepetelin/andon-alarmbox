/** 
 * AlarmBox 
 * Android application for Assembly line monitoring.
 * The app is pending REST API server and receives json messages
 * 
 * Project: Andon
 * Copyright: Lenvo AB (www.lenvo.se)
 */
package com.automation.andonalarmbox;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "andonManager";
 
    // Contacts table name
    private static final String TABLE_ALARMS = "alarms";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_STATION = "station";
    private static final String KEY_LINE_TI = "line_title";
    private static final String KEY_TIME = "time";    
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALARMS_TABLE = 
        		"CREATE TABLE " + TABLE_ALARMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_STATION + " TEXT,"
                + KEY_LINE_TI + " TEXT,"
                + KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_ALARMS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
 
        // Create tables again
        onCreate(db);
    }   

 // Adding new contact
    public void addRow(Alarm alarm) {}
     
    // Getting single contact
    public Alarm getRow(int id) { return null; }
     
    // Getting All Contacts
    public List<Alarm> getRows() { return null; }
     
    // Getting contacts Count
    public int getRowsCount() { return 0; }
   
    // Updating single contact
    public int updateRow(Alarm alarm) { return 0; }
     
    // Deleting single contact
    public void deleteRow(Alarm alarm) {}    
    
    
}
