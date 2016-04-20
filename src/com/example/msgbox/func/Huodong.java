package com.example.msgbox.func;

import com.example.core.*;
import com.example.msgbox.MyApplication;
import com.example.msgbox.R;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class Huodong extends Activity implements OnItemClickListener {

	private static final String TAG = "Huodong";
	
	private ProgressDialog pd;
	private RssFeed feed;
	
	//定义Handler对象
	private Handler handler = new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			//处理UI
			pd.dismiss();
			if(msg.arg1 == 1){
				showListView();
			}else{
				showNullListView();
			}
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication app = (MyApplication)getApplication();
		app.addActivity(this);
		
		setContentView(R.layout.huodong_rss);
		setTitle("活动中心");
		
		pd = ProgressDialog.show(this, "请稍候..", "正在获取活动信息...", true,	false);
		
		new Thread(){
			@Override
			public void run(){
				//你要执行的方法
				feed = DataToolsHelper.getHuodongRss();
				//执行完毕后给handler发送一个消息
				Message msg = new Message();
				if(feed != null){
					msg.arg1 = 1;
				}else{
					msg.arg1 = 0;
				}
				
				handler.sendMessage(msg);
			}
			}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
	
	private void showNullListView() {
		TextView tv = new TextView(this);
		tv.setText("无法连接到服务器");
		RelativeLayout layout = (RelativeLayout) this.findViewById(R.id.huodonglayout);
		layout.addView(tv);
	}
	
	/*
	 * 把RSS内容绑定到ui界面进行显示
	 */
	private void showListView() {

		ListView itemList = (ListView) this.findViewById(R.id.huodonglist);
		if (feed == null) {
			setTitle("访问失败");
			return;
		}
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,
				feed.getAllItems(), android.R.layout.simple_list_item_2,
				new String[] { RssItem.TITLE, RssItem.PUBDATE }, 
				new int[] {	android.R.id.text1, android.R.id.text2 });
		itemList.setAdapter(simpleAdapter);
		itemList.setOnItemClickListener((OnItemClickListener) this);
		itemList.setSelection(0);
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

		Log.i(TAG, "click item [" + position + "]");
		Intent intent = new Intent();
		intent.setClass(this, ShowDescriptionActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("title", feed.getItem(position).getTitle());
		bundle.putString("description", feed.getItem(position).getDescription());
		bundle.putString("link", feed.getItem(position).getLink());
		bundle.putString("pubdate", feed.getItem(position).getPubdate());
		// 用android.intent.extra.INTENT的名字来传递参数
		intent.putExtra("android.intent.extra.rssItem", bundle);
		startActivityForResult(intent, 0);
	}

}