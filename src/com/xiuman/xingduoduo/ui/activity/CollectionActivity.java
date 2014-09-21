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
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.CollectionGridViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskDeleteCollectionBack;
import com.xiuman.xingduoduo.callback.TaskGoodsListBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsOne;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshGridView;

/**
 * @名称：CollectionActivity.java
 * @描述：收藏列表界面
 * @author danding 2014-8-6
 */
public class CollectionActivity extends Base2Activity implements
		OnClickListener {

	/*-----------------------------------------组件------------------------------------*/
	// 返回
	private Button btn_back;
	// 编辑
	private Button btn_edit;
	// 标题
	private TextView tv_title;
	// 下拉刷新
	private PullToRefreshGridView pullgridview;
	// GridView
	private GridView collection_list;
	// 数据加载Dialog
	private LoadingDialog loadingdialog;
	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 收藏夹为空时显示的布局
	private LinearLayout llyt_collection_null_collection;
	// 去商城挑选
	private Button btn_collection_go2center;

	/*----------------------------------------数据-------------------------------------*/
	// 当前登录的用户
	private User user;

	// -----------------------ImageLoader-----------------------------
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();

	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	/*----------------------------------------Adapter-------------------------------------*/
	private CollectionGridViewAdapter adapter;

	// 数据请求-----------------------------------------------------------------------
	// 请求页码
	private int currentPage = 1;
	// 请求收藏列表
	private ActionValue<GoodsOne> value_collections;
	// 请求删除收藏
	private ActionValue<?> value_delete;
	// 请求得到的（商品列表）
	private ArrayList<GoodsOne> collection_get = new ArrayList<GoodsOne>();
	// 当前显示的收藏
	private ArrayList<GoodsOne> collection_current = new ArrayList<GoodsOne>();
	// 要删除收藏的商品信息
	private GoodsOne goodsone;

	int i =1;
	
	// 消息处理Handler
	@SuppressLint("HandlerLeak")
	@SuppressWarnings("unchecked")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 获取数据成功
				value_collections = (ActionValue<GoodsOne>) msg.obj;
				collection_get = (ArrayList<GoodsOne>) value_collections
						.getDatasource();
				
				if (!value_collections.isSuccess()) {// 收藏列表为空
					llyt_collection_null_collection.setVisibility(View.VISIBLE);
				} else {
					if (isUp) {// 下拉
						collection_current.clear();
						collection_current.addAll(collection_get);
						adapter = new CollectionGridViewAdapter(
								CollectionActivity.this, collection_current,
								options, imageLoader, false, handler);
						// 下拉加载完成
						pullgridview.onPullDownRefreshComplete();
						collection_list.setAdapter(adapter);
					} else {// 上拉
						collection_current.addAll(collection_get);
						adapter.notifyDataSetChanged();

						// 上拉刷新完成
						pullgridview.onPullUpRefreshComplete();
						// 设置是否有更多的数据
						if (currentPage < value_collections.getTotalpage()) {
							pullgridview.setHasMoreData(true);
						} else {
							pullgridview.setHasMoreData(false);
						}
					}

					
					llyt_collection_null_collection
							.setVisibility(View.INVISIBLE);
				}
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.VISIBLE);
				llyt_collection_null_collection.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.DELETE_COLLECTION_ADAPTER:// 更新收藏列表(adapter请求删除)
				goodsone = (GoodsOne) msg.obj;
				// 请求删除收藏
				HttpUrlProvider.getIntance().getDeleteCollection(
						CollectionActivity.this,
						new TaskDeleteCollectionBack(handler),
						URLConfig.DELETE_COLLECTION, goodsone.getId(),
						user.getUserId());
				loadingdialog.show();
				break;
			case AppConfig.DELETE_COLLECTION:// 请求接口
				value_delete = (ActionValue<?>) msg.obj;
				if (value_delete.isSuccess()) {// 删除成功
					ToastUtil.ToastView(CollectionActivity.this,
							value_delete.getMessage());
					if (collection_current.size() == 1) {
						collection_current.clear();
					} else {
						collection_current.remove(goodsone);
					}
					adapter.notifyDataSetChanged();
					if (collection_current.size() == 0) {
						llyt_collection_null_collection
								.setVisibility(View.VISIBLE);
					}
					// 删除本地数据库
					MyApplication.getInstance().deleteCollection(goodsone.getId());
				} else {
					ToastUtil.ToastView(CollectionActivity.this,
							value_delete.getMessage());
				}

				loadingdialog.dismiss();

				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.weiboitem_pic_loading) //
		// 在ImageView加载过程中显示图片
				.showImageForEmptyUri(R.drawable.onloading_goods_poster) // image连接地址为空时
				.showImageOnFail(R.drawable.onloading_goods_poster) // image加载失败
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();
		// 上级界面传递过来的用户数据
		user = (User) getIntent().getExtras().getSerializable("user");
	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_common_back2);
		btn_edit = (Button) findViewById(R.id.btn_common_right2);
		tv_title = (TextView) findViewById(R.id.tv_common_title2);
		pullgridview = (PullToRefreshGridView) findViewById(R.id.pullgridview_collections);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_collection_null_collection = (LinearLayout) findViewById(R.id.llyt_collection_null_collection);
		btn_collection_go2center = (Button) findViewById(R.id.btn_collection_go2center);
		// 上拉加载是否可用
		pullgridview.setPullLoadEnabled(true);
		// 是否滚动到底部自动加载
		pullgridview.setScrollLoadEnabled(true);

		// 获取可刷新的GridView
		collection_list = pullgridview.getRefreshableView();

		// 设置GridView属性
		collection_list.setFadingEdgeLength(0);
		collection_list.setHorizontalSpacing(5);
		collection_list.setVerticalSpacing(5);
		collection_list.setNumColumns(3);
		collection_list.setSelector(R.drawable.whole_bg_selector);
		collection_list.setScrollbarFadingEnabled(true);
		collection_list.setBackgroundColor(getResources().getColor(
				R.color.color_bg));
	}

	@Override
	protected void initUI() {
		tv_title.setText(R.string.collection_title);
		loadingdialog = new LoadingDialog(CollectionActivity.this);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_edit.setOnClickListener(this);
		btn_collection_go2center.setOnClickListener(this);

		// 进入商品详情界面
		collection_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = collection_list.getItemAtPosition(position);
				// 将商品数据传递给
				if (obj instanceof GoodsOne) {
					GoodsOne goods_one = (GoodsOne) obj;
					Intent intent = new Intent(CollectionActivity.this,
							GoodsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("goods_id", goods_one.getId());
					bundle.putSerializable("goods_one", goods_one);
					bundle.putInt("pic_tag", 0);
					intent.putExtras(bundle);
					startActivity(intent);
					overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}
			}
		});

		pullgridview.setOnRefreshListener(new OnRefreshListener<GridView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				isUp = true;
				currentPage = 1;
				initFirstData(currentPage);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				isUp = false;
				if (value_collections.getPage() < value_collections
						.getTotalpage()) {
					currentPage += 1;
					initFirstData(currentPage);
				} else {
					ToastUtil.ToastView(CollectionActivity.this, getResources()
							.getString(R.string.no_more));
					// 下拉加载完成
					pullgridview.onPullDownRefreshComplete();
					// 上拉刷新完成
					pullgridview.onPullUpRefreshComplete();
					// 设置是否有更多的数据
					pullgridview.setHasMoreData(false);
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		// 加载数据，测试数据，添加操作
		pullgridview.doPullRefreshing(true, 500);
		// 设置刷新时间
		TimeUtil.setLastUpdateTime2(pullgridview);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back2:// 返回
			finish();
			break;
		case R.id.btn_common_right2:// 编辑收藏列表
			if (btn_edit.getText().toString().equals("编辑")) {
				adapter.flag = true;
				btn_edit.setText(R.string.collection_title_right_orver);
				adapter.notifyDataSetChanged();
			} else {
				adapter.flag = false;
				btn_edit.setText(R.string.collection_title_right_n);
				adapter.notifyDataSetChanged();
			}
			break;
		case R.id.btn_collection_go2center:// 去商城挑选
			Intent intent_main = new Intent(CollectionActivity.this,
					MainActivity.class);
			startActivity(intent_main);
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData(int currentPage) {
		String UID = user.getUserId();
		// 请求数据
		HttpUrlProvider.getIntance().getCollections(this,
				new TaskGoodsListBack(handler), URLConfig.COLLECTIONS,
				currentPage, UID);
		loadingdialog.show();

	}

}
