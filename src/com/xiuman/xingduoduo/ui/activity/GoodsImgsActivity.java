package com.xiuman.xingduoduo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.ui.base.Base2Activity;

/**
 * 
 * @名称：GoodsImgsActivity.java
 * @描述：商品的图文详情界面
 * @author danding
 * @version
 * @date：2014-6-27
 */
public class GoodsImgsActivity extends Base2Activity implements OnClickListener {

	/*-------------------------------组件----------------------------*/
	// 返回
	private Button btn_back;
	// 收藏(隐藏掉)
	private Button btn_collect;
	// WebView
	private WebView webview_img;
	//网页载入进度
	private ProgressBar pb_progress;
	//传递过来的商品图文连接为空时显示的布局
	private LinearLayout llyt_null_goods_img;
	
	/*-------------------------------数据----------------------------*/
	// 从上级界面获取到的商品图文详情数据
	private String goods_img_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_img_txt);
		initData();
		findViewById();
		initUI();
		setListener();

	}

	@Override
	protected void initData() {

		// 测试数据，添加操作
		goods_img_url = getIntent().getExtras().getString("introduction");
	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_back_goods);
		btn_collect = (Button) findViewById(R.id.btn_collect);
		webview_img = (WebView) findViewById(R.id.webview_img);
		pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
		llyt_null_goods_img = (LinearLayout) findViewById(R.id.llyt_goods_null_img);
	}

	@Override
	protected void initUI() {
		btn_collect.setVisibility(View.INVISIBLE);
		
		//图片连接是否为空
		if(goods_img_url.equals("")){
			llyt_null_goods_img.setVisibility(View.VISIBLE);
		}else{
			llyt_null_goods_img.setVisibility(View.INVISIBLE);
		}
		
		// WebView设置
		WebSettings ws = webview_img.getSettings();
		// 宽视图，默认false
		ws.setUseWideViewPort(true);
		ws.setLoadWithOverviewMode(true);
		// 支持javaScript(一直刷新。。。。)
		// ws.setJavaScriptEnabled(true);
		// 支持缩放
		ws.setSupportZoom(true);
		// 缩放尺寸
		ws.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		// 设置出现缩放工具
		ws.setBuiltInZoomControls(true);
		// 网页自适应
		ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// 设置初始比例
		webview_img.setInitialScale(100);
		ws.setDomStorageEnabled(true);

		webview_img.setWebViewClient(new MyWebViwClient());

		webview_img.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				pb_progress.setProgress(newProgress);
				if(newProgress==100){
					pb_progress.setVisibility(View.INVISIBLE);
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		// 载入网页
		webview_img.loadUrl(URLConfig.IMG_TXT_IP + goods_img_url);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
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

		}
	}

	class MyWebViwClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			webview_img.loadUrl(url);
			return true;
		}

	}

}
