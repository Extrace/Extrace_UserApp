package com.track.loader;

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

public class PackageLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<Package> adapter;
	private Activity context;

	public PackageLoader(IDataAdapter<Package> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication) context.getApplication())
				.getDomainServiceUrl();
	}

	@Override
	public void onDataReceive(String class_name, String json_data) {
		if (class_name.equals("Package")) {
			Package ci = JsonUtils.fromJson(json_data,
					new TypeToken<Package>() {
					});
			Log.e("pkgData", ci.toString());
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
		} else if (class_name.equals("R_Package")) // 保存完成
		{
			Package ci = JsonUtils.fromJson(json_data,
					new TypeToken<Package>() {
					});
			adapter.getData().setId(ci.getId());
			adapter.getData().onSave();
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "包裹信息保存完成!", Toast.LENGTH_SHORT).show();
		} else if (class_name.equals("E_Package")) {
			Package ci = JsonUtils.fromJson(json_data,
					new TypeToken<Package>() {
					});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "包裹已经存在!", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub

	}

	public void New(String id) {
		url += "newTransPackage/id/" + id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Load(String id) {
		url += "getTransPackage/" + id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Save(Package tp) {
		String jsonObj = JsonUtils.toJson(tp, true);
		url += "saveTransPackage";
		try {
			execute(url, "POST", jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
