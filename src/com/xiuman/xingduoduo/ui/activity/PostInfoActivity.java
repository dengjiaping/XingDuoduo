package com.xiuman.xingduoduo.ui.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.PostInfoImgsListViewAdapter;
import com.xiuman.xingduoduo.adapter.ReplyStarterListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskPostReplyBack;
import com.xiuman.xingduoduo.callback.TaskReplySendBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.model.BBSPostReply;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.CircleImageView;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshListView;

/**
 * @名称：PostInfoActivity.java
 * @描述：帖子详情
 * @author danding 2014-8-16
 */
public class PostInfoActivity extends Base2Activity implements OnClickListener {

	/*---------------------------------组件-----------------------------------*/
	// 返回
	private Button btn_postinfo_back;
	// 只看楼主
	private Button btn_postinfo_starter;
	// 标题栏
	private TextView tv_postinfo_title;
	// 下拉刷新ScrollView
	private PullToRefreshListView pulllv_postinfo;
	// 帖子标签
	private ImageView iv_postinfo_tag;

	// 楼主----------------------------------------------------------
	// 楼主头像
	private CircleImageView iv_postinfo_starter_head;
	// 楼主用户名
	private TextView tv_postinfo_starter_name;
	// 楼主性别
	private ImageView iv_postinfo_starter_sex;
	// 发表时间
	private TextView tv_postinfo_starter_time;
	// 回复楼主的按钮
	private Button btn_postinfo_starter_reply;
	// 楼主帖子标题
	private TextView tv_postinfo_starter_title;
	// 楼主帖子内容
	private TextView tv_postinfo_starter_content;
	// 楼主的帖子里的图片(如果有则显示，无则隐藏)
	private ListView lv_postinfo_starter_imgs;

	// 回复楼主-----------------------------------------------------
	// 回复帖子列表
	private ListView lv_postinfo_replys;

	// 回复输入框
	private EditText et_reply;
	// 发送回复
	private Button btn_reply;

	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 夹在数据时显示的Dialog
	private LoadingDialog loadingdialog;

	/*---------------------------ImageLoader------------------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	// 控制软键盘的显示与隐藏
	private InputMethodManager imm;

	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	/*--------------------------------Adapter---------------------------------*/
	// 回复列表
	private ReplyStarterListViewAdapter adapter;
	// 帖子图片
	private PostInfoImgsListViewAdapter adapter_img;

	/*--------------------------------数据-------------------------------------*/
	// 从上级界面接收到的帖子信息(主要是楼主)
	private BBSPost postinfo_starter;
	// 获取恢复列表返回结果
	private ActionValue<BBSPostReply> value;
	// 回复列表
	private ArrayList<BBSPostReply> bbsReply;
	// 发表恢复返回结果
	private ActionValue<?> valueSend;
	//
	private String forumId;

	private String userId;
	private User user;

	// 当前请求页
	private int currentPage = 1;

	// 消息处理Handler-------------------------------------
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case AppConfig.BBS_REPLY_POST_BACK:

				value = (ActionValue<BBSPostReply>) msg.obj;
				if (value.isSuccess()) {
					if (isUp) {
						bbsReply = value.getDatasource();
						bbsReply.remove(0);

						// 回复楼层
						adapter = new ReplyStarterListViewAdapter(
								PostInfoActivity.this, bbsReply, options,
								imageLoader);
						lv_postinfo_replys.setAdapter(adapter);
						// 下拉加载完成
						pulllv_postinfo.onPullDownRefreshComplete();
						// 上拉刷新完成
						pulllv_postinfo.onPullUpRefreshComplete();
					} else {
						bbsReply.addAll(value.getDatasource());
						adapter.notifyDataSetChanged();
						// 上拉刷新完成
						pulllv_postinfo.onPullUpRefreshComplete();
					}
					pulllv_postinfo.setHasMoreData(true);
				} else {
					ToastUtil.ToastView(PostInfoActivity.this, "没有更多回复！");
					// 上拉刷新完成
					pulllv_postinfo.onPullUpRefreshComplete();
					pulllv_postinfo.setHasMoreData(false);
				}
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.BBS_REPLY_SEND_BACK:// 获取回复成功
				valueSend = (ActionValue<?>) msg.obj;
				if (valueSend.isSuccess()) {
					imm.hideSoftInputFromWindow(et_reply.getWindowToken(), 0);
					ToastUtil.ToastView(PostInfoActivity.this,
							valueSend.getMessage());
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					String createTime = format.format(date);
					boolean sex = false;
					if (user.getGender() != null) {
						if (user.getGender() == "male") {
							sex = true;
						}
					}
					BBSPostReply bps = new BBSPostReply(et_reply.getText()
							.toString(), createTime,
							postinfo_starter.getTitle(), user.getNickname(),
							postinfo_starter.getId() + "",
							postinfo_starter.getPostTypeId(), 1+"", sex,
							user.getHead_image(), user.getName());

					bbsReply.add(bps);
					et_reply.setText("");
					adapter.notifyDataSetChanged();
					llyt_network_error.setVisibility(View.INVISIBLE);
					// 回复楼层
					// adapter = new ReplyStarterListViewAdapter(
					// PostInfoActivity.this, bbsReply);
					// lv_postinfo_replys.setAdapter(adapter);

				} else {
					ToastUtil.ToastView(PostInfoActivity.this, "回复失败请重试");

				}
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.VISIBLE);
				break;
			case AppConfig.BBS_REPLY_FAILD:
				ToastUtil.ToastView(PostInfoActivity.this, "回复失败请重试!");
				break;
			case AppConfig.FLUSH_IMG_ADAPTER:// 刷新图片
				adapter_img.notifyDataSetChanged();
				setListViewHeight(lv_postinfo_starter_imgs);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_info);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		options = new DisplayImageOptions.Builder()
