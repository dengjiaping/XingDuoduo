package com.xiuman.xingduoduo.ui.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.BBSAdViewPagerAdapter;
import com.xiuman.xingduoduo.adapter.BBSPlateListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskBBSPlateBack;
import com.xiuman.xingduoduo.callback.TaskPostListBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.activity.MyPostActivity;
import com.xiuman.xingduoduo.ui.activity.PostListActivity;
import com.xiuman.xingduoduo.ui.activity.UserInfoActivity;
import com.xiuman.xingduoduo.ui.activity.UserLoginActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.util.ImageCropUtils;
import com.xiuman.xingduoduo.util.SizeUtil;
import com.xiuman.xingduoduo.view.CircleImageView;
import com.xiuman.xingduoduo.view.indicator.CirclePageIndicator;

/**
 * @名称：FragmentCommunication.java
 * @描述：交流界面
 * @author danding
 * @version 2014-6-3
 */
public class FragmentBBS extends BaseFragment implements OnClickListener {

	/*----------------------------------组件--------------------------------*/
	// 标题栏
	private TextView tv_title;
	// Viewpager
	private ViewPager viewpager_bbs_ad;
	// Indicator
	private CirclePageIndicator mIndicator;
	// ListView
	private ListView lv_bbs_plates;
	// 广告名
	private TextView tv_bbs_ad_name;

	// 头像工具类
	private ImageCropUtils cropUtils;
	// 头像(Bitmap)
	private Bitmap user_head_bitmap;

	// 弹出pop
	private Button btn_update_info;
	/*---------PopWindow----------*/
	// 个人信息的pop
	private PopupWindow pop;
	// 个人信息View
	private View popview;
	// 头像+昵称
	private LinearLayout llyt_bbs_user_head;
	// 头像
	private CircleImageView iv_bbs_user_head;
	// 昵称
	private TextView tv_bbs_user_name;
	// 个人帖子
	private LinearLayout llyt_bbs_user_post;
	// 个人回复
	private LinearLayout llyt_bbs_user_reply;

	/*-------------------------------Adapter--------------------------------*/
	// 广告Adapter
	private BBSAdViewPagerAdapter ad_adapter;
	// 板块Adapter
	private BBSPlateListViewAdapter plate_adapter;

	/*----------------------------请求数据返回--------------------------------------*/
	// 板块返回
	private ActionValue<BBSPlate> value_plates = new ActionValue<BBSPlate>();
	// 板块列表
	private ArrayList<BBSPlate> plates = new ArrayList<BBSPlate>();
	// 请求广告贴返回
	private ActionValue<BBSPost> value = new ActionValue<BBSPost>();
	// 广告贴列表
	private List<BBSPost> bbspost = new ArrayList<BBSPost>();

	// ViewPager 循环播放---------------------------------------------------
	boolean cunhuan = false;
	private int page_id = 0;
	private Thread switchTask = new Thread() {
		public void run() {
			if (cunhuan) {
				viewpager_bbs_ad.setCurrentItem(page_id);
				page_id++;
				if (page_id >= bbspost.size()) {
					page_id = 0;
				}
			}
			cunhuan = true;
			mHandler.postDelayed(switchTask, 2000);
		}
	};
	Handler mHandler = new Handler();
	// 请求广告结果返回-----------------------------------------------------
	@SuppressLint("HandlerLeak")
	private Handler HandlerMain = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case AppConfig.BBS_POST_BACK:// 获取广告贴返回
				value = (ActionValue<BBSPost>) msg.obj;
				if (value.isSuccess()) {
					bbspost = value.getDatasource();
					setAdData();
					MyApplication.getInstance().saveBBSAds(value);
				}

				break;

