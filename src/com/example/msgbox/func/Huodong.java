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
	
	//����Handler����
	private Handler handler = new Handler(){
		@Override
		//������Ϣ���ͳ�����ʱ���ִ��Handler���������
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			//����UI
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
		setTitle("�����");
		
		pd = ProgressDialog.show(this, "���Ժ�..", "���ڻ�ȡ���Ϣ...", true,	false);
		
		new Thread(){
			@Override
			public void run(){
				//��Ҫִ�еķ���
				feed = DataToolsHelper.getHuodongRss();
				//ִ����Ϻ��handler����һ����Ϣ
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
		tv.setText("�޷����ӵ�������");
		RelativeLayout layout = (RelativeLayout) this.findViewById(R.id.huodonglayout);
		layout.addView(tv);
	}
	
	/*
	 * ��RSS���ݰ󶨵�ui���������ʾ
	 */
	private void showListView() {

		ListView itemList = (ListView) this.findViewById(R.id.huodonglist);
		if (feed == null) {
			setTitle("����ʧ��");
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
		// ��android.intent.extra.INTENT�����������ݲ���
		intent.putExtra("android.intent.extra.rssItem", bundle);
		startActivityForResult(intent, 0);
	}

}