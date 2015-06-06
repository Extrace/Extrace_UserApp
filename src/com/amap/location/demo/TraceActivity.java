package com.amap.location.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.track.app.user.R;

public class TraceActivity extends Activity implements OnMapLoadedListener {
	@SuppressWarnings("unused")
	private AMap aMap;
	private MapView mapView;
	private List<LatLng> traceList;
	private double trace[] = { 34.820026, 113.534138, 34.820581, 113.534198,
			34.821139, 113.534225, 34.821572, 113.534059, 34.821594,
			113.533644, 34.821422, 113.53333, 34.821125, 113.533148, 34.820821,
			113.53303, 34.820406, 113.532937, 34.819876, 113.533191, 34.819403,
			113.533375, 34.819425, 113.53398, 34.819632, 113.534355, 34.820019,
			113.534547, 34.820516, 113.534608, 34.821053, 113.534651 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multy_location);
		mapView = (MapView) findViewById(R.id.map);

		mapView.onCreate(savedInstanceState);// 此方法必须重写
		mapView.getMap().setOnMapLoadedListener(this);
		traceList = new ArrayList<LatLng>();
		for (int i = 0; i < trace.length - 1; i++) {
			LatLng mLatlng = new LatLng(trace[i], trace[++i]);
			traceList.add(mLatlng);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		mapView.getMap().addPolyline(new PolylineOptions().addAll(traceList));
	}
}
