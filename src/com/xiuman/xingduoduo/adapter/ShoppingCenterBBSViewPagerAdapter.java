package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.testdata.Test;
import com.xiuman.xingduoduo.ui.activity.MainActivity;
import com.xiuman.xingduoduo.ui.activity.PostListActivity;

/**
 * @名称：ShoppingCenterBBSViewPagerAdapter.java
 * @描述：商城界面的热门话题
 * @author danding
 * @version 2014-6-9
 */
public class ShoppingCenterBBSViewPagerAdapter extends PagerAdapter implements
		OnClickListener {

	private MainActivity context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private ArrayList<View> views;
	private String imgUrl;
	private View view = null;

	/**
	 * 构造函数
	 * 
	 * @param ads
	 * @param ad_ivs
	 * @param context
	 * @param options
	 * @param imageLoader
	 */
	public ShoppingCenterBBSViewPagerAdapter(String imgUrl,
			MainActivity context, DisplayImageOptions options,
			ImageLoader imageLoader) {
		super();
		this.imgUrl = imgUrl;
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
		views = new ArrayList<View>();
		for (int i = 0; i < 2; i++) {
			View view = new View(context);
			views.add(view);
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView iv_ad = null;
		LinearLayout llyt_center_plate_1 = null;
		LinearLayout llyt_center_plate_2 = null;
		LinearLayout llyt_center_plate_3 = null;
		if (position == 0) {
			view = View.inflate(context, R.layout.item_ad_img, null);
			iv_ad = (ImageView) view.findViewById(R.id.iv_ad);
			iv_ad.setScaleType(ImageView.ScaleType.FIT_XY);
			// 切换
			imageLoader.displayImage(URLConfig.IMG_IP + imgUrl, iv_ad, options);
		}
		if (position == 1) {
			view = View.inflate(context, R.layout.include_center_bbs_plate,
					null);
			llyt_center_plate_1 = (LinearLayout) view
					.findViewById(R.id.llyt_center_plate_1);
			llyt_center_plate_2 = (LinearLayout) view
					.findViewById(R.id.llyt_center_plate_2);
			llyt_center_plate_3 = (LinearLayout) view
					.findViewById(R.id.llyt_center_plate_3);

		}

		views.set(position, view);
		container.addView(view);
		if (position == 0) {
			iv_ad.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					context.selectTab(2);
				}
			});
		}
		if (position == 1) {
			llyt_center_plate_1.setOnClickListener(this);
			llyt_center_plate_2.setOnClickListener(this);
			llyt_center_plate_3.setOnClickListener(this);
		}

		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView(views.get(position));
	}

	@Override
	public void onClick(View v) {
		BBSPlate plate = new BBSPlate();
		switch (v.getId()) {
		case R.id.llyt_center_plate_1:
			plate = Test.getBBSPlates().get(0);
			break;
		case R.id.llyt_center_plate_2:
			plate = Test.getBBSPlates().get(1);
			break;
		case R.id.llyt_center_plate_3:
			plate = Test.getBBSPlates().get(2);
			break;
		}

		Intent intent = new Intent(context, PostListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("bbs_plate", plate);
		intent.putExtras(bundle);
		context.startActivity(intent);
		context.overridePendingTransition(R.anim.translate_horizontal_start_in,
				R.anim.translate_horizontal_start_out);
	}
}
