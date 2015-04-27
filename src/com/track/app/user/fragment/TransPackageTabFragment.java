package com.track.app.user.fragment;

import com.track.app.user.R;
import com.track.app.user.R.id;
import com.track.app.user.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TransPackageTabFragment extends Fragment {

	private static EditText mEditText;
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 view = inflater.inflate(R.layout.transpackage,container,false);
		 mEditText = (EditText)view.findViewById(R.id.id_et_pkgNum);
		 Log.e("mEditText", mEditText.toString());
		return view;
	}

	public void getDatas() {
		// TODO Auto-generated method stub
		
		Bundle bundle=getArguments();
		if(bundle==null){
			return;
		}else{
		String pkgNum=bundle.getString("pkgNum");
		Log.e("pkgNum", pkgNum);
		mEditText.setText(pkgNum);
		}
	}
}
