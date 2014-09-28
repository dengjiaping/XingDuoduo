package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

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
import com.xiuman.xingduoduo.model.OrderSimple;
import com.xiuman.xingduoduo.util.TimeUtil;

/**
 * @名称：OrderHistoryListViewAdapter.java
 * @描述：我的历史订单列表Adapter
 * @author danding 2014-8-5
 */
public class OrderHistoryListViewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<OrderSimple> orders;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;

	/**
	 * @param context
	 * @param orders
	 * @param options
	 * @param imageLoader
	 */
	public OrderHistoryListViewAdapter(Context context,
			ArrayList<OrderSimple> orders, DisplayImageOptions options,
			ImageLoader imageLoader) {
		super();
		this.context = context;
		this.orders = orders;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return orders.size();
	}

	@Override
	public Object getItem(int position) {
		return orders.get(position);
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
			convertView = View.inflate(context,
					R.layout.item_order_history_list, null);

			holder.tv_item_order_history_order_id = (TextView) convertView
					.findViewById(R.id.tv_item_order_history_order_id);
			holder.tv_item_order_history_order_state = (TextView) convertView
					.findViewById(R.id.tv_item_order_history_order_state);
			holder.iv_item_order_history_poster = (ImageView) convertView
					.findViewById(R.id.iv_item_order_history_poster);
			holder.tv_item_order_history_create_time = (TextView) convertView
					.findViewById(R.id.tv_item_order_history_create_time);
			holder.tv_item_order_history_goods_number = (TextView) convertView
					.findViewById(R.id.tv_item_order_history_goods_number);
			holder.tv_item_order_history_goods_total = (TextView) convertView
					.findViewById(R.id.tv_item_order_history_goods_total);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置数据
		OrderSimple order = orders.get(position);

		holder.tv_item_order_history_order_id.setText(order.getOrderSn());
		holder.tv_item_order_history_order_state
				.setText(order.getOrderStatus());
		imageLoader.displayImage(URLConfig.IMG_IP+order.getSmallGoodsImagePath(), holder.iv_item_order_history_poster, options);
		holder.tv_item_order_history_create_time.setText(order.getCreate_date());
		holder.tv_item_order_history_goods_number.setText(order.getQuanity()+"");
		holder.tv_item_order_history_goods_total.setText(order.getTotalAmount());
		return convertView;
	}

	/**
	 * @名称：OrderHistoryListViewAdapter.java
	 * @描述：优化
	 * @author danding 2014-8-5
	 */
	class ViewHolder {
		// 订单号
		TextView tv_item_order_history_order_id;
		// 订单状态
		TextView tv_item_order_history_order_state;
		// 订单图
		ImageView iv_item_order_history_poster;
		//订单生成时间
		TextView tv_item_order_history_create_time;
		// 商品总数
		TextView tv_item_order_history_goods_number;
		// 商品总计
		TextView tv_item_order_history_goods_total;
	}

}
