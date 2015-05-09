package com.track.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.ui.adapter.MenuAdapter;

public class NavigationDrawerFragment extends Fragment {

	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	private NavigationDrawerCallbacks mCallbacks;

	private ListView mDrawerListView;

	private TextView mTextView;

	private ImageView mImageView;

	private View rootview;

	private int mCurrentSelectedPosition = 0;

	public NavigationDrawerFragment() {

	}

	public static NavigationDrawerFragment newInstance() {
		NavigationDrawerFragment fragment = new NavigationDrawerFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState
					.getInt(STATE_SELECTED_POSITION);
		}

		selectItem(mCurrentSelectedPosition);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootview = inflater.inflate(R.layout.fragment_drawer, container, false);
		mDrawerListView = (ListView) rootview.findViewById(R.id.id_drawer_list);
		mImageView = (ImageView) rootview.findViewById(R.id.id_iv_account);
		mTextView = (TextView) rootview.findViewById(R.id.id_bt_login);
		setClickLisener();
		mDrawerListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectItem(position);
					}
				});
		MenuAdapter menuAdapter = new MenuAdapter(getActivity());
		mDrawerListView.setAdapter(menuAdapter);
		// 为listview设置当前的item为选中状态，默认为0，即第一个item
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return rootview;
	}

	private void setClickLisener() {
		// TODO Auto-generated method stub
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				test();
			}
		});

		mTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				test();
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	/**
	 * 当我们点击抽屉里的每个item时， 首先设置当前选中item的为选中状态 若item和activity之前有过交互，则设置position
	 * 
	 * @param position
	 */
	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		Log.e("fragment:position", "" + position);
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	private void test() {
		Log.d("Test", "onClickListener ist gestartet");
		Toast.makeText(getActivity().getApplicationContext(), "Test",
				Toast.LENGTH_LONG).show();
		toLoginActivity();
	}

	/**
	 * 当fragment和activity建立关系时触发 在本工程中，就是当你点击fragment的菜单项时 会回到mainActivity
	 * 所以当你点击一个项目，就会初始化mcallbacks
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	/**
	 * 保存当前的item的position值
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	/*
	 * private ActionBar getActionBar() { return ((ActionBarActivity)
	 * getActivity()).getSupportActionBar(); }
	 */

	// 这是一个回调接口
	public static interface NavigationDrawerCallbacks {
		void onNavigationDrawerItemSelected(int position);

	}

	public void toLoginActivity() {
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		startActivity(intent);
	}
}
