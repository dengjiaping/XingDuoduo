package com.xiuman.xingduoduo.ui.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.pic.Bimp;
import com.xiuman.xingduoduo.util.pic.FileUtils;

/**
 * @名称：PostPublicActivity.java
 * @描述：发布帖子界面
 * @author danding 2014-8-11
 */
public class PostPublishActivity extends Base2Activity implements
		OnClickListener {

	/*----------------------------------------组件--------------------------------------*/
	// 返回
	private Button btn_back;
	// 提交
	private Button btn_publish;
	// 标题（界面）
	private TextView tv_title;
	// 标题（帖子）
	private EditText et_post_title;
	// 内容（帖子）
	private EditText et_post_content;
	// 图片列表
	private GridView gv_post_imgs;

	/*---------PopWindow----------*/
	// 头像选择Pop
	private PopupWindow pop;
	// 头像选择View
	private View popview;
	// 相册选择
	private Button btn_pop_photo_album;
	// 拍照
	private Button btn_pop_photo_camera;
	// 取消
	private Button btn_pop_photo_cancel;
	// 屏幕宽高
	private int screenWidth, screenHeight;

	/*----------------------------------------数据--------------------------------------*/
	// 从上级界面接收到的板块id(要将帖子发布在哪个板块)
	private String plate_id;
	// 选择的图片列表
	private ArrayList<String> post_imgs = new ArrayList<String>();

	/*----------------------------------------adapter-----------------------------------*/
	// 添加图片的Adapter
	private GridAdapter adapter;

	// 图片名
	public String name;

	// 存储路径
	private static final String PATH = Environment
			.getExternalStorageDirectory() + "/DCIM/xingduoduo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_publish);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {
		// 获取屏幕宽高
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 获取屏幕宽度
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_common_back2);
		btn_publish = (Button) findViewById(R.id.btn_common_right2);
		tv_title = (TextView) findViewById(R.id.tv_common_title2);
		et_post_title = (EditText) findViewById(R.id.et_post_title);
		et_post_content = (EditText) findViewById(R.id.et_post_content);
		gv_post_imgs = (GridView) findViewById(R.id.gv_post_imgs);
	}

	@Override
	protected void initUI() {
		tv_title.setText("发表帖子");
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_publish.setOnClickListener(this);
		gv_post_imgs.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					showPop(et_post_content);
				} else {
					Intent intent = new Intent(PostPublishActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
	}

	/**
	 * 显示popupwindow
	 * 
	 * @param view
	 */
	private void showPop(View view) {
		if (pop == null) {
			popview = View.inflate(this, R.layout.pop_photo_user_head, null);
			pop = new PopupWindow(popview, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
		}

		btn_pop_photo_album = (Button) popview
				.findViewById(R.id.btn_pop_photo_album);
		btn_pop_photo_camera = (Button) popview
				.findViewById(R.id.btn_pop_photo_camera);
		btn_pop_photo_cancel = (Button) popview
				.findViewById(R.id.btn_pop_photo_cancel);

		btn_pop_photo_album.setOnClickListener(this);
		btn_pop_photo_camera.setOnClickListener(this);
		btn_pop_photo_cancel.setOnClickListener(this);

		// 使其聚焦
		pop.setFocusable(true);
		// 设置允许在外点击消失
		pop.setOutsideTouchable(true);
		// 给pop设置背景
		Drawable background = new ColorDrawable(Color.TRANSPARENT);
		pop.setBackgroundDrawable(background);
		// 设置pop动画
		pop.setAnimationStyle(R.style.PopupAnimation);
		// 设置pop 的位置
		pop.showAtLocation(view, Gravity.TOP, 0, screenHeight);
	}

	/**
	 * 
	 * @描述：隐藏pop
	 * @date：2014-6-19
	 */
	private void dismissPop() {
		if (pop != null) {
			pop.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pop_photo_album:// 从相册选择
			Intent intent = new Intent(PostPublishActivity.this,
					AlbumActivity.class);
			startActivity(intent);
			dismissPop();
			break;
		case R.id.btn_pop_photo_camera:// 拍照
			selectPhoto();
			dismissPop();
			break;
		case R.id.btn_pop_photo_cancel:// 取消
			dismissPop();
			break;
		}

	}

	/**
	 * @名称：PostPublishActivity.java
	 * @描述：图片显示Adapter
	 * @author danding 2014-8-14
	 */
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		/**
		 * @名称：PostPublishActivity.java
		 * @描述：优化类
		 * @author danding 2014-8-14
		 */
		public class ViewHolder {
			public ImageView image;
		}

		// 消息处理
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		/**
		 * @描述：加载图片 2014-8-14
		 */
		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter = new GridAdapter(this);
		adapter.update();
		gv_post_imgs.setAdapter(adapter);
	}

	@Override
	protected void onRestart() {
//		adapter.update();
//		adapter.notifyDataSetChanged();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 1;
	private String path = "";

	/**
	 * @描述：选择照片 2014-8-14
	 */
	public void selectPhoto() {
//		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		File file = new File(Environment.getExternalStorageDirectory()
//				+ "/myimage/", String.valueOf(System.currentTimeMillis())
//				+ ".jpg");
//		path = file.getPath();
//		Uri imageUri = Uri.fromFile(new File(Environment
//				.getExternalStorageDirectory() + "/myimage/", String
//				.valueOf(System.currentTimeMillis()) + ".jpg"));
//		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		startActivityForResult(openCameraIntent, TAKE_PICTURE);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用系统相机
		new DateFormat();
		name = DateFormat.format("yyyyMMdd_hhmmss",
				Calendar.getInstance(Locale.CHINA))
				+ ".jpg";
		Uri imageUri = Uri.fromFile(new File(PATH, name));
		path = new File(PATH,name).getPath();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

		startActivityForResult(intent, TAKE_PICTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				if (Bimp.drr.size() < 9 && resultCode == -1) {
					Bimp.drr.add(path);
				}
				break;
			}
		}
	}

}
