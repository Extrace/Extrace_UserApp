package com.track.app.user.fragment;

import com.track.app.user.R;
import com.track.app.user.R.id;
import com.track.app.user.R.layout;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NavigationDrawerFragment extends Fragment {

	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	private NavigationDrawerCallbacks mCallbacks;

	private ListView mDrawerListView;

	private int mCurrentSelectedPosition = 0;

	public NavigationDrawerFragment() {
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	/**
	 * 抽屉也是fragment，这里定义了它的相关属性和格式 这里为抽屉定义了一个listview
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_drawer, container, false);
		
		// 获取抽屉的listview
		mDrawerListView = (ListView) v.findViewById(R.id.id_drawer_list);
		
		// 定义每个选项的点击监听器
		mDrawerListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectItem(position);
					}
				});

		// 为listview设置adapter，这个ArrayAdapter的构造器参数应该是固定的
		// 最后一个参数是一个String数组，并获取了每个title_section的内容
		mDrawerListView.setAdapter(new ArrayAdapter<String>(getActionBar()
				.getThemedContext(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, new String[] {
						getString(R.string.title_section1),
						getString(R.string.title_section2),
						getString(R.string.title_section3), }));
		MenuAdapter menuAdapter =  new MenuAdapter(getActivity());
		mDrawerListView.setAdapter(menuAdapter);
		
		// 为listview设置当前的item为选中状态，默认为0，即第一个item
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return v;
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

	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

	// 这是一个回调接口
	public static interface NavigationDrawerCallbacks {
		void onNavigationDrawerItemSelected(int position);
	}

}
