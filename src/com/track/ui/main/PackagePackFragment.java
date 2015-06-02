package com.track.ui.main;

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
import com.track.ui.domain.PackageEditActivity;
import com.track.ui.minor.PackageListTabFragment;
import com.zxing.activity.CaptureActivity;

public class PackagePackFragment extends Fragment {

	private View view;
	private ImageView mImageCamera;
	private static EditText mEditText;
	private PackageListTabFragment mCenterTabFragment;
	private Button mButton;
	private Button mBtR;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.package_pack, container, false);
		mEditText = (EditText) view.findViewById(R.id.id_et_pkgNum);
		mImageCamera = (ImageView) view.findViewById(R.id.id_iv_camera);
		mButton = (Button) view.findViewById(R.id.button1);
		mBtR = (Button) view.findViewById(R.id.btnToPackages);
		mCenterTabFragment = new PackageListTabFragment();
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
					builder.setTitle("此操作将会新建包裹：");
					// 设置Content来显示一个信息
					builder.setMessage("确定新建包裹:\n" + mEditText.getText()
							+ "\n吗？");
					// 设置一个PositiveButton
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									StartCreatePackage();
									// Toast.makeText(getActivity(),
									// mEditText.getText(),
									// Toast.LENGTH_SHORT).show();
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
				ft.replace(R.id.container, mCenterTabFragment);
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

	void StartCreatePackage() {
		Intent intent = new Intent();
		intent.putExtra("Action", "New");
		intent.putExtra("PkgId", mEditText.getText().toString().trim());
		intent.setClass(this.getActivity(), PackageEditActivity.class);
		startActivityForResult(intent, 0);
	}
}
