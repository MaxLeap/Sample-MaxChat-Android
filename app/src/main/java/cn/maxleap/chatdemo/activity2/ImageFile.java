package cn.maxleap.chatdemo.activity2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import cn.maxleap.chatdemo.adapter2.FolderAdapter;
import cn.maxleap.chatdemo.utils.Bimp;
import cn.maxleap.chatdemo.utils.PublicWay;
import cn.maxleap.chatdemo.utils.Res;
import cn.maxleap.chatdemo.utils.UiUtils;


/**
 * 这个类主要是用来进行显示包含图片的文件夹
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:48:06
 */
public class ImageFile extends Activity {

	private FolderAdapter folderAdapter;
	private Button        bt_cancel;
	private Context       mContext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(Res.getLayoutID("plugin_camera_image_file"));
		UiUtils.setSystemBar(this);
		PublicWay.activityList.add(this);
		mContext = this;
		bt_cancel = (Button) findViewById(Res.getWidgetID("cancel"));
		bt_cancel.setOnClickListener(new CancelListener());
		GridView gridView = (GridView) findViewById(Res.getWidgetID("fileGridView"));
		TextView textView = (TextView) findViewById(Res.getWidgetID("headerTitle"));
		textView.setText(Res.getString("photo"));
		folderAdapter = new FolderAdapter(this);
		gridView.setAdapter(folderAdapter);
	}

	private class CancelListener implements OnClickListener {// 取消按钮的监听
		public void onClick(View v) {
			//清空选择的图片
			Bimp.tempSelectBitmap.clear();
			Intent intent = new Intent();
			intent.setClass(mContext, ShuoActivity.class);
			startActivity(intent);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(mContext, ShuoActivity.class);
			startActivity(intent);
		}
		
		return true;
	}

}
