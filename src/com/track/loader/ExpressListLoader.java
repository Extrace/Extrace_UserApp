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
import com.track.net.JsonUtils;
import com.track.ui.main.ExTraceApplication;

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
		} else {
			List<ExpressSheet> cstm = JsonUtils.fromJson(json_data,
					new TypeToken<List<ExpressSheet>>() {
					});
			adapter.setData(cstm);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}

	public void LoadExpressList() {
		url += "getExpressList/ID/like/6?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadExpressListInPackage(String pkgId) {
		url += "getExpressListInPackage/PackageId/" + pkgId + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
