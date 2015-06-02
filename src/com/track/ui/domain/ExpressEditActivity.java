package com.track.ui.domain;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.loader.ExpressLoader;
import com.track.misc.model.Customer;
import com.track.misc.model.ExpressSheet;
import com.track.net.IDataAdapter;
import com.track.ui.misc.CustomerListActivity;
import com.zxing.activity.CaptureActivity;

/**
 * 点击快件任务列表的一项时，跳转到此Activity
 * 
 * @author Eamonn
 *
 */
@SuppressWarnings("deprecation")
public class ExpressEditActivity extends ActionBarActivity implements
		ActionBar.TabListener, IDataAdapter<ExpressSheet> {

	public static final int REQUEST_CAPTURE = 100;
	public static final int REQUEST_RCV = 101;
	public static final int REQUEST_SND = 102;
	public static TextView mIDView;

	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	private ExpressSheet mItem;

	private ExpressLoader mLoader;
	private Intent mIntent;
	private ExpressEditFragment1 baseFragment;
	private ExpressEditFragment2 externFragment;
	private boolean new_es = false; // 新建

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_express_edit);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		// 获取上个Activity或者fragment传来的Intent
		mIntent = getIntent();
		if (mIntent.hasExtra("Action")) {
			if (mIntent.getStringExtra("Action").equals("New")) {
				new_es = true;
				mItem = new ExpressSheet();
				mItem.setId(mIntent.getStringExtra("ExpId"));
				try {
					mLoader = new ExpressLoader(this, this);
					if (new_es) {
						new_es = false;
						mLoader.New(mItem.getId());
					} else {
						mLoader.Load(mItem.getId());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (mIntent.getStringExtra("Action").equals("NewD")) {
				try {
					mLoader = new ExpressLoader(this, this);
					mLoader.Dispatch(mIntent.getStringExtra("ExpId"));

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (mIntent.getStringExtra("Action").equals("Query")) {
				try {
					mLoader = new ExpressLoader(this, this);
					mLoader.Load(mIntent.getStringExtra("ExpId"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (mIntent.getStringExtra("Action").equals("Edit")) {
				ExpressSheet es;
				if (mIntent.hasExtra("ExpressSheet")) {
					// 创建一个运单？
					es = (ExpressSheet) mIntent
							.getSerializableExtra("ExpressSheet");
					Refresh(es.getId());
				} else {
					this.setResult(RESULT_CANCELED, mIntent);
					this.finish();
				}
			} else {
				this.setResult(RESULT_CANCELED, mIntent);
				this.finish();
			}
		} else {
			this.setResult(RESULT_CANCELED, mIntent);
			this.finish();
		}

	}

	/**
	 * 创建菜单选项
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.express_edit, menu);
		return true;
	}

	/**
	 * 点击不同菜单项出触发不同的事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_ok:
			Save();
			return true;
		case R.id.action_refresh:
			if (mItem != null) {
				Refresh(mItem.getId());
			}
			return true;
			// 触发哪一个？返回键。。
		case (android.R.id.home):
			mIntent.putExtra("ExpressSheet", mItem);
			mIntent.putExtra("result", mIDView.getEditableText().toString());
			this.setResult(RESULT_OK, mIntent);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public ExpressSheet getData() {
		return mItem;
	}

	@Override
	public void setData(ExpressSheet data) {
		mItem = data;
	}

	@Override
	public void notifyDataSetChanged() {
		if (baseFragment != null) {
			baseFragment.RefreshUI(mItem);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		Customer customer;

		switch (resultCode) {
		case RESULT_OK:
			switch (requestCode) {
			case REQUEST_CAPTURE:
				if (data.hasExtra("BarCode")) {
					String id = data.getStringExtra("BarCode");
					try {
						mLoader = new ExpressLoader(this, this);
						if (new_es) {
							new_es = false;
							mLoader.New(id);
						} else {
							mLoader.Load(id);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case REQUEST_RCV:
				if (data.hasExtra("Customer")) {
					customer = (Customer) data.getSerializableExtra("Customer");
					mItem.setReceiver(customer);
					baseFragment.displayRcv(mItem);
				}
				break;
			case REQUEST_SND:
				if (data.hasExtra("Customer")) {
					customer = (Customer) data.getSerializableExtra("Customer");
					mItem.setSender(customer);
					baseFragment.displaySnd(mItem);
				}
				break;
			}
			break;
		default:
			break;
		}
	}

	void Refresh(String id) {
		try {
			mLoader = new ExpressLoader(this, this);
			mLoader.Load(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void Save() {
		if (mItem != null && mItem.getReceiver() != null
				&& mItem.getSender() != null) {
			// mItem.setId(mIDView.getText().toString());
			mLoader = new ExpressLoader(this, this);
			mLoader.Save(mItem);
		} else {
			Toast.makeText(getApplicationContext(), "运单信息填写不完整，无法揽收！",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void StartCapture() {
		Intent intent = new Intent();
		intent.putExtra("Action", "Captrue");
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_CAPTURE);
	}

	private void GetCustomer(int intent_code) {
		Intent intent = new Intent();
		intent.setClass(this, CustomerListActivity.class);
		if (intent_code == REQUEST_RCV) {
			if (baseFragment.mRcvNameView.getTag() == null) {
				Log.e("RCVtag", "null");
				intent.putExtra("Action", "Add");
			} else {
				Log.e("RCVtag", "yes");
				intent.putExtra("Action", "New");
				intent.putExtra("Customer",
						(Customer) baseFragment.mRcvNameView.getTag());
			}
		} else if (intent_code == REQUEST_SND) {
			if (baseFragment.mSndNameView.getTag() == null) {
				intent.putExtra("Action", "Add");
			} else {
				intent.putExtra("Action", "New");
				intent.putExtra("Customer",
						(Customer) baseFragment.mSndNameView.getTag());
			}
		}
		startActivityForResult(intent, intent_code);
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				baseFragment = ExpressEditFragment1.newInstance();
				return baseFragment;
			case 1:
				externFragment = ExpressEditFragment2.newInstance();
				return externFragment;
			}
			return ExpressEditFragment1.newInstance();
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_ex_edit1).toUpperCase(l);
			case 1:
				return getString(R.string.title_ex_edit2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ExpressEditFragment1 extends Fragment {

		// private TextView mIDView;
		private TextView mRcvNameView;
		private TextView mRcvTelCodeView;
		private TextView mRcvDptView;
		private TextView mRcvAddrView;
		private TextView mRcvRegionView;

		private TextView mSndNameView;
		private TextView mSndTelCodeView;
		private TextView mSndDptView;
		private TextView mSndAddrView;
		private TextView mSndRegionView;
		private TextView mExpStatus;

		@SuppressWarnings("unused")
		private TextView mRcverView;
		private TextView mRcvTimeView;

		@SuppressWarnings("unused")
		private TextView mSnderView;
		private TextView mSndTimeView;

		private ImageView mbtnCapture;
		private ImageView mbtnRcv;
		private ImageView mbtnSnd;

		public static ExpressEditFragment1 newInstance() {
			ExpressEditFragment1 fragment = new ExpressEditFragment1();
			return fragment;
		}

		public ExpressEditFragment1() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_express_edit1,
					container, false);
			mIDView = (TextView) rootView.findViewById(R.id.expressId);
			mRcvNameView = (TextView) rootView
					.findViewById(R.id.expressRcvName);
			mRcvTelCodeView = (TextView) rootView
					.findViewById(R.id.expressRcvTel);
			mRcvAddrView = (TextView) rootView
					.findViewById(R.id.expressRcvAddr);
			mRcvDptView = (TextView) rootView.findViewById(R.id.expressRcvDpt);
			mRcvRegionView = (TextView) rootView
					.findViewById(R.id.expressRcvRegion);

			mSndNameView = (TextView) rootView
					.findViewById(R.id.expressSndName);
			mSndTelCodeView = (TextView) rootView
					.findViewById(R.id.expressSndTel);
			mSndAddrView = (TextView) rootView
					.findViewById(R.id.expressSndAddr);
			mSndDptView = (TextView) rootView.findViewById(R.id.expressSndDpt);
			mSndRegionView = (TextView) rootView
					.findViewById(R.id.expressSndRegion);
			mRcvTimeView = (TextView) rootView
					.findViewById(R.id.expressAccTime);
			mSndTimeView = (TextView) rootView
					.findViewById(R.id.expressDlvTime);
			mExpStatus = (TextView) rootView.findViewById(R.id.expressStatus);

			mbtnCapture = (ImageView) rootView
					.findViewById(R.id.action_ex_capture_icon);
			mbtnCapture.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					((ExpressEditActivity) getActivity()).StartCapture();
				}
			});
			mbtnRcv = (ImageView) rootView
					.findViewById(R.id.action_ex_rcv_icon);
			mbtnRcv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					((ExpressEditActivity) getActivity())
							.GetCustomer(REQUEST_RCV);
				}
			});
			mbtnSnd = (ImageView) rootView
					.findViewById(R.id.action_ex_snd_icon);
			mbtnSnd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					((ExpressEditActivity) getActivity())
							.GetCustomer(REQUEST_SND);
				}
			});
			return rootView;
		}

		void setExpId() {
			Bundle idBundle = new Bundle();
			idBundle.putString("ExpId", mIDView.getText().toString());
			setArguments(idBundle);
		}

		void RefreshUI(ExpressSheet es) {
			mIDView.setText(es.getId());
			displayRcv(es);
			displaySnd(es);
			if (es.getAcceptetime() != null)
				mRcvTimeView.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss",
						es.getAcceptetime()));
			else
				mRcvTimeView.setText(null);
			if (es.getDelivetime() != null)
				mSndTimeView.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss",
						es.getDelivetime()));
			else
				mSndTimeView.setText(null);
			displayBtn(es);
			switch (es.getStatus()) {
			case ExpressSheet.STATUS.STATUS_CREATED:
				mExpStatus.setText("新建");
				break;
			case ExpressSheet.STATUS.STATUS_RECEIVED:
				mExpStatus.setText("已揽收");
				break;
			case ExpressSheet.STATUS.STATUS_DELIVERIED:
				mExpStatus.setText("已交付");
				break;
			case ExpressSheet.STATUS.STATUS_DISPATCHED:
				mExpStatus.setText("派送中");
				break;
			case ExpressSheet.STATUS.STATUS_PARTATION:
				mExpStatus.setText("正在分拣");
				break;
			}
		}

		void displayBtn(ExpressSheet es) { // 按钮状态控制
			if (es.getStatus() == 0) {
				mbtnRcv.setVisibility(1);
				mbtnSnd.setVisibility(1);
			} else {
				mbtnRcv.setVisibility(0);
				mbtnSnd.setVisibility(0);
			}
		}

		void displayRcv(ExpressSheet es) {
			if (es.getReceiver() != null) {
				mRcvNameView.setText(es.getReceiver().getCname());
				mRcvTelCodeView.setText(es.getReceiver().getTelcode());
				mRcvNameView.setTag(es.getReceiver());
				mRcvAddrView.setText(es.getReceiver().getAddress());
				mRcvDptView.setText(es.getReceiver().getDepartment());
				mRcvRegionView.setText(es.getReceiver().getRegionString());
			} else {
				mRcvNameView.setText(null);
				mRcvTelCodeView.setText(null);
				mRcvNameView.setTag(null);
				mRcvAddrView.setText(null);
				mRcvDptView.setText(null);
				mRcvRegionView.setText(null);
			}
		}

		void displaySnd(ExpressSheet es) {
			if (es.getSender() != null) {
				mSndNameView.setText(es.getSender().getCname());
				mSndTelCodeView.setText(es.getSender().getTelcode());
				mSndNameView.setTag(es.getSender());
				mSndAddrView.setText(es.getSender().getAddress());
				mSndDptView.setText(es.getSender().getDepartment());
				mSndRegionView.setText(es.getSender().getRegionString());
			} else {
				mSndNameView.setText(null);
				mSndTelCodeView.setText(null);
				mSndNameView.setTag(null);
				mSndAddrView.setText(null);
				mSndDptView.setText(null);
				mSndRegionView.setText(null);
			}
		}
	}

	public static class ExpressEditFragment2 extends Fragment {

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static ExpressEditFragment2 newInstance() {
			ExpressEditFragment2 fragment = new ExpressEditFragment2();
			// Bundle args = new Bundle();
			// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			// fragment.setArguments(args);
			return fragment;
		}

		public ExpressEditFragment2() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_express_edit2,
					container, false);
			// TextView textView = (TextView) rootView
			// .findViewById(R.id.section_label);
			// textView.setText(Integer.toString(getArguments().getInt(
			// ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

}
