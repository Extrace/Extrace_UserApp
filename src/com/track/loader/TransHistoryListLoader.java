package com.track.loader;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.track.misc.model.TransHistory;
import com.track.net.HttpAsyncTask;
import com.track.net.HttpResponseParam.RETURN_STATUS;
import com.track.net.IDataAdapter;
import com.track.util.ExTraceApplication;
import com.track.util.JsonUtils;

public class TransHistoryListLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<List<TransHistory>> adapter;
	private Activity context;

	public TransHistoryListLoader(IDataAdapter<List<TransHistory>> adpt,
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
		} else if (class_data.equals("AD_ExpressSheet")) {
		} else {
			List<TransHistory> th_list = JsonUtils.fromJson(json_data,
					new TypeToken<List<TransHistory>>() {
					});
			Log.e("transhis", th_list.toString());
			if (th_list.size() > 0) {
				adapter.setData(th_list);
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(context, "此包裹内没有快件哦！", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
		Toast.makeText(context, "没有内容", Toast.LENGTH_SHORT).show();
	}

	// 获取指定包裹的快件列表
	public void LoadExpressTransHistoryList(String eid) {
		url += "getTransHistoryList/ExpId/" + eid + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
