package com.track.ui.minor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.ui.domain.ExpressEditActivity;
import com.zxing.activity.CaptureActivity;

public class TransPackageTabFragment extends Fragment {

	private static EditText mEditText;
	private ImageView mImageCamera;
	private View view;
	private Button mExpInfoBt;
	private Button mExpTrackBt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.transpackage, container, false);
		mEditText = (EditText) view.findViewById(R.id.id_et_pkgNum);
		mExpInfoBt = (Button) view.findViewById(R.id.button1);
		mExpTrackBt = (Button) view.findViewById(R.id.button2);
		mImageCamera = (ImageView) view.findViewById(R.id.id_iv_camera);
		mImageCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openCameraIntent = new Intent(getActivity(),
						CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});

		mExpInfoBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StartQueryExpress();
			}
		});
		mExpTrackBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StartTackExpress();

			}
		});
		return view;
	}

	protected void StartTackExpress() {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "目前还未实现此功能哦~", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String result;
		if (data != null) {

			result = data.getExtras().getString("result");

		} else {
			result = "";
		}
		mEditText.setText(result);

	}

	public void StartQueryExpress() {
		if (mEditText.getText().toString().trim().equals("")) {
			Toast.makeText(getActivity(), "运单号不能为空!", Toast.LENGTH_SHORT)
					.show();
		} else {
			Intent intent = new Intent();
			intent.putExtra("ExpId", mEditText.getText().toString().trim());
			intent.putExtra("Action", "Query");
			intent.setClass(this.getActivity(), ExpressEditActivity.class);
			startActivityForResult(intent, 0);
		}
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
