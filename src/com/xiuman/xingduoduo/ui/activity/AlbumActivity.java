package com.xiuman.xingduoduo.ui.activity;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.ImageBucketAdapter;
import com.xiuman.xingduoduo.model.ImageBucket;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.pic.AlbumHelper;

/**
 * @名称：AlbumActivity.java
 * @描述：相册列表
 * @author danding 2014-8-15
 */
public class AlbumActivity extends Base2Activity implements OnClickListener {

	/*--------------------------------组件-----------------------------*/
	// 返回
	private Button btn_back;
	// 右侧(隐藏)
	private Button btn_right;
	// 标题
	private TextView tv_title;
	// 相册列表
	private ListView lv_album;

	/*--------------------------------数据-----------------------------*/
	// 相册列表
	private List<ImageBucket> dataList;

	/*---------------------------------Adapter--------------------------*/
	private ImageBucketAdapter adapter;// 自定义的适配器
	// 相册工具类
	private AlbumHelper helper;

	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		dataList = helper.getImagesBucketList(false);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
	}

	@Override
	protected void findViewById() {
		lv_album = (ListView) findViewById(R.id.lv_album);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText("选择相册");

		adapter = new ImageBucketAdapter(this, dataList);
		lv_album.setAdapter(adapter);
	}

	@Override
	protected void setListener() {

		btn_back.setOnClickListener(this);
		lv_album.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/**
				 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
				 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
				 */
				/**
				 * 通知适配器，绑定的数据发生了改变，应当刷新视图
				 */
				Object obj = lv_album.getItemAtPosition(position);
				if(obj instanceof ImageBucket){
					ImageBucket bucket = (ImageBucket)obj;
					// adapter.notifyDataSetChanged();
					Intent intent = new Intent(AlbumActivity.this,
							AlbumLsitActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("bucket", bucket.bucketName);
					intent.putExtras(bundle);
					intent.putExtra(AlbumActivity.EXTRA_IMAGE_LIST,
							(Serializable) dataList.get(position).imageList);
					startActivityForResult(intent, 5);
				}
				
				
			}

		});

	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:
			finish();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 5) {
			if (resultCode == 2) {
				finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
