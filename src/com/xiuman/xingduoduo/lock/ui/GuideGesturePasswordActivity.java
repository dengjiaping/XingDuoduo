package com.xiuman.xingduoduo.lock.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
/**
 * 
 * 名称：GuideGesturePasswordActivity.java
 * 描述：手势密码设置引导
 * @author danding
 * @version
 * 2014-6-18
 */
public class GuideGesturePasswordActivity extends Base2Activity implements OnClickListener {
	
	/*-----------------------------组件-----------------------*/
	//创建手势密码
	private Button btn_create;
	//返回
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturepassword_guide);
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
		btn_back = (Button) findViewById(R.id.btn_back_gesture);
		btn_create = (Button) findViewById(R.id.gesturepwd_guide_btn);
	}

	@Override
	protected void initUI() {
		
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_create.setOnClickListener(this);
	}

	/**
	 * 
	 * 描述：点击事件
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back_gesture://返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in, R.anim.translate_horizontal_finish_out);
			break;
		case R.id.gesturepwd_guide_btn://创建手势密码
			Intent intent = new Intent(GuideGesturePasswordActivity.this,
					CreateGesturePasswordActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
			break;
		}
	}

}
