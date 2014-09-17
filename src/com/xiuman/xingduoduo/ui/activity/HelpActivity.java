package com.xiuman.xingduoduo.ui.activity;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.Base2Activity;

/**
 * @名称：HelpActivity.java
 * @描述：使用帮助界面
 * @author danding
 * @version
 * @date：2014-7-2
 */
public class HelpActivity extends Base2Activity implements OnClickListener {

	/*-------------------------组件----------------------*/
	// 返回
	private Button btn_back;
	// 标题
	private TextView tv_title;
	// 右侧
	private Button btn_right;
	// 帮助文字
	private TextView tv_help_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		initData();
		findViewById();
		initUI();
		setListener();

	}

	@Override
	protected void initData() {

	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		tv_help_txt = (TextView) findViewById(R.id.tv_help_txt);
	}

	@Override
	protected void initUI() {
		tv_title.setText("使用帮助");
		btn_right.setVisibility(View.INVISIBLE);
		
		StringBuffer sb = new StringBuffer();
		InputStream is;
		try {
			is = getAssets().open("help.txt");
			int len = -1;
			byte[] buf = new byte[11024];
			while ((len = is.read(buf)) != -1) {
				sb.append(new String(buf, 0, len, "gbk"));
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		tv_help_txt.setText(sb.toString());
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;

		default:
			break;
		}
	}

}
