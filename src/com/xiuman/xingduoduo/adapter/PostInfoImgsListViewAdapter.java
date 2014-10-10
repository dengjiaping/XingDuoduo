package com.xiuman.xingduoduo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;

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
	int width;

	public PostInfoImgsListViewAdapter(Context context,
			DisplayImageOptions options, ImageLoader imageLoader,
			List<String> imgs) {
		super();
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
		this.imgs = imgs;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_postinfo_img,
					null);
			holder = new ViewHolder();
			holder.iv_item_postinfo_img_item = (ImageView) convertView
					.findViewById(R.id.iv_item_postinfo_img_item);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(URLConfig.PRIVATE_IMG_IP+imgs.get(position),
				holder.iv_item_postinfo_img_item, options,new SimpleImageLoadingListener());

		
		return convertView;
	}
	static class ViewHolder {
		public ImageView iv_item_postinfo_img_item;
	}
}
