package com.xiuman.xingduoduo.lock.ui;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.lock.util.LockPatternUtils;
import com.xiuman.xingduoduo.lock.view.LockPatternView;
import com.xiuman.xingduoduo.lock.view.LockPatternView.Cell;
import com.xiuman.xingduoduo.ui.activity.MainActivity;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ImageCropUtils;
import com.xiuman.xingduoduo.view.CircleImageView;

/**
 * 
 * 名称：UnlockGesturePasswordActivity.java 描述：解锁手势密码界面
 * 
 * @author danding
 * @version 2014-6-18
 */
public class UnlockGesturePasswordActivity extends Base2Activity {
	private LockPatternView mLockPatternView;
	private int mFailedPatternAttemptsSinceLastTimeout = 0;
	private CountDownTimer mCountdownTimer = null;
	private Handler mHandler = new Handler();
	private TextView mHeadTextView;
	private Animation mShakeAnim;
	// 头像
	private CircleImageView iv_head;
	// 头像工具
	private ImageCropUtils cropUtils;
	private Toast mToast;

	private boolean cancel = false;

	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	private void showToast(CharSequence message) {
		if (null == mToast) {
			mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			mToast.setText(message);
		}

		mToast.show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturepassword_unlock);

		initData();
		findViewById();
		initUI();

		mLockPatternView = (LockPatternView) this
				.findViewById(R.id.gesturepwd_unlock_lockview);
		mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
		mLockPatternView.setTactileFeedbackEnabled(true);
		mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
		mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!MyApplication.getInstance().getLockPatternUtils()
				.savedPatternExists()) {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCountdownTimer != null)
			mCountdownTimer.cancel();
	}

	private Runnable mClearPatternRunnable = new Runnable() {
		public void run() {
			mLockPatternView.clearPattern();
		}
	};

	protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

		public void onPatternStart() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
			patternInProgress();
		}

		public void onPatternCleared() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
		}

		public void onPatternDetected(List<LockPatternView.Cell> pattern) {
			if (pattern == null)
				return;
			if (MyApplication.getInstance().getLockPatternUtils()
					.checkPattern(pattern)) {
				mLockPatternView
						.setDisplayMode(LockPatternView.DisplayMode.Correct);
				// 判断是解锁还是删除手势密码
				if (cancel) {
					showToast("解锁成功");
					Intent intent = new Intent(
							UnlockGesturePasswordActivity.this,
							MainActivity.class);
					startActivity(intent);
					overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
					finish();

				} else {

					showToast("已取消");
					MyApplication.getInstance().getLockPatternUtils()
							.clearLock();
					finish();
					overridePendingTransition(
							R.anim.translate_horizontal_finish_in,
							R.anim.translate_horizontal_finish_out);
				}
			} else {
				mLockPatternView
						.setDisplayMode(LockPatternView.DisplayMode.Wrong);
				if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
					mFailedPatternAttemptsSinceLastTimeout++;
					int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
							- mFailedPatternAttemptsSinceLastTimeout;
					if (retry >= 0) {
						if (retry == 0)
							showToast("您已5次输错密码，请30秒后再试");
						mHeadTextView.setText("密码错误，还可以再输入" + retry + "次");
						mHeadTextView.setTextColor(Color.RED);
						mHeadTextView.startAnimation(mShakeAnim);
					}

				} else {
					showToast("输入长度不够，请重试");
				}

				if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
					mHandler.postDelayed(attemptLockout, 2000);
				} else {
					mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
				}
			}
		}

		public void onPatternCellAdded(List<Cell> pattern) {

		}

		private void patternInProgress() {
		}
	};
	Runnable attemptLockout = new Runnable() {

		@Override
		public void run() {
			mLockPatternView.clearPattern();
			mLockPatternView.setEnabled(false);
			mCountdownTimer = new CountDownTimer(
					LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
					if (secondsRemaining > 0) {
						mHeadTextView.setText(secondsRemaining + " 秒后重试");
					} else {
						mHeadTextView.setText("请绘制手势密码");
						mHeadTextView.setTextColor(Color.WHITE);
					}

				}

				@Override
				public void onFinish() {
					mLockPatternView.setEnabled(true);
					mFailedPatternAttemptsSinceLastTimeout = 0;
				}
			}.start();
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.weiboitem_pic_loading) //
				// 在ImageView加载过程中显示图片
				.showImageOnLoading(R.drawable.bg_head)
				.showImageForEmptyUri(R.drawable.bg_head)
				// image连接地址为空时
				.showImageOnFail(R.drawable.bg_head)
				// image加载失败
				.cacheInMemory(false)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true)
				// 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.NONE).build();
		cancel = getIntent().getBooleanExtra("cancel", false);
		cropUtils = new ImageCropUtils(this);
	}

	@Override
	protected void findViewById() {
		iv_head = (CircleImageView) findViewById(R.id.gesturepwd_unlock_face);
	}

	@Override
	protected void initUI() {
		if (MyApplication.getInstance().isUserLogin()) {
			imageLoader.displayImage(
					URLConfig.IMG_IP
							+ MyApplication.getInstance().getUserInfo()
									.getHead_image(), iv_head, options);
		} else {
			try {
				File head = new File(cropUtils.createDirectory()
						+ cropUtils.createNewPhotoName());
				if (head.exists()) {
					Bitmap user_head_bitmap = BitmapFactory
							.decodeFile(cropUtils.createDirectory()
									+ cropUtils.createNewPhotoName());
					iv_head.setImageBitmap(user_head_bitmap);
				}
			} catch (Exception e) {
				iv_head.setImageResource(R.drawable.bg_head);
			}
		}
	}

	@Override
	protected void setListener() {

	}

}
