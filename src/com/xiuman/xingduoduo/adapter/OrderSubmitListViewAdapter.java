package com.xiuman.xingduoduo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.GoodsCart;

/**
 * 
 * @名称：OrderSubmitListViewAdapter.java
 * @描述：订单确认界面商品列表
 * @author danding
 * @version
 * @date：2014-7-28
 */
public class OrderSubmitListViewAdapter extends BaseAdapter {

	private Context context;
	// 订单商品列表
	private List<GoodsCart> cart_goods;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;

	/**
	 * @param context
	 * @param cart_goods
	 * @param options
	 * @param imageLoader
	 */
	public OrderSubmitListViewAdapter(Context context,
			List<GoodsCart> cart_goods, DisplayImageOptions options,
			ImageLoader imageLoader) {
		super();
		this.context = context;
		this.cart_goods = cart_goods;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return cart_goods.size();
	}

	@Override
	public Object getItem(int position) {
		return cart_goods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_order_submit,
					null);
			holder.iv_item_order_submit_goods_poster = (ImageView) convertView
					.findViewById(R.id.iv_item_order_submit_goods_poster);
			holder.tv_item_order_submit_goods_name = (TextView) convertView
					.findViewById(R.id.tv_item_order_submit_goods_name);
			holder.tv_item_order_submit_goods_number = (TextView) convertView
					.findViewById(R.id.tv_item_order_submit_goods_number);
			holder.tv_item_order_submit_goods_size = (TextView) convertView
					.findViewById(R.id.tv_item_order_submit_goods_size);
			holder.tv_item_order_submit_goods_price = (TextView) convertView
					.findViewById(R.id.tv_item_order_submit_goods_price);
			holder.tv_item_order_submit_cart_total = (TextView) convertView
					.findViewById(R.id.tv_item_order_submit_cart_total);
			holder.iv_item_order_submit_goods_isActivity = (ImageView) convertView.findViewById(R.id.iv_item_order_submit_goods_isActivity);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 设置数据
		GoodsCart goods = cart_goods.get(position);
		//测试数据，添加操作
		imageLoader.displayImage(URLConfig.IMG_IP + goods.getSmallGoodsImagePath(), holder.iv_item_order_submit_goods_poster, options);
		if(goods.isActivities()){
			holder.iv_item_order_submit_goods_isActivity.setVisibility(View.VISIBLE);
		}else{
			holder.iv_item_order_submit_goods_isActivity.setVisibility(View.INVISIBLE);
		}
		holder.tv_item_order_submit_goods_name.setText(goods.getProductName());
		holder.tv_item_order_submit_goods_number.setText(goods.getQuanity()+"");
		holder.tv_item_order_submit_goods_size.setText(goods.getSpecifications());
		if(goods.getSpecifications()==null){
			holder.tv_item_order_submit_goods_size.setText("标准规格");
		}
		holder.tv_item_order_submit_goods_price.setText(goods.getProductPrice()+"");
		holder.tv_item_order_submit_cart_total.setText(goods.getTotalPrice());
		

		return convertView;
	}

	class ViewHolder {
		// 商品图
		ImageView iv_item_order_submit_goods_poster;
		// 商品名称
		TextView tv_item_order_submit_goods_name;
		//活动商品标记
		ImageView iv_item_order_submit_goods_isActivity;
		// 商品规格
		TextView tv_item_order_submit_goods_size;
		// 商品单价
		TextView tv_item_order_submit_goods_price;
		// 商品数量
		TextView tv_item_order_submit_goods_number;
		// 商品总价
		TextView tv_item_order_submit_cart_total;
	}

}
