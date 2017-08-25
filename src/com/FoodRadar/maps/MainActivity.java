package com.example.maps3;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;

public class MainActivity extends Activity implements LocationListener,OnGestureListener {

	TextView tv1;
	ListView list;
	Button btnmaps;
	HttpClient client;
	JSONArray json;
	LocationManager lm;
	LinearLayout ll;
	String towers;
	int lat=0;
	int longi=0;
	Location loc;
	JSONObject temp;
	String abc;
	SharedPreferences prefs;
	static GeoPoint myloc; 
	final static String URL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private GestureDetector gDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gDetector=new GestureDetector(this);
	//	getWindow().getDecorView().setBackgroundColor(Color.CYAN);
		try {
	//		tv1=(TextView) findViewById(R.id.map3txt);
			btnmaps=(Button) findViewById(R.id.openmaps);
			btnmaps.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent in=new Intent("android.intent.action.MAPS");
					startActivity(in);
				}
			});
			list=(ListView) findViewById(android.R.id.list);
			client=new DefaultHttpClient();
			  lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
			  	Criteria crit=new Criteria();
			  	towers=lm.getBestProvider(crit, false);
				 loc=lm.getLastKnownLocation(towers);
				if(loc!=null){
					lat=(int) ((loc.getLatitude()*1E6));
					longi=(int) ((loc.getLongitude()*1E6));	
					abc=stringify(loc.getLatitude(),loc.getLongitude());
				 myloc=new GeoPoint(lat,longi);
				}
		//	tv1.append("Getting data from joogle");
		//	tv1.append("Got details from joogle");
		//	JSONObject json=getDetails("abc","xyz",true);
		    new Read().execute("formatted_address");
			//tv1.setText(json.getString("formatted_address"));
		}catch (Exception e){
			tv1.setText(e.getMessage());
		}
		

	}
	
	
	public JSONArray getDetails(String query,String key,boolean sensor) throws Exception
	{
		StringBuilder url=new StringBuilder(URL);
		//url.append("?query="+query+"&"+"sensor="+Boolean.toString(sensor)+"&"+"key="+key);
		Log.d("str",abc);
		System.out.println("Lasoon :"+abc);
	//	String abc=stringify(loc.getLatitude(),loc.getLongitude());
		url.append("location="+abc);
		url.append("&radius=1000&sensor=true&types=restaurant&key=AIzaSyCkjlbpgXoCfq2lnXM-OG94n4ymjYQRbBo");
		HttpGet get=new HttpGet(url.toString());
	//	tv1.append("Before executing " + url.toString());
        HttpResponse r = client.execute(get);
	   // tv1.append("After executing");
		int status = r.getStatusLine().getStatusCode();
		if (status == 200){
			HttpEntity e=r.getEntity();
			String data = EntityUtils.toString(e);
			//JSONObject result = new JSONObject(data);
		//	JSONArray timeline=new JSONArray(data);
			//JSONObject result=timeline.getJSONObject(0);
			JSONObject result=new JSONObject(data);
			JSONArray arr=result.getJSONArray("results");
			JSONObject result2=arr.getJSONObject(0);
			
			return (arr); 
		}     
				else {
			return null;
		}
		
	}
public class Read extends AsyncTask<String,Integer,String[]>
	{

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
			json=getDetails("restaurants","0QRKpEpCk26NDOaMiNL01hBC5TbvQiE3D8CnAhQ",true);
			int i=0;
			
			String[] str=new String[10];
			for(i=0;i<10;i++)
			{
				temp=json.getJSONObject(i);
				str[i]=temp.getString("name");
			}
			return str;
			}
			catch(Exception e){
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result[]) {
			// TODO Auto-generated method stub
			/*for(int i=0;i<result.length;i++)
			{
			tv1.append(result[i]);
			tv1.append("\n");
			}*/
		  
			list.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, result ));
			 for(int i=0;i<10;i++)
			    {
			    	Maindata dat=Maindata.getInstance();
			    	dat.arr[i]=result[i];
			    	try
			    	{
			    	JSONObject temp=json.getJSONObject(i);
			    	dat.ids[i]=temp.getString("id");
			    	}
			    	catch(Exception e)
			    	{}
			    }
			list.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					//list.getItemAtPosition(arg3)
					try
					{
						int argu=(int) arg3;
						temp=json.getJSONObject(argu);
							String id=temp.getString("id");
						//	String temp2=m.getName();
							//if(temp2.equals("1e6434732c6aa524ea79c2591f3cb070c4103d92"))
							
							Bundle b=new Bundle();
							b.putString("key",id);
							//Intent and=new Intent(MainActivity.this,Menu.class);
						//	and.putExtras(b);
							// startActivity(and);
							Intent i=new Intent("android.intent.action.MENU");
							i.putExtras(b);
							startActivity(i);
								
						
					
					}
					catch(Exception e)
					{
						
					}
					}
				
				
			});
		
		    
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		lat=(int) (location.getLatitude()*1E6);
		longi=(int) (location.getLongitude()*1E6);
		GeoPoint myloc=new GeoPoint(lat,longi);
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


	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		try
		{
			if(e2.getX()>e1.getX())
			{
				Intent in=new Intent("android.intent.action.OPTIONS");
				startActivity(in);
			}
			else if(e2.getX()<e1.getX())
			{
				Intent in=new Intent("android.intent.action.PLACESELECT" );
				startActivity(in);
			}
		}
		catch(Exception e)
		{
			
		}
		
		
		
		return true;
	}


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return gDetector.onTouchEvent(event);
	}
	
}
