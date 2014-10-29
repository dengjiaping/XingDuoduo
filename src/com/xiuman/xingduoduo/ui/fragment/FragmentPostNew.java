package com.xiuman.xingduoduo.ui.fragment;

import java.util.ArrayList;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.PlatePostListViewAdapter;
import com.xiuman.xingduoduo.adapter.PlateStickPostListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskPostListBack;
import com.xiuman.xingduoduo.callback.TaskTopPostBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.activity.PostInfoActivity;
import com.xiuman.xingduoduo.ui.activity.PostListActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.util.SizeUtil;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.util.options.CustomOptions;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.floatbutton.FloatingActionButton;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshListView;

/**
 * @名称：FragmentPostNew.java
 * @描述：最新帖子
 * @author danding
 * @时间 2014-10-23
 */
public class FragmentPostNew extends BaseFragment implements OnClickListener {
	/*-------------------------------------组件----------------------------------*/
	// 上下文
	private PostListActivity activity;
	// 滚动到顶部
	private FloatingActionButton button_floating_action;
	// 下拉刷新ScrollView
	private PullToRefreshListView pullsv_post;
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
	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	private int currentPage = 1;
	// 标记是否还有帖子
	private boolean flag = true;
	/*--------------------------------------数据--------------------------------*/
	// 从上级界面接收到的板块信息
	private BBSPlate plate;
	/*--------------------------------------Adapter-----------------------------*/
	// adapter(帖子列表)
	private PlatePostListViewAdapter adapter;
	// 置顶帖子
	private PlateStickPostListViewAdapter adapter_stick;
	// 返回结果(普通帖子)
	private ActionValue<BBSPost> value_normal = new ActionValue<BBSPost>();
	// 普通帖子列表
	private ArrayList<BBSPost> bbspost = new ArrayList<BBSPost>();
	// 普通帖子列表
	private ArrayList<BBSPost> bbspost_get = new ArrayList<BBSPost>();
	// 返回结果置顶帖子
	private ActionValue<BBSPost> value_top = new ActionValue<BBSPost>();
	// 置顶帖子列表
	private ArrayList<BBSPost> bbspostTop;

	// 消息处理Handler-------------------------------------
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 获取数据成功
				loadingdialog.dismiss(getActivity());
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;

