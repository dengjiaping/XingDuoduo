package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.view.CircleImageView;

/**
 * @名称：PlatePostListViewAdapter.java
 * @描述：交流板块帖子列表
 * @author danding 2014-8-8
 */
public class PlatePostListViewAdapter extends BaseAdapter {

	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private ArrayList<BBSPost> posts;

	public PlatePostListViewAdapter(Context context,
			DisplayImageOptions options, ImageLoader imageLoader,
			ArrayList<BBSPost> posts) {
		super();
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
		this.posts = posts;
	}

	@Override
	public int getCount() {
		return posts.size();
	}

	@Override
	public Object getItem(int position) {
		return posts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_bbs_plate_post,
					null);
			holder.tv_item_bbs_plate_post_title = (TextView) convertView
					.findViewById(R.id.tv_item_bbs_plate_post_title);
			holder.tv_item_bbs_plate_post_content = (TextView) convertView
					.findViewById(R.id.tv_item_bbs_plate_post_content);
			holder.tv_item_bbs_plate_post_user = (TextView) convertView
					.findViewById(R.id.tv_item_bbs_plate_post_user);
			holder.tv_item_bbs_plate_post_reply = (TextView) convertView
					.findViewById(R.id.tv_item_bbs_plate_post_reply);
			holder.tv_item_bbs_plate_post_img_number = (TextView) convertView
					.findViewById(R.id.tv_item_bbs_plate_post_img_number);
			holder.tv_item_bbs_plate_post_time = (TextView) convertView
					.findViewById(R.id.tv_item_bbs_plate_post_time);
			holder.iv_item_bbs_plate_post_img_1 = (ImageView) convertView
					.findViewById(R.id.iv_item_bbs_plate_post_img_1);
			holder.iv_item_bbs_plate_post_img_2 = (ImageView) convertView
					.findViewById(R.id.iv_item_bbs_plate_post_img_2);
			holder.iv_item_bbs_plate_post_img_3 = (ImageView) convertView
					.findViewById(R.id.iv_item_bbs_plate_post_img_3);
			holder.llyt_item_bbs_plate_post_img_container = (LinearLayout) convertView
					.findViewById(R.id.llyt_item_bbs_plate_post_img_container);
			holder.iv_item_post_plate_post_head = (CircleImageView) convertView
					.findViewById(R.id.iv_item_post_plate_post_head);
			holder.iv_item_post_plate_post_sex = (ImageView) convertView
					.findViewById(R.id.iv_item_post_plate_post_sex);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final BBSPost post = posts.get(position);
		// 性别
		if (post.isSex()) {
			holder.iv_item_post_plate_post_sex
					.setImageResource(R.drawable.sex_male);
		} else {
			holder.iv_item_post_plate_post_sex
					.setImageResource(R.drawable.sex_female);
		}
		// 头像
		imageLoader.displayImage(URLConfig.PRIVATE_IMG_IP + post.getAvatar(),
				holder.iv_item_post_plate_post_head, options,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
					}
					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						if (post.isSex()) {
							holder.iv_item_post_plate_post_head
									.setImageResource(R.drawable.ic_male);
						} else {
							holder.iv_item_post_plate_post_head
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
		holder.tv_item_bbs_plate_post_title.setText(post.getTitle());
		holder.tv_item_bbs_plate_post_content.setText(Html.fromHtml(post
				.getContent()));
		holder.tv_item_bbs_plate_post_user.setText(post.getNickname());
		if (post.getNickname() == null) {
			holder.tv_item_bbs_plate_post_user.setText(post.getUsername());
		}
		holder.tv_item_bbs_plate_post_reply.setText(post.getReplyCount() + "");
		holder.tv_item_bbs_plate_post_time.setText(TimeUtil.getTimeStr(
				TimeUtil.strToDate(post.getCreateTime()), new Date()));

		// 图片数
		int number = 0;
		number = post.getPostImgs().size();
		if (number > 3) {
			holder.tv_item_bbs_plate_post_img_number
					.setText("共" + number + "张");
		}
		// 图片
		holder.llyt_item_bbs_plate_post_img_container
				.setVisibility(View.VISIBLE);
		if (number == 0) {
			holder.iv_item_bbs_plate_post_img_1.setVisibility(View.INVISIBLE);
			holder.iv_item_bbs_plate_post_img_2.setVisibility(View.INVISIBLE);
			holder.iv_item_bbs_plate_post_img_3.setVisibility(View.INVISIBLE);
			holder.llyt_item_bbs_plate_post_img_container
					.setVisibility(View.GONE);
		} else if (number == 1) {
			holder.iv_item_bbs_plate_post_img_1.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_2.setVisibility(View.INVISIBLE);
			holder.iv_item_bbs_plate_post_img_3.setVisibility(View.INVISIBLE);
			imageLoader.displayImage(post.getPostImgs().get(0),
					holder.iv_item_bbs_plate_post_img_1, options);
		} else if (number == 2) {
			holder.iv_item_bbs_plate_post_img_1.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_2.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_3.setVisibility(View.INVISIBLE);
			imageLoader.displayImage(post.getPostImgs().get(0),
					holder.iv_item_bbs_plate_post_img_1, options,
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {

						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {
							imageLoader.displayImage(post.getPostImgs().get(1),
									holder.iv_item_bbs_plate_post_img_2,
									options);
						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {

						}
					});

		} else if (number >= 3) {
			holder.iv_item_bbs_plate_post_img_1.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_2.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_3.setVisibility(View.VISIBLE);
			// 加载第一章图片结束加载第二张
			imageLoader.displayImage(post.getPostImgs().get(0),
					holder.iv_item_bbs_plate_post_img_1, options,
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {

						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {
							// 加载第二章完成之后加载第三张
							imageLoader.displayImage(post.getPostImgs().get(1),
									holder.iv_item_bbs_plate_post_img_2,
									options, new ImageLoadingListener() {

										@Override
										public void onLoadingStarted(
												String arg0, View arg1) {

										}

										@Override
										public void onLoadingFailed(
												String arg0, View arg1,
												FailReason arg2) {

										}

										@Override
										public void onLoadingComplete(
												String arg0, View arg1,
												Bitmap arg2) {
											imageLoader
													.displayImage(
															post.getPostImgs()
																	.get(2),
															holder.iv_item_bbs_plate_post_img_3,
															options);
										}

										@Override
										public void onLoadingCancelled(
												String arg0, View arg1) {

										}
									});
						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {

						}
					});

		}

		return convertView;
	}

	static class ViewHolder {
		// 用户头像
		CircleImageView iv_item_post_plate_post_head;
		// 用户性别
		ImageView iv_item_post_plate_post_sex;
		// 标题
		TextView tv_item_bbs_plate_post_title;
		// 内容
		TextView tv_item_bbs_plate_post_content;
		// 用户昵称
		TextView tv_item_bbs_plate_post_user;
		// 回复数
		TextView tv_item_bbs_plate_post_reply;
		// 图片数
		TextView tv_item_bbs_plate_post_img_number;
		// 创建时间
		TextView tv_item_bbs_plate_post_time;
		// 图片1
		ImageView iv_item_bbs_plate_post_img_1;
		// 图片2
		ImageView iv_item_bbs_plate_post_img_2;
		// 图片3
		ImageView iv_item_bbs_plate_post_img_3;
		// 没有使用
		LinearLayout llyt_item_bbs_plate_post_img_container;
	}

}
