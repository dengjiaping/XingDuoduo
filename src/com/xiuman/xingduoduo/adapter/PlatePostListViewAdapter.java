package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.model.PostStarter;
import com.xiuman.xingduoduo.util.HtmlTag;
import com.xiuman.xingduoduo.util.TimeUtil;
/**
 * @名称：PlatePostListViewAdapter.java
 * @描述：交流板块帖子列表
 * @author danding
 * 2014-8-8
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
		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_bbs_plate_post, null);
			holder.tv_item_bbs_plate_post_title = (TextView) convertView.findViewById(R.id.tv_item_bbs_plate_post_title);
			holder.tv_item_bbs_plate_post_content = (TextView) convertView.findViewById(R.id.tv_item_bbs_plate_post_content);
			holder.tv_item_bbs_plate_post_user = (TextView) convertView.findViewById(R.id.tv_item_bbs_plate_post_user);
			holder.tv_item_bbs_plate_post_reply = (TextView) convertView.findViewById(R.id.tv_item_bbs_plate_post_reply);
			holder.tv_item_bbs_plate_post_img_number = (TextView) convertView.findViewById(R.id.tv_item_bbs_plate_post_img_number);
			holder.tv_item_bbs_plate_post_time = (TextView) convertView.findViewById(R.id.tv_item_bbs_plate_post_time);
			holder.iv_item_bbs_plate_post_img_1 = (ImageView) convertView.findViewById(R.id.iv_item_bbs_plate_post_img_1);
			holder.iv_item_bbs_plate_post_img_2 = (ImageView) convertView.findViewById(R.id.iv_item_bbs_plate_post_img_2);
			holder.iv_item_bbs_plate_post_img_3 = (ImageView) convertView.findViewById(R.id.iv_item_bbs_plate_post_img_3);
			holder.iv_item_bbs_plate_post_tag = (ImageView) convertView.findViewById(R.id.iv_item_bbs_plate_post_tag);
			holder.llyt_item_bbs_plate_post_img_container = (LinearLayout) convertView.findViewById(R.id.llyt_item_bbs_plate_post_img_container);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		BBSPost post = posts.get(position);
		//帖子标签(精华，推荐)
//		holder.iv_item_bbs_plate_post_tag;
		holder.tv_item_bbs_plate_post_title.setText(post.title);
		holder.tv_item_bbs_plate_post_content.setText(post.content);
		holder.tv_item_bbs_plate_post_user.setText(post.user);
		holder.tv_item_bbs_plate_post_reply.setText(post.replyCount+"");
		holder.tv_item_bbs_plate_post_time.setText(TimeUtil.getTimeStr(TimeUtil.strToDate(post.createTime), new Date()));
		
		//图片数
		int number = 0;
		List<String> post_imgs = new ArrayList<String>();
		post_imgs = HtmlTag.match(post.contentHtml, "img", "src");
		number = post_imgs.size();
		if(number>3){
			holder.tv_item_bbs_plate_post_img_number.setText("共"+number+"张");
		}
		//图片
		holder.llyt_item_bbs_plate_post_img_container.setVisibility(View.VISIBLE);
		if(number==0){
			holder.iv_item_bbs_plate_post_img_1.setVisibility(View.INVISIBLE);
			holder.iv_item_bbs_plate_post_img_2.setVisibility(View.INVISIBLE);
			holder.iv_item_bbs_plate_post_img_3.setVisibility(View.INVISIBLE);
			holder.llyt_item_bbs_plate_post_img_container.setVisibility(View.GONE);
		}else if(number==1){
			holder.iv_item_bbs_plate_post_img_1.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_2.setVisibility(View.INVISIBLE);
			holder.iv_item_bbs_plate_post_img_3.setVisibility(View.INVISIBLE);
			imageLoader.displayImage(post_imgs.get(0), holder.iv_item_bbs_plate_post_img_1, options);
		}else if(number==2){
			holder.iv_item_bbs_plate_post_img_1.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_2.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_3.setVisibility(View.INVISIBLE);
			imageLoader.displayImage(post_imgs.get(0), holder.iv_item_bbs_plate_post_img_1, options);
			imageLoader.displayImage(post_imgs.get(1), holder.iv_item_bbs_plate_post_img_2, options);
		}else if(number>=3){
			holder.iv_item_bbs_plate_post_img_1.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_2.setVisibility(View.VISIBLE);
			holder.iv_item_bbs_plate_post_img_3.setVisibility(View.VISIBLE);
			imageLoader.displayImage(post_imgs.get(0), holder.iv_item_bbs_plate_post_img_1, options);
			imageLoader.displayImage(post_imgs.get(1), holder.iv_item_bbs_plate_post_img_2, options);
			imageLoader.displayImage(post_imgs.get(2), holder.iv_item_bbs_plate_post_img_3, options);
		}
		
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView tv_item_bbs_plate_post_title;
		TextView tv_item_bbs_plate_post_content;
		TextView tv_item_bbs_plate_post_user;
		TextView tv_item_bbs_plate_post_reply;
		TextView tv_item_bbs_plate_post_img_number;
		TextView tv_item_bbs_plate_post_time;
		ImageView iv_item_bbs_plate_post_img_1;
		ImageView iv_item_bbs_plate_post_img_2;
		ImageView iv_item_bbs_plate_post_img_3;
		//没有使用
		ImageView iv_item_bbs_plate_post_tag;
		LinearLayout llyt_item_bbs_plate_post_img_container;
	}

}
