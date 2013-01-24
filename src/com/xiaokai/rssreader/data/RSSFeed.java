package com.xiaokai.rssreader.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.nfc.Tag;
import android.util.Log;


/**
 * 功能描述：和一个RSS的完整文件相对应 本例中Rss feed 文件存储在assets目录下
 * 
 * @author sangxiaokai
 * @email sangxiaokai@qq.com
 * @phone +86 15237210133
 */
public class RSSFeed {
	private static final String TAG = "RSSFeed";
	private String _title = null;
	private String _pubdate = null;
	private int itemCount = 0;
	private List<RSSItem> itemList;

	public RSSFeed() {
		// TODO Auto-generated constructor stub
		itemList = new Vector<RSSItem>(0);
	}

	public int addItem(RSSItem item) {
		itemList.add(item);
		itemCount++;
		return itemCount;
	}

	public RSSItem getItem(int location) {
		return itemList.get(location);
	}

	/**
	 * 得到所有的items,为列表生成所需要的数据
	 * @return
	 */
	public List getAllItemsForListView() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int size = itemList.size();
		for (int i = 0; i < size; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put(RSSItem.TITLE, itemList.get(i).getTitle());
			item.put(RSSItem.PUBDATE, itemList.get(i).getPubdate());
			Log.i(TAG, "item: "+item.get(RSSItem.TITLE));
			data.add(item);
		}
		return data;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setTitle(String title) {
		this._title = title;
	}

	public void setPubDate(String pubdate) {
		this._pubdate = pubdate;
	}

	public String getTitle() {
		return this._title;
	}

	public String getPubDate() {
		return this._pubdate;
	}

}
