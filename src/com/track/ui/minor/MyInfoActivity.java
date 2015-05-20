package com.track.ui.minor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.track.app.user.R;
import com.track.ui.main.MainActivity;

public class MyInfoActivity extends Activity {

	private ImageView mBackIV;
	private TextView mLogoutTV;
	private SharedPreferences mSp;
	private TextView mUnameTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		mBackIV = (ImageView) findViewById(R.id.id_iv_back);
		mLogoutTV = (TextView) findViewById(R.id.id_tv_logout);
		mUnameTV = (TextView) findViewById(R.id.id_tv_myinfo_uname);
		setUname();
		setClickListener();
	}

	private void setUname() {
		// TODO Auto-generated method stub
		mSp = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
		mUnameTV.setText(mSp.getString("uname", "    "));
	}

	private void setClickListener() {
		mBackIV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toMainActivity();
			}
		});
		mLogoutTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doLogout();
			}
		});
	}

	protected void doLogout() {
		mSp = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
		mSp.edit().clear().commit();
		toMainActivity();
	}

	protected void toMainActivity() {
		Intent mainIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(mainIntent);
	}

}
