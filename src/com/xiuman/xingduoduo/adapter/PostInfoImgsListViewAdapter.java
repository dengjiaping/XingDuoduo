package com.xiuman.xingduoduo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.util.SizeUtil;

/**
 * @名称：PostInfoImgsListViewAdapter.java
 * @描述：帖子详情里的图片Adapter
 * @author danding 2014-8-16
 */
public class PostInfoImgsListViewAdapter extends BaseAdapter {

	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private List<String> imgs;
	private Handler handler;
	// 屏幕宽度
	int width;

	public PostInfoImgsListViewAdapter(Context context,
			DisplayImageOptions options, ImageLoader imageLoader,
			List<String> imgs, Handler handler) {
		super();
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
		this.imgs = imgs;
		this.handler = handler;
		width = MyApplication.getInstance().getScreenWidth();
	}

	@Override
	public int getCount() {
		return imgs.size();
	}

	@Override
	public Object getItem(int position) {
		return imgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.item_postinfo_img, null);
		final ImageView iv_item_postinfo_img_item = (ImageView) convertView
				.findViewById(R.id.iv_item_postinfo_img_item);

		imageLoader.displayImage(URLConfig.PRIVATE_IMG_IP + imgs.get(position),
				iv_item_postinfo_img_item, options, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {

					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {

						int img_width = width - SizeUtil.dip2px(context, 20);
						if (img_width > arg2.getWidth()) {
							int img_height = (int) (img_width
									/ (arg2.getWidth() * 1.0) * arg2
									.getHeight());
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
									img_width, img_height);
							iv_item_postinfo_img_item.setLayoutParams(params);
						}
						if (position == imgs.size() - 1) {
							Message msg = Message.obtain();
							msg.obj = AppConfig.FLUSH_IMG_ADAPTER;
							handler.sendMessage(msg);
						}
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {

					}
				});

		return convertView;
	}
}
