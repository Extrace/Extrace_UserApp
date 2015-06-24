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

import com.track.app.user.R;
import com.track.loader.PackageLoader;
import com.track.misc.model.Package;
import com.track.net.IDataAdapter;
import com.track.ui.domain.ExpressEditActivity;
import com.track.ui.domain.PackageListFragment;
import com.zxing.activity.CaptureActivity;

public class PackageReceiveFragment extends Fragment implements
		IDataAdapter<Package> {

	private View view;
	private ImageView mImageCamera;
	private static EditText mEditText;
	private Button mButton;
	private PackageListFragment mPackageListFragment;
	private Button mBtR;
	private PackageLoader mLoader;
	private PkgListCallbacks mCallbacks;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.package_receive, container, false);
		mEditText = (EditText) view.findViewById(R.id.id_et_pkgNum);
		mImageCamera = (ImageView) view.findViewById(R.id.id_iv_camera);
		mButton = (Button) view.findViewById(R.id.bt_receive);
		mPackageListFragment = new PackageListFragment();
		mBtR = (Button) view.findViewById(R.id.btnMyPackages);
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
				// mCallbacks.toReceiveFragment(mEditText.getText().toString()
				// .trim(), "RcvPkg");
				receivePackage();
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

	protected void receivePackage() {
		mLoader = new PackageLoader(this, this.getActivity());
		mLoader.receivePackage(mEditText.getText().toString());
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

		void toReceiveFragment(String PkgId, String type);
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

	@Override
	public Package getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(Package data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub

	}
}
