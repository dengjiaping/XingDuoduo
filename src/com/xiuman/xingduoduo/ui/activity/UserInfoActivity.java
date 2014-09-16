package com.xiuman.xingduoduo.ui.activity;

import java.io.File;
import java.io.IOException;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ImageCropUtils;
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
public class UserInfoActivity extends Base2Activity implements OnClickListener,
		OnCheckedChangeListener {

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
	// 注册时间
	private TextView tv_userinfo_user_createdate;
	// 性别
	private RelativeLayout rlyt_userinfo_user_sex;
	// 性别
	private TextView tv_userinfo_user_sex;
	// 邮箱
	private RelativeLayout rlyt_userinfo_user_email;
	// 邮箱地址
	private TextView tv_userinfo_user_email;
	// 修改密码
	private Button btn_userinfo_user_update;
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

	/*--------------------------性别--------------------------*/
	// 组
	private RadioGroup sex_type_group;
	// 男女
	private RadioButton rbtn_userinfo_user_sex_male,
			rbtn_userinfo_user_sex_female;
	// 取消按钮
	private Button btn_userinfo_user_sex_cancel;

	/*---------------------------------数据变量-----------------------------------*/
	// 屏幕宽高
	private int screenWidth, screenHeight;
	// 获取头像工具类
	private ImageCropUtils cropUtils;
	// 用户头像(Bitmap)
	private Bitmap user_head_bitmap;
	// 性别选择Dialog
	private Dialog dialog;
	// 当前性别
	private String sex;

	// ----------------当前登录用户-----------------------
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
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
		rlyt_userinfo_user_email = (RelativeLayout) findViewById(R.id.rlyt_userinfo_user_email);
		tv_userinfo_user_email = (TextView) findViewById(R.id.tv_userinfo_user_email);
		btn_userinfo_user_update = (Button) findViewById(R.id.btn_userinfo_user_update);
		btn_userinfo_user_address = (Button) findViewById(R.id.btn_userinfo_user_address);
		tv_userinfo_user_createdate = (TextView) findViewById(R.id.tv_userinfo_user_createdate);
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
		rlyt_userinfo_user_email.setOnClickListener(this);
		btn_userinfo_user_update.setOnClickListener(this);
		btn_userinfo_exit.setOnClickListener(this);
	}

	/**
	 * @描述：加载用户信息 2014-8-12
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (MyApplication.getInstance().isUserLogin()) {
			user = MyApplication.getInstance().getUserInfo();
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
		}

		// 测试数据
		tv_userinfo_user_rank.setText(user.getRankNmae());
		tv_userinfo_user_name.setText(user.getUserName());
		tv_userinfo_user_sex.setText("保密");
		tv_userinfo_user_email.setText(user.getEmail());
		tv_userinfo_user_createdate.setText(user.getCreateDate());
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
		case R.id.rlyt_userinfo_user_sex:// 性别
			showDialog();
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
		case R.id.btn_user_info_user_sex_cancel:// 取消Dialog
			dialog.dismiss();
			break;
		case R.id.btn_userinfo_submit:// 提交用户信息
			Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT)
					.show();
			Intent intent_submit = new Intent();
			Bundle bundle_submit = new Bundle();

			// user.setUser_address(user_address);
			// //测试数据
			// user.setUser_head_url("");
			// user.setUser_login_name(tv_userinfo_user_login_name.getText()+"");
			// user.setUser_name(tv_userinfo_user_name.getText()+"");
			// user.setUser_sex(tv_userinfo_user_sex.getText()+"");

			bundle_submit.putSerializable("user", user);
			intent_submit.putExtras(bundle_submit);
			setResult(AppConfig.UPDATE_USER_INFO, intent_submit);
			finish();
			break;
		case R.id.rlyt_userinfo_user_email:// 修改邮箱

			break;
		case R.id.btn_userinfo_user_update:// 修改密码
			Intent intent_psw = new Intent(this, UserPswUpdateActivity.class);
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
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			break;
		case AppConfig.UPDATE_USER_ADDRESS:// 更新用户收货地址信息
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

	/**
	 * 
	 * @描述：性别选择Dialog
	 * @date：2014-6-19
	 */
	private void showDialog() {
		dialog = new Dialog(this, R.style.MyDialog);// 使用自定义主题的Dialog
		// 设置Dialog的内部布局
		LayoutInflater factory = LayoutInflater.from(this);
		final View view = factory.inflate(R.layout.dialog_sex, null);
		// find
		sex_type_group = (RadioGroup) view.findViewById(R.id.sex_type_group);
		btn_userinfo_user_sex_cancel = (Button) view
				.findViewById(R.id.btn_user_info_user_sex_cancel);
		rbtn_userinfo_user_sex_male = (RadioButton) view
				.findViewById(R.id.rbtn_userinfo_user_sex_male);
		rbtn_userinfo_user_sex_female = (RadioButton) view
				.findViewById(R.id.rbtn_userinfo_user_sex_female);

		// 设置监听
		sex_type_group.setOnCheckedChangeListener(this);
		btn_userinfo_user_sex_cancel.setOnClickListener(this);

		sex = (String) tv_userinfo_user_sex.getText();
		if (sex.equals("男")) {
			rbtn_userinfo_user_sex_male.setChecked(true);
			rbtn_userinfo_user_sex_female.setChecked(false);
		} else if (sex.equals("女")) {
			rbtn_userinfo_user_sex_male.setChecked(false);
			rbtn_userinfo_user_sex_female.setChecked(true);
		}

		dialog.setContentView(view);
		dialog.show();
	}

	/**
	 * 男女选择
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rbtn_userinfo_user_sex_male:// 男
			tv_userinfo_user_sex.setText("男");
			dialog.dismiss();
			break;
		case R.id.rbtn_userinfo_user_sex_female:// 女
			tv_userinfo_user_sex.setText("女");
			dialog.dismiss();
			break;

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
}
