package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.Classify;

/**
 * @名称：ClassifyListViewAdapter.java
 * @描述：一级分类Adapter
 * @author danding
 * @时间 2014-10-16
 */
public class ClassifyListViewAdapter extends BaseAdapter {

	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private ArrayList<Classify> classifies;
	int width , height;
	public ClassifyListViewAdapter(Context context,
			DisplayImageOptions options, ImageLoader imageLoader,
			ArrayList<Classify> classifies) {
		super();
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
		this.classifies = classifies;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.icon_acatogory_1);
		width = bmp.getWidth();
		height = bmp.getHeight();
	}

	@Override
	public int getCount() {
		return classifies.size();
	}

	@Override
	public Object getItem(int position) {
		return classifies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_classify, null);
			holder = new ViewHolder();
			holder.iv_classify = (ImageView) convertView
					.findViewById(R.id.iv_classify);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
			holder.iv_classify.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(URLConfig.IMG_IP
				+ classifies.get(position).getLogoPath(), holder.iv_classify,
				options);
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_classify;
	}
}
