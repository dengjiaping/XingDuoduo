package com.xiuman.xingduoduo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.Base2Activity;

/**
 * @名称：AppRecommendActivity.java
 * @描述：应用推荐
 * @author danding 2014-9-21
 */
public class AppRecommendActivity extends Base2Activity implements OnClickListener {
	
	/*------------------------------------组件---------------------------------*/
	//返回
	private Button btn_back;
	//标题栏
	private TextView tv_title;
	//右侧
	private Button btn_right;
	//应用列表
	private ListView lv_app_recommend;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_recommend);
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
		btn_back =(Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		lv_app_recommend = (ListView) findViewById(R.id.lv_app_recommend);
	}

	@Override
	protected void initUI() {	
		tv_title.setText("推荐应用");
		btn_right.setVisibility(View.INVISIBLE);
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
