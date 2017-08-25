package com.example.maps3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Home extends Activity {
ListView list;
String[] myarr={"Find Restaurants","Search By Budget","Search by dish","Search by Coupons","Search By Popularity"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		list=(ListView) findViewById(android.R.id.list);
		list.setAdapter(new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1, myarr ));
	}

}
