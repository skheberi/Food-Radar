package com.example.maps3;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CustomPinpoint extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> pinpoints=new ArrayList<OverlayItem>(); 
	private Context c;
	int mTextSize;
	public CustomPinpoint(Drawable arg0) {
		super(boundCenter(arg0));
		// TODO Auto-generated constructor stub
	}
	public CustomPinpoint(Drawable m,Context c) {
		this(m);
		this.c=c;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return pinpoints.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return pinpoints.size();
	}
   public void insertPinpoint(OverlayItem item)
   {
	   pinpoints.add(item);
	   this.populate(); //internal processing
	   
   }
   //Drawing on the screen
@Override
public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	// TODO Auto-generated method stub
	super.draw(canvas, mapView, shadow);
	for (int index = 0; index < pinpoints.size(); index++)
    {
        OverlayItem item = pinpoints.get(index);
        GeoPoint point = item.getPoint();
        Point ptScreenCoord = new Point() ;
        mapView.getProjection().toPixels(point, ptScreenCoord);
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(20);
        paint.setFakeBoldText(true);
        paint.setARGB(200, 0, 0,100);
        canvas.drawText(item.getTitle(), ptScreenCoord.x-10, ptScreenCoord.y+mTextSize, paint);


}
   
}

}
