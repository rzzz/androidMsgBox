package com.example.core;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class RssHandler extends DefaultHandler {
	RssFeed rssFeed;
	RssItem rssItem;

	String lastElementName = "";// ��Ǳ��������ڱ���ڽ������������ǹ��ĵļ�����ǩ�����������ǹ��ĵı�ǩ����0

	final int RSS_TITLE = 1;// ���� title ��ǩ������1��ע��������title�������Ƕ�������item�ĳ�Ա������
	final int RSS_LINK = 2;// ���� link ��ǩ������2
	final int RSS_DESCRIPTION = 3;// ���� description ��ǩ������3
	final int RSS_AUTHOR = 4;
	final int RSS_PUBDATE = 5; // ����pubdate��ǩ,����5,ע��������pubdate,�����Ƕ�������item��pubdate��Ա������	

	int currentFlag = 0;

	public RssHandler() {

	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		rssFeed = new RssFeed();
		rssItem = new RssItem();
	}
	
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		
		if ("chanel".equals(localName)) {
			// �����ǩ��û�����ǹ��ĵ����ݣ����Բ�������currentFlag=0
			currentFlag = 0;
			return;
		}
		
		if ("item".equals(localName)) {
			rssItem = new RssItem();
			return;
		}
		
		if ("title".equals(localName)) {
			currentFlag = RSS_TITLE;
			return;
		}
		if ("description".equals(localName)) {
			currentFlag = RSS_DESCRIPTION;
			return;
		}
		if ("link".equals(localName)) {
			currentFlag = RSS_LINK;
			return;
		}
		if ("pubDate".equals(localName)) {
			currentFlag = RSS_PUBDATE;
			return;
		}
		if ("author".equals(localName)) {
			currentFlag = RSS_AUTHOR;
			return;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		
		// ��ȡ�ַ���
		String text = new String(ch, start, length);
		Log.i("i", "Ҫ��ȡ�����ݣ�" + text);

		if(rssItem == null){ 
			return;		//��û����item֮ǰ��feed��title��pubdata������
		}
		
		switch (currentFlag) {
		case RSS_TITLE:
			rssItem.setTitle(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_DESCRIPTION:
			rssItem.setDescription(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_LINK:
			rssItem.setLink(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_PUBDATE:
			rssItem.setPubdate(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_AUTHOR:
			rssItem.setAuthor(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		default:
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		
		// �������һ��item�ڵ�������ͽ�rssItem��ӵ�rssFeed�С�
		if ("item".equals(localName)) {

			rssFeed.addItem(rssItem);
			rssItem = null;
			return;
		}
	}

	public RssFeed getRssFeed() {
		return rssFeed;
	}
}