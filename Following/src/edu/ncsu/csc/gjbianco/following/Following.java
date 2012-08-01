package edu.ncsu.csc.gjbianco.following;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Following extends MapActivity
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
	
							double latitude  = location.getLatitude();
							double longitude = location.getLongitude();
							GeoPoint usersLocation = new GeoPoint( (int) (latitude * 1E6), (int) (longitude * 1E6));
	
							String out = "lat: " + ((int)latitude) + "\nlong: " + ((int)longitude);
							mainTextView.setText(out);
							
							
							// add marker to user's current location
							Drawable marker = Following.this.getResources().getDrawable(R.drawable.marker);
							MapView mapView = (MapView) findViewById(R.id.mapview);
							List<Overlay> mapOverlays = mapView.getOverlays();
							FollowingItemizedOverlay itemizedOverlay = new FollowingItemizedOverlay(marker, Following.this.getApplicationContext());
							
//							GeoPoint mexicoCity = new GeoPoint(19240000,-99120000);
							
//							OverlayItem overlayItem = new OverlayItem(mexicoCity, "Hola, Mundo!", "I'm in Mexico City!");
							OverlayItem overlayItem = new OverlayItem(usersLocation, "Info", "User's current location.");
							itemizedOverlay.addOverlay(overlayItem);
							mapOverlays.add(itemizedOverlay);
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
		
		// make MapView zoomable
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		// load marker image
//		List<Overlay> mapOverlays = mapView.getOverlays();
//		Drawable marker = this.getResources().getDrawable(R.drawable.marker);
//		FollowingItemizedOverlay itemizedOverlay = new FollowingItemizedOverlay(marker, this);
		
//		GeoPoint usersLocation = new GeoPoint(19240000,-99120000);
//		OverlayItem overlayItem = new OverlayItem(usersLocation, "Hola, Mundo!", "I'm in Mexico City!");
//		itemizedOverlay.addOverlay(overlayItem);
//		mapOverlays.add(itemizedOverlay);
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

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
