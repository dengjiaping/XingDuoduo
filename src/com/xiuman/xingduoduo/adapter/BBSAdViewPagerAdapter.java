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
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.ui.activity.PostInfoActivity;

/**
 * 
 * @名称：CommunicationAdViewPagerAdapter.java
 * @描述：交流区广告栏
 * @author danding
 * @version
 * @date：2014-7-28
 */
public class BBSAdViewPagerAdapter extends PagerAdapter {

	private List<BBSPost> ads = new ArrayList<BBSPost>();
	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private ArrayList<View> views;

	/**
	 * @param ads
	 * @param ad_ivs
	 * @param context
	 * @param options
	 * @param imageLoader
	 */
	public BBSAdViewPagerAdapter(List<BBSPost> ads, Context context,
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

		views.set(position, view);
		// 切换
		BBSPost post = ads.get(position);
		imageLoader.displayImage(URLConfig.PRIVATE_IMG_IP
				+ post.getImgList().get(0).getImgurl(), iv_ad, options);
		container.addView(view);

		iv_ad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Object obj = ads.get(position);
				if (obj instanceof BBSPost) {
					BBSPost postinfo = (BBSPost) obj;
					Intent intent = new Intent(context, PostInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("postinfo_starter", postinfo.getId());
					// 版块id
					bundle.putString("forumId", "10");
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

		// iv_ad = ad_ivs.get(position);

		container.removeView(views.get(position));
	}

}
