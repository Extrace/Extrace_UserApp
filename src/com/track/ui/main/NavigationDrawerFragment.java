package com.track.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.track.ui.minor.MyInfoActivity;

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

		SharedPreferences sp = this.getActivity().getSharedPreferences(
				"userInfo", Activity.MODE_PRIVATE);
		mTextView.setText(sp.getString("uname", "点击登录哦~"));

		if (!sp.contains("uname")) {
			setLoginClickLisener();
			mDrawerListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							if (position > 0 && position < 6) {
								Toast.makeText(
										getActivity().getApplicationContext(),
										"请先登录！", Toast.LENGTH_LONG).show();
							} else
								selectItem(position);
						}
					});
		} else {
			setAnotherClick();
			mDrawerListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							selectItem(position);
						}
					});
		}
		MenuAdapter menuAdapter = new MenuAdapter(getActivity());
		mDrawerListView.setAdapter(menuAdapter);
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return rootview;
	}

	private void setAnotherClick() {
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				test();
			}
		});
		mTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				test();
			}
		});

	}

	private void setLoginClickLisener() {
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toLoginActivity();
			}
		});

		mTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toLoginActivity();
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
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	private void test() {
		// Toast.makeText(getActivity().getApplicationContext(), "您已登录",
		// Toast.LENGTH_LONG).show();
		Intent myinfoIntent = new Intent(getActivity(), MyInfoActivity.class);
		startActivity(myinfoIntent);
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
