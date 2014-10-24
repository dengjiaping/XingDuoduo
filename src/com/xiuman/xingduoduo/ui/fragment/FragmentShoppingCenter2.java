package com.xiuman.xingduoduo.ui.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
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
import com.xiuman.xingduoduo.adapter.ShoppingCenterBBSViewPagerAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskCenterAdBack;
import com.xiuman.xingduoduo.callback.TaskCenterCategoryBack;
import com.xiuman.xingduoduo.callback.TaskCenterGoodsBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.Ad;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.activity.ActivityActivity;
import com.xiuman.xingduoduo.ui.activity.CenterClassifyActivity;
import com.xiuman.xingduoduo.ui.activity.GoodsActivity;
import com.xiuman.xingduoduo.ui.activity.LimitBuyActivity;
import com.xiuman.xingduoduo.ui.activity.MainActivity;
import com.xiuman.xingduoduo.ui.activity.SearchActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.util.SizeUtil;
import com.xiuman.xingduoduo.view.PullScrollView;
import com.xiuman.xingduoduo.view.PullScrollView.OnTurnListener;
import com.xiuman.xingduoduo.view.indicator.CirclePageIndicator;

/**
 * @名称：FragmentShoppingCenter2.java
 * @描述：商城首页
 * @author danding
 * @时间 2014-10-21
 */
