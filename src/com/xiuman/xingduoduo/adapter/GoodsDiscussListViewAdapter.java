package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
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
	
	public GoodsDiscussListViewAdapter(Context context,
			ArrayList<GoodsDiscuss> discusses) {
		super();
		this.context = context;
		this.discusses = discusses;
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
		ViewHolder holder;
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
		
		holder.iv_item_discuss_head.setImageResource(R.drawable.bg_head);
		holder.tv_item_discuss_index.setText(position+1+"、");
		holder.tv_item_discuss_name.setText(discuss.getUserName());
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
