package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.xiuman.xingduoduo.adapter.ReplyFloorListViewAdapter;
import com.xiuman.xingduoduo.model.PostReply;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshScrollView;

/**
 * @名称：FloorReplyInfoActivity.java
 * @描述：层主以及回复层主
 * @author danding 2014-8-19
 */
public class FloorReplyInfoActivity extends Base2Activity implements OnClickListener {

	/*-------------------------组件----------------------------*/
	// 返回
	private Button btn_back;
	// 右侧(隐藏)
	private Button btn_right;
	// 标题栏
	private TextView tv_title;
	// 下拉可刷新的ScrollView
	private PullToRefreshScrollView pullsv_floorinfo;
	// ScrollView
	private ScrollView sv_floorinfo;

	// 层主----------------------------------------------------------
	// 层主头像
	private ImageView iv_floorinfo_head;
	// 层主用户名
	private TextView tv_floorinfo_name;
	// 发表时间
	private TextView tv_floorinfo_time;
	// 回复层主的按钮
	private Button btn_floorinfo_reply;
	// 层主帖子内容
	private TextView tv_floorinfo_content;
	// 回复层主的帖子列表
	private ListView lv_floorinfo_replys;
	// 底部回复输入框(父)----------------------
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

	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;
	
	/*----------------------Adapter----------------------------------*/
	//adapter
	private ReplyFloorListViewAdapter adapter;
	
	/*------------------------数据-----------------------------------*/
	//从上级界面接收到的回复信息---------------------------------------
	//层主信息
	private PostReply reply;
	//测试数据(有接口时不需传递回复楼层的回复)
	private ArrayList<PostReply> replys;
	//层主的楼层信息
	private int floor = 1;
	
	
	
	
	//消息处理
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_floor_reply_info);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	protected void initData() {
		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.weiboitem_pic_loading) //
		// 在ImageView加载过程中显示图片
		.showImageOnLoading(R.drawable.onloading)
				.showImageForEmptyUri(R.drawable.onloading) // image连接地址为空时
				.showImageOnFail(R.drawable.onloading) // image加载失败
				.cacheInMemory(false) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.NONE).build();
		
		//从上级界面接收到的信息
		Bundle bundle = getIntent().getExtras();
		reply = (PostReply) bundle.getSerializable("reply");
		replys = (ArrayList<PostReply>) bundle.getSerializable("replys");
		floor = bundle.getInt("floor");
	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		btn_reply = (Button) findViewById(R.id.btn_reply);
		et_reply = (EditText) findViewById(R.id.et_reply);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_reply_container = (LinearLayout) findViewById(R.id.llyt_reply_container);
		
		pullsv_floorinfo = (PullToRefreshScrollView) findViewById(R.id.pullsv_floorinfo);
		// 下拉刷新不可用,上拉加载可用
		pullsv_floorinfo.setPullRefreshEnabled(false);
		pullsv_floorinfo.setPullLoadEnabled(true);
		pullsv_floorinfo.setScrollLoadEnabled(true);
		sv_floorinfo = pullsv_floorinfo.getRefreshableView();
		View view = View.inflate(this, R.layout.include_post_floorinfo_container,
				null);
		btn_floorinfo_reply = (Button) view.findViewById(R.id.btn_floorinfo_reply);
		iv_floorinfo_head = (ImageView) view.findViewById(R.id.iv_floorinfo_head);
		tv_floorinfo_name = (TextView) view.findViewById(R.id.tv_floorinfo_name);
		tv_floorinfo_time = (TextView) view.findViewById(R.id.tv_floorinfo_time);
		tv_floorinfo_content = (TextView) view.findViewById(R.id.tv_floorinfo_content);
		lv_floorinfo_replys = (ListView) view.findViewById(R.id.lv_floorinfo_replys);
		
		sv_floorinfo.addView(view);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		
		tv_title.setText(floor+"楼");
		tv_floorinfo_name.setText(reply.getReply_user().getUserName());
		tv_floorinfo_time.setText(TimeUtil.getTimeStr(TimeUtil.strToDate(reply.getReply_time()), new Date()));
		tv_floorinfo_content.setText(reply.getReply_content());
		adapter = new ReplyFloorListViewAdapter(this, replys, true);
		lv_floorinfo_replys.setAdapter(adapter);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_floorinfo_reply.setOnClickListener(this);
		btn_reply.setOnClickListener(this);
		
		lv_floorinfo_replys.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_floorinfo_replys.getItemAtPosition(position);
				if(obj instanceof PostReply){
					PostReply reply = (PostReply)obj;
					et_reply.setHint("回复@"+reply.getReply_user().getUserName()+":");
					et_reply.setFocusable(true);
					et_reply.requestFocus();
				}
			}
		});
	}
	/**
	 * @描述：点击事件
	 * @param v
	 * 2014-8-19
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back://返回
			finish();
			break;
		case R.id.btn_floorinfo_reply://回复层主
			
			break;
		case R.id.btn_reply://回复
			et_reply.setHint("说点啥...");
			et_reply.setFocusable(true);
			et_reply.requestFocus();
			break;
		default:
			break;
		}
	}

}
