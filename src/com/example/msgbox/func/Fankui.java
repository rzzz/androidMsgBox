package com.example.msgbox.func;

import com.example.core.*;
import com.example.msgbox.MyApplication;
import com.example.msgbox.R;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Fankui extends Activity {

	private static final String TAG = "Fankui";
	
	Button sub;
	Button cancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//MyApplication app = (MyApplication)getApplication();
		//app.addActivity(this);
		
		setContentView(R.layout.fankuiinfo);
		setTitle("反馈");
		
		sub = (Button)findViewById(R.id.fanokbt);
		sub.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				String text = sub.getText().toString().trim();
				if(text.length() > 0){
					Toast.makeText(Fankui.this, "谢谢您的反馈", Toast.LENGTH_SHORT).show();
					
					MyApplication app = (MyApplication)getApplication();
					DataToolsHelper.submitFankui(app.getUserName(), text, new DataToolsHelper.OnDataLoadedListener() {
						
						@Override
						public void onSuccess(String paramString) {
							Log.i(TAG, "fankui Success");
						}
						
						@Override
						public void onFailure(String paramString) {
							Log.i(TAG, "fankui Failure");							
						}
					});				
				}
				else{
					Toast.makeText(Fankui.this, "请输入反馈内容", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		cancel = (Button)findViewById(R.id.fancancelbt);
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
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
