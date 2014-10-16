package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.AppRecommendListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.callback.TaskAppRecommendBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.AppRecommend;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * @名称：AppRecommendActivity.java
 * @描述：应用推荐
 * @author danding 2014-9-21
 */
public class AppRecommendActivity extends Base2Activity implements
		OnClickListener {

	/*------------------------------------组件---------------------------------*/
	// 返回
	private Button btn_back;
	// 标题栏
	private TextView tv_title;
	// 右侧
	private Button btn_right;
	// 应用列表
	private ListView lv_app_recommend;
	// 加载
	private LoadingDialog loadingdialog;

	/*-----------------------ImageLoader-----------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	// Adapter
	private AppRecommendListViewAdapter adapter;

	// 请求返回结果
	private ActionValue<AppRecommend> value_app;
	// 列表
	private ArrayList<AppRecommend> apps = new ArrayList<AppRecommend>();

	// 数据处理Hanlder
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 商品信息数据请求成功
				value_app = (ActionValue<AppRecommend>) msg.obj;
				apps = value_app.getDatasource();
				adapter = new AppRecommendListViewAdapter(
						AppRecommendActivity.this, options, imageLoader, apps);
				lv_app_recommend.setAdapter(adapter);

				loadingdialog.dismiss();
				break;
			case AppConfig.NET_ERROR_NOTNET://
				loadingdialog.dismiss();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_recommend);
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
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		lv_app_recommend = (ListView) findViewById(R.id.lv_app_recommend);
	}

	@Override
	protected void initUI() {
		tv_title.setText("精品推荐");
		btn_right.setVisibility(View.INVISIBLE);
		getAppRecommend();
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		lv_app_recommend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Object obj = lv_app_recommend.getItemAtPosition(position);
				if (obj instanceof AppRecommend) {
					AppRecommend app = (AppRecommend) obj;
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					Uri content_url = Uri.parse(app.getAppUrl());
					intent.setData(content_url);
					startActivity(intent);
				}

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(loadingdialog==null){
			loadingdialog = new LoadingDialog(this);
		}
	}
	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;

		default:
			break;
		}
	}

	/**
	 * @描述：请求应用推荐 2014-9-22
	 */
	private void getAppRecommend() {
		HttpUrlProvider.getIntance().getAppRecommend(this,
				new TaskAppRecommendBack(handler));
		loadingdialog.show(AppRecommendActivity.this);
	}

}
