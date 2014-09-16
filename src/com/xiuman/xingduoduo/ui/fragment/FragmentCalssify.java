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
import android.widget.Button;
import android.widget.GridView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.ClassifyGridviewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskClassifyBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.Classify;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.activity.ClassifyActivity;
import com.xiuman.xingduoduo.ui.activity.SearchActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * 
 * @名称：FragmentCalssify.java
 * @描述：商品分类界面
 * @author danding
 * @version 2014-6-3
 */
public class FragmentCalssify extends BaseFragment implements OnClickListener {

	/*--------------------------------------组件---------------------------------*/
	//搜索按钮
	private Button btn_search;
	//分类
	private GridView gridview_good_classify;
	// 进度加载
	private LoadingDialog loadingdialog;

	/*--------------------------------------adapter------------------------------*/
	private ClassifyGridviewAdapter classify_adapter;

	/*--------------------------------------数据-------------------------*/
	//无网络时读取配置文件里的分类Id
	private String[] classifyes_ids;
	//分类名
	private String[] classifyes_names;
	//网络连接情况
	private boolean isNet = true;
	// 请求接口返回的数据
	private ActionValue<Classify> value;
	// 返回的分类列表
	private ArrayList<Classify> classies = new ArrayList<Classify>();
	// Handler
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功
				loadingdialog.dismiss();
				value = (ActionValue<Classify>) msg.obj;
				classies = (ArrayList<Classify>) value.getDatasource();
				classify_adapter = new ClassifyGridviewAdapter(
						getActivity());
				gridview_good_classify.setAdapter(classify_adapter);
				isNet = true;
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络(使用本地保存的数据)
				loadingdialog.dismiss();
				classify_adapter = new ClassifyGridviewAdapter(
						getActivity());
				gridview_good_classify.setAdapter(classify_adapter);
				isNet = false;
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
	@Override
	protected void initData() {
		classifyes_ids = getResources().getStringArray(R.array.classify_id);
		classifyes_names = getResources().getStringArray(R.array.classify_name);
	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById(View view) {
		loadingdialog = new LoadingDialog(getActivity());
		gridview_good_classify = (GridView) view
				.findViewById(R.id.gridview_good_classify);
		btn_search = (Button) view.findViewById(R.id.btn_search);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		initClassify();
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		btn_search.setOnClickListener(this);

		/**
		 * 分类点击
		 */
		gridview_good_classify
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String classify_name = classifyes_names[position];
						if(isNet){
							Classify classify = classies.get(position);
							String classify_id = classify.getCategoryId();

							Intent intent = new Intent(getActivity(),
									ClassifyActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("classify_id", classify_id);
							bundle.putString("classify_name", classify_name);
							intent.putExtras(bundle);
							getActivity().startActivity(intent);
							getActivity().overridePendingTransition(
									R.anim.translate_horizontal_start_in,
									R.anim.translate_horizontal_start_out);
						}else{
							String classify_id = classifyes_ids[position];
							Intent intent = new Intent(getActivity(),
									ClassifyActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("classify_id", classify_id);
							bundle.putString("classify_name", classify_name);
							intent.putExtras(bundle);
							getActivity().startActivity(intent);
							getActivity().overridePendingTransition(
									R.anim.translate_horizontal_start_in,
									R.anim.translate_horizontal_start_out);
						}
						
						Object obj = gridview_good_classify
								.getItemAtPosition(position);
						if (obj instanceof Classify) {
							
						}
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
		loadingdialog.show();
	}
	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search://搜索
			Intent intent_search = new Intent(getActivity(),SearchActivity.class);
			startActivity(intent_search);
			getActivity().overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
			break;

		default:
			break;
		}
	}

}
