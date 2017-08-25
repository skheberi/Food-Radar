package com.example.maps3;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Options extends ListActivity {
	String choices[]={"Budget","Dish","Coupons","Popularity","Add Review"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Options.this, android.R.layout.simple_list_item_1, choices));
		ListView list=getListView();
		list.setTextFilterEnabled(true);
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg3==2)
				{
					Intent in=new Intent("android.intent.action.SUDEXO");
					startActivity(in);
				}
				else if(arg3==1)
				{
					Intent in=new Intent("android.intent.action.DISHES");
					startActivity(in);
				}
				else if(arg3==4)
				{
					Intent in=new Intent("android.intent.action.REVIEW");
					startActivity(in);
				}
				
			}
			
		});
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	
}