			case AppConfig.NET_ERROR_NOTNET:// 无网络
				value = MyApplication.getInstance().getBBSAds();
				if (value != null) {
					bbspost = value.getDatasource();
					setAdData();
				}
				break;
			case AppConfig.BBS_PLATE_BACK_SUCCESSED:// 返回板块成功
				value_plates = (ActionValue<BBSPlate>) msg.obj;
				if (value_plates.isSuccess()) {
					plates = value_plates.getDatasource();
					// 设置板块
					setPlateDate(plates);
					MyApplication.getInstance().saveBBSPlate(value_plates);
				}
				break;
			case AppConfig.BBS_PLATE_BACK_FAILD:// 返回失败
				value_plates = MyApplication.getInstance().getBBSPlate();
				if (value_plates != null) {
					plates = value_plates.getDatasource();
					// 设置板块
					setPlateDate(plates);
				}
				break;

			}
		}
	};

	/*------------------------------------ImageLoader---------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bbs, container, false);
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

		cropUtils = new ImageCropUtils(getActivity());

	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById(View view) {
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_bbs_ad_name = (TextView) view.findViewById(R.id.tv_bbs_ad_name);
		viewpager_bbs_ad = (ViewPager) view.findViewById(R.id.viewpager_bbs_ad);
		mIndicator = (CirclePageIndicator) view
				.findViewById(R.id.indicator_bbs_ad);
		lv_bbs_plates = (ListView) view.findViewById(R.id.lv_bbs_plates);
		btn_update_info = (Button) view.findViewById(R.id.btn_update_info);

	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		// 加在上一次保存的广告贴数据
		setLastAdData();
		// 请求加载数据
		initFirstData();
		tv_title.setText("圈套");

	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		btn_update_info.setOnClickListener(this);
		// 广告切换监听
		viewpager_bbs_ad
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						super.onPageSelected(position);
						// tv_bbs_ad_name.setText(Test.addTestCommunicationAd()
						// .get(position).getAd_content());
						tv_bbs_ad_name
								.setText(bbspost.get(position).getTitle());
						mIndicator.setCurrentItem(position);
						page_id = position;
					}
				});
		lv_bbs_plates.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_bbs_plates.getItemAtPosition(position);
				if (obj instanceof BBSPlate) {
					BBSPlate plate = (BBSPlate) obj;
					Intent intent = new Intent(getActivity(),
							PostListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("bbs_plate", plate);
					intent.putExtras(bundle);
					getActivity().startActivity(intent);
					getActivity().overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	/**
	 * 
	 * 描述：设置广告数据
	 */
	private void setAdData() {
		// 添加广告,测试数据，添加操作
		ad_adapter = new BBSAdViewPagerAdapter(bbspost, getActivity(), options,
				imageLoader);
		viewpager_bbs_ad.setAdapter(ad_adapter);

		mIndicator.setViewPager(viewpager_bbs_ad);

		if (switchTask.getState() == Thread.State.NEW && bbspost.size() > 0) {
			tv_bbs_ad_name.setText(bbspost.get(0).getTitle());
			switchTask.start();
		}
	}

	/**
	 * @描述：设置板块数据
	 * @param plates
	 * @时间 2014-10-22
	 */
	private void setPlateDate(ArrayList<BBSPlate> plates) {
		if (plates.size() > 0) {
			for (int i = 0; i < plates.size(); i++) {
				if (plates.get(i).getIsShow()==1) {
					plates.remove(i);
				}
			}
			// 设置板块
			plate_adapter = new BBSPlateListViewAdapter(getActivity(), plates, options, imageLoader);
			lv_bbs_plates.setAdapter(plate_adapter);
		}

	}

	/**
	 * @描述：设置首页商品数据刚打开时为上次请求的数据 
	 * @2014-9-21
	 */
	private void setLastAdData() {
		value = MyApplication.getInstance().getBBSAds();
		if (value != null) {
			bbspost = value.getDatasource();
			// 设置广告数据
			setAdData();
		}
		value_plates = MyApplication.getInstance().getBBSPlate();
		value = MyApplication.getInstance().getBBSAds();
		if (value_plates != null) {
			plates = value_plates.getDatasource();
			// 设置板块数据
			setPlateDate(plates);
		}
	}

	/**
	 * @描述：登录 2014-9-25
	 */
	protected void toLogin() {
		Intent intent = new Intent(getActivity(), UserLoginActivity.class);
		getActivity().startActivity(intent);
		getActivity().overridePendingTransition(
				R.anim.translate_vertical_start_in,
				R.anim.translate_vertical_start_out);

	}

	/**
	 * @描述：加载数据 2014-9-25
	 */
	protected void initFirstData() {
		// 获取广告贴
		HttpUrlProvider.getIntance().getAdPost(getActivity(),
				new TaskPostListBack(HandlerMain), "10", 1, 6);

		// 获取板块
		HttpUrlProvider.getIntance().getBBSPlate(getActivity(),
				new TaskBBSPlateBack(HandlerMain));

	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_update_info:// 顶部按钮
			showPop(btn_update_info);
			break;

		case R.id.llyt_bbs_user_post:// 我的发帖
			if (MyApplication.getInstance().isUserLogin()) {
				Intent intent = new Intent(getActivity(), MyPostActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("type", 0);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
			} else {
				toLogin();
			}
			dismissPop();
			break;
		case R.id.llyt_bbs_user_reply:// 我的回复
			if (MyApplication.getInstance().isUserLogin()) {
				Intent intent = new Intent(getActivity(), MyPostActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("type", 1);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
			} else {
				toLogin();
			}
			dismissPop();
			break;

		case R.id.llyt_bbs_user_head:// 登陆
			if (MyApplication.getInstance().isUserLogin()) {
				Intent intent = new Intent(getActivity(),
						UserInfoActivity.class);
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);

			} else {
				toLogin();

			}
			dismissPop();
			break;
		}

	}

	/**
	 * 显示popupwindow
	 * 
	 * @param view
	 */
	private void showPop(View view) {
		if (pop == null) {
			popview = View.inflate(getActivity(), R.layout.pop_bbs_my, null);
			pop = new PopupWindow(popview, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
		}

		llyt_bbs_user_head = (LinearLayout) popview
				.findViewById(R.id.llyt_bbs_user_head);
		iv_bbs_user_head = (CircleImageView) popview
				.findViewById(R.id.iv_bbs_user_head);
		tv_bbs_user_name = (TextView) popview
				.findViewById(R.id.tv_bbs_user_name);
		llyt_bbs_user_post = (LinearLayout) popview
				.findViewById(R.id.llyt_bbs_user_post);
		llyt_bbs_user_reply = (LinearLayout) popview
				.findViewById(R.id.llyt_bbs_user_reply);

		if (MyApplication.getInstance().isUserLogin()) {
			tv_bbs_user_name.setText(MyApplication.getInstance().getUserInfo()
					.getNickname());
			File head = new File(cropUtils.createDirectory()
					+ cropUtils.createNewPhotoName());
			if (head.exists()) {

				user_head_bitmap = BitmapFactory.decodeFile(cropUtils
						.createDirectory() + cropUtils.createNewPhotoName());
				iv_bbs_user_head.setImageBitmap(user_head_bitmap);
			} else if (MyApplication.getInstance().getUserInfo()
					.getHead_image() != null) {
				imageLoader.displayImage(URLConfig.IMG_IP
						+ MyApplication.getInstance().getUserInfo()
								.getHead_image(), iv_bbs_user_head, options);
			}

		}

		llyt_bbs_user_head.setOnClickListener(this);
		llyt_bbs_user_post.setOnClickListener(this);
		llyt_bbs_user_reply.setOnClickListener(this);

		// 使其聚焦
		pop.setFocusable(true);
		// 设置允许在外点击消失
		pop.setOutsideTouchable(true);
		// 给pop设置背景
		Drawable background = new ColorDrawable(Color.TRANSPARENT);
		pop.setBackgroundDrawable(background);
		// 设置pop动画
		pop.setAnimationStyle(R.style.PopupAnimation2);
		// 设置pop 的位置
		pop.showAtLocation(view, Gravity.TOP, 0,
				SizeUtil.dip2px(getActivity(), 46)
						+ getStatusHeight(getActivity()));
	}

	/**
	 * 
	 * @描述：隐藏pop
	 * @date：2014-6-19
	 */
	private void dismissPop() {
		if (pop != null) {
			pop.dismiss();
		}
	}

	/**
	 * @描述：获取状态栏高度
	 * @param activity
	 * @return 2014-9-25
	 */
	public static int getStatusHeight(Activity activity) {
		int statusHeight = 0;
		Rect localRect = new Rect();
		activity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass
						.getField("status_bar_height").get(localObject)
						.toString());
				statusHeight = activity.getResources()
						.getDimensionPixelSize(i5);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
			}
		}
		return statusHeight;
	}
}
