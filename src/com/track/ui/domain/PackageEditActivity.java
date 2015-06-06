package com.track.ui.domain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.loader.PackageLoader;
import com.track.misc.model.Package;
import com.track.misc.model.TransNode;
import com.track.net.IDataAdapter;
import com.track.ui.domain.ExpressListFragment.OnFragmentInteractionListener;
import com.track.ui.misc.TransNodeListActivity;
import com.zxing.activity.CaptureActivity;

/**
 * 点击快件任务列表的一项时，跳转到此Activity
 * 
 * @author Eamonn
 *
 */
public class PackageEditActivity extends ActionBarActivity implements
		IDataAdapter<Package>, OnFragmentInteractionListener {

	public static final int REQUEST_CAPTURE = 100;
	public static final int REQUEST_TND = 101;
	public static final int REQUEST_SND = 102;
	public static TextView mIDView;

	private Package mItem;
	private static TransNode sourcetransNode;
	private static TransNode targettransNode;
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
		baseFragment = PackageEditFragment1.newInstance();
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.container, baseFragment).commit();

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
				Package pkg;
				if (mIntent.hasExtra("Package")) {
					// 创建一个运单？
					pkg = (Package) mIntent.getSerializableExtra("Package");
					Refresh(pkg.getId());
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
	public Package getData() {
		return mItem;
	}

	@Override
	public void setData(Package data) {
		Log.e("pkgdate", data.toString());
		mItem = data;
	}

	@Override
	public void notifyDataSetChanged() {
		if (baseFragment != null) {
			Log.e("refresh", "refeshUI");
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
			case REQUEST_TND:
				if (data.hasExtra("TransNode")) {
					targettransNode = (TransNode) data
							.getSerializableExtra("TransNode");
					mItem.setTargetnode(targettransNode.getId());
					mItem.setTargetTransNode(targettransNode);
					baseFragment.displayTnd(mItem);
				}
				break;
			case REQUEST_SND:
				if (data.hasExtra("TransNode")) {
					sourcetransNode = (TransNode) data
							.getSerializableExtra("TransNode");
					mItem.setSourcenode(sourcetransNode.getId());
					mItem.setSourceTransNode(sourcetransNode);
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
			Toast.makeText(getApplicationContext(), "包裹为null",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void StartCapture() {
		Intent intent = new Intent();
		intent.putExtra("Action", "Captrue");
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_CAPTURE);
	}

	private void getTransNodes(int intentcode) {
		Intent intent = new Intent();
		intent.setClass(this, TransNodeListActivity.class);
		if (intentcode == REQUEST_SND) {
			if (baseFragment.mSourceView.getTag() != null) {
				Log.e("tag", "not null");
				intent.putExtra("Action", "New");
				intent.putExtra("NodeId", baseFragment.mSourceView.getText()
						.toString());
			} else {
				Log.e("tag", "null");
			}
		}
		if (intentcode == REQUEST_TND) {
			if (baseFragment.mTargetView.getTag() != null) {
				intent.putExtra("Action", "New");
				intent.putExtra("NodeId", baseFragment.mTargetView.getText()
						.toString());
			} else {

			}
		}
		startActivityForResult(intent, intentcode);
	}

	private void getExpressList(String type) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.container, ExpressListFragment.newInstance(mIDView
				.getText().toString(), type));
		ft.addToBackStack(null);
		ft.commit();
	}

	private void QueryExpressInPkg(String pid) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.container, ExpressListFragment.newInstance(mIDView
				.getText().toString(), "NPkg"));
		ft.addToBackStack(null);
		ft.commit();
	}

	private void PackOk(String pid) {

	}

	public static class PackageEditFragment1 extends Fragment {

		private ImageView mBtnScan;
		private TextView mSourceView;
		private ImageView mBtnSource;
		private TextView mTargetView;
		private TextView mTarget1View;
		private TextView mTarget2View;

		private TextView mSource1View;
		private TextView mSource2View;
		private ImageView mBtnTarget;
		private TextView mCreateTime;
		private TextView mStatus;
		private Button mAddExpresssBt;
		private Button mQueryExpressBt;
		private Button mPackOk;

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
			mTarget1View = (TextView) rootView
					.findViewById(R.id.packageTarget1);
			mTarget2View = (TextView) rootView
					.findViewById(R.id.packageTarget2);
			mSource1View = (TextView) rootView.findViewById(R.id.packageSouce1);
			mSource2View = (TextView) rootView.findViewById(R.id.packageSouce2);

			mStatus = (TextView) rootView.findViewById(R.id.packageStatus);
			mCreateTime = (TextView) rootView
					.findViewById(R.id.packageCreateTime);
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
					((PackageEditActivity) getActivity())
							.getTransNodes(REQUEST_SND);
				}

			});
			mBtnTarget = (ImageView) rootView
					.findViewById(R.id.action_ptarget_icon);
			mBtnTarget.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					((PackageEditActivity) getActivity())
							.getTransNodes(REQUEST_TND);
				}
			});
			mAddExpresssBt = (Button) rootView.findViewById(R.id.button_add_es);
			mAddExpresssBt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((PackageEditActivity) getActivity())
							.getExpressList("ExTAN");
				}
			});
			mQueryExpressBt = (Button) rootView
					.findViewById(R.id.button_queryEsInPkg);
			mQueryExpressBt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((PackageEditActivity) getActivity())
							.QueryExpressInPkg(mIDView.getText().toString()
									.trim());
				}
			});
			mPackOk = (Button) rootView.findViewById(R.id.button_packOk);
			mPackOk.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((PackageEditActivity) getActivity()).PackOk(mIDView
							.getText().toString().trim());
				}
			});
			return rootView;

		}

		void RefreshUI(Package mItem) {
			mIDView.setText(mItem.getId());
			displayTnd(mItem);
			displaySnd(mItem);
			if (mItem.getCreatetime() != null)
				mCreateTime.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss",
						mItem.getCreatetime()));
			else
				mCreateTime.setText(null);
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

		private void displayTnd(Package pk) {

			if (pk != null && pk.getTargetTransNode() != null) {
				mTargetView.setText(pk.getTargetTransNode().getNodename());
				mTarget1View.setText(pk.getTargetTransNode().getRegioncode());
				mTarget2View.setText(pk.getTargetTransNode().getTelcode());
			} else {
				mTargetView.setText(null);
				mTarget1View.setText(null);
				mTarget2View.setText(null);
			}
		}

		private void displaySnd(Package pk) {

			if (pk != null && pk.getSourceTransNode() != null) {
				mSourceView.setText(pk.getSourceTransNode().getNodename());
				mSource1View.setText(pk.getSourceTransNode().getRegioncode());
				mSource2View.setText(pk.getSourceTransNode().getTelcode());
			} else {
				mSourceView.setText(null);
				mSource1View.setText(null);
				mSource2View.setText(null);
			}
		}

	}

	@Override
	public void onFragmentInteraction(String id) {
		// TODO Auto-generated method stub

	}

}
