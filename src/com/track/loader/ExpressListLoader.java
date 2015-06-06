package com.track.loader;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.track.misc.model.ExpressSheet;
import com.track.net.HttpAsyncTask;
import com.track.net.HttpResponseParam.RETURN_STATUS;
import com.track.net.IDataAdapter;
import com.track.util.ExTraceApplication;

public class ExpressListLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<List<ExpressSheet>> adapter;
	private Activity context;

	public ExpressListLoader(IDataAdapter<List<ExpressSheet>> adpt,
			Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication) context.getApplication())
				.getDomainServiceUrl();
	}

	@Override
	public void onDataReceive(String class_data, String json_data) {
		if (json_data.equals("Deleted")) {
			// adapter.getData().remove(0); //这个地方不好处理
			Toast.makeText(context, "快件信息已删除!", Toast.LENGTH_SHORT).show();
		} else if (class_data.equals("AD_ExpressSheet")) {
			Toast.makeText(context, "加入包裹成功！", Toast.LENGTH_SHORT).show();
		} else {
			List<ExpressSheet> cstm = com.track.util.JsonUtils.fromJson(
					json_data, new TypeToken<List<ExpressSheet>>() {
					});
			if (cstm.size() > 0) {
				adapter.setData(cstm);
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(context, "此包裹内没有快件！", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}

	// 获取快件列表,可以指定（将 like ---> eq ）
	// 也可以模糊查询 （ 6 代表运单id的模糊查询？）
	public void LoadExpressList() {
		url += "getExpressList/id/like/6?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取指定包裹的快件列表
	public void LoadExpressListInPackage(String pkgId) {
		url += "getExpressListInPackage/PackageId/" + pkgId + "?_type=json";
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

	// 添加快件到包裹
	public void AddtoPkg(String id, String pid) {
		int uid = ((ExTraceApplication) context.getApplication())
				.getLoginUser().getId();
		url += "addExpressSheetId/id/" + id + "/pid/" + pid + "/uid/" + uid
				+ "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
