package com.example.maps3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dishes extends Activity {
Button b;
EditText et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dish);
		et=(EditText) findViewById(R.id.editText1);
		b=(Button) findViewById(R.id.button1);
		
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str=(et.getText()).toString();
			//	Toast mytoast=Toast.makeText(Dishes.this,str, Toast.LENGTH_LONG);
			//		mytoast.show();
				
				Bundle b=new Bundle();
				
				b.putString("key", str);
				Intent in=new Intent("android.intent.action.DISHRESULT");
				in.putExtras(b);
				startActivity(in);
			
			}
			
		});
	}

}
