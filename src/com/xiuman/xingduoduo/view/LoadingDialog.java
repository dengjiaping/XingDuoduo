package com.xiuman.xingduoduo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.xiuman.xingduoduo.R;

/**
 * 
 * @名称：MyDialog.java
 * @描述：自定义加载Dialog
 * @author danding
 * @version
 * @date：2014-7-24
 */
public class LoadingDialog {
	private Dialog mDialog;

	public LoadingDialog(Context context) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.loading, null);

		mDialog = new Dialog(context, R.style.MyDialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dismiss();
					return true;
				}
				return false;
			}
		});

	}

	public void show(Context context) {
		mDialog.show();
	}

	public void dismiss() {
		mDialog.dismiss();
	}
}
