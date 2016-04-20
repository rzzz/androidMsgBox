package com.example.msgbox.func;

import com.example.core.*;
import com.example.msgbox.MyApplication;
import com.example.msgbox.R;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class Jianjie extends Activity {

	private static final String TAG = "Jianjie";
	
	WebView webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication app = (MyApplication)getApplication();
		app.addActivity(this);
		
		setContentView(R.layout.jianjieinfo);
		setTitle("¼ò½é");
		
		webview = (WebView)findViewById(R.id.jianjieweb);
		DataToolsHelper.showJianjie(webview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

}