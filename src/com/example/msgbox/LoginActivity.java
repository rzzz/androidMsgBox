package com.example.msgbox;

import com.example.core.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private static final String TAG = "LoginActivity";
	private static boolean isWaitNet = false;
	Button denglu;
	Button quexiao;

	EditText name;
	EditText psw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//MyApplication app = (MyApplication) getApplication();
		//app.addActivity(this);

		setContentView(R.layout.login);

		denglu = (Button) findViewById(R.id.loginbt);
		quexiao = (Button) findViewById(R.id.cancelbt);

		name = (EditText) findViewById(R.id.editText1);
		psw = (EditText) findViewById(R.id.editText2);

		denglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				Log.i(TAG, "click youke");

				if (LoginActivity.isWaitNet)
					return;

				final String namestr = name.getText().toString().trim();
				final String pswstr = psw.getText().toString().trim();

				LoginActivity.isWaitNet = true;

				DataToolsHelper.verifyPsw(namestr, pswstr,
						new DataToolsHelper.OnDataLoadedListener() {

							@Override
							public void onSuccess(String paramString) {
								SharedPreferences sharedPreferences = getSharedPreferences(
										"test1", Activity.MODE_PRIVATE);
								SharedPreferences.Editor editor = sharedPreferences
										.edit();
								editor.putBoolean("bhas", true);
								editor.putString("sn", namestr);
								editor.putString("sp", pswstr);
								editor.commit();

								MyApplication app = (MyApplication) getApplication();
								app.setUserName(namestr);

								LoginActivity.isWaitNet = false;

								runOnUiThread(new Runnable() {
									public void run() {
										finish();
										Intent intent = new Intent();
										intent.setClass(LoginActivity.this,
												MainMenuActivity.class);
										LoginActivity.this
												.startActivity(intent);
									}
								});
							}

							@Override
							public void onFailure(String paramString) {
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(LoginActivity.this,
												"密码或用户名错误！", Toast.LENGTH_SHORT)
												.show();
									}
								});

								LoginActivity.isWaitNet = false;
							}
						});
			}

		});

		quexiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				Log.i(TAG, "click quexiao");

				finish();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

}
