package com.example.maps3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlaceSelect extends Activity {
	Button b;
	EditText e;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeselect);
		b=(Button)findViewById(R.id.findrest);
		e=(EditText) findViewById(R.id.restname);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle b=new Bundle();
				b.putString("key",e.getText().toString());
				//Intent and=new Intent(MainActivity.this,Menu.class);
			//	and.putExtras(b);
				// startActivity(and);
				Intent i=new Intent("android.intent.action.PLACERESULT");
				i.putExtras(b);
				startActivity(i);
				
			}
		});
	}

}