public class FragmentShoppingCenter2 extends BaseFragment implements
		OnClickListener, OnTurnListener {

	/*--------------------------------------------------组件-------------------------------------------------*/
	private MainActivity activity;
	// 标题
	private TextView tv_title;
	// 搜索
	private Button btn_search;
	// 顶部图
	private ImageView iv_center_bg_img;
	// sv
	private PullScrollView sv_shopping_center;
	// 倒计时
	private TextView tv_center_daojishi;
	// 第一块广告栏------------------------------------
	// 广告栏ViewPager
	private ViewPager viewpager_shoppingcenter_ad;
	// 广告栏指示器
	private CirclePageIndicator indicator_ad;

	// 第二块限时抢购------------------------------------
	// 背景图
	private ImageView iv_center_2_limitbuy;

	// 第三块专题(高大上。。)--------------------------------
	// 新品尝鲜
	private ImageView iv_center_3_new;
	// 高大上
	private ImageView iv_center_3_gaodashang;
	// 同志
	private ImageView iv_center_3_tz;

	// 第四块论坛话题-----------------------------------------
	// 论坛ViewPager
	private ViewPager viewpager_shoppingcenter_topic;
	// 指示器
	private CirclePageIndicator indicator_topic;

	// 第五快Top热卖-----------------------------------------
	// 热卖more
	private Button btn_center_5_top_more;
	// 热卖图
	private ImageView iv_center_5_top_sale;
	// 热卖产品1，2，3，4
	private ImageView iv_center_5_1;
	private ImageView iv_center_5_2;
	private ImageView iv_center_5_3;
	private ImageView iv_center_5_4;

	// 第六块主题活动-----------------------------------------
	// 主题
	private TextView tv_center_6_title;
	// 主题more
	private Button btn_center_6_topic_more;
	// 套餐商品3（大）
	private ImageView iv_center_6_right_3;

	// 第七块精品专区
	// 精品more
	private Button btn_center_7_jingpin_more;
	// 精品图
	private ImageView iv_center_7_jingpin;
	// 精品商品1，2，3，4
	private ImageView iv_center_7_1;
	private ImageView iv_center_7_2;
	private ImageView iv_center_7_3;
	private ImageView iv_center_7_4;
	/*------------------------------------ImageLoader---------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;
	// 配置图片加载及显示选项
	public DisplayImageOptions options2;

	/*-------------------------------------------------数据----------------------------------------------*/
	// 屏幕宽高
	private int screenWidth;
	/*-------------------------------------------------广告自动播放---------------------------------------*/
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
			mHandler.postDelayed(switchTask, 3000);
		}
	};
	Handler mHandler = new Handler();

	/*------------------------------------------------请求数据--------------------------------------------*/
	// 请求广告返回-----------------------------------------------------------------------------------------
	private ActionValue<Ad> value_ads = new ActionValue<Ad>();
	// 广告集合
	private ArrayList<Ad> ads = new ArrayList<Ad>();

	// 请求商品返回------------------------------------------------------------------------------------------
	private ActionValue<Ad> values_goods = new ActionValue<Ad>();
	// 商品集合
	private ArrayList<Ad> goods_center = new ArrayList<Ad>();

	// 请求专区返回------------------------------------------------------------------------------------------
	private ActionValue<Ad> values_category = new ActionValue<Ad>();
	// 专区集合
	private ArrayList<Ad> categorys = new ArrayList<Ad>();

	/*------------------------------------------------Adapter-----------------------------------------------*/
	// 广告Adapter
	private ShoppingCenterAdViewPagerAdapter ad_adapter;
	// 热门画地adapter
	private ShoppingCenterBBSViewPagerAdapter bbs_adapter;

	// 消息处理-------------------------------------------------------------------------------------------------
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
			case AppConfig.CENTER_GOODS_SUCCED:// 获取商品
				values_goods = (ActionValue<Ad>) msg.obj;
				if (values_goods.isSuccess()) {
					goods_center = values_goods.getDatasource();
					// 设置商品数据
					setGoodsData(goods_center);
					MyApplication.getInstance().saveCenterGoods(values_goods);
				}
				break;
			case AppConfig.CENTER_GOODS_FAILD:// 商品获取失败（无网络）
				values_goods = MyApplication.getInstance().getCenterGoods();
				if (values_goods != null) {
					goods_center = values_goods.getDatasource();
					// 设置商品数据
					setGoodsData(goods_center);
				} else {
				}
				break;
			case AppConfig.CENTER_CATEGORY_SUCCED:// 获取专区成功
				values_category = (ActionValue<Ad>) msg.obj;
				if (values_category.isSuccess()) {
					categorys = values_category.getDatasource();
					// 设置专区数据
					setCategoryDate(categorys);
					MyApplication.getInstance().saveCenterCategory(
							values_category);
				}
				break;
			case AppConfig.CENTER_CATEGORY_FAILD:// 获取专区失败
				values_category = MyApplication.getInstance()
						.getCenterCategory();
				if (values_category != null) {
					categorys = values_category.getDatasource();
					// 设置专区数据
					setCategoryDate(categorys);
				} else {
				}
				break;

			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shopping_center2,
				container, false);
		initData();
		findViewById(view);
		initUI();
		setListener();
		return view;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder().cacheInMemory(false) // 加载图片时会在内存中加载缓存
				.bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
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
		activity = (MainActivity) getActivity();
	}

	@Override
	protected void findViewById(View view) {
		iv_center_bg_img = (ImageView) view.findViewById(R.id.iv_center_bg_img);
		sv_shopping_center = (PullScrollView) view
				.findViewById(R.id.sv_shopping_center);
		tv_center_daojishi = (TextView) view
				.findViewById(R.id.tv_center_2_daojishi);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		tv_title = (TextView) view.findViewById(R.id.tv_title);

		viewpager_shoppingcenter_ad = (ViewPager) view
				.findViewById(R.id.viewpager_shoppingcenter_ad);
		indicator_ad = (CirclePageIndicator) view
				.findViewById(R.id.indicator_ad);

		iv_center_2_limitbuy = (ImageView) view
				.findViewById(R.id.iv_center_2_limitbuy);
		iv_center_3_new = (ImageView) view.findViewById(R.id.iv_center_3_new);
		iv_center_3_gaodashang = (ImageView) view
				.findViewById(R.id.iv_center_3_gaodashang);
		iv_center_3_tz = (ImageView) view.findViewById(R.id.iv_center_3_tz);

		viewpager_shoppingcenter_topic = (ViewPager) view
				.findViewById(R.id.viewpager_shoppingcenter_topic);
		indicator_topic = (CirclePageIndicator) view
				.findViewById(R.id.indicator_topic);

		btn_center_5_top_more = (Button) view
				.findViewById(R.id.btn_center_5_top_more);
		iv_center_5_top_sale = (ImageView) view
				.findViewById(R.id.iv_center_5_top_sale);
		iv_center_5_1 = (ImageView) view.findViewById(R.id.iv_center_5_1);
		iv_center_5_2 = (ImageView) view.findViewById(R.id.iv_center_5_2);
		iv_center_5_3 = (ImageView) view.findViewById(R.id.iv_center_5_3);
		iv_center_5_4 = (ImageView) view.findViewById(R.id.iv_center_5_4);

		tv_center_6_title = (TextView) view
				.findViewById(R.id.tv_center_6_title);
		btn_center_6_topic_more = (Button) view
				.findViewById(R.id.btn_center_6_topic_more);
		iv_center_6_right_3 = (ImageView) view
				.findViewById(R.id.iv_center_6_right_3);

		btn_center_7_jingpin_more = (Button) view
				.findViewById(R.id.btn_center_7_jingpin_more);
		iv_center_7_jingpin = (ImageView) view
				.findViewById(R.id.iv_center_7_jingpin);
		iv_center_7_1 = (ImageView) view.findViewById(R.id.iv_center_7_1);
		iv_center_7_2 = (ImageView) view.findViewById(R.id.iv_center_7_2);
		iv_center_7_3 = (ImageView) view.findViewById(R.id.iv_center_7_3);
		iv_center_7_4 = (ImageView) view.findViewById(R.id.iv_center_7_4);

		sv_shopping_center.setHeader(iv_center_bg_img);
		sv_shopping_center.setOnTurnListener(this);
		
		// 设置控件LayoutParams---------------------------------------
		int currentWidth = screenWidth - SizeUtil.dip2px(getActivity(), 20);

		// 第二块限时抢购
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
				currentWidth, (int) (currentWidth / 600.0 * 160));
		iv_center_2_limitbuy.setLayoutParams(params1);

		// 第三块主题
		int width3 = (int) ((currentWidth - SizeUtil.dip2px(getActivity(), 20)) / 3.0);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				width3, (int) ((width3 / 190.0) * 250));
		iv_center_3_new.setLayoutParams(params2);
		iv_center_3_gaodashang.setLayoutParams(params2);
		iv_center_3_tz.setLayoutParams(params2);

		// 四：Top和精品的左侧分类图标
		int width4 = (int) ((currentWidth - SizeUtil.dip2px(getActivity(), 5)) / 10.0 * 3.9);
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
				width4, (int) ((width4 / 230.0) * 500));
		iv_center_5_top_sale.setLayoutParams(params3);
		iv_center_7_jingpin.setLayoutParams(params3);

		// 5、五：Top和精品的商品
		int width5 = (int) ((currentWidth - SizeUtil.dip2px(getActivity(), 5)) / 10.0 * 6.1) / 2;
		LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
				width5, (int) ((width5 / 180.0) * 248));
		iv_center_5_1.setLayoutParams(params4);
		iv_center_5_2.setLayoutParams(params4);
		iv_center_5_3.setLayoutParams(params4);
		iv_center_5_4.setLayoutParams(params4);
		iv_center_7_1.setLayoutParams(params4);
		iv_center_7_2.setLayoutParams(params4);
		iv_center_7_3.setLayoutParams(params4);
		iv_center_7_4.setLayoutParams(params4);

		// 主题活动的主商品
		LinearLayout.LayoutParams params7 = new LinearLayout.LayoutParams(
				currentWidth, (int) ((currentWidth / 600.0) * 640));
		iv_center_6_right_3.setLayoutParams(params7);
	}

	@Override
	protected void initUI() {
		tv_title.setText("性多多");
		// 加载上次请求的数据
		setLastData();

		// 请求数据
		getCenterDate();

		// 开始倒计时
		startDaojishi();
	}

	@Override
	protected void setListener() {
		// 常规Listener
		btn_search.setOnClickListener(this);
		iv_center_2_limitbuy.setOnClickListener(this);
		btn_center_6_topic_more.setOnClickListener(this);
		// 点击进入专区
		iv_center_3_new.setOnClickListener(new OnClickListener3());
		iv_center_3_gaodashang.setOnClickListener(new OnClickListener3());
		iv_center_3_tz.setOnClickListener(new OnClickListener3());

		btn_center_5_top_more.setOnClickListener(new OnClickListener3());
		iv_center_5_top_sale.setOnClickListener(new OnClickListener3());

		btn_center_7_jingpin_more.setOnClickListener(new OnClickListener3());
		iv_center_7_jingpin.setOnClickListener(new OnClickListener3());

		// 进入商品详情
		iv_center_5_1.setOnClickListener(new OnClickListener2());
		iv_center_5_2.setOnClickListener(new OnClickListener2());
		iv_center_5_3.setOnClickListener(new OnClickListener2());
		iv_center_5_4.setOnClickListener(new OnClickListener2());
		iv_center_6_right_3.setOnClickListener(new OnClickListener2());
		iv_center_7_1.setOnClickListener(new OnClickListener2());
		iv_center_7_2.setOnClickListener(new OnClickListener2());
		iv_center_7_3.setOnClickListener(new OnClickListener2());
		iv_center_7_4.setOnClickListener(new OnClickListener2());
	}

	/**
	 * @描述：进入应用时加载上次保存的数据
	 * @时间 2014-10-23
	 */
	private void setLastData() {

		value_ads = MyApplication.getInstance().getAds();
		if (value_ads != null) {
			ads = value_ads.getDatasource();
			// 设置广告数据
			setAdData(ads);
		} else {
		}
		values_goods = MyApplication.getInstance().getCenterGoods();
		if (values_goods != null) {
			goods_center = values_goods.getDatasource();
			// 设置商品数据
			setGoodsData(goods_center);
		} else {
		}
		values_category = MyApplication.getInstance().getCenterCategory();
		if (values_category != null) {
			categorys = values_category.getDatasource();
			// 设置专区数据
			setCategoryDate(categorys);
		} else {
		}

	}

	/**
	 * 
	 * 描述：设置广告数据
	 */
	private void setAdData(ArrayList<Ad> ads) {
		// 添加广告,测试数据，添加操作
		ad_adapter = new ShoppingCenterAdViewPagerAdapter(ads, getActivity(),
				options2, imageLoader);
		viewpager_shoppingcenter_ad.setAdapter(ad_adapter);
		indicator_ad.setViewPager(viewpager_shoppingcenter_ad);
		if (switchTask.getState() == Thread.State.NEW && ads.size() > 0) {
			switchTask.start();
		}
	}

	/**
	 * @描述：设置商品数据
	 * @param goods_center
	 * @时间 2014-10-22
	 */
	private void setGoodsData(ArrayList<Ad> goods_center) {
		if (goods_center.size() >= 9) {
			imageLoader
					.displayImage(URLConfig.IMG_IP
							+ goods_center.get(0).getLogoPath(), iv_center_5_1,
							options);
			imageLoader
					.displayImage(URLConfig.IMG_IP
							+ goods_center.get(1).getLogoPath(), iv_center_5_2,
							options);
			imageLoader
					.displayImage(URLConfig.IMG_IP
							+ goods_center.get(2).getLogoPath(), iv_center_5_3,
							options);
			imageLoader
					.displayImage(URLConfig.IMG_IP
							+ goods_center.get(3).getLogoPath(), iv_center_5_4,
							options);
			imageLoader.displayImage(URLConfig.IMG_IP
					+ goods_center.get(4).getLogoPath(), iv_center_6_right_3,
					options);
			tv_center_6_title.setText(goods_center.get(4).getName());
			imageLoader
					.displayImage(URLConfig.IMG_IP
							+ goods_center.get(5).getLogoPath(), iv_center_7_1,
							options);
			imageLoader
					.displayImage(URLConfig.IMG_IP
							+ goods_center.get(6).getLogoPath(), iv_center_7_2,
							options);
			imageLoader
					.displayImage(URLConfig.IMG_IP
							+ goods_center.get(7).getLogoPath(), iv_center_7_3,
							options);
			imageLoader
					.displayImage(URLConfig.IMG_IP
							+ goods_center.get(8).getLogoPath(), iv_center_7_4,
							options);
		}
	}

	/**
	 * @描述：设置专区数据
	 * @param categorys
	 * @时间 2014-10-22
	 */
	private void setCategoryDate(ArrayList<Ad> categorys) {
		// if (categorys.size() == 7) {
		imageLoader
				.displayImage(
						URLConfig.IMG_IP + categorys.get(0).getLogoPath(),
						iv_center_2_limitbuy, options);
		imageLoader.displayImage(URLConfig.IMG_IP
				+ categorys.get(1).getLogoPath(), iv_center_3_new, options);
		imageLoader.displayImage(URLConfig.IMG_IP
				+ categorys.get(2).getLogoPath(), iv_center_3_gaodashang,
				options);
		imageLoader.displayImage(URLConfig.IMG_IP
				+ categorys.get(3).getLogoPath(), iv_center_3_tz, options);
		// 设置热门话题
		bbs_adapter = new ShoppingCenterBBSViewPagerAdapter(categorys.get(4)
				.getLogoPath(), activity, options2, imageLoader);
		viewpager_shoppingcenter_topic.setAdapter(bbs_adapter);
		indicator_topic.setViewPager(viewpager_shoppingcenter_topic);

		imageLoader
				.displayImage(
						URLConfig.IMG_IP + categorys.get(5).getLogoPath(),
						iv_center_5_top_sale, options);
		imageLoader.displayImage(URLConfig.IMG_IP
				+ categorys.get(6).getLogoPath(), iv_center_7_jingpin, options);
		// }
	}

	/**
	 * 默认点击事件
	 */
	@Override
	public void onClick(View v) {
		// 要打开的商城界面分类名称
		String classify_name = "";
		// 要打开的商城界面分类类型
		String classify_url;
		switch (v.getId()) {
		case R.id.btn_search:// 搜索界面
			Intent intent_search = new Intent(getActivity(),
					SearchActivity.class);
			getActivity().startActivity(intent_search);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.iv_center_2_limitbuy:// 进入限时抢购界面
			classify_name = "限时抢购";
			classify_url = "isTime";
			Intent intent = new Intent(getActivity(), LimitBuyActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("classify_name", classify_name);
			bundle.putString("classify_url", classify_url);
			intent.putExtras(bundle);

			startActivity(intent);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;

		case R.id.btn_center_6_topic_more:// 进入主题活动专区(活动专区)
			classify_name = "活动专区";
			classify_url = "isActivities";
			Intent intent2 = new Intent(getActivity(), ActivityActivity.class);
			Bundle bundle2 = new Bundle();
			bundle2.putString("classify_name", classify_name);
			bundle2.putString("classify_url", classify_url);
			intent2.putExtras(bundle2);

			startActivity(intent2);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;

		}
	}

	/**
	 * @名称：FragmentShoppingCenter2.java
	 * @描述：进入商品详情
	 * @author danding
	 * @时间 2014-10-22
	 */
	class OnClickListener2 implements OnClickListener {
		String goods_id = "";

		@Override
		public void onClick(View v) {
			if (goods_center.size() == 9) {
				switch (v.getId()) {
				case R.id.iv_center_5_1:// 热卖1
					goods_id = goods_center.get(0).getHomeId();
					break;
				case R.id.iv_center_5_2:// 热卖2
					goods_id = goods_center.get(1).getHomeId();
					break;
				case R.id.iv_center_5_3:// 热卖3
					goods_id = goods_center.get(2).getHomeId();
					break;
				case R.id.iv_center_5_4:// 热卖4
					goods_id = goods_center.get(3).getHomeId();
					break;
				case R.id.iv_center_6_right_3:// 主题活动商品
					goods_id = goods_center.get(4).getHomeId();
					break;
				case R.id.iv_center_7_1:// 精品1
					goods_id = goods_center.get(5).getHomeId();
					break;
				case R.id.iv_center_7_2:// 精品2
					goods_id = goods_center.get(6).getHomeId();
					break;
				case R.id.iv_center_7_3:// 精品3
					goods_id = goods_center.get(7).getHomeId();
					break;
				case R.id.iv_center_7_4:// 精品4
					goods_id = goods_center.get(8).getHomeId();
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
	 * @名称：FragmentShoppingCenter2.java
	 * @描述：进入专区
	 * @author danding
	 * @时间 2014-10-22
	 */
	class OnClickListener3 implements OnClickListener {
		// 要打开的商城界面分类名称
		String classify_name = "";
		// 要打开的商城界面分类类型
		String classify_url = "";

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_center_3_new:// 新品专区
				classify_name = "新品尝鲜";
				classify_url = "isNew";
				break;
			case R.id.iv_center_3_gaodashang:// 高大上
				classify_name = "高大上";
				classify_url = "isBest";
				break;
			case R.id.iv_center_3_tz:// 同志
				classify_name = "一路同行";
				classify_url = "isWalk";
				break;
			case R.id.btn_center_5_top_more:// 进入热卖专区
			case R.id.iv_center_5_top_sale:// 进入热卖专区
				classify_name = "Hot热卖";
				classify_url = "isHot";
				break;

			case R.id.btn_center_7_jingpin_more:// 进入精品专区(恶搞)
			case R.id.iv_center_7_jingpin:// 进入精品专区
				classify_name = "性情四色";
				classify_url = "isSpoof";
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
	 * @描述：获取首页的广告，商品，专区
	 * @时间 2014-10-22
	 */
	private void getCenterDate() {
		// 获取广告
		HttpUrlProvider.getIntance().getCenterAd(getActivity(),
				new TaskCenterAdBack(handler), URLConfig.CENTER_AD, "1");
		// 获取专区
		HttpUrlProvider.getIntance().getCenterAd(getActivity(),
				new TaskCenterCategoryBack(handler), URLConfig.CENTER_AD, "2");
		// 获取商品
		HttpUrlProvider.getIntance().getCenterAd(getActivity(),
				new TaskCenterGoodsBack(handler), URLConfig.CENTER_AD, "3");
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

	/**
	 * @描述：开始倒计时
	 * @时间 2014-10-22
	 */
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

	@Override
	public void onTurn() {
		
	}
}
