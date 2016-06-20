/** 
 * AlarmBox 
 * Android application for Assembly line monitoring.
 * The app is pending REST API server and receives json messages
 * 
 * Project: Andon
 * Copyright: Lenvo AB (www.lenvo.se)
 */
package com.automation.andonalarmbox;

public class Alarm {
    //private variables
    int _id;
    String _station;
    String _line_title;
    String _time;

	// Empty constructor
    public Alarm(){
         
    }
    // constructor
    public Alarm(int id, String station, String line_title, String time){
        this._id = id;
        this._station = station;
        this._line_title = line_title;
        this._time = time;
    }
     
    // constructor
    public Alarm(String station, String line_title, String time){
        this._station = station;
        this._line_title = line_title;
        this._time = time;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting station
    public String getStation(){
        return this._station;
    }
     
    // setting station
    public void setStation(String station){
        this._station = station;
    }
     
    // getting line title
    public String getLineTitle(){
        return this._line_title;
    }
     
    // setting phone number
    public void setLineTitle(String line_title){
        this._line_title = line_title;
    }
    
    // getting time
    public String getTime(){
        return this._time;
    }
     
    // setting phone number
    public void setTime(String time){
        this._time = time;
    }
    
}
