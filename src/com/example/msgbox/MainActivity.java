package com.example.msgbox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	Button youke;
	Button denglu;
	Button jinru;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication app = (MyApplication)getApplication();
		app.addActivity(this);
		
		setContentView(R.layout.activity_main);
		
		youke = (Button)findViewById(R.id.button1);
		denglu = (Button)findViewById(R.id.button2);
		jinru = (Button)findViewById(R.id.button3);
		
		updateBts();
		
		youke.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View paramView) {
				Log.i(TAG, "click youke");
				
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainMenuActivity.class);
                startActivity(intent);
			}
			
		});
		
		denglu.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View paramView) {
				Log.i(TAG, "click denglu");
				
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
			}
			
		});
		
		jinru.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View paramView) {
				Log.i(TAG, "click jinru");
			
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainMenuActivity.class);
                startActivity(intent);
			}
			
		});
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
		updateBts();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
	
	private void updateBts(){
		SharedPreferences sharedPreferences= getSharedPreferences("test1", Activity.MODE_PRIVATE); 
		boolean bhas = sharedPreferences.getBoolean("bhas", false);
		MyApplication app = (MyApplication)getApplication();
		app.setUserName(sharedPreferences.getString("sn", ""));
		
		if(bhas){
			youke.setVisibility(View.INVISIBLE);
			denglu.setVisibility(View.INVISIBLE);
			jinru.setVisibility(View.VISIBLE);
			
			TextView tx = (TextView)findViewById(R.id.fmusername);
			tx.setText("»¶Ó­£º" + sharedPreferences.getString("sn", ""));
		}
		else{
			youke.setVisibility(View.VISIBLE);
			denglu.setVisibility(View.VISIBLE);
			jinru.setVisibility(View.INVISIBLE);
		}
	}

}
