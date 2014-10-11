package com.xiuman.xingduoduo.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * 名称：SearchViewPagerAdapter.java 描述：搜索界面(热门、最近搜索切换)
 * 
 * @author danding
 * @version 2014-6-10
 */
public class SearchViewPagerAdapter extends PagerAdapter {

	private List<Fragment> fragments;
	private FragmentManager fragmentManager;

	public SearchViewPagerAdapter(List<Fragment> fragments, Context context,
			FragmentManager fragmentManager) {
		super();
		this.fragments = fragments;
		this.fragmentManager = fragmentManager;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = fragments.get(position);
		if (!fragment.isAdded()) { // 如果fragment还没有added
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(fragment, fragment.getClass().getSimpleName());
			ft.commit();
			/**
			 * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
			 * 会在进程的主线程中,用异步的方式来执行。 如果想要立即执行这个等待中的操作,就要调用这个方法(只能在主线程中调用)。
			 * 要注意的是,所有的回调和相关的行为都会在这个调用中被执行完成,因此要仔细确认这个方法的调用位置。
			 */
			fragmentManager.executePendingTransactions();
		}

		if (fragment.getView().getParent() == null) {
			container.addView(fragment.getView()); // 为viewpager增加布局
		}
		return fragment.getView();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(fragments.get(position).getView());
	}
}
