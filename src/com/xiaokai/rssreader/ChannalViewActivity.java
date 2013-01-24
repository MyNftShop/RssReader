package com.xiaokai.rssreader;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.cookie.SM;
import org.apache.http.util.EncodingUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.xiaokai.rssreader.data.RSSFeed;
import com.xiaokai.rssreader.data.RSSItem;

import android.os.Bundle;
import android.R.xml;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.util.Config;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AnalogClock;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 
 * 功能描述：频道信息列表的显示页面
 * 
 * @author sangxiaokai
 * @email sangxiaokai@qq.com
 * @phone +86 15237210133 Copyright © 2013-2113 Sang Puyuan. All rights
 *        reserved. You can not use it for any commercial purposes
 * 
 */
public class ChannalViewActivity extends Activity implements
		OnItemClickListener {

	private static final String TAG = "main";
	private RSSFeed feed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle == null) {
			this.finish();
			return;
		}
		String urlString = bundle.getString(BundleUnit.KEY_INTENT_URL);
		if (urlString.equals("")) {
			this.finish();
			return;
		}
		feed = getFeed(urlString);
		showListView();
	}

	/**
	 * 显示列表
	 */
	private void showListView() {
		// TODO Auto-generated method stub
		ListView listView = (ListView) findViewById(R.id.listView1);
		if (feed == null) {
			setTitle("访问RSS失败");
			return;
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this,
				feed.getAllItemsForListView(),
				android.R.layout.simple_list_item_2, new String[] {
						RSSItem.TITLE, RSSItem.PUBDATE }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		listView.setAdapter(simpleAdapter);
		listView.setSelection(0);
		// 设置Listview的点击动作
		listView.setOnItemClickListener(this);

	}

	/**
	 * 得到一个RSSFeed
	 * 
	 * @param rssUrl
	 * @return
	 */
	private RSSFeed getFeed(String urlString) {
		// TODO Auto-generated method stub
		RSSFeed feed = null;
		try {
			// 获得RSSFeed的输入对象
			InputSource inputSource = getRssFeedInputSource(urlString);
			if (inputSource != null) {
				Log.i(TAG, "获得输入流成功！");

				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				XMLReader xmlReader = parser.getXMLReader();

				RSSHandler rssHandler = new RSSHandler();
				xmlReader.setContentHandler(rssHandler);
				xmlReader.parse(inputSource);
				return rssHandler.getFeed();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return feed;
	}

	/**
	 * 获得RSSFeed的输入流
	 * 
	 * // 1：获得XML编码方式 // 2：通过InputStreamReader设定好编码 // ，然后将然后将InputStreamReader
	 * // 通过InputSource的构造方法传给InputSource // 3：返回input source
	 * 
	 * @return
	 */
	private InputSource getRssFeedInputSource(String urlString) {
		Log.i(TAG, "getRssFeedInputSource url= " + urlString);
		InputSource inputSource = null;
		try {
			// 1：获得XML编码方式
			URL url;
			url = new URL(urlString);
			// 得到探测器代理对象
			CodepageDetectorProxy detector = CodepageDetectorProxy
					.getInstance();
			// 向代理对象添加探测器
			detector.add(JChardetFacade.getInstance());
			// 得到编码字符集对象
			Charset charset = detector.detectCodepage(url);
			// 得到编码名称
			String encodingName = charset.name();
			// 2：通过InputStreamReader设定好编码
			// ，然后将然后将InputStreamReader
			// 通过InputSource的构造方法传给InputSource
			InputStream inputStream = url.openStream();
			// 通过InputStreamReader设定编码方式
			InputStreamReader streamReader = new InputStreamReader(inputStream,
					encodingName);
			inputSource = new InputSource(streamReader);
		} catch (Exception e) {
			Log.e(TAG, "error:" + e.getMessage());
		}
		// 3：返回input source,出错返回null
		return inputSource;
	}

	// if (URLUtil.isHttpUrl(filename) || URLUtil.isHttpsUrl(filename)) {
	// // 如果URL是网址：filename是http 或 https 网址
	// try {
	// URL url = new URL(filename);
	// inputStream = url.openStream();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// Log.e(TAG, "获得RSS Feed失败！ type=url \n" + e.getMessage());
	// }
	// }
	// // filename是文件名
	// // 如果RSS.xml是存储在assets文件夹下的文件，使用下面的代码
	// AssetManager assetManager = getAssets();
	// try {
	// inputStream = assetManager.open(filename);
	// } catch (Exception e) {
	// Log.e(TAG, "获得RSS Feed失败！type= file \n" + e.getMessage());
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent itemIntent = new Intent(this, ShowDescriptionActivity.class);

		Bundle bundle = new Bundle();
		bundle.putString(RSSItem.TITLE, feed.getItem(position).getTitle());
		bundle.putString(RSSItem.CATEGORY, feed.getItem(position).getCategory());
		bundle.putString(RSSItem.LINK, feed.getItem(position).getLink());
		bundle.putString(RSSItem.DESCRIPTION, feed.getItem(position)
				.getDescription());
		bundle.putString(RSSItem.PUBDATE, feed.getItem(position).getPubdate());

		itemIntent.putExtra(RSSItem.BUNDLE_STRING, bundle);

		startActivityForResult(itemIntent, RESULT_OK);
	}

}
