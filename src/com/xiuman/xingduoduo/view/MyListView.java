package com.xiuman.xingduoduo.view;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 
 * @名称：MyListView.java
 * @描述：自定义ListView(解决ScrollView嵌套ListView的问题)
 * @author danding
 * @version
 * @date：2014-7-28
 */
public class MyListView extends ListView {

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 该方法解决嵌套
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			this.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, false));
			break;
		}
		return super.onTouchEvent(ev);
	}
}
