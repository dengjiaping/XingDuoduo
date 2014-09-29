package com.xiuman.xingduoduo.ui.fragment;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.umeng.fb.FeedbackAgent;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.lock.ui.GuideGesturePasswordActivity;
import com.xiuman.xingduoduo.lock.ui.UnlockGesturePasswordActivity;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.ui.activity.AppRecommendActivity;
import com.xiuman.xingduoduo.ui.activity.CollectionActivity;
import com.xiuman.xingduoduo.ui.activity.HelpActivity;
import com.xiuman.xingduoduo.ui.activity.OrderListActivity;
import com.xiuman.xingduoduo.ui.activity.SettingActivity;
import com.xiuman.xingduoduo.ui.activity.UserInfoActivity;
import com.xiuman.xingduoduo.ui.activity.UserLoginActivity;
import com.xiuman.xingduoduo.ui.activity.UserRegisterActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.util.ImageCropUtils;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.CircleImageView;
import com.xiuman.xingduoduo.view.CustomDialog;
import com.xiuman.xingduoduo.view.PullScrollView;
import com.xiuman.xingduoduo.view.PullScrollView.OnTurnListener;
import com.xiuman.xingduoduo.view.SlidingButton;

/**
 * @名称：FragmentMe.java
 * @描述：个人中心
 * @author danding
 * @version 2014-6-3
 */
