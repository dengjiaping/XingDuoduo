package com.xiuman.xingduoduo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.ShoppingCartListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskDeleteCartBack;
import com.xiuman.xingduoduo.callback.TaskShoppingCartBack;
import com.xiuman.xingduoduo.callback.TaskUpdateCartGoodsNumberBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsCart;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.activity.GoodsActivity;
import com.xiuman.xingduoduo.ui.activity.MainActivity;
import com.xiuman.xingduoduo.ui.activity.OrderSubmitActivity;
import com.xiuman.xingduoduo.ui.activity.SearchActivity;
import com.xiuman.xingduoduo.ui.activity.UserLoginActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshListView;

/**
 * 
 * @名称：FragmentShoppingCart.java
 * @描述：购物车界面
 * @author danding
 * @version 2014-6-3
 */
@SuppressLint("HandlerLeak")
public class FragmentShoppingCart extends BaseFragment implements
		OnClickListener {

	/*-----------------------------------组件--------------------------------*/
	// 二维码
	private Button btn_sweep;
	// 搜索
	private Button btn_search;
	// ListView
	private ListView mListView;
	// 下拉刷新ListView
	private PullToRefreshListView mPullListView;
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 数据加载Dialog
	private LoadingDialog loadingdialog;
	// 购物车为空显示的布局--------------------------
	private LinearLayout llyt_null_cart;
	// 去商城挑选
	private Button btn_go2center;
	// 全选LinearLayout
	private LinearLayout llyt_all;
	// 选择数量
	private TextView tv_cart_numer;

	// ---------------- 结算-------------------------
	// 结算布局
	private LinearLayout llyt_balance;
	// 全选
	private CheckBox cb_balance;
	// 购物车商品总价
	private TextView tv_prices;
	// 结算按钮
	private Button btn_balance;
	/*-----------------------------------数据------------------------------------------*/
	// 当前登录的用户
	private User user;
	// 购物车商品列表
	// private ArrayList<GoodsCart> cart_goods = new ArrayList<GoodsCart>();

	/*-----------------------------------Adapter-------------------------------------*/
	private ShoppingCartListViewAdapter adapter;

	/*------------------------------------请求数据----------------------------------------*/
	// 购物车列表
	private ActionValue<GoodsCart> value_goods;
	// 购物车商品列表get
	private ArrayList<GoodsCart> cart_goods_get = new ArrayList<GoodsCart>();
	// 当前显示
	private ArrayList<GoodsCart> cart_goods_current = new ArrayList<GoodsCart>();
	// 当前页
	private int currentPage = 1;
	// 设置购物车所有商品被选中
	private List<Boolean> list_checked_current = new ArrayList<Boolean>();
	// 移除购物车时返回的结果
	private ActionValue<?> value_delete;
	// 传递而来的要移除的商品
	private GoodsCart goods;

	// 修改购物车数量----------------------------------
	private ActionValue<?> value_update;
	// 请求修改的桑普
	private GoodsCart goods_update;
	// 要修改的数量
	private int goods_number = 0;
	// 要修改的商品的位置
	private int goods_position = 0;
	// 修改前的总价
	private String goods_total = "";

	// 用于结算的商品列表
	private ArrayList<GoodsCart> cart_goods_balance = new ArrayList<GoodsCart>();

	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;
	// 是否进入购物车刷新
	private boolean isReFresh = true;// 默认刷新(如果用户没有登录则不刷新)

	// 标记--------------------
	// 是否显示空购物车
	private boolean isShowNull = false;
	// 标记是否全选
	private boolean isCheckedAll = true;

	// Handler
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功
				value_goods = (ActionValue<GoodsCart>) msg.obj;
				if (!value_goods.isSuccess() && currentPage == 1) {// 列表为空
					llyt_null_cart.setVisibility(View.VISIBLE);
					tv_prices.setText(0 + "");
					btn_balance.setText("结算(" + 0 + ")");
					// 下拉加载完成
					mPullListView.onPullDownRefreshComplete();
					btn_balance.setClickable(false);
					btn_balance.setBackgroundColor(R.color.color_black3);
				} else {
					cart_goods_get = (ArrayList<GoodsCart>) value_goods
							.getDatasource();
					if (isUp) {// 下拉
						cart_goods_current = cart_goods_get;
						list_checked_current = setGetChecked(cart_goods_get);
						adapter = new ShoppingCartListViewAdapter(
								getActivity(), cart_goods_current, imageLoader,
								options, handler, list_checked_current);
						// 下拉加载完成
						mPullListView.onPullDownRefreshComplete();

						// 设置默认选中所有
						cb_balance.setChecked(true);
					} else {// 上拉
						list_checked_current
								.addAll(setGetChecked(cart_goods_get));
						cart_goods_current.addAll(cart_goods_get);
						adapter.notifyDataSetChanged();

						// 上拉刷新完成
						mPullListView.onPullUpRefreshComplete();
					}
					TimeUtil.setLastUpdateTime(mPullListView);
					mListView.setAdapter(adapter);
					llyt_null_cart.setVisibility(View.INVISIBLE);
					llyt_balance.setVisibility(View.VISIBLE);
					setBalanceInfo();
				}
				llyt_network_error.setVisibility(View.INVISIBLE);
				llyt_all.setVisibility(View.VISIBLE);
				loadingdialog.dismiss();
				break;
			case AppConfig.NET_ERROR_NOTNET:// 网络连接失败
				llyt_network_error.setVisibility(View.VISIBLE);
				llyt_null_cart.setVisibility(View.INVISIBLE);
				llyt_balance.setVisibility(View.INVISIBLE);
				llyt_all.setVisibility(View.INVISIBLE);
				loadingdialog.dismiss();
				break;
			case AppConfig.UPDATE_SHOPPING_CART:// 请求更新购物车数据(更新总价，数量)
				Bundle bundle = msg.getData();
				goods_update = (GoodsCart) bundle
						.getSerializable("goods_update");
				goods_number = bundle.getInt("goods_number");
				goods_position = bundle.getInt("goods_position");
				goods_total = bundle.getString("goods_total");
				HttpUrlProvider.getIntance()
						.getUpdateShoppingCart(getActivity(),
								new TaskUpdateCartGoodsNumberBack(handler),
								URLConfig.UPDATE_GOODS_NUMBER,
								goods_update.getCartItemId(),
								goods_update.getQuanity());
				loadingdialog.show();
				break;
			case AppConfig.UPDATE_SHOPPING_CART_GOODS_NUMBER:// 请求更新接口
				value_update = (ActionValue<?>) msg.obj;
				if (value_update.isSuccess()) {// 修改成功
				} else {// 修改失败则改回原值
					ToastUtil.ToastView(getActivity(),
							value_update.getMessage());
					cart_goods_current.get(goods_position).setQuanity(
							goods_number);
					cart_goods_current.get(goods_position).setTotalPrice(
							goods_total);
					adapter.notifyDataSetChanged();
				}
				setBalanceInfo();
				loadingdialog.dismiss();
				break;
			case AppConfig.UPDATE_SHOPPING_CART_SELECT:// 更新已选择的商品（结算），cb_balance的状态
				for (int i = 0; i < adapter.getMap().size(); i++) {
					isCheckedAll = adapter.getMap().get(i);
					if (!isCheckedAll) {
						if (cb_balance.isChecked() == true) {
							cb_balance.setChecked(isCheckedAll);
						}
						setBalanceInfo();
						return;
					}
				}
				cb_balance.setChecked(isCheckedAll);
				break;
			case AppConfig.UPDATE_DELETE_SHOPPING_CART_GOODS:// 请求移除购物车返回结果
				value_delete = (ActionValue<?>) msg.obj;
				if (!value_delete.isSuccess()) {// 移除失败
					ToastUtil.ToastView(getActivity(),
							value_delete.getMessage());
				} else {// 移除成功
					ToastUtil.ToastView(getActivity(),
							value_delete.getMessage());
					cart_goods_current.remove(goods);
					adapter.notifyDataSetChanged();
					setBalanceInfo();
					isShowNull();
				}
				loadingdialog.dismiss();
				break;
			case AppConfig.UPDATE_SHOPPING_CART_GOODS:// 更新商品列表,请求移除商品接口
				goods = (GoodsCart) msg.obj;
				HttpUrlProvider.getIntance().getDeleteCartGoods(getActivity(),
						new TaskDeleteCartBack(handler),
						URLConfig.DELETE_GOODS_CART, goods.getCartItemId());
				loadingdialog.show();
				break;
			}
		}
	};

	private MainActivity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shopping_cart,
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
		activity = (MainActivity) getActivity();
		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.weiboitem_pic_loading) //
		// 在ImageView加载过程中显示图片
				.showImageForEmptyUri(R.drawable.onloading_goods_poster) // image连接地址为空时
				.showImageOnFail(R.drawable.onloading_goods_poster) // image加载失败
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();

	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById(View view) {
		btn_go2center = (Button) view.findViewById(R.id.btn_go2center);
		btn_sweep = (Button) view.findViewById(R.id.btn_sweep_1);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		llyt_null_cart = (LinearLayout) view.findViewById(R.id.llyt_cart_null);
		cb_balance = (CheckBox) view.findViewById(R.id.cb_balance);
		tv_prices = (TextView) view.findViewById(R.id.tv_prices);
		btn_balance = (Button) view.findViewById(R.id.btn_balance);
		
		llyt_all = (LinearLayout) view.findViewById(R.id.llyt_all);
		tv_cart_numer = (TextView) view.findViewById(R.id.tv_cart_numer);
		llyt_network_error = (LinearLayout) view
				.findViewById(R.id.llyt_network_error);
		llyt_balance = (LinearLayout) view.findViewById(R.id.llyt_balance);
		loadingdialog = new LoadingDialog(getActivity());
		// 下拉刷新
		mPullListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_shopping_cart_goods);
		// 设置上啦加载是否可用
		mPullListView.setPullLoadEnabled(false);
		// 是否滚到底部自动加载数据
		mPullListView.setScrollLoadEnabled(false);
		// 获取可刷新的控件
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(
				R.drawable.custom_line_4));

	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		user = MyApplication.getInstance().getUserInfo();
		if (user == null) {
			ToastUtil.ToastView(getActivity(), "您还没有登录,暂时不能将您喜爱的宝贝加入购物车哦！");
		}
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		btn_sweep.setOnClickListener(this);
		btn_go2center.setOnClickListener(this);
		btn_balance.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		llyt_network_error.setOnClickListener(this);
		// 购物车商品选择，结算总价等控制。
		cb_balance.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					if (adapter.getMap().size() > 0) {
						for (int i = 0; i < adapter.getMap().size(); i++) {
							adapter.getMap().set(i, isChecked);
						}
					}
					adapter.notifyDataSetChanged();
					isCheckedAll = true;
				} else {
					if (isCheckedAll) {
						for (int i = 0; i < adapter.getMap().size(); i++) {
							adapter.getMap().set(i, isChecked);
						}
						adapter.notifyDataSetChanged();
						isCheckedAll = false;
					} else {
						boolean flag = false;
						for (int i = 0; i < adapter.getMap().size(); i++) {
							if (adapter.getMap().get(i) == false) {
								flag = false;
								break;
							}
							flag = true;
						}
						if (flag) {
							for (int i = 0; i < adapter.getMap().size(); i++) {
								adapter.getMap().set(i, isChecked);
							}
						}
						adapter.notifyDataSetChanged();
					}
				}

				// 更新需要结算的数据信息
				setBalanceInfo();
			}
		});

		// 下拉刷新
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				isUp = true;
				currentPage = 1;
				initShopingCart(currentPage);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				isUp = false;
				if (value_goods.getPage() < value_goods.getTotalpage()) {
					currentPage += 1;
					initShopingCart(currentPage);
				} else {
					ToastUtil.ToastView(getActivity(), getResources()
							.getString(R.string.no_more));
					// 下拉加载完成
					mPullListView.onPullDownRefreshComplete();
					// 上拉刷新完成
					mPullListView.onPullUpRefreshComplete();
					// 设置是否有更多的数据
					mPullListView.setHasMoreData(false);
				}
			}
		});

		// 查看商品详情
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = mListView.getItemAtPosition(position);
				if (obj instanceof GoodsCart) {
					GoodsCart goodscart = (GoodsCart) obj;
					String goods_id = goodscart.getGoodId();
					Intent intent = new Intent(getActivity(),
							GoodsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("goods_id", goods_id);
					intent.putExtras(bundle);
					// 根据返回码确定是否刷新购物车
					startActivity(intent);
					getActivity().overridePendingTransition(
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
		case R.id.btn_search:// 搜索按钮
			Intent intent_search = new Intent(getActivity(),
					SearchActivity.class);
			getActivity().startActivity(intent_search);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.btn_go2center:// 去商城挑选
			activity.selectTab(0);
			break;
		case R.id.btn_balance:// 结算
			if (user == null) {
				Intent intent = new Intent(getActivity(),
						UserLoginActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(
						R.anim.translate_vertical_start_in,
						R.anim.translate_vertical_start_out);
			} else {
				Intent intent_order = new Intent(getActivity(),
						OrderSubmitActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("balance_goods", cart_goods_balance);
				intent_order.putExtras(bundle);
				startActivity(intent_order);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
			}
			break;
		case R.id.llyt_network_error:// 重新加载
			currentPage = 1;
			isUp = true;
			initShopingCart(currentPage);
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		user = MyApplication.getInstance().getUserInfo();
		// 每次加载获取保存本地购物车数量与当前购物车数量进行比较，相同则不刷新，不同则刷新
		int basic_number = MyApplication.getInstance().getCartGoodsNumber();

		if (MyApplication.getInstance().isUserLogin()) {
			if (cart_goods_current.size() == 0) {
				isReFresh = true;
			} else {
				// 保存购物车数量到本地
				int total_number = 0;
				for (int i = 0; i < cart_goods_current.size(); i++) {
					total_number += cart_goods_current.get(i).getQuanity();
				}
				if (basic_number != total_number) {
					isReFresh = true;
				} else {
					isReFresh = false;
				}
			}
		} else {
			if (user != null) {
				if (cart_goods_current.size() == 0) {
					isReFresh = true;
				} else {
					int total_number = 0;
					for (int i = 0; i < cart_goods_current.size(); i++) {
						total_number += cart_goods_current.get(i).getQuanity();
					}
					if (basic_number != total_number) {
						isReFresh = true;
					} else {
						isReFresh = false;
					}
				}
			} else {
				isReFresh = false;
				llyt_null_cart.setVisibility(View.VISIBLE);
			}
		}
		if (isReFresh) {
			mPullListView.doPullRefreshing(true, 500);
		}
	}

	/**
	 * @描述：加载购物车数据
	 * @param currentPage
	 *            2014-8-18
	 */
	private void initShopingCart(int currentPage) {
		HttpUrlProvider.getIntance().getShoppingCart(getActivity(),
				new TaskShoppingCartBack(handler), URLConfig.GOODS_CART,
				currentPage, user.getUserId());
		loadingdialog.show();
	}

	/**
	 * 
	 * @描述：设置要加入结算的数据信息(总价，数量)
	 * @date：2014-7-23
	 */
	private void setBalanceInfo() {
		// 获取选中的商品的总价
		double total = 0;
		int number = 0;
		List<GoodsCart> balance_goods = adapter.getBalanceGoods();
		cart_goods_balance = adapter.getBalanceGoods();
		for (int i = 0; i < balance_goods.size(); i++) {
			total += Double.valueOf(balance_goods.get(i).getTotalPrice());
			number += balance_goods.get(i).getQuanity();
		}
		tv_prices.setText(total + "");
		btn_balance.setText("结算(" + number + ")");
		tv_cart_numer.setText("共" + number + "件");
		if (number > 0) {
			btn_balance.setClickable(true);
			btn_balance.setBackgroundResource(R.drawable.btn_color_selector);
		} else {
			btn_balance.setClickable(false);
			btn_balance.setBackgroundColor(R.color.color_black3);
		}

		// 保存购物车数量到本地
		int total_number = 0;
		for (int i = 0; i < cart_goods_current.size(); i++) {
			total_number += cart_goods_current.get(i).getQuanity();
		}
		MyApplication.getInstance().setCartGoodsNumber(total_number);

		// 根据选中的商品数量来改变结算栏的状态
	}

	/**
	 * @描述：控制是否显示购物车为空时的界面
	 * @date：2014-7-22
	 */
	private void isShowNull() {
		if (cart_goods_current.size() > 0) {
			isShowNull = false;
		} else {
			isShowNull = true;
		}
		if (isShowNull) {
			llyt_null_cart.setVisibility(View.VISIBLE);
		} else {
			llyt_null_cart.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * @描述：设置上拉加载时全部被选中
	 * @param goods_cart
	 *            2014-8-18
	 * @return
	 */
	private List<Boolean> setGetChecked(ArrayList<GoodsCart> goods_cart) {
		List<Boolean> list_checked_get = new ArrayList<Boolean>();
		for (int i = 0; i < goods_cart.size(); i++) {
			list_checked_get.add(true);
		}
		return list_checked_get;
	}
}
