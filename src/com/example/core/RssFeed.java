package com.example.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RssFeed {
	private String feedTitle; // ����
	private String feedPubdate; // ��������
	
	private List<RssItem> rssItems; // ���������б�item

	public RssFeed() {
		rssItems = new ArrayList<RssItem>();
	}

	// ���RssItem��Ŀ,�����б���
	public int addItem(RssItem rssItem) {
		rssItems.add(rssItem);
		return rssItems.size();
	}

	// �����±��ȡRssItem
	public RssItem getItem(int position) {
		return rssItems.get(position);
	}

	public List<HashMap<String, Object>> getAllItems() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < rssItems.size(); i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put(RssItem.TITLE, rssItems.get(i).getTitle());
			item.put(RssItem.PUBDATE, rssItems.get(i).getPubdate());
			data.add(item);
		}
		return data;
	}

	public String getTitle() {
		return feedTitle;
	}

	public void setTitle(String title) {
		this.feedTitle = title;
	}

	public String getPubdate() {
		return feedPubdate;
	}

	public void setPubdate(String pubdate) {
		this.feedPubdate = pubdate;
	}

	public int getItemCount() {
		return rssItems.size();
	}
}
