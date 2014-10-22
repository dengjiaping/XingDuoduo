package com.xiuman.xingduoduo.ui.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.ShoppingCenterAdViewPagerAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskCenterAdBack;
import com.xiuman.xingduoduo.callback.TaskStickGoodsListBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.Ad;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.model.GoodsStick;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.testdata.Test;
import com.xiuman.xingduoduo.ui.activity.ActivityActivity;
import com.xiuman.xingduoduo.ui.activity.BBSPlateActivity;
import com.xiuman.xingduoduo.ui.activity.CenterClassifyActivity;
import com.xiuman.xingduoduo.ui.activity.ClassifyActivity;
import com.xiuman.xingduoduo.ui.activity.GoodsActivity;
import com.xiuman.xingduoduo.ui.activity.LimitBuyActivity;
import com.xiuman.xingduoduo.ui.activity.SearchActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.PullScrollView;
import com.xiuman.xingduoduo.view.PullScrollView.OnTurnListener;
import com.xiuman.xingduoduo.view.indicator.CirclePageIndicator;

/**
 * 
 * @名称：FragmentShoppingCenter.java
 * @描述：商城界面
 * @author danding
 * @version 2014-6-3
 */
public class FragmentShoppingCenter extends BaseFragment implements
		OnClickListener, OnTurnListener {

	/*------------------------------------组件----------------------------*/
	// 搜索
	private Button btn_search;
	private TextView tv_title;
	// 扫码
	private Button btn_sweep;
	// 顶部图
	private ImageView iv_center_bg_img;
	// sv
	private PullScrollView sv_shopping_center;

	// 屏幕宽高
	private int screenWidth;

	/*------------------------------------商城主界面-----------------------*/
	// 限时抢购
	private LinearLayout llyt_center_time_limit_by;
	// 倒计时
	private TextView tv_center_daojishi;
	// 高大上
	private LinearLayout llyt_center_gaodashang;
	// 同志
	private LinearLayout llyt_center_tz;
	// 新品专区
	private LinearLayout llyt_center_new_goods;
	// 礼品专区
	private LinearLayout llyt_center_lipin;
	// 涨姿势板块(论坛)
	private LinearLayout llyt_center_zhangzishi;
	// 恶搞专区
	private LinearLayout llyt_center_egao;
	// 模块四Top热卖
	private ImageView iv_center_4_top_sale;
	// More
	private Button btn_center_4_top_sale;
	// 模块四商品左------------------------------------------------
	private RelativeLayout rlyt_center_4_left_bottom;
	// 图片
	private ImageView iv_center_4_left_bottom;
	// 模块四商品右上---------------------------------------------
	private RelativeLayout rlyt_center_4_right_top;
	// 图片
	private ImageView iv_center_4_right_top;
	// 模块四商品右下---------------------------------------------
	private RelativeLayout rlyt_center_4_right_bottom;
	// 图片
	private ImageView iv_center_4_right_bottom;
	// 模块五内衣
	private ImageView iv_center_5_top;
	// More
	private Button btn_center_5_top;
	// 模块五商品左-----------------------------------------------
	private RelativeLayout rlyt_center_5_left_bottom;
	// 图片
	private ImageView iv_center_5_left_bottom;
	// 模块五商品右上---------------------------------------------
	private RelativeLayout rlyt_center_5_right_top;
	// 图片
	private ImageView iv_center_5_right_top;
	// 模块五商品右下---------------------------------------------
	private RelativeLayout rlyt_center_5_right_bottom;
	// 图片
	private ImageView iv_center_5_right_bottom;
	// 模块六内衣
	private ImageView iv_center_6_top;
	// More
	private Button btn_center_6_top;
	// 模块六商品左-----------------------------------------------
	private RelativeLayout rlyt_center_6_left_bottom;
	// 图片
	private ImageView iv_center_6_left_bottom;
	// 模块六商品右上--------------------------------------------
	private RelativeLayout rlyt_center_6_right_top;
	// 图片
	private ImageView iv_center_6_right_top;
	// 模块六商品右下-------------------------------------------
	private RelativeLayout rlyt_center_6_right_bottom;
	// 图片
	private ImageView iv_center_6_right_bottom;
	// 模块七内衣
	private ImageView iv_center_7_top;
	// More
	private Button btn_center_7_top;
	// 模块七商品左---------------------------------------------
	private RelativeLayout rlyt_center_7_left_bottom;
	// 图片
	private ImageView iv_center_7_left_bottom;
	// 模块七商品右上------------------------------------------
	private RelativeLayout rlyt_center_7_right_top;
	// 图片
	private ImageView iv_center_7_right_top;
	// 模块七商品右下------------------------------------------
	private RelativeLayout rlyt_center_7_right_bottom;
	// 图片
	private ImageView iv_center_7_right_bottom;

	/*------------------------------------广告----------------------------*/
	private CirclePageIndicator mIndicator;
	// 广告ViewPager
	private ViewPager viewpager_shoppingcenter_ad;
	// 广告Adapter
	private ShoppingCenterAdViewPagerAdapter ad_adapter;
	// ViewPager 循环播放
	boolean cunhuan = false;
	private int page_id = 1;
	private Thread switchTask = new Thread() {
		public void run() {
			if (cunhuan) {
				viewpager_shoppingcenter_ad.setCurrentItem(page_id);
				page_id++;
				if (page_id >= ads.size()) {
					page_id = 0;
				}
			}
			cunhuan = true;
			mHandler.postDelayed(switchTask, 2000);
		}
	};
	Handler mHandler = new Handler();

	/*------------------------------------ImageLoader---------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;
	// 配置图片加载及显示选项
	public DisplayImageOptions options2;
	// 无网络时读取配置文件里的分类Id
	private String[] classifyes_ids;

	/*------------------------------------请求数据---------------------*/

	// 请求广告返回
	private ActionValue<Ad> value_ads = new ActionValue<Ad>();
	// 广告商品
	private ArrayList<Ad> ads = new ArrayList<Ad>();

	// 请求置顶商品
	private ActionValue<GoodsStick> value_stick = new ActionValue<GoodsStick>();
	// 置顶商品
	private ArrayList<GoodsStick> stick = new ArrayList<GoodsStick>();
	// 置顶top
	private ArrayList<GoodsStick> stick_4 = new ArrayList<GoodsStick>();
	// 置顶内衣
	private ArrayList<GoodsStick> stick_5 = new ArrayList<GoodsStick>();
	// 置顶套套
	private ArrayList<GoodsStick> stick_6 = new ArrayList<GoodsStick>();
	// 置顶道具
	private ArrayList<GoodsStick> stick_7 = new ArrayList<GoodsStick>();

	// 消息处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:
				value_ads = (ActionValue<Ad>) msg.obj;
				if (value_ads.isSuccess()) {
					ads = value_ads.getDatasource();
					// 设置广告数据
					setAdData(ads);
					MyApplication.getInstance().saveAds(value_ads);
				}
				break;
			case AppConfig.NET_ERROR_NOTNET:// 广告获取失败（无网络）
				value_ads = MyApplication.getInstance().getAds();
				if (value_ads != null) {
					ads = value_ads.getDatasource();
					// 设置广告数据
					setAdData(ads);
				} else {
				}
				break;
			case AppConfig.STICK_SUCCED:// 获取置顶商品
				value_stick = (ActionValue<GoodsStick>) msg.obj;
				if (value_stick.isSuccess()) {
					stick = value_stick.getDatasource();
					MyApplication.getInstance().saveStick(value_stick);
					setStick(stick);
				}
				break;
			case AppConfig.STICK_FAILD:// 置顶商品获取失败（无网络）
				value_stick = MyApplication.getInstance().getStcik();
				if (value_stick != null) {
					stick = value_stick.getDatasource();
					setStick(stick);

				} else {

				}
				break;
			}
		}
	};

	/**
	 * @描述：设置首页商品数据刚打开时为上次请求的数据 2014-9-21
	 */
	private void setLastData() {
		value_ads = MyApplication.getInstance().getAds();
		if (value_ads != null) {
			ads = value_ads.getDatasource();
			// 设置广告数据
			setAdData(ads);
		} else {
		}
		value_stick = MyApplication.getInstance().getStcik();
		if (value_stick != null) {
			stick = value_stick.getDatasource();
			// 设置置顶商品
			setStick(stick);
		} else {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shopping_center,
				container, false);
		initData();
		findViewById(view);
		initUI();
		setListener();
		return view;
	}

	/**
	 * 数据初始化
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();
		options2 = new DisplayImageOptions.Builder()
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
		screenWidth = MyApplication.getInstance().getScreenWidth();

		classifyes_ids = getResources().getStringArray(R.array.classify_id);

	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById(View view) {
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		btn_sweep = (Button) view.findViewById(R.id.btn_sweep_1);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		iv_center_bg_img = (ImageView) view.findViewById(R.id.iv_center_bg_img);
		sv_shopping_center = (PullScrollView) view
				.findViewById(R.id.sv_shopping_center);

		viewpager_shoppingcenter_ad = (ViewPager) view
				.findViewById(R.id.viewpager_shoppingcenter_ad);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

		llyt_center_time_limit_by = (LinearLayout) view
				.findViewById(R.id.llyt_center_time_limit_by);
		tv_center_daojishi = (TextView) view
				.findViewById(R.id.tv_center_daojishi);
		llyt_center_gaodashang = (LinearLayout) view
				.findViewById(R.id.llyt_center_gaodashang);
		llyt_center_tz = (LinearLayout) view.findViewById(R.id.llyt_center_tz);
		llyt_center_new_goods = (LinearLayout) view
				.findViewById(R.id.llyt_center_new_goods);
		llyt_center_lipin = (LinearLayout) view
				.findViewById(R.id.llyt_center_lipin);
		llyt_center_zhangzishi = (LinearLayout) view
				.findViewById(R.id.llyt_center_zhangzishi);
		llyt_center_egao = (LinearLayout) view
				.findViewById(R.id.llyt_center_egao);
		iv_center_4_top_sale = (ImageView) view
				.findViewById(R.id.iv_center_4_top_sale);
		btn_center_4_top_sale = (Button) view
				.findViewById(R.id.btn_center_4_top_sale);
		// ---
		rlyt_center_4_left_bottom = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_4_left_bottom);
		iv_center_4_left_bottom = (ImageView) view
				.findViewById(R.id.iv_center_4_left_bottom);
		// ---
		rlyt_center_4_right_top = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_4_right_top);
		iv_center_4_right_top = (ImageView) view
				.findViewById(R.id.iv_center_4_right_top);
		// --
		rlyt_center_4_right_bottom = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_4_right_bottom);
		iv_center_4_right_bottom = (ImageView) view
				.findViewById(R.id.iv_center_4_right_bottom);

		iv_center_5_top = (ImageView) view.findViewById(R.id.iv_center_5_top);
		btn_center_5_top = (Button) view.findViewById(R.id.btn_center_5_top);
		// ---
		rlyt_center_5_left_bottom = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_5_left_bottom);
		iv_center_5_left_bottom = (ImageView) view
				.findViewById(R.id.iv_center_5_left_bottom);
		// ---
		rlyt_center_5_right_top = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_5_right_top);
		iv_center_5_right_top = (ImageView) view
				.findViewById(R.id.iv_center_5_right_top);
		// ---
		rlyt_center_5_right_bottom = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_5_right_bottom);
		iv_center_5_right_bottom = (ImageView) view
				.findViewById(R.id.iv_center_5_right_bottom);

		iv_center_6_top = (ImageView) view.findViewById(R.id.iv_center_6_top);
		btn_center_6_top = (Button) view.findViewById(R.id.btn_center_6_top);
		// ---
		rlyt_center_6_left_bottom = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_6_left_bottom);
		iv_center_6_left_bottom = (ImageView) view
				.findViewById(R.id.iv_center_6_left_bottom);
		// ---
		rlyt_center_6_right_top = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_6_right_top);
		iv_center_6_right_top = (ImageView) view
				.findViewById(R.id.iv_center_6_right_top);
		// --
		rlyt_center_6_right_bottom = (RelativeLayout) view
				.findViewById(R.id.llyt_center_6_right_bottom);
		iv_center_6_right_bottom = (ImageView) view
				.findViewById(R.id.iv_center_6_right_bottom);

		iv_center_7_top = (ImageView) view.findViewById(R.id.iv_center_7_top);
		btn_center_7_top = (Button) view.findViewById(R.id.btn_center_7_top);
		// ---
		rlyt_center_7_left_bottom = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_7_left_bottom);
		iv_center_7_left_bottom = (ImageView) view
				.findViewById(R.id.iv_center_7_left_bottom);
		// ---
		rlyt_center_7_right_top = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_7_right_top);
		iv_center_7_right_top = (ImageView) view
				.findViewById(R.id.iv_center_7_right_top);
		// ---
		rlyt_center_7_right_bottom = (RelativeLayout) view
				.findViewById(R.id.rlyt_center_7_right_bottom);
		iv_center_7_right_bottom = (ImageView) view
				.findViewById(R.id.iv_center_7_right_bottom);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		tv_title.setText("商城");
		// 设置数据为上次请求的数据
		setLastData();
		// 获取广告
		getAds();
		// 获取置顶商品
		getStick();
		// 倒计时
		startDaojishi();

		sv_shopping_center.setHeader(iv_center_bg_img);
		sv_shopping_center.setOnTurnListener(this);

		// 动态设置控件大小
		// (params1)
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
				screenWidth / 4, (int) ((screenWidth / 4) * (131.0 / 160)));
		llyt_center_gaodashang.setLayoutParams(params1);
		llyt_center_tz.setLayoutParams(params1);
		llyt_center_lipin.setLayoutParams(params1);
		llyt_center_zhangzishi.setLayoutParams(params1);
		// params2
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				screenWidth, (int) (screenWidth * (272.0 / 640)));
		iv_center_4_top_sale.setLayoutParams(params2);
		iv_center_5_top.setLayoutParams(params2);
		iv_center_6_top.setLayoutParams(params2);
		iv_center_7_top.setLayoutParams(params2);
		// params3
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
				screenWidth / 2, (int) (screenWidth / 2 * (344.0 / 320)));
		llyt_center_time_limit_by.setLayoutParams(params3);
		rlyt_center_4_left_bottom.setLayoutParams(params3);
		rlyt_center_5_left_bottom.setLayoutParams(params3);
		rlyt_center_6_left_bottom.setLayoutParams(params3);
		rlyt_center_7_left_bottom.setLayoutParams(params3);
		// params4
		LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
				screenWidth / 2, (int) (screenWidth / 2 * (171.0 / 319)));
		llyt_center_new_goods.setLayoutParams(params4);
		llyt_center_egao.setLayoutParams(params4);
		rlyt_center_4_right_bottom.setLayoutParams(params4);
		rlyt_center_4_right_top.setLayoutParams(params4);
		rlyt_center_5_right_bottom.setLayoutParams(params4);
		rlyt_center_5_right_top.setLayoutParams(params4);
		rlyt_center_6_right_bottom.setLayoutParams(params4);
		rlyt_center_6_right_top.setLayoutParams(params4);
		rlyt_center_7_right_bottom.setLayoutParams(params4);
		rlyt_center_7_right_top.setLayoutParams(params4);

	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		btn_search.setOnClickListener(this);
		btn_sweep.setOnClickListener(this);
		// 广告切换监听
		viewpager_shoppingcenter_ad
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						super.onPageSelected(position);
						mIndicator.setCurrentItem(position);
						page_id = position;
					}
				});
		llyt_center_zhangzishi.setOnClickListener(new OnClickListener2());

		llyt_center_time_limit_by.setOnClickListener(new OnClickListener2());
		llyt_center_gaodashang.setOnClickListener(new OnClickListener1());
		llyt_center_tz.setOnClickListener(new OnClickListener1());
		llyt_center_new_goods.setOnClickListener(new OnClickListener1());
		llyt_center_lipin.setOnClickListener(new OnClickListener2());
		llyt_center_egao.setOnClickListener(new OnClickListener1());
		iv_center_4_top_sale.setOnClickListener(new OnClickListener1());
		btn_center_4_top_sale.setOnClickListener(new OnClickListener1());
		rlyt_center_4_left_bottom.setOnClickListener(new OnClickListener3());
		rlyt_center_4_right_top.setOnClickListener(new OnClickListener3());
		rlyt_center_4_right_bottom.setOnClickListener(new OnClickListener3());
		iv_center_5_top.setOnClickListener(new OnClickListener2());
		btn_center_5_top.setOnClickListener(new OnClickListener2());
		rlyt_center_5_left_bottom.setOnClickListener(new OnClickListener3());
		rlyt_center_5_right_top.setOnClickListener(new OnClickListener3());
		rlyt_center_5_right_bottom.setOnClickListener(new OnClickListener3());
		iv_center_6_top.setOnClickListener(new OnClickListener2());
		btn_center_6_top.setOnClickListener(new OnClickListener2());
		rlyt_center_6_left_bottom.setOnClickListener(new OnClickListener3());
		rlyt_center_6_right_top.setOnClickListener(new OnClickListener3());
		rlyt_center_6_right_bottom.setOnClickListener(new OnClickListener3());
		iv_center_7_top.setOnClickListener(new OnClickListener2());
		btn_center_7_top.setOnClickListener(new OnClickListener2());
		rlyt_center_7_left_bottom.setOnClickListener(new OnClickListener3());
		rlyt_center_7_right_top.setOnClickListener(new OnClickListener3());
		rlyt_center_7_right_bottom.setOnClickListener(new OnClickListener3());
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * 
	 * 描述：设置广告数据
	 */
	private void setAdData(ArrayList<Ad> ads) {
		// 添加广告,测试数据，添加操作
		ad_adapter = new ShoppingCenterAdViewPagerAdapter(ads,
				getActivity(), options2, imageLoader);
		viewpager_shoppingcenter_ad.setAdapter(ad_adapter);
		mIndicator.setViewPager(viewpager_shoppingcenter_ad);
		if (switchTask.getState() == Thread.State.NEW&&ads.size()>0) {
			switchTask.start();
		}
	}

	/**
	 * @描述：设置置顶商品
	 * @param stick
	 *            2014-9-21
	 */
	private void setStick(ArrayList<GoodsStick> stick) {
		if (stick.size() == 12) {
			for (int i = 0; i < stick.size(); i++) {
				if (stick.get(i).isIs_hot()) {
					stick_4.add(stick.get(i));
				}
			}

			stick.removeAll(stick_4);
			for (int i = 0; i < stick.size(); i++) {
				if (stick.get(i).getGoodsCategoryId().equals(classifyes_ids[1])) {
					stick_5.add(stick.get(i));
				}
			}
			for (int i = 0; i < stick.size(); i++) {
				if (stick.get(i).getGoodsCategoryId().equals(classifyes_ids[0])) {
					stick_6.add(stick.get(i));
				}
			}
			for (int i = 0; i < stick.size(); i++) {
				if (stick.get(i).getGoodsCategoryId().equals(classifyes_ids[3])) {
					stick_7.add(stick.get(i));
				}
			}

			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_4.get(0).getSourceImagePath(),
					iv_center_4_left_bottom, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_4.get(1).getSourceImagePath(),
					iv_center_4_right_top, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_4.get(2).getSourceImagePath(),
					iv_center_4_right_bottom, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_5.get(0).getSourceImagePath(),
					iv_center_5_left_bottom, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_5.get(1).getSourceImagePath(),
					iv_center_5_right_top, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_5.get(2).getSourceImagePath(),
					iv_center_5_right_bottom, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_6.get(0).getSourceImagePath(),
					iv_center_6_left_bottom, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_6.get(1).getSourceImagePath(),
					iv_center_6_right_top, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_6.get(2).getSourceImagePath(),
					iv_center_6_right_bottom, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_7.get(0).getSourceImagePath(),
					iv_center_7_left_bottom, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_7.get(1).getSourceImagePath(),
					iv_center_7_right_top, options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ stick_7.get(2).getSourceImagePath(),
					iv_center_7_right_bottom, options);
		}
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:// 搜索按钮
			Intent intent_search = new Intent(getActivity(),
					SearchActivity.class);
			getActivity().startActivity(intent_search);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.btn_sweep_1:// 扫码
			// Intent intent = new Intent();
			// intent.setClass(getActivity(), SweepActivty.class);
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivityForResult(intent, 1);
			break;
		}
	}

	/**
	 * 对扫描得到的二维码进行处理
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 添加操作
				ToastUtil.ToastView(getActivity(), bundle.getString("result"));
			}
			break;
		}
	}

	/**
	 * @名称：FragmentShoppingCenter.java
	 * @描述：商城界面小分类点击事件
	 * @author danding
	 * @version
	 * @date：2014-7-30
	 */
	class OnClickListener1 implements OnClickListener {

		// 要打开的商城界面分类名称
		String classify_name = "";
		// 要打开的商城界面分类类型
		String classify_url;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.llyt_center_gaodashang:// 高大上
				classify_name = "高大上";
				classify_url = "isBest";
				break;
			case R.id.llyt_center_tz:// 同志
				classify_name = "同志";
				classify_url = "isWalk";
				break;
			case R.id.llyt_center_new_goods:// 新品专区
				classify_name = "新品专区";
				classify_url = "isNew";
				break;
			case R.id.llyt_center_egao:// 恶搞专区
				classify_name = "恶搞专区";
				classify_url = "isSpoof";
				break;
			case R.id.btn_center_4_top_sale:// TopMore

			case R.id.iv_center_4_top_sale:// top热卖
				classify_name = "Top热卖";
				classify_url = "isHot";
				break;
			}

			Intent intent = new Intent(getActivity(),
					CenterClassifyActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("classify_name", classify_name);
			bundle.putString("classify_url", classify_url);
			intent.putExtras(bundle);

			startActivity(intent);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
		}

	}

	/**
	 * @名称：FragmentShoppingCenter.java
	 * @描述：商城界面具体商品点击事件
	 * @author danding
	 * @version
	 * @date：2014-7-30
	 */
	class OnClickListener2 implements OnClickListener {

		// 要打开的商城界面分类名称
		String classify_name = "";
		// 要打开的商城界面分类类型
		String classify_url;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.llyt_center_zhangzishi:// 涨姿势
//				BBSPlate plate = Test.getBBSPlates().get(0);
//				Intent intent_plate = new Intent(getActivity(),
//						BBSPlateActivity.class);
//				Bundle bundle_plate = new Bundle();
//				bundle_plate.putSerializable("bbs_plate", plate);
//				intent_plate.putExtras(bundle_plate);
//				getActivity().startActivity(intent_plate);
//				getActivity().overridePendingTransition(
//						R.anim.translate_horizontal_start_in,
//						R.anim.translate_horizontal_start_out);
				break;
			case R.id.llyt_center_time_limit_by:// 限时抢购
				classify_name = "限时抢购";
				classify_url = "isTime";
				Intent intent = new Intent(getActivity(),
						LimitBuyActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("classify_name", classify_name);
				bundle.putString("classify_url", classify_url);
				intent.putExtras(bundle);

				startActivity(intent);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
				break;
			case R.id.llyt_center_lipin:// 礼品专区
				classify_name = "活动专区";
				classify_url = "isActivities";
				Intent intent2 = new Intent(getActivity(),
						ActivityActivity.class);
				Bundle bundle2 = new Bundle();
				bundle2.putString("classify_name", classify_name);
				bundle2.putString("classify_url", classify_url);
				intent2.putExtras(bundle2);

				startActivity(intent2);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
				break;
			case R.id.btn_center_5_top:// 5More

			case R.id.iv_center_5_top:// 模块五
				Intent intent_5 = new Intent(getActivity(),
						ClassifyActivity.class);
				Bundle bundle_5 = new Bundle();
				bundle_5.putString("classify_id", classifyes_ids[1]);
				bundle_5.putString("classify_name", "性随衣动");
				intent_5.putExtras(bundle_5);
				getActivity().startActivity(intent_5);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
				break;
			case R.id.btn_center_6_top:// 6More

			case R.id.iv_center_6_top:// 模块六
				Intent intent_6 = new Intent(getActivity(),
						ClassifyActivity.class);
				Bundle bundle_6 = new Bundle();
				bundle_6.putString("classify_id", classifyes_ids[0]);
				bundle_6.putString("classify_name", "放纵一夏");
				intent_6.putExtras(bundle_6);
				getActivity().startActivity(intent_6);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
				break;
			case R.id.btn_center_7_top:// 7More

			case R.id.iv_center_7_top:// 模块七
				Intent intent_7 = new Intent(getActivity(),
						ClassifyActivity.class);
				Bundle bundle_7 = new Bundle();
				bundle_7.putString("classify_id", classifyes_ids[3]);
				bundle_7.putString("classify_name", "爱不释手");
				intent_7.putExtras(bundle_7);
				getActivity().startActivity(intent_7);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
				break;

			}
		}

	}

	/**
	 * @名称：FragmentShoppingCenter.java
	 * @描述：首页置顶商品
	 * @author danding 2014-9-21
	 */
	class OnClickListener3 implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (stick != null) {
				String goods_id = null;
				switch (v.getId()) {
				case R.id.rlyt_center_4_left_bottom:// 模块四左下
					goods_id = stick_4.get(0).getId();
					break;
				case R.id.rlyt_center_4_right_top:// 模块四右上
					goods_id = stick_4.get(1).getId();
					break;
				case R.id.rlyt_center_4_right_bottom:// 模块四右下
					goods_id = stick_4.get(2).getId();
					break;
				case R.id.rlyt_center_5_left_bottom:// 模块五左下
					goods_id = stick_5.get(0).getId();
					break;
				case R.id.rlyt_center_5_right_top:// 模块五上
					goods_id = stick_5.get(1).getId();
					break;
				case R.id.rlyt_center_5_right_bottom:// 模块五右下
					goods_id = stick_5.get(2).getId();
					break;
				case R.id.rlyt_center_6_left_bottom:// 模块六左下
					goods_id = stick_6.get(0).getId();
					break;
				case R.id.rlyt_center_6_right_top:// 模块六右上
					goods_id = stick_6.get(1).getId();
					break;
				case R.id.llyt_center_6_right_bottom:// 模块六右下
					goods_id = stick_6.get(2).getId();
					break;
				case R.id.rlyt_center_7_left_bottom:// 模块七左下
					goods_id = stick_7.get(0).getId();
					break;
				case R.id.rlyt_center_7_right_top:// 模块七右上
					goods_id = stick_7.get(1).getId();
					break;
				case R.id.rlyt_center_7_right_bottom:// 模块七右下
					goods_id = stick_7.get(2).getId();
					break;
				}
				Intent intent = new Intent(getActivity(), GoodsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("goods_id", goods_id);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
			}
		}
	}

	/**
	 * @描述：获取广告 2014-9-20
	 */
	private void getAds() {
		HttpUrlProvider.getIntance().getCenterAd(getActivity(),
				new TaskCenterAdBack(handler),
				URLConfig.CENTER_AD, "1");
	}

	/**
	 * @描述：获取置顶商品 2014-9-21
	 */
	private void getStick() {
		HttpUrlProvider.getIntance().getStickGoods(getActivity(),
				new TaskStickGoodsListBack(handler), URLConfig.STICK_GOODS);
	}

	@Override
	public void onTurn() {

	}

	/*------------------------------------倒计时-------------------------------*/
	private Calendar mDate2;
	private int mYear, mMonth, mDay;
	private String date;
	private Handler mHandler2 = new Handler();// 全局handler
	int time = 0;// 时间差

	private void updateDateTime() {
		mDate2 = Calendar.getInstance();
		mYear = mDate2.get(Calendar.YEAR);
		mMonth = mDate2.get(Calendar.MONTH);
		mDay = mDate2.get(Calendar.DAY_OF_MONTH);

		date = mYear + "-" + (getDateFormat(mMonth + 1)) + "-"
				+ getDateFormat(mDay) + " " + 24 + ":" + "00" + ":00";
		// if (mHour >= 0) {
		// date = mYear + "-" + (getDateFormat(mMonth + 1)) + "-"
		// + getDateFormat(mDay + 1) + " " + 17 + ":" + "00" + ":00";
		// }

	}

	public String getDateFormat(int time) {
		String date = time + "";
		if (time < 10) {
			date = "0" + date;
		}
		return date;
	}

	class TimeCount implements Runnable {
		@Override
		public void run() {
			while (time >= 0)// 整个倒计时执行的循环
			{
				time--;
				mHandler2.post(new Runnable() // 通过它在UI主线程中修改显示的剩余时间
						{
							public void run() {
								tv_center_daojishi.setText(getInterval(time));// 显示剩余时间
							}
						});
				try {
					Thread.sleep(1000);// 线程休眠一秒钟 这个就是倒计时的间隔时间
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 下面是倒计时结束逻辑
			mHandler2.post(new Runnable() {
				@Override
				public void run() {
					tv_center_daojishi.setText("设定的时间到。");
				}
			});
		}
	}

	/**
	 * 设定显示文字
	 */
	public static String getInterval(int time) {
		String txt = null;
		if (time >= 0) {
			long hour = time % (24 * 3600) / 3600;// 小时
			long minute = time % 3600 / 60;// 分钟
			long second = time % 60;// 秒

			txt = hour + "小时" + minute + "分" + second + "秒";
		} else {
			txt = "已过期";
		}
		return txt;
	}

	private void startDaojishi() {
		updateDateTime();
		time = getTimeInterval(date);
		new Thread(new TimeCount()).start();// 开启线程
	}

	/**
	 * 获取两个日期的时间差
	 */
	public static int getTimeInterval(String data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		int interval = 0;
		try {
			Date currentTime = new Date();// 获取现在的时间
			Date beginTime = dateFormat.parse(data);
			interval = (int) ((beginTime.getTime() - currentTime.getTime()) / (1000));// 时间差
																						// 单位秒
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return interval;
	}
}
