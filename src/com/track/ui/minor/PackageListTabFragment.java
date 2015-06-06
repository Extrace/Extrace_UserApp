package com.track.ui.minor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.ui.domain.ExpressListFragment;

public class PackageListTabFragment extends Fragment {

	private PkgListCallbacks mCallbacks;
	private View view;
	@SuppressWarnings("unused")
	private FragmentManager mFragmentManager;
	@SuppressWarnings("unused")
	private ExpressListFragment mExpressListFragment;
	private TextView mSendTV;
	private TextView mReceiveTV;
	private TextView mTransTV;
	private ImageView mSendIV;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_package_list, container,
				false);
		SharedPreferences sp = this.getActivity().getSharedPreferences(
				"userInfo", Activity.MODE_PRIVATE);
		mFragmentManager = getFragmentManager();
		mExpressListFragment = new ExpressListFragment();
		mSendIV = (ImageView) view.findViewById(R.id.id_iv_tasksend);
		mSendTV = (TextView) view.findViewById(R.id.id_tv_tasksend);
		mTransTV = (TextView) view.findViewById(R.id.id_tv_tasktransfer);
		mReceiveTV = (TextView) view.findViewById(R.id.id_tv_taskreceive);

		if (sp.contains("uname")) {
			// 派送包裹
			mSendIV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showSendTasks();
				}
			});
			mSendTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showSendTasks();
				}
			});
			// 揽收包裹
			mReceiveTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showReceiveTasks();
				}
			});
			// 转运包裹
			mTransTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showTransferTasks();
				}
			});

		} else {
			mSendIV.setOnClickListener(mNoClickListener);
			mSendTV.setOnClickListener(mNoClickListener);
			mReceiveTV.setOnClickListener(mNoClickListener);
			mTransTV.setOnClickListener(mNoClickListener);
		}
		return view;
	}

	OnClickListener mNoClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
		}
	};

	protected void showSendTasks() {
		mCallbacks.toFragment("ExDLV");
	}

	protected void showReceiveTasks() {
		mCallbacks.toFragment("ExRCV");
	}

	protected void showTransferTasks() {

		mCallbacks.toFragment("ExTAN");

	}

	protected void showMyinfo() {

		Intent myinfoIntent = new Intent(getActivity(), MyInfoActivity.class);
		startActivity(myinfoIntent);

	}

	// 这是一个回调接口
	public static interface PkgListCallbacks {
		void toFragment(String type);
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

}
