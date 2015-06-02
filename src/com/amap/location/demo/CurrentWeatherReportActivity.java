package com.amap.location.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.LocationManagerProxy;
import com.track.app.user.R;

/**
 * 实时天气示例 获取当前地区天气情况
 * */
public class CurrentWeatherReportActivity extends Activity implements
		AMapLocalWeatherListener {
	private LocationManagerProxy mLocationManagerProxy;

	private TextView mLocationTextView;// 地点
	private TextView mWeatherTextView;// 天气
	private TextView mWeatherTemperatureTextView;// 气温
	private TextView mWindDirctionTextView;// 风向
	private TextView mWindPowerTextView;// 风力
	private TextView mAirHumidityTextView;// 空气湿度
	private TextView mWeatherPublishTextView;// 发布时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
		setContentView(R.layout.activity_current_weather_report);
		init();
		initView();

	}

	private void init() {
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		// 获取实时天气预报
		// 如果需要同时请求实时、未来三天天气，请确保定位获取位置后使用,分开调用，可忽略本句。
		mLocationManagerProxy.requestWeatherUpdates(
				LocationManagerProxy.WEATHER_TYPE_LIVE, this);

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		mLocationTextView = (TextView) findViewById(R.id.current_weather_location_text);
		mWeatherTextView = (TextView) findViewById(R.id.current_weather_weather_text);
		mWeatherTemperatureTextView = (TextView) findViewById(R.id.current_weather_temperature_text);
		mWindDirctionTextView = (TextView) findViewById(R.id.current_weather_wind_direction_text);
		mWindPowerTextView = (TextView) findViewById(R.id.current_weather_wind_power_text);
		mAirHumidityTextView = (TextView) findViewById(R.id.current_weather_air_humidity_text);
		mWeatherPublishTextView = (TextView) findViewById(R.id.current_weather_weather_publish_text);
	}

	@Override
	public void onWeatherForecaseSearched(AMapLocalWeatherForecast arg0) {

	}

	@Override
	public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {

		if (aMapLocalWeatherLive != null
				&& aMapLocalWeatherLive.getAMapException().getErrorCode() == 0) {
			// 天气预报成功回调 设置天气信息
			mLocationTextView.setText(aMapLocalWeatherLive.getCity());
			mWeatherTextView.setText(aMapLocalWeatherLive.getWeather());
			mWeatherTemperatureTextView.setText(aMapLocalWeatherLive
					.getTemperature() + "℃");
			mWindDirctionTextView.setText(aMapLocalWeatherLive.getWindDir()
					+ "风");
			mWindPowerTextView.setText(aMapLocalWeatherLive.getWindPower()
					+ "级");
			mAirHumidityTextView.setText(aMapLocalWeatherLive.getHumidity()
					+ "%");
			mWeatherPublishTextView.setText(aMapLocalWeatherLive
					.getReportTime());
		} else {

			// 获取天气预报失败
			Toast.makeText(
					this,
					"获取天气预报失败:"
							+ aMapLocalWeatherLive.getAMapException()
									.getErrorMessage(), Toast.LENGTH_SHORT)
					.show();

		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		// 销毁定位
		mLocationManagerProxy.destroy();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
