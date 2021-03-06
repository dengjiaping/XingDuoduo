package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.GoodsOrderInfo;
import com.xiuman.xingduoduo.model.OrderDiscuss;

/**
 * 
 * @名称：DiscussOrderListViewAdapter.java
 * @描述：商品评价列表
 * @author danding
 * @version
 * @date：2014-7-28
 */
public class DiscussOrderListViewAdapter extends BaseAdapter {

	private Context context;
	// 订单商品列表
	private List<GoodsOrderInfo> cart_goods;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	// 评论内容
	private ArrayList<OrderDiscuss> discuss_contents;
	/**
	 * @param context
	 * @param cart_goods
	 * @param options
	 * @param imageLoader
	 */
	public DiscussOrderListViewAdapter(Context context,
			List<GoodsOrderInfo> cart_goods, DisplayImageOptions options,
			ImageLoader imageLoader) {
		super();
		this.context = context;
		this.cart_goods = cart_goods;
		this.options = options;
		this.imageLoader = imageLoader;
		discuss_contents = new ArrayList<OrderDiscuss>();
		for(int i=0;i<cart_goods.size();i++){
			OrderDiscuss discuss = new OrderDiscuss(cart_goods.get(i).getGoodId(), "真不错，全5分", 5, 5, 5);
			discuss_contents.add(discuss);
		}
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
		convertView = View.inflate(context, R.layout.item_discuss_order, null);
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
		holder.ratingbar_zhiliang = (RatingBar) convertView
				.findViewById(R.id.ratingbar_zhiliang);
		holder.ratingbar_taidu = (RatingBar) convertView
				.findViewById(R.id.ratingbar_taidu);
		holder.ratingbar_sudu = (RatingBar) convertView
				.findViewById(R.id.ratingbar_sudu);
		holder.iv_item_order_submit_goods_isActivity = (ImageView) convertView
				.findViewById(R.id.iv_item_order_submit_goods_isActivity);
		holder.et_item_discuss_goods_content = (EditText) convertView
				.findViewById(R.id.et_item_discuss_goods_content);

		// convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }

		// 设置数据
		GoodsOrderInfo goods = cart_goods.get(position);
		// 测试数据，添加操作
		imageLoader.displayImage(
				URLConfig.IMG_IP + goods.getSmallGoodsImagePath(),
				holder.iv_item_order_submit_goods_poster, options);
		if (goods.isActivity()) {
			holder.iv_item_order_submit_goods_isActivity
					.setVisibility(View.VISIBLE);
		} else {
			holder.iv_item_order_submit_goods_isActivity
					.setVisibility(View.INVISIBLE);
		}
		holder.tv_item_order_submit_goods_name.setText(goods.getProductName());
		holder.tv_item_order_submit_goods_number.setText(goods.getQuanity()
				+ "");
		holder.tv_item_order_submit_goods_size.setText(goods
				.getSpecifications());
		if (goods.getSpecifications() == null) {
			holder.tv_item_order_submit_goods_size.setText("标准规格");
		}
		holder.tv_item_order_submit_goods_price.setText(goods.getProductPrice()
				+ "");
		holder.tv_item_order_submit_cart_total.setText(Float.parseFloat(goods
				.getProductPrice()) * goods.getQuanity() + "");

		holder.et_item_discuss_goods_content
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						// discuss_contents.set(position,holder.et_item_discuss_goods_content.getText().toString());
						discuss_contents.get(position).setContent(holder.et_item_discuss_goods_content
								.getText().toString());
					}
				});

		holder.ratingbar_zhiliang
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						discuss_contents.get(position).setGoodsQuality(holder.ratingbar_zhiliang
								.getRating());
					}
				});
		holder.ratingbar_taidu
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						discuss_contents.get(position).setServiceAttitude(holder.ratingbar_taidu
								.getRating());
					}
				});
		holder.ratingbar_sudu
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						discuss_contents.get(position).setDeliverySpeed(holder.ratingbar_sudu
								.getRating());
					}
				});

		return convertView;
	}

	public ArrayList<OrderDiscuss> getDiscuss_contents() {
		return discuss_contents;
	}

	public void setDiscuss_contents(ArrayList<OrderDiscuss> discuss_contents) {
		this.discuss_contents = discuss_contents;
	}

	class ViewHolder {
		// 商品图
		ImageView iv_item_order_submit_goods_poster;
		// 商品名称
		TextView tv_item_order_submit_goods_name;
		// 活动商品标记
		ImageView iv_item_order_submit_goods_isActivity;
		// 商品规格
		TextView tv_item_order_submit_goods_size;
		// 商品单价
		TextView tv_item_order_submit_goods_price;
		// 商品数量
		TextView tv_item_order_submit_goods_number;
		// 商品总价
		TextView tv_item_order_submit_cart_total;
		// 质量
		RatingBar ratingbar_zhiliang;
		// 态度
		RatingBar ratingbar_taidu;
		// 发货
		RatingBar ratingbar_sudu;
		// 评价内容
		EditText et_item_discuss_goods_content;
	}

}
