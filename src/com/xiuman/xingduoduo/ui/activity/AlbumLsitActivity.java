package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.ImageGridAdapter;
import com.xiuman.xingduoduo.adapter.ImageGridAdapter.TextCallback;
import com.xiuman.xingduoduo.model.ImageItem;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.pic.AlbumHelper;
import com.xiuman.xingduoduo.util.pic.Bimp;
/**
 * @名称：AlbumLsitActivity.java
 * @描述：相册图片列表
 * @author danding
 * 2014-8-14
 */
public class AlbumLsitActivity extends Base2Activity {
	
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	/*--------------------组件------------------------------------*/
	//返回
	private Button btn_back;
	//选择完成
	private Button btn_sure;
	//标题栏 
	private TextView tv_title;
	//图片展示GridView
	private GridView gridView;
	
	;
	/*-------------------------------数据-------------------------------*/
	private List<ImageItem> dataList;
	
	/*---------------------------Adapter-------------------------------*/
	//Adapter
	private ImageGridAdapter adapter;
	AlbumHelper helper;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(AlbumLsitActivity.this, "最多选择9张图片", 400).show();
				break;

			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album_list);
		
		initData();
		findViewById();
		initUI();
		setListener();
	}
	@Override
	protected void initData() {
		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
	}

	@Override
	protected void findViewById() {
		btn_sure = (Button) findViewById(R.id.btn_common_right2);
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}

	@Override
	protected void initUI() {
		adapter = new ImageGridAdapter(this, dataList,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				btn_sure.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.notifyDataSetChanged();
			}

		});
	}

	@Override
	protected void setListener() {
		btn_sure.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}

				if (Bimp.act_bool) {
					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++) {
					if (Bimp.drr.size() < 9) {
						Bimp.drr.add(list.get(i));
					}
				}
				//完成选择(相册列表也关闭)
				setResult(2);
				finish();
			}

		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 返回键
			//取消选择(相册列表不关闭)
			setResult(3);
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
		}
		return true;
	}

}
