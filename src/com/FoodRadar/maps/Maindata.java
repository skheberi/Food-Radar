package com.example.maps3;

import android.app.Application;

public class Maindata{
public static Maindata instance=null;
public static String arr[]=new String[10];
public static String ids[]=new String[10];
private Maindata(){}
public static Maindata getInstance()
{
	//arr=new String[5];
	if(instance==null)
	{
		instance=new Maindata();
	}
	return instance;
}
	
}
