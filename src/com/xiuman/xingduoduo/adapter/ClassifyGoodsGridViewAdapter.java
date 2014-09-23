package com.xiuman.xingduoduo.adapter;

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
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.GoodsOne;
import com.xiuman.xingduoduo.util.SizeUtil;

/**
 * 
 * @名称：ClassifyGoodsGridViewAdapter.java
 * @描述：分类商品展示界面的Adapter
 * @author danding
 * @version
 * @date：2014-6-25
 */
public class ClassifyGoodsGridViewAdapter extends BaseAdapter {

	private Context context;
	private List<GoodsOne> goods;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private GoodsOne goodsone;
	int screenWidth = 0;

	public ClassifyGoodsGridViewAdapter(Context context, List<GoodsOne> goods,
			DisplayImageOptions options, ImageLoader imageLoader) {
		super();
		this.context = context;
		this.goods = goods;
		this.options = options;
		this.imageLoader = imageLoader;
		screenWidth = MyApplication.getInstance().getScreenWidth();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_calssify_goods,
					null);
			holder = new ViewHolder();
			holder.iv_item_goods_poster = (ImageView) convertView
					.findViewById(R.id.iv_item_goods_poster);
			holder.tv_item_goods_name = (TextView) convertView
					.findViewById(R.id.tv_item_goods_name);
			holder.tv_item_goods_price = (TextView) convertView
					.findViewById(R.id.tv_item_goods_price);
			holder.tv_item_goods_sales = (TextView) convertView
					.findViewById(R.id.tv_item_goods_sales);
			
			if(screenWidth!=0){
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/2-SizeUtil.dip2px(context, 20), screenWidth/2-SizeUtil.dip2px(context, 20));
				holder.iv_item_goods_poster.setLayoutParams(params);
			}
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		goodsone = goods.get(position);

		// 添加操作，测试数据
		imageLoader.displayImage(URLConfig.IMG_IP+goodsone.getSmallGoodsImagePath(),
				holder.iv_item_goods_poster, options);
		holder.tv_item_goods_name.setText(goodsone.getName());
		holder.tv_item_goods_price.setText("￥" + goodsone.getGoods_price());
		holder.tv_item_goods_sales.setText("月销量 " + goodsone.getSalesVolume());

		return convertView;
	}

	/**
	 * 
	 * @名称：ClassifyGoodsGridViewAdapter.java
	 * @描述：优化
	 * @author danding
	 * @version
	 * @date：2014-6-25
	 */
	static class ViewHolder {
		// 海报图
		ImageView iv_item_goods_poster;
		// 名字
		TextView tv_item_goods_name;
		// 价格
		TextView tv_item_goods_price;
		// 销量
		TextView tv_item_goods_sales;
	}
	
}
