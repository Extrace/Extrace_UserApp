package com.track.ui.misc;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.loader.RegionListLoader;
import com.track.misc.model.CodeNamePair;
import com.track.ui.adapter.RegionListAdapter;

@SuppressWarnings("all")
public class RegionListActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fm = getSupportFragmentManager();

		// Create the list fragment and add it as our sole content.
		if (fm.findFragmentById(android.R.id.content) == null) {
			PlaceholderFragment list = new PlaceholderFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	public static class PlaceholderFragment extends ListFragment {
		private RegionListAdapter mAdapter;
		private RegionListLoader mLoader;

		private int opStatus;
		private String selectCode, selectString;
		Intent mIntent;

		public PlaceholderFragment() {
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			setEmptyText("无行政区域选定!");

			setHasOptionsMenu(true);

			mAdapter = new RegionListAdapter(new ArrayList<CodeNamePair>(),
					this.getActivity());
			setListAdapter(mAdapter);

			// setListShown(false);

			if (mIntent.hasExtra("RegionId")) {
				selectCode = mIntent.getStringExtra("RegionId");
				selectString = mIntent.getStringExtra("RegionString");
				this.getActivity().setTitle(selectString);
			}

			opStatus = 0;
			RefreshList(opStatus, "");
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			mIntent = activity.getIntent();
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

			inflater.inflate(R.menu.region_list, menu);
			MenuItem item = menu.findItem(R.id.action_reset);
			MenuItemCompat
					.setShowAsAction(
							item,
							MenuItemCompat.SHOW_AS_ACTION_ALWAYS
									| MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			item = menu.findItem(R.id.action_ok);
			MenuItemCompat
					.setShowAsAction(
							item,
							MenuItemCompat.SHOW_AS_ACTION_ALWAYS
									| MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			switch (id) {
			case R.id.action_ok:
				if (opStatus > 2) {
					SelectOk();
				} else {
					Toast.makeText(this.getActivity(), "请选择完整的三级行政区!",
							Toast.LENGTH_SHORT).show();
				}
				return true;
			case R.id.action_reset:
				opStatus = 0;
				RefreshList(opStatus, "");
				this.getActivity().setTitle("请选择区域");
				return true;
			case R.id.action_settings:
				// SettingsActivity a = new SettingsActivity();
				// a.startActivities(null);
				return true;
			}
			return super.onOptionsItemSelected(item);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// Insert desired behavior here.
			CodeNamePair cnp = mAdapter.getItem(position);
			selectCode = cnp.getCode();
			switch (opStatus) {
			case 0:
				selectString = cnp.getName();
				opStatus++;
				RefreshList(opStatus, selectCode);
				break;
			case 1:
				selectString += " " + cnp.getName();
				opStatus++;
				RefreshList(opStatus, selectCode);
				break;
			case 2:
				opStatus++;
				selectString += " " + cnp.getName();
				RefreshList(opStatus, selectCode);
				break;
			default:
				break;
			}

			// selectCode = cnp.getCode();
			// selectString = cnp.getName();
			this.getActivity().setTitle(selectString);
		}

		private void SelectOk() {
			/* 将选中的对象赋值给Intent */
			mIntent.putExtra("RegionId", selectCode);
			mIntent.putExtra("RegionString", selectString);

			this.getActivity().setResult(RESULT_OK, mIntent);
			this.getActivity().finish();
		}

		private void RefreshList(int status, String region_code) {
			try {
				mLoader = new RegionListLoader(mAdapter, this.getActivity());
				switch (status) {
				case 0:
					mLoader.LoadProvinceList();
					break;
				case 1:
					mLoader.LoadCityList(region_code);
					break;
				case 2:
					mLoader.LoadTownList(region_code);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
