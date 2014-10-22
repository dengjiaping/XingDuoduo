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
import com.xiuman.xingduoduo.model.BBSPlate;

/**
 * @名称：BBSPlateListViewAdapter.java
 * @描述：论坛板块Adapter
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class BBSPlateListViewAdapter extends BaseAdapter {

	private Context context;
	// 板块列表
	private ArrayList<BBSPlate> plates;
	public DisplayImageOptions options;// 配置图片加载及显示选项
	public ImageLoader imageLoader;

	public BBSPlateListViewAdapter(Context context, ArrayList<BBSPlate> plates,
			DisplayImageOptions options, ImageLoader imageLoader) {
		super();
		this.context = context;
		this.plates = plates;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return plates.size();
	}

	@Override
	public Object getItem(int position) {
		return plates.get(position);
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
			convertView = View.inflate(context, R.layout.item_bbs_plate, null);

			holder.iv_item_bbs_plate_icon = (ImageView) convertView
					.findViewById(R.id.iv_item_bbs_plate_icon);
			holder.tv_item_bbs_plate_name = (TextView) convertView
					.findViewById(R.id.tv_item_bbs_plate_name);
			holder.tv_item_bbs_plate_description = (TextView) convertView
					.findViewById(R.id.tv_item_bbs_plate_description);
			holder.iv_item_bbs_plate_tag = (ImageView) convertView
					.findViewById(R.id.iv_item_bbs_plate_tag);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 设置数据
		BBSPlate plate = plates.get(position);
		holder.tv_item_bbs_plate_name.setText(plate.getTitle());
		holder.tv_item_bbs_plate_description.setText(plate.getDescription());
		imageLoader.displayImage(URLConfig.PLATE_IMG_IP+plate.getLogo(), holder.iv_item_bbs_plate_icon, options);
		holder.iv_item_bbs_plate_tag.setVisibility(View.INVISIBLE);

		return convertView;
	}

	class ViewHolder {

		// 标题
		TextView tv_item_bbs_plate_name;
		// 图标
		ImageView iv_item_bbs_plate_icon;
		// 描述
		TextView tv_item_bbs_plate_description;
		// 标签
		ImageView iv_item_bbs_plate_tag;
	}

}
