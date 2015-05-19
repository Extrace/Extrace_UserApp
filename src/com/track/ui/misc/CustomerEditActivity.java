package com.track.ui.misc;

import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.loader.CustomerEditLoader;
import com.track.misc.model.Customer;
import com.track.net.IDataAdapter;

public class CustomerEditActivity extends ActionBarActivity implements
		IDataAdapter<Customer> {

	public static final int INTENT_NEW = 1;
	public static final int INTENT_EDIT = 2;

	private Customer mItem;
	private CustomerEditLoader mLoader;

	private Intent mIntent;

	private EditText mNameView;
	private EditText mTelCodeView;
	private TextView mRegionView;
	private EditText mAddrView;
	private EditText mDptView;
	private EditText mPostCodeView;

	String mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_edit);

		mNameView = (EditText) findViewById(R.id.edtName);
		mTelCodeView = (EditText) findViewById(R.id.edtTelCode);
		mAddrView = (EditText) findViewById(R.id.edtAddr);
		mDptView = (EditText) findViewById(R.id.edtDpt);
		mPostCodeView = (EditText) findViewById(R.id.edtPostCode);
		mRegionView = (TextView) findViewById(R.id.txtRegion);

		findViewById(R.id.btnGetRegion).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						GetRegion();
					}
				});
		mIntent = getIntent();

		if (mIntent.hasExtra("Action")) {
			if (mIntent.getStringExtra("Action").equals("New")) {
				mItem = new Customer();
			} else if (mIntent.getStringExtra("Action").equals("Edit")) {
				Customer customer;
				if (mIntent.hasExtra("Customer")) {
					customer = (Customer) mIntent
							.getSerializableExtra("Customer");
					Refresh(customer.getId());
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

	@Override
	protected void onStart() {
		super.onStart();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.customer_edit, menu);
		return true;
	}

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
		case (android.R.id.home):
			/* 将选中的对象赋值给Intent */
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("Customer",mItem);
			// mIntent.putExtras(bundle);

			mIntent.putExtra("Customer", mItem);
			this.setResult(RESULT_OK, mIntent);
			this.finish();
			// Intent intent = new Intent(this, CustomerListActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Customer getData() {
		return mItem;
	}

	@Override
	public void setData(Customer data) {
		mItem = new Customer();
		mItem = data;
	}

	@Override
	public void notifyDataSetChanged() {
		mNameView.setText(mItem.getCname());
		mTelCodeView.setText(mItem.getTelcode());
		mAddrView.setText(mItem.getAddress());
		mDptView.setText(mItem.getDepartment());
		mRegionView.setTag(mItem.getRegioncode());
		mRegionView.setText(mItem.getRegionString()); // ================
		mPostCodeView.setText(String.valueOf(mItem.getPoscode()));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			String regionId,
			regionString;
			if (data.hasExtra("RegionId")) {
				regionId = data.getStringExtra("RegionId");
				regionString = data.getStringExtra("RegionString");
			} else {
				regionId = "";
				regionString = "";
			}
			mRegionView.setTag(regionId);
			mRegionView.setText(regionString);
			break;
		default:
			break;
		}
	}

	private void GetRegion() {
		Intent intent = new Intent();
		intent.setClass(this, RegionListActivity.class);
		try {
			String rCode = mRegionView.getTag().toString();
			String rString = mRegionView.getText().toString();
			intent.putExtra("RegionId", rCode);
			intent.putExtra("RegionString", rString);
		} catch (Exception e) {

		}
		startActivityForResult(intent, 0);
	}

	private void Refresh(int id) {
		try {
			mLoader = new CustomerEditLoader(this, this);
			mLoader.LoadCustomer(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Save() {
		mLoader = new CustomerEditLoader(this, this);
		// mItem = new Customer();
		// try {
		// mItem.setID(mTelCodeView.getText().toString());
		// } catch (Exception e) {
		// Toast.makeText(this, "客户电话号码不能为空!", Toast.LENGTH_SHORT).show();
		// }
		try {
			mItem.setCname(mNameView.getText().toString());
		} catch (Exception e) {
			Toast.makeText(this, "客户姓名不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (mItem.getCname().length() < 2) {
			Toast.makeText(this, "客户姓名不能太短!", Toast.LENGTH_SHORT).show();
			return;
		}

		try {
			mItem.setTelcode(mTelCodeView.getText().toString());
		} catch (Exception e) {
			Toast.makeText(this, "客户电话号码不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}

		if (mItem.getTelcode().length() < 6) {
			Toast.makeText(this, "客户电话号码不能太短!", Toast.LENGTH_SHORT).show();
			return;
		}

		String regex_mb = "(\\+\\d+)?1[34578]\\d{9}$"; // 移动电话的正则表达式
		String regex_ph = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$"; // 固定电话的正则表达式
		Pattern pattern_mb = Pattern.compile(regex_mb);
		Pattern pattern_ph = Pattern.compile(regex_ph);
		if (!(pattern_mb.matcher(mItem.getTelcode()).matches() || pattern_ph
				.matcher(mItem.getTelcode()).matches())) {
			Toast.makeText(this, "客户电话号码格式错误!", Toast.LENGTH_SHORT).show();
			return;
		}

		try {
			mItem.setAddress(mAddrView.getText().toString());
		} catch (Exception e) {
			Toast.makeText(this, "客户地址不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}
		try {
			mItem.setDepartment(mDptView.getText().toString());
		} catch (Exception e) {
			mItem.setDepartment("");
		}
		try {
			mItem.setPoscode(Integer.parseInt(mPostCodeView.getText()
					.toString()));
		} catch (Exception e) {
			mItem.setPoscode(0);
		}
		try {
			mItem.setRegioncode(mRegionView.getTag().toString());
			mItem.setRegionString(mRegionView.getText().toString());
		} catch (Exception e) {
			Toast.makeText(this, "客户地址行政区不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}
		mLoader.SaveCustomer(mItem);
	}
}
