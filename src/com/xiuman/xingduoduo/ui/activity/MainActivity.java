package com.xiuman.xingduoduo.ui.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.BaseActivity;
import com.xiuman.xingduoduo.ui.fragment.FragmentBBS;
import com.xiuman.xingduoduo.ui.fragment.FragmentCalssify;
import com.xiuman.xingduoduo.ui.fragment.FragmentMe;
import com.xiuman.xingduoduo.ui.fragment.FragmentShoppingCart;
import com.xiuman.xingduoduo.ui.fragment.FragmentShoppingCenter;
import com.xiuman.xingduoduo.util.NetUtil;
import com.xiuman.xingduoduo.util.ToastUtil;

/**
 * 
 * @名称：MainActivity.java
 * @描述：程序的主界面
 * @author danding
 * @version 2014-6-3
 */
public class MainActivity extends BaseActivity implements
		OnCheckedChangeListener {

	// 商城
	private FragmentShoppingCenter fragmentShoppingCenter;
	// 分类
	private FragmentCalssify fragmentClassiyf;
	// 购物车
	private FragmentShoppingCart fragmentShoppingCart;
	// 交流中心
	private FragmentBBS fragmentCommunication;
	// 个人中心
	private FragmentMe fragmentMe;
	// Fragment碎片管理
	private FragmentManager fragmentManager;

	// 底部菜单
	private RadioGroup radiogroup_main_bottom_menu;
	//商城
	private RadioButton rbtn_shopping_center;

	// 再按一次退出
	private static Boolean isQuit = false;
	Timer timer = new Timer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	/**
	 * 数据初始化
	 */
	@Override
	protected void initData() {
		fragmentManager = getSupportFragmentManager();
	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById() {
		radiogroup_main_bottom_menu = (RadioGroup) findViewById(R.id.radiogroup_main_bottom_menu);
		rbtn_shopping_center = (RadioButton) findViewById(R.id.rbtn_shopping_center);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
//		rbtn_shopping_center.setChecked(true);
		selectTab(0);
		if(!NetUtil.isNetAvailable(this)){
			ToastUtil.ToastView(this, "网络连接失败");
		}
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		radiogroup_main_bottom_menu.setOnCheckedChangeListener(this);
	}

	/**
	 * 状态切换
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rbtn_shopping_center:// 商城
			selectTab(0);
			break;
		case R.id.rbtn_calssify:// 分类
			selectTab(1);
			break;
		case R.id.rbtn_shopping_cart:// 购物车
			selectTab(2);
			break;
		case R.id.rbtn_communication:// 交流
			selectTab(3);
			break;
		case R.id.rbtn_me:// 个人中心
			selectTab(4);
			break;
		}
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
		case 0:// 商城
			if (fragmentShoppingCenter == null) {
				fragmentShoppingCenter = new FragmentShoppingCenter();
				transaction.add(R.id.content, fragmentShoppingCenter);
			} else {
				transaction.show(fragmentShoppingCenter);
			}
			rbtn_shopping_center.setChecked(true);
			break;
		case 1:// 分类
			if (fragmentClassiyf == null) {
				fragmentClassiyf = new FragmentCalssify();
				transaction.add(R.id.content, fragmentClassiyf);
			} else {
				transaction.show(fragmentClassiyf);
			}

			break;
		case 2:// 购物车
			if (fragmentShoppingCart == null) {
				fragmentShoppingCart = new FragmentShoppingCart();
				transaction.add(R.id.content, fragmentShoppingCart);
			} else {
				transaction.show(fragmentShoppingCart);
			}
			break;
		case 3:// 交流
			if (fragmentCommunication == null) {
				fragmentCommunication = new FragmentBBS();
				transaction.add(R.id.content, fragmentCommunication);
			} else {
				transaction.show(fragmentCommunication);
			}
			break;
		case 4:// 个人中心
			if (fragmentMe == null) {
				fragmentMe = new FragmentMe();
				transaction.add(R.id.content, fragmentMe);
			} else {
				transaction.show(fragmentMe);
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
		if (fragmentShoppingCenter != null) {
			transaction.hide(fragmentShoppingCenter);
		}
		if (fragmentClassiyf != null) {
			transaction.hide(fragmentClassiyf);
		}
		if (fragmentShoppingCart != null) {
			transaction.hide(fragmentShoppingCart);
		}
		if (fragmentCommunication != null) {
			transaction.hide(fragmentCommunication);
		}
		if (fragmentMe != null) {
			transaction.hide(fragmentMe);
		}
	}

	/**
	 * 按键点击事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 返回键
			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(getBaseContext(), "再按一次退出", Toast.LENGTH_SHORT)
						.show();
				TimerTask task = null;
				task = new TimerTask() {
					@Override
					public void run() {
						isQuit = false;
					}
				};
				timer.schedule(task, 2000);
			} else {
				finish();
				System.exit(0);
			}
		}
		return true;
	}
}
