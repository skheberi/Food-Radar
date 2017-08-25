package com.example.maps3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.maps3.Menu.Read;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Sudexo extends Activity {
	ListView list;
	JSONArray myarr;
	HttpClient client;
	TableLayout tl;
	Maindata dat;
	final static String URL="http://192.168.2.8:2000/SAMPLE/Connecttodb2.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sud);
	//	list=(ListView) findViewById(android.R.id.list);
				dat=Maindata.getInstance();
			//	Toast mytoast=Toast.makeText(Sudexo.this,dat.arr[0], Toast.LENGTH_LONG);
			//	mytoast.show();

	 tl = (TableLayout) findViewById(R.id.main_table);
	 TableRow tr_head = new TableRow(this);
	 tr_head.setId(10);
	 tr_head.setBackgroundColor(Color.GRAY);
	 tr_head.setLayoutParams(new LayoutParams(
	 LayoutParams.FILL_PARENT,
	 LayoutParams.WRAP_CONTENT));
	 TextView label_date = new TextView(this);
     label_date.setId(20);
     label_date.setText("NAMES");
     label_date.setTextColor(Color.WHITE);
     label_date.setPadding(5, 5, 5, 5);
     tr_head.addView(label_date);// add the column to the table row
     tl.addView(tr_head, new TableLayout.LayoutParams(
             LayoutParams.FILL_PARENT,
             LayoutParams.WRAP_CONTENT));
		client=new DefaultHttpClient();
		  new Read().execute("text");

	}	
	public JSONArray getData(String name) throws Exception
	{
		StringBuilder url=new StringBuilder(URL);
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
			 return null;

	}
	public class Read extends AsyncTask<String,Integer,String[]>
	{

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			try
			{
				//putdata();
			myarr=getData("mybringback");
			String[] str=new String[10];
			String[] str2=new String[10];
			for(int i=0;i<myarr.length();i++)
				{
				JSONObject temp=myarr.getJSONObject(i);
				str[i]=temp.getString("NAME");
				}
			//Comparing Arrays
			Set<String> set=new LinkedHashSet<String>(Arrays.asList(str));
			 set.retainAll(Arrays.asList(dat.arr));
			 str2=set.toArray(new String[0]);
			 
			 
			
			
			return str2;
			}
			catch(Exception e)
			{
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			
			// TODO Auto-generated method stub
			//list.setAdapter(new ArrayAdapter<String>(Sudexo.this, android.R.layout.simple_list_item_1, result ));
		//	Toast mytoast=Toast.makeText(Sudexo.this,result[2], Toast.LENGTH_LONG);
		//	mytoast.show();
			//TextView t=new TextView(Sudexo.this);
			//t.setText("Hello");
			int count=0;
			for(int i=0;i<result.length;i++)
			{
				TableRow tr = new TableRow(Sudexo.this);
				if(count%2!=0) tr.setBackgroundColor(Color.GRAY);
		    	tr.setId(100+count);
		    	tr.setLayoutParams(new LayoutParams(
		    	LayoutParams.MATCH_PARENT,
		    	LayoutParams.WRAP_CONTENT));
		    	TextView labelDATE = new TextView(Sudexo.this);
		    	labelDATE.setId(200+count); 
		    	labelDATE.setText(result[i]);
		    	labelDATE.setTextSize(20);
		    	labelDATE.setPadding(2, 0, 5, 0);
		    	labelDATE.setTextColor(Color.WHITE);
		    	tr.addView(labelDATE);
		    	tl.addView(tr, new TableLayout.LayoutParams(
	                    LayoutParams.MATCH_PARENT,
	                    LayoutParams.WRAP_CONTENT));
		       count++;
	
			}
		}
		
	}
	
}
