package com.track.ui.main;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.track.app.user.R;
import com.track.util.Utility;

/**
 * 
 * 登陆活动
 * 
 */
public class LoginActivity extends FragmentActivity {
	// 进度对话框模块
	private ProgressDialog prgDialog;
	private TextView errorMsg;
	private EditText unameET;
	private EditText pwdET;
	private ImageView mImageViewBack;
	private static JSONObject obj;
	private FragmentManager manager;
	private FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mImageViewBack = (ImageView) findViewById(R.id.id_iv_back);
		mImageViewBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loginIntent = new Intent(getApplicationContext(),
						MainActivity.class);
				loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(loginIntent);
			}
		});
		errorMsg = (TextView) findViewById(R.id.login_error);
		unameET = (EditText) findViewById(R.id.loginUsername);
		pwdET = (EditText) findViewById(R.id.loginPassword);
		// 初始化 Progress Dialog object
		prgDialog = new ProgressDialog(this);
		// 设置 Progress Dialog 文本
		prgDialog.setMessage("Please wait...");
		prgDialog.setCancelable(false);
	}

	/**
	 * 登陆按钮被触发
	 * 
	 * @param view
	 */
	public void loginUser(View view) {
		String uname = unameET.getText().toString();
		String pwd = pwdET.getText().toString();
		// 初始化 Http Request Param Object
		RequestParams params = new RequestParams();
		if (Utility.isNotNull(uname) && Utility.isNotNull(pwd)) {
			// 将username放入params中
			params.put("uname", uname);
			// 将pwd放入params中
			params.put("pwd", pwd);
			invokeWS(params);
		}
		// 当输入框留白
		else {
			Toast.makeText(getApplicationContext(), "您还没有输入用户名或密码！",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 与webservice通信的方法
	 * 
	 * @param params
	 */
	public void invokeWS(RequestParams params) {
		// 显示 Progress Dialog
		prgDialog.show();
		// 创建 AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();

		client.get(
				"http://192.168.191.11:8080/TestCxfHibernate/rest/Misc/doLogin",
				params, new AsyncHttpResponseHandler() {
					// When the response returned by REST has Http response code
					// '200'

					@Override
					public void onSuccess(String response) {
						// 隐藏 Progress Dialog
						prgDialog.hide();
						try {
							// 将服务端返回的字符串转换成JSON Object
							obj = new JSONObject(response);
							if (obj.getBoolean("status")) {
								Toast.makeText(getApplicationContext(),
										"登录成功!", Toast.LENGTH_LONG).show();
								// 进入HomeActivity
								navigatetoMainActivity();
							} else {
								errorMsg.setText(obj.getString("error_msg"));
								Toast.makeText(getApplicationContext(),
										obj.getString("error_msg"),
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							Toast.makeText(
									getApplicationContext(),
									"Error Occured [Server's JSON response might be invalid]!",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();

						}
					}

					// When the response returned by REST has Http response code
					// other than '200'
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						prgDialog.hide();
						// When Http response code is '404'
						if (statusCode == 404) {
							Toast.makeText(getApplicationContext(), "资源未找到",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code is '500'
						else if (statusCode == 500) {
							Toast.makeText(getApplicationContext(), "服务器发生异常",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code other than 404, 500
						else {
							Toast.makeText(
									getApplicationContext(),
									"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
									Toast.LENGTH_LONG).show();
						}
					}
				});

	}

	/**
	 * 从LoginActivity跳转MainActivity
	 * 
	 * @throws JSONException
	 */
	public void navigatetoMainActivity() throws JSONException {
		Intent mainIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		mainIntent.putExtra("uname", obj.getString("username"));
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		NavigationDrawerFragment mNavigationDrawerFragment = new NavigationDrawerFragment();
		Bundle bundle = new Bundle();
		bundle.putString("uname", obj.getString("username"));
		mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(mainIntent);
	}

	/**
	 * 注册按钮被触发
	 * 
	 * @param view
	 */
	public void navigatetoRegisterActivity(View view) {
		Intent loginIntent = new Intent(getApplicationContext(),
				RegisterActivity.class);
		loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(loginIntent);
	}

}
