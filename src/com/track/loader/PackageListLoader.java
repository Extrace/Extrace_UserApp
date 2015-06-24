package com.track.loader;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.track.misc.model.Package;
import com.track.net.HttpAsyncTask;
import com.track.net.HttpResponseParam.RETURN_STATUS;
import com.track.net.IDataAdapter;
import com.track.util.ExTraceApplication;
import com.track.util.JsonUtils;

public class PackageListLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<List<Package>> adapter;
	private Activity context;

	public PackageListLoader(IDataAdapter<List<Package>> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication) context.getApplication())
				.getDomainServiceUrl();
	}

	@Override
	public void onDataReceive(String class_name, String json_data) {
		// TODO Auto-generated method stub
		Log.e("jsondata", json_data);
		if (class_name.equals("Package")) {

		} else {
			// Toast.makeText(context, json_data, Toast.LENGTH_SHORT).show();
			Log.e("jsondata", json_data);
			List<Package> pkg = JsonUtils.fromJson(json_data,
					new TypeToken<List<Package>>() {
					});
			if (pkg.size() > 0) {
				// Log.e("pkglist", pkg.get(0).getTargetTransNode().toString());
				adapter.setData(pkg);
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(context, "您还未接收任何包裹！", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);

	}

	public void getPackageListByUid() {
		int uid = ((ExTraceApplication) context.getApplication())
				.getLoginUser().getId();
		url += "getPackageListByUid/uid/" + uid + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
