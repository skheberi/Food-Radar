package com.example.maps3;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.maps3.Maps.Touch;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class Demo extends MapActivity implements LocationListener{
	MapView mymap;
	int x,y;
	Geocoder geocoder;
	List<Overlay> overlaylst;
	MyLocationOverlay compass;
	Drawable d,d2;
	String abc;
	LocationManager lm;
	MapController controller;
	HttpClient client;
	JSONArray json;
	TextView tt;
	String towers;
	int lat=0;
	int longi=0;
	GeoPoint myloc,myloc2;

	final static String URL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		mymap=(MapView)findViewById(R.id.mapview2);
		mymap.setBuiltInZoomControls(true);
		
	//	TextView tvv=(TextView) findViewById(R.id.tvmaps);
       // Touch mytouch=new Touch();
        overlaylst=mymap.getOverlays();
	//	overlaylst.add(mytouch);
		compass=new MyLocationOverlay(Demo.this,mymap);
		overlaylst.add(compass);
		controller=mymap.getController();
		//GeoPoint point=new GeoPoint(19047616,72872229);
        //controller.animateTo(point);
		
       controller.setZoom(12);
        mymap.setSatellite(false);
        d=getResources().getDrawable(R.drawable.green);
        d2=getResources().getDrawable(R.drawable.red);
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
			 controller.setCenter(myloc);
			OverlayItem overlayitem=new OverlayItem(myloc,"My Location","loc");
		    CustomPinpoint cust=new CustomPinpoint(d2,Demo.this);
		    cust.insertPinpoint(overlayitem);
		    overlaylst.add(cust);
		}
		// tt=(TextView)findViewById(R.id.texttest);
		client=new DefaultHttpClient();
		new Read().execute("text");
	}
	
public JSONArray getLast(String username) throws Exception
{
	StringBuilder url=new StringBuilder(URL);
	url.append("location="+abc);
	url.append("&radius=1000&sensor=true&types=restaurant&key=AIzaSyCkjlbpgXoCfq2lnXM-OG94n4ymjYQRbBo");
	HttpGet get=new HttpGet(url.toString());
	HttpResponse r=client.execute(get);
	 int status = r.getStatusLine().getStatusCode();
	 if (status == 200){
			HttpEntity e=r.getEntity();
			String data = EntityUtils.toString(e);
			JSONObject result=new JSONObject(data);
			JSONArray arr=result.getJSONArray("results");
						return arr;
	 }
	 else
		 return null;
}
public class Read extends AsyncTask<String,Integer,JSONArray>
{

	@Override
	protected JSONArray doInBackground(String... params) {
		// TODO Auto-generated method stub
		try
		{
		json=getLast("mybringback");
		return json;
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONArray myobj) {
		// TODO Auto-generated method stub
		try
		{
			for(int i=0;i<5;i++)
			{
				JSONObject result2=myobj.getJSONObject(i);
				JSONObject result3=result2.getJSONObject("geometry");
				JSONObject result4=result3.getJSONObject("location");
	
			
				double one=result4.getDouble("lat");
				double two=result4.getDouble("lng");
			//	tt.setText(String.valueOf(one));
				int lat2,long2;
				lat2=(int) (one*1E6);
		    	long2=(int)(two*1E6);
		    	myloc2=new GeoPoint(lat2,long2);
		    	OverlayItem overlayitem2=new OverlayItem(myloc2,result2.getString("name"),"loc");
			    CustomPinpoint cust2=new CustomPinpoint(d,Demo.this);
			    cust2.insertPinpoint(overlayitem2);
			    overlaylst.add(cust2);
			    
		 }
		}
		catch(Exception e)
		{}
	}
	
}
@Override
protected boolean isRouteDisplayed() {
	// TODO Auto-generated method stub
	return false;
}
@Override
public void onLocationChanged(Location arg0) {
	// TODO Auto-generated method stub
	lat=(int) (arg0.getLatitude()*1E6);
	longi=(int) (arg0.getLongitude()*1E6);
	GeoPoint myloc=new GeoPoint(lat,longi);
	OverlayItem overlayitem=new OverlayItem(myloc,"My Location","loc");
    CustomPinpoint cust=new CustomPinpoint(d,Demo.this);
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
public String stringify(double one,double two)
{
	String str1=String.valueOf(one);
	String str2=String.valueOf(two);
	String str3=str1+","+str2;
	return str3;
}
}
