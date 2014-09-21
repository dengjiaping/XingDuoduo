package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.GoodsCart;
import com.xiuman.xingduoduo.util.AnimationUtil;
import com.xiuman.xingduoduo.util.NetUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.CustomDialog;

/**
 * 
 * @名称：ShoppingCartListViewAdapter.java
 * @描述：购物车列表Adapter
 * @author danding
 * @version
 * @date：2014-7-21
 */
@SuppressLint("UseSparseArrays")
public class ShoppingCartListViewAdapter extends BaseAdapter {

	private Context context;
	// 购物车商品列表
	private ArrayList<GoodsCart> cart_goods;
	// 用户结算时的商品列表
	private ArrayList<GoodsCart> cart_goods_balance;
	// ImageLoader
	private ImageLoader imageloader;
	// options
	public DisplayImageOptions options;
	// Handler，传递消息到调用Adapter的地方
	private Handler handler;
	// List记录购物车已被选择的产品
	private List<Boolean> list_checked;
	// 购物车某种产品的数量
	private int number;
	// 移除商品时显示的Dialog
	private CustomDialog dialog_remove;

	/**
	 * 
	 * @param context
	 * @param cart_goods
	 * @param imageloader
	 * @param options
	 * @param handler
	 */
	public ShoppingCartListViewAdapter(Context context,
			ArrayList<GoodsCart> cart_goods, ImageLoader imageloader,
			DisplayImageOptions options, Handler handler,
			List<Boolean> list_checked) {
		super();
		this.context = context;
		this.cart_goods = cart_goods;
		this.imageloader = imageloader;
		this.options = options;
		this.handler = handler;
		this.list_checked = list_checked;

		cart_goods_balance = new ArrayList<GoodsCart>();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		// if (convertView == null) {
		holder = new ViewHolder();
		convertView = View.inflate(context, R.layout.item_shopping_cart, null);
		holder.flyt_item_shoping_cart_view = (FrameLayout) convertView
				.findViewById(R.id.flyt_item_shoping_cart_view);
		holder.llyt_item_shopping_cart_view1 = (LinearLayout) convertView
				.findViewById(R.id.llyt_item_shopping_cart_view1);
		holder.cb_item_shopping_cart_goods = (CheckBox) convertView
				.findViewById(R.id.cb_item_shopping_cart_goods);
		holder.iv_item_shopping_cart_goods_poster = (ImageView) convertView
				.findViewById(R.id.iv_item_shopping_cart_goods_poster);
		holder.tv_item_shopping_cart_goods_name = (TextView) convertView
				.findViewById(R.id.tv_item_shopping_cart_goods_name);
		holder.tv_item_shopping_cart_goods_size = (TextView) convertView
				.findViewById(R.id.tv_item_shopping_cart_goods_size);
		// holder.tv_item_shopping_cart_goods_number = (TextView) convertView
		// .findViewById(R.id.tv_item_shopping_cart_goods_number);
		holder.iv_item_shopping_cart_goods_isActivity = (ImageView) convertView.findViewById(R.id.iv_item_shopping_cart_goods_isActivity);
		holder.tv_item_shopping_cart_goods_number2 = (TextView) convertView
				.findViewById(R.id.tv_item_shopping_cart_goods_number2);
		holder.tv_item_shopping_cart_price = (TextView) convertView
				.findViewById(R.id.tv_item_shopping_cart_goods_price);
		holder.tv_item_shopping_cart_total = (TextView) convertView
				.findViewById(R.id.tv_item_shopping_cart_total);
		holder.btn_item_shopping_cart_update = (Button) convertView
				.findViewById(R.id.btn_item_shopping_cart_update);
		holder.flyt_item_shopping_cart_view2 = (FrameLayout) convertView
				.findViewById(R.id.flyt_item_shopping_cart_view2);
		holder.ivbtn_item_shopping_cart_minus = (ImageView) convertView
				.findViewById(R.id.ivbtn_item_shopping_cart_minus);
		holder.et_item_shopping_cart_goods_number = (EditText) convertView
				.findViewById(R.id.et_item_shopping_cart_goods_number);
		holder.ivbtn_item_shopping_cart_add = (ImageView) convertView
				.findViewById(R.id.ivbtn_item_shopping_cart_add);
		holder.btn_item_shopping_cart_remove_goods = (Button) convertView
				.findViewById(R.id.btn_item_shopping_cart_remove_goods);

		// convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }

		final GoodsCart goods = cart_goods.get(position);
		number = goods.getQuanity();
		
		// 图片
		imageloader.displayImage(
				URLConfig.IMG_IP + goods.getSmallGoodsImagePath(),
				holder.iv_item_shopping_cart_goods_poster, options);
		//活动商品标记
		if(goods.isActivities()){
			holder.iv_item_shopping_cart_goods_isActivity.setVisibility(View.VISIBLE);
		}else{
			holder.iv_item_shopping_cart_goods_isActivity.setVisibility(View.INVISIBLE);
		}
		// 名称
		holder.tv_item_shopping_cart_goods_name.setText(goods.getProductName());
		// 已选择的型号
		holder.tv_item_shopping_cart_goods_size.setText(goods
				.getSpecifications());
		if (holder.tv_item_shopping_cart_goods_size.getText().equals("")) {
			holder.tv_item_shopping_cart_goods_size.setText("标准规格");
		}

		// 数量
		// holder.tv_item_shopping_cart_goods_number.setText(goods
		// .getGoods_number() + "");
		// 数量2
		holder.tv_item_shopping_cart_goods_number2.setText(goods.getQuanity()
				+ "");
		// 单价
		holder.tv_item_shopping_cart_price.setText(goods.getProductPrice());
		// 总价
		holder.tv_item_shopping_cart_total.setText(goods.getTotalPrice());

		// 默认隐藏的布局数据
		holder.et_item_shopping_cart_goods_number.setText(goods.getQuanity()
				+ "");

		
		
		// 修改商品数量
		holder.btn_item_shopping_cart_update
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (NetUtil.isHasNetAvailable(context)) {
							if (holder.flyt_item_shopping_cart_view2
									.getVisibility() == View.INVISIBLE) {// 编辑界面隐藏
								AnimationUtil.showAnimation1(
										holder.llyt_item_shopping_cart_view1,
										holder.flyt_item_shoping_cart_view);
								AnimationUtil.showAnimation2(
										holder.flyt_item_shopping_cart_view2,
										holder.flyt_item_shoping_cart_view);

								holder.flyt_item_shopping_cart_view2
										.setVisibility(View.VISIBLE);
								holder.btn_item_shopping_cart_update
										.setText(context.getResources()
												.getString(R.string.cart_ok));
							} else {// 编辑界面显示（完成编辑）
								AnimationUtil
										.backAnimation1(holder.llyt_item_shopping_cart_view1);
								AnimationUtil
										.backAnimation2(holder.flyt_item_shopping_cart_view2);

								holder.flyt_item_shopping_cart_view2
										.setVisibility(View.INVISIBLE);
								holder.btn_item_shopping_cart_update
										.setText(context
												.getResources()
												.getString(R.string.cart_update));

								//保存修改前的商品数量和总价
								int goods_number = goods.getQuanity();
								String goods_total = goods.getTotalPrice();
								
								
								// 编辑完成后设置数据
								// 设置商品数量
								// holder.tv_item_shopping_cart_goods_number
								// .setText(number + "");
								// 设置商品数量2
								holder.tv_item_shopping_cart_goods_number2.setText(Integer
										.valueOf(holder.et_item_shopping_cart_goods_number
												.getText().toString())
										+ "");
								// 设置该商品总价
								holder.tv_item_shopping_cart_total.setText(Double
										.valueOf(goods.getProductPrice())
										* Integer
												.valueOf(holder.et_item_shopping_cart_goods_number
														.getText().toString())
										+ "");
								// 更新List中的数据
								goods.setQuanity(Integer
										.valueOf(holder.et_item_shopping_cart_goods_number
												.getText().toString()));
								goods.setTotalPrice(Double.valueOf(goods
										.getProductPrice())
										* Integer
												.valueOf(holder.et_item_shopping_cart_goods_number
														.getText().toString())
										+ "");

								Message msg = Message.obtain();
								msg.what = AppConfig.UPDATE_SHOPPING_CART;
								Bundle bundle = new Bundle();
								bundle.putSerializable("goods_update", goods);
								bundle.putInt("goods_number", goods_number);
								bundle.putString("goods_total", goods_total);
								bundle.putInt("goods_position", position);
//								msg.arg1 = Integer
//										.valueOf(holder.et_item_shopping_cart_goods_number
//												.getText().toString());
//								msg.arg2 = position;
//								msg.obj = goods;
								msg.setData(bundle);
								handler.sendMessage(msg);
							}
						}else{
							ToastUtil.ToastView(context, "当前没有网络，请联网后再试");
						}
					}
				});
		// 减
		holder.ivbtn_item_shopping_cart_minus
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						number = Integer
								.valueOf(holder.et_item_shopping_cart_goods_number
										.getText().toString());
						if (number > 1) {
							number--;
							holder.et_item_shopping_cart_goods_number
									.setText(number + "");
						}
					}
				});
		// 加
		holder.ivbtn_item_shopping_cart_add
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						number = Integer
								.valueOf(holder.et_item_shopping_cart_goods_number
										.getText().toString());
						number++;
						holder.et_item_shopping_cart_goods_number
								.setText(number + "");

					}
				});
		// 设置默认选择
		holder.cb_item_shopping_cart_goods.setChecked(list_checked
				.get(position));

		// 商品是否选中
		holder.cb_item_shopping_cart_goods
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						list_checked.set(position, isChecked);
						// 传递给Activity
						Message msg = Message.obtain();
						msg.what = AppConfig.UPDATE_SHOPPING_CART_SELECT;
						msg.obj = isChecked;
						handler.sendMessage(msg);
					}
				});

		// 移除商品
		holder.btn_item_shopping_cart_remove_goods
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog_remove = new CustomDialog(
								context,
								context.getString(R.string.dialog_shopping_cart_title),
								context.getString(R.string.dialog_shopping_cart_message));

						dialog_remove.show();
						// 确定移除商品(添加操作，传递数据到服务端)
						dialog_remove.btn_custom_dialog_sure
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										dialog_remove.dismiss();
										// 移除商品
										cart_goods.remove(goods);
										list_checked.remove(position);
										Message msg = Message.obtain();
										msg.what = AppConfig.UPDATE_SHOPPING_CART_GOODS;
										msg.obj = goods;
										handler.handleMessage(msg);

									}
								});
						// 取消移除
						dialog_remove.btn_custom_dialog_cancel
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										dialog_remove.dismiss();

									}
								});
					}
				});
		return convertView;
	}

	/**
	 * 
	 * @描述：获取选中的购物车商品列表，用于下订单
	 * @date：2014-7-22
	 * @return
	 */
	public ArrayList<GoodsCart> getBalanceGoods() {
		cart_goods_balance.clear();
		for (int i = 0; i < list_checked.size(); i++) {
			if (list_checked.get(i)) {
				cart_goods_balance.add(cart_goods.get(i));
			}
		}
		return cart_goods_balance;
	}

	/**
	 * 
	 * @描述：获取购物车的选择与否
	 * @date：2014-7-23
	 * @return
	 */
	public List<Boolean> getMap() {
		return list_checked;
	}

	/**
	 * 
	 * @名称：ShoppingCartListViewAdapter.java
	 * @描述：优化
	 * @author danding
	 * @version
	 * @date：2014-7-22
	 */
	class ViewHolder {

		// 整体布局
		FrameLayout flyt_item_shoping_cart_view;
		// 左移的布局
		LinearLayout llyt_item_shopping_cart_view1;
		// CheckBox(被选中的结算的时候加入订单中)
		CheckBox cb_item_shopping_cart_goods;
		// 商品图标
		ImageView iv_item_shopping_cart_goods_poster;
		//赠品标记
		ImageView iv_item_shopping_cart_goods_isActivity;
		// 商品名称
		TextView tv_item_shopping_cart_goods_name;
		// 商品规格
		TextView tv_item_shopping_cart_goods_size;
		// 商品数量
		// TextView tv_item_shopping_cart_goods_number;
		// 商品数量2
		TextView tv_item_shopping_cart_goods_number2;
		// 商品单价
		TextView tv_item_shopping_cart_price;
		// 商品总价
		TextView tv_item_shopping_cart_total;
		// 变价商品数量规格
		Button btn_item_shopping_cart_update;

		// 购物车商品编辑布局(默认隐藏)
		FrameLayout flyt_item_shopping_cart_view2;
		// 商品数量减
		ImageView ivbtn_item_shopping_cart_minus;
		// 商品数量编辑
		EditText et_item_shopping_cart_goods_number;
		// 加
		ImageView ivbtn_item_shopping_cart_add;
		// 移除购物车
		Button btn_item_shopping_cart_remove_goods;
	}
}
