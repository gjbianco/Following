package edu.ncsu.csc.gjbianco.following;

import com.google.android.maps.MapView;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	// call parent onCreate()
        super.onCreate(savedInstanceState);
        
        // set Activity layout
        setContentView(R.layout.main);
        
        // get textview and button
        TextView mainTextView = (TextView) findViewById(R.id.textView1);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton1);
        
        // acquire reference to system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        
        // define location listener
        LocationListener locationListener = new LocationListener() {
        	public void onLocationChanged(Location location) {
        		// called when a new location is found by network location provider
		        TextView mainTextView = (TextView) findViewById(R.id.textView1);
        		mainTextView.setText(location.toString());
        	}
        	
        	public void onStatusChanged(String provider, int status, Bundle extras) {}
        	
        	public void onProviderEnabled(String provider) {}
        	
        	public void onProviderDisabled(String provider) {}
        };
        
        // register listener with Location Manager
//        if(toggle.isChecked())
	        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        else
//        	locationManager.removeUpdates(locationListener);
    }
}
