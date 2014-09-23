package com.xiuman.xingduoduo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;

/**
 * 
 * @名称：MyDialog2.java
 * @描述：自定义Dialog2
 * @author danding
 * @version
 * @date：2014-7-24
 */
public class CustomDialog2 {
	private Dialog mDialog;
	// Message
	private TextView dialog_message;
	// 取消
	public Button btn_custom_dialog_cancel;
	// 确定
	public Button btn_custom_dialog_sure;

	public CustomDialog2(Context context,String message) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_custom2, null);

		mDialog = new Dialog(context, R.style.MyDialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(false);
//		mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//			@Override
//			public boolean onKey(DialogInterface dialog, int keyCode,
//					KeyEvent event) {
//				if (keyCode == KeyEvent.KEYCODE_BACK) {
//					dismiss();
//					return true;
//				}
//				return false;
//			}
//		});
		dialog_message = (TextView) view
				.findViewById(R.id.tv_dialog_custom_message);
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
