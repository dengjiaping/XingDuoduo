package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

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
import com.xiuman.xingduoduo.model.Ad;
import com.xiuman.xingduoduo.ui.activity.ClassifyActivity;
import com.xiuman.xingduoduo.ui.activity.GoodsActivity;
import com.xiuman.xingduoduo.ui.activity.PostInfoActivity;

/**
 * @名称：ShoppingCenterAdViewPagerAdapter.java
 * @描述：商城界面广告Adapter
 * @author danding
 * @version 2014-6-9
 */
public class ShoppingCenterAdViewPagerAdapter extends PagerAdapter {

	private ArrayList<Ad> ads;
	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private ArrayList<View> views;
	/**
	 * 构造函数
	 * @param ads
	 * @param ad_ivs
	 * @param context
	 * @param options
	 * @param imageLoader
	 */
	public ShoppingCenterAdViewPagerAdapter(ArrayList<Ad> ads, Context context,
			DisplayImageOptions options, ImageLoader imageLoader) {
		super();
		this.ads = ads;
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
		views = new ArrayList<View>();
		for (int i = 0; i < ads.size(); i++) {
			View view = new View(context);
			views.add(view);
		}
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
		View view = View.inflate(context, R.layout.item_ad_img, null);
		ImageView iv_ad = (ImageView) view.findViewById(R.id.iv_ad);
		iv_ad.setScaleType(ImageView.ScaleType.FIT_XY);
		views.set(position, view);
		// 切换
		imageLoader.displayImage(URLConfig.IMG_IP
				+ ads.get(position).getLogoPath(), iv_ad, options);
		container.addView(view);

		iv_ad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Ad ad = ads.get(position);
				if (ad.getGdCategory().equals("1")) {
					Intent intent = new Intent(context, GoodsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("goods_id", ad.getHomeId());
					intent.putExtras(bundle);
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}else if(ad.getGdCategory().equals("2")){
					Intent intent = new Intent(context,ClassifyActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("classify_id", ad.getHomeId());
					bundle.putSerializable("classify_name", ad.getName());
					intent.putExtras(bundle);
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}else if(ad.getGdCategory().equals("3")){
					Intent intent = new Intent(context,PostInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("postinfo_starter", ad.getHomeId());
					bundle.putSerializable("forumId", ad.getIntroduction());
					intent.putExtras(bundle);
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}
			}
		});

		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView(views.get(position));
	}
}
