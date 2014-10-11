package com.xiuman.xingduoduo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.db.dao.SearchRecentlyDao;
import com.xiuman.xingduoduo.ui.activity.SearchGoodsListActivity;
import com.xiuman.xingduoduo.ui.base.BaseFragment;

/**
 * 
 * 名称：FragmentSearchRecently.java 描述：最近搜索界面
 * 
 * @author danding
 * @version 2014-6-6
 */
public class FragmentSearchRecently extends BaseFragment {

	/*-----------------------------------组件-------------------------------*/
	// ListView
	private ListView lv_search_recently;
	// 搜索历史为空的时候显示的部分
	private LinearLayout llyt_search_null;

	/*----------------------------------数据库------------------------------*/
	// 数据库
	private SearchRecentlyDao recently_dao;

	/*----------------------------------数据填充-----------------------------*/
	// Adapter
	private SearchRecentlyListViewAdapter adapter;

	/**
	 * Message消息处理
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AppConfig.SEARCH_ADD:// 添加搜索历史

				break;
			case AppConfig.SEARCH_DELETE:// 删除搜索历史
				adapter.notifyDataSetChanged();
				showIsNull();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_recently,
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
	@Override
	protected void initData() {
		// 数据库
		recently_dao = new SearchRecentlyDao(getActivity());
	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById(View view) {
		lv_search_recently = (ListView) view
				.findViewById(R.id.lv_search_recently);
		llyt_search_null = (LinearLayout) view
				.findViewById(R.id.llyt_search_null);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		adapter = new SearchRecentlyListViewAdapter(recently_dao,
				getActivity(), handler);
		lv_search_recently.setAdapter(adapter);

		showIsNull();
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		lv_search_recently.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String keyword = (String) lv_search_recently.getItemAtPosition(position);
				//打开搜索到的商品列表界面
				Intent intent_keyword = new Intent(getActivity(),SearchGoodsListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("keyword", keyword);
				intent_keyword.putExtras(bundle);
				getActivity().startActivity(intent_keyword);
				getActivity().overridePendingTransition(R.anim.translate_horizontal_start_in, R.anim.translate_horizontal_start_out);
			}
		});
	}

	/**
	 * 重新载入
	 */
	@Override
	public void onResume() {
		super.onResume();
		showIsNull();
	}

	/**
	 * 
	 * 描述：根据数据判断是否显示搜索历史为空的标记
	 */
	private void showIsNull() {
		if (recently_dao.isNull()) {// 判断是否为空
			llyt_search_null.setVisibility(View.VISIBLE);
		} else {
			llyt_search_null.setVisibility(View.INVISIBLE);
		}
	}
	/**
	 * 
	 * @描述：添加搜索关键字到数据库
	 * @date：2014-6-23
	 * @param keyword
	 */
	public void add2DB(String keyword){
		recently_dao.insert(keyword);
//		adapter.notifyDataSetChanged();
		adapter = new SearchRecentlyListViewAdapter(recently_dao, getActivity(), handler);
		lv_search_recently.setAdapter(adapter);
		showIsNull();
	}
	
	
	/**
	 * 
	 * 名称：FragmentSearchRecently.java 描述：最近搜索适配器
	 * 
	 * @author danding
	 * @version 2014-6-12
	 */
	class SearchRecentlyListViewAdapter extends BaseAdapter {
		// 关键字集合
		private List<String> keywords = new ArrayList<String>();
		// 数据库操作
		private SearchRecentlyDao recently_dao;
		private Context context;
		private Handler handler;

		/**
		 * 构造函数
		 * 
		 * @param recently_dao
		 * @param context
		 */
		public SearchRecentlyListViewAdapter(SearchRecentlyDao recently_dao,
				Context context, Handler handler) {
			super();
			this.handler = handler;
			this.recently_dao = recently_dao;
			this.context = context;
			//暂时注释
			keywords = recently_dao.getKeywords();
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

		/**
		 * 控件少，就不用ViewHolder了
		 */
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = View.inflate(context, R.layout.item_search_recently,
					null);
			TextView tv_search_work = (TextView) view
					.findViewById(R.id.tv_search_recently_keyword);
			Button btn_delete_keyword = (Button) view
					.findViewById(R.id.btn_delete_keyword);

			tv_search_work.setText(keywords.get(position));
			/**
			 * 删除搜索历史
			 */
			btn_delete_keyword.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 删除数据库这的数据
					recently_dao.delete(keywords.get(position));

					keywords.remove(position);

					Message msg = Message.obtain();
					msg.what = AppConfig.SEARCH_DELETE;
					handler.sendMessage(msg);
				}
			});

			return view;
		}

	}

}
