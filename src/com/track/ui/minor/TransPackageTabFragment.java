package com.track.ui.minor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.track.app.user.R;
import com.zxing.activity.CaptureActivity;

public class TransPackageTabFragment extends Fragment {

	private static EditText mEditText;
	private ImageView mImageCamera;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.transpackage, container, false);
		mEditText = (EditText) view.findViewById(R.id.id_et_pkgNum);
		mImageCamera = (ImageView) view.findViewById(R.id.id_iv_camera);
		mImageCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openCameraIntent = new Intent(getActivity(),
						CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String result = data.getExtras().getString("result");
		mEditText.setText(result);

	}

	public void getDatas() {
		Bundle bundle = getArguments();
		if (bundle == null) {
			return;
		} else {
			String pkgNum = bundle.getString("pkgNum");
			mEditText.setText(pkgNum);
		}
	}
}
