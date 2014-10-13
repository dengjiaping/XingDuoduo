package com.xiuman.xingduoduo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
/**
 * @名称：FragmentGoodsDiscuss.java
 * @描述：商品评价
 * @author danding
 * @时间 2014-10-13
 */
public class FragmentGoodsDiscuss extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_discuss, container,
				false);
		initData();
		findViewById(view);
		initUI();
		setListener();
		return view;
	}
	@Override
	protected void initData() {

	}

	@Override
	protected void findViewById(View view) {

	}

	@Override
	protected void initUI() {

	}

	@Override
	protected void setListener() {

	}

}
