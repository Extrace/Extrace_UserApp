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
		} else if (class_name.equals("UNP_Package")) {
			Toast.makeText(context, "拆包成功！可在您的转运包裹中查看快件信息！", Toast.LENGTH_SHORT)
					.show();
		} else if (class_name.equals("NULL_Package")) {
			Toast.makeText(context, "不存在此包裹，请检查包裹ID！", Toast.LENGTH_SHORT)
					.show();
		} else if (class_name.equals("E_Package")) {
			Package ci = JsonUtils.fromJson(json_data,
					new TypeToken<Package>() {
					});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "包裹已经存在!", Toast.LENGTH_SHORT).show();
		} else if (class_name.equals("P_Package")) {
			Package ci = JsonUtils.fromJson(json_data,
					new TypeToken<Package>() {
					});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "包裹打包完成!", Toast.LENGTH_SHORT).show();
		} else if (class_name.equals("RCV_Package")) {
			Toast.makeText(context, "接收成功!", Toast.LENGTH_SHORT).show();

		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub

	}

	public void New(String id) {
		int uid = ((ExTraceApplication) context.getApplication())
				.getLoginUser().getId();
		url += "newTransPackage/id/" + id + "/uid/" + uid + "?_type=json";
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

	public void PackOk(String pid) {
		int uid = ((ExTraceApplication) context.getApplication())
				.getLoginUser().getId();
		url += "packTransPackage/pid/" + pid + "/uid/" + uid + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receivePackage(String pid) {
		int uid = ((ExTraceApplication) context.getApplication())
				.getLoginUser().getId();
		url += "receivePackagByUid/pid/" + pid + "/uid/" + uid + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 拆包，将包内快件状态改为“分拣”,并放入转运包裹？

	public void UnpackExpressList(String pKgId) {
		int uid = ((ExTraceApplication) context.getApplication())
				.getLoginUser().getId();
		url += "UnpackExpressList/PackageId/" + pKgId + "/uid/" + uid
				+ "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
