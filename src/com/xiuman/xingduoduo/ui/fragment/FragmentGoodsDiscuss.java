package com.xiuman.xingduoduo.ui.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.GoodsDiscussListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskGoodsDiscussListBack;
import com.xiuman.xingduoduo.model.ActionValueDiscuss;
import com.xiuman.xingduoduo.model.DiscussTotal;
import com.xiuman.xingduoduo.model.GoodsDiscuss;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.activity.GoodsInfoDiscussActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshListView;

/**
 * @名称：FragmentGoodsDiscuss.java
 * @描述：商品评价
 * @author danding
 * @时间 2014-10-13
 */
public class FragmentGoodsDiscuss extends BaseFragment implements
		OnClickListener {

	/*-------------------------------组件---------------------------------*/
	// 下拉刷新ListView
	private PullToRefreshListView pulllistview_discuss;
	// ListView
	private ListView lv_discuss;
	// 网络未连接显示的布局
	private LinearLayout llyt_network_error;
	// 评论为空的时候显示的布局
	private LinearLayout llyt_goods_null;
	// 请求数据时显示的diaolog
	private LoadingDialog loadingdialog;
	// 父
	private GoodsInfoDiscussActivity activity;
	// 商品的综合评分
	private TextView tv_goods_total_rating;
	private RatingBar ratingbar_total;
	// 质量评分
	private RatingBar ratingbar_zhiliang;
	private TextView tv_total_zhiliang;
	// 服务评分
	private RatingBar ratingbar_taidu;
	private TextView tv_total_taidu;
	// 发货速度评分
	private RatingBar ratingbar_sudu;
	private TextView tv_total_sudu;

	/*----------------------------------标记-------------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	/*----------------------------------数据-------------------------------------*/
	// 从上级界面接收到的商品id
	private String goods_id;

	/*----------------------------------Adapter-----------------------------------*/
	// 评论Adapter
	private GoodsDiscussListViewAdapter adapter;

	/*---------------------------------请求数据-----------------------------------*/
	// 请求页码
	private int currentPage = 1;
	// 请求获取的评价列表
	private ArrayList<GoodsDiscuss> discusses_get = new ArrayList<GoodsDiscuss>();
	// 当前显示的评价列表
	private ArrayList<GoodsDiscuss> discusses_current = new ArrayList<GoodsDiscuss>();
	// 请求结果
	private ActionValueDiscuss<GoodsDiscuss> value_discuss = new ActionValueDiscuss<GoodsDiscuss>();
	// 综合评分-------
	private DiscussTotal discussTotal = new DiscussTotal();

	/*-----------------------ImageLoader-----------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();

	// 数据处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功，设置数据

				value_discuss = (ActionValueDiscuss<GoodsDiscuss>) msg.obj;
				discusses_get = (ArrayList<GoodsDiscuss>) value_discuss
						.getDatasource();
				if (!value_discuss.isSuccess()) {// 数据为空
					llyt_goods_null.setVisibility(View.VISIBLE);
					// 下拉加载完成
					pulllistview_discuss.onPullDownRefreshComplete();
					// 上拉刷新完成
					pulllistview_discuss.onPullUpRefreshComplete();
				} else {
					if (isUp) {// 下拉
						discusses_current = discusses_get;
						// 获取综合评分
						discussTotal = value_discuss.getTotaldatasource()
								.get(0);
						setTotal(discussTotal);

						adapter = new GoodsDiscussListViewAdapter(
								getActivity(), discusses_current, imageLoader);
						// 下拉加载完成
						pulllistview_discuss.onPullDownRefreshComplete();
						lv_discuss.setAdapter(adapter);
					} else {// 上拉
						discusses_current.addAll(discusses_get);
						adapter.notifyDataSetChanged();

						// 上拉刷新完成
						pulllistview_discuss.onPullUpRefreshComplete();
						// 设置是否有更多的数据
						if (currentPage < value_discuss.getTotalpage()) {
							pulllistview_discuss.setHasMoreData(true);
						} else {
							pulllistview_discuss.setHasMoreData(false);
						}
					}
					TimeUtil.setLastUpdateTime(pulllistview_discuss);

					llyt_goods_null.setVisibility(View.INVISIBLE);
				}

				loadingdialog.dismiss(getActivity());
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 请求数据失败(网络)

				loadingdialog.dismiss(getActivity());
				llyt_network_error.setVisibility(View.VISIBLE);
				break;
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_discuss,
				container, false);
		initData();
		findViewById(view);
		initUI();
		setListener();
		return view;
	}

	@Override
	protected void initData() {
		activity = (GoodsInfoDiscussActivity) getActivity();
		goods_id = activity.getGoods_id();
	}

	@Override
	protected void findViewById(View view) {
		loadingdialog = new LoadingDialog(activity);
		llyt_network_error = (LinearLayout) view
				.findViewById(R.id.llyt_network_error);
		llyt_goods_null = (LinearLayout) view
				.findViewById(R.id.llyt_goods_null);

		pulllistview_discuss = (PullToRefreshListView) view
				.findViewById(R.id.pulllistview_discuss);

		lv_discuss = pulllistview_discuss.getRefreshableView();
		pulllistview_discuss.setPullRefreshEnabled(false);
		pulllistview_discuss.setPullLoadEnabled(true);
		pulllistview_discuss.setScrollLoadEnabled(true);

		lv_discuss.setCacheColorHint(Color.TRANSPARENT);
		lv_discuss
				.setDivider(getResources().getDrawable(R.drawable.line_small));
		lv_discuss.setSelector(new ColorDrawable(Color.TRANSPARENT));

		View headview = View.inflate(getActivity(), R.layout.include_discuss,
				null);
		tv_goods_total_rating = (TextView) headview
				.findViewById(R.id.tv_goods_total_rating);
		ratingbar_total = (RatingBar) headview
				.findViewById(R.id.ratingbar_total);
		ratingbar_zhiliang = (RatingBar) headview
				.findViewById(R.id.ratingbar_zhiliang);
		tv_total_zhiliang = (TextView) headview
				.findViewById(R.id.tv_total_zhiliang);
		ratingbar_taidu = (RatingBar) headview
				.findViewById(R.id.ratingbar_taidu);
		tv_total_taidu = (TextView) headview.findViewById(R.id.tv_total_taidu);
		ratingbar_sudu = (RatingBar) headview.findViewById(R.id.ratingbar_sudu);
		tv_total_sudu = (TextView) headview.findViewById(R.id.tv_total_sudu);

		lv_discuss.addHeaderView(headview);
	}

	@Override
	protected void initUI() {
		initFirstData(currentPage);
	}

	@Override
	protected void setListener() {
		llyt_network_error.setOnClickListener(this);
		// 设置监听
		pulllistview_discuss
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						isUp = true;
						currentPage = 1;
						initFirstData(currentPage);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						isUp = false;
						if (value_discuss.getPage() < value_discuss
								.getTotalpage()) {
							currentPage += 1;
							initFirstData(currentPage);
						} else {
							ToastUtil.ToastView(getActivity(), getResources()
									.getString(R.string.no_more));
							// 下拉加载完成
							pulllistview_discuss.onPullDownRefreshComplete();
							// 上拉刷新完成
							pulllistview_discuss.onPullUpRefreshComplete();
							// 设置是否有更多的数据
							pulllistview_discuss.setHasMoreData(false);
						}
					}
				});
	}

	/**
	 * @描述：设置综合评分
	 * @param discussTotal
	 * @时间 2014-10-21
	 */
	private void setTotal(DiscussTotal discussTotal) {
		tv_goods_total_rating.setText(discussTotal.getComprScore() + "");
		tv_total_zhiliang.setText(discussTotal.getGoodsQualityTotal() + "");
		tv_total_taidu.setText(discussTotal.getServiceAttitudeTotal() + "");
		tv_total_sudu.setText(discussTotal.getDeliverySpeedTotal() + "");
		ratingbar_total.setRating(discussTotal.getComprScore());
		ratingbar_zhiliang.setRating(discussTotal.getGoodsQualityTotal());
		ratingbar_sudu.setRating(discussTotal.getDeliverySpeedTotal());
		ratingbar_taidu.setRating(discussTotal.getServiceAttitudeTotal());
	}

	@Override
	public void onResume() {
		super.onResume();
		if (loadingdialog == null) {
			loadingdialog = new LoadingDialog(getActivity());
		}
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData(int currentPage) {
		loadingdialog.show(getActivity());
		// 请求获取商品评价
		HttpUrlProvider.getIntance().getGoodsDiscuss(getActivity(),
				new TaskGoodsDiscussListBack(handler),
				URLConfig.GOODS_DISCUSS_LIST, currentPage, goods_id);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.llyt_network_error:// 重新加载
			currentPage = 1;
			initFirstData(currentPage);
			break;
		}
	}
}
