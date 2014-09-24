package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.model.BBSPost;
/**
 * @名称：PlateStickPostListViewAdapter.java
 * @描述：置顶帖子的Adapter
 * @author danding
 * 2014-8-11
 */
public class PlateStickPostListViewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<BBSPost> stick_posts;
	
	
	public PlateStickPostListViewAdapter(Context context,
			ArrayList<BBSPost> stick_posts) {
		super();
		this.context = context;
		this.stick_posts = stick_posts;
	}

	@Override
	public int getCount() {
		return stick_posts.size();
	}

	@Override
	public Object getItem(int position) {
		return stick_posts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_bbs_plate_stick_post, null);
			holder.iv_item_bbs_plate_stick_post_poster = (ImageView) convertView.findViewById(R.id.iv_item_bbs_plate_stick_post_poster);
			holder.tv_item_bbs_plate_stick_post_title = (TextView) convertView.findViewById(R.id.tv_item_bbs_plate_stick_post_title);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		BBSPost stick_post = stick_posts.get(position);
		
		holder.iv_item_bbs_plate_stick_post_poster.setImageResource(R.drawable.icon_stick_post);
		holder.tv_item_bbs_plate_stick_post_title.setText(stick_post.getTitle());
		
		return convertView;
	}

	static class ViewHolder{
		ImageView iv_item_bbs_plate_stick_post_poster;
		TextView tv_item_bbs_plate_stick_post_title;
	}
}
