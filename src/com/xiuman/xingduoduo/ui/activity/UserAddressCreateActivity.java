package com.xiuman.xingduoduo.ui.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskAddAddressBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.RegexUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.wheel.OnWheelChangedListener;
import com.xiuman.xingduoduo.view.wheel.WheelView;
import com.xiuman.xingduoduo.view.wheel.adapter.ArrayWheelAdapter;

/**
 * 
 * @名称：UserAddressActivity.java
 * @描述：用户收货地址编辑界面
 * @author danding
 * @version
 * @date：2014-6-20
 */
public class UserAddressCreateActivity extends Base2Activity implements
		OnClickListener, OnWheelChangedListener {

	/*-------------------------------------组件--------------------------------*/
	/**
	 * 提交修改
	 */
	private Button btn_address_submit;
	/**
	 * 标题栏
	 */
	private TextView tv_title;
	/**
	 * 返回
	 */
	private Button btn_back_user_address;
	/**
	 * 所在地区
	 */
	private LinearLayout llyt_create_user_receipt_address;
	/**
	 * 所在地区
	 */
	private TextView tv_create_user_receipt_address;
	/**
	 * 详细地址
	 */
	private EditText et_create_user_receipt_address_detail;
	/**
	 * 收货地址
	 */
	private EditText et_create_user_receipt_name;
	/**
	 * 手机号码
	 */
	private EditText et_create_user_receipt_phone;
	/**
	 * 邮编
	 */
	private EditText et_create_user_receipt_ems;
	/**
	 * 保存提交
	 */
	private Button btn_create_user_delete_address;
	// ------------------ 地区选择Diaolg-----------------
	// Dialog
	private Dialog dialog;
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

	// 提交进度加载

	private LoadingDialog loadingdialog;

	/*------------------------------数据-------------------------------*/
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

	// 当前登陆的用户
	private User user;

	// 请求添加新地址---------------------------------------------
	// 请求返回结果
	private ActionValue<?> value_add;

	// 消息处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:
				value_add = (ActionValue<?>) msg.obj;
				if (!value_add.isSuccess()) {// 添加失败
					ToastUtil.ToastView(UserAddressCreateActivity.this,
							value_add.getMessage() + ",请重试！");
				} else {
					ToastUtil.ToastView(UserAddressCreateActivity.this,
							value_add.getMessage());
					setResult(AppConfig.RESULT_CODE_ADD_ADDRESS);
					finish();
				}
				loadingdialog.dismiss();
				break;
			case AppConfig.NET_ERROR_NOTNET:// 连接失败
				ToastUtil.ToastView(UserAddressCreateActivity.this,
						"网络连接失败，请检查网络后重试");
				loadingdialog.dismiss();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_address_create);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {

		user = MyApplication.getInstance().getUserInfo();

		initJsonData();
		initDatas();
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		btn_address_submit = (Button) findViewById(R.id.btn_address_submit);
		btn_back_user_address = (Button) findViewById(R.id.btn_back_user_address);
		tv_title = (TextView) findViewById(R.id.tv_address_title);
		llyt_create_user_receipt_address = (LinearLayout) findViewById(R.id.llyt_create_user_receipt_address);
		tv_create_user_receipt_address = (TextView) findViewById(R.id.tv_create_user_receipt_address);
		et_create_user_receipt_address_detail = (EditText) findViewById(R.id.et_create_user_receipt_address_detail);
		et_create_user_receipt_name = (EditText) findViewById(R.id.et_create_user_receipt_name);
		et_create_user_receipt_phone = (EditText) findViewById(R.id.et_create_user_receipt_phone);
		et_create_user_receipt_ems = (EditText) findViewById(R.id.et_create_user_receipt_ems);
		btn_create_user_delete_address = (Button) findViewById(R.id.btn_create_user_delete_address);

	}

	@Override
	protected void initUI() {
		btn_address_submit.setVisibility(View.INVISIBLE);
		tv_title.setText("新建收货地址");
	}

	@Override
	protected void setListener() {
		llyt_create_user_receipt_address.setOnClickListener(this);
		btn_back_user_address.setOnClickListener(this);
		btn_create_user_delete_address.setOnClickListener(this);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.llyt_create_user_receipt_address:// 收货地址
			showAddressDialog();
			break;
		case R.id.btn_back_user_address:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;
		case R.id.btn_dialog_address_cancel:// 取消选择的地区
			dialog.dismiss();
			break;
		case R.id.btn_dialog_address_sure:// 确定选择的地区
			tv_create_user_receipt_address.setText(mCurrentProviceName
					+ mCurrentCityName + mCurrentAreaName);
			dialog.dismiss();
			break;
		case R.id.btn_create_user_delete_address:// 提交收货地址
			submitAddress();
			break;
		}
	}
	/**
	 * @描述：请求添加收货地址，并进行判断
	 * 2014-8-21
	 */
	private void submitAddress() {
		String address_area = tv_create_user_receipt_address.getText()
				.toString().trim();
		String address_detail = et_create_user_receipt_address_detail.getText()
				.toString().trim();
		String address_user = et_create_user_receipt_name.getText().toString()
				.trim();
		String address_phone = et_create_user_receipt_phone.getText()
				.toString().trim();
		String address_ems = et_create_user_receipt_ems.getText().toString()
				.trim();

		if (address_area.equals("")) {
			ToastUtil.ToastView(this, "请选择收货地区");
			return;
		} else if (address_detail.equals("")) {
			ToastUtil.ToastView(this, "请填写详细收货地址");
			return;
		} else if (address_user.equals("")) {
			ToastUtil.ToastView(this, "请填写收货人姓名");
			return;
		} else if (address_phone.equals("")) {
			ToastUtil.ToastView(this, "请填写收货人联系电话");
			return;
		} else if (address_ems.equals("")) {
			ToastUtil.ToastView(this, "请填写收货人所在地的邮政编码");
			return;
		} else if (!address_phone.matches(RegexUtil.regex_phone)) {
			ToastUtil.ToastView(this, "请填写正确的电话号码");
			return;
		} else if (!address_ems.matches(RegexUtil.regex_ems)) {
			ToastUtil.ToastView(this, "请填写正确的邮政编码");
			return;
		}

		//请求添加收货地址
		HttpUrlProvider.getIntance().getAddAddresses(this,
				new TaskAddAddressBack(handler), URLConfig.ADD_ADDRESS,
				user.getUserId(), address_user, address_phone, "",
				address_area, address_detail, address_ems);
		loadingdialog.show(UserAddressCreateActivity.this);

	}

	/**
	 * 
	 * @描述：弹出的地区选择Dialog
	 * @date：2014-6-20
	 */
	private void showAddressDialog() {
		dialog = new Dialog(this, R.style.MyDialog);// 使用自定义主题的Dialog
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

		dialog.setContentView(view);
		dialog.show();
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
	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss();
		loadingdialog = null;
	}
}
