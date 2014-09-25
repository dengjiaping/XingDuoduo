package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.GoodsDiscussListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskGoodsDiscussListBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsDiscuss;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshListView;

/**
 * @名称：GoodsDiscussActivity.java
 * @描述：商品评论界面
 * @author danding 2014-8-7
 */
public class GoodsDiscussActivity extends Base2Activity implements
		OnClickListener {

	/*--------------------------------------组件--------------------------------*/
	// 返回
	private Button btn_back;
	// 右侧(隐藏)
	private Button btn_right;
	// 标题栏
	private TextView tv_title;
	// 下拉刷新ListView
	private PullToRefreshListView pulllistview_discuss;
	// ListView
	private ListView lv_discuss;
	// 网络未连接显示的布局
	private LinearLayout llyt_network_error;
	// 评论为空的时候显示的布局
	private RelativeLayout rlyt_discuss_null;
	// 请求数据时显示的diaolog
	private LoadingDialog loadingdialog;

	/*----------------------------------标记-------------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	/*----------------------------------数据-------------------------------------*/
	// 从上级界面接收到的商品id
	private String goods_id;

	/*----------------------------------Adapter-----------------------------------*/
	// 评论Adapter
	private GoodsDiscussListViewAdapter adapter;

	/*---------------------------------请求数据-----------------------------------*/
	// 请求页码
	private int currentPage = 1;
	// 请求获取的评价列表
	private ArrayList<GoodsDiscuss> discusses_get = new ArrayList<GoodsDiscuss>();
	// 当前显示的评价列表
	private ArrayList<GoodsDiscuss> discusses_current = new ArrayList<GoodsDiscuss>();
	// 请求结果
	private ActionValue<GoodsDiscuss> value_discuss;

	// 数据处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功，设置数据

				value_discuss = (ActionValue<GoodsDiscuss>) msg.obj;
				discusses_get = (ArrayList<GoodsDiscuss>) value_discuss
						.getDatasource();
				if (!value_discuss.isSuccess()) {// 数据为空
					rlyt_discuss_null.setVisibility(View.VISIBLE);
					// 下拉加载完成
					pulllistview_discuss.onPullDownRefreshComplete();
					// 上拉刷新完成
					pulllistview_discuss.onPullUpRefreshComplete();
				} else {
					if (isUp) {// 下拉
						discusses_current = discusses_get;
						adapter = new GoodsDiscussListViewAdapter(
								GoodsDiscussActivity.this, discusses_current);
						// 下拉加载完成
						pulllistview_discuss.onPullDownRefreshComplete();
						lv_discuss.setAdapter(adapter);
					} else {// 上拉
						discusses_current.addAll(discusses_get);
						adapter.notifyDataSetChanged();

						// 上拉刷新完成
						pulllistview_discuss.onPullUpRefreshComplete();
						// 设置是否有更多的数据
						if (currentPage < value_discuss.getTotalpage()) {
							pulllistview_discuss.setHasMoreData(true);
						} else {
							pulllistview_discuss.setHasMoreData(false);
						}
					}
					TimeUtil.setLastUpdateTime(pulllistview_discuss);
					
					rlyt_discuss_null.setVisibility(View.INVISIBLE);
				}

				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 请求数据失败(网络)

				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.VISIBLE);
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_discuss);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {
		goods_id = getIntent().getExtras().getString("goods_id");
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		rlyt_discuss_null = (RelativeLayout) findViewById(R.id.rlyt_discuss_null_discuss);

		pulllistview_discuss = (PullToRefreshListView) findViewById(R.id.pulllistview_discuss);

		lv_discuss = pulllistview_discuss.getRefreshableView();
		pulllistview_discuss.setPullLoadEnabled(false);
		pulllistview_discuss.setScrollLoadEnabled(false);

		lv_discuss.setCacheColorHint(Color.TRANSPARENT);
		lv_discuss
				.setDivider(getResources().getDrawable(R.drawable.line_small));
		lv_discuss.setSelector(new ColorDrawable(Color.TRANSPARENT));

	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText(R.string.discuss_title);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);

		// 设置监听
		pulllistview_discuss
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						isUp = true;
						currentPage = 1;
						initFirstData(currentPage);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						isUp = false;
						if (value_discuss.getPage() < value_discuss
								.getTotalpage()) {
							currentPage += 1;
							initFirstData(currentPage);
						} else {
							ToastUtil.ToastView(GoodsDiscussActivity.this,
									getResources().getString(R.string.no_more));
							// 下拉加载完成
							pulllistview_discuss.onPullDownRefreshComplete();
							// 上拉刷新完成
							pulllistview_discuss.onPullUpRefreshComplete();
							// 设置是否有更多的数据
							pulllistview_discuss.setHasMoreData(false);
						}
					}
				});
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:// 返回
			finish();

			break;

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 加载数据
		pulllistview_discuss.doPullRefreshing(true, 500);
		// 设置刷新时间
		TimeUtil.setLastUpdateTime(pulllistview_discuss);
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData(int currentPage) {
		loadingdialog.show(GoodsDiscussActivity.this);
		// 请求获取商品评价
		HttpUrlProvider.getIntance().getGoodsDiscuss(this,
				new TaskGoodsDiscussListBack(handler),
				URLConfig.GOODS_DISCUSS_LIST, currentPage, goods_id);
	}

}
