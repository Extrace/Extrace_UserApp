package com.track.loader;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.track.misc.model.TransNode;
import com.track.net.HttpAsyncTask;
import com.track.net.HttpResponseParam.RETURN_STATUS;
import com.track.net.IDataAdapter;
import com.track.util.ExTraceApplication;
import com.track.util.JsonUtils;

public class TransNodeListLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<List<TransNode>> adapter;
	private Activity context;

	public TransNodeListLoader(IDataAdapter<List<TransNode>> adpt,
			Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication) context.getApplication())
				.getMiscServiceUrl();
	}

	@Override
	public void onDataReceive(String class_data, String json_data) {
		if (json_data.equals("Deleted")) {
			Toast.makeText(context, "网点信息已删除!", Toast.LENGTH_SHORT).show();
		} else {
			if (json_data == null || json_data.length() == 0) {
				Toast.makeText(context, "没有符合条件的网点信息!", Toast.LENGTH_SHORT)
						.show();
				adapter.setData(null);
				adapter.notifyDataSetChanged();
			} else {
				// 将json字符串转换成list，new typetoken里面的格式必须和服务器返回的格式一样
				List<TransNode> cstm = JsonUtils.fromJson(json_data,
						new TypeToken<List<TransNode>>() {
						});
				Log.e("TransNode", cstm.toString());
				adapter.setData(cstm);
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}

	// 获取网点列表byTelcode
	public void LoadTransNodeListByTelCode(String telCode) {
		url += "getTransNodeListByTelCode/" + telCode + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取网点列表byName（支持模糊查询）
	public void LoadTransNodeListByName(String name) {
		url += "getTransNodeListByName/" + name + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取网点列表byCode
	public void LoadTransNodeListByCode(String code) {
		url += "getNode/" + code + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取网点列表byId
	public void LoadTransNodeListById(String id) {
		url += "getNodebyId/" + id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取所有网点列表
	public void LoadTransNodeList() {
		url += "getTransNodeList?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
