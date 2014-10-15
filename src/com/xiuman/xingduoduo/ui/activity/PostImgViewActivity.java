package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.PostImageViewPagerAdapter;
import com.xiuman.xingduoduo.model.PostImg;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.view.indicator.CirclePageIndicator;

/**
 * @名称：PostImgViewActivity.java
 * @描述：帖子详情图片预览
 * @author danding 2014-10-11
 */
public class PostImgViewActivity extends Base2Activity implements
		OnGestureListener {

	/*-------------------------------组件-----------------------------------------*/
	// ViewPager
	private ViewPager viewpager;
	// Indicator
	private CirclePageIndicator indicator;
	// 手势
	private GestureDetector gestureScanner;

	// adapter
	private PostImageViewPagerAdapter adapter;

	/*---------------------------------数据----------------------------------------*/
	// 图片列表
	private ArrayList<PostImg> imgs = new ArrayList<PostImg>();
	// 从上级界面接收到的当前打开的图片的位置
	private int current = 0;

	/*-----------------------------ImagerLoader--------------------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_img_view);
		initData();
		findViewById();
		initUI();
		setListener();
		doGestureEvent();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	protected void initData() {
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.weiboitem_pic_loading) //
				// 在ImageView加载过程中显示图片
				.showImageOnLoading(R.drawable.onloading)
				.showImageForEmptyUri(R.drawable.onloading) // image连接地址为空时
				.showImageOnFail(R.drawable.onloading) // image加载失败
				.cacheInMemory(false) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();

		Bundle bundle = getIntent().getExtras();
		imgs = (ArrayList<PostImg>) bundle.getSerializable("imgs");
		current = bundle.getInt("current");

	}

	@Override
	protected void findViewById() {
		viewpager = (ViewPager) findViewById(R.id.viewpager_post_img);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator_post_img);
	}

	@Override
	protected void initUI() {
		adapter = new PostImageViewPagerAdapter(this, imgs, options, imageLoader);
		viewpager.setAdapter(adapter);

		indicator.setViewPager(viewpager);
		viewpager.setCurrentItem(current);
		indicator.setCurrentItem(current);
	}

	@Override
	protected void setListener() {
		viewpager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						super.onPageSelected(position);
						indicator.setCurrentItem(position);
					}
				});
	}

	public void doGestureEvent() {
		gestureScanner = new GestureDetector(this, this);
		gestureScanner
				.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
					public boolean onSingleTapConfirmed(MotionEvent e) {

						finish();
						return false;
					}

					@Override
					public boolean onDoubleTap(MotionEvent e) {
						return false;
					}

					@Override
					public boolean onDoubleTapEvent(MotionEvent e) {
						return false;
					}
				});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		imageLoader.stop();
		imageLoader.clearMemoryCache();
	}

	/**
	 * 进行事件分发
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		PostImgViewActivity.this.onTouchEvent(ev);
		viewpager.dispatchTouchEvent(ev);
		if (gestureScanner.onTouchEvent(ev)) {
			ev.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}
}
