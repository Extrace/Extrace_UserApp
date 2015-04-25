package com.track.app.user;

import com.track.app.user.R;
import com.zxing.activity.CaptureActivity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	private ActionBarHelper mActionBar;

	/**
	 * 
	 */
	private TransNodeTabFragment mTransNodeTabFragment;
	private MyCenterTabFragment mCenterTabFragment;
	private TransPackageTabFragment mTransPackageTabFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavigationDrawerFragment = new NavigationDrawerFragment();
		mActionBar = new ActionBarHelper();
		mActionBar.init();

		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.navigation_drawer, mNavigationDrawerFragment)
		// .commit();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.navigation_drawer, mNavigationDrawerFragment)
				.commit();

		setUpDrawer();
	}

	/**
	 * 创建抽屉
	 */
	private void setUpDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerListener(new MyDrawerListener());
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// 初始化DrawerToggle
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});
	}

	/**
	 * 判断抽屉是否打开
	 */
	// public boolean isDrawerOpen() {
	// return mDrawerLayout != null
	// && mDrawerLayout.isDrawerOpen(Gravity.LEFT);
	// }

	/**
	 * 点击抽屉中的不同的item时，分别响应不同的动作
	 * 
	 * @author Eamonn
	 */
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// 获得fragmentManager
		FragmentManager fragmentManager = getSupportFragmentManager();
		PlaceHolderFragment mPlaceholderFragment = new PlaceHolderFragment();
		mTransNodeTabFragment = new TransNodeTabFragment();
		mCenterTabFragment = new MyCenterTabFragment();
		FragmentTransaction mFragmentTransaction = fragmentManager
				.beginTransaction();
		// 然后替换当前的fragment，并将参数item位置参数+1传递给placeholderFragment
		switch (position) {
		case 0:
			mFragmentTransaction.replace(R.id.container,
					mPlaceholderFragment.newInstance(position + 1)).commit();
			// 关闭抽屉
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;
		case 1:
			mFragmentTransaction.replace(R.id.container, mTransNodeTabFragment)
					.commit();
			// 关闭抽屉
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;
		case 2:
			mFragmentTransaction.replace(R.id.container, mCenterTabFragment)
					.commit();
			// 关闭抽屉
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;
		}

	}

	/**
	 * 当选中不同的抽屉选项时，设置不同的ActionBar标题
	 * 
	 * @param number
	 */
	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	/**
	 * 显示菜单（也是为了显示ActionBar上的图标菜单选项）
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 当用户点击菜单item（这个item包括：抽屉图标、以及actionBar右边的菜单图标）
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		int id = item.getItemId();
		// 当点击扫描二维码的图标时，调用扫描类
		if (id == R.id.action_scan) {
			Intent openCameraIntent = new Intent(MainActivity.this,
					CaptureActivity.class);
			startActivityForResult(openCameraIntent, 0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 获取扫描的条形码数据，传递给fragment
	 * 
	 * @author Eamonn
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			String result = data.getExtras().getString("result");
			mTransPackageTabFragment = new TransPackageTabFragment();
			Bundle bundle = new Bundle();
			bundle.putString("pkgNum", result);
			mTransPackageTabFragment.setArguments(bundle);
			mTransPackageTabFragment.getDatas();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * 设置Drawerlayout的监听器，当抽屉打开、关闭、滑动和状态改变时 按钮的变化效果和ActionBar的title变化效果
	 * 
	 * @author Eamonn
	 * 
	 */
	private class MyDrawerListener implements DrawerLayout.DrawerListener {
		@Override
		public void onDrawerOpened(View drawerView) {
			mDrawerToggle.onDrawerOpened(drawerView);
			mActionBar.onDrawerOpened();
		}

		@Override
		public void onDrawerClosed(View drawerView) {
			mDrawerToggle.onDrawerClosed(drawerView);
			mActionBar.setTitle(mTitle);
			mActionBar.onDrawerClosed();
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
		}

		@Override
		public void onDrawerStateChanged(int newState) {
			mDrawerToggle.onDrawerStateChanged(newState);
		}
	}

	/**
	 * 设置ActionBar相关属性，包括抽屉打开和关闭时：ActionBar的文字显示变化
	 * 
	 * @author Eamonn
	 * 
	 */
	private class ActionBarHelper {
		private final ActionBar mActionBar;
		private CharSequence mDrawerTitle;
		private CharSequence mTitle;

		ActionBarHelper() {
			mActionBar = getSupportActionBar();
		}

		public void init() {
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setDisplayShowHomeEnabled(false);
			mTitle = getString(R.string.title_section1);
			mActionBar.setTitle(mTitle);
			mDrawerTitle = getTitle();
		}

		public void onDrawerClosed() {
			mActionBar.setTitle(mTitle);
		}

		public void onDrawerOpened() {
			mActionBar.setTitle(mDrawerTitle);
		}

		public void setTitle(CharSequence title) {
			mTitle = title;
		}
	}

}