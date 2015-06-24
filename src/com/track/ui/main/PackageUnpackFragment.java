package com.track.ui.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.track.loader.PackageLoader;
import com.track.misc.model.Package;
import com.track.net.IDataAdapter;
import com.track.ui.domain.ExpressListFragment;
import com.track.ui.minor.PackageListTabFragment;
import com.zxing.activity.CaptureActivity;

public class PackageUnpackFragment extends Fragment implements
		IDataAdapter<Package> {

	private View view;
	private ImageView mImageCamera;
	private static EditText mEditText;
	private Button mButton;
	private PackageListTabFragment mPackageListFragment;
	private Button mBtR;
	private PackageLoader mLoader;
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
				if (mEditText.getText().toString().trim().equals("")) {
					Toast.makeText(getActivity(), "包裹号不能为空！",
							Toast.LENGTH_SHORT).show();
				} else {
					// 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					// 设置Title的图标
					builder.setIcon(R.drawable.ic_launcher);
					// 设置Title的内容
					builder.setTitle("此操作将会拆包：");
					// 设置Content来显示一个信息
					builder.setMessage("确定拆包:\n" + mEditText.getText() + "\n吗？");
					// 设置一个PositiveButton
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// mCallbacks.toUnpackExpListFragment(
									// mEditText.getText().toString()
									// .trim(), "UnPkg");
									unPack();
								}
							});
					// 设置一个NegativeButton
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							});
					builder.show();

				}
			}
		});

		mBtR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager mfragmentManager = getFragmentManager();
				FragmentTransaction ft = mfragmentManager.beginTransaction();
				ft.replace(R.id.container,
						ExpressListFragment.newInstance("ExTAN"));
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		return view;
	}

	public void unPack() {
		mLoader = new PackageLoader(this, this.getActivity());
		mLoader.UnpackExpressList(mEditText.getText().toString().trim());
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

		void toUnpackExpListFragment(String PkgId, String type);
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
