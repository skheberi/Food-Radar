package com.example.maps3;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Review extends Activity implements OnItemSelectedListener{

	Spinner one,two,three;
	Button b;
	JSONArray myarr;
	HttpClient client;
	JSONObject json;
	int pos;
	Maindata dat;
	String dishes[]=new String[100];
	String dishids[]=new String[100];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String nos[]={"1","2","3","4","5","6","7","8","9","10"};
		setContentView(R.layout.review);
		one=(Spinner)findViewById(R.id.spinner1);
		two=(Spinner)findViewById(R.id.spinner2);
		three=(Spinner)findViewById(R.id.spinner3);
		
		b=(Button)findViewById(R.id.enterrev);
		 dat=Maindata.getInstance();
		 ArrayAdapter<String> adapter=new ArrayAdapter<String>(Review.this, android.R.layout.simple_spinner_item,dat.arr);
			one.setAdapter(adapter);
			one.setOnItemSelectedListener(this);
			ArrayAdapter<String> adapter3=new ArrayAdapter<String>(Review.this, android.R.layout.simple_spinner_item,nos);
			three.setAdapter(adapter3);
			three.setOnItemSelectedListener(this);	
			
			b.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle b=new Bundle();
					int i=three.getSelectedItemPosition();
					i++;
					String s=String.valueOf(i);
					b.putString("key1",s);
					int j=two.getSelectedItemPosition();
					String s2=dishids[j];
					b.putString("key2",s2);
					
					
					Intent in=new Intent("android.intent.action.REVIEWRESULT");
					in.putExtras(b);
					startActivity(in);
				}
			});
		
	}
	public JSONArray getData(String name) throws Exception
	{

		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("cat_id", dat.ids[pos]));
		try {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(
	                "http://192.168.2.8:2000/SAMPLE/Connecttodb.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        String data = EntityUtils.toString(entity);
	        JSONObject result=new JSONObject(data);
	        
			JSONArray arr=result.getJSONArray("maindata");
			JSONObject result2=arr.getJSONObject(1);
			return arr;
	       
	       // t.append("sent");
	      //  is = entity.getContent();
	    } catch (Exception e) {
	       
	    }
		return null;
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
		 pos=one.getSelectedItemPosition();
		 new Read().execute("text");
					
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	public class Read extends AsyncTask<String,Integer,JSONArray>
	{

		@Override
		protected JSONArray doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try
			{
				//putdata();
			myarr=getData("mybringback");
			return myarr;
			}
			catch(Exception e)
			{
				
			}
			return null;
		
		}

		@Override
		protected void onPostExecute(JSONArray result) {
						// TODO Auto-generated method stub
			for(int i=0;i<result.length();i++)
			{
				try
				{
				 json=result.getJSONObject(i);
				String name=json.getString("NAME");
				String id=json.getString("ID");
				dishes[i]=name;
			dishids[i]=id;
					}
				catch(Exception e){}
			}
			ArrayAdapter<String> adapter2=new ArrayAdapter<String>(Review.this, android.R.layout.simple_spinner_item,dishes);
			two.setAdapter(adapter2);
			two.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		
		
		}
		

}
}
