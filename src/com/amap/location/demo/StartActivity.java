package com.amap.location.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.amap.api.location.LocationManagerProxy;
import com.track.app.user.R;

/**
 * Demo首页
 * */
public class StartActivity extends Activity implements OnClickListener {
	private TextView mCurrentWeatherReportTextView;// 实时天气预报
	private TextView mFutureWeatherReportTextView;// 未来三天天气预报
	private TextView mNetLocationTextView;// 网络定位
	private TextView mMultyLocationTextView;// 混合定位
	private TextView mGpsLocationTextView;// GPS定位
	private TextView mGeoFenceTextView;// 地理围栏

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		setTitle("定位SDK " + LocationManagerProxy.getVersion());
		initView();
	}

	/**
	 * 初始化控件
	 * */
	private void initView() {
		mCurrentWeatherReportTextView = (TextView) findViewById(R.id.current_weather_report_text);
		mFutureWeatherReportTextView = (TextView) findViewById(R.id.future_weather_report_text);
		mNetLocationTextView = (TextView) findViewById(R.id.location_net_method_text);
		mMultyLocationTextView = (TextView) findViewById(R.id.location_multy_method_text);
		mGpsLocationTextView = (TextView) findViewById(R.id.location_gps_method_text);
		mGeoFenceTextView = (TextView) findViewById(R.id.location_geofence_method_text);

		mCurrentWeatherReportTextView.setOnClickListener(this);
		mFutureWeatherReportTextView.setOnClickListener(this);
		mNetLocationTextView.setOnClickListener(this);
		mMultyLocationTextView.setOnClickListener(this);
		mGpsLocationTextView.setOnClickListener(this);
		mGeoFenceTextView.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.current_weather_report_text:
			// 实时天气预报
			Intent intent = new Intent(StartActivity.this,
					CurrentWeatherReportActivity.class);
			startActivity(intent);
			break;
		case R.id.future_weather_report_text:
			// 未来三天天气预报
			Intent forcastIntent = new Intent(StartActivity.this,
					FutureWeatherReportActivity.class);
			startActivity(forcastIntent);
			break;
		case R.id.location_net_method_text:
			// 网络定位（Wifi+基站）
			Intent netIntent = new Intent(StartActivity.this,
					NetLocationActivity.class);
			startActivity(netIntent);
			break;
		case R.id.location_multy_method_text:
			// 混合定位
			Intent multyIntent = new Intent(StartActivity.this,
					MultyLocationActivity.class);
			startActivity(multyIntent);
			break;
		case R.id.location_gps_method_text:
			// GPS 定位
			Intent gpsIntent = new Intent(StartActivity.this,
					GPSLocationActivity.class);
			startActivity(gpsIntent);
			break;
		case R.id.location_geofence_method_text:
			// 地理围栏
			Intent geoFenceIntent = new Intent(StartActivity.this,
					GeoFenceActivity.class);
			startActivity(geoFenceIntent);
			break;

		}

	}

}
