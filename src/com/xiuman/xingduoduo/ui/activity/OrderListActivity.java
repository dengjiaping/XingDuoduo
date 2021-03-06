package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.OrderHistoryListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskOrderHistoryBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.OrderSimple;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.util.options.CustomOptions;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshListView;

/**
 * @名称：OrderListActivity.java
 * @描述：订单列表界面
 * @author danding 2014-8-4
 */
public class OrderListActivity extends Base2Activity implements OnClickListener {

	/*------------------------------------组件-------------------------------------*/
	// 返回
	private Button btn_back;
	// 右侧(隐藏)
	private Button btn_right;
	// 标题栏
	private TextView tv_title;
	// 网络未连接显示的布局
	private LinearLayout llyt_network_error;
	// 列表为空时显示的布局
	private LinearLayout llyt_order_null;
	// 下拉刷新列表
	private PullToRefreshListView pulllistview;
	// ListView
	private ListView lv_order;

	// -----------------------ImageLoader-----------------------------
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*----------------------------------数据----------------------------------*/
	// 当前登录的用户
	private User user;

	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	// 数据加载Dialog
	private LoadingDialog loadingdialog;

	// ---------------------------------Adapter---------------------------------
	// 数据Adapter
	private OrderHistoryListViewAdapter adapter;

	/*-----------------------------------请求接口-------------------------------*/
	// 消息返回（订单列表）
	private ActionValue<OrderSimple> value_order = new ActionValue<OrderSimple>();
	// 当前显示的订单列表
	private ArrayList<OrderSimple> orders_current = new ArrayList<OrderSimple>();
	// 请求得到的订单列表
	private ArrayList<OrderSimple> orders_get = new ArrayList<OrderSimple>();
	// 当前请求页码
	private int currentPage = 1;

	// 查看详情的位置
	private int check_position;

	// 消息处理Handler
	@SuppressLint("HandlerLeak")
	@SuppressWarnings("unchecked")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 获取数据成功
				value_order = (ActionValue<OrderSimple>) msg.obj;
				orders_get = (ArrayList<OrderSimple>) value_order
						.getDatasource();

				if (!value_order.isSuccess()) {// 获取数据为空
					llyt_order_null.setVisibility(View.VISIBLE);
				} else {

					if (isUp) {// 下拉
						orders_current = orders_get;
						adapter = new OrderHistoryListViewAdapter(
								OrderListActivity.this, orders_current,
								options, imageLoader);
						// 下拉加载完成
						pulllistview.onPullDownRefreshComplete();
						lv_order.setAdapter(adapter);
					} else {// 上拉
						orders_current.addAll(orders_get);
						adapter.notifyDataSetChanged();

						// 上拉刷新完成
						pulllistview.onPullUpRefreshComplete();
						// 设置是否有更多的数据
						if (currentPage < value_order.getTotalpage()) {
							pulllistview.setHasMoreData(true);
						} else {
							pulllistview.setHasMoreData(false);
						}
					}

					llyt_order_null.setVisibility(View.INVISIBLE);
				}

				loadingdialog.dismiss(OrderListActivity.this);
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss(OrderListActivity.this);
				llyt_network_error.setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_list);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {
		options = CustomOptions.getOptions2();

		// 获取用户信息
		user = (User) getIntent().getExtras().getSerializable("user");

	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_order_null = (LinearLayout) findViewById(R.id.llyt_order_null);

		pulllistview = (PullToRefreshListView) findViewById(R.id.pulllistview_order_list);
		pulllistview.setPullLoadEnabled(true);
		pulllistview.setScrollLoadEnabled(true);
		lv_order = pulllistview.getRefreshableView();

		lv_order.setDividerHeight(10);
		lv_order.setDivider(getResources().getDrawable(
				R.drawable.drawable_transparent));
		lv_order.setSelector(R.color.color_white);
		lv_order.setHeaderDividersEnabled(true);

	}

	@Override
	protected void initUI() {
		// 隐藏右侧按钮
		btn_right.setVisibility(View.INVISIBLE);
		// 设置标题
		tv_title.setText(R.string.order_history_title);
		loadingdialog = new LoadingDialog(OrderListActivity.this);

		// 初次加载数据
		// 设置刷新时间
		pulllistview.doPullRefreshing(true, 500);
		TimeUtil.setLastUpdateTime(pulllistview);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		llyt_network_error.setOnClickListener(this);

		// 查看订单详情
		lv_order.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_order.getItemAtPosition(position);
				if (obj instanceof OrderSimple) {
					check_position = position;
					OrderSimple order = (OrderSimple) obj;

					Intent intent = new Intent(OrderListActivity.this,
							OrderInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("order_id", order.getId());
					intent.putExtras(bundle);
					startActivityForResult(intent, AppConfig.REQUEST_CODE);
					overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}

			}
		});

		// 下拉上啦刷新
		pulllistview.setOnRefreshListener(new OnRefreshListener<ListView>() {

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
				if (value_order.getPage() < value_order.getTotalpage()) {
					currentPage += 1;
					initFirstData(currentPage);
				} else {
					ToastUtil.ToastView(OrderListActivity.this, getResources()
							.getString(R.string.no_more));
					// 下拉加载完成
					pulllistview.onPullDownRefreshComplete();
					// 上拉刷新完成
					pulllistview.onPullUpRefreshComplete();
					// 设置是否有更多的数据
					pulllistview.setHasMoreData(false);
				}
			}
		});
	}

	/**
	 * @描述：初次加载或者恢复使用时刷新界面 2014-8-14
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if(loadingdialog==null){
			loadingdialog = new LoadingDialog(this);
		}

	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;
		case R.id.llyt_network_error:// 设置网络后重新请求数据
			isUp = true;
			currentPage = 1;
			initFirstData(currentPage);// 请求首页数据
			break;
		}
	}

	/**
	 * 加载数据
	 */
	private void initFirstData(int page) {
		// 加载数据接口
		loadingdialog.show(OrderListActivity.this);
		HttpUrlProvider.getIntance().getOrderHistory(this,
				new TaskOrderHistoryBack(handler), URLConfig.ORDER_HISTORY,
				page, user.getUserId());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == AppConfig.REQUEST_CODE) {
			if (resultCode == AppConfig.RESULT_CODE_OK) {// 删除刷新
				orders_current.remove(check_position);
				adapter.notifyDataSetChanged();
				if (orders_current.size() == 0) {
					llyt_order_null.setVisibility(View.VISIBLE);
				}
			} else if (resultCode == AppConfig.RESULT_CODE_OK_2) {// 评价成功
				pulllistview.doPullRefreshing(true, 500);
				TimeUtil.setLastUpdateTime(pulllistview);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss(OrderListActivity.this);
		loadingdialog = null;
		imageLoader.stop();
		imageLoader.clearMemoryCache();
	}
}
