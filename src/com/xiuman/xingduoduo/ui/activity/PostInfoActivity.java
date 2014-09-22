package com.xiuman.xingduoduo.ui.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.PlatePostListViewAdapter;
import com.xiuman.xingduoduo.adapter.PlateStickPostListViewAdapter;
import com.xiuman.xingduoduo.adapter.PostInfoImgsListViewAdapter;
import com.xiuman.xingduoduo.adapter.ReplyStarterListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.callback.TaskPostReplyBack;
import com.xiuman.xingduoduo.callback.TaskReplySendBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.model.BBSPostReply;
import com.xiuman.xingduoduo.model.PostReply;
import com.xiuman.xingduoduo.model.PostStarter;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.testdata.Test;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.HtmlTag;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshScrollView;

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
	private PullToRefreshScrollView pullsv_postinfo;
	// 可刷新的ScrollView
	private ScrollView sv_postinfo;
	// 帖子标签
	private ImageView iv_postinfo_tag;

	// 楼主----------------------------------------------------------
	// 楼主头像
	private ImageView iv_postinfo_starter_head;
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

	// 底部回复输入框(父)----------------------
	private LinearLayout llyt_reply_container;
	// 回复输入框
	private EditText et_reply;
	// 发送回复
	private Button btn_reply;

	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 帖子为空时显示的布局
	private LinearLayout llyt_null_post;
	// 夹在数据时显示的Dialog
	private LoadingDialog loadingdialog;

	/*---------------------------ImageLoader------------------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

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
	// 回复列表
	private ArrayList<PostReply> replys;

	private ActionValue<BBSPostReply> value;

	private ArrayList<BBSPostReply> bbsReply;

	private ActionValue<?> valueSend;

	private String forumId;
	
	private String userId;

	// 消息处理Handler-------------------------------------
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case AppConfig.BBS_REPLY_POST_BACK:

				value = (ActionValue<BBSPostReply>) msg.obj;
				if (value.isSuccess()) {
					bbsReply = value.getDatasource();
					bbsReply.remove(0);

					// 回复楼层
					adapter = new ReplyStarterListViewAdapter(
							PostInfoActivity.this, bbsReply);
					lv_postinfo_replys.setAdapter(adapter);

				}
				loadingdialog.dismiss();

				break;
			case AppConfig.BBS_REPLY_SEND_BACK:
				valueSend = (ActionValue<?>) msg.obj;
				if (valueSend.isSuccess()) {

					ToastUtil.ToastView(PostInfoActivity.this,
							valueSend.getMessage());
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					String createTime = format.format(date);
					BBSPostReply bps = new BBSPostReply(et_reply.getText().toString(),
							createTime, postinfo_starter.title, userId, postinfo_starter.id, postinfo_starter.postTypeId, 1);

					bbsReply.add(bps);
					adapter.notifyDataSetChanged();

					// 回复楼层
//					adapter = new ReplyStarterListViewAdapter(
//							PostInfoActivity.this, bbsReply);
//					lv_postinfo_replys.setAdapter(adapter);

				} else {
					ToastUtil.ToastView(PostInfoActivity.this, "回复失败请重试");

				}
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.VISIBLE);
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
		// .showStubImage(R.drawable.weiboitem_pic_loading) //
		// 在ImageView加载过程中显示图片
				.showImageForEmptyUri(R.drawable.onloading_goods_poster) // image连接地址为空时
				.showImageOnFail(R.drawable.onloading_goods_poster) // image加载失败
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();

		// 从上级界面接收到的帖子
		postinfo_starter = (BBSPost) getIntent().getExtras().getSerializable(
				"postinfo_starter");
		forumId = getIntent().getExtras().getString("forumId");
		User user=MyApplication.getInstance().getUserInfo();
		userId=user.getUserId();

	}

	@Override
	protected void findViewById() {
		btn_postinfo_back = (Button) findViewById(R.id.btn_postinfo_back);
		btn_postinfo_starter = (Button) findViewById(R.id.btn_postinfo_starter);
		tv_postinfo_title = (TextView) findViewById(R.id.tv_postinfo_title);

		llyt_reply_container = (LinearLayout) findViewById(R.id.llyt_reply_container);
		et_reply = (EditText) findViewById(R.id.et_reply);
		btn_reply = (Button) findViewById(R.id.btn_reply);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_null_post = (LinearLayout) findViewById(R.id.llyt_plate_null_post);
		loadingdialog = new LoadingDialog(this);

		pullsv_postinfo = (PullToRefreshScrollView) findViewById(R.id.pullsv_postinfo);
		// 下拉刷新不可用,上拉加载可用
		pullsv_postinfo.setPullRefreshEnabled(false);
		pullsv_postinfo.setPullLoadEnabled(true);
		pullsv_postinfo.setScrollLoadEnabled(true);
		sv_postinfo = pullsv_postinfo.getRefreshableView();
		View view = View.inflate(this, R.layout.include_postinfo_container,
				null);
		iv_postinfo_starter_head = (ImageView) view
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
		lv_postinfo_replys = (ListView) view
				.findViewById(R.id.lv_postinfo_replys);
		sv_postinfo.addView(view);
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
	}

	/**
	 * @描述：加载帖子数据 2014-8-16
	 */
	private void initPostInfo() {
		tv_postinfo_starter_name.setText(postinfo_starter.user);
		tv_postinfo_starter_title.setText(postinfo_starter.title);
		tv_postinfo_starter_content.setText(postinfo_starter.content);
		tv_postinfo_starter_time.setText(postinfo_starter.createTime);
		List<String> imgList = new ArrayList<String>();

		imgList = HtmlTag.match(postinfo_starter.contentHtml, "img", "src");
		// 测试数据
		// replys = Test.getTestRelpy();
		HttpUrlProvider.getIntance().getPostReply(PostInfoActivity.this,
				new TaskPostReplyBack(handler), postinfo_starter.id);

		adapter_img = new PostInfoImgsListViewAdapter(this, options,
				imageLoader, imgList);
		lv_postinfo_starter_imgs.setAdapter(adapter_img);

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
			if(MyApplication.getInstance().isUserLogin()){
			if ((et_reply.getText().toString()).length() < 3) {
				ToastUtil.ToastView(PostInfoActivity.this, "回复不能少于3个字");
			} else {
				
				HttpUrlProvider.getIntance().getPostReplySend(
						PostInfoActivity.this, new TaskReplySendBack(handler),
						forumId, "" + postinfo_starter.postTypeId,
						"" + postinfo_starter.id, postinfo_starter.title,
						et_reply.getText().toString(), userId);

			}}else{
				Intent intentLogin = new Intent(PostInfoActivity.this,
						UserLoginActivity.class);
				startActivity(intentLogin);
				overridePendingTransition(R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
				
				
			}

			break;
		}
	}

}
