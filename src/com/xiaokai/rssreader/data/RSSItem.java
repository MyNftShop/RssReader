package com.xiaokai.rssreader.data;


/**
 * 
 * 功能描述：和一个RSS中的item相对应
 * 
 * @author sangxiaokai
 * @email sangxiaokai@qq.com
 * @phone +86 15237210133
 */
public class RSSItem {

	public static final String BUNDLE_STRING = "android.intent.extra.rssitem";
	public static final String TITLE = "title";
	public static final String LINK = "link";
	public static final String CATEGORY = "category";
	public static final String DESCRIPTION = "description";
	public static final String PUBDATE = "pubDate";
	public  String _title = null;
	public  String _pubdate = null;
	public  String _link = null;
	public  String _category = null;
	public  String _description = null;

	public RSSItem() {
	}

	public void setTitle(String string) {
		this._title = string;
	}

	public void setPubdate(String string) {
		this._pubdate = string;
	}

	public void setCategory(String string) {
		this._category = string;
	}

	public void setLink(String string) {
		this._link = string;
	}

	public void setDescription(String string) {
		this._description = string;
	}

	public String getTitle() {
		return this._title ;
	}

	public String getPubdate() {
		return this._pubdate ;
	}

	public String getCategory() {
		return this._category ;
	}

	public String getLink() {
		return this._link ;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return this._description;
	}
	
	public String toString() {
		if (this._title.length()>20) {
			return this._title.subSequence(0, 42)+"...";
		}
		return _category;
	}
}
