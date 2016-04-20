package com.example.msgbox;

import java.util.LinkedList;
import java.util.List;

import com.example.core.DataToolsHelper;


import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application {

	// myApp = (MyApp) getApplication(); //����Զ����Ӧ�ó���MyApp

	private String userName = "";

	public boolean isComeWithPsw() {
		return userName.length() > 2;
	}

	public void setUserName(String s) {
		this.userName = s;
	}

	public String getUserName() {
		return this.userName;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		DataToolsHelper.init(this);
	}

	private List<Activity> activityList = new LinkedList<Activity>();

	// ���Activity��������
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// ��������Activity��finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		
		//activityList.clear();
		System.exit(0);
	}
}