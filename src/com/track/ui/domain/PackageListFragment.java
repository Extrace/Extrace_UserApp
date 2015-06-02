package com.track.ui.domain;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.track.loader.ExpressListLoader;
import com.track.misc.model.ExpressSheet;
import com.track.ui.adapter.ExpressListAdapter;
import com.track.util.ExTraceApplication;

public class PackageListFragment extends ListFragment {

	private static final String ARG_EX_TYPE = "ExType";

	private String mExType;
	private ExpressListAdapter mAdapter;
	private ExpressListLoader mLoader;

	Intent mIntent;

	private OnFragmentInteractionListener mListener;

	public static PackageListFragment newInstance(String ex_type) {

		PackageListFragment fragment = new PackageListFragment();

		Bundle args = new Bundle();
		args.putString(ARG_EX_TYPE, ex_type); // 构造方法传入参数,使用Bundle来作为参数的容器
		fragment.setArguments(args);
		return fragment;
	}

	public PackageListFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e("onActivity", "created");
		if (getArguments() != null) { // 另一种读出传入参数的方式
			mExType = getArguments().getString(ARG_EX_TYPE);
		}

		setEmptyText("您还没有快递任务哦~");

		// 初始化ExList适配器
		mAdapter = new ExpressListAdapter(new ArrayList<ExpressSheet>(),
				this.getActivity(), mExType);

		setListAdapter(mAdapter);

		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		registerForContextMenu(getListView());

		RefreshList();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (null != mListener) {
			mListener.onFragmentInteraction(mAdapter.getItem(position).getId());
		}
		EditExpress(mAdapter.getItem(position));
	}

	public interface OnFragmentInteractionListener {
		public void onFragmentInteraction(String id);
	}

	private void RefreshList() {

		String pkgId = null;
		switch (mExType) {
		case "ExDLV":
			pkgId = ((ExTraceApplication) this.getActivity().getApplication())
					.getLoginUser().getDeliverpid();
			break;
		case "ExRCV":
			pkgId = ((ExTraceApplication) this.getActivity().getApplication())
					.getLoginUser().getReceivepid();
			break;
		case "ExTAN":
			pkgId = ((ExTraceApplication) this.getActivity().getApplication())
					.getLoginUser().getTranspid();
			break;
		}
		mLoader = new ExpressListLoader(mAdapter, this.getActivity());
		mLoader.LoadExpressListInPackage(pkgId);
		mLoader.LoadExpressList();
	}

	/**
	 * 编辑运单？why？
	 * 
	 * @param es
	 */
	void EditExpress(ExpressSheet es) {
		Intent intent = new Intent();
		intent.putExtra("Action", "Edit");
		intent.putExtra("ExpressSheet", es);
		intent.setClass(this.getActivity(), ExpressEditActivity.class);
		startActivityForResult(intent, 0);
	}

}
