package com.xiuman.xingduoduo.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.GoodsOne;
/**
 * @名称：CollectionGridViewAdapter.java
 * @描述：收藏列表Adapter
 * @author danding
 * 2014-8-6
 */
public class CollectionGridViewAdapter extends BaseAdapter {
	
	private Context context;
	private List<GoodsOne> goods;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private GoodsOne goodsone;
	private Handler handler;
	//item是否晃动标记
	public boolean flag = false;
	

	public CollectionGridViewAdapter(Context context, List<GoodsOne> goods,
			DisplayImageOptions options, ImageLoader imageLoader,boolean flag,Handler handler) {
		super();
		this.context = context;
		this.goods = goods;
		this.options = options;
		this.imageLoader = imageLoader;
		this.flag = flag;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return goods.size();
	}

	@Override
	public Object getItem(int position) {
		return goods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_collection,
					null);
			holder = new ViewHolder();
			holder.rlyt_item_collection_container = (RelativeLayout) convertView.findViewById(R.id.rlyt_item_collection_container);
			holder.iv_item_collection_goods_poster = (ImageView) convertView
					.findViewById(R.id.iv_item_collection_goods_poster);
			holder.btn_item_collection_delete = (Button) convertView.findViewById(R.id.btn_item_collection_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		goodsone = goods.get(position);

		imageLoader.displayImage(URLConfig.IMG_IP+goodsone.getSmallGoodsImagePath(),
				holder.iv_item_collection_goods_poster, options);
		
		if(flag){
			holder.btn_item_collection_delete.setVisibility(View.VISIBLE);
			Animation adim = AnimationUtils.loadAnimation(context, R.anim.rotate);
			holder.rlyt_item_collection_container.startAnimation(adim);
			if(goods.size()==1){
				holder.rlyt_item_collection_container.clearAnimation();
			}
		}else{
			holder.btn_item_collection_delete.setVisibility(View.INVISIBLE);
			holder.rlyt_item_collection_container.clearAnimation();
		}
		//删除
		holder.btn_item_collection_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg = Message.obtain();
				msg.what = AppConfig.DELETE_COLLECTION_ADAPTER;//传递消息
				msg.obj = goodsone;
				handler.sendMessage(msg);
			}
		});
		
		return convertView;
	}

	/**
	 * 
	 * @名称：CollectionGridViewAdapter.java
	 * @描述：优化
	 * @author danding
	 * @version
	 * @date：2014-6-25
	 */
	static class ViewHolder {
		//item容器
		RelativeLayout rlyt_item_collection_container;
		// 海报图
		ImageView iv_item_collection_goods_poster;
		//删除按钮
		Button btn_item_collection_delete;
	}

}
