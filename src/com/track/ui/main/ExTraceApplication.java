package com.track.ui.main;

import android.app.Application;
import android.content.SharedPreferences;

import com.track.misc.model.User;

public class ExTraceApplication extends Application {
	private static final String PREFS_NAME = "ExTrace.cfg";
	String mServerUrl;
	String mMiscService, mDomainService;
	User user;

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

	public void setServerUrl(String url) {
		mServerUrl = url;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("ServerUrl", mServerUrl);
		editor.commit();
	}

	@Override
	public void onCreate() {
		super.onCreate();

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		mServerUrl = settings.getString("ServerUrl", "");
		mMiscService = settings.getString("MiscService", "/REST/Misc/");
		mDomainService = settings.getString("DomainService", "/REST/Domain/");
		if (mServerUrl == null || mServerUrl.length() == 0) {
			mServerUrl = "http://192.168.7.100:8080/TestCxfHibernate";
			mMiscService = "/REST/Misc/";
			mDomainService = "/REST/Domain/";
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("ServerUrl", mServerUrl);
			editor.putString("MiscService", mMiscService);
			editor.putString("DomainService", mDomainService);
			editor.commit();
		}
		// 临时造一个用户
		// userInfo = new User();
		// userInfo.setID(12);
		// userInfo.setReceivePackageID("1111112222");
		// userInfo.setTransPackageID("1111113333");
		// userInfo.setDelivePackageID("1111115555");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		// save data of the map
	}
}
