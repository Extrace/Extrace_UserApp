package com.amap.location.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Window;
import android.widget.TextView;

import com.amap.api.location.LocationManagerProxy;
import com.track.app.user.R;

/**
 * 使用gps进行定位示例
 * */
public class GPSLocationActivity extends Activity {
	private LocationManagerProxy mLocationManagerProxy;

	private TextView mLocationLatlngTextView;// 定位经纬度信息
	private TextView mLocationAccurancyTextView;// 定位精度信息
	private TextView mLocationMethodTextView;// 定位方式信息
	private TextView mLocationTimeTextView;// 定位时间信息

	public static final String GPSLOCATION_BROADCAST_ACTION = "com.location.apis.gpslocationdemo.broadcast";

	private PendingIntent mPendingIntent;

	private Handler mHandler = new Handler() {

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
		setContentView(R.layout.activity_gps_location);
		initView();
	}

	private BroadcastReceiver mGPSLocationReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 接受广播
			if (intent.getAction().equals(GPSLOCATION_BROADCAST_ACTION)) {

				// 只进行一次定位，定位成功后移除定位请求
				mLocationManagerProxy.removeUpdates(mPendingIntent);

				Bundle bundle = intent.getExtras();

				// 获取bundle里的数据
				Parcelable parcelable = bundle
						.getParcelable(LocationManagerProxy.KEY_LOCATION_CHANGED);

				Location location = (Location) parcelable;
				if (location == null) {
					return;
				}
				// 定位成功回调信息，设置相关消息
				mLocationLatlngTextView.setText(location.getLatitude() + "  "
						+ location.getLongitude());
				mLocationAccurancyTextView.setText(String.valueOf(location
						.getAccuracy()));
				mLocationMethodTextView.setText(location.getProvider());

				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = new Date(location.getTime());

				mLocationTimeTextView.setText(df.format(date));

			}

		}
	};

	private void init() {
		IntentFilter fliter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		fliter.addAction(GPSLOCATION_BROADCAST_ACTION);
		registerReceiver(mGPSLocationReceiver, fliter);
		Intent intent = new Intent(GPSLOCATION_BROADCAST_ACTION);
		mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
				intent, 0);
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		// 采用peddingIntent方式进行对定位调用
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		mLocationManagerProxy.requestLocationUpdates(
				LocationManagerProxy.GPS_PROVIDER, 60 * 1000, 15,
				mPendingIntent);
		// 如果一直定位失败则2min后停止定位
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mLocationManagerProxy.removeUpdates(mPendingIntent);
			}
		}, 2 * 60 * 1000);

	}

	private void initView() {
		mLocationLatlngTextView = (TextView) findViewById(R.id.gps_location_latlng_text);
		mLocationAccurancyTextView = (TextView) findViewById(R.id.gps_location_accurancy_text);
		mLocationMethodTextView = (TextView) findViewById(R.id.gps_location_method_text);
		mLocationTimeTextView = (TextView) findViewById(R.id.gps_location_time_text);

	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 移除定位请求
		mLocationManagerProxy.removeUpdates(mPendingIntent);
		unregisterReceiver(mGPSLocationReceiver);
		// 销毁定位
		mLocationManagerProxy.destroy();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
