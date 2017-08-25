package com.example.maps3;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;

public class Maps extends MapActivity implements LocationListener {
	MapView mymap;
	int x,y;
	Geocoder geocoder;
	List<Overlay> overlaylst;
	MyLocationOverlay compass;
	Drawable d;
	String abc;
	LocationManager lm;
	JSONObject locobj;
	HttpClient client;
	 TextView tv;
	 
	final static String URL="https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Sydney&sensor=true&key=AIzaSyCkjlbpgXoCfq2lnXM-OG94n4ymjYQRbBo";
	String towers;
	int lat=0;
	int longi=0;
	GeoPoint myloc,myloc2;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		compass.disableCompass();
		super.onPause();
		lm.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		compass.enableCompass();
		super.onResume();
		lm.requestLocationUpdates(towers, 500, 1, this);
	}
	MapController controller;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		mymap=(MapView)findViewById(R.id.mapview2);
		mymap.setBuiltInZoomControls(true);
	//	TextView tvv=(TextView) findViewById(R.id.tvmaps);
        Touch mytouch=new Touch();
        overlaylst=mymap.getOverlays();
		overlaylst.add(mytouch);
		compass=new MyLocationOverlay(Maps.this,mymap);
		overlaylst.add(compass);
		controller=mymap.getController();
		//GeoPoint point=new GeoPoint(19047616,72872229);
        //controller.animateTo(point);
       controller.setZoom(8);
        mymap.setSatellite(false);
        d=getResources().getDrawable(R.drawable.ic_launcher);
        lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria crit=new Criteria();
		towers=lm.getBestProvider(crit, false);
		Location location=lm.getLastKnownLocation(towers);
		if(location!=null){
			lat=(int) (location.getLatitude()*1E6);
			longi=(int) (location.getLongitude()*1E6);
			 myloc=new GeoPoint(lat,longi);
			 abc=stringify(location.getLatitude(),location.getLongitude());
					 //tv.setText(abc);
			//tvv.setText(myloc.toString());
			OverlayItem overlayitem=new OverlayItem(myloc,"My Location","loc");
		    CustomPinpoint cust=new CustomPinpoint(d,Maps.this);
		    cust.insertPinpoint(overlayitem);
		    overlaylst.add(cust);
		   // tv=(TextView)findViewById(R.id.texttest);	      
		   try{
		    	new Read().execute("name");
		   }
		    catch(Exception e)
		    {
		    	
		    }
           
            
	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
	
		System.out.println("Eclipse");
		// TODO Auto-generated method stub
		return false;
	}
	class Touch extends Overlay{

		@Override
		public boolean onTouchEvent(MotionEvent arg0, MapView arg1) {
			// TODO Auto-generated method stub
		return super.onTouchEvent(arg0, arg1);
			/*if(arg0.getAction()==MotionEvent.ACTION_DOWN)
			{
				x=(int) arg0.getX();
				y=(int) arg0.getY();
				GeoPoint touchedPoint=mymap.getProjection().fromPixels(x, y);
			}
			return true; */
		}
		
	}
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		lat=(int) (arg0.getLatitude()*1E6);
		longi=(int) (arg0.getLongitude()*1E6);
		GeoPoint myloc=new GeoPoint(lat,longi);
		OverlayItem overlayitem=new OverlayItem(myloc,"My Location","loc");
	    CustomPinpoint cust=new CustomPinpoint(d,Maps.this);
	    cust.insertPinpoint(overlayitem);
	    overlaylst.add(cust);

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	public JSONObject getLast(String str) throws Exception
	{
		StringBuilder url=new StringBuilder(URL);
		
	//	url.append("location="+abc);
	//	url.append("&radius=1000&sensor=true&types=restaurant&key=AIzaSyCkjlbpgXoCfq2lnXM-OG94n4ymjYQRbBo");
		
		System.out.println("Pahunch gaye");
		HttpGet get=new HttpGet(url.toString());
		 HttpResponse r = client.execute(get);
		 tv.append("Object mildada");
		 int status = r.getStatusLine().getStatusCode();
		 if (status == 200){
				HttpEntity e=r.getEntity();
				String data = EntityUtils.toString(e);
				JSONObject result=new JSONObject(data);
				JSONArray arr=result.getJSONArray("results");
				JSONObject result2=arr.getJSONObject(0);
			
			//	JSONObject result3=result2.getJSONObject("geometry");
			//	JSONObject result4=result3.getJSONObject("location");
			     return result2;
		 }
		 else
		return null;
	}
	public class Read extends AsyncTask<String,Integer,String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
		    	locobj=getLast("Hello");
		        String test=locobj.getString("name");
		    	return test;
		        
		    	}
			catch(Exception e)
		    {
		    	
		    }
			return null;
		}
		@Override
		protected void onPostExecute(String str) {
		 
		     //String test=myobj.getString("name");
			 tv.append("bhangda");

			// TODO Auto-generated method stub
						/*	for(int i=0;i<10;i++)
				{
					JSONObject json,json2,json3;
					json=myobj.getJSONObject(i);
					json2=json.getJSONObject("geometry");
					json3=json2.getJSONObject("location");*/
			/*	double one=myobj.getDouble("lat");
	        	double two=myobj.getDouble("lng");     */

	/*		 tv.setText(String.valueOf(one));
	        Log.d("Coordinates", one+" "+two);
	    	int lat2,long2;
	    	//19.043742,72.864633
	    	lat2=(int) (one*1E6);
	    	long2=(int)(two*1E6);
	    	myloc2=new GeoPoint(lat2,long2);
	    	OverlayItem overlayitem2=new OverlayItem(myloc2,"My Location","loc");
		    CustomPinpoint cust2=new CustomPinpoint(d,Maps.this);
		    cust2.insertPinpoint(overlayitem2);
		    overlaylst.add(cust2);*/
			 
			}
		
	}

	public String stringify(double one,double two)
	{
		String str1=String.valueOf(one);
		String str2=String.valueOf(two);
		String str3=str1+","+str2;
		return str3;
	}
}