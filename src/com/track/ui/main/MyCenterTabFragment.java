package com.track.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.ui.domain.ExpressListFragment;

public class MyCenterTabFragment extends Fragment {

	private View view;
	private FragmentManager mFragmentManager;
	private ExpressListFragment mExpressListFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.mycenter, container, false);
		mFragmentManager = getFragmentManager();
		mExpressListFragment = new ExpressListFragment();
		// 派送任务
		view.findViewById(R.id.id_iv_tasksend).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// Toast.makeText(getActivity().getApplicationContext(),
						// "tst", Toast.LENGTH_LONG).show();
						showSendTasks();
					}
				});
		view.findViewById(R.id.id_tv_tasksend).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// Toast.makeText(getActivity().getApplicationContext(),
						// "tst", Toast.LENGTH_LONG).show();
						showSendTasks();
					}
				});
		// 揽收任务
		view.findViewById(R.id.id_iv_tasksend).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showReceiveTasks();
					}
				});
		// 转运任务
		view.findViewById(R.id.id_iv_tasksend).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showTransferTasks();
					}
				});
		// 个人信息
		view.findViewById(R.id.id_iv_tasksend).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showMyinfo();
					}
				});
		return view;
	}

	protected void showMyinfo() {
		// TODO Auto-generated method stub

	}

	protected void showTransferTasks() {
		// TODO Auto-generated method stub

	}

	protected void showReceiveTasks() {
		// TODO Auto-generated method stub

	}

	protected void showSendTasks() {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity().getApplicationContext(), "tst",
				Toast.LENGTH_LONG).show();
		mFragmentManager
				.beginTransaction()
				.replace(R.id.id_fragment_mycenter,
						mExpressListFragment.newInstance("ExDLV")).commit();
	}
}
