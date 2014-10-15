package com.xiuman.xingduoduo.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.view.OnlyImageView;

/**
 * @描述：用户头像
 * @名称：HeadImgViewActivity.java
 * @author CSX
 * @日期：2014-10-12
 */
public class HeadImgViewActivity extends Base2Activity implements
		OnGestureListener {

	// 头像ImageView
	private OnlyImageView iv_head;
	// container

	/*-----------------------------ImagerLoader--------------------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*------------------------数据------------------------*/
	// 性别
	private boolean user_sex;
	// 头像地址
	private String user_head;
	// 手势
	private GestureDetector gestureScanner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_imgview);
		initData();
		findViewById();
		initUI();
		setListener();
		doGestureEvent();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.weiboitem_pic_loading) //
				// 在ImageView加载过程中显示图片
				.showImageOnLoading(R.drawable.onloading)
				.showImageForEmptyUri(R.drawable.onloading) // image连接地址为空时
				.showImageOnFail(R.drawable.onloading) // image加载失败
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();

		user_sex = getIntent().getExtras().getBoolean("user_sex");
		user_head = getIntent().getExtras().getString("user_head");
	}

	@Override
	protected void findViewById() {
		iv_head = (OnlyImageView) findViewById(R.id.image_view);
	}

	@Override
	protected void initUI() {
		imageLoader.displayImage(URLConfig.IMG_IP + user_head, iv_head,
				options, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						if (user_sex) {
							iv_head.setImageResource(R.drawable.ic_male);
						} else {
							iv_head.setImageResource(R.drawable.ic_female);
						}
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {

					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {

					}
				});
	}

	@Override
	protected void setListener() {
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

	/**
	 * 进行事件分发
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		HeadImgViewActivity.this.onTouchEvent(ev);
		// llyt_head_container.dispatchTouchEvent(ev);
		if (gestureScanner.onTouchEvent(ev)) {
			ev.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
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
