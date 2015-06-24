package com.track.ui.minor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.track.app.user.R;
import com.track.misc.model.Position;
import com.track.misc.model.TransNode;
import com.track.util.ExTraceApplication;
import com.track.util.JsonUtils;

public class TransNodeTabFragment extends Fragment implements LocationSource,
		AMapLocationListener, OnPoiSearchListener {

	private View rootView;
	private ProgressDialog prgDialog;
	private AMap aMap;
	private AMapLocation mLocation;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private String url;
	private static List<TransNode> list_p = new ArrayList<TransNode>();
	private ImageView mSearchView;
	private EditText mEditText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.transnode, container, false);
		mapView = (MapView) rootView.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		mSearchView = (ImageView) rootView
				.findViewById(R.id.action_searchAround);
		mEditText = (EditText) rootView.findViewById(R.id.editText1);

		mSearchView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				search(mEditText.getText().toString().trim());
			}
		});
		setUpMap();
		return rootView;
	}

	@Override
	public void onPoiSearched(PoiResult arg0, int arg1) {
		if (arg1 == 0) {
			aMap.clear();// 清理之前的图标
			List<PoiItem> poiItems = arg0.getPois();
			if (poiItems.isEmpty()) {
				Toast.makeText(getActivity(), "无查询结果！", Toast.LENGTH_SHORT)
						.show();
			} else {
				for (PoiItem p : poiItems) {
					Log.e("result", p.toString());
				}
				PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
				poiOverlay.removeFromMap();
				poiOverlay.addToMap();
				poiOverlay.zoomToSpan();
			}
		}
	}

	protected void search(String key) {
		if (!key.equals("")) {
			if (mLocation != null) {
				PoiSearch.Query query = new PoiSearch.Query(key,
						mLocation.getCityCode());
				query.setPageSize(10);
				query.setPageNum(0);
				PoiSearch poisearch = new PoiSearch(getActivity(), query);
				poisearch.setOnPoiSearchListener(this);
				LatLonPoint point = new LatLonPoint(mLocation.getLatitude(),
						mLocation.getLongitude());
				Toast.makeText(
						getActivity(),
						mLocation.getLatitude() + "||"
								+ mLocation.getAltitude(), Toast.LENGTH_SHORT)
						.show();
				// 第一个参数为自己的定位点，第二个为搜索半径
				poisearch.setBound(new SearchBound(point, 3000, true));
				poisearch.searchPOIAsyn();
			} else {
				Toast.makeText(getActivity(), "定位失败，请检查您的GPS或者网络是否正常开启！",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getActivity(), "请输入您要查找的关键字哦~", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	public TransNodeTabFragment() {

	}

	private void setUpMap() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		// aMap.setOnMapClickListener(this);

	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getAMapException().getErrorCode() == 0) {
				mLocation = amapLocation;
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点

			} else {
				Log.e("AmapErr", "Location ERR:"
						+ amapLocation.getAMapException().getErrorCode());
			}
		}
	}

	@Override
	public void activate(OnLocationChangedListener arg0) {
		mListener = arg0;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy
					.getInstance(getActivity());
			// 第三个参数是移动多少距离会回调方法
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms）
			// 其中如果间隔时间为-1，则定位只定一次
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 10 * 1000, 10, this);
		}
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			// removeUpdates(this)取消定位请求
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		MenuItem search = menu.findItem(R.id.action_search);
		final SearchView searchView = (SearchView) search.getActionView();
		searchView.setQueryHint("网点名称，区域码...");
		if (searchView != null) {

			SearchViewCompat.setOnQueryTextListener(searchView,
					new OnQueryTextListenerCompat() {
						@Override
						public boolean onQueryTextChange(String newText) {

							return true;
						}

						// 当搜索结果提交时执行
						@Override
						public boolean onQueryTextSubmit(String query) {
							if (!TextUtils.isEmpty(query)) {
								loadNodesLatlng(query);
								SearchViewCompat.setQuery(searchView, null,
										true);
							}
							return true;
						}
					});
			SearchViewCompat.setOnCloseListener(searchView,
					new OnCloseListenerCompat() {
						@Override
						public boolean onClose() {
							if (!TextUtils.isEmpty(SearchViewCompat
									.getQuery(searchView))) {
								SearchViewCompat.setQuery(searchView, null,
										true);
							}
							return true;
						}

					});
			MenuItemCompat.setActionView(search, searchView);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	private void loadNodesLatlng(String query) {
		prgDialog = new ProgressDialog(getActivity());
		prgDialog.show();
		prgDialog.setTitle("Please Wait...");
		url = ((ExTraceApplication) getActivity().getApplication())
				.getMiscServiceUrl();
		if (checkCode(query)) {
			url += "getNodebyId/" + query;
		} else {
			url += "getNodebyName/" + query;
		}
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				prgDialog.hide();
				// When Http response code is '404'
				if (statusCode == 404) {
					Toast.makeText(getActivity(), "资源未找到", Toast.LENGTH_LONG)
							.show();
				}
				// When Http response code is '500'
				else if (statusCode == 500) {
					Toast.makeText(getActivity(), "服务器发生异常", Toast.LENGTH_LONG)
							.show();
				}
				// When Http response code other than 404, 500
				else {
					Toast.makeText(getActivity(),
							"设备网络异常或服务器为开启！" + statusCode + " " + url,
							Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				prgDialog.hide();
				String result = new String(arg2);
				list_p = JsonUtils.fromJson(result,
						new TypeToken<List<TransNode>>() {
						});
				Log.e("position", list_p.toString());
				addNodeMarks();
			}
		});

	}

	protected void addNodeMarks() {
		aMap.clear();
		MarkerOptions markerOptions = new MarkerOptions();
		if (!list_p.isEmpty()) {
			for (TransNode t : list_p) {
				Position p = t.getPosition();
				markerOptions.title(t.getNodename());
				markerOptions.snippet("电话: " + t.getTelcode() + "\n" + "区域码: "
						+ t.getRegioncode());
				// 设置Marker的坐标，为我们点击地图的经纬度坐标
				markerOptions.position(new LatLng(p.getX(), p.getY()));
				// 设置Marker的可见性
				markerOptions.visible(true);
				// 设置Marker是否可以被拖拽，这里先设置为false，之后会演示Marker的拖拽功能
				markerOptions.draggable(false);
				// 将Marker添加到地图上去
				aMap.addMarker(markerOptions);
			}
		} else {
			Toast.makeText(getActivity(), "查询不到此网点哦~", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public boolean checkCode(String str) {
		String regex_mb = "[0-9]+$"; // 移动电话的正则表达式
		Pattern pattern_mb = Pattern.compile(regex_mb);
		return pattern_mb.matcher(str).matches();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
