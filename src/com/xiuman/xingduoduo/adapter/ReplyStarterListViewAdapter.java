package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.BBSPostReply;
import com.xiuman.xingduoduo.ui.activity.HeadImgViewActivity;
import com.xiuman.xingduoduo.ui.activity.PostImgViewActivity;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.view.CircleImageView;

/**
 * @名称：PostReplyListViewAdapter.java
 * @描述：回复楼主的帖子Adapter
 * @author danding 2014-8-16
 */
public class ReplyStarterListViewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<BBSPostReply> replys;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;

	public ReplyStarterListViewAdapter(Context context,
			ArrayList<BBSPostReply> replys, DisplayImageOptions options,
			ImageLoader imageLoader) {
		super();
		this.context = context;
		this.replys = replys;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return replys.size();
	}

	@Override
	public Object getItem(int position) {
		return replys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (position == 0) {
			View view = View.inflate(context,
					R.layout.include_postinfo_container, null);
			final CircleImageView iv_postinfo_starter_head = (CircleImageView) view
					.findViewById(R.id.iv_postinfo_starter_head);
			ImageView iv_postinfo_starter_sex = (ImageView) view
					.findViewById(R.id.iv_postinfo_starter_sex);
			ImageView iv_postinfo_tag = (ImageView) view
					.findViewById(R.id.iv_postinfo_tag);
			TextView tv_postinfo_starter_name = (TextView) view
					.findViewById(R.id.tv_postinfo_starter_name);
			TextView tv_postinfo_starter_time = (TextView) view
					.findViewById(R.id.tv_postinfo_starter_time);
			TextView tv_postinfo_starter_content = (TextView) view
					.findViewById(R.id.tv_postinfo_starter_content);
			TextView tv_postinfo_starter_title = (TextView) view
					.findViewById(R.id.tv_postinfo_starter_title);
			Button btn_postinfo_starter_reply = (Button) view
					.findViewById(R.id.btn_postinfo_starter_reply);
			ListView lv_postinfo_starter_imgs = (ListView) view
					.findViewById(R.id.lv_postinfo_starter_imgs);
			final BBSPostReply postinfo_starter = replys.get(position);
			// 头像
			imageLoader.displayImage(
					URLConfig.IMG_IP + postinfo_starter.getAvatar(),
					iv_postinfo_starter_head, options,
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {
							if (postinfo_starter.isSex()) {
								iv_postinfo_starter_head
										.setImageResource(R.drawable.ic_male);
							} else {
								iv_postinfo_starter_head
										.setImageResource(R.drawable.ic_female);
							}
						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {

						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {

						}
					});
			// 性别
			if (postinfo_starter.isSex()) {
				iv_postinfo_starter_sex.setImageResource(R.drawable.sex_male);
			} else {
				iv_postinfo_starter_sex.setImageResource(R.drawable.sex_female);
			}
			tv_postinfo_starter_name.setText(postinfo_starter.getNickname());
			if (postinfo_starter.getNickname() == null) {
				tv_postinfo_starter_name
						.setText(postinfo_starter.getUsername());
			}
			tv_postinfo_starter_title.setText(postinfo_starter.getTitle());
			tv_postinfo_starter_content.setText(postinfo_starter.getContent());
			tv_postinfo_starter_time.setText(TimeUtil.getTimeStr(
					TimeUtil.strToDate(postinfo_starter.getCreateTime()),
					new Date()));
			//
			PostInfoImgsListViewAdapter adapter_img = new PostInfoImgsListViewAdapter(
					context, options, imageLoader,
					postinfo_starter.getImgList());
			if(postinfo_starter.getImgList()!=null){
				lv_postinfo_starter_imgs.setAdapter(adapter_img);
			}

			
			lv_postinfo_starter_imgs
			.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(context,
							PostImgViewActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("current", position);
					bundle.putSerializable("imgs",
							postinfo_starter.getImgList());
					intent.putExtras(bundle);

					context.startActivity(intent);
					((Activity) context).overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);

				}
			});
			
			iv_postinfo_starter_head
			.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							HeadImgViewActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("user_head", postinfo_starter.getAvatar());
					bundle.putBoolean("user_sex", postinfo_starter.isSex());
					intent.putExtras(bundle);
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}
			});
			
			return view;
		} else {
			View view2 = View.inflate(context,
					R.layout.item_postinfo_reply_starter, null);
			holder = new ViewHolder();
			holder.iv_item_postinfo_reply_head = (CircleImageView) view2
					.findViewById(R.id.iv_item_postinfo_reply_head);
			holder.iv_item_postinfo_reply_sex = (ImageView) view2
					.findViewById(R.id.iv_item_postinfo_reply_sex);
			holder.tv_item_postinfo_reply_name = (TextView) view2
					.findViewById(R.id.tv_item_postinfo_reply_name);
			holder.tv_item_postinfo_reply_time = (TextView) view2
					.findViewById(R.id.tv_item_postinfo_reply_time);
			holder.tv_item_postinfo_reply_content = (TextView) view2
					.findViewById(R.id.tv_item_postinfo_reply_content);
			holder.tv_item_postinfo_reply_floor = (TextView) view2
					.findViewById(R.id.tv_item_postinfo_reply_floor);
			holder.btn_item_postinfo_starter_reply = (Button) view2
					.findViewById(R.id.btn_item_postinfo_starter_reply);

			final BBSPostReply reply = replys.get(position);
			// 头像
			imageLoader.displayImage(URLConfig.IMG_IP + reply.getAvatar(),
					holder.iv_item_postinfo_reply_head, options,
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {
							if (reply.isSex()) {
								holder.iv_item_postinfo_reply_head
										.setImageResource(R.drawable.ic_male);
							} else {
								holder.iv_item_postinfo_reply_head
										.setImageResource(R.drawable.ic_female);
							}
						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {

						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {

						}
					});
			if (reply.isSex()) {
				holder.iv_item_postinfo_reply_sex
						.setImageResource(R.drawable.sex_male);
			} else {
				holder.iv_item_postinfo_reply_sex
						.setImageResource(R.drawable.sex_female);
			}

			holder.tv_item_postinfo_reply_name.setText(reply.getNickname());
			if (reply.getNickname() == null) {
				holder.tv_item_postinfo_reply_name.setText(reply.getUsername());
			}
			holder.tv_item_postinfo_reply_floor.setText(position + "楼");
			holder.tv_item_postinfo_reply_time.setText(TimeUtil.getTimeStr(
					TimeUtil.strToDate(reply.getCreateTime()), new Date()));
			holder.tv_item_postinfo_reply_content.setText(Html.fromHtml(reply
					.getContent()));

			holder.btn_item_postinfo_starter_reply
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

						}
					});

			holder.iv_item_postinfo_reply_head
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(context,
									HeadImgViewActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("user_head", reply.getAvatar());
							bundle.putBoolean("user_sex", reply.isSex());
							intent.putExtras(bundle);
							context.startActivity(intent);
							((Activity) context).overridePendingTransition(
									R.anim.translate_horizontal_start_in,
									R.anim.translate_horizontal_start_out);
						}
					});

			return view2;
		}
	}

	/**
	 * @名称：PostReplyListViewAdapter.java
	 * @描述：优化
	 * @author danding 2014-8-16
	 */
	static class ViewHolder {
		// 头像
		CircleImageView iv_item_postinfo_reply_head;
		// 性别
		ImageView iv_item_postinfo_reply_sex;
		// 用户名
		TextView tv_item_postinfo_reply_name;
		// 时间
		TextView tv_item_postinfo_reply_time;
		// 楼层
		TextView tv_item_postinfo_reply_floor;
		// 回复按钮
		Button btn_item_postinfo_starter_reply;
		// 回复内容
		TextView tv_item_postinfo_reply_content;
		// 回复楼层（contanier）
		// LinearLayout llyt_item_postinfo_starter_reply_floor_container;
		// // 楼层1
		// LinearLayout llyt_item_postinfo_starter_reply_floor_1;
		// // 用户名
		// TextView tv_item_replyinfo_reply_floor_username1;
		// // 楼主标记
		// ImageView iv_item_replyinfo_reply_floor_starter1;
		// // 回复内容
		// TextView tv_item_replyinfo_reply_floor_content1;
		// // 恢复时间
		// TextView tv_item_replyinfo_reply_floor_time1;
		// // 楼层2
		// LinearLayout llyt_item_postinfo_starter_reply_floor_2;
		// // 用户名
		// TextView tv_item_replyinfo_reply_floor_username2;
		// // 楼主标记
		// ImageView iv_item_replyinfo_reply_floor_starter2;
		// // 回复内容
		// TextView tv_item_replyinfo_reply_floor_content2;
		// // 恢复时间
		// TextView tv_item_replyinfo_reply_floor_time2;
		// // 加载更多
		// RelativeLayout rlyt_item_postinfo_starter_reply_floor_loadmore;
		// // 剩余回复数量
		// TextView tv_load_more_reply_number;
	}
}
