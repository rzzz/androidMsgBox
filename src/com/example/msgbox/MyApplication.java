package com.example.msgbox;

import java.util.LinkedList;
import java.util.List;

import com.example.core.DataToolsHelper;


import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application {

	// myApp = (MyApp) getApplication(); //获得自定义的应用程序MyApp

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

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		
		//activityList.clear();
		System.exit(0);
	}
}