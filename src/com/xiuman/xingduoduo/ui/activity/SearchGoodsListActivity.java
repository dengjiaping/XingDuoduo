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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.ClassifyGoodsGridViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskGoodsListBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsOne;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.util.options.CustomOptions;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.floatbutton.FloatingActionButton;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshGridView;

/**
 * 
 * @名称：SearchGoodsListActivity.java
 * @描述：搜索商品列表
 * @author danding
 * @version
 * @date：2014-7-28
 */
public class SearchGoodsListActivity extends Base2Activity implements
		OnClickListener {
	/*----------------------------------组件---------------------------------*/
	private FloatingActionButton button_floating_action;
	// 标题栏
	// 返回
	private Button btn_back;
	// 标题
	private TextView tv_title;
	// 刷新
	private Button btn_right;
	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 获取到的数据为空的时候显示的布局
	private LinearLayout llyt_goods_null;

	// ----------------下拉刷新的GridView----------------------------
	private PullToRefreshGridView pullgridview_classify_goods_list;
	// GridView
	private GridView gridview_classify_goods_list;
	// Adapter
	private ClassifyGoodsGridViewAdapter adapter;

	// -----------------------ImageLoader-----------------------------
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*----------------------------------数据---------------------------------*/
	// 从上级界面接收到的搜索关键字
	private String keyword;
	// 请求接口得到的商品数据
	private ActionValue<GoodsOne> value = new ActionValue<GoodsOne>();
	// （商品列表）
	private ArrayList<GoodsOne> goods_get = new ArrayList<GoodsOne>();
	// 当前现实的商品列表
	private ArrayList<GoodsOne> goods_current = new ArrayList<GoodsOne>();
	// 当前页
	private int currentPage = 1;

	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	// 数据加载Dialog
	private LoadingDialog loadingdialog;

	// 消息处理Handler
	@SuppressLint("HandlerLeak")
	@SuppressWarnings("unchecked")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 获取数据成功

				value = (ActionValue<GoodsOne>) msg.obj;

				goods_get = (ArrayList<GoodsOne>) value.getDatasource();
				if (!value.isSuccess()) {
					llyt_goods_null.setVisibility(View.VISIBLE);
				} else {
					if (isUp) {// 下拉
						goods_current = goods_get;
						adapter = new ClassifyGoodsGridViewAdapter(
								SearchGoodsListActivity.this, goods_current,
								options, imageLoader);
						// 下拉加载完成
						pullgridview_classify_goods_list
								.onPullDownRefreshComplete();
						gridview_classify_goods_list.setAdapter(adapter);
					} else {// 上拉
						goods_current.addAll(goods_get);
						adapter.notifyDataSetChanged();

						// 上拉刷新完成
						pullgridview_classify_goods_list
								.onPullUpRefreshComplete();
						// 设置是否有更多的数据
						if (currentPage < value.getTotalpage()) {
							pullgridview_classify_goods_list
									.setHasMoreData(true);
						} else {
							pullgridview_classify_goods_list
									.setHasMoreData(false);
						}
					}
					TimeUtil.setLastUpdateTime2(pullgridview_classify_goods_list);

					llyt_goods_null.setVisibility(View.INVISIBLE);
				}
				loadingdialog.dismiss(SearchGoodsListActivity.this);
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss(SearchGoodsListActivity.this);
				llyt_network_error.setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center_classify);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {
		options = CustomOptions.getOptions2();

		// 获取Intent传递的数据
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		keyword = bundle.getString("keyword");
		currentPage = 1;
	}

	@Override
	protected void findViewById() {
		button_floating_action = (FloatingActionButton) findViewById(R.id.button_floating_action);
		// 标题栏
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_goods_null = (LinearLayout) findViewById(R.id.llyt_goods_null);

		// 刷新控件
		pullgridview_classify_goods_list = (PullToRefreshGridView) findViewById(R.id.pullgridview_center_classify_goods_list);
		// 上拉加载是否可用
		pullgridview_classify_goods_list.setPullLoadEnabled(true);
		// 是否滚动到底部自动加载
		pullgridview_classify_goods_list.setScrollLoadEnabled(true);

		// 获取可刷新的GridView
		gridview_classify_goods_list = pullgridview_classify_goods_list
				.getRefreshableView();

		// 设置GridView属性
		// gridview_classify_goods_list.setCacheColorHint(Color.TRANSPARENT);
		gridview_classify_goods_list.setFadingEdgeLength(0);
		gridview_classify_goods_list.setHorizontalSpacing(1);
		gridview_classify_goods_list.setVerticalSpacing(1);
		gridview_classify_goods_list.setNumColumns(2);
		gridview_classify_goods_list.setSelector(R.drawable.whole_bg_selector);
		gridview_classify_goods_list.setScrollbarFadingEnabled(true);
		gridview_classify_goods_list.setBackgroundColor(getResources()
				.getColor(R.color.color_bg));
		button_floating_action.attachToListView(gridview_classify_goods_list);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText(keyword);
		loadingdialog = new LoadingDialog(SearchGoodsListActivity.this);
		// 加载数据，测试数据，添加操作
		initFirstData(currentPage, keyword);
		// 设置刷新时间
		TimeUtil.setLastUpdateTime2(pullgridview_classify_goods_list);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		llyt_network_error.setOnClickListener(this);

		// 设置上拉下拉监听
		pullgridview_classify_goods_list
				.setOnRefreshListener(new OnRefreshListener<GridView>() {
					/**
					 * @描述：下拉刷新
					 * @date：2014-6-25
					 * @param refreshView
					 */
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						isUp = true;
						currentPage = 1;
						initFirstData(currentPage, keyword);
					}

					/**
					 * @描述：上拉加载
					 * @date：2014-6-25
					 * @param refreshView
					 */
					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						isUp = false;
						if (value.getPage() < value.getTotalpage()) {
							currentPage += 1;
							initFirstData(currentPage, keyword);
						} else {
							ToastUtil.ToastView(SearchGoodsListActivity.this,
									getResources().getString(R.string.no_more));
							// 下拉加载完成
							pullgridview_classify_goods_list
									.onPullDownRefreshComplete();
							// 上拉刷新完成
							pullgridview_classify_goods_list
									.onPullUpRefreshComplete();
							// 设置是否有更多的数据
							pullgridview_classify_goods_list
									.setHasMoreData(false);
						}
					}
				});
		// GridView item开商品详情界面
		gridview_classify_goods_list
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Object obj = gridview_classify_goods_list
								.getItemAtPosition(position);
						// 将商品数据传递给
						if (obj instanceof GoodsOne) {
							GoodsOne goods_one = (GoodsOne) obj;
							Intent intent = new Intent(
									SearchGoodsListActivity.this,
									GoodsActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("goods_one", goods_one);
							bundle.putSerializable("goods_id",
									goods_one.getId());
							intent.putExtras(bundle);
							startActivity(intent);
							overridePendingTransition(
									R.anim.translate_horizontal_start_in,
									R.anim.translate_horizontal_start_out);
						}

					}
				});
		button_floating_action.setOnClickListener(this);
	}

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
		case R.id.btn_common_back:
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;
		case R.id.llyt_network_error:// 重新加载数据
			isUp = true;
			currentPage = 1;
			initFirstData(currentPage, keyword);
			break;
		case R.id.button_floating_action:// 滚动到顶部
//			gridview_classify_goods_list.smoothScrollToPosition(0);
			gridview_classify_goods_list.requestFocusFromTouch();
			gridview_classify_goods_list.setSelection(0);
			break;
		}
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData(int currentPage, String keyword) {
		// 请求数据
		HttpUrlProvider.getIntance().getSearchGoods(this,
				new TaskGoodsListBack(handler), URLConfig.SEARCH, currentPage,
				keyword);
		loadingdialog.show(SearchGoodsListActivity.this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss(SearchGoodsListActivity.this);
		loadingdialog = null;
		imageLoader.stop();
		imageLoader.clearMemoryCache();
	}
}
