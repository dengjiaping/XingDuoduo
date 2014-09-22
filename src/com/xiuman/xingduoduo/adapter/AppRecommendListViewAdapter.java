package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.model.AppRecommend;

/**
 * @名称：AppRecommendListViewAdapter.java
 * @描述：应用推荐
 * @author danding 2014-9-22
 */
public class AppRecommendListViewAdapter extends BaseAdapter {

	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private ArrayList<AppRecommend> apps;

	public AppRecommendListViewAdapter(Context context,
			DisplayImageOptions options, ImageLoader imageLoader,
			ArrayList<AppRecommend> apps) {
		super();
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
		this.apps = apps;
	}

	@Override
	public int getCount() {
		return apps.size();
	}

	@Override
	public Object getItem(int position) {
		return apps.get(position);
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
			convertView = View.inflate(context, R.layout.item_app_recommend,
					null);
			holder.iv_app_head = (ImageView) convertView
					.findViewById(R.id.iv_app_head);
			holder.tv_app_name = (TextView) convertView
					.findViewById(R.id.tv_app_name);
			holder.tv_app_desc = (TextView) convertView
					.findViewById(R.id.tv_app_desc);
			holder.btn_download_app = (Button) convertView
					.findViewById(R.id.btn_download_app);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final AppRecommend app = apps.get(position);

		imageLoader.displayImage(app.getAppImageUrl(), holder.iv_app_head,
				options);
		holder.tv_app_name.setText(app.getAppName());
		holder.tv_app_desc.setText(app.getAppIntroduct());
		holder.btn_download_app.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse(app.getAppUrl());   
				intent.setData(content_url);  
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	static class ViewHolder {
		ImageView iv_app_head;
		TextView tv_app_desc;
		TextView tv_app_name;
		Button btn_download_app;
	}

}
