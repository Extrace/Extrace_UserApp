package com.track.loader;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.track.misc.model.Customer;
import com.track.net.HttpAsyncTask;
import com.track.net.HttpResponseParam.RETURN_STATUS;
import com.track.net.IDataAdapter;
import com.track.util.ExTraceApplication;
import com.track.util.JsonUtils;

public class CustomerListLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<List<Customer>> adapter;
	private Activity context;

	public CustomerListLoader(IDataAdapter<List<Customer>> adpt,
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
			Toast.makeText(context, "客户信息已删除!", Toast.LENGTH_SHORT).show();
		} else {
			if (json_data == null || json_data.length() == 0) {
				Toast.makeText(context, "没有符合条件的客户信息!", Toast.LENGTH_SHORT)
						.show();
				adapter.setData(null);
				adapter.notifyDataSetChanged();
			} else {
				// 将json字符串转换成list，new typetoken里面的格式必须和服务器返回的格式一样
				List<Customer> cstm = JsonUtils.fromJson(json_data,
						new TypeToken<List<Customer>>() {
						});
				adapter.setData(cstm);
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);

	}

	// 获取客户列表byTelcode
	public void LoadCustomerListByTelCode(String telCode) {
		url += "getCustomerListByTelCode/" + telCode + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取客户列表byName（支持模糊查询）
	public void LoadCustomerListByName(String name) {
		url += "getCustomerListByName/" + name + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取所有客户列表
	public void LoadCustomerList() {
		url += "getCustomerList?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 删除客户ById
	public void DeleteCustomer(int id) {
		url += "deleteCustomerInfo/" + id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
