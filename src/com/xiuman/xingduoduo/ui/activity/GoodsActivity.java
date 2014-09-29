package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.GoodsImgsViewPagerAdapter;
import com.xiuman.xingduoduo.adapter.GoodsRecommendHListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskAdd2CartBack;
import com.xiuman.xingduoduo.callback.TaskAddCollectionBack;
import com.xiuman.xingduoduo.callback.TaskDeleteCollectionBack;
import com.xiuman.xingduoduo.callback.TaskGoodsInfoBack;
import com.xiuman.xingduoduo.callback.TaskGoodsRecommendBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsOne;
import com.xiuman.xingduoduo.model.GoodsStandard;
import com.xiuman.xingduoduo.model.GoodsTwo;
import com.xiuman.xingduoduo.model.ImagePath;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.BadgeView;
import com.xiuman.xingduoduo.view.CustomDialog;
import com.xiuman.xingduoduo.view.HorizontalListView;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.SingleSelectCheckBoxs;
import com.xiuman.xingduoduo.view.SingleSelectCheckBoxs.OnSelectListener;
import com.xiuman.xingduoduo.view.indicator.CirclePageIndicator;

/**
 * 
 * @名称：GoodsActivity.java
 * @描述：商品详情展示界面
 * @author danding
 * @version 2014-6-18
 */
public class GoodsActivity extends Base2Activity implements OnClickListener {

	/*--------------------------------组件-------------------------------*/
	// 返回
	private Button btn_back_goods;
	// 购物车
	private Button btn_goods_shopping_cart;
	// 收藏
	private Button btn_collect;
	// ViewPager（图片）
	private ViewPager viewpager_goods_imgs;
	// 指示器
	private CirclePageIndicator indicator_goods_imgs;
	// 图片ImageView s
	private List<ImageView> img_ivs = new ArrayList<ImageView>();
	// 商品价格
	private TextView tv_goods_price;
	// 商品原价
	private TextView tv_goods_cost_price;
	// 商品折扣
	private TextView tv_goods_zhekou;
	// 商品名
	private TextView tv_goods_name;
	// 月销量
	private TextView tv_goods_sales;
	// 商品基本参数
	private TextView tv_goods_params;
	// 查看图文详情
	private Button btn_goods_detail;
	// 查看评价
	private Button btn_goods_appraise;
	// 查看基本参数
	private Button btn_goods_params;
	//相关推荐
	private LinearLayout llyt_goods_recommend;
	// 商品推荐
	private HorizontalListView lv_goods_recommend;
	// 加入购物车
	private Button btn_goods_add2cart;
	// 立即购买
	private Button btn_goods_buy;
	// 打开购物车
	private Button btn_call_kefu;
	// 购物车数量
	private BadgeView badge;
	// 拨打电话Dialog
	private CustomDialog dialog;

	// ------------------------------PopWindow-------------------------------
	private PopupWindow pop;
	// View(pop)
	private View popview;
	// 取消按钮
	private Button btn_pop_goods_cancel;
	// 加入购物车
	private Button btn_pop_goods_add2cart;
	// 屏幕宽高
	private int screenHeight, screenWidth;
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

	/*-------------------------------Adapter-----------------------------*/
	// 图片ViewPager
	private GoodsImgsViewPagerAdapter adapter_img;
	// 商品推荐Adapter
	private GoodsRecommendHListViewAdapter adapter_recommend;

	/*--------------------------------数据-------------------------------*/
	// 当前购物车数量
	private int cart_goods_number = 0;
	// 当前界面请求获取的二级商品详情
	private GoodsTwo goods_two;
	// 商品图片列表
	private ArrayList<ImagePath> goods_img_urls = new ArrayList<ImagePath>();
	// 从上级界面接收到的商品id
	private String goods_id;
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

	// 数据加载Dialog
	private LoadingDialog loadingdialog;
	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 底部加入购物车按钮
	private LinearLayout llyt_bottom;

