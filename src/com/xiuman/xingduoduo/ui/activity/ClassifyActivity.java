package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
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
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshGridView;

/**
 * 
 * @名称：ClassifyActivity.java
 * @描述：分类商品显示界面
 * @author danding
 * @version 2014-6-9
 */
public class ClassifyActivity extends Base2Activity implements OnClickListener,
		android.widget.RadioGroup.OnCheckedChangeListener {

	/*----------------------------------组件---------------------------------*/
	// 标题栏
	// 返回
	private Button btn_back;
	// 标题
	private TextView tv_title;
	// 刷新
	private Button btn_refresh;
	// 商品排序
	private RadioGroup radiogroup_goods_sort;
	// 第一个综合
	private RadioButton rbtn_classify_sort_multiple;
	//第四个价格排序
	private RadioButton rbtn_classify_sort_jiage;
	// 数据加载Dialog
	private LoadingDialog loadingdialog;
	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	//商品列表为空时显示的布局
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
	// 接收到的分类Id
	private String classify_id;
	//接收到的分类名
	private String classify_name;
	// 综合
	private List<GoodsOne> goods_zonghe = new ArrayList<GoodsOne>();
	// 销量
	private List<GoodsOne> goods_xiaoliang = new ArrayList<GoodsOne>();
	// 时间
	private List<GoodsOne> goods_shijian = new ArrayList<GoodsOne>();
	// 价格
	private List<GoodsOne> goods_jiage = new ArrayList<GoodsOne>();
	// 当前显示的商品排序
	private List<GoodsOne> goods_current = new ArrayList<GoodsOne>();
	// 请求得来的商品(各种排序)
	private List<GoodsOne> goods_get = new ArrayList<GoodsOne>();
	// 请求接口接收到的商品数据
	private ActionValue<GoodsOne> value;

	/*-----------------------------------数据请求相关--------------------------*/
	// 当前请求的接口的后缀(默认为综合接口后缀)
	private String sort_url;
	// 当前请求的数据为第几页(默认1页)
	private int currentPage = 1;
	// 当前请求接口数字代表(1:综合，2:销量，3:时间，4:价格)
	private int sort_tag = 1;
	//价格排序方式(desc降序，asc升序)默认升序
	private String sort_style = "asc";

	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	// 数据处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功，设置数据(如果是下拉，则重新请求第一页数据，如果上拉则将上拉请求得到的数据加到当前显示的数量集合中)
				value = (ActionValue<GoodsOne>) msg.obj;
				goods_get = (ArrayList<GoodsOne>) value.getDatasource();
				if (value.isSuccess()) {
					if (isUp) {// 下拉重新设置
						switch (sort_tag) {// 根据排序类型设置当前
						case 1:// 综合
							goods_zonghe = goods_get;
							goods_current = goods_zonghe;
							break;
						case 2:// 销量
							goods_xiaoliang = goods_get;
							goods_current = goods_xiaoliang;
							break;
						case 3:// 时间
							goods_shijian = goods_get;
							goods_current = goods_shijian;
							break;
						case 4:// 价格
							goods_jiage = goods_get;
							goods_current = goods_jiage;
							break;
						}
						adapter = new ClassifyGoodsGridViewAdapter(
								ClassifyActivity.this, goods_current, options,
								imageLoader);
						// 下拉加载完成
						pullgridview_classify_goods_list
								.onPullDownRefreshComplete();
						// 上拉刷新完成
						pullgridview_classify_goods_list
								.onPullUpRefreshComplete();
						// 设置是否有更多的数据
						TimeUtil.setLastUpdateTime2(pullgridview_classify_goods_list);
						gridview_classify_goods_list.setAdapter(adapter);
					} else {// 上拉刷新
						switch (sort_tag) {// 根据排序类型设置当前
						case 1:// 综合
							goods_zonghe.addAll(goods_get);
							goods_current = goods_zonghe;
							break;
						case 2:// 销量
							goods_xiaoliang.addAll(goods_get);
							goods_current = goods_xiaoliang;
							break;
						case 3:// 时间
							goods_shijian.addAll(goods_get);
							goods_current = goods_shijian;
							break;
						case 4:// 价格
							goods_jiage.addAll(goods_get);
							goods_current = goods_jiage;
							break;
						}
//						boolean hasMore = false;
//						if (value.getPage() < value.getTotalpage()) {
//							hasMore = true;
//						}
						adapter.notifyDataSetChanged();
						// 下拉加载完成
//						pullgridview_classify_goods_list
//								.onPullDownRefreshComplete();
						// 上拉刷新完成
						pullgridview_classify_goods_list
								.onPullUpRefreshComplete();
//						// 设置是否有更多的数据
//						pullgridview_classify_goods_list
//								.setHasMoreData(hasMore);
						TimeUtil.setLastUpdateTime2(pullgridview_classify_goods_list);
					}

					
					llyt_goods_null.setVisibility(View.INVISIBLE);
				}else{
					llyt_goods_null.setVisibility(View.VISIBLE);
				}
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 请求数据失败(网络)

				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classify);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	/**
	 * 数据初始化
	 */
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
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.NONE).build();

		// 获取Intent传递的数据
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		classify_id = bundle.getString("classify_id");
		classify_name = bundle.getString("classify_name");
		// 默认综合排序
		sort_url = URLConfig.SORT_ZONGHE;
		sort_tag = 1;
	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById() {
		// 标题栏
		btn_back = (Button) findViewById(R.id.btn_back_3);
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		tv_title = (TextView) findViewById(R.id.tv_classify_title_name);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_goods_null = (LinearLayout) findViewById(R.id.llyt_goods_null);
		// 商品排序
		radiogroup_goods_sort = (RadioGroup) findViewById(R.id.radiogroup_goods_sort);
		rbtn_classify_sort_multiple = (RadioButton) findViewById(R.id.rbtn_classify_sort_multiple);
		rbtn_classify_sort_jiage = (RadioButton) findViewById(R.id.rbtn_classify_sort_price);
		
		// 刷新控件
		pullgridview_classify_goods_list = (PullToRefreshGridView) findViewById(R.id.pullgridview_classify_goods_list);
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
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		// 分类名
		tv_title.setText(classify_name);

		// 默认选中综合
		rbtn_classify_sort_multiple.setChecked(true);
		
		loadingdialog = new LoadingDialog(ClassifyActivity.this);
		// 加载数据，测试数据，添加操作
		initFirstData(sort_url, currentPage);
		// 设置刷新时间
		TimeUtil.setLastUpdateTime2(pullgridview_classify_goods_list);
		// 首次进入界面刷新
		// pullgridview_classify_goods_list.doPullRefreshing(true, 500);
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_refresh.setOnClickListener(this);
		llyt_network_error.setOnClickListener(this);
		// 排序
		radiogroup_goods_sort.setOnCheckedChangeListener(this);

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
						initFirstData(sort_url, currentPage);
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
							initFirstData(sort_url, currentPage);
						} else {
							ToastUtil.ToastView(ClassifyActivity.this,
									getResources().getString(R.string.no_more));
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
							Intent intent = new Intent(ClassifyActivity.this,
									GoodsActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("goods_one", goods_one);
							bundle.putString("goods_id", goods_one.getId());
							intent.putExtras(bundle);
							startActivity(intent);
							overridePendingTransition(
									R.anim.translate_horizontal_start_in,
									R.anim.translate_horizontal_start_out);
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
		case R.id.btn_back_3:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;
		case R.id.btn_refresh:// 刷新
			isUp = true;
			currentPage = 1;
			initFirstData(sort_url, currentPage);
			break;
		case R.id.llyt_network_error:// 重新连接网络请求数据
			isUp = true;
			currentPage = 1;
			initFirstData(sort_url, currentPage);
			break;

		}
	}

	/**
	 * 排序状态切换
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(checkedId==R.id.rbtn_classify_sort_price){
			rbtn_classify_sort_jiage.setChecked(false);
			if(rbtn_classify_sort_jiage.isSelected()){
				if(sort_style.equals("asc")){
					sort_style = "desc";
				}else if(sort_style.equals("desc")){
					sort_style = "asc";
				}
			}
			
			rbtn_classify_sort_jiage.setSelected(true);
		}
		switch (checkedId) {
		case R.id.rbtn_classify_sort_multiple:// 综合
			sort_tag = 1;
			currentPage = 1;
			sort_url = URLConfig.SORT_ZONGHE;
			isUp = true;
//			if (goods_zonghe.size() == 0) {// 首次加载排序
				initFirstData(sort_url, currentPage);
//			} else {
//				if (goods_zonghe.size() > 20) {
//					for(int i=0;i<20;i++){
//						goods_current.add(goods_zonghe.get(i));
//					}
//				} else {
//					for(int i=0;i<goods_zonghe.size();i++){
//						goods_current.add(goods_zonghe.get(i));
//					}
//				}
//				adapter.notifyDataSetChanged();
//			}
				rbtn_classify_sort_jiage.setSelected(false);
			break;
		case R.id.rbtn_classify_sort_sales:// 销量
			sort_tag = 2;
			currentPage = 1;
			sort_url = URLConfig.SORT_XIAOLIANG;
			isUp = true;
//			if (goods_xiaoliang.size() == 0) {// 首次加载排序
				initFirstData(sort_url, currentPage);
//			} else {
//				if (goods_xiaoliang.size() > 20) {
//					for(int i=0;i<20;i++){
//						goods_current.add(goods_xiaoliang.get(i));
//					}
//				} else {
//					for(int i=0;i<goods_zonghe.size();i++){
//						goods_current.add(goods_xiaoliang.get(i));
//					}
//				}
//				adapter.notifyDataSetChanged();
//			}
				rbtn_classify_sort_jiage.setSelected(false);
			break;
		case R.id.rbtn_classify_sort_time:// 时间
			sort_tag = 3;
			currentPage = 1;
			sort_url = URLConfig.SORT_SHIJIAN;
			isUp = true;
//			if (goods_shijian.size() == 0) {// 首次加载排序
				initFirstData(sort_url, currentPage);
//			} else {
//				goods_current.clear();
//				if (goods_shijian.size() > 20) {
//					for(int i=0;i<20;i++){
//						goods_current.add(goods_shijian.get(i));
//					}
//					
//				} else {
//					for(int i=0;i<goods_shijian.size();i++){
//						goods_current.add(goods_shijian.get(i));
//					}
//				}
//				adapter.notifyDataSetChanged();
//			}
				rbtn_classify_sort_jiage.setSelected(false);
			break;
		case R.id.rbtn_classify_sort_price:// 价格
			sort_tag = 4;
			currentPage = 1;
			sort_url = URLConfig.SORT_JIAGE;
			isUp = true;
//			if (goods_jiage.size() == 0) {// 首次加载排序
				initFirstData(sort_url, currentPage);
//			} else {
//				if (goods_jiage.size() > 20) {
//					for(int i=0;i<20;i++){
//						goods_current.add(goods_jiage.get(i));
//					}
//				} else {
//					for(int i=0;i<goods_zonghe.size();i++){
//						goods_current.add(goods_jiage.get(i));
//					}
//				}
//				adapter.notifyDataSetChanged();
//			}
			break;
		}
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData(String sort_url, int currentPage) {
		// 加载数据显示加载Dialog
		loadingdialog.show(ClassifyActivity.this);
		// 请求数据
		
		if(sort_url.equals(URLConfig.SORT_JIAGE)){
			HttpUrlProvider.getIntance().getClassifyGoods(this,
					new TaskGoodsListBack(handler), sort_url, currentPage,
					classify_id,sort_style);
		}else{
			HttpUrlProvider.getIntance().getClassifyGoods(this,
					new TaskGoodsListBack(handler), sort_url, currentPage,
					classify_id,"desc");
		}

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss();
		loadingdialog = null;
		imageLoader.stop();
	}
}
