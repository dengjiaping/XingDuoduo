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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.PlatePostListViewAdapter;
import com.xiuman.xingduoduo.adapter.PlateStickPostListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.model.PostStarter;
import com.xiuman.xingduoduo.testdata.Test;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshScrollView;

/**
 * @名称：BBSPlateActivity.java
 * @描述：交流板块帖子列表
 * @author danding 2014-8-8
 */
public class BBSPlateActivity extends Base2Activity implements OnClickListener {

	/*---------------------------------组件-----------------------------------*/
	// 返回
	private Button btn_back;
	// 发帖
	private Button btn_post;
	// 标题栏
	private TextView tv_title;
	// 下拉刷新ScrollView
	private PullToRefreshScrollView pullsv_post;
	// 可刷新的ScrollView
	private ScrollView sv_posts;
	// ListView(帖子)
	private ListView lv_posts;
	// ListView(置顶帖子)
	private ListView lv_stick_posts;
	// 板块图标
	private ImageView iv_plate_icon;
	// 板块标题
	private TextView tv_plate_name;
	// 板块描述
	private TextView tv_plate_description;
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

	/*--------------------------------------数据--------------------------------*/
	// 从上级界面接收到的板块信息
	private BBSPlate plate;
	// 所有的帖子列表
	private ArrayList<PostStarter> posts = new ArrayList<PostStarter>();
	// 帖子列表(普通)
	private ArrayList<PostStarter> normal_posts = new ArrayList<PostStarter>();
	// 置顶帖子列表
	private ArrayList<PostStarter> stick_posts = new ArrayList<PostStarter>();

	/*--------------------------------------Adapter-----------------------------*/
	// adapter(帖子列表)
	private PlatePostListViewAdapter adapter;
	// 置顶帖子
	private PlateStickPostListViewAdapter adapter_stick;

	// 消息处理Handler-------------------------------------
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 获取数据成功
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.INVISIBLE);
				if (normal_posts.size() == 0) {
					llyt_null_post.setVisibility(View.VISIBLE);
				}
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbs_plate);
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

		// 从上级界面接收到的板块信息
		plate = (BBSPlate) getIntent().getExtras().getSerializable("bbs_plate");
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_null_post = (LinearLayout) findViewById(R.id.llyt_plate_null_post);
		btn_back = (Button) findViewById(R.id.btn_bbs_back);
		btn_post = (Button) findViewById(R.id.btn_bbs_right);
		tv_title = (TextView) findViewById(R.id.tv_bbs_title);
		
		pullsv_post = (PullToRefreshScrollView) findViewById(R.id.pullsv_posts);
		pullsv_post.setPullLoadEnabled(true);
		pullsv_post.setScrollLoadEnabled(true);
		sv_posts = pullsv_post.getRefreshableView();

		// container
		View view = View.inflate(this, R.layout.include_bbs_posts_container,
				null);
		lv_posts = (ListView) view.findViewById(R.id.lv_posts);
		lv_stick_posts = (ListView) view.findViewById(R.id.lv_stick_posts);
		tv_plate_name = (TextView) view.findViewById(R.id.tv_bbs_plate_name);
		tv_plate_description = (TextView) view
				.findViewById(R.id.tv_bbs_plate_description);
		iv_plate_icon = (ImageView) view.findViewById(R.id.iv_bbs_plate_icon);

		sv_posts.addView(view);

	}

	@Override
	protected void initUI() {
		tv_title.setText("最新帖子");
		iv_plate_icon.setImageResource(plate.getPlate_icon());
		tv_plate_name.setText(plate.getPlate_name());
		tv_plate_description.setText(plate.getPlate_description());

		initFirstData();
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_post.setOnClickListener(this);

		//查看帖子详情
		lv_posts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_posts.getItemAtPosition(position);
				if(obj instanceof PostStarter){
					PostStarter postinfo = (PostStarter)obj;
					Intent intent = new Intent(BBSPlateActivity.this,PostInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("postinfo_starter", postinfo);
					intent.putExtras(bundle);
					startActivity(intent);
					overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
				}
			}
		});
	}

	/**
	 * 点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bbs_right://发表帖子
			Intent intent_publish = new Intent(BBSPlateActivity.this,PostPublishActivity.class);
			startActivity(intent_publish);
			break;

		default:
			break;
		}
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData() {
		// 请求数据
		// HttpUrlProvider.getIntance().getCenterClassifyGoods(
		// BBSPlateActivity.this,
		// new TaskCenterClassifyBack(handler), classify_url);
		// loadingdialog.show();
		posts = Test.getTestPost();

		// 将获得的帖子列表分为普通贴和置顶贴
		for (int i = 0; i < posts.size(); i++) {
			if (posts.get(i).getPost_tag().equals("置顶")) {
				stick_posts.add(posts.get(i));
			} else {
				normal_posts.add(posts.get(i));
			}
		}

		adapter = new PlatePostListViewAdapter(this, options, imageLoader,
				normal_posts);
		adapter_stick = new PlateStickPostListViewAdapter(this, stick_posts);
		lv_posts.setAdapter(adapter);
		lv_stick_posts.setAdapter(adapter_stick);

	}
}
