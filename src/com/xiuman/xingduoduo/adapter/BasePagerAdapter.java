package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.activity.MainActivity;
import com.xiuman.xingduoduo.util.SharedPreUtils;

/**
 * @名称：BasePagerAdapter.java
 * @描述：引导页适配器
 * @author danding
 * 2014-10-11
 */
public class BasePagerAdapter extends PagerAdapter{
	private List<View> views=new ArrayList<View>();
	private Context context;
	public BasePagerAdapter(List<View> views,Context context){
		this.views=views;
		this.context = context;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position));
		if(position==views.size()-1){
			views.get(position).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context, MainActivity.class));
					((Activity) context).finish();
					((Activity) context).overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
					SharedPreUtils.setBoolean(context, false,"guide", "first_start");
				}
			});
		}
		return views.get(position);
	}}
