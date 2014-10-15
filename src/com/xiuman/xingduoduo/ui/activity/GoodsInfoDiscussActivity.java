package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.SearchViewPagerAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskAdd2CartBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsStandard;
import com.xiuman.xingduoduo.model.GoodsTwo;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.BaseActivity;
import com.xiuman.xingduoduo.ui.fragment.FragmentGoodsDiscuss;
import com.xiuman.xingduoduo.ui.fragment.FragmentGoodsInfo;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.BadgeView;
import com.xiuman.xingduoduo.view.CustomDialog;
import com.xiuman.xingduoduo.view.SingleSelectCheckBoxs;
import com.xiuman.xingduoduo.view.SingleSelectCheckBoxs.OnSelectListener;

/**
 * @名称：GoodsInfoDiscussActivity.java
 * @描述：商品图文详情+评论列表
 * @author danding
 * @时间 2014-10-13
 */
public class GoodsInfoDiscussActivity extends BaseActivity implements
		SwipeBackActivityBase, OnClickListener {

	/*-------------------------------------------组件--------------------------------------*/
	// 标题栏
	private RelativeLayout rlyt_title;
	// 返回
	private Button btn_back;
	//
	private RadioGroup rg_container;
	// 详情
	private RadioButton rbtn_info;
	// 评论
	private RadioButton rbtn_discuss;
	// 购物车
	private Button btn_shopping_cart;
	// 购物车数量
	private BadgeView badge;
	// 内容container
	private LinearLayout llyt_container;
	// ViewPager
	private ViewPager viewpager;
	// 联系客服
	private Button btn_call_kefu;
	// 加入购物车
	private Button btn_add2cart;
	// 立即购买
	private Button btn_buy_at_once;

	// FragmentManager-----------------------------------------
	private FragmentManager fragmentManager;
	// Fragments
	private List<Fragment> fragments = new ArrayList<Fragment>();
	// 图文详情
	private FragmentGoodsInfo fragment_info;
	// 评价
	private FragmentGoodsDiscuss fragment_discuss;

	// ViewPager Adapter--------------------------------------
	private SearchViewPagerAdapter viewpager_adapter;
	// ------------------------------PopWindow-------------------------------
	private PopupWindow pop;
	// View(pop)
	private View popview;
	// 取消按钮
	private Button btn_pop_goods_cancel;
	// 加入购物车
	private Button btn_pop_goods_add2cart;
	// 屏幕宽高
	private int screenHeight;
	// 商品规格列表
	private SingleSelectCheckBoxs sscb_pop_size;
	// 数量减
	private ImageView ivbtn_pop_minus;
	// 数量加
	private ImageView ivbtn_pop_add;
	// 数量EditText
	private EditText et_pop_goods_number;
	// 商品图
	private ImageView iv_pop_goods_psoter;
	// 商品价格
	private TextView tv_pop_goods_price;
	// 商品规格
	private TextView tv_pop_goods_size;
	// 拨打电话Dialog
	private CustomDialog dialog;

	/*----------------------------------滑动结束--------------------------*/
	private SwipeBackActivityHelper mHelper;
	public SwipeBackLayout mSwipeBackLayout;

	/*-----------------------------------------后缩动画-------------------------------*/
	// 主页缩放动画
	private Animation mScalInAnimation1;
	// 主页缩放完毕小幅回弹动画
	private Animation mScalInAnimation2;
	// 主页回弹正常状态动画
	private Animation mScalOutAnimation;
	// 标题恢复动画
	private Animation mTranInAnimation;
	// 标题消失动画
	private Animation mTranOutAnimation;

	/*--------------------------------数据-------------------------------*/
	// 当前购物车数量
	private int cart_goods_number = 0;
	// 从上级界面接收到的二级商品详情
	private GoodsTwo goods_two;
	// 从上级界面接收到的商品id
	private String goods_id;
	//从上级界面接收到的要查看的界面current
	private int current_fragment = 0;
	// 默认设置的商品数量
	private int goods_number = 1;
	// 当前登录的用户信息
	private User user;

	/*---------------------------------加入购物车选择的商品规格和商品数量------------------------*/
	// 选择商品规格时选中的产品id
	private String productId;
	// 选择产品的数量
	private int quantity = 1;
	// 在选择规格的时候弹窗标记(1加入购物车，2立即购买)(确定规格的时候判断动作)
	private int add_tag = 1;

	/*-----------------------ImageLoader-----------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*--------------------------------消息返回--------------------*/
	// 添加商品到购物车
	private ActionValue<?> value_add2cart;

	// 消息处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.ADD2Cart:// 添加商品到购物车成功
				value_add2cart = (ActionValue<?>) msg.obj;

				if (value_add2cart.isSuccess()) {
					ToastUtil.ToastView(GoodsInfoDiscussActivity.this,
							value_add2cart.getMessage());
					cart_goods_number += Integer.parseInt(et_pop_goods_number
							.getText().toString());
					MyApplication.getInstance().setCartGoodsNumber(
							cart_goods_number);
					badge.setText(cart_goods_number + "");
					if (add_tag == 1) {

					} else if (add_tag == 2) {// 立即购买(进入购物车)
						Intent intent_cart = new Intent(
								GoodsInfoDiscussActivity.this,
								ShoppingCartActivity.class);
						startActivity(intent_cart);
						overridePendingTransition(
								R.anim.translate_horizontal_start_in,
								R.anim.translate_horizontal_start_out);
					}

				} else {
					ToastUtil.ToastView(GoodsInfoDiscussActivity.this,
							value_add2cart.getMessage());
				}

				break;
			case AppConfig.NET_ERROR_NOTNET://

				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_info_discuss);
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
				.cacheInMemory(true)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true)
				// 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.NONE).build();

		// 获取屏幕宽高
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 获取屏幕宽度
		screenHeight = dm.heightPixels;
		
		//获取上级界面传递过来的商品数据
		goods_two = (GoodsTwo) getIntent().getExtras().getSerializable("goods_two");
		goods_id = getIntent().getExtras().getString("goods_id");
		current_fragment = getIntent().getExtras().getInt("current");
		fragment_info = new FragmentGoodsInfo();
		fragment_discuss = new FragmentGoodsDiscuss();
		fragmentManager = getSupportFragmentManager();
		fragments.add(fragment_info);
		fragments.add(fragment_discuss);

		/*----------------------------------滑动结束--------------------------*/
		mHelper = new SwipeBackActivityHelper(this);
		mHelper.onActivityCreate();
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);// 右滑结束
	}

	@Override
	protected void findViewById() {
		rlyt_title = (RelativeLayout) findViewById(R.id.rlyt_title);
		rg_container = (RadioGroup) findViewById(R.id.rg_container);
		rbtn_info = (RadioButton) findViewById(R.id.rbtn_info);
		rbtn_discuss = (RadioButton) findViewById(R.id.rbtn_discuss);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_shopping_cart = (Button) findViewById(R.id.btn_shopping_cart);
		badge = new BadgeView(this, btn_shopping_cart);
		badge.setLayoutDirection(1);
		badge.setTextSize(10);

		llyt_container = (LinearLayout) findViewById(R.id.llyt_contanier);
		viewpager = (ViewPager) findViewById(R.id.viewpager_goods_info_discuss);

		btn_call_kefu = (Button) findViewById(R.id.btn_call_kefu);
		btn_add2cart = (Button) findViewById(R.id.btn_goods_add2cart);
		btn_buy_at_once = (Button) findViewById(R.id.btn_goods_buy_at_once);

		// 动画初始化
		mScalInAnimation1 = AnimationUtils.loadAnimation(
				GoodsInfoDiscussActivity.this, R.anim.root_in);
		mScalInAnimation2 = AnimationUtils.loadAnimation(
				GoodsInfoDiscussActivity.this, R.anim.root_in2);
		mScalOutAnimation = AnimationUtils.loadAnimation(
				GoodsInfoDiscussActivity.this, R.anim.root_out);
		mTranInAnimation = AnimationUtils.loadAnimation(
				GoodsInfoDiscussActivity.this, R.anim.title_in);
		mTranOutAnimation = AnimationUtils.loadAnimation(
				GoodsInfoDiscussActivity.this, R.anim.title_out);
	}

	@Override
	protected void initUI() {
		viewpager.setOffscreenPageLimit(2);
		viewpager_adapter = new SearchViewPagerAdapter(fragments, this,
				fragmentManager);
		viewpager.setAdapter(viewpager_adapter);

		
		if(current_fragment==0){
			viewpager.setCurrentItem(0);
			rbtn_info.setChecked(true);
		}else{
			viewpager.setCurrentItem(1);
			rbtn_discuss.setChecked(true);
		}
		
		// 设置商品规格
		selectGoodsSize();
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_add2cart.setOnClickListener(this);
		btn_buy_at_once.setOnClickListener(this);
		btn_call_kefu.setOnClickListener(this);
		btn_shopping_cart.setOnClickListener(this);

		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				if (position == 0) {
					rbtn_info.setChecked(true);
				}
				if (position == 1) {
					rbtn_discuss.setChecked(true);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		rg_container.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbtn_info:
					viewpager.setCurrentItem(0);
					break;
				case R.id.rbtn_discuss:
					viewpager.setCurrentItem(1);
					break;
				}
			}
		});

		mScalInAnimation1.setAnimationListener(new ScalInAnimation());
	}
	/**
	 * @描述：获取商品id
	 * @return
	 * @时间 2014-10-14
	 */
	public String getGoods_id() {
		return goods_id;
	}

	@Override
	protected void onResume() {
		super.onResume();
		cart_goods_number = MyApplication.getInstance().getCartGoodsNumber();
		if (MyApplication.getInstance().isUserLogin()) {
			badge.setText(cart_goods_number + "");
		} else {
			badge.setText(0 + "");
		}
		badge.show();
		if (MyApplication.getInstance().isUserLogin()) {
			user = MyApplication.getInstance().getUserInfo();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;
		case R.id.btn_goods_add2cart:// 加入购物车
			showPop(v);
			add_tag = 1;
			break;
		case R.id.btn_goods_buy_at_once:// 立即购买
			showPop(v);
			add_tag = 2;
			break;
		case R.id.btn_call_kefu:// 联系客服
			dialog = new CustomDialog(this,
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
		case R.id.btn_shopping_cart:// 打开购物车
			if (MyApplication.getInstance().isUserLogin()) {
				Intent intent_cart = new Intent(GoodsInfoDiscussActivity.this,
						ShoppingCartActivity.class);
				startActivity(intent_cart);
				overridePendingTransition(R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
			} else {
				Intent intent_login = new Intent(GoodsInfoDiscussActivity.this,
						UserLoginActivity.class);
				startActivity(intent_login);
				overridePendingTransition(R.anim.translate_vertical_start_in,
						R.anim.translate_vertical_start_out);
			}
			break;
		case R.id.btn_pop_cancel:// 退出规格选择
			dismissPop();
			break;
		case R.id.ivbtn_pop_add:// 增加商品数量
			goods_number = Integer.valueOf(et_pop_goods_number.getText()
					.toString());
			goods_number++;
			et_pop_goods_number.setText(goods_number + "");
			break;
		case R.id.ivbtn_pop_minus:// 减少商品数量
			goods_number = Integer.valueOf(et_pop_goods_number.getText()
					.toString());
			if (goods_number > 1) {
				goods_number--;
				et_pop_goods_number.setText(goods_number + "");
			}
			break;
		case R.id.btn_pop_goods_add2cart:// pop确定加入购物车或者立即购买
			if (sscb_pop_size.getPosition() < 0) {
				ToastUtil.ToastView(GoodsInfoDiscussActivity.this, "请选择商品规格");
			} else if (add_tag == 1) {
				add2Cart();
				dismissPop();
			} else if (add_tag == 2) {
				add2Cart();
				dismissPop();
			}
			break;
		}
	}

	/**
	 * @描述：加入购物车 2014-8-13
	 */
	private void add2Cart() {
		if (MyApplication.getInstance().isUserLogin()) {
			user = MyApplication.getInstance().getUserInfo();
			String userId = user.getUserId();
			HttpUrlProvider.getIntance().getAdd2Cart(this,
					new TaskAdd2CartBack(handler), URLConfig.ADD_GOODS_CART,
					userId, productId, quantity);

		} else {
			Intent intent_login = new Intent(GoodsInfoDiscussActivity.this,
					UserLoginActivity.class);
			startActivity(intent_login);
			overridePendingTransition(R.anim.translate_vertical_start_in,
					R.anim.translate_vertical_start_out);
		}
	}

	/**
	 * 
	 * @描述：用户点击购买的时候弹出的商品规格选择框
	 * @param view
	 *            显示pop位置指示
	 * @date：2014-7-15
	 */
	private void selectGoodsSize() {
		if (pop == null) {
			popview = View.inflate(GoodsInfoDiscussActivity.this,
					R.layout.pop_goods_size, null);
			pop = new PopupWindow(popview, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
		}
		sscb_pop_size = (SingleSelectCheckBoxs) popview
				.findViewById(R.id.sscb_pop_size);
		btn_pop_goods_cancel = (Button) popview
				.findViewById(R.id.btn_pop_cancel);
		btn_pop_goods_add2cart = (Button) popview
				.findViewById(R.id.btn_pop_goods_add2cart);
		ivbtn_pop_add = (ImageView) popview.findViewById(R.id.ivbtn_pop_add);
		ivbtn_pop_minus = (ImageView) popview
				.findViewById(R.id.ivbtn_pop_minus);
		et_pop_goods_number = (EditText) popview
				.findViewById(R.id.et_pop_goods_number);
		iv_pop_goods_psoter = (ImageView) popview
				.findViewById(R.id.iv_pop_goods_psoter);
		tv_pop_goods_price = (TextView) popview
				.findViewById(R.id.tv_pop_goods_price);
		tv_pop_goods_size = (TextView) popview
				.findViewById(R.id.tv_pop_goods_size);

		ivbtn_pop_add.setOnClickListener(this);
		ivbtn_pop_minus.setOnClickListener(this);
		btn_pop_goods_cancel.setOnClickListener(this);
		btn_pop_goods_add2cart.setOnClickListener(this);

		// 商品数量改变监听
		et_pop_goods_number.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				quantity = Integer.parseInt(et_pop_goods_number.getText()
						.toString().trim());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		// 设置规格
		ArrayList<GoodsStandard> sizes = goods_two.getProductDetail();
		if (sizes.size() == 1) {
			sizes.get(0).setSpecifications("标准规格");
		}
		sscb_pop_size.setData(sizes);
		sscb_pop_size.setOnSelectListener(new OnSSChkClickEvent());
		// 设置默认商品数量
		et_pop_goods_number.setText(goods_number + "");

		// 商品图
		imageLoader.displayImage(
				URLConfig.IMG_IP + goods_two.getThumbnailGoodsImagePath(),
				iv_pop_goods_psoter, options);
		tv_pop_goods_price.setText("￥" + goods_two.getGoods_price());

		// 使其聚焦
		pop.setFocusable(true);
		// 设置允许在外点击消失
		pop.setOutsideTouchable(true);
		// 给pop设置背景
		Drawable background = new ColorDrawable(Color.TRANSPARENT);
		pop.setBackgroundDrawable(background);
		pop.setAnimationStyle(R.style.PopupAnimation);

	}

	/**
	 * 显示 pop
	 * 
	 * @param view
	 */
	private void showPop(View view) {
		if (pop != null) {
			// 标题和主页开始播放动画
			rlyt_title.startAnimation(mTranOutAnimation);
			llyt_container.startAnimation(mScalInAnimation1);

			pop.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					rlyt_title.startAnimation(mTranInAnimation);
					llyt_container.startAnimation(mScalOutAnimation);
				}
			});

			// 设置pop 的位置
			pop.showAtLocation(view, Gravity.TOP, 0, screenHeight);
		}
	}

	/**
	 * 
	 * @描述：隐藏pop
	 * @date：2014-6-19
	 */
	private void dismissPop() {
		if (pop != null) {
			pop.dismiss();
			// 标题和主页开始播放动画
		}
	}

	/**
	 * @描述：商品规格选择监听
	 * @author danding 2014-7-31
	 */
	class OnSSChkClickEvent implements OnSelectListener {

		@Override
		public void onSelect(int position) {
			productId = goods_two.getProductDetail().get(position)
					.getProductId();
			tv_pop_goods_price.setText("￥"
					+ goods_two.getProductDetail().get(position).getPrice());
			tv_pop_goods_size.setText(goods_two.getProductDetail()
					.get(position).getSpecifications());
		}
	}

	/**
	 * @描述：缩小动画的回调
	 * @名称：GoodsActivity.java
	 * @author CSX
	 * @日期：2014-10-10
	 */
	public class ScalInAnimation implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			llyt_container.startAnimation(mScalInAnimation2);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}
	}

	/*--------------------------------------滑动结束---------------------------*/
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate();
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v == null && mHelper != null)
			return mHelper.findViewById(id);
		return v;
	}

	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mHelper.getSwipeBackLayout();
	}

	@Override
	public void setSwipeBackEnable(boolean enable) {
		getSwipeBackLayout().setEnableGesture(enable);
	}

	@Override
	public void scrollToFinishActivity() {
		getSwipeBackLayout().scrollToFinishActivity();
	}

}
