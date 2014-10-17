package com.xiuman.xingduoduo.adapter;

import com.xiuman.xingduoduo.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
/**
 * @名称：ClassifyListViewAdapter.java
 * @描述：一级分类Adapter
 * @author danding
 * @时间 2014-10-16
 */
public class ClassifyListViewAdapter extends BaseAdapter {
	
	private Context context;
//	private ArrayList<E>
	//一级分类图
	private int[] aCatogory;
	
	
	public ClassifyListViewAdapter(Context context, int[] aCatogory) {
	super();
	this.context = context;
	this.aCatogory = aCatogory;
}

	@Override
	public int getCount() {
		return aCatogory.length;
	}

	@Override
	public Object getItem(int position) {
		return aCatogory[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_classify, null);
			holder = new ViewHolder();
			holder.iv_classify = (ImageView) convertView.findViewById(R.id.iv_classify);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv_classify.setImageResource(aCatogory[position]);
		return convertView;
	}
	
	


	static class ViewHolder{
		ImageView iv_classify;
	}
}
