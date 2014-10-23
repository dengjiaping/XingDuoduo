package com.xiuman.xingduoduo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.ui.base.BaseActivity;
import com.xiuman.xingduoduo.ui.fragment.FragmentPostJingHua;
import com.xiuman.xingduoduo.ui.fragment.FragmentPostNew;

/**
 * @名称：PostListActivity.java
 * @描述：帖子列表
 * @author danding
 * @时间 2014-10-23
 */
public class PostListActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {

	/*---------------------------------组件---------------------------*/
	// 返回
	private Button btn_back;
	// 发帖
	private Button btn_right;
	// contanier
	private RadioGroup rg_container;
	// 最新帖子界面
	private FragmentPostNew fragmentNew;
	// 精化帖子界面
	private FragmentPostJingHua fragmentJingHua;
	// Fragment碎片管理
	private FragmentManager fragmentManager;

	// 从上级界面接收到的板块信息
	private BBSPlate plate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_list);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {
		fragmentManager = getSupportFragmentManager();
		// 从上级界面接收到的板块信息
		plate = (BBSPlate) getIntent().getExtras().getSerializable("bbs_plate");
		setPlate(plate);
	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_right = (Button) findViewById(R.id.btn_bbs_right);
		rg_container = (RadioGroup) findViewById(R.id.rg_container);
	}

	@Override
	protected void initUI() {
		selectTab(0);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		rg_container.setOnCheckedChangeListener(this);
		btn_right.setOnClickListener(this);
	}

	/**
	 * 
	 * 描述：界面切换
	 * 
	 * @param index
	 */
	public void selectTab(int index) {
		// 开启事物
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 隐藏当前显示的Fragment
		hideFragment(transaction);
		// Fragment切换
		switch (index) {
		case 0:// 最新
			if (fragmentNew == null) {
				fragmentNew = new FragmentPostNew();
				transaction.add(R.id.flyt_contanier, fragmentNew);
			} else {
				transaction.show(fragmentNew);
			}
			break;
		case 1:// 精化
			if (fragmentJingHua == null) {
				fragmentJingHua = new FragmentPostJingHua();
				transaction.add(R.id.flyt_contanier, fragmentJingHua);
			} else {
				transaction.show(fragmentJingHua);
			}

			break;
		}

		// 提交事物
		transaction.commitAllowingStateLoss();
	}

	/**
	 * 
	 * 描述：隐藏已经显示的Fragment,提高应用的运行速度
	 * 
	 * @param transaction
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (fragmentNew != null) {
			transaction.hide(fragmentNew);
		}
		if (fragmentJingHua != null) {
			transaction.hide(fragmentJingHua);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;

		case R.id.btn_bbs_right:// 发帖
			Intent intent_publish = new Intent(PostListActivity.this,
					PostPublishActivity.class);
			Bundle bundle = new Bundle();
			// 版块id
			bundle.putString("forumId", plate.getId());
			intent_publish.putExtras(bundle);
			startActivity(intent_publish);
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rbtn_post_new:// 最i新
			selectTab(0);
			break;
		case R.id.rbtn_post_jinghua:// 精化
			selectTab(1);
			break;
		}
	}

	public BBSPlate getPlate() {
		return plate;
	}

	public void setPlate(BBSPlate plate) {
		this.plate = plate;
	}

}
