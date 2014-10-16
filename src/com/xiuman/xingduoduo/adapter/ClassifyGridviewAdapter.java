package com.xiuman.xingduoduo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.constants.ConstantParameter;

/**
 * @名称：ClassifyGridviewAdapter.java 
 * @描述：商品分类首页适配器
 * @author danding
 * @version 2014-6-5
 */
public class ClassifyGridviewAdapter extends BaseAdapter {
	//分类图标
	private int[] posters = ConstantParameter.posters;
	private Context context;
	//分类名
	private String[] names;
	//描述
	private String [] descriptions; 
	/**
	 * @param classies
	 * @param context
	 */
	public ClassifyGridviewAdapter(Context context) {
		super();
		this.context = context;
		names = context.getResources().getStringArray(R.array.classify_name);
		descriptions = context.getResources().getStringArray(R.array.classify_decription);
	}

	@Override
	public int getCount() {
		return posters.length;
	}

	@Override
	public Object getItem(int position) {
		return posters[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_gridview_frg_classify, null);
			
			holder.iv_classify_poster = (ImageView) convertView.findViewById(R.id.iv_classify_poster);
			holder.tv_classify_name = (TextView) convertView.findViewById(R.id.tv_classify_name);
			holder.tv_classify_description = (TextView) convertView.findViewById(R.id.tv_classify_description);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.iv_classify_poster.setImageResource(posters[position]);
		holder.tv_classify_name.setText(names[position]);
		holder.tv_classify_description.setText(descriptions[position]);
		return convertView;
	}
	
	/**
	 * 
	 * 名称：ClassifyGridviewAdapter.java
	 * 描述：性能优化
	 * @author danding
	 * @version
	 * 2014-6-6
	 */
	public static class ViewHolder{
		//分类海报
		ImageView iv_classify_poster;
		//名
		TextView tv_classify_name;
		//描述
		TextView tv_classify_description;
		
	}
}
