package com.track.ui.main;

import android.app.Application;
import android.content.SharedPreferences;

import com.track.misc.model.User;

public class ExTraceApplication extends Application {
	private static final String PREFS_NAME = "ExTrace.cfg2";
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
		mMiscService = settings.getString("MiscService", "/rest/Misc/");
		mDomainService = settings.getString("DomainService", "/rest/Domain/");
		if (mServerUrl == null || mServerUrl.length() == 0) {
			mServerUrl = "http://169.254.250.120:8080/TestCxfHibernate";
			mMiscService = "/rest/Misc/";
			mDomainService = "/rest/Domain/";
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("ServerUrl", mServerUrl);
			editor.putString("MiscService", mMiscService);
			editor.putString("DomainService", mDomainService);
			editor.commit();
		}
		// 临时造一个用户
		user = new User();
		user.setId(12);
		user.setReceivepid("1111112222");
		user.setTranspid("1111113333");
		user.setDeliverpid("1111115555");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		// save data of the map
	}
}
