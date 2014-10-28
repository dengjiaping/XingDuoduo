package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.GoodsDiscuss;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.view.CircleImageView;

/**
 * @名称：GoodsDiscussListViewAdapter.java
 * @描述：商品评论Adapter
 * @author danding 2014-8-7
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
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_goods_discuss,
					null);
			holder = new ViewHolder();

			holder.tv_item_discuss_index = (TextView) convertView
					.findViewById(R.id.tv_item_discuss_index);
			holder.tv_item_discuss_name = (TextView) convertView
					.findViewById(R.id.tv_item_discuss_name);
			holder.tv_item_discuss_time = (TextView) convertView
					.findViewById(R.id.tv_item_discuss_time);
			holder.tv_item_discuss_content = (TextView) convertView
					.findViewById(R.id.tv_item_discuss_content);
			holder.iv_item_discuss_head = (CircleImageView) convertView
					.findViewById(R.id.iv_item_discuss_head);
			holder.ratingbar_item = (RatingBar) convertView
					.findViewById(R.id.ratingbar_item);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		GoodsDiscuss discuss = discusses.get(position);

		imageLoader.displayImage(URLConfig.IMG_IP + discuss.getHead_image(),
				holder.iv_item_discuss_head, options);
		holder.ratingbar_item.setRating((discuss.getDeliverySpeed()+discuss.getServiceAttitude()+discuss.getGoodsQuality())/3);
		holder.tv_item_discuss_index.setText(position + 1 + "、");
		holder.tv_item_discuss_name.setText(discuss.getNickname());
		if(discuss.getNickname()==null){
			holder.tv_item_discuss_name.setText("***");
		}
		holder.tv_item_discuss_time.setText(TimeUtil.getTimeStr(
				TimeUtil.strToDate(discuss.getCreateTime()), new Date()));
		holder.tv_item_discuss_content.setText(discuss.getContent());

		return convertView;
	}

	class ViewHolder {
		TextView tv_item_discuss_index;
		TextView tv_item_discuss_name;
		TextView tv_item_discuss_time;
		TextView tv_item_discuss_content;
		RatingBar ratingbar_item;
		CircleImageView iv_item_discuss_head;
	}

}
