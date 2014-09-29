package com.xiuman.xingduoduo.ui.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskUpdateUserInfoBack;
import com.xiuman.xingduoduo.callback.TaskUserInfoBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.RegexUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.CustomDialog2;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.wheel.OnWheelChangedListener;
import com.xiuman.xingduoduo.view.wheel.WheelView;
import com.xiuman.xingduoduo.view.wheel.adapter.ArrayWheelAdapter;

/**
 * @名称：UpdateUserInfoActivity.java
 * @描述：修改个人信息
 * @author danding 2014-9-20
 */
public class UpdateUserInfoActivity extends Base2Activity implements
		OnClickListener, OnCheckedChangeListener, OnWheelChangedListener {

	/*------------------------------组件---------------------------*/
	// 返回
	private Button btn_back;
	// 标题
	private TextView tv_title;
	// 右侧
	private Button btn_right;
	// 姓名
	private EditText et_update_user_info_name;
	// 昵称
	private EditText et_update_user_info_nickname;
	// 性别
	private LinearLayout llyt_update_user_info_sex;
	private TextView tv_update_user_info_sex;
	// 所在地
	private LinearLayout llyt_update_user_info_address;
	private TextView tv_update_user_info_address;
	// 详细地址
	private EditText et_update_user_info_address_info;
	// 邮编
	private EditText et_update_user_info_ems;
	// 电话
	private EditText et_update_user_info_phone;
	// 生日
	private LinearLayout llyt_update_user_info_birth;
	private TextView tv_update_user_info_birth;
	// 提交修改
	private Button btn_submit_update;
	// 加载进度
	private LoadingDialog loadingdialog;
	//取消修改Dialog;
	private CustomDialog2 dialog_cancel;

	/*--------------------------性别--------------------------*/
	// 组
	private RadioGroup sex_type_group;
	// 男女
	private RadioButton rbtn_userinfo_user_sex_male,
			rbtn_userinfo_user_sex_female;
	// 取消按钮
	private Button btn_userinfo_user_sex_cancel;
	// 性别选择Dialog
	private Dialog dialog_sex;
	// 当前性别
	private String sex;

	/*------------------------------所在地-------------------------------*/
	// ------------------ 地区选择Diaolg-----------------
	// Dialog
	private Dialog dialog_address;
	/**
	 * 省的WheelView控件
	 */
	private WheelView mProvince;
	/**
	 * 市的WheelView控件
	 */
	private WheelView mCity;
	/**
	 * 区的WheelView控件
	 */
	private WheelView mArea;
	// 取消
	private Button btn_dialog_address_cancel;
	// 确定
	private Button btn_dialog_address_sure;
	/**
	 * 所有省
	 */
	private String[] mProvinceDatas;
	/**
	 * key - 省 value - 市s
	 */
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区s
	 */
	private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();

	/**
	 * 当前省的名称
	 */
	private String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	private String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	private String mCurrentAreaName = "";
	/**
	 * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
	 */
	private JSONObject mJsonObj;

	/*-------------------------------------------生日---------------------------------------------*/
	private Dialog dialog_birth;
	// 取消
	private Button btn_dialog_birth_cancel;
	// 确定
	private Button btn_dialog_birth_sure;
	// 时间选择
	private DatePicker date_picker;

	// 从上级界面接收到的用户信息
	private User user;

	// 修改消息返回
	private ActionValue<?> value_update;
	// 消息处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 修改成功
				value_update = (ActionValue<?>) msg.obj;
				if (value_update.isSuccess()) {
					getUserInfo();
				} else {
					loadingdialog.dismiss();
					ToastUtil.ToastView(UpdateUserInfoActivity.this,
							value_update.getMessage());
				}
				break;
			case AppConfig.SUCCESS_USER_INFO:// 获取用户信息
				user = MyApplication.getInstance().getUserInfo();
				Intent intent = new Intent(UpdateUserInfoActivity.this,
						UserInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtras(bundle);
				setResult(AppConfig.RESULT_CODE_OK, intent);
				loadingdialog.dismiss();
				finish();
				overridePendingTransition(
						R.anim.translate_horizontal_finish_in,
						R.anim.translate_horizontal_finish_out);
				break;
			case AppConfig.NET_ERROR_NOTNET:

				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_userinfo);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {
		user = (User) getIntent().getExtras().getSerializable("user");
		initJsonData();
		initDatas();
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		btn_submit_update = (Button) findViewById(R.id.btn_submit_update);
		tv_title = (TextView) findViewById(R.id.tv_common_title);

		et_update_user_info_name = (EditText) findViewById(R.id.et_update_user_info_name);
		et_update_user_info_nickname = (EditText) findViewById(R.id.et_update_user_info_nickname);
		et_update_user_info_address_info = (EditText) findViewById(R.id.et_update_user_info_address_info);
		et_update_user_info_phone = (EditText) findViewById(R.id.et_update_user_info_phone);
		et_update_user_info_ems = (EditText) findViewById(R.id.et_update_user_info_ems);

		llyt_update_user_info_address = (LinearLayout) findViewById(R.id.llyt_update_user_info_address);
		tv_update_user_info_address = (TextView) findViewById(R.id.tv_update_user_info_address);
		llyt_update_user_info_birth = (LinearLayout) findViewById(R.id.llyt_update_user_info_birth);
		tv_update_user_info_birth = (TextView) findViewById(R.id.tv_update_user_info_birth);
		llyt_update_user_info_sex = (LinearLayout) findViewById(R.id.llyt_update_user_info_sex);
		tv_update_user_info_sex = (TextView) findViewById(R.id.tv_update_user_info_sex);

	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText("编辑个人信息");

		tv_update_user_info_address.setText(user.getAreaStore());
		tv_update_user_info_birth.setText(user.getBirth());
		String user_sex = "";
		user_sex = user.getGender();
		tv_update_user_info_sex.setText(user_sex);
		if (user_sex == null) {
			tv_update_user_info_sex.setText("保密");
		}else if(user_sex.equals("male")){
			tv_update_user_info_sex.setText("男");
		}else if(user_sex.equals("female")){
			tv_update_user_info_sex.setText("女");
		}
		et_update_user_info_address_info.setText(user.getAddress());
		et_update_user_info_ems.setText(user.getZipCode());
		et_update_user_info_name.setText(user.getName());
		et_update_user_info_nickname.setText(user.getNickname());
		et_update_user_info_phone.setText(user.getPhone());

	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_submit_update.setOnClickListener(this);
		llyt_update_user_info_address.setOnClickListener(this);
		llyt_update_user_info_birth.setOnClickListener(this);
		llyt_update_user_info_sex.setOnClickListener(this);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:// 返回
			cancelDialog();
			break;
		case R.id.btn_submit_update:// 提交修改
			submitUpdate();
			break;
		case R.id.llyt_update_user_info_address:// 所在地
			showAddressDialog();
			break;
		case R.id.btn_dialog_address_cancel:// 取消选择的地区
			dialog_address.dismiss();
			break;
		case R.id.btn_dialog_address_sure:// 确定选择的地区
			tv_update_user_info_address.setText(mCurrentProviceName
					+ mCurrentCityName + mCurrentAreaName);
			dialog_address.dismiss();
			break;
		case R.id.llyt_update_user_info_birth:// 生日
			showBirthDialg();
			break;
		case R.id.btn_dialog_birth_cancel:
			dialog_birth.dismiss();
			break;
		case R.id.btn_dialog_birth_sure:// 确定日期
			StringBuffer sb = new StringBuffer();
			sb.append(String.format("%d-%02d-%02d", date_picker.getYear(),
					date_picker.getMonth() + 1, date_picker.getDayOfMonth()));
			tv_update_user_info_birth.setText(sb.toString());
			dialog_birth.dismiss();
			break;
		case R.id.llyt_update_user_info_sex:// 性别
			showDialog();
			break;
		case R.id.btn_user_info_user_sex_cancel:// 取消
			dialog_sex.dismiss();
			break;
		}
	}

	/**
	 * 
	 * @描述：性别选择Dialog
	 * @date：2014-6-19
	 */
	private void showDialog() {
		dialog_sex = new Dialog(this, R.style.MyDialog);// 使用自定义主题的Dialog
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

		sex = (String) tv_update_user_info_sex.getText();
		if (sex.equals("男")) {
			rbtn_userinfo_user_sex_male.setChecked(true);
			rbtn_userinfo_user_sex_female.setChecked(false);
		} else if (sex.equals("女")) {
			rbtn_userinfo_user_sex_male.setChecked(false);
			rbtn_userinfo_user_sex_female.setChecked(true);
		}

		dialog_sex.setContentView(view);
		dialog_sex.show();
	}

	/**
	 * 男女选择
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rbtn_userinfo_user_sex_male:// 男
			tv_update_user_info_sex.setText("男");
			dialog_sex.dismiss();
			break;
		case R.id.rbtn_userinfo_user_sex_female:// 女
			tv_update_user_info_sex.setText("女");
			dialog_sex.dismiss();
			break;

		}
	}

	/**
	 * 
	 * @描述：弹出的地区选择Dialog
	 * @date：2014-6-20
	 */
	private void showAddressDialog() {
		dialog_address = new Dialog(this, R.style.MyDialog);// 使用自定义主题的Dialog
		// 设置Dialog的内部布局
		LayoutInflater factory = LayoutInflater.from(this);
		final View view = factory.inflate(R.layout.dialog_city, null);

		// find
		mProvince = (WheelView) view
				.findViewById(R.id.wheel_dialog_address_pro);
		mCity = (WheelView) view.findViewById(R.id.wheel_dialog_address_city);
		mArea = (WheelView) view.findViewById(R.id.wheel_dialog_address_area);
		btn_dialog_address_cancel = (Button) view
				.findViewById(R.id.btn_dialog_address_cancel);
		btn_dialog_address_sure = (Button) view
				.findViewById(R.id.btn_dialog_address_sure);

		// 设置监听
		btn_dialog_address_cancel.setOnClickListener(this);
		btn_dialog_address_sure.setOnClickListener(this);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				mProvinceDatas);
		adapter.setTextSize(15);
		mProvince.setViewAdapter(adapter);
		// 添加change事件
		mProvince.addChangingListener(this);
		// 添加change事件
		mCity.addChangingListener(this);
		// 添加change事件
		mArea.addChangingListener(this);
		updateCities();
		updateAreas();

		dialog_address.setContentView(view);
		dialog_address.show();
	}

	/**
	 * 
	 * @描述：根据当前市，更新区WheelView的信息
	 * @date：2014-6-20
	 */
	private void updateAreas() {
		int cCurrent = mCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[cCurrent];
		String[] areas = mAreaDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				areas);
		adapter.setTextSize(15);
		mArea.setViewAdapter(adapter);
		mArea.setCurrentItem(0);

		int aCurrent = mArea.getCurrentItem();
		mCurrentAreaName = areas[aCurrent];

	}

	/**
	 * 
	 * @描述：根据当前省更新WheelView
	 * @date：2014-6-20
	 */
	private void updateCities() {
		int pCurrent = mProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities);
		adapter.setTextSize(15);
		mCity.setViewAdapter(adapter);
		mCity.setCurrentItem(0);
		updateAreas();
	}

	/**
	 * 
	 * @描述：解析json
	 * @date：2014-6-20
	 */
	private void initDatas() {
		try {
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
				String province = jsonP.getString("p");// 省名字

				mProvinceDatas[i] = province;

				JSONArray jsonCs = null;
				try {
					jsonCs = jsonP.getJSONArray("c");
				} catch (Exception e1) {
					continue;
				}
				String[] mCitiesDatas = new String[jsonCs.length()];
				for (int j = 0; j < jsonCs.length(); j++) {
					JSONObject jsonCity = jsonCs.getJSONObject(j);
					String city = jsonCity.getString("n");// 市名字
					mCitiesDatas[j] = city;
					JSONArray jsonAreas = null;
					try {

						jsonAreas = jsonCity.getJSONArray("a");
					} catch (Exception e) {
						continue;
					}

					String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
					for (int k = 0; k < jsonAreas.length(); k++) {
						String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
						mAreasDatas[k] = area;
					}
					mAreaDatasMap.put(city, mAreasDatas);
				}

				mCitisDatasMap.put(province, mCitiesDatas);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		// mJsonObj = null;
	}

	/**
	 * @描述：从assert文件夹中读取省市区的json文件，然后转化为json对象
	 * @date：2014-6-20
	 */
	private void initJsonData() {
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = getAssets().open("city.json");
			int len = -1;
			byte[] buf = new byte[1001024];
			while ((len = is.read(buf)) != -1) {
				sb.append(new String(buf, 0, len, "gbk"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * WheelView改变监听
	 */
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mProvince) {
			try {
				updateCities();
			} catch (Exception e) {
			}

		} else if (wheel == mCity) {
			try {
				updateAreas();
			} catch (Exception e) {
			}

		} else if (wheel == mArea) {
			mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
		}
	}

	/**
	 * @描述：选择出生日期 2014-9-21
	 */
	public void showBirthDialg() {
		dialog_birth = new Dialog(this, R.style.MyDialog);// 使用自定义主题的Dialog
		// 设置Dialog的内部布局
		LayoutInflater factory = LayoutInflater.from(this);
		final View view = factory.inflate(R.layout.dialog_birth, null);

		btn_dialog_birth_cancel = (Button) view
				.findViewById(R.id.btn_dialog_birth_cancel);
		btn_dialog_birth_sure = (Button) view
				.findViewById(R.id.btn_dialog_birth_sure);
		date_picker = (DatePicker) view.findViewById(R.id.date_picker);

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		date_picker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), null);

		// 设置监听
		btn_dialog_birth_cancel.setOnClickListener(this);
		btn_dialog_birth_sure.setOnClickListener(this);

		dialog_birth.setContentView(view);
		dialog_birth.show();
	}

	/**
	 * @描述：提交修改 2014-9-21
	 */
	private void submitUpdate() {
		String name = et_update_user_info_name.getText().toString().trim();
		String nickname = et_update_user_info_nickname.getText().toString()
				.trim();
		String gender = tv_update_user_info_sex.getText().toString().trim();
		String birth = tv_update_user_info_birth.getText().toString().trim();
		String area = tv_update_user_info_address.getText().toString().trim();
		String address = et_update_user_info_address_info.getText().toString()
				.trim();
		String ems = et_update_user_info_ems.getText().toString().trim();
		String phone = et_update_user_info_phone.getText().toString().trim();

		if (nickname.equals("")) {
			ToastUtil.ToastView(this, "请输入您的昵称");
			return;
		} else if (!phone.equals("") && !phone.matches(RegexUtil.regex_phone)) {
			ToastUtil.ToastView(this, "请填写正确的电话号码");
			return;
		} else if (!ems.equals("") && !ems.matches(RegexUtil.regex_ems)) {
			ToastUtil.ToastView(this, "请填写正确的邮政编码");
			return;
		}
		HttpUrlProvider.getIntance().getUpdateUserInfo(this,
				new TaskUpdateUserInfoBack(handler),
				URLConfig.UPDATE_USER_INFO, user.getUserId(), gender, birth,
				name, area, address, ems, phone, nickname);
		loadingdialog.show(UpdateUserInfoActivity.this);

	}

	/**
	 * @描述：重新获取用户信息 2014-9-21
	 */
	private void getUserInfo() {
		HttpUrlProvider.getIntance().getUserInfo(this,
				new TaskUserInfoBack(handler), URLConfig.USERINFO,
				user.getUserName());
	}
	/**
	 * @描述：取消修改Dialog
	 * 2014-9-23
	 */
	private void cancelDialog(){
		dialog_cancel = new CustomDialog2(this, "确认取消修改？");
		dialog_cancel.show();
		dialog_cancel.btn_custom_dialog_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog_cancel.dismiss();
				finish();
				overridePendingTransition(R.anim.translate_horizontal_finish_in,
						R.anim.translate_horizontal_finish_out);
			}
		});
		dialog_cancel.btn_custom_dialog_cancel
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog_cancel.dismiss();
					}
				});
	}
	/**
	 * 按键点击事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 返回键
			cancelDialog();
		}
		return true;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss();
		loadingdialog = null;
	}
}
