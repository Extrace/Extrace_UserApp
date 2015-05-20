package com.track.loader;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.track.misc.model.ExpressSheet;
import com.track.net.HttpAsyncTask;
import com.track.net.HttpResponseParam.RETURN_STATUS;
import com.track.net.IDataAdapter;
import com.track.net.JsonUtils;
import com.track.util.ExTraceApplication;

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
		} else if (class_name.equals("E_ExpressSheet")) // 已经存在
		{
			ExpressSheet ci = JsonUtils.fromJson(json_data,
					new TypeToken<ExpressSheet>() {
					});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "快件运单信息已经存在!", Toast.LENGTH_SHORT).show();
		} else if (class_name.equals("R_ExpressSheet")) // 保存完成
		{
			ExpressSheet ci = JsonUtils.fromJson(json_data,
					new TypeToken<ExpressSheet>() {
					});
			adapter.getData().setId(ci.getId());
			adapter.getData().onSave();
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "快件运单信息保存完成!", Toast.LENGTH_SHORT).show();
		} else {
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub

	}

	public void Load(String id) {
		url += "getExpressSheet/" + id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public void Save(ExpressSheet es) {
		String jsonObj = JsonUtils.toJson(es, true);
		url += "saveExpressSheet";
		try {
			execute(url, "POST", jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
