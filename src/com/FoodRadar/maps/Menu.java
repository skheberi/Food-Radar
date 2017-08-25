package com.example.maps3;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.client.*;
import android.app.Application;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends Activity {

//TableRow header=new TableRow(this);
	JSONArray myarr;
	HttpClient client;
	TableLayout tl;
	TextView t;
	final static String URL="http://192.168.43.2:2000/SAMPLE/Connecttodb.php";
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.myfile);
	//t=(TextView) findViewById(R.id.trial);
	//t.append("city");
	client=new DefaultHttpClient();
	  new Read().execute("text");
	tl=(TableLayout)findViewById(R.id.MenuTable);
	TableRow tr_head = new TableRow(this);
	tr_head.setId(10);
	tr_head.setBackgroundColor(Color.GRAY);
	tr_head.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	//creating first column heading
	TextView label_date = new TextView(this);
    label_date.setId(20);
    label_date.setText("NAME");
    label_date.setTextColor(Color.WHITE);
    label_date.setPadding(5, 5, 5, 5);
    tr_head.addView(label_date);
    //Creating 2nd column heading
    TextView label_weight_kg = new TextView(this);
    label_weight_kg.setId(21);// define id that must be unique
    label_weight_kg.setText("PRICE"); // set the text for the header 
    label_weight_kg.setTextColor(Color.WHITE); // set the color
    label_weight_kg.setPadding(10, 10, 10, 10); // set the padding (if required)
    tr_head.addView(label_weight_kg); 
    //Creating a 3rd column
    TextView type = new TextView(this);
    type.setId(21);// define id that must be unique
    type.setText("TYPE"); // set the text for the header 
    type.setTextColor(Color.WHITE); // set the color
    type.setPadding(5, 5, 5, 5); // set the padding (if required)
    tr_head.addView(type); 
   tl.addView(tr_head, new TableLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT));
   
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
                "http://192.168.43.100:2000/SAMPLE/Connecttodb.php");
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
//	t.append("Request sent");
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
		
		if(result==null)
		{
			Toast mytoast=Toast.makeText(Menu.this,"Menu Under Construction", Toast.LENGTH_LONG);
			mytoast.show();
		}
	    int count=0;
	    for(int i=0;i<result.length();i++)
	    {
	    	JSONObject result2=result.getJSONObject(i);
	    	String name=result2.getString("NAME");
	    	String price=result2.getString("PRICE");
	    	String type=result2.getString("TYPE");
	    	TableRow tr = new TableRow(Menu.this);
	    	if(count%2!=0) tr.setBackgroundColor(Color.GRAY);
	    	tr.setId(100+count);
	    	tr.setLayoutParams(new LayoutParams(
	    	LayoutParams.MATCH_PARENT,
	    	LayoutParams.WRAP_CONTENT));
	    	TextView labelDATE = new TextView(Menu.this);
	    	labelDATE.setId(200+count); 
	    	labelDATE.setText(name);
	    	labelDATE.setPadding(2, 0, 5, 0);
	    	labelDATE.setTextColor(Color.WHITE);
	    	tr.addView(labelDATE);
	    	
	    	TextView labelWEIGHT = new TextView(Menu.this);
	    	labelWEIGHT.setId(200+count);
	    	labelWEIGHT.setText(price);
	    	labelWEIGHT.setTextColor(Color.WHITE);
	    	tr.addView(labelWEIGHT);

	    	TextView labelWEIGHT2 = new TextView(Menu.this);
	    	labelWEIGHT2.setId(200+count);
	    	labelWEIGHT2.setText(type);
	    	labelWEIGHT2.setTextColor(Color.WHITE);
	    	tr.addView(labelWEIGHT2);
	    	
	    	tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
	       count++;
	    }
		
	//	objarr[0].setId(id);
	//t.append(String.valueOf(objarr[0].getId()));
	//	t.append(String.valueOf(id));
		}
		catch(Exception e)
		{}
	}
	public void putdata()
	{
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("cat_id", "Snacks"));
		try {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(
	                "http://192.168.2.8:2000/SAMPLE/Connecttodb.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        t.append("sent");
	      //  is = entity.getContent();
	    } catch (Exception e) {
	       
	    }
	}
}
}