package com.xiuman.xingduoduo.ui.fragment;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.umeng.analytics.MobclickAgent;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.ClassifyGridviewAdapter;
import com.xiuman.xingduoduo.adapter.ClassifyListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskClassifyBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BClassify;
import com.xiuman.xingduoduo.model.Classify;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.activity.ClassifyActivity;
import com.xiuman.xingduoduo.ui.activity.SearchActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.util.SizeUtil;

/**
 * @名称：FragmentCalssify.java
 * @描述：商品分类界面
 * @author danding
 * @version 2014-6-3
 */
public class FragmentCalssify extends BaseFragment implements OnClickListener {

	/*--------------------------------------组件---------------------------------*/
	// 搜索按钮
	private EditText et_search_input_keyword;
	// 分类
	private GridView gridview_good_classify;
	// 一级分类
	private ListView lv_goods_classify;
	// ImageView indicator
	private ImageView iv_classify_indicator;

	/*--------------------------------------adapter------------------------------*/
	// 一级分类Adapter
	private ClassifyListViewAdapter adapter_aCategory;
	// 二级分类
	private ClassifyGridviewAdapter classify_adapter;
	// 二级分类宽度
	private int bcWidth = 0;

	/*-------------------------------------ImageLoader-------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*--------------------------------------数据-------------------------*/
	// 请求接口返回的数据
	private ActionValue<Classify> value = new ActionValue<Classify>();
	// 返回的分类列表
	private ArrayList<Classify> classies = new ArrayList<Classify>();
	// 当前限时的二级分类数据
	private ArrayList<BClassify> b_classies = new ArrayList<BClassify>();
	// Handler
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功
				value = (ActionValue<Classify>) msg.obj;
				if (value.isSuccess()) {
					MyApplication.getInstance().saveCategory(value);
					// 获取一级分类
					classies = (ArrayList<Classify>) value.getDatasource();
					setData(classies);
				}
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络(使用本地保存的数据)
				value = MyApplication.getInstance().getCategory();
				if (value != null) {
					// 获取一级分类
					classies = (ArrayList<Classify>) value.getDatasource();
					setData(classies);
				}
				break;
			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_calssify, container,
				false);
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
		options = new DisplayImageOptions.Builder()
				// 在ImageView加载过程中显示图片
				.showImageOnLoading(R.drawable.onloading)
				.showImageForEmptyUri(R.drawable.onloading)
				// image连接地址为空时
				.showImageOnFail(R.drawable.onloading)
				// image加载失败
				.cacheInMemory(false)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true)
				// 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.NONE).build();
		bcWidth = (int) (MyApplication.getInstance().getScreenWidth() / 4.0 * 2.9 - SizeUtil
				.dip2px(getActivity(), 30)) / 2;

	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById(View view) {
		lv_goods_classify = (ListView) view
				.findViewById(R.id.lv_goods_classify);
		iv_classify_indicator = (ImageView) view
				.findViewById(R.id.iv_classify_indicator);
		gridview_good_classify = (GridView) view
				.findViewById(R.id.gridview_good_classify);
		et_search_input_keyword = (EditText) view.findViewById(R.id.et_search_input_keyword);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		//加载上次保存的数据
		value = MyApplication.getInstance().getCategory();
		if (value != null) {
			// 获取一级分类
			classies = (ArrayList<Classify>) value.getDatasource();
			setData(classies);
		}
		
		//请求数据
		initClassify();

	}

	/**
	 * @描述：设置数据
	 * @时间 2014-10-23
	 */
	private void setData(ArrayList<Classify> classies) {
		// 一级分类
		adapter_aCategory = new ClassifyListViewAdapter(getActivity(), options,
				imageLoader, classies);
		lv_goods_classify.setAdapter(adapter_aCategory);
		initLayout(lv_goods_classify);
		// 设置滑块的初始位置
		setIndicatorLocation();

		// 初始二级分类
		b_classies = classies.get(0).getGoodsCategoryparams();
		classify_adapter = new ClassifyGridviewAdapter(options, imageLoader,
				b_classies, getActivity(), bcWidth);
		gridview_good_classify.setAdapter(classify_adapter);
		//统计第一个一级分类
		MobclickAgent.onEvent(getActivity(), "Category_"+1);
	}

	/**
	 * @描述：设置滑块初始位置
	 * @时间 2014-10-23
	 */
	private void setIndicatorLocation() {
		double currentWdith = MyApplication.getInstance().getScreenWidth() / 4
				* 1.1 - SizeUtil.dip2px(getActivity(), 15);
		int currentHeight = (int) (currentWdith / 128 * 100);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_classify_indicator);
		int width = bmp.getHeight();
		bmp.recycle();
		iv_classify_indicator.setY(currentHeight / 2 - width / 2);
		lv_goods_classify.setSelection(0);
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		et_search_input_keyword.setOnClickListener(this);
		/**
		 * 分类点击
		 */
		gridview_good_classify
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Object obj = gridview_good_classify
								.getItemAtPosition(position);
						if (obj instanceof BClassify) {
							BClassify bc_classify = (BClassify) obj;
							Intent intent = new Intent(getActivity(),
									ClassifyActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("classify_id",
									bc_classify.getCategoryId());
							bundle.putString("classify_name",
									bc_classify.getCategoryName());
							intent.putExtras(bundle);
							getActivity().startActivity(intent);
							getActivity().overridePendingTransition(
									R.anim.translate_horizontal_start_in,
									R.anim.translate_horizontal_start_out);
						}
					}

				});
		// 一级分类点击事件
		lv_goods_classify.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				b_classies = classies.get(position).getGoodsCategoryparams();
				classify_adapter = new ClassifyGridviewAdapter(options,
						imageLoader, b_classies, getActivity(), bcWidth);
				gridview_good_classify.setAdapter(classify_adapter);
				startSildingInAnimation(position);
				//统计第一个一级分类
				MobclickAgent.onEvent(getActivity(), "Category_"+(position+1));

			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * @描述：请求分类数据 2014-8-13
	 */
	private void initClassify() {
		HttpUrlProvider.getIntance().getClassify(getActivity(),
				new TaskClassifyBack(handler), URLConfig.CLASSIFY);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.et_search_input_keyword:// 搜索
			Intent intent_search = new Intent(getActivity(),
					SearchActivity.class);
			startActivity(intent_search);
			getActivity().overridePendingTransition(
					R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;

		default:
			break;
		}
	}

	/**
	 * @描述：
	 * @param leftView
	 * @时间 2014-10-16
	 */
	public void initLayout(final ListView leftView) {
		leftView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if (leftView.getChildAt(0) != null) {
					int deltaY = tempScrollY - getScroll();
					tempScrollY = getScroll();
					iv_classify_indicator.setY(iv_classify_indicator.getY()
							+ deltaY);
				}
			}

		});

	}

	// 设置一级分类ListView的高度
	private Dictionary<Integer, Integer> listViewItemHeights = new Hashtable<Integer, Integer>();
	// 过度
	private int tempScrollY;

	/**
	 * @描述：获取滑动距离
	 * @return
	 * @时间 2014-10-16
	 */
	private int getScroll() {
		View c = lv_goods_classify.getChildAt(0);
		int scrollY = -c.getTop();
		listViewItemHeights.put(lv_goods_classify.getFirstVisiblePosition(),
				c.getHeight());
		for (int i = 0; i < lv_goods_classify.getFirstVisiblePosition(); ++i) {
			if (listViewItemHeights.get(i) != null)
				scrollY += listViewItemHeights.get(i);

		}
		return scrollY;
	}

	/**
	 * @描述：设置指示器的位置
	 * @param position
	 * @时间 2014-10-16
	 */
	public void startSildingInAnimation(int position) {
		int translate = lv_goods_classify.getChildAt(
				position - lv_goods_classify.getFirstVisiblePosition())
				.getTop()
				+ lv_goods_classify.getChildAt(
						position - lv_goods_classify.getFirstVisiblePosition())
						.getMeasuredHeight()
				/ 2
				- iv_classify_indicator.getHeight() / 2;
		iv_classify_indicator.setY(translate);
	}
}
