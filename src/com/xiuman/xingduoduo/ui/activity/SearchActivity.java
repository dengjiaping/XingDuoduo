package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.SearchViewPagerAdapter;
import com.xiuman.xingduoduo.db.dao.SearchRecentlyDao;
import com.xiuman.xingduoduo.ui.base.BaseActivity;
import com.xiuman.xingduoduo.ui.fragment.FragmentSearchHot;
import com.xiuman.xingduoduo.ui.fragment.FragmentSearchRecently;

/**
 * 
 * @名称：SearchActivity.java
 * @描述：搜索界面
 * @author danding
 * @version
 * @date：2014-6-23
 */
public class SearchActivity extends BaseActivity implements
		SwipeBackActivityBase, OnClickListener {

	/*---------------------------------组件--------------------------------*/
	// ViewPager
	private ViewPager viewpager_search_content;
	// 搜索指示器
	private ImageView iv_search_indicator;
	// 搜索标题(最近、热门)
	private TextView tv_search_recently, tv_search_hot;
	// 热门、最近
	private LinearLayout llyt_search_recently, llyt_search_hot;

	/*---------------标题栏------------------*/
	// 搜索输入框
	private EditText et_search_input_keyword;
	// 清除按钮
	private Button btn_clear_search_input;
	// 搜索、取消搜索按钮
	private Button btn_start_search;

	/*---------------------------------------------------------------------*/
	// 屏幕宽高
	private int tabWidth;
	// 当前页卡编号
	private int currIndex = 0;
	// 动画图片偏移量
	private int zero = 0;
	// 单个水平动画位移
	private int one;
	private int two;
	// FragmentManager
	private FragmentManager fragmentManager;

	/*------------------------------------数据----------------------------*/
	// Fragments
	private List<Fragment> fragments = new ArrayList<Fragment>();
	//热门搜索Fragment
	private FragmentSearchHot fragment_searchHot;
	//最近搜索Fragment
	private FragmentSearchRecently fragment_searchRecently;
	// 数据库操作
	private SearchRecentlyDao recently_dao;
	//ViewPager Adapter
	private SearchViewPagerAdapter viewpager_adapter; 
	
	
	/*----------------------------------滑动结束--------------------------*/
	private SwipeBackActivityHelper mHelper;
	public SwipeBackLayout mSwipeBackLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
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
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 获取屏幕宽度
		tabWidth = dm.widthPixels;
		one = tabWidth / 2; // 设置水平动画平移大小
		two = one * 2;
		
		
		fragment_searchHot = new FragmentSearchHot();
		fragment_searchRecently = new FragmentSearchRecently();
		fragmentManager = getSupportFragmentManager();
		fragments.add(fragment_searchRecently);
		fragments.add(fragment_searchHot);

		// 数据库
		recently_dao = new SearchRecentlyDao(this);

		/*----------------------------------滑动结束--------------------------*/
		mHelper = new SwipeBackActivityHelper(this);
		mHelper.onActivityCreate();
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);// 右滑结束
	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById() {
		llyt_search_recently = (LinearLayout) findViewById(R.id.llyt_search_recently);
		llyt_search_hot = (LinearLayout) findViewById(R.id.llyt_search_hot);
		tv_search_recently = (TextView) findViewById(R.id.tv_search_recently);
		tv_search_hot = (TextView) findViewById(R.id.tv_search_hot);
		iv_search_indicator = (ImageView) findViewById(R.id.iv_tab_indicator);
		viewpager_search_content = (ViewPager) findViewById(R.id.viewpager_search_content);

		// 标题栏
		btn_clear_search_input = (Button) findViewById(R.id.btn_clear_search_input);
		btn_start_search = (Button) findViewById(R.id.btn_start_search);
		et_search_input_keyword = (EditText) findViewById(R.id.et_search_input_keyword);

	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		viewpager_search_content.setOffscreenPageLimit(2);
		viewpager_adapter = new SearchViewPagerAdapter(
				fragments, this, fragmentManager);
		viewpager_search_content.setAdapter(viewpager_adapter);

		// 以下是设置移动条的初始位置
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				tabWidth / 2, LinearLayout.LayoutParams.WRAP_CONTENT);
		iv_search_indicator.setLayoutParams(lp);

		// 初始搜索类型
		setSearchTitleColor(0);
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {

		// 搜索输入框内容监听
		et_search_input_keyword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (et_search_input_keyword.getText().toString().trim().length() > 0) {
					btn_clear_search_input.setVisibility(View.VISIBLE);
					btn_start_search.setText("搜索");
				} else {
					btn_clear_search_input.setVisibility(View.INVISIBLE);
					btn_start_search.setText("取消");
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		llyt_search_recently.setOnClickListener(new MyOnClickListener(0));
		llyt_search_hot.setOnClickListener(new MyOnClickListener(1));
		viewpager_search_content
				.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int index) {
						Animation animation = null;
						switch (index) {

						case 0:
							if (currIndex == 1) {
								animation = new TranslateAnimation(two, 0, 0, 0);
							}

							break;
						case 1:
							if (currIndex == 0) {
								animation = new TranslateAnimation(zero, one,
										0, 0);
							}

							break;

						}
						setSearchTitleColor(index);
						currIndex = index;
						animation.setFillAfter(true);// True:图片停在动画结束位置
						animation.setDuration(150);
						iv_search_indicator.startAnimation(animation);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});

		btn_start_search.setOnClickListener(this);
		btn_clear_search_input.setOnClickListener(this);
	}

	/**
	 * 
	 * @描述：点击事件
	 * @date：2014-6-23
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start_search:// 搜索按钮
			// 添加操作
			if (btn_start_search.getText().equals("搜索")) {
				// 打开搜索结果界面，将搜索关键字添加到数据库
				fragment_searchRecently.add2DB(et_search_input_keyword.getText().toString().trim());
				viewpager_adapter.notifyDataSetChanged();
				
				//打开搜索到的商品列表界面
				Intent intent_keyword = new Intent(SearchActivity.this,SearchGoodsListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("keyword", et_search_input_keyword.getText().toString().trim());
				intent_keyword.putExtras(bundle);
				startActivity(intent_keyword);
				overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
			} else if (btn_start_search.getText().equals("取消")) {
				// 关闭搜索界面
				finish();
				overridePendingTransition(
						R.anim.translate_horizontal_finish_in,
						R.anim.translate_horizontal_finish_out);
			}

			break;
		case R.id.btn_clear_search_input://清除输入
			et_search_input_keyword.setText("");
		default:
			break;
		}
	}

	/**
	 * 
	 * 描述：设置当前搜索类型的字体颜色
	 * 
	 * @param index
	 */
	private void setSearchTitleColor(int index) {
		switch (index) {
		case 0:
			tv_search_recently.setTextColor(getResources().getColor(
					R.color.color_mian));
			tv_search_hot.setTextColor(getResources().getColor(
					R.color.color_black));
			break;
		case 1:
			tv_search_recently.setTextColor(getResources().getColor(
					R.color.color_black));
			tv_search_hot.setTextColor(getResources().getColor(
					R.color.color_mian));
			break;
		}
	}

	/**
	 * 自定义点击事件（搜索）
	 * 
	 * @ClassName: MyOnClickListener
	 * 
	 */
	private class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			viewpager_search_content.setCurrentItem(index);
		}
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

	/*--------------------------------------滑动结束-----------------------------------*/

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate();
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v == null && mHelper != null)
			return mHelper.findViewById(id);
		return v;
	}

	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mHelper.getSwipeBackLayout();
	}

	@Override
	public void setSwipeBackEnable(boolean enable) {
		getSwipeBackLayout().setEnableGesture(enable);
	}

	@Override
	public void scrollToFinishActivity() {
		getSwipeBackLayout().scrollToFinishActivity();
	}

}
