package com.track.ui.misc;

import java.util.ArrayList;
import java.util.regex.Pattern;

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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.loader.CustomerListLoader;
import com.track.misc.model.Customer;
import com.track.ui.adapter.CustomerListAdapter;

public class CustomerListActivity extends ActionBarActivity {

	PlaceholderFragment list_fg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fm = getSupportFragmentManager();

		if (fm.findFragmentById(android.R.id.content) == null) {
			list_fg = new PlaceholderFragment();
			fm.beginTransaction().add(android.R.id.content, list_fg).commit();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		list_fg.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 点击运单编辑界面的客户姓名，跳转到此fragment
	 */
	public static class PlaceholderFragment extends ListFragment {
		private CustomerListAdapter mAdapter;
		private CustomerListLoader mLoader;

		private Customer selectItem;
		private int selectPosition;

		Intent mIntent;

		public PlaceholderFragment() {
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			setEmptyText("查找客户!");

			setHasOptionsMenu(true);
			mAdapter = new CustomerListAdapter(new ArrayList<Customer>(),
					this.getActivity());
			setListAdapter(mAdapter);
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

			registerForContextMenu(getListView());
			// getListView().setLongClickable(true);

			// setListShown(false);

			if (mIntent.hasExtra("Action")) {
				if (mIntent.getStringExtra("Action").equals("New")) {
					selectItem = (Customer) mIntent
							.getSerializableExtra("Customer");
					RefreshList(selectItem.getCname());
				} else if (mIntent.getStringExtra("Action").equals("Query")
						|| mIntent.getStringExtra("Action").equals("Add")) {
					// mItem = new Customer();
					RefeshAllList();
				} else if (mIntent.getStringExtra("Action").equals("Edit")) {
					if (mIntent.hasExtra("Customer")) {
						selectItem = (Customer) mIntent
								.getSerializableExtra("Customer");
						RefreshList(selectItem.getCname());
					} else {
						this.getActivity().setResult(RESULT_CANCELED, mIntent);
						this.getActivity().finish();
					}
				} else {
					this.getActivity().setResult(RESULT_CANCELED, mIntent);
					this.getActivity().finish();
				}
			} else {
				this.getActivity().setResult(RESULT_CANCELED, mIntent);
				this.getActivity().finish();
			}
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			mIntent = activity.getIntent();
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

			// 客户列表菜单
			inflater.inflate(R.menu.customer_list, menu);

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

		// 点击客户列表的界面的菜单选项，分别触发不同的事件
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			switch (id) {
			case android.R.id.home:
				this.getActivity().finish();
				return true;
			case R.id.action_ok:
				// 将选中客户数据放进mIntent，结束自己的Activity
				SelectOk();
				return true;
			case R.id.action_edit:
				// 跳转到CustomerEditActvity
				EditItem();
				return true;
			case R.id.action_new:
				// 跳转到CustomerEditActivity
				NewItem();
				return true;
			case R.id.action_search:
				return true;
			}
			return super.onOptionsItemSelected(item);
		}

		// 长按客户列表触发上下文菜单
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			super.onCreateContextMenu(menu, v, menuInfo);
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

			// onListItemClick(null,null,info.position,0);

			selectItem = mAdapter.getItem(info.position);
			selectPosition = info.position;
			this.getActivity().setTitle(selectItem.getCname());
			menu.setHeaderTitle("客户: " + selectItem.getCname());
			menu.add(info.position, 1, 0, "选择");
			menu.add(info.position, 2, 1, "修改");
			menu.add(info.position, 3, 2, "删除");
		}

		@Override
		public boolean onContextItemSelected(MenuItem item) {
			// AdapterContextMenuInfo info = (AdapterContextMenuInfo)
			// item.getMenuInfo();
			if (item.getTitle().equals("选择")) {
				SelectOk(); // 返回给上层
			} else if (item.getTitle().equals("修改")) {
				EditItem(); // 编辑客户
			} else if (item.getTitle().equals("删除")) {
				DeleteItem(); // 删除客户
			}
			return super.onContextItemSelected(item);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			selectItem = mAdapter.getItem(position);
			selectPosition = position;
			this.getActivity().setTitle(selectItem.getCname());
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) { // 编辑界面的回调
			switch (resultCode) {
			case RESULT_OK:
				Customer customer;
				if (data.hasExtra("Customer")) {
					customer = (Customer) data.getSerializableExtra("Customer");
					if (requestCode == CustomerEditActivity.INTENT_NEW) {
						mAdapter.getData().add(customer);
					} else if (requestCode == CustomerEditActivity.INTENT_EDIT) {
						mAdapter.setItem(customer, selectPosition);
					}
					mAdapter.notifyDataSetChanged();
				}
				// if (data.hasExtra("Customer")) {
				// customer = (Customer)
				// data.getSerializableExtra("Customer");
				// RefreshList(customer.getName());
				// }
				break;
			default:
				break;
			}
		}

		private void SelectOk() {
			if (selectItem != null) {
				mIntent.putExtra("Customer", selectItem);
				this.getActivity().setResult(RESULT_OK, mIntent);
				this.getActivity().finish();
			} else {
				Toast.makeText(getActivity(), "请至少选择一项！", Toast.LENGTH_SHORT)
						.show();
			}
		}

		private void EditItem() {
			if (selectItem != null) {
				Intent intent = new Intent();
				intent.setClass(this.getActivity(), CustomerEditActivity.class);
				intent.putExtra("Action", "Edit");
				intent.putExtra("Customer", selectItem);
				this.getActivity().startActivityForResult(intent,
						CustomerEditActivity.INTENT_EDIT);
			} else {
				Toast.makeText(getActivity(), "请至少选择一项！", Toast.LENGTH_SHORT)
						.show();
			}
		}

		private void NewItem() {
			Intent intent = new Intent();
			intent.setClass(this.getActivity(), CustomerEditActivity.class);
			intent.putExtra("Action", "New");
			this.getActivity().startActivityForResult(intent,
					CustomerEditActivity.INTENT_NEW); // 激发下层编辑-建立新的
		}

		private void DeleteItem() {
			try {
				mLoader = new CustomerListLoader(mAdapter, this.getActivity());
				mLoader.DeleteCustomer(selectItem.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void RefreshList(String name) {
			this.getActivity().setTitle("");
			if (checkMobile(name)) { // 符合电话号码的规律,按电话号码查询
				try {
					mLoader = new CustomerListLoader(mAdapter,
							this.getActivity());
					mLoader.LoadCustomerListByTelCode(name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					mLoader = new CustomerListLoader(mAdapter,
							this.getActivity());
					mLoader.LoadCustomerListByName(name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void RefeshAllList() {
			// TODO Auto-generated method stub
			try {
				mLoader = new CustomerListLoader(mAdapter, this.getActivity());
				mLoader.LoadCustomerList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean checkMobile(String str) {
			String regex_mb = "(\\+\\d+)?1[34578]\\d{9}$"; // 移动电话的正则表达式
			String regex_ph = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$"; // 固定电话的正则表达式
			Pattern pattern_mb = Pattern.compile(regex_mb);
			Pattern pattern_ph = Pattern.compile(regex_ph);
			return pattern_mb.matcher(str).matches()
					|| pattern_ph.matcher(str).matches();
		}
	}

}
