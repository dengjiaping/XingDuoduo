package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.PlatePostListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.callback.TaskPostListBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.SizeUtil;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshListView;

/**
 * @名称：MyPostActivity.java
 * @描述：我的帖子列表
 * @author danding 2014-9-25
 */
public class MyPostActivity extends Base2Activity implements OnClickListener {

	/*---------------------------------组件-----------------------------------*/
	// 返回
	private Button btn_back;
	// 发帖
	private Button btn_post;
	// 标题栏
	private TextView tv_title;
	// 下拉刷新ScrollView
	private PullToRefreshListView pulllv_my_post;
	// ListView(帖子)
	private ListView lv_posts;
	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 帖子为空时显示的布局
	private LinearLayout llyt_null_post;
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

	/*--------------------------------------Adapter-----------------------------*/
	// adapter(帖子列表)
	private PlatePostListViewAdapter adapter;
	// 请求帖子返回结果
	private ActionValue<BBSPost> value = new ActionValue<BBSPost>();
	// 当前显示的帖子列表
	private ArrayList<BBSPost> bbspost = new ArrayList<BBSPost>();
	// 请求获取的帖子列表
	private ArrayList<BBSPost> bbspost_get = new ArrayList<BBSPost>();
	// 用户论坛id
	private String userId;
	// 当前请求页
	private int currentPage = 1;
	// 获取帖子类型(0：我的帖子，1：我的回复)
	private int type = 0;
	private boolean flag = true;
	// 消息处理Handler-------------------------------------
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 获取数据成功
				loadingdialog.dismiss(MyPostActivity.this);
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;

			case AppConfig.BBS_POST_BACK:// 获取帖子列表
				value = (ActionValue<BBSPost>) msg.obj;
				bbspost_get = value.getDatasource();
				if (value.isSuccess()) {
					if (isUp) {
						bbspost = bbspost_get;
						adapter = new PlatePostListViewAdapter(
								MyPostActivity.this, options, imageLoader,
								bbspost);
						lv_posts.setAdapter(adapter);
						// 下拉加载完成
						pulllv_my_post.onPullDownRefreshComplete();
						//设置刷新时间
						TimeUtil.setLastUpdateTime(pulllv_my_post);
					} else {
						bbspost.addAll(bbspost_get);
						adapter.notifyDataSetChanged();
						// 上拉刷新完成
						pulllv_my_post.onPullUpRefreshComplete();
					}
					flag = true;
				} else {
					ToastUtil.ToastView(MyPostActivity.this, "没有更多帖子！");
					pulllv_my_post.setHasMoreData(false);
					// 上拉刷新完成
					pulllv_my_post.onPullUpRefreshComplete();
					flag = false;
					if (currentPage == 1) {// 第一次请求返回结果为空
						llyt_null_post.setVisibility(View.VISIBLE);
					}
				}
				llyt_network_error.setVisibility(View.INVISIBLE);
				loadingdialog.dismiss(MyPostActivity.this);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss(MyPostActivity.this);
				llyt_network_error.setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
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
				.showImageOnLoading(R.drawable.onloading)
				.showImageForEmptyUri(R.drawable.onloading)
				// image连接地址为空时
				.showImageOnFail(R.drawable.onloading)
				// image加载失败
				.cacheInMemory(false)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true)
				// 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.NONE).build();
		if (MyApplication.getInstance().isUserLogin()) {
			userId = MyApplication.getInstance().getUserInfo().getUser_id();
		}

		type = getIntent().getExtras().getInt("type");
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_null_post = (LinearLayout) findViewById(R.id.llyt_plate_null_post);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_post = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);

		pulllv_my_post = (PullToRefreshListView) findViewById(R.id.pulllv_my_posts);
		pulllv_my_post.setPullLoadEnabled(true);
		pulllv_my_post.setScrollLoadEnabled(true);
		lv_posts = pulllv_my_post.getRefreshableView();

		View view = View.inflate(this, R.layout.include_my_posts_container,
				null);
		lv_posts.setDividerHeight(SizeUtil.dip2px(this, 8));
		lv_posts.setDivider(getResources().getDrawable(
				R.drawable.drawable_transparent));
		lv_posts.setSelector(getResources().getDrawable(
				R.drawable.drawable_transparent));

		lv_posts.addHeaderView(view);
	}

	@Override
	protected void initUI() {
		if (type == 0) {
			tv_title.setText("我的帖子");
		} else if (type == 1) {
			tv_title.setText("我的回复");
		}
		btn_post.setVisibility(View.INVISIBLE);
		// 获取列表
		initFirstData(currentPage, type);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_post.setOnClickListener(this);
		llyt_network_error.setOnClickListener(this);

		// 查看帖子详情
		lv_posts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_posts.getItemAtPosition(position);
				if (obj instanceof BBSPost) {
					BBSPost postinfo = (BBSPost) obj;
					Intent intent = new Intent(MyPostActivity.this,
							PostInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("postinfo_starter", postinfo.getId());
					bundle.putString("forumId", postinfo.getForumId() + "");
					intent.putExtras(bundle);
					startActivity(intent);
					overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}
			}
		});

		// 下拉上啦刷新
		pulllv_my_post.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				isUp = true;
				currentPage = 1;
				initFirstData(currentPage, type);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				isUp = false;
				if (flag) {
					currentPage += 1;
					initFirstData(currentPage, type);
				} else {
					ToastUtil.ToastView(MyPostActivity.this, getResources()
							.getString(R.string.no_more));
					// 下拉加载完成
					pulllv_my_post.onPullDownRefreshComplete();
					// 上拉刷新完成
					pulllv_my_post.onPullUpRefreshComplete();
					// 设置是否有更多的数据
					pulllv_my_post.setHasMoreData(false);
				}
			}
		});

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
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:// 返回按钮
			finish();
			break;
		case R.id.llyt_network_error:// 重新加载
			currentPage = 1;
			initFirstData(currentPage, type);
		}
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData(int currentPage, int type) {
		if (type == 0) {

			// 请求数据
			HttpUrlProvider.getIntance().getMyPost(MyPostActivity.this,
					new TaskPostListBack(handler), userId, currentPage);
		} else if (type == 1) {
			// 请求数据
			HttpUrlProvider.getIntance().getMyReplyPost(MyPostActivity.this,
					new TaskPostListBack(handler), userId, currentPage);
		}
		loadingdialog.show(MyPostActivity.this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss(MyPostActivity.this);
		loadingdialog = null;
		imageLoader.stop();
		imageLoader.clearMemoryCache();
	}
}
