package com.track.ui.domain;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.track.loader.PackageListLoader;
import com.track.misc.model.Package;
import com.track.ui.adapter.PackageListAdapter;

public class PackageListFragment extends ListFragment {

	private static final String ARG_EX_TYPE = "ExType";

	private PackageListAdapter mAdapter;
	private PackageListLoader mLoader;

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
		}

		setEmptyText("您还没有接收任何包裹哦~");

		// 初始化PkgList适配器
		mAdapter = new PackageListAdapter(new ArrayList<Package>(),
				this.getActivity());

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
		EditPackage((mAdapter.getItem(position)));
	}

	public interface OnFragmentInteractionListener {
		public void onFragmentInteraction(String id);
	}

	private void RefreshList() {

		mLoader = new PackageListLoader(mAdapter, this.getActivity());
		mLoader.getPackageListByUid();
	}

	/**
	 * 编辑运单？why？
	 * 
	 * @param es
	 */
	void EditPackage(Package pkg) {
		Intent intent = new Intent();
		intent.putExtra("Action", "Edit");
		intent.putExtra("Package", pkg);
		intent.setClass(this.getActivity(), PackageEditActivity.class);
		startActivityForResult(intent, 0);
	}

}
