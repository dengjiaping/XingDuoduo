package com.xiuman.xingduoduo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.model.GoodsOne;

/**
 * @名称：LimitBuyGiftListViewAdapter.java
 * @描述：限时抢购跟礼品专区Adapter
 * @author danding 2014-9-17
 */
public class LimitBuyGiftListViewAdapter extends BaseAdapter {

	private Context context;
	private List<GoodsOne> goods;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;
	private GoodsOne goodsone;

	public LimitBuyGiftListViewAdapter(Context context, List<GoodsOne> goods,
			DisplayImageOptions options, ImageLoader imageLoader) {
		super();
		this.context = context;
		this.goods = goods;
		this.options = options;
		this.imageLoader = imageLoader;
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
		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_litmitbuy_gift,
					null);
			holder = new ViewHolder();
			holder.iv_lg_goods_poster = (ImageView) convertView
					.findViewById(R.id.iv_lg_goods_poster);
			holder.tv_lg_goods_name = (TextView) convertView
					.findViewById(R.id.tv_lg_goods_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		goodsone = goods.get(position);

		// 添加操作，测试数据
		imageLoader.displayImage(
				URLConfig.IMG_IP + goodsone.getSourceImagePath(),
				holder.iv_lg_goods_poster, options, new ImageLoadingListener() {

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
						int img_width = MyApplication.getInstance()
								.getScreenWidth();
						int img_height = (int) (img_width
								/ (arg2.getWidth() * 1.0) * arg2.getHeight());
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								img_width, img_height);
						holder.iv_lg_goods_poster.setLayoutParams(params);
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {

					}
				});
		holder.tv_lg_goods_name.setText(goodsone.getName());

		return convertView;
	}

	/**
	 * @名称：LimitBuyGiftListViewAdapter.java
	 * @描述：优化
	 * @author danding 2014-9-17
	 */
	class ViewHolder {
		// 海报图
		ImageView iv_lg_goods_poster;
		// 名字
		TextView tv_lg_goods_name;
	}
}
