package com.track.ui.main;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amap.location.demo.MultyLocationActivity;
import com.track.app.user.R;
import com.track.ui.domain.ExpressListFragment;
import com.track.ui.domain.ExpressListFragment.OnFragmentInteractionListener;
import com.track.ui.domain.ExpressSendFragment;
import com.track.ui.domain.PackageListFragment;
import com.track.ui.domain.TransHistoryListFragment;
import com.track.ui.minor.MyCenterTabFragment;
import com.track.ui.minor.PackageListTabFragment;
import com.track.ui.minor.TransPackageTabFragment;
import com.track.ui.misc.CustomerListActivity;
import com.zxing.activity.CaptureActivity;

@SuppressLint("RtlHardcoded")
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks,
		MyCenterTabFragment.TestCallbacks, ActionBar.TabListener,
		OnFragmentInteractionListener, PackageListTabFragment.PkgListCallbacks,
		PackageUnpackFragment.PkgListCallbacks,
		TransPackageTabFragment.mTrackCallbacks,
		PackageReceiveFragment.PkgListCallbacks,
		PackageListFragment.OnFragmentInteractionListener {

	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	private ActionBarHelper mActionBar;

	private TransPackageTabFragment mTransPackageTabFragment;
	private ExpressSendFragment mExpressSendFragment;
	private FragmentManager mfragmentManager;
	private ExpressReceiveFragment mExpressReceiveFragment;
	private ExpressDispatcherFragment mExpressDispatcherFragment;
	private ExpressListFragment mExpressListFragment;
	private PackageUnpackFragment mPackageUnpackFragment;
	private PackagePackFragment mPackagePackFragment;
	private PackageReceiveFragment mPackageReceiveFragment;
	private AboutFragment mAboutFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavigationDrawerFragment = new NavigationDrawerFragment();
		mActionBar = new ActionBarHelper();
		// Bundle extras = getIntent().getExtras();
		// mNavigationDrawerFragment.setArguments(extras);
		mActionBar.init();
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
	 * 点击抽屉中的不同的item时，分别响应不同的动作
	 * 
	 * @author Eamonn
	 */
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// 获得fragmentManager
		SharedPreferences sp = getSharedPreferences("userInfo",
				Activity.MODE_PRIVATE);
		mfragmentManager = getSupportFragmentManager();
		mTransPackageTabFragment = new TransPackageTabFragment();
		mExpressSendFragment = new ExpressSendFragment();
		mExpressReceiveFragment = new ExpressReceiveFragment();
		mExpressDispatcherFragment = new ExpressDispatcherFragment();
		mPackagePackFragment = new PackagePackFragment();
		mPackageUnpackFragment = new PackageUnpackFragment();
		mPackageReceiveFragment = new PackageReceiveFragment();
		mAboutFragment = new AboutFragment();

		FragmentTransaction mFragmentTransaction = mfragmentManager
				.beginTransaction();
		// 然后替换当前的fragment，并将参数item位置参数+1传递给placeholderFragment
		switch (position) {
		// 首页
		case 0:
			mFragmentTransaction.replace(R.id.container,
					PlaceHolderFragment.newInstance(position + 1));
			mFragmentTransaction.commit();
			// 关闭抽屉
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;
		// 快件揽收
		case 1:
			if (sp.getInt("role", -1) == 0) {
				mFragmentTransaction.replace(R.id.container,
						mExpressReceiveFragment);
				mFragmentTransaction.commit();
				onSectionAttached(position + 1);
			} else {
				test();
			}
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;
		// 快件派送
		case 2:
			if (sp.getInt("role", -1) == 0) {
				mFragmentTransaction.replace(R.id.container,
						mExpressDispatcherFragment);
				mFragmentTransaction.commit();
				onSectionAttached(position + 1);
			} else {
				test();
			}
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;

		// 包裹拆包
		case 3:
			if (sp.getInt("role", -1) == 1) {
				mFragmentTransaction.replace(R.id.container,
						mPackageUnpackFragment);
				mFragmentTransaction.commit();
				onSectionAttached(position + 1);
			} else {
				test();
			}
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;

		// 包裹打包
		case 4:
			if (sp.getInt("role", -1) == 1) {
				mFragmentTransaction.replace(R.id.container,
						mPackagePackFragment);
				mFragmentTransaction.commit();
				onSectionAttached(position + 1);
			} else {
				test();
			}
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;

		// 接收包裹
		case 5:
			if (sp.getInt("role", -1) == 1 || sp.getInt("role", -1) == 2) {
				mFragmentTransaction.replace(R.id.container,
						mPackageReceiveFragment);
				mFragmentTransaction.commit();
				onSectionAttached(position + 1);
			} else {
				test();
			}
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;

		// 客户管理
		case 6:
			getCustomers();
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;

		// 关于
		case 7:
			mFragmentTransaction.replace(R.id.container, mAboutFragment);
			mFragmentTransaction.commit();
			onSectionAttached(position + 1);
			try {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} catch (NullPointerException e) {

			}
			break;
		}

	}

	private void getCustomers() {
		Intent intent = new Intent();
		intent.setClass(this, CustomerListActivity.class);
		intent.putExtra("Action", "Query");
		startActivityForResult(intent, 0);
	}

	/**
	 * 当选中不同的抽屉选项时，设置不同的ActionBar标题
	 * 
	 * @param number
	 */
	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.menu_index);
			break;
		case 2:
			mTitle = getString(R.string.menu_receive);
			break;
		case 3:
			mTitle = getString(R.string.menu_deliver);
			break;
		case 4:
			mTitle = getString(R.string.menu_unpack);
			break;
		case 5:
			mTitle = getString(R.string.menu_pack);
			break;
		case 6:
			mTitle = getString(R.string.menu_receive_pacakge);
			break;
		case 7:
			mTitle = getString(R.string.menu_customermanage);
			break;
		case 8:
			mTitle = getString(R.string.menu_about);
			break;
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
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

	private static Boolean isQuit = false;
	Timer timer = new Timer();

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(getBaseContext(), "再按一次返回键退出程序",
						Toast.LENGTH_SHORT).show();
				TimerTask task = null;
				task = new TimerTask() {
					@Override
					public void run() {
						isQuit = false;
					}
				};
				timer.schedule(task, 2000);
			} else {
				finish();
				System.exit(0);
			}
		}
		return false;
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

		if (id == R.id.action_exit) {
			this.finish();
			return true;
		}

		if (id == R.id.action_my_package) {

			showPackages();

			return true;
		}

		if (id == R.id.action_my_location) {
			toLocationActivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void toLocationActivity() {
		Intent multyIntent = new Intent(MainActivity.this,
				MultyLocationActivity.class);
		startActivity(multyIntent);

	}

	private void showPackages() {

	}

	/**
	 * 获取扫描的条形码数据，传递给fragment
	 * 
	 * @author Eamonn
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
			mTitle = getString(R.string.menu_index);
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFragmentInteraction(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toFragment(String type) {
		mfragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = mfragmentManager.beginTransaction();
		ft.replace(R.id.container, ExpressListFragment.newInstance(type));
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void toUnpackExpListFragment(String PkgId, String type) {
		mfragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = mfragmentManager.beginTransaction();
		ft.replace(R.id.container, ExpressListFragment.newInstance(PkgId, type));
		ft.addToBackStack(null);
		ft.commit();
	}

	public void test() {
		Toast.makeText(getApplicationContext(), "身份不允许！", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void toTrackFragmets(String id) {
		// TODO Auto-generated method stub
		mfragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = mfragmentManager.beginTransaction();
		ft.replace(R.id.container, TransHistoryListFragment.newInstance(id));
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void toReceiveFragment(String PkgId, String type) {

	}
}
