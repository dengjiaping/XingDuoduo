package com.xiuman.xingduoduo.ui.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import u.aly.T;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskUserInfoBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ImageCropUtils;
import com.xiuman.xingduoduo.util.PostSimulation;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.CircleImageView;
import com.xiuman.xingduoduo.view.CustomDialog;

/**
 * 
 * @名称：UserInfoActivity.java
 * @描述：个人信息界面
 * @author danding
 * @version
 * @date：2014-6-19
 */
public class UserInfoActivity extends Base2Activity implements OnClickListener {

	/*---------------------------------组件----------------------------*/
	// 提交用户资料
	private Button btn_userinfo_submit;
	// 返回
	private Button btn_back;
	// 用户头像
	private RelativeLayout rlyt_userinfo_user_head;
	// 用户头像
	private CircleImageView iv_userinfo_user_head;
	// 会员信息
	private TextView tv_userinfo_user_rank;
	// 会员名
	private TextView tv_userinfo_user_name;
	// 性别
	private RelativeLayout rlyt_userinfo_user_sex;
	// 性别
	private TextView tv_userinfo_user_sex;
	// 修改密码
	private Button btn_userinfo_user_update_psw;
	// 修改个人信息
	private Button btn_userinfo_user_update_info;
	// 收货地址
	private Button btn_userinfo_user_address;
	// 推出登陆
	private Button btn_userinfo_exit;
	// 退出登录对话框
	private CustomDialog dialog_exit;

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
	private String userId;

	/*-----------------------ImageLoader-----------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;
	/*---------------------------------数据变量-----------------------------------*/
	// 获取头像工具类
	private ImageCropUtils cropUtils;
	// 用户头像(Bitmap)
	private Bitmap user_head_bitmap;

