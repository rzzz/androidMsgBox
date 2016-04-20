package com.example.core;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.content.res.AssetManager;

import java.net.URL;
import java.nio.charset.Charset;

public class RssFeed_SAXParser {

	public static RssFeed getFeed(String urlStr, boolean isLocal, Context app)
			throws ParserConfigurationException, SAXException, IOException {
		InputSource inputSource = null;

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); // ����SAX��������
		SAXParser saxParser = saxParserFactory.newSAXParser(); // ������������������
		XMLReader xmlReader = saxParser.getXMLReader(); // ͨ��saxParser����xmlReader�Ķ���

		RssHandler rssHandler = new RssHandler();
		xmlReader.setContentHandler(rssHandler);

		if (isLocal) {
			try {
				InputStream is = null;
				AssetManager manager = app.getAssets();
				is = manager.open(urlStr); // ������assets�ļ�����
				inputSource = new InputSource(is);
				inputSource.setEncoding("UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		} else {
			URL url = new URL(urlStr);

			/*
			 * CodepageDetectorProxy detector =
			 * CodepageDetectorProxy.getInstance(); // �����������̽����
			 * detector.add(JChardetFacade.getInstance()); // �õ������ַ������� Charset
			 * charset = detector.detectCodepage(url); // �õ��������� String
			 * encodingName = charset.name();
			 */
			String encodingName = getReomoteURLFileEncode(url);
			System.out.println(encodingName);

			// �����GB2312����
			if ("GBK".equals(encodingName)) {
				try{
					InputStream stream = null;
					stream = url.openStream();
					// ͨ��InputStreamReader�趨���뷽ʽ
					InputStreamReader streamReader = new InputStreamReader(stream, encodingName);
					inputSource = new InputSource(streamReader);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			} else {
				// ��utf-8����
				try{
					inputSource = new InputSource(url.openStream());
					inputSource.setEncoding("UTF-8");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}

		if(inputSource == null){
			return null;
		}
		
		xmlReader.parse(inputSource);
		return rssHandler.getRssFeed();
	}

	/**
	 * ���Զ��URL�ļ��ı����ʽ
	 */
	public static String getReomoteURLFileEncode(URL url) {

		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(new ByteOrderMarkDetector());
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		Charset charset = null;
		try {
			System.out.println(url);
			charset = detector.detectCodepage(url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (charset != null) {
			return charset.name();
		} else {
			return "UTF-8";
		}
	}
}
