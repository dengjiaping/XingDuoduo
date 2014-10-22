package com.xiuman.xingduoduo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.lock.ui.UnlockGesturePasswordActivity;
import com.xiuman.xingduoduo.util.SharedPreUtils;

/**
 * 
 * @名称：SplashActivity.java
 * @描述：应用启动界面
 * @author danding
 * @version
 * @date：2014-7-2
 */
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// 在打开应用的时候判断是否初次安装或者刚升级版本，初次安装或升级版本则进行部分数据操作
		// 当前保存的上一版本或者初次安装无版本信息时的版本信息
		String local_version_name = MyApplication.getInstance()
				.getLocalVersionName();
		int local_version_code = MyApplication.getInstance()
				.getLocalVersionCode();
		// 当前安装的版本信息
		String current_version_name = MyApplication.getInstance()
				.getVersionName();
		int current_version_code = MyApplication.getInstance().getVersionCode();
		
		//应用升级或者初次打开则播放引导页
		if (local_version_name != current_version_name
				&& local_version_code != current_version_code) {
			MyApplication.getInstance().saveVersion(current_version_name, current_version_code);
			SharedPreUtils.setBoolean(this, true, "guide", "first_start");
			MyApplication.getInstance().saveBBSAds(null);
			MyApplication.getInstance().saveAds(null);
		}

		// 打开界面之后判断是否是初次启动应用，如果是则直接进入应用，如果不是则进入引导页
		if (SharedPreUtils.getBoolean(this, true, "guide", "first_start")) {// 初次启动
			Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			finish();
		} else {
			final boolean result = MyApplication.getInstance()
					.getLockPatternUtils().isPatternExist();

			// 延迟两秒钟后进入主界面
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// 是否设置了手势密码
					if (result) {
						Intent intent = new Intent(SplashActivity.this,
								UnlockGesturePasswordActivity.class);
						// 用来标记是删除密码，还是解锁!（解锁）
						intent.putExtra("cancel", true);
						startActivity(intent);
						overridePendingTransition(
								R.anim.translate_horizontal_start_in,
								R.anim.translate_horizontal_start_out);
						finish();
					} else {
						Intent intent = new Intent(SplashActivity.this,
								MainActivity.class);
						startActivity(intent);
						overridePendingTransition(
								R.anim.translate_horizontal_start_in,
								R.anim.translate_horizontal_start_out);
						finish();
					}
				}
			}, 2000);
		}
	}
}
