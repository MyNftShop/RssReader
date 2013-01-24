package com.xiaokai.rssreader;

import java.lang.reflect.Constructor;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.R.fraction;
import android.util.Log;

import com.xiaokai.rssreader.data.RSSFeed;
import com.xiaokai.rssreader.data.RSSItem;

/**
 * 
 * 功能描述：处理解析后的数据  RSS ContentHandler
 * 
 * @author sangxiaokai
 * @email sangxiaokai@qq.com
 * @phone +86 15237210133
 */
public class RSSHandler extends DefaultHandler {

	private static final String TAG = "RSSHandler";

	private final int RSS_TITLE = 1;
	private final int RSS_DESC = 2;
	private final int RSS_LINK = 3;
	private final int RSS_CATEGORY = 4;
	private final int RSS_PUBDATE = 5;

	private RSSFeed rssFeed;
	private RSSItem rssItem;

	private int currentstate;

	/**
	 * 获得存储了数据的FEED对象
	 * 
	 * @return
	 */
	public RSSFeed getFeed() {
		// TODO Auto-generated method stub
		return rssFeed;
	}

	/**
	 * {@link Constructor}
	 */
	public RSSHandler() {
	}

	public void startDocument() throws SAXException {
		rssFeed = new RSSFeed();
		rssItem = new RSSItem();
	}

	public void endDocument() throws SAXException {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
//		Log.i(TAG, "uri: " + uri + " localname: " + localName + " qname: "
//				+ qName);
		if (localName.equals("channel")) {
			currentstate = 0;
			return;
		}
		if (localName.equals("item")) {
			rssItem = new RSSItem();
			return;
		}
		if (localName.equals("title")) {
			currentstate = RSS_TITLE;
			return;
		}
		if (localName.equals("description")) {
			currentstate = RSS_DESC;
			return;
		}
		if (localName.equals("link")) {
			currentstate = RSS_LINK;
			return;
		}
		if (localName.equals("category")) {
			currentstate = RSS_CATEGORY;
			return;
		}
		if (localName.equals("pubDate")) {
			currentstate = RSS_PUBDATE;
			return;
		}
		currentstate = 0;
	}

	public void endElement(String uri, String localName, String qName) {

		if (localName.equals("item")) {
			rssFeed.addItem(rssItem);
			return;
		}

	}

	public void characters(char[] ch, int start, int length) {
		String string = new String(ch, start, length);
		switch (currentstate) {
		case RSS_TITLE:
			rssItem.setTitle(string);
			Log.i(TAG, "set title=" +string);
			currentstate=0;
			break;
		case RSS_DESC :
			rssItem.setDescription(string);
			Log.i(TAG, "setDescription=" +string);
		currentstate=0;
		break;
		case RSS_CATEGORY:
			rssItem.setCategory(string);
			Log.i(TAG, "setCategory=" +string);
			currentstate=0;
			break;
		case RSS_LINK :
			rssItem.setLink(string);
			Log.i(TAG, "setLink=" +string);
			currentstate=0;
			break;
		case RSS_PUBDATE :
			rssItem.setPubdate(string);
			Log.i(TAG, "setPubdate=" +string);
			currentstate=0;
			break;
		default:
			break;
		}
	}
}
