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
	private List<ImageView> ad_ivs;
	private ImageView iv_ad;
	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;

	/**
	 * @param ads
	 * @param ad_ivs
	 * @param context
	 * @param options
	 * @param imageLoader
	 */
	public BBSAdViewPagerAdapter(List<BBSPost> ads, List<ImageView> ad_ivs,
			Context context, DisplayImageOptions options,
			ImageLoader imageLoader) {
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
		iv_ad.setScaleType(ImageView.ScaleType.CENTER_CROP);
		iv_ad.setTag(position);
		// 切换
		BBSPost post = ads.get(position);

		// List<String> post_imgs = new ArrayList<String>();
		// post_imgs = HtmlTag.match(post.contentHtml, "img", "src");
		// Mylog.i("广告图片地址", post_imgs.get(0));
		imageLoader.displayImage(post.getPostImgs().get(0), iv_ad, options);
		// iv_ad.setImageResource(R.drawable.bg_center_ad_loading);
		container.addView(iv_ad);

		iv_ad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Object obj = ads.get(position);
				if (obj instanceof BBSPost) {
					BBSPost postinfo = (BBSPost) obj;
					Intent intent = new Intent(context, PostInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("postinfo_starter", postinfo);
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

		return iv_ad;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		iv_ad = ad_ivs.get(position);

		container.removeView(iv_ad);
	}

}
