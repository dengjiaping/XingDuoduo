package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

import u.aly.T;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.ReplyStarterListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.callback.TaskPostReplyBack;
import com.xiuman.xingduoduo.callback.TaskReplySendBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BBSPostReply;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ToastUtil;
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
	private Button btn_share;
	// 标题栏
	private TextView tv_postinfo_title;
	// 下拉刷新ScrollView
	private PullToRefreshListView pulllv_postinfo;

	// 回复楼主-----------------------------------------------------
	// 回复帖子列表
	private ListView lv_postinfo_replys;

	//回复container
	private LinearLayout llyt_reply_container;
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

	/*--------------------------------数据-------------------------------------*/
	// 从上级界面接收到的帖子信息(主要是楼主)
	private BBSPostReply postinfo_starter;
	// 获取恢复列表返回结果
	private ActionValue<BBSPostReply> value = new ActionValue<BBSPostReply>();
	// 回复列表
	private ArrayList<BBSPostReply> bbsReply = new ArrayList<BBSPostReply>();
	// 发表恢复返回结果
	private ActionValue<?> valueSend = new ActionValue<T>();
	// 板块id
	private String forumId;
	// 用户id
	private String userId;
	// 用户信息
	private User user;
	// 帖子id
	private String post_id;

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
						postinfo_starter = bbsReply.get(0);
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
//					ToastUtil.ToastView(PostInfoActivity.this, "没有更多回复！");
					// 上拉刷新完成
					pulllv_postinfo.onPullUpRefreshComplete();
					pulllv_postinfo.setHasMoreData(false);
				}
				loadingdialog.dismiss(PostInfoActivity.this);
				llyt_network_error.setVisibility(View.INVISIBLE);
				llyt_reply_container.setVisibility(View.VISIBLE);
				break;
			case AppConfig.BBS_REPLY_SEND_BACK:// 获取回复成功
				valueSend = (ActionValue<?>) msg.obj;
				if (valueSend.isSuccess()) {
					imm.hideSoftInputFromWindow(et_reply.getWindowToken(), 0);
					ToastUtil.ToastView(PostInfoActivity.this,
							valueSend.getMessage());
					llyt_network_error.setVisibility(View.INVISIBLE);
					// 回复楼层
					// adapter = new ReplyStarterListViewAdapter(
					// PostInfoActivity.this, bbsReply);
					// lv_postinfo_replys.setAdapter(adapter);
					et_reply.setText("");
				} else {
					ToastUtil.ToastView(PostInfoActivity.this, "回复失败请重试");

				}
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss(PostInfoActivity.this);
				llyt_network_error.setVisibility(View.VISIBLE);
				llyt_reply_container.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.BBS_REPLY_FAILD:
				ToastUtil.ToastView(PostInfoActivity.this, "回复失败请重试!");
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
				.showImageOnLoading(R.drawable.onloading)
				.showImageForEmptyUri(R.drawable.onloading)
				// image连接地址为空时
				.showImageOnFail(R.drawable.onloading)
				// image加载失败
				.cacheInMemory(true)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true)
				// 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 从上级界面接收到的帖子
		// BBSPost postinfo_starte = (BBSPost)
		// getIntent().getExtras().getSerializable(
		// "postinfo_starter");
		post_id = getIntent().getExtras().getString("postinfo_starter");
		forumId = getIntent().getExtras().getString("forumId");
		if (MyApplication.getInstance().isUserLogin()) {
			user = MyApplication.getInstance().getUserInfo();
			userId = user.getUser_id();
		}
		
		//初始化微信分享
		initShare();
	}

	@Override
	protected void findViewById() {
		btn_postinfo_back = (Button) findViewById(R.id.btn_postinfo_back);
		btn_share = (Button) findViewById(R.id.btn_share);
		tv_postinfo_title = (TextView) findViewById(R.id.tv_postinfo_title);

		et_reply = (EditText) findViewById(R.id.et_reply);
		btn_reply = (Button) findViewById(R.id.btn_reply);
		llyt_reply_container = (LinearLayout) findViewById(R.id.llyt_reply_container);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		loadingdialog = new LoadingDialog(PostInfoActivity.this);

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
	}

	@Override
	protected void initUI() {
		tv_postinfo_title.setText(R.string.postinfo_title);
		initPostInfo();
	}

	@Override
	protected void setListener() {
		btn_postinfo_back.setOnClickListener(this);
		btn_share.setOnClickListener(this);
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
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(loadingdialog==null){
			loadingdialog = new LoadingDialog(this);
		}
		if (MyApplication.getInstance().isUserLogin()) {
			user = MyApplication.getInstance().getUserInfo();
			userId = user.getUser_id();
		}
	}

	/**
	 * @描述：加载帖子数据 2014-8-16
	 */
	private void initPostInfo() {
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
		// 擦擦擦
		HttpUrlProvider.getIntance().getPostReply(PostInfoActivity.this,
				new TaskPostReplyBack(handler), post_id, currentPage);
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
		case R.id.btn_share:// 分享
			showPop(v);
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
					// 擦擦擦
					HttpUrlProvider.getIntance().getPostReplySend(
							PostInfoActivity.this,
							new TaskReplySendBack(handler), forumId,
							"" + postinfo_starter.getPostTypeId(),
							"" + post_id, postinfo_starter.getTitle(),
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
		case R.id.iv_postinfo_starter_head:// 查看楼主头像
			Intent intent = new Intent(PostInfoActivity.this,
					HeadImgViewActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("user_head", postinfo_starter.getAvatar());
			bundle.putBoolean("user_sex", postinfo_starter.isSex());
			intent.putExtras(bundle);
			startActivity(intent);
			overridePendingTransition(R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.btn_weixin://分享到微信
//			startShare(true);
			dismissPop();
			break;
		case R.id.btn_weixin_circle://朋友圈
//			startShare(false);
			dismissPop();
			break;
		case R.id.btn_pop_post_cancel://取消分享
			dismissPop();
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss(PostInfoActivity.this);
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

	/*---------PopWindow----------*/
	// 头像选择Pop
	private PopupWindow pop;
	// 头像选择View
	private View popview;
	// 相册选择
	private Button btn_weixin;
	// 拍照
	private Button btn_weixin_circle;
	// 取消
	private Button btn_pop_post_cancel;
	/*--------------------------友盟分享--------------------------*/
	public static final String DESCRIPTOR = "com.umeng.share";
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService(DESCRIPTOR,RequestType.SOCIAL);

	/**
	 * @描述：初始化微信分享
	 * @时间 2014-10-16
	 */
	private void initShare() {
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId = "wxbb84418b57740c4e";
		String appSecret = "d0e79d1086c1f37138660420c8cdcb30";

		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(PostInfoActivity.this, appId,
				appSecret);
		wxHandler.addToSocialSDK();
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(PostInfoActivity.this,
				appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}
	/**
	 * @描述：设置分享内容
	 * @时间 2014-10-16
	 */
	private void share(String content,String title,String imgUrl,String contentUrl) {
		UMImage urlImage = new UMImage(PostInfoActivity.this,
				"http://www.umeng.com/images/pic/home/social/img-1.png");
		// 设置微信好友分享内容
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		// 设置分享文字
		weixinContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能，微信");
		// 设置title
		weixinContent.setTitle("友盟社会化分享组件-微信");
		// 设置分享内容跳转URL
		weixinContent.setTargetUrl("http://www.umeng.com");
		// 设置分享图片
		weixinContent.setShareImage(urlImage);
		mController.setShareMedia(weixinContent);

		// 设置微信朋友圈分享内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能，朋友圈");
		// 设置朋友圈title
		circleMedia.setTitle("友盟社会化分享组件-朋友圈");
		circleMedia.setShareImage(urlImage);
		circleMedia.setTargetUrl("http://www.umeng.com");
		mController.setShareMedia(circleMedia);
	}

	/**
	 * 显示popupwindow
	 * 
	 * @param view
	 */
	private void showPop(View view) {
		if (pop == null) {
			popview = View.inflate(this, R.layout.pop_post_share, null);
			pop = new PopupWindow(popview, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
		}

		btn_weixin = (Button) popview
				.findViewById(R.id.btn_weixin);
		btn_weixin_circle = (Button) popview
				.findViewById(R.id.btn_weixin_circle);
		btn_pop_post_cancel = (Button) popview
				.findViewById(R.id.btn_pop_post_cancel);

		btn_weixin.setOnClickListener(this);
		btn_weixin_circle.setOnClickListener(this);
		btn_pop_post_cancel.setOnClickListener(this);

		// 使其聚焦
		pop.setFocusable(true);
		// 设置允许在外点击消失
		pop.setOutsideTouchable(true);
		// 给pop设置背景
		Drawable background = new ColorDrawable(Color.TRANSPARENT);
		pop.setBackgroundDrawable(background);
		// 设置pop动画
		pop.setAnimationStyle(R.style.PopupAnimation);
		// 设置pop 的位置
		pop.showAtLocation(view, Gravity.TOP, 0, (int) (MyApplication
				.getInstance().getScreenHeight() * 3 / 4));
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
	 * @描述：开始分享
	 * @param flag
	 * @时间 2014-10-16
	 */
	private void startShare(boolean flag,String content,String title,String imgUrl,String contentUrl) {
		share(content, title, imgUrl, contentUrl);
		if (flag) {
			// 分享到微信
			mController.postShare(PostInfoActivity.this, SHARE_MEDIA.WEIXIN,
					new SnsPostListener() {
						@Override
						public void onStart() {
							// 分享
							Toast.makeText(PostInfoActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onComplete(SHARE_MEDIA platform, int eCode,
								SocializeEntity entity) {
							if (eCode == 200) {
							} else {
								String eMsg = "";
								if (eCode == -101) {
									eMsg = "没有授权";
								}
								Toast.makeText(PostInfoActivity.this,
										"分享失败[" + eCode + "] " + eMsg,
										Toast.LENGTH_SHORT).show();
							}
						}
					});
		} else {
			// 分享到朋友圈
			mController.postShare(this, SHARE_MEDIA.WEIXIN_CIRCLE,
					new SnsPostListener() {
						@Override
						public void onStart() {
						}

						@Override
						public void onComplete(SHARE_MEDIA platform, int eCode,
								SocializeEntity entity) {
							if (eCode == 200) {
							} else {
								String eMsg = "";
								if (eCode == -101) {
									eMsg = "没有授权";
								}
								Toast.makeText(PostInfoActivity.this,
										"分享失败[" + eCode + "] " + eMsg,
										Toast.LENGTH_SHORT).show();
							}
						}
					});
		}
	}

}
