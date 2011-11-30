package se.fiktivalabbet.droidpark;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MapPicker extends MapActivity implements OnClickListener
{
	private MapView map;
	private MapController controller;
	private Button resultBtn;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maplayout);
		resultBtn = (Button) findViewById(R.id.longLatBtn);
		resultBtn.setOnClickListener(this);
		initMap();
		initThisLocation();
	}
	
	private void initThisLocation()
	{
		final MyLocationOverlay mlo = new MyLocationOverlay(this, map);
		mlo.enableMyLocation();
		mlo.runOnFirstFix(new Runnable()
		{
			public void run()
			{
				controller.setZoom(8);
				controller.animateTo(mlo.getMyLocation());
			}
		});
		map.getOverlays().add(mlo);
	}

	private void initMap()
	{
		map = (MapView) findViewById(R.id.map);
		controller = map.getController();
		map.setSatellite(false);
		map.setBuiltInZoomControls(true);
		
	}

	@Override
	protected boolean isRouteDisplayed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.longLatBtn:
			GeoPoint gp = map.getMapCenter();
			Intent data = new Intent();
			data.putExtra("longLat", gp.toString());
			Log.v("PickedLocation", gp.toString());
			setResult(RESULT_OK, data);
			finish();
			break;
		}
		
	}

}
