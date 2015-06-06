package com.track.loader;

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

public class CustomerEditLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<Customer> adapter;
	private Activity context;

	public CustomerEditLoader(IDataAdapter<Customer> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication) context.getApplication())
				.getMiscServiceUrl();
	}

	@Override
	public void onDataReceive(String class_data, String json_data) {
		if (class_data.equals("Customer")) {
			Customer ci = JsonUtils.fromJson(json_data,
					new TypeToken<Customer>() {
					});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
		} else if (class_data.equals("R_Customer")) // 保存完成
		{
			Customer ci = JsonUtils.fromJson(json_data,
					new TypeToken<Customer>() {
					});
			adapter.getData().setId(ci.getId());
			adapter.getData().onSave();
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "客户信息保存完成!", Toast.LENGTH_SHORT).show();
		} else {
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}

	// 获取客户信息byId
	public void LoadCustomer(int id) {
		url += "getCustomerInfo/" + id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SaveCustomer(int id, String name, String rgCode, String addr,
			String dpt, int pCode, String telCode) {
		Customer cstm = new Customer();

		cstm.setId(id);
		cstm.setCname(name);
		cstm.setRegioncode(rgCode);
		cstm.setAddress(addr);
		cstm.setDepartment(dpt);
		cstm.setPoscode(pCode);
		cstm.setTelcode(telCode);

		SaveCustomer(cstm);
	}

	// 保存客户信息
	public void SaveCustomer(Customer cstm) {
		String jsonObj = JsonUtils.toJson(cstm, true);
		url += "saveCustomerInfo";
		try {
			execute(url, "POST", jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
