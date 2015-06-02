package com.track.ui.domain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.loader.PackageLoader;
import com.track.misc.model.Package;
import com.track.net.IDataAdapter;
import com.zxing.activity.CaptureActivity;

/**
 * 点击快件任务列表的一项时，跳转到此Activity
 * 
 * @author Eamonn
 *
 */
@SuppressWarnings("deprecation")
public class PackageEditActivity extends ActionBarActivity implements
		IDataAdapter<Package> {

	public static final int REQUEST_CAPTURE = 100;
	public static final int REQUEST_RCV = 101;
	public static final int REQUEST_SND = 102;
	public static TextView mIDView;

	private Package mItem;

	private PackageLoader mLoader;
	private Intent mIntent;
	private PackageEditFragment1 baseFragment;
	private boolean new_pkg = false; // 新建

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_edit);

		// final ActionBar actionBar = getSupportActionBar();
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction()
				.replace(R.id.container, PackageEditFragment1.newInstance())
				.commit();

		// 获取上个Activity或者fragment传来的Intent
		mIntent = getIntent();
		if (mIntent.hasExtra("Action")) {
			if (mIntent.getStringExtra("Action").equals("New")) {
				new_pkg = true;
				mItem = new Package();
				mItem.setId(mIntent.getStringExtra("PkgId"));
				try {
					mLoader = new PackageLoader(this, this);
					if (new_pkg) {
						new_pkg = false;
						mLoader.New(mItem.getId());
					} else {
						mLoader.Load(mItem.getId());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (mIntent.getStringExtra("Action").equals("NewD")) {
				try {
					mLoader = new PackageLoader(this, this);
					// mLoader.Dispatch(mIntent.getStringExtra("ExpId"));

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (mIntent.getStringExtra("Action").equals("Query")) {
				try {
					mLoader = new PackageLoader(this, this);
					mLoader.Load(mIntent.getStringExtra("ExpId"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (mIntent.getStringExtra("Action").equals("Edit")) {
				Package es;
				if (mIntent.hasExtra("Package")) {
					// 创建一个运单？
					es = (Package) mIntent.getSerializableExtra("Package");
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
			mIntent.putExtra("Package", mItem);
			mIntent.putExtra("result", mIDView.getEditableText().toString());
			this.setResult(RESULT_OK, mIntent);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void notifyDataSetChanged() {
		if (baseFragment != null) {
			baseFragment.RefreshUI(mItem);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case RESULT_OK:
			switch (requestCode) {
			case REQUEST_CAPTURE:
				if (data.hasExtra("BarCode")) {
					String id = data.getStringExtra("BarCode");
					try {
						mLoader = new PackageLoader(this, this);
						if (new_pkg) {
							new_pkg = false;
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
					// customer = (Customer)
					// data.getSerializableExtra("Customer");
					// mItem.setReceiver(customer);
					// baseFragment.displayRcv(mItem);
				}
				break;
			case REQUEST_SND:
				if (data.hasExtra("Customer")) {
					// customer = (Customer)
					// data.getSerializableExtra("Customer");
					// mItem.setSender(customer);
					// baseFragment.displaySnd(mItem);
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
			mLoader = new PackageLoader(this, this);
			mLoader.Load(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void Save() {
		if (mItem != null) {
			// mItem.setId(mIDView.getText().toString());
			mLoader = new PackageLoader(this, this);
			mLoader.Save(mItem);
		} else {
			Toast.makeText(getApplicationContext(), "运单为null",
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
		// Intent intent = new Intent();
		// intent.setClass(this, CustomerListActivity.class);
		// if (intent_code == REQUEST_RCV) {
		// if (baseFragment.mSourceView.getTag() == null) {
		// Log.e("RCVtag", "null");
		// intent.putExtra("Action", "Add");
		// } else {
		// Log.e("RCVtag", "yes");
		// intent.putExtra("Action", "New");
		// intent.putExtra("Customer",
		// (Customer) baseFragment.mSourceView.getTag());
		// }
		// } else if (intent_code == REQUEST_SND) {
		// if (baseFragment.mSndNameView.getTag() == null) {
		// intent.putExtra("Action", "Add");
		// } else {
		// intent.putExtra("Action", "New");
		// intent.putExtra("Customer",
		// (Customer) baseFragment.mSndNameView.getTag());
		// }
		// }
		// startActivityForResult(intent, intent_code);
	}

	public static class PackageEditFragment1 extends Fragment {

		private ImageView mBtnScan;
		private TextView mSourceView;
		private ImageView mBtnSource;
		private TextView mTargetView;
		private ImageView mBtnTarget;
		private TextView mCreateTime;
		private TextView mStatus;

		public static PackageEditFragment1 newInstance() {
			PackageEditFragment1 fragment = new PackageEditFragment1();
			return fragment;
		}

		public PackageEditFragment1() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_package_edit,
					container, false);
			mIDView = (TextView) rootView.findViewById(R.id.packageId);
			mSourceView = (TextView) rootView.findViewById(R.id.pkgsourcenode);
			mTargetView = (TextView) rootView.findViewById(R.id.pkgTargetnode);
			mStatus = (TextView) rootView.findViewById(R.id.packageStatus);

			mBtnScan = (ImageView) rootView
					.findViewById(R.id.action_pk_capture_icon);
			mBtnScan.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					((PackageEditActivity) getActivity()).StartCapture();
				}
			});
			mBtnSource = (ImageView) rootView
					.findViewById(R.id.action_psource_icon);
			mBtnSource.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
				}
			});
			mBtnTarget = (ImageView) rootView
					.findViewById(R.id.action_ptarget_icon);
			mBtnTarget.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
				}
			});
			return rootView;
		}

		void RefreshUI(Package mItem) {
			mIDView.setText(mItem.getId());
			// displayRcv(mItem);
			// displaySnd(mItem);
			if (mItem.getCreatetime() != null)
				mCreateTime.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss",
						mItem.getCreatetime()));
			else
				mCreateTime.setText(null);
			// displayBtn(mItem);
			switch (mItem.getStatus()) {
			case Package.STATUS.STATUS_CREATED:
				mStatus.setText("新建");
				break;
			case Package.STATUS.STATUS_DELIVED:
				mStatus.setText("揽收货篮");
				break;
			case Package.STATUS.STATUS_DISPACHED:
				mStatus.setText("派送货篮");
				break;
			case Package.STATUS.STATUS_PACKED:
				mStatus.setText("打包");
				break;
			case Package.STATUS.STATUS_PARTATION:
				mStatus.setText("分拣");
				break;
			}
		}

		void displayBtn(Package mItem) { // 按钮状态控制
			if (mItem.getStatus() == 0) {
				mBtnSource.setVisibility(1);
				mBtnTarget.setVisibility(1);
			} else {
				mBtnSource.setVisibility(0);
				mBtnTarget.setVisibility(0);
			}
		}

		void displayRcv(Package mItem) {
			// if (mItem.getReceiver() != null) {
			// mSourceView.setText(mItem.getReceiver().getCname());
			// mRcvTelCodeView.setText(mItem.getReceiver().getTelcode());
			// mSourceView.setTag(mItem.getReceiver());
			// mRcvAddrView.setText(mItem.getReceiver().getAddress());
			// mRcvDptView.setText(mItem.getReceiver().getDepartment());
			// mRcvRegionView.setText(mItem.getReceiver().getRegionString());
			// } else {
			// mSourceView.setText(null);
			// mRcvTelCodeView.setText(null);
			// mSourceView.setTag(null);
			// mRcvAddrView.setText(null);
			// mRcvDptView.setText(null);
			// mRcvRegionView.setText(null);
			// }
		}

		void displaySnd(Package es) {
			// if (es.getSender() != null) {
			// mSndNameView.setText(es.getSender().getCname());
			// mSndTelCodeView.setText(es.getSender().getTelcode());
			// mSndNameView.setTag(es.getSender());
			// mSndAddrView.setText(es.getSender().getAddress());
			// mSndDptView.setText(es.getSender().getDepartment());
			// mSndRegionView.setText(es.getSender().getRegionString());
			// } else {
			// mSndNameView.setText(null);
			// mSndTelCodeView.setText(null);
			// mSndNameView.setTag(null);
			// mSndAddrView.setText(null);
			// mSndDptView.setText(null);
			// mSndRegionView.setText(null);
			// }
			// }
		}

	}

	@Override
	public Package getData() {
		return mItem;
	}

	@Override
	public void setData(Package data) {
		mItem = data;
	}
}
