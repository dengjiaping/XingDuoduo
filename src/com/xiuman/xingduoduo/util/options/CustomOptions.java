package com.xiuman.xingduoduo.util.options;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xiuman.xingduoduo.R;

/**
 * @名称：CustomOptions.java
 * @描述：ImageLoader options
 * @author danding
 * @时间 2014-10-29
 */
public class CustomOptions {
	/**
	 * @描述：帖子详情图
	 * @return
	 * @时间 2014-10-29
	 */
	@SuppressWarnings("deprecation")
	public static DisplayImageOptions getOptions1() {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inSampleSize = 4;

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.onloading)
				.showImageForEmptyUri(R.drawable.onloading)
				// image连接地址为空时
				.showImageOnFail(R.drawable.onloading)
				// image加载失败
				.decodingOptions(bmpFactoryOptions)
				.cacheInMemory(true)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true)
				// 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		return options;
	}
	/**
	 * @描述：帖子列表图(商品列表)
	 * @return
	 * @时间 2014-10-29
	 */
	@SuppressWarnings("deprecation")
	public static DisplayImageOptions getOptions2() {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inSampleSize = 2;

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.onloadong_post) //
				.showImageOnLoading(R.drawable.onloading)
				// 在ImageView加载过程中显示图片
				.showImageForEmptyUri(R.drawable.onloading)
				// image连接地址为空时
				.showImageOnFail(R.drawable.onloading)
				// image加载失败
				// .resetViewBeforeLoading(false)
				.cacheInMemory(false)
				.decodingOptions(bmpFactoryOptions)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true)
				// 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
		return options;
	}
	/**
	 * @描述：用户头像图
	 * @return
	 * @时间 2014-10-29
	 */
	// options3论坛用户头像
	@SuppressWarnings("deprecation")
	public static DisplayImageOptions getOptions3() {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inSampleSize = 4;

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.onloading)
				.showImageForEmptyUri(R.drawable.ic_male)
				// image连接地址为空时
				.showImageOnFail(R.drawable.ic_male)
				// image加载失败
				.decodingOptions(bmpFactoryOptions).cacheInMemory(false)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(15))
				// 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		return options;
	}
}
