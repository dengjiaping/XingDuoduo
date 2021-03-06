package com.xiuman.xingduoduo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
/**
 * 
 * @名称：ProtocolActivity.java
 * @描述：性多多服务协议界面
 * @author danding
 * @version 
 * @date：2014-6-20
 */
public class ProtocolActivity extends Base2Activity implements OnClickListener {

	/*------------------------------组件-----------------------------*/
	//返回
	private Button btn_back;
	//右侧
	private Button btn_right;
	//标题
	private TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_protocol);
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
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText("使用条例");
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
		case R.id.btn_common_back://返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in, R.anim.translate_horizontal_finish_out);
			break;

		default:
			break;
		}
	}

}
