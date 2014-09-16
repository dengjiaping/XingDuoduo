package com.xiuman.xingduoduo.ui.base;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.umeng.analytics.MobclickAgent;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppManager;

/**
 * 
 * @名称：Base2Activity.java
 * @描述：Activity基类，支持侧滑结束Activity
 * @author danding
 * @version 2014-6-6
 */
public abstract class Base2Activity extends SwipeBackActivity {
	public static final String TAG = "Base2Activity";
	public SwipeBackLayout mSwipeBackLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 添加到Activity栈中
		AppManager.getAppManager().addActivity(this);
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);// 右滑结束
		Log.d("Base2Activity", "Base2Activity");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	public void onBackPressed() {
		scrollToFinishActivity();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 自定义返回键的效果
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 返回键
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
		}
		return true;
	}

	/**
	 * 
	 * 描述：数据初始化
	 */
	protected abstract void initData();

	/**
	 * 
	 * 描述：渲染界面
	 */
	protected abstract void findViewById();

	/**
	 * 
	 * 描述：界面初始化
	 */
	protected abstract void initUI();

	/**
	 * 
	 * 描述：设置监听
	 */
	protected abstract void setListener();
}
