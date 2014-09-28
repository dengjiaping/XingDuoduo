package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.GoodsDiscuss;
import com.xiuman.xingduoduo.util.TimeUtil;
/**
 * @名称：GoodsDiscussListViewAdapter.java
 * @描述：商品评论Adapter
 * @author danding
 * 2014-8-7
 */
public class GoodsDiscussListViewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<GoodsDiscuss> discusses;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	
	public GoodsDiscussListViewAdapter(Context context,
			ArrayList<GoodsDiscuss> discusses, DisplayImageOptions options,
			ImageLoader imageLoader) {
		super();
		this.context = context;
		this.discusses = discusses;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return discusses.size();
	}

	@Override
	public Object getItem(int position) {
		return discusses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_goods_discuss, null);
			holder = new ViewHolder();
			
			holder.tv_item_discuss_index = (TextView) convertView.findViewById(R.id.tv_item_discuss_index);
			holder.tv_item_discuss_name = (TextView) convertView.findViewById(R.id.tv_item_discuss_name);
			holder.tv_item_discuss_time = (TextView) convertView.findViewById(R.id.tv_item_discuss_time);
			holder.tv_item_discuss_content = (TextView) convertView.findViewById(R.id.tv_item_discuss_content);
			holder.iv_item_discuss_head = (ImageView) convertView.findViewById(R.id.iv_item_discuss_head);
			
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		GoodsDiscuss discuss = discusses.get(position);
		
		imageLoader.displayImage(URLConfig.IMG_IP+discuss.getImagehead(), holder.iv_item_discuss_head, options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				holder.iv_item_discuss_head.setImageResource(R.drawable.bg_head);
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
		holder.tv_item_discuss_index.setText(position+1+"、");
		holder.tv_item_discuss_name.setText(discuss.getNickname());
		holder.tv_item_discuss_time.setText(TimeUtil.getTimeStr(
				TimeUtil.strToDate(discuss.getCreateTime()), new Date()));
		holder.tv_item_discuss_content.setText(discuss.getContent());
		
		return convertView;
	}
	
	class ViewHolder{
		TextView tv_item_discuss_index;
		TextView tv_item_discuss_name;
		TextView tv_item_discuss_time;
		TextView tv_item_discuss_content;
		ImageView iv_item_discuss_head;
	}

}
