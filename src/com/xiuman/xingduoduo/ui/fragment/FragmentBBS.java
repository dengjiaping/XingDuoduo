package com.xiuman.xingduoduo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.BBSAdViewPagerAdapter;
import com.xiuman.xingduoduo.adapter.BBSPlateListViewAdapter;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.testdata.Test;
import com.xiuman.xingduoduo.ui.activity.BBSPlateActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.view.indicator.CirclePageIndicator;

/**
 * @名称：FragmentCommunication.java
 * @描述：交流界面
 * @author danding
 * @version 2014-6-3
 */
public class FragmentBBS extends BaseFragment {

	/*----------------------------------组件--------------------------------*/
	// ScrollView
	private ScrollView sv_bbs;
	// Viewpager
	private ViewPager viewpager_bbs_ad;
	// Indicator
	private CirclePageIndicator mIndicator;
	// ListView
	private ListView lv_bbs_plates;
	// 广告名
	private TextView tv_bbs_ad_name;

	/*-------------------------------Adapter--------------------------------*/

	// 广告Adapter
	private BBSAdViewPagerAdapter ad_adapter;
	// 板块Adapter
	private BBSPlateListViewAdapter plate_adapter;

	/*----------------------------数据--------------------------------------*/
	// 广告ImageView
	private List<ImageView> ad_ivs = new ArrayList<ImageView>();
	// 板块列表
	private ArrayList<BBSPlate> plates = new ArrayList<BBSPlate>();

	// ViewPager 循环播放---------------------------------------------------
	boolean cunhuan = false;
	private int page_id = 1;
	private Runnable switchTask = new Runnable() {
		public void run() {
			if (cunhuan) {
				viewpager_bbs_ad.setCurrentItem(page_id);
				page_id++;
				if (page_id >= 6) {
					page_id = 0;
				}
			}
			cunhuan = true;
			mHandler.postDelayed(switchTask, 3000);
		}
	};
	Handler mHandler = new Handler();

	/*------------------------------------ImageLoader---------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bbs,
				container, false);
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
		// 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
		options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.weiboitem_pic_loading) //
				// 在ImageView加载过程中显示图片
				.showImageOnLoading(R.drawable.bg_center_ad_loading)
				.showImageForEmptyUri(R.drawable.bg_center_ad_loading) // image连接地址为空时
				.showImageOnFail(R.drawable.bg_center_ad_loading) // image加载失败
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();

		// 测试数据，板块
		plates = Test.getBBSPlates();
	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById(View view) {
		tv_bbs_ad_name = (TextView) view.findViewById(R.id.tv_bbs_ad_name);
		viewpager_bbs_ad = (ViewPager) view.findViewById(R.id.viewpager_bbs_ad);
		mIndicator = (CirclePageIndicator) view
				.findViewById(R.id.indicator_bbs_ad);
		lv_bbs_plates = (ListView) view.findViewById(R.id.lv_bbs_plates);
		sv_bbs = (ScrollView) view.findViewById(R.id.sv_bbs);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		// 设置广告数据
		setAdData();

		// 设置板块
		plate_adapter = new BBSPlateListViewAdapter(getActivity(), plates);
		lv_bbs_plates.setAdapter(plate_adapter);
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		// 广告切换监听
		viewpager_bbs_ad
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						super.onPageSelected(position);
						tv_bbs_ad_name.setText(Test.addTestCommunicationAd()
								.get(position).getAd_content());
						mIndicator.setCurrentItem(position);
						page_id = position;
					}
				});
		lv_bbs_plates.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_bbs_plates.getItemAtPosition(position);
				if(obj instanceof BBSPlate){
					BBSPlate plate = (BBSPlate)obj;
					Intent intent = new Intent(getActivity(),BBSPlateActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("bbs_plate", plate);
					intent.putExtras(bundle);
					getActivity().startActivity(intent);
					getActivity().overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	/**
	 * 
	 * 描述：设置广告数据
	 */
	private void setAdData() {
		// 添加广告,测试数据，添加操作
		for (int i = 0; i < 6; i++) {
			ImageView iv_ad = new ImageView(getActivity());
			ad_ivs.add(iv_ad);
		}
		ad_adapter = new BBSAdViewPagerAdapter(Test.addTestCommunicationAd(),
				ad_ivs, getActivity(), options, imageLoader);
		viewpager_bbs_ad.setAdapter(ad_adapter);
		mIndicator.setViewPager(viewpager_bbs_ad);
		switchTask.run();
		tv_bbs_ad_name.setText(Test.addTestCommunicationAd().get(0)
				.getAd_content());
	}
}
