package com.xiaokai.rssreader;

import java.util.IllegalFormatCodePointException;

import com.xiaokai.rssreader.data.RSSItem;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * 功能描述：一条信息的详细显示页面
 * 
 * @author sangxiaokai
 * @email sangxiaokai@qq.com
 * @phone +86 15237210133
 */
public class ShowDescriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showdescription);

		String title = null;
		String content = null;

		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent.getBundleExtra(RSSItem.BUNDLE_STRING);
			if (bundle == null) {
				title = "出错了";
				content = "传入数据错误或无法传入";
			} else {
				title = bundle.getString(RSSItem.TITLE);
				content = bundle.getString(RSSItem.PUBDATE) + "\n\n";
				content += bundle.getString(RSSItem.DESCRIPTION).replace(' ',
						' ')
						+ "\n\n";
				content += "详细信息访问以下网址:\n " + bundle.getString(RSSItem.LINK);
			}

		}
		initViewers(title, content);
	}

	/**
	 * 设置内容显示
	 * @param title
	 * @param content
	 */
	private void initViewers(String title, String content) {
		// TODO Auto-generated method stub
		Button backButton=(Button)findViewById(R.id.buttonBack);
		TextView titleTextView=(TextView)findViewById(R.id.titleView);
		TextView contentTextView=(TextView)findViewById(R.id.contentView);
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		titleTextView.setText(title);
		
		contentTextView.setText(content);
		//设置textview里边的超链接可以被点击 ,暂时缺少

	}

}
