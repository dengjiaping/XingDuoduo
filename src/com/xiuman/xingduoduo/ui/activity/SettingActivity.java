package com.xiuman.xingduoduo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
/**
 * @名称：SettingActivity.java
 * @描述：设置界面
 * @author danding
 * 2014-8-6
 */
public class SettingActivity extends Base2Activity implements OnClickListener {

	/*------------------------------------------组件-------------------------------------*/
	//返回
	private Button btn_back;
	//右侧
	private Button btn_right;
	//标题
	private TextView tv_title;
	//关于
	private LinearLayout llyt_setting_about;
	//清除缓存
	private LinearLayout llyt_setting_clear_cache;
	//应用推荐
	private LinearLayout llyt_setting_app_recommend;
	//使用条例
	private LinearLayout llyt_setting_protocol;
	//检查更新
	private LinearLayout llyt_setting_update;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
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
		
		llyt_setting_about = (LinearLayout) findViewById(R.id.llyt_setting_about);
		llyt_setting_clear_cache = (LinearLayout) findViewById(R.id.llyt_setting_clear_cache);
		llyt_setting_app_recommend = (LinearLayout) findViewById(R.id.llyt_setting_app_recommend);
		llyt_setting_protocol = (LinearLayout) findViewById(R.id.llyt_setting_protocol);
		llyt_setting_update = (LinearLayout) findViewById(R.id.llyt_setting_update);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText(R.string.setting_title);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		llyt_setting_about.setOnClickListener(this);
		llyt_setting_clear_cache.setOnClickListener(this);
		llyt_setting_app_recommend.setOnClickListener(this);
		llyt_setting_protocol.setOnClickListener(this);
		llyt_setting_update.setOnClickListener(this);
		
		
	}
	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back://返回
			finish();
			break;
		case R.id.llyt_setting_about://关于
			Intent intent_about = new Intent(SettingActivity.this,AboutActivity.class);
			startActivity(intent_about);
			overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
			break;
		case R.id.llyt_setting_clear_cache://清除缓存
			
			break;
		case R.id.llyt_setting_app_recommend://应用推荐
			
			break;
		case R.id.llyt_setting_protocol://使用条例
			Intent intent_protocol = new Intent(SettingActivity.this,ProtocolActivity.class);
			startActivity(intent_protocol);
			overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
			break;
		case R.id.llyt_setting_update://检查更新
			UmengUpdateAgent.update(SettingActivity.this);
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
				@Override
				public void onUpdateReturned(int updateStatus,
						UpdateResponse updateInfo) {
					switch (updateStatus) {
					case 0: // has update
						UmengUpdateAgent.showUpdateDialog(SettingActivity.this,
								updateInfo);
						break;
					case 1: // has no update
						Toast.makeText(SettingActivity.this, "没有更新",
								Toast.LENGTH_SHORT).show();
						break;
					case 2: // none wifi
						Toast.makeText(SettingActivity.this, "没有wifi连接， 只在wifi下更新",
								Toast.LENGTH_SHORT).show();
						break;
					case 3: // time out
						Toast.makeText(SettingActivity.this, "超时", Toast.LENGTH_SHORT)
								.show();
						break;
					}
				}
			});
			break;
		}
	}

}
