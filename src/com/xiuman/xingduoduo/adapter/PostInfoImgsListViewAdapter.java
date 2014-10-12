package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.PostImg;
import com.xiuman.xingduoduo.util.SizeUtil;

/**
 * @名称：PostInfoImgsListViewAdapter.java
 * @描述：帖子详情里的图片Adapter
 * @author danding 2014-8-16
 */
public class PostInfoImgsListViewAdapter extends BaseAdapter {

	private Context context;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private ArrayList<PostImg> imgs;
	private Handler handler;
	// 屏幕宽度
	int width;
	//高度
	private HashMap<Integer, Integer> totalHeight;

	@SuppressLint("UseSparseArrays")
	public PostInfoImgsListViewAdapter(Context context,
			DisplayImageOptions options, ImageLoader imageLoader,
			ArrayList<PostImg> imgs, Handler handler) {
		super();
		this.context = context;
		this.options = options;
		this.imageLoader = imageLoader;
		this.imgs = imgs;
		this.handler = handler;
		width = MyApplication.getInstance().getScreenWidth();
		totalHeight = new HashMap<Integer, Integer>();
	}

	@Override
	public int getCount() {
		return imgs.size();
	}

	@Override
	public Object getItem(int position) {
		return imgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.item_postinfo_img, null);
		final ImageView iv_item_postinfo_img_item = (ImageView) convertView
				.findViewById(R.id.iv_item_postinfo_img_item);
		totalHeight.put(position, 0);
		imageLoader.displayImage(URLConfig.PRIVATE_IMG_IP + imgs.get(position).getImgurl(),
				iv_item_postinfo_img_item, options, new ImageLoadingListener() {

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

						int img_width = width - SizeUtil.dip2px(context, 20);
						if (img_width > arg2.getWidth()) {
							int img_height = (int) (img_width
									/ (arg2.getWidth() * 1.0) * arg2
									.getHeight());
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
									img_width, img_height);
							iv_item_postinfo_img_item.setLayoutParams(params);
							totalHeight.put(position, img_height+SizeUtil.dip2px(context, 8));
						}else{
							totalHeight.put(position, arg2.getHeight()+SizeUtil.dip2px(context, 8));
						}
						if (position == imgs.size() - 1) {
							Message msg = Message.obtain();
							msg.obj = AppConfig.FLUSH_IMG_ADAPTER;
							handler.sendMessage(msg);
						}
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {

					}
				});

		return convertView;
	}
	/**
	 * @描述：获取adapter的高度
	 * @日期：2014-10-12
	 * @return
	 */
	public HashMap<Integer, Integer> getTotalHeight() {
		return totalHeight;
	}

	public void setTotalHeight(HashMap<Integer, Integer> totalHeight) {
		this.totalHeight = totalHeight;
	}
	/**
	 * @描述：获取Adapter高度
	 * @日期：2014-10-12
	 * @param map
	 * @return
	 */
	public int getListViewHeight(){
		int height = 0;
		Set<Integer> mapSet =  totalHeight.keySet();	//获取所有的key值 为set的集合
		Iterator<Integer> itor =  mapSet.iterator();//获取key的Iterator便利
		while(itor.hasNext()){//存在下一个值
			Integer key = itor.next();//当前key值
			height += totalHeight.get(key);
		}
		return height;
	}
}
