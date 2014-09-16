package com.xiuman.xingduoduo.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.model.Ad;

/**
 * 
 * @名称：ShoppingCenterAdViewPagerAdapter.java 
 * @描述：商城界面广告Adapter
 * @author danding
 * @version 2014-6-9
 */
public class ShoppingCenterAdViewPagerAdapter extends PagerAdapter {

	private List<Ad> ads;
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
	public ShoppingCenterAdViewPagerAdapter(List<Ad> ads,
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
		// imageLoader.displayImage(ads.get(position).getAd_poster_url(), iv_ad,
		// options);
		iv_ad.setImageResource(R.drawable.bg_center_ad_loading);
		container.addView(iv_ad);

		iv_ad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

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
