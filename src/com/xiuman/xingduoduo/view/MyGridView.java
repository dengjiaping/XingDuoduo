package com.xiuman.xingduoduo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * @名称：MyGridView.java
 * @描述：不可滑动的GridView
 * @author danding
 * 2014-8-11
 */
public class MyGridView extends GridView {
	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