public class FragmentMe extends BaseFragment implements OnClickListener,
		OnTurnListener {

	/*----------------------------组件-----------------------------*/
	// 注册
	private Button btn_me_register;
	// 设置
	private Button btn_me_setting;
	// 头像
	private CircleImageView iv_me_head;
	// 用户昵称
	private TextView tv_me_user_name;;
	// 下拉回弹背景
	private ImageView iv_me_bg_img;
	// 下拉回弹ScrollView
	private PullScrollView sv_me;
	// 拨打电话Dialog
	private CustomDialog dialog;

	/*----------------横向菜单-----------*/
	// 我的订单
	private Button btn_me_my_order;
	// 我的收藏
	private Button btn_me_my_collection;
	// 系统消息
	private Button btn_me_app_mseeage;

	/*-----------帮助信息栏------------*/
	// 隐私保护滑动按钮
	private SlidingButton mSlideButton;
	// 隐私保护
	private LinearLayout llyt_me_menu_protect;
	// 意见反馈
	private LinearLayout llyt_me_menu_feedback;
	// 拨打客服
	private LinearLayout llyt_me_menu_kefu;
	// 微博咨询
	private LinearLayout llyt_me_menu_weibo;
	// 使用帮助
	private LinearLayout llyt_me_menu_help;
	// 您可能喜欢的应用
	private LinearLayout llyt_setting_app_recommend;

	/*--------------------------数据变量-----------------*/
	// 头像工具类
	private ImageCropUtils cropUtils;
	// 头像(Bitmap)
	private Bitmap user_head_bitmap;
	// 当前登录用户
	private User user;
	/*-----------------------ImageLoader-----------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*--------------------------友盟------------------------*/
	// 意见反馈
	private FeedbackAgent agent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_me, container, false);
		initData();
		findViewById(view);
		initUI();
		setListener();
		return view;
	}

	/**
	 * 数据初始化
	 */
	@Override
	protected void initData() {
		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.weiboitem_pic_loading) //
		// 在ImageView加载过程中显示图片
				.showImageForEmptyUri(R.drawable.bg_head) // image连接地址为空时
				.showImageOnFail(R.drawable.bg_head) // image加载失败
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();
		cropUtils = new ImageCropUtils(getActivity());

		agent = new FeedbackAgent(getActivity());
		agent.sync();
	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById(View view) {
		btn_me_register = (Button) view.findViewById(R.id.btn_me_register);
		btn_me_setting = (Button) view.findViewById(R.id.btn_me_setting);
		sv_me = (PullScrollView) view.findViewById(R.id.sv_me);
		iv_me_bg_img = (ImageView) view.findViewById(R.id.iv_me_bg_img);

		tv_me_user_name = (TextView) view.findViewById(R.id.tv_me_user_name);
		iv_me_head = (CircleImageView) view.findViewById(R.id.iv_me_head);

		// 横向菜单
		btn_me_my_order = (Button) view.findViewById(R.id.btn_me_my_order);
		btn_me_my_collection = (Button) view
				.findViewById(R.id.btn_me_my_collection);
		btn_me_app_mseeage = (Button) view.findViewById(R.id.btn_me_my_app_msg);

		// 帮助
		mSlideButton = (SlidingButton) view.findViewById(R.id.mSliderBtn);
		llyt_me_menu_protect = (LinearLayout) view
				.findViewById(R.id.llyt_me_menu_protect);
		llyt_me_menu_feedback = (LinearLayout) view
				.findViewById(R.id.llyt_me_menu_feedback);
		llyt_me_menu_kefu = (LinearLayout) view
				.findViewById(R.id.llyt_me_menu_kefu);
		llyt_me_menu_weibo = (LinearLayout) view
				.findViewById(R.id.llyt_me_menu_weibo);
		llyt_me_menu_help = (LinearLayout) view
				.findViewById(R.id.llyt_me_menu_help);
		llyt_setting_app_recommend = (LinearLayout) view
				.findViewById(R.id.llyt_setting_app_recommend);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		mSlideButton.setImageResource(R.drawable.btn_slide_bottom,
				R.drawable.btn_slide_frame, R.drawable.btn_slide_mask,
				R.drawable.btn_slide_unpressed, R.drawable.btn_slide_pressed);

		sv_me.setHeader(iv_me_bg_img);
		sv_me.setOnTurnListener(this);
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		btn_me_register.setOnClickListener(this);
		btn_me_setting.setOnClickListener(this);
		iv_me_head.setOnClickListener(this);

		// 菜单
		btn_me_my_order.setOnClickListener(this);
		btn_me_my_collection.setOnClickListener(this);
		btn_me_app_mseeage.setOnClickListener(this);

		// 帮助
		llyt_me_menu_protect.setOnClickListener(this);
		llyt_me_menu_feedback.setOnClickListener(this);
		llyt_me_menu_kefu.setOnClickListener(this);
		llyt_me_menu_weibo.setOnClickListener(this);
		llyt_me_menu_help.setOnClickListener(this);
		llyt_setting_app_recommend.setOnClickListener(this);

		// 滑动按钮开启隐私保护
		mSlideButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// MyApplication.getInstance().getLockPatternUtils().clearLock();
					boolean result = MyApplication.getInstance()
							.getLockPatternUtils().isPatternExist();
					if (!result) {
						Intent intent = new Intent(getActivity(),
								GuideGesturePasswordActivity.class);
						// 打开新的Activity
						startActivity(intent);
					}
				} else {// 由打开到关闭

					Intent intent = new Intent(getActivity(),
							UnlockGesturePasswordActivity.class);
					// 用来标记是删除密码，还是解锁
					intent.putExtra("cancel", false);
					startActivity(intent);
				}
			}
		});
	}

	/**
	 * 返回该界面判断是否设置密码
	 */
	@Override
	public void onResume() {
		super.onResume();
		boolean result = MyApplication.getInstance().getLockPatternUtils()
				.isPatternExist();
		mSlideButton.setChecked(result);
		// 加载用户信息
		initUserInfo();
	}

	/**
	 * 加载用户信息
	 */
	private void initUserInfo() {
		// 如果用户已经登陆
		if (MyApplication.getInstance().isUserLogin()) {
			user = MyApplication.getInstance().getUserInfo();
			File head = new File(cropUtils.createDirectory()
					+ cropUtils.createNewPhotoName());
			if (head.exists()) {
				user_head_bitmap = BitmapFactory.decodeFile(cropUtils
						.createDirectory() + cropUtils.createNewPhotoName());
				iv_me_head.setImageBitmap(user_head_bitmap);
			}else if(user.getHead_image()!=null){
				imageLoader.displayImage(URLConfig.IMG_IP+user.getHead_image(), iv_me_head, options);
			}
			tv_me_user_name.setText(user.getNickname());
			btn_me_register.setVisibility(View.INVISIBLE);
		} else {
			iv_me_head.setImageResource(R.drawable.bg_head);
			tv_me_user_name.setText(R.string.me_please_login);
			btn_me_register.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 
	 * 描述：点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_me_register:// 注册
			Intent intent_register = new Intent(getActivity(),
					UserRegisterActivity.class);
			getActivity().startActivity(intent_register);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.btn_me_setting:// 设置
			Intent intent_setting = new Intent(getActivity(),
					SettingActivity.class);
			getActivity().startActivity(intent_setting);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.iv_me_head:// 用户头像(如果用户已经登录则查看用户信息，否则登录界面)
			if (MyApplication.getInstance().isUserLogin()) {// 查看用户信息
				Intent intent_user = new Intent(getActivity(),
						UserInfoActivity.class);
				Bundle bundle_user = new Bundle();
				// 测试数据
				bundle_user.putSerializable("user", user);
				intent_user.putExtras(bundle_user);
				getActivity().startActivity(intent_user);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
			} else {// 登录
				Intent intent_login = new Intent(getActivity(),
						UserLoginActivity.class);
				getActivity().startActivity(intent_login);
				getActivity().overridePendingTransition(
						R.anim.translate_vertical_start_in,
						R.anim.translate_vertical_start_out);
			}
			break;
		case R.id.btn_me_my_order:// 我的订单

			if (MyApplication.getInstance().isUserLogin()) {// 如果用户已经登录则可以查看订单
				Intent intent_order = new Intent(getActivity(),
						OrderListActivity.class);
				Bundle bundle_user = new Bundle();
				// 测试数据
				bundle_user.putSerializable("user", user);
				intent_order.putExtras(bundle_user);
				getActivity().startActivity(intent_order);
			} else {
				Intent intent_login = new Intent(getActivity(),
						UserLoginActivity.class);
				getActivity().startActivity(intent_login);
				getActivity().overridePendingTransition(
						R.anim.translate_vertical_start_in,
						R.anim.translate_vertical_start_out);
			}

			break;
		case R.id.btn_me_my_collection:// 我的收藏
			if (MyApplication.getInstance().isUserLogin()) {// 如果用户已经登录则可以查看收藏
				Intent intent_collection = new Intent(getActivity(),
						CollectionActivity.class);
				Bundle bundle_user = new Bundle();
				// 测试数据
				bundle_user.putSerializable("user", user);
				intent_collection.putExtras(bundle_user);
				getActivity().startActivity(intent_collection);
			} else {
				Intent intent_login = new Intent(getActivity(),
						UserLoginActivity.class);
				getActivity().startActivity(intent_login);
				getActivity().overridePendingTransition(
						R.anim.translate_vertical_start_in,
						R.anim.translate_vertical_start_out);
			}

			break;
		case R.id.btn_me_my_app_msg:// 系统消息
			ToastUtil.ToastView(getActivity(), "功能暂未开放哦^_^");
			break;
		case R.id.llyt_me_menu_protect:// 隐私保护
			if (mSlideButton.isChecked()) {
				mSlideButton.setChecked(false);
			} else {
				mSlideButton.setChecked(true);
			}
			break;
		case R.id.llyt_me_menu_feedback:// 意见反馈
			agent.startFeedbackActivity();
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.llyt_me_menu_kefu:// 拨打客服电话
			dialog = new CustomDialog(getActivity(),
					getString(R.string.dialog_call_kefu_title),
					getString(R.string.dialog_call_kefu_message));
			dialog.btn_custom_dialog_sure
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:"
											+ getString(R.string.me_menu_kefu)));
							startActivity(intent);
							dialog.dismiss();
						}
					});
			dialog.btn_custom_dialog_cancel
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
			dialog.show();
			break;
		case R.id.llyt_me_menu_help:// 使用帮助
			Intent intent_help = new Intent(getActivity(), HelpActivity.class);
			startActivity(intent_help);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.llyt_me_menu_weibo:// 微博

			break;
		case R.id.llyt_setting_app_recommend:// 应用推荐
			Intent intent_app = new Intent(getActivity(),
					AppRecommendActivity.class);
			startActivity(intent_app);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		}
	}

	@Override
	public void onTurn() {

	}
}
