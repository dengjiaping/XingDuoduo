package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.model.ImagePath;

/**
 * 
 * @名称：GoodsImgsViewPagerAdapter.java
 * @描述：商品详情页图片列表
 * @author danding
 * @version
 * @date：2014-6-26
 */
public class GoodsImgsViewPagerAdapter extends PagerAdapter {

	private ArrayList<ImagePath> img_urls;// 图片地址
	private List<ImageView> img_ivs;// IamgeViews
	private ImageView iv_img;
	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;

	/**
	 * 构造函数
	 * 
	 * @param img_urls
	 * @param img_ivs
	 * @param context
	 * @param options
	 * @param imageLoader
	 */
	public GoodsImgsViewPagerAdapter( ArrayList<ImagePath> img_urls,
			List<ImageView> img_ivs, Context context,
			DisplayImageOptions options, ImageLoader imageLoader) {
		super();
		this.img_urls = img_urls;
		this.img_ivs = img_ivs;
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return img_urls.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		iv_img = img_ivs.get(position);
		iv_img.setAdjustViewBounds(true);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		iv_img.setLayoutParams(params);
		iv_img.setScaleType(ImageView.ScaleType.FIT_XY);
		iv_img.setTag(position);
		// 测试数据，添操作
		imageLoader.displayImage(img_urls.get(position).toString(),
				iv_img, options);
		// iv_img.setImageResource(R.drawable.bg_center_ad_loading);
		container.addView(iv_img);
		return iv_img;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		iv_img = img_ivs.get(position);

		container.removeView(iv_img);
	}

}