	/*--------------------------------消息返回--------------------*/
	// 商品信息
	private ActionValue<GoodsTwo> value_goodsinfo;
	// 添加收藏
	private ActionValue<?> value_add;
	// 删除收藏
	private ActionValue<?> value_delete;
	// 添加商品到购物车
	private ActionValue<?> value_add2cart;
	
	//相关推荐-------------------------------------------------------------
	private ActionValue<GoodsOne> value_recommend;
	//相关推荐商品列表
	private ArrayList<GoodsOne> goods_recommend;
	
	// 数据处理Hanlder
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 商品信息数据请求成功
				value_goodsinfo = (ActionValue<GoodsTwo>) msg.obj;
				if (!value_goodsinfo.isSuccess()) {// 获取商品详情失败
					ToastUtil.ToastView(GoodsActivity.this,
							value_goodsinfo.getMessage());
				} else {
					// 获取商品详情
					goods_two = value_goodsinfo.getDatasource().get(0);
					setGoodsData(goods_two);
					llyt_network_error.setVisibility(View.INVISIBLE);
					llyt_bottom.setVisibility(View.VISIBLE);
					//请求获取相关推荐商品
					getRecommend(value_goodsinfo.getDatasource().get(0).getGoodsCategoryId());
				}
				loadingdialog.dismiss();
				break;
			case AppConfig.NET_ERROR_NOTNET:// 请求失败(网络)
				llyt_network_error.setVisibility(View.VISIBLE);
				loadingdialog.dismiss();
				llyt_bottom.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.ADD_COLLECTION:// 添加收藏
				loadingdialog.dismiss();
				value_add = (ActionValue<?>) msg.obj;
				if (value_add.isSuccess()) {// 收藏成功
					ToastUtil.ToastView(GoodsActivity.this,
							value_add.getMessage());
					btn_collect
							.setBackgroundResource(R.drawable.bg_btn_collect_p);
					MyApplication.getInstance().addCollection(goods_id);
				} else {// 收藏失败
					ToastUtil.ToastView(GoodsActivity.this,
							value_add.getMessage());
					if(value_add.getMessage().contains("您已经收藏过此商品")){
						btn_collect.setBackgroundResource(R.drawable.bg_btn_collect_p);
						MyApplication.getInstance().addCollection(goods_id);
					}
				}

				break;
			case AppConfig.DELETE_COLLECTION:// 删除收藏
				loadingdialog.dismiss();
				value_delete = (ActionValue<?>) msg.obj;

				if (value_delete.isSuccess()) {// 删除成功
					ToastUtil.ToastView(GoodsActivity.this,
							value_delete.getMessage());
					btn_collect
							.setBackgroundResource(R.drawable.bg_btn_collect_n);
					MyApplication.getInstance().deleteCollection(goods_id);
					setResult(AppConfig.RESULT_CODE_OK);
				} else {// 删除失败
					ToastUtil.ToastView(GoodsActivity.this,
							value_delete.getMessage());
				}

				break;
			case AppConfig.ADD2Cart:// 添加商品到购物车成功
				loadingdialog.dismiss();
				value_add2cart = (ActionValue<?>) msg.obj;

				if (value_add2cart.isSuccess()) {
					ToastUtil.ToastView(GoodsActivity.this,
							value_add2cart.getMessage());
					cart_goods_number += Integer.parseInt(et_pop_goods_number
							.getText().toString());
					MyApplication.getInstance().setCartGoodsNumber(
							cart_goods_number);
					badge.setText(cart_goods_number + "");
					if (add_tag == 1) {

					} else if (add_tag == 2) {// 立即购买(进入购物车)
						Intent intent_cart = new Intent(GoodsActivity.this,
								ShoppingCartActivity.class);
						startActivity(intent_cart);
						overridePendingTransition(
								R.anim.translate_horizontal_start_in,
								R.anim.translate_horizontal_start_out);
					}

				} else {
					ToastUtil.ToastView(GoodsActivity.this,
							value_delete.getMessage());
				}

