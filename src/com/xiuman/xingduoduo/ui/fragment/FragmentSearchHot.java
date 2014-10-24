package com.xiuman.xingduoduo.ui.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.callback.TaskSearchKeyWordBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.SearchKeyWord;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.testdata.Test;
import com.xiuman.xingduoduo.ui.activity.SearchGoodsListActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;

/**
 * 
 * 名称：FragmentSearchHot.java 描述：热门搜索界面
 * 
 * @author danding
 * @version 2014-6-6
 */
public class FragmentSearchHot extends BaseFragment {

	/*---------------------------------组件----------------------------*/
	// ListView
	private ListView lv_search_hot;

	/*-------------------------------填充数据--------------------------*/
	// Adapter
	private SearchHotListViewAdapter adapter;

	// 请求关键字返回
	private ActionValue<SearchKeyWord> value = new ActionValue<SearchKeyWord>();
	// 关键字集合
	private ArrayList<SearchKeyWord> keywords = new ArrayList<SearchKeyWord>();

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功
				value = (ActionValue<SearchKeyWord>) msg.obj;
				if (value.isSuccess()) {
					keywords = value.getDatasource();
					adapter = new SearchHotListViewAdapter(getActivity(),
							keywords);
					lv_search_hot.setAdapter(adapter);
				}
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络(使用本地保存的数据)
				keywords = Test.getTestSearchHot();
				adapter = new SearchHotListViewAdapter(getActivity(), keywords);
				lv_search_hot.setAdapter(adapter);
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_hot, container,
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

	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById(View view) {
		lv_search_hot = (ListView) view.findViewById(R.id.lv_search_hot);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		// 请求关键字
		HttpUrlProvider.getIntance().getSearchKeyWords(getActivity(),
				new TaskSearchKeyWordBack(handler));
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		lv_search_hot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SearchKeyWord keyword = (SearchKeyWord) lv_search_hot
						.getItemAtPosition(position);
				// 打开搜索到的商品列表界面
				Intent intent_keyword = new Intent(getActivity(),
						SearchGoodsListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("keyword", keyword.getHotsearch());
				intent_keyword.putExtras(bundle);
				getActivity().startActivity(intent_keyword);
				getActivity().overridePendingTransition(
						R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);

			}
		});
	}

	/**
	 * 
	 * 名称：FragmentSearchHot.java 描述：热门搜索适配器
	 * 
	 * @author danding
	 * @version 2014-6-12
	 */
	class SearchHotListViewAdapter extends BaseAdapter {

		private Context context;
		// 热门搜索关键字集合
		private ArrayList<SearchKeyWord> keywords;

		/**
		 * 构造函数
		 * 
		 * @param context
		 * @param keywords
		 */
		public SearchHotListViewAdapter(Context context,
				ArrayList<SearchKeyWord> keywords) {
			super();
			this.context = context;
			this.keywords = keywords;
		}

		@Override
		public int getCount() {
			return keywords.size();
		}

		@Override
		public Object getItem(int position) {
			return keywords.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(context, R.layout.item_search_hot, null);
			TextView tv_top = (TextView) view.findViewById(R.id.tv_top);
			TextView tv_keyword = (TextView) view
					.findViewById(R.id.tv_search_hot_keyword);

			if (position == 0) {
				tv_top.setBackgroundResource(R.drawable.bg_search_hot1);
			} else if (position == 1) {
				tv_top.setBackgroundResource(R.drawable.bg_search_hot2);
			} else if (position == 2) {
				tv_top.setBackgroundResource(R.drawable.bg_search_hot3);
			} else {
				tv_top.setBackgroundResource(R.drawable.bg_search_hot4);
			}

			tv_top.setText(position + 1 + "");
			tv_keyword.setText(keywords.get(position).getHotsearch());

			return view;
		}

	}

}
