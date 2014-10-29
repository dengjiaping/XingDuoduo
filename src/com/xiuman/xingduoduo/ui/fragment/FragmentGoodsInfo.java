package com.xiuman.xingduoduo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.ui.activity.GoodsInfoDiscussActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;

/**
 * @名称：FragmentGoodsInfo.java
 * @描述：商品图文详情
 * @author danding
 * @时间 2014-10-13
 */
public class FragmentGoodsInfo extends BaseFragment {

	/*---------------------------------组件-----------------------------------*/
	// 父
	private GoodsInfoDiscussActivity activity;
	// 商品id
	private String goods_img_url = "";
	// WebView
	private WebView webview_img;
	// 网页载入进度
	private ProgressBar pb_progress;
	// 传递过来的商品图文连接为空时显示的布局
	private LinearLayout llyt_null_goods_img;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_info, container,
				false);
		initData();
		findViewById(view);
		initUI();
		setListener();
		return view;
	}

	@Override
	protected void initData() {
		activity = (GoodsInfoDiscussActivity) getActivity();
		goods_img_url = activity.getGoods_id();
	}

	@Override
	protected void findViewById(View view) {
		webview_img = (WebView) view.findViewById(R.id.webview_img);
		pb_progress = (ProgressBar) view.findViewById(R.id.pb_progress);
		llyt_null_goods_img = (LinearLayout) view
				.findViewById(R.id.llyt_goods_null_img);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initUI() {
		// 图片连接是否为空
		if (goods_img_url.equals("")) {
			llyt_null_goods_img.setVisibility(View.VISIBLE);
		} else {
			llyt_null_goods_img.setVisibility(View.INVISIBLE);
		}

		// WebView设置
		WebSettings ws = webview_img.getSettings();
		// 宽视图，默认false
		ws.setUseWideViewPort(false);
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
				if (newProgress == 100) {
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

	}

	class MyWebViwClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			webview_img.loadUrl(url);
			return true;
		}

	}
}
