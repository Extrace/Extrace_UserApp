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
import com.track.net.JsonUtils;
import com.track.ui.main.ExTraceApplication;

public class CustomerListLoader extends HttpAsyncTask {

	// private static final String PREFS_NAME = "ExTrace.cfg";
	String url;// = "http://192.168.7.100:8080/TestCxfHibernate/REST/Misc/";
	IDataAdapter<List<Customer>> adapter;
	private Activity context;

	public CustomerListLoader(IDataAdapter<List<Customer>> adpt,
			Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication) context.getApplication())
				.getMiscServiceUrl();
		// SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
		// 0);
		// url = settings.getString("ServerUrl", "");
		// if(url == null || url.length() == 0)
		// {
		// url = "http://192.168.7.100:8080/TestCxfHibernate/REST/Misc/";
		// //������ĳ����TOMCAT������URL
		// SharedPreferences.Editor editor = settings.edit();
		// editor.putString("ServerUrl", url);
		// editor.commit();
		// }
	}

	@Override
	public void onDataReceive(String class_data, String json_data) {
		if (json_data.equals("Deleted")) {
			// adapter.getData().remove(0); //����ط����ô���
			Toast.makeText(context, "�ͻ���Ϣ��ɾ��!", Toast.LENGTH_SHORT).show();
		} else {
			if (json_data == null || json_data.length() == 0) {
				Toast.makeText(context, "û�з��������Ŀͻ���Ϣ!",
						Toast.LENGTH_SHORT).show();
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

	public void LoadCustomerListByTelCode(String telCode) {
		url += "getCustomerListByTelCode/" + telCode + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadCustomerListByName(String name) {
		url += "getCustomerListByName/" + name + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DeleteCustomer(int id) {
		url += "deleteCustomer/" + id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
