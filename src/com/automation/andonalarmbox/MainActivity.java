package com.automation.andonalarmbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Vibrator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.ListActivity;


import android.widget.SimpleAdapter;


public class MainActivity extends ListActivity {

	// JSON Node names
	private static final String TAG_ID = "id";
	private static final String TAG_STATION = "name";
	private static final String TAG_TIME = "email";
	private static final String TAG_LINE_TITLE = "line title";
	
	ArrayList<HashMap<String, String>> alarms;
	ArrayList<HashMap<String, String>> new_alarms;
	Timer timer;
	String lastEventId ="0";

	public final static String EXTRA_MESSAGE = "com.automation.andonalarmbox.MESSAGE";
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_main);

			this.alarms = new ArrayList<HashMap<String, String>>();
			this.new_alarms = new ArrayList<HashMap<String, String>>();
			
	        timer = new Timer();
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                runOnUiThread(new Runnable() {

	                    @Override
	                    public void run() {
	                        new GetAndonAlarms().execute();
	                    }
	                });
	            }
	        }, 0, 1000);       
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //@SuppressWarnings({ "rawtypes" })
    public void addData(ArrayList<HashMap<String, String>> new_alarms) {	
    	alarms.addAll(0, new_alarms);

		ListAdapter adapter = new SimpleAdapter(
				MainActivity.this, alarms, R.layout.list_alarm2, 
				new String[] { TAG_STATION, TAG_TIME, TAG_LINE_TITLE }, 
				new int[] { R.id.station, R.id.time, R.id.line_title });

		ListView list = getListView();
        list.setAdapter(adapter);
        
        if (!new_alarms.isEmpty() ) 
        {
        	Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
       	 	v.vibrate(1000);
        	final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP2);
        }
    }    
     
    // Async task Class GetAndonAlarms
    private class GetAndonAlarms extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
    	
        @Override
        protected void onPreExecute() {	
            super.onPreExecute();
         }
    	
        @Override   	
    	protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
        	
        	new_alarms = fromJSONtoArrayList();
    		return new_alarms;
    	}
    	
        @Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			super.onPostExecute(result);
			addData(result);
		}
    	
    }
	
	protected ArrayList<HashMap<String, String>> fromJSONtoArrayList() {
		
		ArrayList<HashMap<String, String>> myStationList = new ArrayList<HashMap<String, String>>();
		
		try {
			//URL json = new URL("http://10.0.2.2:8080/RestServiceImpl.svc/json/" + lastEventId);
			//URL json = new URL("http://10.0.2.2:7007/RestServiceImpl.svc/json/" + lastEventId);	
			
			URL json = new URL("http://192.168.1.44:1051/BrokerService/jsim/" + lastEventId);
			
		    //URL json = new URL("http://192.168.1.44:1051/BrokerService/json/" + lastEventId);
			
					
			URLConnection jc = json.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));

			String line = reader.readLine();

			JSONObject jsonResponse = new JSONObject(line);
			
			JSONArray jsonArray = jsonResponse.getJSONArray("JsonEventsResult");
			
			//JSONArray jsonArray = jsonResponse.getJSONArray("JsonEventsSimulatedResult");
			
			//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			//String currentDateandTime = sdf.format(new Date());
			
			for (int i = 0; i < jsonArray.length(); i++) {			
				JSONObject jObject = (JSONObject)jsonArray.get(i);
	
				HashMap<String, String> station = new HashMap<String, String>();
				
				//String id = "" + (i + 1);
				// adding each child node to HashMap key => value
				station.put(TAG_ID, jObject.getString("Id"));
				station.put(TAG_STATION, jObject.getString("StationName"));
				station.put(TAG_TIME, jObject.getString("Timestamp"));
				station.put(TAG_LINE_TITLE, jObject.getString("LineName"));
				
				lastEventId = jObject.getString("Id");
				
				// adding contact to contact list
				myStationList.add(station);
			}
			reader.close();
		} catch (Exception e) {
			 e.printStackTrace();
		}		
		return myStationList;
	} 

	public boolean forceStop(MenuItem item) 
	{
		android.os.Process.killProcess(android.os.Process.myPid());		
		return false;
	}
	
}
