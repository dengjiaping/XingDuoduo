package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.BClassify;

/**
 * @名称：ClassifyGridviewAdapter.java
 * @描述：商品分类首页适配器
 * @author danding
 * @version 2014-6-5
 */
public class ClassifyGridviewAdapter extends BaseAdapter {
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private ArrayList<BClassify> b_classifies;
	private Context context;
	private int width;
	public ClassifyGridviewAdapter(DisplayImageOptions options,
			ImageLoader imageLoader, ArrayList<BClassify> b_classifies,
			Context context,int width) {
		super();
		this.options = options;
		this.imageLoader = imageLoader;
		this.b_classifies = b_classifies;
		this.context = context;
		this.width = width;
	}

	@Override
	public int getCount() {
		return b_classifies.size();
	}

	@Override
	public Object getItem(int position) {
		return b_classifies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.item_gridview_frg_classify, null);

			holder.iv_classify_poster = (ImageView) convertView
					.findViewById(R.id.iv_classify_poster);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (int) (width/202.0*236));
			holder.iv_classify_poster.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(URLConfig.IMG_IP
				+ b_classifies.get(position).getLogoPath(),
				holder.iv_classify_poster, options);
		return convertView;
	}

	/**
	 * 
	 * 名称：ClassifyGridviewAdapter.java 描述：性能优化
	 * 
	 * @author danding
	 * @version 2014-6-6
	 */
	public static class ViewHolder {
		// 分类海报
		ImageView iv_classify_poster;

	}
}
