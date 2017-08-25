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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DishResult extends Activity {
	TableLayout tl;
	HttpClient client;
	JSONArray myarr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dishresult);
		tl=(TableLayout) findViewById(R.id.main_table);
		TableRow tr_head = new TableRow(this);
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setLayoutParams(new LayoutParams(
		LayoutParams.MATCH_PARENT,
		LayoutParams.WRAP_CONTENT));
		//1st column
		TextView label_date = new TextView(this);
	    label_date.setId(20);
	    label_date.setText("NAME");
	    label_date.setTextColor(Color.WHITE);
	    label_date.setPadding(5, 5, 5, 5);
	    tr_head.addView(label_date);
	    //2nd column
	    TextView label_weight_kg = new TextView(this);
	    label_weight_kg.setId(21);// define id that must be unique
	    label_weight_kg.setText("PRICE"); // set the text for the header 
	    label_weight_kg.setTextColor(Color.WHITE); // set the color
	    label_weight_kg.setPadding(10, 10, 10, 10); // set the padding (if required)
	    tr_head.addView(label_weight_kg); 
	    //3rd column
	    TextView type = new TextView(this);
	    type.setId(21);// define id that must be unique
	    type.setText("RESTAURANT"); // set the text for the header 
	    type.setTextColor(Color.WHITE); // set the color
	    type.setPadding(5, 5, 5, 5); // set the padding (if required)
	    tr_head.addView(type); 
	    tl.addView(tr_head, new TableLayout.LayoutParams(
	            LayoutParams.MATCH_PARENT,
	            LayoutParams.WRAP_CONTENT));
	    new Read().execute("text");
	}
	public JSONArray getData(String name) throws Exception
	{

		Bundle getb=getIntent().getExtras();
		String temp2=getb.getString("key");
		//t.append("param"+temp2);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("cat_id", temp2));
		try {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(
	                "http://192.168.2.8:2000/SAMPLE/Connecttodb4.php");
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
		/*StringBuilder url=new StringBuilder(URL);
		url.append("?format=json");
		HttpGet get=new HttpGet(url.toString());
		HttpResponse r=client.execute(get);
//		t.append("Request sent");
		 int status = r.getStatusLine().getStatusCode();
		 if (status == 200){
				HttpEntity e=r.getEntity();
				String data = EntityUtils.toString(e);
				JSONObject result=new JSONObject(data);
				JSONArray arr=result.getJSONArray("maindata");
				JSONObject result2=arr.getJSONObject(1);
				return arr;
		 		}
		 else
			 return null;*/


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
			try
			{
			//	t.append(result);
		//	Maindata objarr[]=new Maindata[10];
			
			
		    int count=0;
		    for(int i=0;i<result.length();i++)
		    {
		    	Maindata dat=Maindata.getInstance();
		 //   	String names[]=dat.arr;
		    	
		    	JSONObject result2=result.getJSONObject(i);
		    	String name=result2.getString("NAME");
		    	String price=result2.getString("PRICE");
		    	String type=result2.getString("HOTELNAME");
		    	for(int j=0;j<10;j++)
		    	{
		    		if(dat.arr[j].equalsIgnoreCase(type))
		    		{
		    			
		    		
		    	TableRow tr = new TableRow(DishResult.this);
		    	if(count%2!=0) tr.setBackgroundColor(Color.GRAY);
		    	tr.setId(100+count);
		    	tr.setLayoutParams(new LayoutParams(
		    	LayoutParams.MATCH_PARENT,
		    	LayoutParams.WRAP_CONTENT));
		    	TextView labelDATE = new TextView(DishResult.this);
		    	labelDATE.setId(200+count); 
		    	labelDATE.setText(name);
		    	labelDATE.setTextSize(20);
		    	labelDATE.setPadding(2, 0, 5, 0);
		    	labelDATE.setTextColor(Color.WHITE);
		    	tr.addView(labelDATE);
		    	
		    	TextView labelWEIGHT = new TextView(DishResult.this);
		    	labelWEIGHT.setId(200+count);
		    	labelWEIGHT.setText(price);
		    	labelWEIGHT.setTextSize(20);
		    	labelWEIGHT.setTextColor(Color.WHITE);
		    	tr.addView(labelWEIGHT);

		 	TextView labelWEIGHT2 = new TextView(DishResult.this);
		    	labelWEIGHT2.setId(200+count);
		    	labelWEIGHT2.setText(type);
		    	labelWEIGHT2.setTextColor(Color.WHITE);
		    	labelWEIGHT2.setTextSize(20);
		    	tr.addView(labelWEIGHT2);
		    	
		    	tl.addView(tr, new TableLayout.LayoutParams(
	                    LayoutParams.MATCH_PARENT,
	                    LayoutParams.WRAP_CONTENT));
		       count++;
		    		}
		    	}
		    }
			
		//	objarr[0].setId(id);
		//t.append(String.valueOf(objarr[0].getId()));
		//	t.append(String.valueOf(id));
			}
			catch(Exception e)
			{}
		}

}
}
