package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.GoodsCart;
import com.xiuman.xingduoduo.model.Order;
import com.xiuman.xingduoduo.ui.activity.OrderInfoActivity;
import com.xiuman.xingduoduo.ui.activity.OrderListActivity;
import com.xiuman.xingduoduo.view.CustomDialog;

/**
 * @名称：OrderHistoryListViewAdapter.java
 * @描述：我的历史订单列表Adapter
 * @author danding 2014-8-5
 */
public class OrderHistoryListViewAdapter extends BaseAdapter {

	private Context context;
	private Handler handler;
	private ArrayList<Order> orders;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	// 删除订单Dialog
	private CustomDialog dialog_delete;

	/**
	 * @param context
	 * @param orders
	 * @param options
	 * @param imageLoader
	 */
	public OrderHistoryListViewAdapter(Context context,
			ArrayList<Order> orders, DisplayImageOptions options,
			ImageLoader imageLoader,Handler handler) {
		super();
		this.context = context;
		this.orders = orders;
		this.options = options;
		this.imageLoader = imageLoader;
		this.handler = handler;
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
			holder.lv_item_order_history_goods_list = (ListView) convertView
					.findViewById(R.id.lv_item_order_history_goods_list);
			holder.tv_item_order_history_goods_number = (TextView) convertView
					.findViewById(R.id.tv_item_order_history_goods_number);
			holder.tv_item_order_history_goods_total = (TextView) convertView
					.findViewById(R.id.tv_item_order_history_goods_total);
			holder.btn_item_order_history_delete_order = (Button) convertView
					.findViewById(R.id.btn_item_order_history_delete_order);
			holder.btn_item_order_history_logistics_order = (Button) convertView
					.findViewById(R.id.btn_item_order_history_logistics_order);
			holder.btn_item_order_history_take_order = (Button) convertView
					.findViewById(R.id.btn_item_order_history_take_order);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置数据
		final Order order = orders.get(position);

		holder.tv_item_order_history_order_id.setText(order.getOrder_id());
		holder.tv_item_order_history_order_state
				.setText(order.getOrder_state());

		ArrayList<GoodsCart> cart_goods = order.getGoods_list();
		OrderSubmitListViewAdapter adapter = new OrderSubmitListViewAdapter(
				context, cart_goods, options, imageLoader);
		holder.lv_item_order_history_goods_list.setAdapter(adapter);

		// 设置商品数据信息
		int goods_number = 0;
		double goods_total = 0;
		if (cart_goods.size() > 0) {

			for (int i = 0; i < cart_goods.size(); i++) {
				goods_number += cart_goods.get(i).getQuanity();
				goods_total += Double.valueOf(cart_goods.get(i)
						.getTotalPrice());
			}
			holder.tv_item_order_history_goods_number
					.setText(goods_number + "");
			holder.tv_item_order_history_goods_total.setText(goods_total + "");
		}
		holder.lv_item_order_history_goods_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context,OrderInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("order_info", order);
				intent.putExtras(bundle);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
			}
		});

		// 删除订单
		holder.btn_item_order_history_delete_order
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog_delete = new CustomDialog(
								context,
								context.getString(R.string.dialog_order_history_title),
								context.getString(R.string.dialog_order_history_message));

						dialog_delete.show();
						// 确定删除订单(提交到服务器)
						dialog_delete.btn_custom_dialog_sure
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										dialog_delete.dismiss();
										// 移除商品
										orders.remove(order);
										Message msg = Message.obtain();
										msg.what = AppConfig.UPDATE_ORDER_HISTORY;
										msg.obj = order;
										handler.handleMessage(msg);

									}
								});
						// 取消移除
						dialog_delete.btn_custom_dialog_cancel
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										dialog_delete.dismiss();

									}
								});
					}
				});
		// 查看物流
		holder.btn_item_order_history_logistics_order
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});
		// 确认收货
		holder.btn_item_order_history_take_order
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});

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
		// 订单商品列表
		ListView lv_item_order_history_goods_list;
		// 商品总数
		TextView tv_item_order_history_goods_number;
		// 商品总计
		TextView tv_item_order_history_goods_total;
		// 删除订单
		Button btn_item_order_history_delete_order;
		// 查看物流
		Button btn_item_order_history_logistics_order;
		// 确认收货
		Button btn_item_order_history_take_order;
	}

}
