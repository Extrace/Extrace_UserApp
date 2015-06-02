package com.track.util;

import org.apache.http.Header;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.track.misc.model.User;
import com.track.net.JsonUtils;

public class ExTraceApplication extends Application {
	private static final String PREFS_NAME = "ExTrace_lb.cfg";
	private static final String USER_INFO = "userInfo";
	String mServerUrl;
	String mMiscService, mDomainService;
	String url;
	RequestParams params;
	User user;
	SharedPreferences sp;

	public String getServerUrl() {
		return mServerUrl;
	}

	public String getMiscServiceUrl() {
		return mServerUrl + mMiscService;
	}

	public String getDomainServiceUrl() {
		return mServerUrl + mDomainService;
	}

	public User getLoginUser() {
		return user;
	}

	public void setLoginUser(User user) {
		this.user = user;
	}

	public void setServerUrl(String url) {
		mServerUrl = url;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("ServerUrl", mServerUrl);
		editor.commit();
	}

	public void getUserInfo() {

		sp = getSharedPreferences(USER_INFO, 0);
		String uname = sp.getString("uname", null);
		if (uname != null) {
			RequestParams params = new RequestParams();
			params.put("uname", uname);
			AsyncHttpClient client = new AsyncHttpClient();
			url = getMiscServiceUrl();
			url += "getUser";
			client.get(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(int statusCode, Header[] arg1,
						byte[] arg2, Throwable arg3) {
					if (statusCode == 404) {
						Toast.makeText(getApplicationContext(), "资源未找到",
								Toast.LENGTH_LONG).show();
					} else if (statusCode == 500) {
						Toast.makeText(getApplicationContext(), "服务器发生异常",
								Toast.LENGTH_LONG).show();
					}
					// When Http response code other than 404, 500
					else {
						Toast.makeText(getApplicationContext(),
								"服务器未开启！" + statusCode + " " + url,
								Toast.LENGTH_LONG).show();
					}
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					String str1 = new String(arg2);
					User user = JsonUtils.fromJson(str1, new TypeToken<User>() {
					});
					setLoginUser(user);
					updateUserInfo();
					Toast.makeText(getApplicationContext(), "获取用户数据成功 ！",
							Toast.LENGTH_SHORT).show();
				}

			});
		} else {
			Toast.makeText(getApplicationContext(), "你还未登录！只能使用部分功能！",
					Toast.LENGTH_SHORT).show();
		}
	}

	protected void updateUserInfo() {

		sp = getSharedPreferences(USER_INFO, 0);
		Editor et = sp.edit();
		et.putInt("id", user.getId());
		et.putString("receivepid", user.getReceivepid());
		et.putString("delivepid", user.getDeliverpid());
		et.putString("transpid", user.getTranspid());
		et.commit();
	}

	@Override
	public void onCreate() {
		super.onCreate();

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		mServerUrl = settings.getString("ServerUrl", "");
		mMiscService = settings.getString("MiscService", "/rest/Misc/");
		mDomainService = settings.getString("DomainService", "/rest/Domain/");
		if (mServerUrl == null || mServerUrl.length() == 0) {
			mServerUrl = "http://192.168.10.146:8080/TestCxfHibernate";
			mMiscService = "/rest/Misc/";
			mDomainService = "/rest/Domain/";
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("ServerUrl", mServerUrl);
			editor.putString("MiscService", mMiscService);
			editor.putString("DomainService", mDomainService);
			editor.commit();
		}
		getUserInfo();
		if (user == null) {
			sp = getSharedPreferences(USER_INFO, 0);
			user = new User();
			user.setId(sp.getInt("id", 1));
			user.setReceivepid(sp.getString("receivepid", null));
			user.setTranspid(sp.getString("transpid", null));
			user.setDeliverpid(sp.getString("delivepid", null));
		}
	}
}
