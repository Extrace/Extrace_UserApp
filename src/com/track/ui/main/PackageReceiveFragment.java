package com.track.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.track.ui.minor.PackageListTabFragment;
import com.zxing.activity.CaptureActivity;

public class PackageReceiveFragment extends Fragment {

	private View view;
	private ImageView mImageCamera;
	private static EditText mEditText;
	private Button mButton;
	private PackageListTabFragment mPackageListFragment;
	private Button mBtR;
	private PkgListCallbacks mCallbacks;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.package_unpack, container, false);
		mEditText = (EditText) view.findViewById(R.id.id_et_pkgNum);
		mImageCamera = (ImageView) view.findViewById(R.id.id_iv_camera);
		mButton = (Button) view.findViewById(R.id.button1);
		mPackageListFragment = new PackageListTabFragment();
		mBtR = (Button) view.findViewById(R.id.btnToPackages);
		mImageCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openCameraIntent = new Intent(getActivity(),
						CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), mEditText.getText(),
						Toast.LENGTH_SHORT).show();
				mCallbacks.toTestFragment(
						mEditText.getText().toString().trim(), "UnPkg");
			}

		});

		mBtR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager mfragmentManager = getFragmentManager();
				FragmentTransaction ft = mfragmentManager.beginTransaction();
				ft.replace(R.id.container, mPackageListFragment);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String result;
		if (data != null) {
			result = data.getExtras().getString("result");
		} else {
			result = "";
		}
		mEditText.setText(result);

	}

	public static interface PkgListCallbacks {

		void toTestFragment(String PkgId, String type);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (PkgListCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement TestCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	void StartUnpackPackage() {
		Intent intent = new Intent();
		intent.putExtra("Action", "New");
		intent.putExtra("ExpId", mEditText.getText().toString().trim());
		intent.setClass(this.getActivity(), ExpressEditActivity.class);
		startActivityForResult(intent, 0);
	}
}
