package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.view.OnlyImageView;
/**
 * @名称：ImageViewPagerAdapter.java
 * @描述：帖子图片预览
 * @author danding
 * 2014-10-11
 */
public class ImageViewPagerAdapter extends PagerAdapter {
	/**
	 * 图片列表
	 */
	private Context context;
	//分集图片地址
	private ArrayList<String> imgs;
	

	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;

	public Bitmap bitmap;
	

	public ImageViewPagerAdapter(Context context, ArrayList<String> imgs,
			DisplayImageOptions options, ImageLoader imageLoader) {
		super();
		this.context = context;
		this.imgs = imgs;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return imgs.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = View.inflate(context, R.layout.item_viewpager_post_img, null);

		final OnlyImageView imageview = (OnlyImageView) view
				.findViewById(R.id.image_view);

		//ImageLoader 加载图片
		imageLoader.displayImage(URLConfig.PRIVATE_IMG_IP+imgs.get(position), imageview,options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
			}
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			}
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				bitmap = arg2;
			}
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
			}
		});
		
		container.addView(imageview);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View view = (View) object;
		container.removeView(view);
	}

}