			case AppConfig.BBS_POST_BACK:// 获取帖子列表
				value_normal = (ActionValue<BBSPost>) msg.obj;
				bbspost_get = value_normal.getDatasource();
				if (value_normal.isSuccess()) {
					if (isUp) {
						bbspost = bbspost_get;
						adapter = new PlatePostListViewAdapter(getActivity(),
								 imageLoader, bbspost);
						lv_posts.setAdapter(adapter);
						// 下拉加载完成
						pullsv_post.onPullDownRefreshComplete();
						// 设置是否有更多的数据
						TimeUtil.setLastUpdateTime(pullsv_post);
					} else {
						bbspost.addAll(bbspost_get);
						adapter.notifyDataSetChanged();
						pullsv_post.onPullUpRefreshComplete();
					}
					flag = true;
				} else {
					ToastUtil.ToastView(getActivity(), "没有更多帖子！");
					// 上拉刷新完成
					flag = false;
					pullsv_post.onPullUpRefreshComplete();
					pullsv_post.setHasMoreData(false);
				}
				loadingdialog.dismiss(getActivity());
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.BBS_TOP_POST_BACK:// 获取置顶帖子
				value_top = (ActionValue<BBSPost>) msg.obj;
				if (value_top.isSuccess()) {
					bbspostTop = value_top.getDatasource();
					adapter_stick = new PlateStickPostListViewAdapter(
							getActivity(), bbspostTop);

					lv_stick_posts.setAdapter(adapter_stick);
				}
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss(getActivity());
				llyt_network_error.setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_post_new, container,
				false);
		initData();
		findViewById(view);
		initUI();
		setListener();
		return view;
	}

	@Override
	protected void initData() {
		activity = (PostListActivity) getActivity();
		plate = activity.getPlate();
	}

	@Override
	protected void findViewById(View view) {
		button_floating_action = (FloatingActionButton) view
				.findViewById(R.id.button_floating_action);
		loadingdialog = new LoadingDialog(getActivity());
		llyt_network_error = (LinearLayout) view
				.findViewById(R.id.llyt_network_error);
		llyt_null_post = (LinearLayout) view
				.findViewById(R.id.llyt_plate_null_post);

		pullsv_post = (PullToRefreshListView) view
				.findViewById(R.id.pulllv_posts);
		pullsv_post.setPullLoadEnabled(true);
		pullsv_post.setScrollLoadEnabled(true);
		lv_posts = pullsv_post.getRefreshableView();

		// container
		View view2 = View.inflate(getActivity(),
				R.layout.include_bbs_posts_container, null);
		lv_stick_posts = (ListView) view2.findViewById(R.id.lv_stick_posts);
		tv_plate_name = (TextView) view2.findViewById(R.id.tv_bbs_plate_name);
		tv_plate_description = (TextView) view2
				.findViewById(R.id.tv_bbs_plate_description);
		iv_plate_icon = (ImageView) view2.findViewById(R.id.iv_bbs_plate_icon);

		lv_posts.addHeaderView(view2);
		lv_posts.setDivider(getResources().getDrawable(
				R.drawable.drawable_transparent));
		lv_posts.setDividerHeight(SizeUtil.dip2px(getActivity(), 8));
		lv_posts.setSelector(getResources().getDrawable(
				R.drawable.drawable_transparent));
		button_floating_action.attachToListView(lv_posts);
	}

	@Override
	protected void initUI() {
		imageLoader.displayImage(URLConfig.PLATE_IMG_IP + plate.getLogo(),
				iv_plate_icon, CustomOptions.getOptions2());
		tv_plate_name.setText(plate.getTitle());
		tv_plate_description.setText(plate.getDescription());

		// 获取普通帖子列表
		initFirstData(currentPage);
		// 获取置顶帖子
		HttpUrlProvider.getIntance().getTopPost(getActivity(),
				new TaskTopPostBack(handler), plate.getId(), 1, 10);

		lv_posts.setOnScrollListener(new PauseOnScrollListener(ImageLoader
				.getInstance(), true, false));

		llyt_null_post.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void setListener() {
		llyt_network_error.setOnClickListener(this);

		// 查看帖子详情
		lv_posts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_posts.getItemAtPosition(position);
				if (obj instanceof BBSPost) {
					BBSPost postinfo = (BBSPost) obj;
					Intent intent = new Intent(getActivity(),
							PostInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("postinfo_starter", postinfo.getId());
					bundle.putString("forumId", plate.getId());
					intent.putExtras(bundle);
					startActivity(intent);
					activity.overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}
			}
		});

		lv_stick_posts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_stick_posts.getItemAtPosition(position);
				if (obj instanceof BBSPost) {
					BBSPost postinfo = (BBSPost) obj;
					Intent intent = new Intent(getActivity(),
							PostInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("postinfo_starter", postinfo.getId());
					// 版块id
					bundle.putString("forumId", plate.getId());
					intent.putExtras(bundle);
					startActivity(intent);
					activity.overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}
			}
		});
		// 下拉刷新

		pullsv_post.setOnRefreshListener(new OnRefreshListener<ListView>() {

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
				if (flag) {
					currentPage += 1;
					initFirstData(currentPage);
				} else {
					ToastUtil.ToastView(getActivity(), getResources()
							.getString(R.string.no_more));
					// 下拉加载完成
					pullsv_post.onPullDownRefreshComplete();
					// 上拉刷新完成
					pullsv_post.onPullUpRefreshComplete();
					// 设置是否有更多的数据
					pullsv_post.setHasMoreData(false);
				}
			}
		});
		// 滑动时不加载图片
		lv_posts.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, false));
		button_floating_action.setOnClickListener(this);
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData(int currentPage) {
		// 请求数据
		HttpUrlProvider.getIntance().getPost(getActivity(),
				new TaskPostListBack(handler), URLConfig.FORUM_LIST_IP,
				plate.getId(), currentPage, 15);

		loadingdialog.show(getActivity());
	}

	@Override
	public void onResume() {
		super.onResume();
		if (loadingdialog == null) {
			loadingdialog = new LoadingDialog(getActivity());
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		imageLoader.stop();
		imageLoader.clearMemoryCache();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		imageLoader.stop();
		imageLoader.clearMemoryCache();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.llyt_network_error:// 重新加载数据
			isUp = true;
			currentPage = 1;
			initFirstData(currentPage);
			// 获取置顶帖子
			HttpUrlProvider.getIntance().getTopPost(getActivity(),
					new TaskTopPostBack(handler), plate.getId(), 1, 10);

			break;
		case R.id.button_floating_action:// 滚动到顶部
			lv_posts.requestFocusFromTouch();
			lv_posts.setSelection(0);
			break;
		}
	}
}
