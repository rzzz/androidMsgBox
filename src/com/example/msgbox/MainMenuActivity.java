package com.example.msgbox;

import com.example.msgbox.func.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainMenuActivity";

	private long mExitTime = 0;

	TextView username;
	ImageView exitbt;
	ImageView loginbt;

	ImageView huiyuan;
	ImageView xiaoxi;
	ImageView huodong;
	ImageView jianjie;
	ImageView fankui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication app = (MyApplication) getApplication();
		app.addActivity(this);

		setContentView(R.layout.mainmenu);

		exitbt = (ImageView) findViewById(R.id.exitbt);
		username = (TextView) findViewById(R.id.nametv);
		
		loginbt = (ImageView) findViewById(R.id.loginbt);

		huiyuan = (ImageView) findViewById(R.id.huiyuan);
		huiyuan.setOnClickListener(this);
		xiaoxi = (ImageView) findViewById(R.id.xiaoxi);
		xiaoxi.setOnClickListener(this);
		huodong = (ImageView) findViewById(R.id.huodong);
		huodong.setOnClickListener(this);
		jianjie = (ImageView) findViewById(R.id.jianjie);
		jianjie.setOnClickListener(this);
		fankui = (ImageView) findViewById(R.id.fankui);
		fankui.setOnClickListener(this);

		exitbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				Log.i(TAG, "clicl exit");
				((MyApplication) getApplication()).exit();
			}

		});
		loginbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				Log.i(TAG, "clicl login");
				
                Intent intent = new Intent();
                intent.setClass(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
			}

		});
		
		updateBts();
	}
	
	public void onRestart(){
		super.onRestart();
		updateBts();
	}
	
	private void updateBts(){
		MyApplication app = (MyApplication) getApplication();
		if (app.isComeWithPsw()) {
			username.setText(app.getUserName());
			loginbt.setVisibility(View.INVISIBLE);
		}else{
			loginbt.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	@Override
	public void onClick(View paramView) {
		// TODO Auto-generated method stub

		int keyid = paramView.getId();
		switch (keyid) {
		case R.id.huiyuan: {
			if (((MyApplication) getApplication()).isComeWithPsw()) {
				Intent intent = new Intent();
				intent.setClass(MainMenuActivity.this, Huiyuan.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "请登陆！", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		case R.id.xiaoxi: {
			if (((MyApplication) getApplication()).isComeWithPsw()) {
				Intent intent = new Intent();
				intent.setClass(MainMenuActivity.this, Xiaoxi.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "请登陆！", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		case R.id.huodong: {
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this, Huodong.class);
			startActivity(intent);
		}
			break;
		case R.id.jianjie: {
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this, Jianjie.class);
			startActivity(intent);
		}
			break;
		case R.id.fankui: {
			if (((MyApplication) getApplication()).isComeWithPsw()) {
				Intent intent = new Intent();
				intent.setClass(MainMenuActivity.this, Fankui.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "请登陆！", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();// 更新mExitTime
			} else {
				((MyApplication) getApplication()).exit();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