//				 .showImageOnLoading(R.drawable.onloadong_post)
				.showImageForEmptyUri(R.drawable.onloadong_post)
				// image连接地址为空时
				.showImageOnFail(R.drawable.onloadong_post)
				// image加载失败
				.cacheInMemory(true)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true)
				// 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 从上级界面接收到的帖子
		postinfo_starter = (BBSPost) getIntent().getExtras().getSerializable(
				"postinfo_starter");
		forumId = getIntent().getExtras().getString("forumId");
		if (MyApplication.getInstance().isUserLogin()) {
			user = MyApplication.getInstance().getUserInfo();
			userId = user.getUser_id();
		}

	}

	@Override
	protected void findViewById() {
		btn_postinfo_back = (Button) findViewById(R.id.btn_postinfo_back);
		btn_postinfo_starter = (Button) findViewById(R.id.btn_postinfo_starter);
		tv_postinfo_title = (TextView) findViewById(R.id.tv_postinfo_title);

		et_reply = (EditText) findViewById(R.id.et_reply);
		btn_reply = (Button) findViewById(R.id.btn_reply);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		loadingdialog = new LoadingDialog(this);

		pulllv_postinfo = (PullToRefreshListView) findViewById(R.id.pulllv_postinfo);
		// 下拉刷新不可用,上拉加载可用
		pulllv_postinfo.setPullRefreshEnabled(false);
		pulllv_postinfo.setPullLoadEnabled(true);
		pulllv_postinfo.setScrollLoadEnabled(true);
		lv_postinfo_replys = pulllv_postinfo.getRefreshableView();

		lv_postinfo_replys.setCacheColorHint(Color.TRANSPARENT);
		lv_postinfo_replys.setBackgroundColor(Color.WHITE);
		lv_postinfo_replys.setDivider(getResources().getDrawable(
				R.drawable.line_small));
		lv_postinfo_replys.setSelector(R.color.color_white);

		View view = View.inflate(this, R.layout.include_postinfo_container,
				null);
		iv_postinfo_starter_head = (CircleImageView) view
				.findViewById(R.id.iv_postinfo_starter_head);
		iv_postinfo_starter_sex = (ImageView) view
				.findViewById(R.id.iv_postinfo_starter_sex);
		iv_postinfo_tag = (ImageView) view.findViewById(R.id.iv_postinfo_tag);
		tv_postinfo_starter_name = (TextView) view
				.findViewById(R.id.tv_postinfo_starter_name);
		tv_postinfo_starter_time = (TextView) view
				.findViewById(R.id.tv_postinfo_starter_time);
		tv_postinfo_starter_content = (TextView) view
				.findViewById(R.id.tv_postinfo_starter_content);
		tv_postinfo_starter_title = (TextView) view
				.findViewById(R.id.tv_postinfo_starter_title);
		btn_postinfo_starter_reply = (Button) view
				.findViewById(R.id.btn_postinfo_starter_reply);
		lv_postinfo_starter_imgs = (ListView) view
				.findViewById(R.id.lv_postinfo_starter_imgs);

		lv_postinfo_replys.addHeaderView(view);
	}

	@Override
	protected void initUI() {
		tv_postinfo_title.setText(R.string.postinfo_title);
		initPostInfo();
	}

	@Override
	protected void setListener() {
		btn_postinfo_back.setOnClickListener(this);
		btn_postinfo_starter.setOnClickListener(this);
		btn_postinfo_starter_reply.setOnClickListener(this);
		btn_reply.setOnClickListener(this);
		llyt_network_error.setOnClickListener(this);
		pulllv_postinfo.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				isUp = false;
				currentPage++;
				getReply(currentPage);
			}
		});
		//预览图片
		lv_postinfo_starter_imgs
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(PostInfoActivity.this,
								PostImgViewActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("current", position);
						bundle.putStringArrayList("imgs",
								postinfo_starter.getImgList());
						intent.putExtras(bundle);

						startActivity(intent);
						overridePendingTransition(
								R.anim.translate_horizontal_start_in,
								R.anim.translate_horizontal_start_out);

					}
				});

	}
	@Override
	protected void onResume() {
		super.onResume();
		if (MyApplication.getInstance().isUserLogin()) {
			user = MyApplication.getInstance().getUserInfo();
			userId = user.getUser_id();
		}
	}

	/**
	 * @描述：加载帖子数据 2014-8-16
	 */
	private void initPostInfo() {
		// 头像
		imageLoader.displayImage(
				URLConfig.IMG_IP + postinfo_starter.getAvatar(),
				iv_postinfo_starter_head, options, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						if (postinfo_starter.isSex()) {
							iv_postinfo_starter_head
									.setImageResource(R.drawable.ic_male);
						} else {
							iv_postinfo_starter_head
									.setImageResource(R.drawable.ic_female);
						}
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {

					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {

					}
				});
		// 性别
		if (postinfo_starter.isSex()) {
			iv_postinfo_starter_sex.setImageResource(R.drawable.sex_male);
		} else {
			iv_postinfo_starter_sex.setImageResource(R.drawable.sex_female);
		}
		tv_postinfo_starter_name.setText(postinfo_starter.getNickname());
		if (postinfo_starter.getNickname() == null) {
			tv_postinfo_starter_name.setText(postinfo_starter.getUsername());
		}
		tv_postinfo_starter_title.setText(postinfo_starter.getTitle());
		tv_postinfo_starter_content.setText(postinfo_starter.getContent());
		tv_postinfo_starter_time.setText(TimeUtil.getTimeStr(
				TimeUtil.strToDate(postinfo_starter.getCreateTime()),
				new Date()));
		//
		adapter_img = new PostInfoImgsListViewAdapter(this, options,
				imageLoader, postinfo_starter.getImgList(), handler);
		lv_postinfo_starter_imgs.setAdapter(adapter_img);
		setListViewHeight(lv_postinfo_starter_imgs);
		loadingdialog.show(PostInfoActivity.this);
		// 获取列表
		getReply(currentPage);
	}

	/**
	 * @描述：获取回复
	 * @param currentPage
	 *            2014-9-25
	 */
	private void getReply(int currentPage) {
		// 请求获取回复列表
		HttpUrlProvider.getIntance().getPostReply(PostInfoActivity.this,
				new TaskPostReplyBack(handler), postinfo_starter.getId(),
				currentPage);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_postinfo_back:// 返回
			finish();
			break;
		case R.id.btn_postinfo_starter:// 只看楼主
			if (btn_postinfo_starter.isClickable()) {

			} else {

			}

			break;
		case R.id.btn_postinfo_starter_reply:// 回复楼主
			et_reply.setFocusable(true);
			et_reply.requestFocus();
			break;
		case R.id.btn_reply:// 发送回复
			if (MyApplication.getInstance().isUserLogin()) {
				if ((et_reply.getText().toString().trim()).length() < 2) {
					ToastUtil.ToastView(PostInfoActivity.this, "回复不能少于2个字");
				} else {

					HttpUrlProvider.getIntance().getPostReplySend(
							PostInfoActivity.this,
							new TaskReplySendBack(handler), forumId,
							"" + postinfo_starter.getPostTypeId(),
							"" + postinfo_starter.getId(),
							postinfo_starter.getTitle(),
							et_reply.getText().toString(), userId);

				}
			} else {
				Intent intentLogin = new Intent(PostInfoActivity.this,
						UserLoginActivity.class);
				startActivity(intentLogin);
				overridePendingTransition(R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);

			}

			break;
		case R.id.llyt_network_error:// 重新加载
			currentPage = 1;
			initPostInfo();
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss();
		loadingdialog = null;
		imageLoader.stop();
		imageLoader.clearMemoryCache();
	}
	/**
	 * @描述：设置ListView的高度
	 * @日期：2014-10-11
	 * @param listView
	 */
	public void setListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter(); // 根据传入ListView的adapter的getView方法获取每个item的高度，然后叠加就得到ListView的高度
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1)); // 加上diverHeight
		listView.setLayoutParams(params);
	}
}
