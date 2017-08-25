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

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class ReviewResult extends Activity {
	HttpClient client;
	String temp2,temp3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reviewresult);
		Bundle getb=getIntent().getExtras();
		temp2=getb.getString("key1");
		temp3=getb.getString("key2");
		new Read().execute();
	
		
		
	//	Toast mytoast=Toast.makeText(ReviewResult.this,temp3, Toast.LENGTH_LONG);
	//	mytoast.show();
		
	}
	public void getData(String name) throws Exception
	{
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("cat_id",temp2));
		nameValuePairs.add(new BasicNameValuePair("cat_id2",temp3));
		try
		{
		 HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(
	                "http://192.168.2.8:2000/SAMPLE/inserttodb.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	    //    Toast mytoast=Toast.makeText(ReviewResult.this,temp3, Toast.LENGTH_LONG);
		//	mytoast.show();
	        HttpEntity entity = response.getEntity();
	     //   String data = EntityUtils.toString(entity);
		}
		catch(Exception e){}
	}
	public class Read extends AsyncTask<Void,Void,Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try
			{
			getData("abc");
			}
			catch(Exception e){}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			 Toast mytoast=Toast.makeText(ReviewResult.this,"Submitted", Toast.LENGTH_LONG);
			 mytoast.show();
		}
		
		
	}
	
}
