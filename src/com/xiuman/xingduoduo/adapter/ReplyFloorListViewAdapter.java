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
import com.xiuman.xingduoduo.model.PostReply;
import com.xiuman.xingduoduo.util.TimeUtil;

/**
 * @名称：ReplyFloorListViewAdapter.java
 * @描述：回复楼层的Adapter(在帖子信息界面最多展示2条回复楼层的回复，在回复列表界面展示全部)
 * @author danding 2014-8-18
 */
public class ReplyFloorListViewAdapter extends BaseAdapter {

	private Context context;
	// 回复集合
	private ArrayList<PostReply> replys;
	// 是否展开全部回复
	private boolean isAll;

	public ReplyFloorListViewAdapter(Context context,
			ArrayList<PostReply> replys, boolean isAll) {
		super();
		this.context = context;
		this.replys = replys;
		this.isAll = isAll;

	}

	@Override
	public int getCount() {
		if (isAll) {// true展示全部
			return replys.size();
		} else if (replys.size() >= 2) {// false展示部分2（多于2）
			return 2;
		} else {// 少于2
			return replys.size();
		}
	}

	@Override
	public Object getItem(int position) {
		return replys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.item_postinfo_reply_floor, null);
			holder = new ViewHolder();

			holder.tv_item_replyinfo_reply_floor_username = (TextView) convertView
					.findViewById(R.id.tv_item_replyinfo_reply_floor_username);
			holder.tv_item_replyinfo_reply_floor_content = (TextView) convertView
					.findViewById(R.id.tv_item_replyinfo_reply_floor_content);
			holder.tv_item_replyinfo_reply_floor_time = (TextView) convertView
					.findViewById(R.id.tv_item_replyinfo_reply_floor_time);
			holder.iv_item_replyinfo_reply_floor_starter = (ImageView) convertView
					.findViewById(R.id.iv_item_replyinfo_reply_floor_starter);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PostReply reply = replys.get(position);

		holder.tv_item_replyinfo_reply_floor_username.setText(reply
				.getReply_user().getUserName());
		holder.tv_item_replyinfo_reply_floor_content.setText(reply
				.getReply_content());
		holder.tv_item_replyinfo_reply_floor_time.setText(TimeUtil.getTimeStr(
				TimeUtil.strToDate(reply.getReply_time()), new Date()));
		if (reply.isStarter()) {// 如果回复用户是楼主则加标记
			holder.iv_item_replyinfo_reply_floor_starter
					.setVisibility(View.VISIBLE);
			holder.iv_item_replyinfo_reply_floor_starter
					.setImageResource(R.drawable.bg_post_starter);
		} else {
			holder.iv_item_replyinfo_reply_floor_starter
					.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	static class ViewHolder {
		// 用户名
		TextView tv_item_replyinfo_reply_floor_username;
		// 楼主
		ImageView iv_item_replyinfo_reply_floor_starter;
		// 回复内容
		TextView tv_item_replyinfo_reply_floor_content;
		// 时间
		TextView tv_item_replyinfo_reply_floor_time;
	}

}
