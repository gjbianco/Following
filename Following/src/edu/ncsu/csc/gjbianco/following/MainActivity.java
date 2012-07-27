package edu.ncsu.csc.gjbianco.following;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity
{
	private LocationManager  locationManager;
	private LocationListener locationListener;
	private boolean          gpsState;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// call parent onCreate()
		super.onCreate(savedInstanceState);

		// set Activity layout
		setContentView(R.layout.main);
		
		ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton1);

		OnCheckedChangeListener occl = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// acquire reference to system Location Manager
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

				if(isChecked) {
					// define location listener
					locationListener = new LocationListener() {
						public void onLocationChanged(Location location) {
							TextView mainTextView = (TextView) findViewById(R.id.textView1);
	
							int latitude  = (int) location.getLatitude();
							int longitude = (int) location.getLongitude();
	
							String out = "lat: " + latitude + "\nlong: " + longitude;
							mainTextView.setText(out);
						}
	
						public void onStatusChanged(String provider, int status, Bundle extras) {}
	
						public void onProviderEnabled(String provider) {}
	
						public void onProviderDisabled(String provider) {}
					};
					
					// register listener with Location Manager
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
					gpsState = true;
				}
				else {
					// deregister listener with Location Manager
					locationManager.removeUpdates(locationListener);
					gpsState = false;
				}
			}
		};

		toggle.setOnCheckedChangeListener(occl);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(gpsState)
			locationManager.removeUpdates(locationListener);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(gpsState)
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}
}
