package com.track.loader;

import android.app.Activity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.track.misc.model.User;
import com.track.net.HttpAsyncTask;
import com.track.net.HttpResponseParam.RETURN_STATUS;
import com.track.util.ExTraceApplication;
import com.track.util.JsonUtils;

public class UserInfoLoader extends HttpAsyncTask {

	String url;
	private Activity context;

	public UserInfoLoader(Activity context) {
		super(context);
		this.context = context;
		url = ((ExTraceApplication) context.getApplication())
				.getMiscServiceUrl();
	}

	@Override
	public void onDataReceive(String class_name, String json_data) {
		Log.e("onDataReceive", "noneuser");
		if (class_name.equals("User")) {
			Log.e("onDataReceive", "user");
			User user = JsonUtils.fromJson(json_data, new TypeToken<User>() {
			});
			((ExTraceApplication) context.getApplication()).setLoginUser(user);
		} else {

		}

	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}

	public void LoadUser(String uname) {
		System.out.println("***LoadUser方法开始执行***");
		Log.e("loadUser", "loadUser");
		url += "getUser/" + uname + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
