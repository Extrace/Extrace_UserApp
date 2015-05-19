package com.track.loader;

import java.util.List;

import android.app.Activity;

import com.track.misc.model.Package;
import com.track.net.HttpAsyncTask;
import com.track.net.HttpResponseParam.RETURN_STATUS;
import com.track.net.IDataAdapter;
import com.track.ui.main.ExTraceApplication;

public class TransPackageListLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<List<Package>> adapter;
	private Activity context;

	public TransPackageListLoader(IDataAdapter<List<Package>> adpt,
			Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication) context.getApplication())
				.getDomainServiceUrl();
	}

	@Override
	public void onDataReceive(String class_name, String json_data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub

	}

}
