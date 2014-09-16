package com.xiuman.xingduoduo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;

/**
 * 
 * @名称：MyDialog.java
 * @描述：自定义Dialog
 * @author danding
 * @version
 * @date：2014-7-24
 */
public class CustomDialog {
	private Dialog mDialog;
	// 标题
	private TextView dialog_title;
	// Message
	private TextView dialog_message;
	// 取消
	public Button btn_custom_dialog_cancel;
	// 确定
	public Button btn_custom_dialog_sure;

	public CustomDialog(Context context, String title, String message) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_custom, null);

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
		dialog_title = (TextView) view
				.findViewById(R.id.tv_dialog_custom_title);
		dialog_message = (TextView) view
				.findViewById(R.id.tv_dialog_custom_message);
		dialog_title.setText(title);
		dialog_message.setText(message);

		btn_custom_dialog_cancel = (Button) view
				.findViewById(R.id.btn_custom_dialog_cancel);
		btn_custom_dialog_sure = (Button) view
				.findViewById(R.id.btn_custom_dialog_sure);

	}

	public void show() {
		mDialog.show();
	}

	public void dismiss() {
		mDialog.dismiss();
	}
}
