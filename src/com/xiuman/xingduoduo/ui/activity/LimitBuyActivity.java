package com.xiuman.xingduoduo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshScrollView;

/**
 * @名称：LimitBuyActivity.java
 * @描述：限时抢购
 * @author danding 2014-9-17
 */
public class LimitBuyActivity extends Base2Activity implements OnClickListener {

	/*----------------------------------组件------------------------------------*/
	// 返回
	private Button btn_back;
	// 标题栏
	private TextView tv_title;
	// 右侧
	private Button btn_right;

	// 下拉刷新sv
	private PullToRefreshScrollView pullsv_limitbuy;
	// 可刷新的sv
	private ScrollView sv_limitbuy;
	// ListView
	private ListView lv_limitbuy_container;
	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 商品为空为空时显示的布局
	private LinearLayout llyt_null_goods;
	// 夹在数据时显示的Dialog
	private LoadingDialog loadingdialog;

	/*-------------------------------------ImageLoader-------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_limitby);
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

	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		loadingdialog = new LoadingDialog(this);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_null_goods = (LinearLayout) findViewById(R.id.llyt_goods_null);
		
		pullsv_limitbuy = (PullToRefreshScrollView) findViewById(R.id.pullsv_limtbuy);
		pullsv_limitbuy.setScrollLoadEnabled(true);
		pullsv_limitbuy.setPullLoadEnabled(true);
		sv_limitbuy = pullsv_limitbuy.getRefreshableView();
		
		View view = View.inflate(this, R.layout.include_limitbuy_container, null);
		lv_limitbuy_container = (ListView) view.findViewById(R.id.lv_limitbuy_container);
		
		sv_limitbuy.addView(view);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText("限时抢购");
		
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		
		//下拉刷新，上拉加载
		pullsv_limitbuy.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ScrollView> refreshView) {
				
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ScrollView> refreshView) {
				
			}
		});
		
		lv_limitbuy_container.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});
	}
	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back://返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in, R.anim.translate_horizontal_finish_out);
			break;

		default:
			break;
		}
	}

}