				break;
			case AppConfig.GOODS_RECOMMEND_SUCCESS://相关推荐成功
				//请求成功显示推荐
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				llyt_goods_recommend.setLayoutParams(params);
				value_recommend = (ActionValue<GoodsOne>) msg.obj;
				goods_recommend = value_recommend.getDatasource();
				adapter_recommend = new GoodsRecommendHListViewAdapter(GoodsActivity.this, goods_recommend, options, imageLoader);
				lv_goods_recommend.setAdapter(adapter_recommend);
				break;
			case AppConfig.GOODS_RECOMMEND_FAILD://请求推荐失败
				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, 0);
				llyt_goods_recommend.setLayoutParams(params2);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods);
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

		// 获取屏幕宽高
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 获取屏幕宽度
		screenHeight = dm.heightPixels;
		screenWidth = dm.widthPixels;
		// 上级界面传递过来的商品id
		goods_id = getIntent().getExtras().getString("goods_id");
		

	}

	@Override
	protected void findViewById() {
		viewpager_goods_imgs = (ViewPager) findViewById(R.id.viewpager_goods_imgs);
		indicator_goods_imgs = (CirclePageIndicator) findViewById(R.id.indicator_goods_imgs);
		tv_goods_price = (TextView) findViewById(R.id.tv_goods_price);
		tv_goods_cost_price = (TextView) findViewById(R.id.tv_goods_cost_price);
		tv_goods_zhekou = (TextView) findViewById(R.id.tv_goods_zhekou);
		tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
		tv_goods_sales = (TextView) findViewById(R.id.tv_goods_sales);
		tv_goods_params = (TextView) findViewById(R.id.tv_goods_params);

		btn_back_goods = (Button) findViewById(R.id.btn_back_goods);
		btn_collect = (Button) findViewById(R.id.btn_collect);
		btn_goods_detail = (Button) findViewById(R.id.btn_goods_details);
		btn_goods_appraise = (Button) findViewById(R.id.btn_goods_appraise);
		btn_goods_params = (Button) findViewById(R.id.btn_goods_params);
		btn_goods_add2cart = (Button) findViewById(R.id.btn_goods_add2cart);
		btn_goods_buy = (Button) findViewById(R.id.btn_goods_buy_at_once);
		btn_call_kefu = (Button) findViewById(R.id.btn_call_kefu);
		btn_goods_shopping_cart = (Button) findViewById(R.id.btn_goods_shopping_cart);
		badge = new BadgeView(this, btn_goods_shopping_cart);
		badge.setLayoutDirection(1);
		badge.setTextSize(10);

		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_bottom = (LinearLayout) findViewById(R.id.llyt_bottom);

		llyt_goods_recommend = (LinearLayout) findViewById(R.id.llyt_goods_recommend);
		lv_goods_recommend = (HorizontalListView) findViewById(R.id.lv_goods_recommend);

		loadingdialog = new LoadingDialog(GoodsActivity.this);
	}

	@Override
	protected void initUI() {
		// 设置Viewpager正方形
		RelativeLayout.LayoutParams params_viewpager = new RelativeLayout.LayoutParams(
				screenWidth, screenWidth);
		viewpager_goods_imgs.setLayoutParams(params_viewpager);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
		tv_goods_params.setLayoutParams(params);

		//设置相关推荐默认不显示
		llyt_goods_recommend.setLayoutParams(params);
		// 请求数据
		getGoodsInfo();
	}

	@Override
	protected void setListener() {
		btn_back_goods.setOnClickListener(this);
		btn_collect.setOnClickListener(this);
		btn_goods_detail.setOnClickListener(this);
		btn_goods_appraise.setOnClickListener(this);
		btn_goods_params.setOnClickListener(this);
		btn_goods_add2cart.setOnClickListener(this);
		btn_goods_buy.setOnClickListener(this);
		btn_call_kefu.setOnClickListener(this);
		btn_goods_shopping_cart.setOnClickListener(this);

		// 打开推荐商品
		lv_goods_recommend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_goods_recommend.getItemAtPosition(position);
				// 将商品数据传递给
				if (obj instanceof GoodsOne) {
					GoodsOne goods_one = (GoodsOne) obj;
					Intent intent = new Intent(GoodsActivity.this,
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

	/**
	 * @描述：收藏商品(或者是删除收藏) 2014-8-12
	 */
	private void setCollected() {
		user = MyApplication.getInstance().getUserInfo();
		if (!MyApplication.getInstance().isCollected(goods_id)) {
			HttpUrlProvider.getIntance().getAddCollection(this,
					new TaskAddCollectionBack(handler),
					URLConfig.ADD_COLLECTION, goods_id, user.getUserId());
		} else {
			HttpUrlProvider.getIntance().getDeleteCollection(this,
					new TaskDeleteCollectionBack(handler),
					URLConfig.DELETE_COLLECTION, goods_id, user.getUserId());
		}
	}

	/**
	 * 
	 * @描述：打开界面的时候请求商品数据
	 * @date：2014-6-26
	 * @param goods_id
	 */
	private void setGoodsData(GoodsTwo goods_two) {
		// 首次加在数据显示dialog
		loadingdialog.show(GoodsActivity.this);
		// 是否已经收藏
		if (MyApplication.getInstance().isCollected(goods_id)) {
			btn_collect.setBackgroundResource(R.drawable.bg_btn_collect_p);
		} else {
			btn_collect.setBackgroundResource(R.drawable.bg_btn_collect_n);
		}

		tv_goods_price.setText("￥" + goods_two.getGoods_price() + "");
		String cost_price = "￥"
				+ goods_two.getMarketPrice();
		tv_goods_zhekou.setText(((Float.parseFloat(goods_two.getGoods_price())/goods_two.getMarketPrice())*10+"").substring(0, 3)+"折");
		if(Float.parseFloat(goods_two.getGoods_price())==0){
			tv_goods_zhekou.setText("活动");
		}
		SpannableString sp = new SpannableString(cost_price);
		sp.setSpan(new StrikethroughSpan(), 0, cost_price.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_goods_cost_price.setText(sp);
		tv_goods_name.setText(goods_two.getName());
		tv_goods_sales.setText("销量 " + goods_two.getSalesVolume() + "件");

		String goods_params = "";
		if (goods_two.getGoodsParams().size() > 0) {
			for (int i = 0; i < goods_two.getGoodsParams().size(); i++) {
				String param = goods_two.getGoodsParams().get(i).toString();

				if (i == goods_two.getGoodsParams().size() - 1) {
					if (!param.equals("")) {
						goods_params += param;
					}

				} else {
					if (!param.equals("")) {
						goods_params += param + "\n";
					}
				}

			}
		}
		tv_goods_params.setText(goods_params);

		// 图片viewpager
		goods_img_urls = goods_two.getImagePath();
		
		//是否移除特殊图片
		for(int i=0;i<goods_img_urls.size();i++){
			if(goods_img_urls.get(i).isGdFlag()){
				goods_img_urls.remove(i);
			}
		}
		
		if (goods_img_urls != null && goods_img_urls.size() > 0) {
			for (int i = 0; i < goods_img_urls.size(); i++) {
				ImageView iv_ad = new ImageView(GoodsActivity.this);
				img_ivs.add(iv_ad);
			}
			adapter_img = new GoodsImgsViewPagerAdapter(goods_img_urls,
					img_ivs, GoodsActivity.this, options, imageLoader);
			viewpager_goods_imgs.setAdapter(adapter_img);
			indicator_goods_imgs.setViewPager(viewpager_goods_imgs);
		}
		// 商品推荐
		// lv_goods_recommend.setAdapter(adapter_recommend);

		// 初始化规格选择Pop
		selectGoodsSize();

	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back_goods:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;
		case R.id.btn_collect:// 收藏(如果用户已经登录则直接收藏,没有则先登录)
			if (!MyApplication.getInstance().isUserLogin()) {
				Intent intent_login = new Intent(GoodsActivity.this,
						UserLoginActivity.class);
				startActivity(intent_login);
				overridePendingTransition(R.anim.translate_vertical_start_in,
						R.anim.translate_vertical_start_out);
			} else {
				setCollected();
			}

			break;
		case R.id.btn_goods_details:// 查看图文详情
			Intent intent = new Intent(GoodsActivity.this,
					GoodsImgsActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("introduction", goods_two.getId());
			intent.putExtras(bundle);
			startActivity(intent);
			overridePendingTransition(R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);

			break;
		case R.id.btn_goods_appraise:// 查看商品评价
			Intent intent_discuss = new Intent(GoodsActivity.this,
					GoodsDiscussActivity.class);
			Bundle bundle_discuss = new Bundle();
			bundle_discuss.putString("goods_id", goods_id);
			intent_discuss.putExtras(bundle_discuss);
			startActivity(intent_discuss);
			break;
		case R.id.btn_goods_params:// 查看商品参数
			if (tv_goods_params.getVisibility() == View.VISIBLE) {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						0, 0);
				tv_goods_params.setLayoutParams(params);
				tv_goods_params.setVisibility(View.INVISIBLE);
			} else if (tv_goods_params.getVisibility() == View.INVISIBLE) {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				tv_goods_params.setLayoutParams(params);
				tv_goods_params.setVisibility(View.VISIBLE);

			}
			break;
		case R.id.btn_goods_add2cart:// 加入购物车（如果用户没有选择商品规格，则跳出选择商品规格页面，确定之后加入购物车）
			showPop(v);
			add_tag = 1;
			break;
		case R.id.btn_goods_buy_at_once:// 立即购买(如果用户没有选择商品规格，则跳出规格选择页面，如果有，则直接跳到订单页)
			showPop(v);
			add_tag = 2;
			break;
		case R.id.btn_goods_shopping_cart:// 进入购物车
			if (MyApplication.getInstance().isUserLogin()) {
				Intent intent_cart = new Intent(GoodsActivity.this,
						ShoppingCartActivity.class);
				startActivity(intent_cart);
				overridePendingTransition(R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
			} else {
				Intent intent_login = new Intent(GoodsActivity.this,
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
				ToastUtil.ToastView(GoodsActivity.this, "请选择商品规格");
			} else if (add_tag == 1) {
				add2Cart();
				dismissPop();
			} else if (add_tag == 2) {
				add2Cart();
				dismissPop();
			}
			break;
		case R.id.btn_call_kefu:// 拨打客服电话
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
			popview = View.inflate(GoodsActivity.this, R.layout.pop_goods_size,
					null);
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
			Intent intent_login = new Intent(GoodsActivity.this,
					UserLoginActivity.class);
			startActivity(intent_login);
			overridePendingTransition(R.anim.translate_vertical_start_in,
					R.anim.translate_vertical_start_out);
		}
	}

	/**
	 * @描述：请求商品详情 2014-8-15
	 */
	private void getGoodsInfo() {
		loadingdialog.show(GoodsActivity.this);
		HttpUrlProvider.getIntance().getGoodsInfo(this,
				new TaskGoodsInfoBack(handler), URLConfig.GOODS_INFO, goods_id);
	}
	/**
	 * @描述：获取相关商品推荐
	 * 2014-9-17
	 */
	private void getRecommend(String categoryId){
		HttpUrlProvider.getIntance().getGoodsRecommend(this, new TaskGoodsRecommendBack(handler), categoryId);
	}

	/**
	 * @名称：GoodsActivity.java
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
	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss();
		loadingdialog = null;
		imageLoader.stop();
	}
}
