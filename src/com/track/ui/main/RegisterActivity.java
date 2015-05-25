package com.track.ui.main;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
 * 注册活动
 */
public class RegisterActivity extends Activity {
	private ProgressDialog prgDialog;
	private TextView errorMsg;
	private EditText nameET;
	private EditText emailET;
	private EditText pwdET;
	private ImageView mImageViewBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
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
		errorMsg = (TextView) findViewById(R.id.register_error);
		nameET = (EditText) findViewById(R.id.registerName);
		pwdET = (EditText) findViewById(R.id.registerPassword);
		prgDialog = new ProgressDialog(this);
		prgDialog.setMessage("Please wait...");
		prgDialog.setCancelable(false);
	}

	/**
	 * 注册用户
	 * 
	 * @param view
	 */
	public void registerUser(View view) {
		String name = nameET.getText().toString();
		String email = emailET.getText().toString();
		String password = pwdET.getText().toString();
		RequestParams params = new RequestParams();
		if (Utility.isNotNull(name) && Utility.isNotNull(email)
				&& Utility.isNotNull(password)) {
			if (Utility.validate(email)) {
				params.put("name", name);
				params.put("username", email);
				params.put("password", password);
				invokeWS(params);
			} else {
				Toast.makeText(getApplicationContext(),
						"Please enter valid email", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Please fill the form, don't leave any field blank",
					Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * 与webservice通信
	 * 
	 * @param params
	 */
	public void invokeWS(RequestParams params) {
		// 进度对话框
		prgDialog.show();
		// 用AsyncHttpClient对象创建restful客户端的对象
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://192.168.43.17:9999/useraccount/register/doregister",
				params, new AsyncHttpResponseHandler() {
					// // When the response returned by REST has Http response
					// code
					// // '200'
					// @Override
					// public void onSuccess(String response) {
					// // 隐藏进度对话框
					// prgDialog.hide();
					// try {
					// // 将webservice返回的字符串转化为JSON Object
					// JSONObject obj = new JSONObject(response);
					// if (obj.getBoolean("status")) {
					// // Set Default Values for Edit View controls
					// setDefaultValues();
					// // 显示注册成功的Toast
					// Toast.makeText(getApplicationContext(),
					// "You are successfully registered!",
					// Toast.LENGTH_LONG).show();
					// } else {
					// errorMsg.setText(obj.getString("error_msg"));
					// Toast.makeText(getApplicationContext(),
					// obj.getString("error_msg"),
					// Toast.LENGTH_LONG).show();
					// }
					// } catch (JSONException e) {
					// Toast.makeText(
					// getApplicationContext(),
					// "Error Occured [Server's JSON response might be invalid]!",
					// Toast.LENGTH_LONG).show();
					// e.printStackTrace();
					//
					// }
					// }
					//
					// // When the response returned by REST has Http response
					// code
					// // other than '200'
					// @Override
					// public void onFailure(int statusCode, Throwable error,
					// String content) {
					// prgDialog.hide();
					// // When Http response code is '404'
					// if (statusCode == 404) {
					// Toast.makeText(getApplicationContext(),
					// "Requested resource not found",
					// Toast.LENGTH_LONG).show();
					// }
					// // When Http response code is '500'
					// else if (statusCode == 500) {
					// Toast.makeText(getApplicationContext(),
					// "Something went wrong at server end",
					// Toast.LENGTH_LONG).show();
					// }
					// // When Http response code other than 404, 500
					// else {
					// Toast.makeText(
					// getApplicationContext(),
					// "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
					// Toast.LENGTH_LONG).show();
					// }
					// }

					@Override
					public void onFailure(int statusCode, Header[] arg1,
							byte[] arg2, Throwable arg3) {
						// TODO Auto-generated method stub
						prgDialog.hide();
						// When Http response code is '404'
						if (statusCode == 404) {
							Toast.makeText(getApplicationContext(),
									"Requested resource not found",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code is '500'
						else if (statusCode == 500) {
							Toast.makeText(getApplicationContext(),
									"Something went wrong at server end",
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

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						// 隐藏进度对话框
						prgDialog.hide();
						try {
							// 将webservice返回的字符串转化为JSON Object
							String str = new String(arg2);
							JSONObject obj = new JSONObject(str);
							if (obj.getBoolean("status")) {
								// Set Default Values for Edit View controls
								setDefaultValues();
								// 显示注册成功的Toast
								Toast.makeText(getApplicationContext(),
										"You are successfully registered!",
										Toast.LENGTH_LONG).show();
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
				});
	}

	/**
	 * 从注册活动到登陆活动界面的转换
	 */
	public void navigatetoLoginActivity(View view) {
		Intent loginIntent = new Intent(getApplicationContext(),
				LoginActivity.class);
		// Clears History of Activity
		loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(loginIntent);
	}

	/**
	 * 设置默认值
	 */
	public void setDefaultValues() {
		nameET.setText("");
		emailET.setText("");
		pwdET.setText("");
	}

}
