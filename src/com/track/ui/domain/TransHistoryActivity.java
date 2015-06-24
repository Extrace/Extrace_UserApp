package com.track.ui.domain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amap.location.demo.MultyLocationActivity;
import com.track.app.user.R;

public class TransHistoryActivity extends ActionBarActivity {

	private TransHistoryListFragment mTransHistoryListFragment;
	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String id = null;
		mIntent = getIntent();
		id = mIntent.getStringExtra("ExpId");
		Log.e("expid:", id);
		setContentView(R.layout.activity_transhistory_edit);
		final ActionBar actionBar = getSupportActionBar();
		mTransHistoryListFragment = TransHistoryListFragment.newInstance(id);
		actionBar.setTitle("物流信息");
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction()
				.replace(R.id.container, mTransHistoryListFragment).commit();

	}

	/**
	 * 创建菜单选项
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.transhistory_edit, menu);
		return true;
	}

	/**
	 * 点击不同菜单项出触发不同的事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_map:
			// Toast.makeText(this, "map", Toast.LENGTH_LONG).show();
			Intent mIntent = new Intent();
			mIntent.setClass(this, MultyLocationActivity.class);
			startActivityForResult(mIntent, 0);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
