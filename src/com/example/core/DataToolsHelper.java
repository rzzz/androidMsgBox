package com.example.core;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.content.Context;
import android.webkit.WebView;

public class DataToolsHelper {

	public static Context app = null;
	
	public static void init(Context ap){
		app = ap;
	}

	public static interface OnDataLoadedListener {
		public abstract void onSuccess(String paramString);

		public abstract void onFailure(String paramString);
	}

	public static void verifyPsw(final String name, final String psw,
			final OnDataLoadedListener listener) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				if (name.equals("admin") && psw.equals("123456")) {
					listener.onSuccess("");
				} else {
					listener.onFailure("");
				}

			}
		});
		thread.start();
	}

	public static void submitFankui(String name, String text,
			final OnDataLoadedListener listener) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				listener.onSuccess("");
			}
		});
		thread.start();
	}

	public static void showJianjie(WebView webview) {
		// webview.loadUrl("https://www.baidu.com/");
		webview.loadUrl("file:///android_asset/jianjie.html");
	}

	public static void showHuiyuan(WebView webview) {
		// webview.loadUrl("https://www.baidu.com/");
		webview.loadUrl("file:///android_asset/huiyuan.html");
	}

	public static RssFeed getHuodongRss() {
		//外部使用异步的方式调用
		RssFeed feed = null;
		try {
			// feed = RssFeed_SAXParser.getFeed("http://news.qq.com/newsgn/rss_newsgn.xml", false, null);
			feed = RssFeed_SAXParser.getFeed("rss_huodong.xml", true, app);
			System.out.println(feed.getAllItems());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return feed;
	}
	
	public static RssFeed getXiaoxiRss() {
		//外部使用异步的方式调用
		RssFeed feed = null;
		try {
			// feed = RssFeed_SAXParser.getFeed("http://news.qq.com/newsgn/rss_newsgn.xml", false, null);
			feed = RssFeed_SAXParser.getFeed("rss_xiaoxi.xml", true, app);
			System.out.println(feed.getAllItems());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		return feed;
	}
}
