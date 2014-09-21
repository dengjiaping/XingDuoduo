package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.GoodsOne;
import com.xiuman.xingduoduo.ui.activity.GoodsActivity;

/**
 * 
 * @名称：ShoppingCenterAdViewPagerAdapter.java
 * @描述：商城界面广告Adapter
 * @author danding
 * @version 2014-6-9
 */
public class ShoppingCenterAdViewPagerAdapter extends PagerAdapter {

	private ArrayList<GoodsOne> ads;
	private List<ImageView> ad_ivs;
	private ImageView iv_ad;
	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;

	/**
	 * 构造函数
	 * 
	 * @param ads
	 * @param ad_ivs
	 * @param context
	 * @param options
	 * @param imageLoader
	 */
	public ShoppingCenterAdViewPagerAdapter(ArrayList<GoodsOne> ads,
			List<ImageView> ad_ivs, Context context,
			DisplayImageOptions options, ImageLoader imageLoader) {
		super();
		this.ads = ads;
		this.ad_ivs = ad_ivs;
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return ads.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		iv_ad = ad_ivs.get(position);
		iv_ad.setAdjustViewBounds(true);
		iv_ad.setScaleType(ImageView.ScaleType.FIT_XY);
		iv_ad.setTag(position);
		// 切换
		imageLoader.displayImage(URLConfig.IMG_IP
				+ ads.get(position).getSourceImagePath(), iv_ad, options);
		container.addView(iv_ad);

		iv_ad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GoodsOne goods_one = ads.get(position);
				Intent intent = new Intent(
						context,
						GoodsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("goods_one", goods_one);
				bundle.putSerializable("goods_id",
						goods_one.getId());
				bundle.putInt("pic_tag", 1);
				intent.putExtras(bundle);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
			}
		});

		return iv_ad;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		iv_ad = ad_ivs.get(position);

		container.removeView(iv_ad);
	}
}
