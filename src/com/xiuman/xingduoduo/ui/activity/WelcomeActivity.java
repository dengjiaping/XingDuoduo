package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.BasePagerAdapter;
import com.xiuman.xingduoduo.util.CreateShut;

/**
 * @名称：WelcomeActivity.java
 * @描述：引导页
 * @author danding
 * 2014-9-22
 */
public class WelcomeActivity extends Activity implements OnPageChangeListener
		 {
	//引导ViewPager
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	//指示器
	private LinearLayout indicatorLayout;
	private ArrayList<View> views;
	private ImageView[] indicators = null;
	//引导图
	private int[] images = new int[] { R.drawable.guide_1, R.drawable.guide_2,
			R.drawable.guide_3,R.drawable.guide_4};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		// 创建桌面快捷方式
		new CreateShut(this);
		initView();
	}

	/**
	 * @描述：视图初始化
	 * 2014-9-22
	 */
	private void initView() {
		// 实例化视图控件
		viewPager = (ViewPager) findViewById(R.id.viewpage);
		indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
		views = new ArrayList<View>();
		indicators = new ImageView[images.length]; // 定义指示器数组大小
		for (int i = 0; i < images.length; i++) {
			// 循环加入图片
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(images[i]);
			views.add(imageView);
			// 循环加入指示器
			indicators[i] = new ImageView(this);
			indicators[i].setBackgroundResource(R.drawable.indicators_default);
			if (i == 0) {
				indicators[i].setBackgroundResource(R.drawable.indicators_now);
			}
			indicatorLayout.addView(indicators[i]);
		}
		pagerAdapter = new BasePagerAdapter(views,this);
		viewPager.setAdapter(pagerAdapter); // 设置适配器
		viewPager.setOnPageChangeListener(this);
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// 监听viewpage
	@Override
	public void onPageSelected(int arg0) {
		// 更改指示器图片
		for (int i = 0; i < indicators.length; i++) {
			indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
			if (arg0 != i) {
				indicators[i]
						.setBackgroundResource(R.drawable.indicators_default);
			}
		}
	}
}
