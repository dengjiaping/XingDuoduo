package com.xiuman.xingduoduo.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.BaseActivity;

/**
 * @名称：GoodsInfoDiscussActivity.java
 * @描述：商品图文详情+评论列表
 * @author danding
 * @时间 2014-10-13
 */
public class GoodsInfoDiscussActivity extends BaseActivity {
	
	/*-------------------------------------------组件--------------------------------------*/
	//标题栏
	private RelativeLayout rlyt_title;
	//返回
	private Button btn_back;
	//详情
	private RadioButton rbtn_info;
	//评论
	private RadioButton rbtn_discuss;
	//
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_info_discuss);
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

	}

	@Override
	protected void initUI() {

	}

	@Override
	protected void setListener() {

	}

}
