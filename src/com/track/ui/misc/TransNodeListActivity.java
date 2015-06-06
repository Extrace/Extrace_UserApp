package com.track.ui.misc;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.loader.TransNodeListLoader;
import com.track.misc.model.TransNode;
import com.track.ui.adapter.TransNodeListAdapter;

public class TransNodeListActivity extends ActionBarActivity {

	PlaceholderFragment mListfg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		FragmentManager fm = getSupportFragmentManager();

		if (fm.findFragmentById(android.R.id.content) == null) {
			mListfg = new PlaceholderFragment();
			fm.beginTransaction().add(android.R.id.content, mListfg).commit();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mListfg.onActivityResult(requestCode, resultCode, data);
	}

	public static class PlaceholderFragment extends ListFragment {

		private TransNodeListAdapter mNodeAdapter;
		private TransNodeListLoader mNodeLoader;

		private TransNode selectItem;
		@SuppressWarnings("unused")
		private int mPosition;

		Intent mIntent;

		public PlaceholderFragment() {

		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub

			super.onActivityCreated(savedInstanceState);
			setEmptyText("输入区域码或网点名称!");

			setHasOptionsMenu(true);
			mNodeAdapter = new TransNodeListAdapter(new ArrayList<TransNode>(),
					this.getActivity());

			setListAdapter(mNodeAdapter);
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			registerForContextMenu(getListView());

			if (mIntent.hasExtra("Action")) {
				if (mIntent.getStringExtra("Action").equals("New")) {
					RefreshList(mIntent.getStringExtra("NodeId"));
				} else {
					this.getActivity().setResult(RESULT_CANCELED, mIntent);
					this.getActivity().finish();
				}
			}

		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			mIntent = activity.getIntent();
			Log.e("onattach", mIntent.toString());
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

			// 客户列表菜单
			inflater.inflate(R.menu.transnode_list, menu);

			// 获取“搜索按钮”菜单控件
			MenuItem item = menu.findItem(R.id.action_search);
			final SearchView searchView = (SearchView) item.getActionView();
			if (searchView != null) {

				SearchViewCompat.setOnQueryTextListener(searchView,
						new OnQueryTextListenerCompat() {
							@Override
							public boolean onQueryTextChange(String newText) {

								return true;
							}

							// 当搜索结果提交时执行
							@Override
							public boolean onQueryTextSubmit(String query) {
								if (!TextUtils.isEmpty(query)) {
									RefreshList(query);
									SearchViewCompat.setQuery(searchView, null,
											true);
								}
								return true;
							}
						});
				SearchViewCompat.setOnCloseListener(searchView,
						new OnCloseListenerCompat() {
							@Override
							public boolean onClose() {
								if (!TextUtils.isEmpty(SearchViewCompat
										.getQuery(searchView))) {
									SearchViewCompat.setQuery(searchView, null,
											true);
								}
								return true;
							}

						});
				MenuItemCompat.setActionView(item, searchView);
			}
		}

		private void RefreshList(String code) {
			this.getActivity().setTitle("");
			if (code.length() > 6) {

				try {
					mNodeLoader = new TransNodeListLoader(mNodeAdapter,
							this.getActivity());
					mNodeLoader.LoadTransNodeListById(code);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				try {
					mNodeLoader = new TransNodeListLoader(mNodeAdapter,
							this.getActivity());
					mNodeLoader.LoadTransNodeListByCode(code);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// 点击客户列表的界面的菜单选项，分别触发不同的事件
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			switch (id) {
			case android.R.id.home:
				this.getActivity().finish();
				return true;
			case R.id.action_ok:
				SelectOk();
				return true;
			case R.id.action_edit:
				return true;
			case R.id.action_new:
				return true;
			case R.id.action_search:
				return true;
			}
			return super.onOptionsItemSelected(item);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			selectItem = mNodeAdapter.getItem(position);
			mPosition = position;
			this.getActivity().setTitle(selectItem.getNodename());
		}

		private void SelectOk() {
			if (selectItem != null) {
				mIntent.putExtra("TransNode", selectItem);
				Log.e("transNode", selectItem.toString());
				this.getActivity().setResult(RESULT_OK, mIntent);
				this.getActivity().finish();
			} else {
				Toast.makeText(getActivity(), "请至少选择一项！", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

}