	// ----------------当前登录用户-----------------------
	private User user;
	private ActionValue<?> value = new ActionValue<T>();
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.UPLOAD_PORAIT_RUN:
				String result = (String) msg.obj;
				if (result != null) {
					value = new Gson().fromJson(result,
							ActionValue.class);
					if (value.isSuccess()) {

						ToastUtil.ToastView(getApplication(), "头像已经上传");
						getUserInfo();
					} else {
						ToastUtil.ToastView(getApplication(), "头像上传失败，请重试！");
					}
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		initData();
		findViewById();
		initUI();
		setListener();

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.weiboitem_pic_loading) //
		// 在ImageView加载过程中显示图片
				.showImageForEmptyUri(R.drawable.bg_head) // image连接地址为空时
				.showImageOnFail(R.drawable.bg_head) // image加载失败
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();
		cropUtils = new ImageCropUtils(this);

	}

	@Override
	protected void findViewById() {
		btn_userinfo_submit = (Button) findViewById(R.id.btn_userinfo_submit);
		btn_back = (Button) findViewById(R.id.btn_back_user_info);
		rlyt_userinfo_user_head = (RelativeLayout) findViewById(R.id.rlyt_userinfo_user_head);
		iv_userinfo_user_head = (CircleImageView) findViewById(R.id.iv_userinfo_user_head);
		tv_userinfo_user_rank = (TextView) findViewById(R.id.tv_userinfo_user_rank);
		tv_userinfo_user_name = (TextView) findViewById(R.id.tv_userinfo_user_name);
		tv_userinfo_user_sex = (TextView) findViewById(R.id.tv_userinfo_user_sex);
		rlyt_userinfo_user_sex = (RelativeLayout) findViewById(R.id.rlyt_userinfo_user_sex);
		btn_userinfo_user_update_psw = (Button) findViewById(R.id.btn_userinfo_user_update_psw);
		btn_userinfo_user_address = (Button) findViewById(R.id.btn_userinfo_user_address);
		btn_userinfo_user_update_info = (Button) findViewById(R.id.btn_userinfo_user_update_info);
		btn_userinfo_exit = (Button) findViewById(R.id.btn_userinfo_exit);
	}

	@Override
	protected void initUI() {
		btn_userinfo_submit.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void setListener() {
		btn_userinfo_submit.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		rlyt_userinfo_user_head.setOnClickListener(this);
		rlyt_userinfo_user_sex.setOnClickListener(this);
		btn_userinfo_user_address.setOnClickListener(this);
		btn_userinfo_user_update_psw.setOnClickListener(this);
		btn_userinfo_exit.setOnClickListener(this);
		btn_userinfo_user_update_info.setOnClickListener(this);
	}

	/**
	 * @描述：加载用户信息 2014-8-12
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (MyApplication.getInstance().isUserLogin()) {
			user = MyApplication.getInstance().getUserInfo();
			userId = user.getUserId();
			initUserInfo(user);
		}

	}

	/**
	 * @描述：加载用户信息
	 * @param user
	 *            2014-8-12
	 */
	private void initUserInfo(User user) {
		// 头像文件如果存在本地，则从本地获取
		File head = new File(cropUtils.createDirectory()
				+ cropUtils.createNewPhotoName());
		if (head.exists()) {
			user_head_bitmap = BitmapFactory.decodeFile(cropUtils
					.createDirectory() + cropUtils.createNewPhotoName());
			iv_userinfo_user_head.setImageBitmap(user_head_bitmap);
		} else if (user.getHead_image() != null) {
			imageLoader.displayImage(URLConfig.IMG_IP + user.getHead_image(),
					iv_userinfo_user_head, options);
		}
		// 测试数据
		tv_userinfo_user_rank.setText(user.getRankNmae());
		tv_userinfo_user_name.setText(user.getNickname());
		String user_sex = "";
		user_sex = user.getGender();
		tv_userinfo_user_sex.setText(user_sex);
		if (user_sex == null) {
			tv_userinfo_user_sex.setText("保密");
		} else if (user_sex.equals("male")) {
			tv_userinfo_user_sex.setText("男");
		} else if (user_sex.equals("female")) {
			tv_userinfo_user_sex.setText("女");
		}
	}

	/**
	 * 
	 * 描述：点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back_user_info:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;
		case R.id.rlyt_userinfo_user_head:// 用户头像
			showPop(rlyt_userinfo_user_head);
			break;
		case R.id.btn_userinfo_user_address:// 收货地址
			Intent intent = new Intent(UserInfoActivity.this,
					UserAddressManagerActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("inFlag", false);
			intent.putExtras(bundle);

			startActivity(intent);
			overridePendingTransition(R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.btn_pop_photo_album:// 从相册选择
			dismissPop();
			cropUtils
					.openGalleryAndCropSmallPhoto(AppConfig.CUT_GALLERY_RESULT);
			break;
		case R.id.btn_pop_photo_camera:// 拍照
			dismissPop();
			cropUtils.openCamera(AppConfig.OPEN_CAMERA);
			break;
		case R.id.btn_pop_photo_cancel:// 取消
			dismissPop();
			break;
		case R.id.btn_userinfo_submit:// 提交用户信息
			Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT)
					.show();
			Intent intent_submit = new Intent();
			Bundle bundle_submit = new Bundle();

			bundle_submit.putSerializable("user", user);
			intent_submit.putExtras(bundle_submit);
			setResult(AppConfig.UPDATE_USER_INFO, intent_submit);
			finish();
			break;
		case R.id.btn_userinfo_user_update_psw:// 修改密码
			Intent intent_psw = new Intent(this, UpdateUserPswActivity.class);
			Bundle bundle_psw = new Bundle();
			bundle_psw.putSerializable("user", user);
			intent_psw.putExtras(bundle_psw);
			startActivity(intent_psw);
			overridePendingTransition(R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.btn_userinfo_exit:// 退出登陆(删除用户信息)
			showExitDialog();
			break;
		case R.id.btn_userinfo_user_update_info:// 修改个人信息
			Intent intent_info = new Intent(UserInfoActivity.this,
					UpdateUserInfoActivity.class);
			Bundle bundle_info = new Bundle();
			bundle_info.putSerializable("user", user);
			intent_info.putExtras(bundle_info);
			startActivityForResult(intent_info, AppConfig.REQUEST_CODE);
			overridePendingTransition(R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		}
	}

	/**
	 * 对返回的照片进行处理(后面要加上上传操作)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case AppConfig.OPEN_CAMERA:// 从相机

			cropUtils.cropBigPhotoByCamera(AppConfig.CUT_CAMERA_RESULT);

			break;

		case AppConfig.CUT_CAMERA_RESULT:// 返回记过

			user_head_bitmap = cropUtils.getBitmapByUri();
			iv_userinfo_user_head.setImageBitmap(user_head_bitmap);
			// 保存头像文件到本地
			try {
				cropUtils.saveFile(user_head_bitmap,
						cropUtils.createNewPhotoName());

				// 上传头像
				uploadImg();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case AppConfig.CUT_GALLERY_RESULT:

			if (data != null) {
				user_head_bitmap = cropUtils.getBitmapByIntent(data);
				iv_userinfo_user_head.setImageBitmap(user_head_bitmap);
				// 保存文件到本地
				try {
					cropUtils.saveFile(user_head_bitmap,
							cropUtils.createNewPhotoName());
					// 上传头像
					uploadImg();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			break;
		case AppConfig.UPDATE_USER_ADDRESS:// 更新用户收货地址信息
			break;

		case AppConfig.RESULT_CODE_OK:
			user = (User) data.getExtras().getSerializable("user");
			initUserInfo(user);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
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
		pop.showAtLocation(view, Gravity.TOP, 0, (int) (MyApplication
				.getInstance().getScreenHeight() * 3 / 4));
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

	/**
	 * @描述：显示退出登录对话框 2014-8-12
	 */
	private void showExitDialog() {

		dialog_exit = new CustomDialog(this, "退出登录", "确定退出？退出后将清空用户数据！");
		dialog_exit.show();
		dialog_exit.btn_custom_dialog_sure
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						MyApplication.getInstance().deleteUserInfo();
						dialog_exit.dismiss();
						finish();
					}
				});
		dialog_exit.btn_custom_dialog_cancel
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog_exit.dismiss();
					}
				});
	}

	/**
	 * @描述：上传图片 2014-9-26
	 */
	protected void uploadImg() {
		new Thread() {
			public void run() {
				Message msg = new Message();
				msg.what = AppConfig.UPLOAD_PORAIT_RUN;
				msg.obj = uploadFile();
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * @描述：进行上传操作
	 * @return 2014-9-29
	 */
	protected String uploadFile() {
		List<String> keys = new ArrayList<String>();
		keys.add("usernameIdhead");
		Map<String, String> map = new HashMap<String, String>();
		map.put("usernameIdhead", userId);

		List<String> fileNames = new ArrayList<String>();
		fileNames.add(cropUtils.createDirectory()
				+ cropUtils.createNewPhotoName());

		String filename = cropUtils.createDirectory()
				+ cropUtils.createNewPhotoName();
		return PostSimulation.getInstance().postHead(
				URLConfig.BASE_IP + URLConfig.MY_HEAD_PHOTO_IP, "myFile",
				filename, "usernameIdhead", userId);
		// return Upload.upload(URLConfig.BASE_IP + URLConfig.MY_HEAD_PHOTO_IP,
		// filename, "usernameIdhead", userId);
	}

	/**
	 * @描述：获取用户信息 2014-8-12
	 */
	public void getUserInfo() {
		HttpUrlProvider.getIntance().getUserInfo(UserInfoActivity.this,
				new TaskUserInfoBack(handler), URLConfig.USERINFO,
				user.getUserName());
	}
}
