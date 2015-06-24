package com.track.loader;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.track.misc.model.ExpressSheet;
import com.track.net.HttpAsyncTask;
import com.track.net.HttpResponseParam.RETURN_STATUS;
import com.track.net.IDataAdapter;
import com.track.util.ExTraceApplication;
import com.track.util.JsonUtils;

public class ExpressLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<ExpressSheet> adapter;
	private Activity context;

	public ExpressLoader(IDataAdapter<ExpressSheet> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication) context.getApplication())
				.getDomainServiceUrl();
	}

	@Override
	public void onDataReceive(String class_name, String json_data) {
		if (class_name.equals("ExpressSheet")) {
			ExpressSheet ci = JsonUtils.fromJson(json_data,
					new TypeToken<ExpressSheet>() {
					});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
		} else if (class_name.equals("DLV_ExpressSheet")) {
			Toast.makeText(context, "快件已成功交付！", Toast.LENGTH_SHORT).show();
		} else if (class_name.equals("NP_ExpressSheet")) {
			Toast.makeText(context, "您还没有揽收包裹，请与管理员联系！", Toast.LENGTH_SHORT)
					.show();
		} else if (class_name.equals("DP_ExpressSheet")) {
			Toast.makeText(context, "您还没有派送包裹，请与管理员联系！", Toast.LENGTH_SHORT)
					.show();
		} else if (class_name.equals("D_ExpressSheet")) {
			ExpressSheet ci = JsonUtils.fromJson(json_data,
					new TypeToken<ExpressSheet>() {
					});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "运单正在派送，请到“首页—>个人中心”处理！",
					Toast.LENGTH_SHORT).show();
		} else if (class_name.equals("E_ExpressSheet")) {// 已经存在
			ExpressSheet ci = JsonUtils.fromJson(json_data,
					new TypeToken<ExpressSheet>() {
					});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "运单信息已经存在!", Toast.LENGTH_SHORT).show();
		} else if (class_name.equals("R_ExpressSheet")) {
			// 保存完成
			ExpressSheet ci = JsonUtils.fromJson(json_data,
					new TypeToken<ExpressSheet>() {
					});
			adapter.getData().setId(ci.getId());
			adapter.getData().onSave();
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "运单信息保存完成，已经揽收！", Toast.LENGTH_SHORT)
					.show();
		} else if (class_name.equals("N_ExpressSheet")) {
			Toast.makeText(context, "查无此单，请返回检查运单号是否正确!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {

	}

	// 获取单个快件Byid
	public void Load(String id) {
		url += "getExpressSheet/" + id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 新建一个快件，并将快件放入user的揽收（receive）包裹
	public void New(String id) {
		int uid = ((ExTraceApplication) context.getApplication())
				.getLoginUser().getId();
		url += "newExpressSheet/id/" + id + "/uid/" + uid + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 派送快件
	public void Dispatch(String id) {

		int uid = ((ExTraceApplication) context.getApplication())
				.getLoginUser().getId();
		url += "dispatchExpressSheetId/id/" + id + "/uid/" + uid
				+ "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 保存快件信息
	public void Save(ExpressSheet es) {
		String jsonObj = JsonUtils.toJson(es, true);
		url += "saveExpressSheet";
		try {
			execute(url, "POST", jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 交付快件
	public void Delieve(String id) {

		int uid = ((ExTraceApplication) context.getApplication())
				.getLoginUser().getId();
		url += "deliveryExpressSheetId/id/" + id + "/uid/" + uid
				+ "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
