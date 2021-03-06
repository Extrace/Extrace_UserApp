package com.track.ui.domain;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.track.loader.ExpressListLoader;
import com.track.misc.model.ExpressSheet;
import com.track.ui.adapter.ExpressListAdapter;
import com.track.util.ExTraceApplication;

public class ExpressListFragment extends ListFragment {

	private static final String ARG_EX_TYPE = "ExType";
	private static final String ARG_PK_ID = "PkgId";
	private String mExType;
	private ExpressListAdapter mAdapter;

	private ExpressListLoader mLoader;
	private static String mPkgId;
	@SuppressWarnings("unused")
	private View rootView;
	private ExpressSheet selectItem;
	@SuppressWarnings("unused")
	private int selectPosition;

	Intent mIntent;

	private OnFragmentInteractionListener mListener;

	public static ExpressListFragment newInstance(String ex_type) {

		ExpressListFragment fragment = new ExpressListFragment();

		Bundle args = new Bundle();
		args.putString(ARG_EX_TYPE, ex_type); // 构造方法传入参数,使用Bundle来作为参数的容器
		fragment.setArguments(args);

		return fragment;
	}

	public static ExpressListFragment newInstance(String id, String ex_type) {

		ExpressListFragment fragment = new ExpressListFragment();

		Bundle args = new Bundle();
		args.putString(ARG_EX_TYPE, ex_type);
		args.putString(ARG_PK_ID, id);// 构造方法传入参数,使用Bundle来作为参数的容器
		fragment.setArguments(args);
		return fragment;
	}

	public ExpressListFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e("onActivity", "created");
		if (getArguments() != null) { // 另一种读出传入参数的方式
			mExType = getArguments().getString(ARG_EX_TYPE);
			mPkgId = getArguments().getString(ARG_PK_ID);
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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

		selectItem = mAdapter.getItem(info.position);
		if (selectItem.getStatus() == ExpressSheet.STATUS.STATUS_PARTATION) {
			selectPosition = info.position;
			menu.setHeaderTitle("运单: " + selectItem.getId());
			menu.add(info.position, 1, 0, "放入包裹");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		if (item.getTitle().equals("放入包裹")) {
			SelectOk();
		}
		return super.onContextItemSelected(item);
	}

	private void SelectOk() {
		mLoader = new ExpressListLoader(mAdapter, this.getActivity());
		mLoader.AddtoPkg(selectItem.getId(), mPkgId);
		mLoader.LoadExpressList();
	}

	public interface OnFragmentInteractionListener {
		public void onFragmentInteraction(String id);
	}

	private void RefreshList() {

		String pkgId = null;
		mLoader = new ExpressListLoader(mAdapter, this.getActivity());
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
		case "UnPkg":
			pkgId = mPkgId;
			mLoader.UnpackExpressList(pkgId);
			break;
		case "NPkg":
			pkgId = mPkgId;
			break;
		}
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
