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

public class MyCenterTabFragment extends Fragment {

	private TestCallbacks mCallbacks;
	private View view;
	@SuppressWarnings("unused")
	private FragmentManager mFragmentManager;
	@SuppressWarnings("unused")
	private ExpressListFragment mExpressListFragment;
	private TextView mSendTV;
	private TextView mReceiveTV;
	private TextView mInfoTV;
	private TextView mTransTV;
	private ImageView mSendIV;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.mycenter, container, false);
		SharedPreferences sp = this.getActivity().getSharedPreferences(
				"userInfo", Activity.MODE_PRIVATE);
		mFragmentManager = getFragmentManager();
		mExpressListFragment = new ExpressListFragment();
		mSendIV = (ImageView) view.findViewById(R.id.id_iv_tasksend);
		mSendTV = (TextView) view.findViewById(R.id.id_tv_tasksend);
		mTransTV = (TextView) view.findViewById(R.id.id_tv_tasktransfer);
		mInfoTV = (TextView) view.findViewById(R.id.id_tv_taskmyinfo);
		mReceiveTV = (TextView) view.findViewById(R.id.id_tv_taskreceive);

		if (sp.contains("uname")) {
			// 派送任务
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
			// 揽收任务
			mReceiveTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showReceiveTasks();
				}
			});
			// 转运任务
			mTransTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showTransferTasks();
				}
			});
			// 个人信息
			mInfoTV.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showMyinfo();
				}
			});

		} else {
			mSendIV.setOnClickListener(mNoClickListener);
			mSendTV.setOnClickListener(mNoClickListener);
			mReceiveTV.setOnClickListener(mNoClickListener);
			mTransTV.setOnClickListener(mNoClickListener);
			mInfoTV.setOnClickListener(mNoClickListener);
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
	public static interface TestCallbacks {
		void toFragment(String type);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (TestCallbacks) activity;
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
